# -*- coding: utf-8 -*-
"""
Scraper inclusivo - Computrabajo (cl)
Salida: CSV + JSONL a OUT_DIR (/app/out por defecto)

ENV (.env):
  KW=discapacidad,inclusion,movilidad%20reducida,teletrabajo
  PORTALES=computrabajo
  PAGS=2
  OUTPUT_BASE=empleos_inclusivos
  OUT_DIR=/app/out  LOG_DIR=/app/logs  TMP_DIR=/app/tmp
  SLEEP_MIN=1.0  SLEEP_MAX=2.2
"""

import os, re, json, time, random, hashlib, pathlib
from datetime import date, datetime, timedelta
from typing import List, Dict, Optional, Tuple

import requests
from bs4 import BeautifulSoup
import pandas as pd

# -------------------- Config/env --------------------
OUT_DIR = os.getenv("OUT_DIR", "/app/out")
LOG_DIR = os.getenv("LOG_DIR", "/app/logs")
TMP_DIR = os.getenv("TMP_DIR", "/app/tmp")
KW = [k.strip() for k in os.getenv("KW", "discapacidad,inclusion").split(",") if k.strip()]
PORTALES = [p.strip().lower() for p in os.getenv("PORTALES", "computrabajo").split(",") if p.strip()]
PAGS = int(os.getenv("PAGS", "2"))
OUTPUT_BASE = os.getenv("OUTPUT_BASE", "empleos_inclusivos")
SLEEP_MIN = float(os.getenv("SLEEP_MIN", "1.0"))
SLEEP_MAX = float(os.getenv("SLEEP_MAX", "2.2"))

for d in (OUT_DIR, LOG_DIR, TMP_DIR):
    pathlib.Path(d).mkdir(parents=True, exist_ok=True)

def log(msg: str):
    print(msg, flush=True)

# -------------------- Utilidades --------------------
MESES_ES = {
    "enero":1,"febrero":2,"marzo":3,"abril":4,"mayo":5,"junio":6,
    "julio":7,"agosto":8,"septiembre":9,"setiembre":9,"octubre":10,"noviembre":11,"diciembre":12
}

KEYWORDS_INCLUSION = [
    "discapacidad", "inclusión", "inclusivo", "neurodiverg", "tea", "asperger",
    "movilidad reducida", "silla de ruedas", "acomodo razonable", "accesibilidad",
    "rampa", "ascensor", "baño accesible", "teletrabajo", "remoto", "home office"
]
KEYWORDS_TRANSPORTE = [
    "transporte", "bus de acercamiento", "locomoción", "movilización",
    "estacionamiento", "estacionamientos", "bip", "subsidio transporte"
]

UA_FALLBACK = (
    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 "
    "(KHTML, like Gecko) Chrome/123.0 Safari/537.36"
)

def session_with_headers() -> requests.Session:
    s = requests.Session()
    s.headers.update({
        "User-Agent": UA_FALLBACK,
        "Accept-Language": "es-CL,es;q=0.9",
        "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
        "Upgrade-Insecure-Requests": "1",
        "Connection": "close",
    })
    s.timeout = 25
    return s

def slow():
    time.sleep(random.uniform(SLEEP_MIN, SLEEP_MAX))

def safe_text(node) -> str:
    if not node: return ""
    return re.sub(r"\s+", " ", node.get_text(" ", strip=True))

def normalizar_fecha_es(texto: str, hoy: Optional[date]=None) -> Optional[str]:
    if not texto:
        return None
    t = texto.strip().lower()
    hoy = hoy or date.today()

    if "hoy" in t:   return hoy.isoformat()
    if "ayer" in t:  return (hoy - timedelta(days=1)).isoformat()

    m = re.search(r"hace\s+(\d+)\s*d[ií]as?", t)
    if m:
        d = int(m.group(1))
        return (hoy - timedelta(days=d)).isoformat()

    m = re.search(r"\b(\d{1,2})[/-](\d{1,2})[/-](\d{2,4})\b", t)
    if m:
        dd, mm, yyyy = int(m.group(1)), int(m.group(2)), int(m.group(3))
        if yyyy < 100: yyyy += 2000
        return date(yyyy, mm, dd).isoformat()

    m = re.search(r"(\d{1,2})\s+de\s+([a-záéíóú]+)(?:\s+de\s+(\d{4}))?", t)
    if m:
        dd = int(m.group(1))
        mes_txt = m.group(2).translate(str.maketrans("áéíóú","aeiou"))
        mm = MESES_ES.get(mes_txt)
        yyyy = int(m.group(3)) if m.group(3) else hoy.year
        if mm:
            try:
                return date(yyyy, mm, dd).isoformat()
            except Exception:
                return None
    return None

def flags_accesibilidad(texto: str) -> Tuple[bool, bool, List[str], List[str]]:
    t = (texto or "").lower()
    acc_hits = [kw for kw in KEYWORDS_INCLUSION if kw in t]
    trans_hits = [kw for kw in KEYWORDS_TRANSPORTE if kw in t]
    return (len(acc_hits) > 0, len(trans_hits) > 0, acc_hits, trans_hits)

# -------------------- Extractor: Computrabajo --------------------
class ExtractorComputrabajo:
    base_url = "https://cl.computrabajo.com"

    def __init__(self, session: Optional[requests.Session]=None):
        self.s = session or session_with_headers()

    def _search_urls(self, query: str, page: int) -> List[str]:
        """
        Probamos 2 patrones de listado porque el sitio cambia rutas a veces:
          1) /trabajo-de-{query}?p={page}
          2) /ofertas-de-trabajo/?q={query}&p={page}
        """
        return [
            f"{self.base_url}/trabajo-de-{query}?p={page}",
            f"{self.base_url}/ofertas-de-trabajo/?q={query}&p={page}",
        ]

    def _collect_cards(self, soup: BeautifulSoup) -> List[str]:
        # 1) enlaces típicos
        anchors = soup.select('a.js-o-link[href]')
        # 2) fallback genérico
        if not anchors:
            anchors = soup.select('a[href*="/oferta-"], a[href*="/ofertas-de-trabajo/"]')
        # 3) extraemos URLs únicas
        urls = []
        seen = set()
        for a in anchors:
            href = a.get("href") or ""
            if not href or href.startswith("#"): 
                continue
            url = href if href.startswith("http") else (self.base_url + href)
            if url not in seen:
                seen.add(url); urls.append(url)
        return urls

    def buscar(self, query: str, pages: int=1) -> List[Dict]:
        resultados: List[Dict] = []
        for page in range(1, pages+1):
            list_urls = self._search_urls(query, page)
            page_found = False

            for url in list_urls:
                slow(); log(f"[LIST] GET {url}")
                r = self.s.get(url, timeout=25)
                if r.status_code != 200:
                    log(f"[WARN] HTTP {r.status_code} en {url}")
                    continue

                soup = BeautifulSoup(r.text, "lxml")
                job_urls = self._collect_cards(soup)
                log(f"[LIST] p{page} -> {len(job_urls)} enlaces")

                if not job_urls:
                    continue  # probamos el siguiente patrón de listado

                page_found = True
                for job_url in job_urls:
                    info = self._detalle(job_url)
                    if info: resultados.append(info)
                break  # ya usamos el patrón que funcionó; no pruebo el segundo

            if not page_found:
                log(f"[INFO] Sin tarjetas en p{page} para '{query}' (ambos patrones).")

        return resultados

    def _detalle(self, url: str) -> Optional[Dict]:
        try:
            slow(); log(f"[JOB] GET {url}")
            r = self.s.get(url, timeout=25)
            if r.status_code != 200:
                log(f"[WARN] Detalle {r.status_code}: {url}")
                return None

            soup = BeautifulSoup(r.text, "lxml")

            titulo = safe_text(soup.select_one("h1"))
            if not titulo:
                # fallback título
                titulo = safe_text(soup.select_one("header h1, .box_detail h1"))

            empresa = safe_text(
                soup.select_one('[data-qa="company-name"], a[data-qa="company"], .fc_base a, .box_detail a[href*="empresa"]')
            )

            # Ubicación: probamos varios selectores
            ubicacion = safe_text(
                soup.select_one('[data-qa="job-location"], .i_location, li:contains("Ubicación"), span[class*="location"]')
            )
            if not ubicacion:
                ubicacion = safe_text(soup.find(string=re.compile(r"Ubicaci[oó]n")).parent if soup.find(string=re.compile(r"Ubicaci[oó]n")) else None)

            # Fecha (Publicado / Hace X días)
            fecha_txt = ""
            txt_candidates = soup.find_all(string=re.compile(r"Publicad|Publicado|Hace|d[ií]as"))
            if txt_candidates:
                fecha_txt = safe_text(txt_candidates[0])
            if not fecha_txt:
                fecha_txt = safe_text(soup.select_one('span[class*="date"], li:contains("Publicado"), time'))
            fecha_norm = normalizar_fecha_es(fecha_txt)

            # Descripción para flags de accesibilidad/transporte
            desc = safe_text(soup.select_one("article, #job-description, .job-desc, .box_detail, .fc_base, .description"))

            acc_ok, trans_ok, acc_hits, trans_hits = flags_accesibilidad(f"{titulo} {desc}")

            return {
                "fecha_publicacion": fecha_norm,
                "titulo": titulo,
                "empresa": empresa,
                "ubicacion": ubicacion,
                "fuente": "Computrabajo",
                "url": url,
                "accesibilidad_mencionada": acc_ok,
                "transporte_mencionado": trans_ok,
                "tags_accesibilidad": ";".join(acc_hits),
                "tags_transporte": ";".join(trans_hits),
                "hash": hashlib.md5(url.encode("utf-8")).hexdigest(),
            }
        except Exception as e:
            log(f"[ERR] detalle {url}: {e}")
            return None

# -------------------- Orquestación --------------------
def run() -> List[Dict]:
    session = session_with_headers()
    resultados: List[Dict] = []

    if "computrabajo" in PORTALES:
        compu = ExtractorComputrabajo(session)
        for kw in KW:
            log(f"[RUN] Computrabajo '{kw}' (páginas={PAGS})")
            resultados.extend(compu.buscar(kw, pages=PAGS))

    # deduplicar por URL/hash
    seen = set(); dedup = []
    for r in resultados:
        key = r.get("hash") or r.get("url")
        if key and key not in seen:
            seen.add(key); dedup.append(r)

    # ordenar por fecha (desc)
    def _key(r):
        f = r.get("fecha_publicacion")
        try: return datetime.strptime(f, "%Y-%m-%d") if f else datetime.min
        except: return datetime.min
    dedup.sort(key=_key, reverse=True)
    log(f"[RUN] total resultados dedup: {len(dedup)}")
    return dedup

def exportar(rows: List[Dict]):
    base = f"{OUT_DIR}/{OUTPUT_BASE}"
    cols = ["fecha_publicacion","titulo","empresa","ubicacion","fuente","url",
            "accesibilidad_mencionada","transporte_mencionado","tags_accesibilidad","tags_transporte"]
    df = pd.DataFrame(rows or [])
    for c in cols:
        if c not in df.columns: df[c] = None
    df[cols].to_csv(base + ".csv", index=False, encoding="utf-8-sig")
    with open(base + ".jsonl", "w", encoding="utf-8") as f:
        for r in (rows or []):
            f.write(json.dumps(r, ensure_ascii=False) + "\n")
    open(f"{OUT_DIR}/last_run.ok","w").write(datetime.now().isoformat())
    log(f"OK -> {base}.csv / {base}.jsonl")

if __name__ == "__main__":
    data = run()
    exportar(data)

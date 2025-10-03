FROM python:3.12-slim
ENV PYTHONDONTWRITEBYTECODE=1 PYTHONUNBUFFERED=1 TZ=America/Santiago
RUN apt-get update && apt-get install -y --no-install-recommends \
    tzdata ca-certificates build-essential libxml2-dev libxslt1-dev \
    && rm -rf /var/lib/apt/lists/*

RUN useradd -ms /bin/bash appuser
WORKDIR /app

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY src/ ./src/
RUN mkdir -p /app/out /app/logs /app/tmp && chown -R appuser:appuser /app
USER appuser

ENV OUT_DIR=/app/out LOG_DIR=/app/logs TMP_DIR=/app/tmp \
    KW="discapacidad,inclusion,movilidad%20reducida,teletrabajo" \
    PORTALES="computrabajo" \
    PAGS=2 \
    OUTPUT_BASE="empleos_inclusivos"

CMD ["python","/app/src/scraper_inclusivo.py"]
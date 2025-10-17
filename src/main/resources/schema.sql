-- =====================================================
-- Script SQL - Modelo IntegraJob (MySQL)
-- =====================================================

CREATE TABLE RUBRO (
    id_rubro INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE EMPRESA (
    id_empresa INT AUTO_INCREMENT PRIMARY KEY,
    id_x_fuente VARCHAR(50),
    nombre_emp VARCHAR(255) NOT NULL,
    descrip_emp TEXT,
    direccion VARCHAR(255),
    url VARCHAR(255),
    id_rubro INT,
    CONSTRAINT fk_empresa_rubro FOREIGN KEY (id_rubro) REFERENCES RUBRO(id_rubro)
);

CREATE TABLE UBICACION (
    id_ubicacion INT AUTO_INCREMENT PRIMARY KEY,
    region VARCHAR(100),
    comuna VARCHAR(100),
    direccion VARCHAR(255),
    latitud DECIMAL(10,6),
    longitud DECIMAL(10,6)
);

CREATE TABLE OFERTA_JOB (
    id_oferta INT AUTO_INCREMENT PRIMARY KEY,
    id_empresa INT NOT NULL,
    id_ubicacion INT,
    fuente VARCHAR(50) NOT NULL,
    puesto VARCHAR(255) NOT NULL,
    descripcion TEXT,
    url VARCHAR(255),
    contrato VARCHAR(100),
    exp_req VARCHAR(100),
    fecha_post DATE,
    valid_through DATE,
    hrs_laborales VARCHAR(100),
    fecha_inicio_job DATE,
    vacantes INT,
    CONSTRAINT fk_empresa FOREIGN KEY (id_empresa) REFERENCES EMPRESA(id_empresa),
    CONSTRAINT fk_ubicacion FOREIGN KEY (id_ubicacion) REFERENCES UBICACION(id_ubicacion)
);

-- Tabla SALARIO
CREATE TABLE SALARIO (
    id_salario INT AUTO_INCREMENT PRIMARY KEY,
    id_oferta INT NOT NULL,
    moneda VARCHAR(10),
    minimo DECIMAL(12,2),
    maximo DECIMAL(12,2),
    mostrar_sueldo BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_salario_oferta FOREIGN KEY (id_oferta) REFERENCES OFERTA_JOB(id_oferta)
);

-- Tabla REQUISITO_EDUCACION
CREATE TABLE REQUISITO_EDUCACION (
    id_reqedu INT AUTO_INCREMENT PRIMARY KEY,
    id_oferta INT NOT NULL,
    nivel VARCHAR(100),
    descripcion VARCHAR(255),
    CONSTRAINT fk_reqedu_oferta FOREIGN KEY (id_oferta) REFERENCES OFERTA_JOB(id_oferta)
);

-- Tabla CATEGORIA_OCUPACIONAL
CREATE TABLE CATEGORIA_OCUPACIONAL (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    id_oferta INT NOT NULL,
    codigo VARCHAR(50),
    nombre VARCHAR(150),
    CONSTRAINT fk_categoria_oferta FOREIGN KEY (id_oferta) REFERENCES OFERTA_JOB(id_oferta)
);

-- Tabla ACOMODOS_ACCESIBILIDAD
CREATE TABLE ACOMODOS_ACCESIBILIDAD (
    id_acomodo INT AUTO_INCREMENT PRIMARY KEY,
    id_oferta INT NOT NULL,
    tipo VARCHAR(100),         -- Física, Sensorial, Cognitiva, Organizacional
    detalle VARCHAR(255),      -- Ej: "Teletrabajo", "Rampa de acceso"
    CONSTRAINT fk_acomodo_oferta FOREIGN KEY (id_oferta) REFERENCES OFERTA_JOB(id_oferta)
);

-- Tabla BENEFICIOS
CREATE TABLE BENEFICIOS (
    id_beneficio INT AUTO_INCREMENT PRIMARY KEY,
    id_oferta INT NOT NULL,
    descripcion VARCHAR(255),
    CONSTRAINT fk_beneficio_oferta FOREIGN KEY (id_oferta) REFERENCES OFERTA_JOB(id_oferta)
);

-- tabla TAXONOMÍA
CREATE TABLE TAXONOMIA (
    id_taxonomia INT AUTO_INCREMENT PRIMARY KEY,
    categoria VARCHAR(100) NOT NULL,   -- Ej: "Modalidad", "Jornada", "Industria"
    valor VARCHAR(100) NOT NULL        -- Ej: "Remoto", "Part-time", "TI"
);

-- tabla sesiones
CREATE TABLE CHAT_SESSION (
    id_session INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,   -- opcional si manejas usuarios registrados
    fecha_inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_fin TIMESTAMP NULL
);

CREATE TABLE CHAT_MESSAGE (
    id_message INT AUTO_INCREMENT PRIMARY KEY,
    id_session INT NOT NULL,
    remitente ENUM('USER','BOT') NOT NULL,
    texto TEXT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_message_session FOREIGN KEY (id_session) REFERENCES CHAT_SESSION(id_session)
);

-- =====================================================
-- Fin del schema.sql
-- =====================================================

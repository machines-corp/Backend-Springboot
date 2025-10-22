-- RUBRO
INSERT INTO RUBRO (nombre)
VALUES ('Tecnología');

-- EMPRESA (con rubro asociado)
INSERT INTO EMPRESA (id_x_fuente, nombre_emp, descrip_emp, direccion, url, id_rubro)
VALUES ('LNK001', 'TechCorp', 'Empresa de desarrollo de software', 'Av. Principal 123', 'https://techcorp.com', 1);

-- UBICACION
INSERT INTO UBICACION (region, comuna, direccion, latitud, longitud)
VALUES ('Metropolitana', 'Santiago', 'Av. Providencia 456', -33.4372, -70.6506);

-- OFERTA_JOB (relacionada a empresa y ubicación)
INSERT INTO OFERTA_JOB (id_empresa, id_ubicacion, fuente, puesto, descripcion, url, contrato, exp_req, fecha_post, valid_through, hrs_laborales, fecha_inicio_job, vacantes)
VALUES (1, 1, 'LinkedIn', 'Desarrollador Java', 'Desarrollo backend con Spring Boot', 'https://techcorp.com/jobs/1', 'Indefinido', '2 años de experiencia', '2025-09-29', '2025-12-31', '40 horas', '2025-10-15', 2);

-- SALARIO
INSERT INTO SALARIO (id_oferta, moneda, minimo, maximo, mostrar_sueldo)
VALUES (1, 'CLP', 1200000, 1800000, TRUE);

-- REQUISITO_EDUCACION
INSERT INTO REQUISITO_EDUCACION (id_oferta, nivel, descripcion)
VALUES (1, 'Universitario', 'Ingeniería en Informática o afín');

-- CATEGORIA_OCUPACIONAL
INSERT INTO CATEGORIA_OCUPACIONAL (id_oferta, codigo, nombre)
VALUES (1, 'DEV01', 'Profesional TI');

-- TAXONOMIA
INSERT INTO TAXONOMIA (categoria, valor)
VALUES ('Accesibilidad', 'Teletrabajo');

-- ACOMODOS_ACCESIBILIDAD
INSERT INTO ACOMODOS_ACCESIBILIDAD (id_oferta, tipo, detalle, id_taxonomia)
VALUES (1, 'Organizacional', 'Horario flexible', 1);

-- BENEFICIOS
INSERT INTO BENEFICIOS (id_oferta, descripcion)
VALUES (1, 'Seguro médico complementario');

-- CHAT_SESSION
INSERT INTO CHAT_SESSION (user_id, fecha_inicio, fecha_fin)
VALUES (123, '2025-09-29 09:00:00', '2025-09-29 10:00:00');

-- CHAT_MESSAGE
INSERT INTO CHAT_MESSAGE (id_session, remitente, texto, fecha)
VALUES (1, 'USER', 'Hola, ¿la oferta sigue disponible?', '2025-09-29 09:05:00');

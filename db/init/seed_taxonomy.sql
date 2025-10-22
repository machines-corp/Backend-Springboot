-- === AGRUPACIONES ===
INSERT INTO AGRUPACION (nombre) VALUES
                                    ('Modalidad'),
                                    ('Seniority'),
                                    ('Área'),
                                    ('Industria'),
                                    ('Ubicación');

-- === CARACTERISTICAS ===
-- Modalidad
INSERT INTO CARACTERISTICA (nombre, id_agrupacion) VALUES
                                                       ('Remoto', 1),
                                                       ('Híbrido', 1),
                                                       ('Presencial', 1);

-- Seniority
INSERT INTO CARACTERISTICA (nombre, id_agrupacion) VALUES
                                                       ('Junior', 2),
                                                       ('Semi', 2),
                                                       ('Senior', 2);

-- Área
INSERT INTO CARACTERISTICA (nombre, id_agrupacion) VALUES
                                                       ('Datos', 3),
                                                       ('Desarrollo', 3),
                                                       ('Infraestructura', 3),
                                                       ('Calidad', 3),
                                                       ('Soporte', 3),
                                                       ('Diseño', 3),
                                                       ('Docencia', 3);

-- Industria
INSERT INTO CARACTERISTICA (nombre, id_agrupacion) VALUES
                                                       ('Tecnología', 4),
                                                       ('Educación', 4),
                                                       ('Salud', 4),
                                                       ('Finanzas', 4);

-- Ubicación
INSERT INTO CARACTERISTICA (nombre, id_agrupacion) VALUES
                                                       ('Chile', 5),
                                                       ('LatAm', 5);

-- === SINONIMOS ===
-- Modalidad
INSERT INTO SINONIMO (palabra, id_caracteristica) VALUES
                                                      ('remoto', 1), ('teletrabajo', 1), ('desde casa', 1),
                                                      ('hibrido', 2), ('mixto', 2),
                                                      ('presencial', 3), ('en oficina', 3);

-- Seniority
INSERT INTO SINONIMO (palabra, id_caracteristica) VALUES
                                                      ('jr', 4), ('junior', 4), ('entry', 4),
                                                      ('ssr', 5), ('semi-senior', 5), ('semisenior', 5),
                                                      ('sr', 6), ('senior', 6), ('experto', 6);

-- Área
INSERT INTO SINONIMO (palabra, id_caracteristica) VALUES
                                                      ('data', 7), ('analitica', 7), ('analytics', 7),
                                                      ('dev', 8), ('programacion', 8), ('software', 8),
                                                      ('devops', 9), ('ops', 9),
                                                      ('qa', 10), ('testing', 10),
                                                      ('helpdesk', 11), ('mesa de ayuda', 11),
                                                      ('ux', 12), ('ui', 12), ('ux/ui', 12),
                                                      ('profesor', 13), ('enseñanza', 13);

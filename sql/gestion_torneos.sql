DROP DATABASE IF EXISTS gestion_torneos;
CREATE DATABASE gestion_torneos;
USE gestion_torneos;

CREATE TABLE usuarios (
    id      INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(30) NOT NULL,
    clave   VARCHAR(30) NOT NULL,
    id_rol  INT NOT NULL
);

CREATE TABLE equipos (
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    nombre             VARCHAR(50) NOT NULL,
    entrenador         VARCHAR(50) NOT NULL,
    cantidad_jugadores INT NOT NULL
);

CREATE TABLE jugadores (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    nombre    VARCHAR(50) NOT NULL,
    edad      INT NOT NULL,
    posicion  VARCHAR(30) NOT NULL,
    goles     INT NOT NULL DEFAULT 0,
    id_equipo INT NOT NULL,
    FOREIGN KEY (id_equipo) REFERENCES equipos(id)
);

CREATE TABLE partidos (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    id_local        INT NOT NULL,
    id_visitante    INT NOT NULL,
    fecha           DATE NOT NULL,
    goles_local     INT NOT NULL,
    goles_visitante INT NOT NULL,
    FOREIGN KEY (id_local)     REFERENCES equipos(id),
    FOREIGN KEY (id_visitante) REFERENCES equipos(id)
);

INSERT INTO usuarios (usuario, clave, id_rol) VALUES
    ('ADMIN', '1234', 1),
    ('USUARIO', '5678', 2);

DELIMITER $$

CREATE PROCEDURE sp_login(IN p_usuario VARCHAR(30), IN p_clave VARCHAR(30))
BEGIN
    SELECT id_rol FROM usuarios WHERE usuario = p_usuario AND clave = p_clave;
END$$

CREATE PROCEDURE sp_registrar_equipo(IN p_nombre VARCHAR(50), IN p_entrenador VARCHAR(50), IN p_cantidad INT)
BEGIN
    INSERT INTO equipos (nombre, entrenador, cantidad_jugadores)
    VALUES (p_nombre, p_entrenador, p_cantidad);
END$$

CREATE PROCEDURE sp_registrar_jugador(IN p_nombre VARCHAR(50), IN p_edad INT, IN p_posicion VARCHAR(30), IN p_id_equipo INT)
BEGIN
    INSERT INTO jugadores (nombre, edad, posicion, id_equipo)
    VALUES (p_nombre, p_edad, p_posicion, p_id_equipo);
END$$

CREATE PROCEDURE sp_registrar_partido(IN p_id_local INT, IN p_id_visitante INT, IN p_fecha DATE,
                                      IN p_goles_local INT, IN p_goles_visitante INT)
BEGIN
    INSERT INTO partidos (id_local, id_visitante, fecha, goles_local, goles_visitante)
    VALUES (p_id_local, p_id_visitante, p_fecha, p_goles_local, p_goles_visitante);
END$$

CREATE PROCEDURE sp_registrar_goles(IN p_id_jugador INT, IN p_goles INT)
BEGIN
    UPDATE jugadores SET goles = goles + p_goles WHERE id = p_id_jugador;
END$$

DELIMITER ;

CREATE VIEW vista_equipos AS
SELECT nombre AS equipo, entrenador, cantidad_jugadores
FROM equipos;

CREATE VIEW vista_posiciones AS
SELECT e.nombre AS equipo,
       COUNT(r.gf) AS pj,
       IFNULL(SUM(r.gf > r.gc), 0) AS pg,
       IFNULL(SUM(r.gf = r.gc), 0) AS pe,
       IFNULL(SUM(r.gf < r.gc), 0) AS pp,
       IFNULL(SUM(r.gf), 0) AS gf,
       IFNULL(SUM(r.gc), 0) AS gc,
       IFNULL(SUM(r.gf > r.gc), 0) * 3 + IFNULL(SUM(r.gf = r.gc), 0) AS puntos
FROM equipos e
LEFT JOIN (
    SELECT id_local AS id_equipo, goles_local AS gf, goles_visitante AS gc FROM partidos
    UNION ALL
    SELECT id_visitante, goles_visitante, goles_local FROM partidos
) r ON r.id_equipo = e.id
GROUP BY e.id, e.nombre;

CREATE VIEW vista_partidos AS
SELECT l.nombre AS local,
       v.nombre AS visitante,
       p.fecha,
       CONCAT(p.goles_local, ' - ', p.goles_visitante) AS marcador,
       CASE WHEN p.goles_local > p.goles_visitante THEN l.nombre
            WHEN p.goles_local < p.goles_visitante THEN v.nombre
            ELSE 'Empate' END AS ganador
FROM partidos p
JOIN equipos l ON l.id = p.id_local
JOIN equipos v ON v.id = p.id_visitante;

CREATE VIEW vista_goleadores AS
SELECT j.nombre AS jugador, e.nombre AS equipo, j.goles
FROM jugadores j
JOIN equipos e ON e.id = j.id_equipo;

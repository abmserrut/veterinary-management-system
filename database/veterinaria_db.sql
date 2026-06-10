-- Script: database/veterinaria_db.sql
-- Ejecutar en MySQL Workbench o terminal: mysql -u root -p < veterinaria_db.sql

-- 1. Crear la base de datos
CREATE DATABASE IF NOT EXISTS veterinaria_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
 
USE veterinaria_db;
 
-- 2. Tabla de Pacientes (animales)
CREATE TABLE IF NOT EXISTS pacientes (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    nombre         VARCHAR(100)   NOT NULL,
    especie        VARCHAR(50)    NOT NULL,
    raza           VARCHAR(80),
    peso_kg        DECIMAL(6,2)   NOT NULL CHECK(peso_kg > 0),
    edad_meses     INT            NOT NULL CHECK(edad_meses >= 0),
    fecha_ingreso  DATE           NOT NULL DEFAULT (CURRENT_DATE),
    activo         BOOLEAN        NOT NULL DEFAULT TRUE,
    created_at     TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
 
-- 3. Tabla de Empleados (veterinarios y recepcionistas)
CREATE TABLE IF NOT EXISTS empleados (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    nombre         VARCHAR(100)   NOT NULL,
    apellido       VARCHAR(100)   NOT NULL,
    cedula         VARCHAR(20)    UNIQUE NOT NULL,
    telefono       VARCHAR(20),
    email          VARCHAR(150),
    tipo           ENUM('VETERINARIO','RECEPCIONISTA') NOT NULL,
    salario_base   DECIMAL(10,2)  NOT NULL,
    departamento   VARCHAR(80),
    fecha_contrato DATE           DEFAULT (CURRENT_DATE),
    activo         BOOLEAN        DEFAULT TRUE,
    -- Campos especificos de Veterinario (NULL si es Recepcionista)
    num_licencia   VARCHAR(50),
    especialidad   VARCHAR(100),
    -- Campos especificos de Recepcionista (NULL si es Veterinario)
    turno          VARCHAR(20),
    es_bilingue    BOOLEAN        DEFAULT FALSE
);
 
-- 4. Tabla de Consultas
CREATE TABLE IF NOT EXISTS consultas (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    paciente_id    INT            NOT NULL,
    veterinario_id INT            NOT NULL,
    motivo         TEXT           NOT NULL,
    diagnostico    TEXT,
    tratamiento    TEXT,
    estado         ENUM('PENDIENTE','EN_PROGRESO','COMPLETADA','CANCELADA')
                                  DEFAULT 'PENDIENTE',
    es_urgente     BOOLEAN        DEFAULT FALSE,
    precio_base    DECIMAL(8,2)   NOT NULL,
    fecha_hora     TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    -- Claves foraneas: vincula con otras tablas
    FOREIGN KEY (paciente_id)    REFERENCES pacientes(id) ON DELETE RESTRICT,
    FOREIGN KEY (veterinario_id) REFERENCES empleados(id) ON DELETE RESTRICT
);
 
-- 5. Datos de prueba iniciales
INSERT INTO pacientes (nombre, especie, raza, peso_kg, edad_meses) VALUES
    ('FIRULAIS', 'perro',  'Labrador',       25.5,  36),
    ('MICHI',    'gato',   'Siames',          4.2,  24),
    ('BUGS',     'conejo', 'Rex',             2.1,  12),
    ('LORO',     'ave',    'Loro Amazonico',  0.4,  60);
 
INSERT INTO empleados (nombre, apellido, cedula, tipo, salario_base,
                       num_licencia, especialidad) VALUES
    ('Carlos', 'Martinez', '12345678', 'VETERINARIO', 2500.00, 'VET-001', 'Cirugia'),
    ('Maria',  'Lopez',    '87654321', 'VETERINARIO', 2200.00, 'VET-002', 'Dermatologia');
 
INSERT INTO empleados (nombre, apellido, cedula, tipo, salario_base, turno) VALUES
    ('Ana', 'Gomez', '11223344', 'RECEPCIONISTA', 800.00, 'MANANA');

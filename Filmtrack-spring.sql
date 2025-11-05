-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 04-11-2025 a las 23:15:34
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `filmtrack_spring`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `actor`
--

CREATE TABLE `actor` (
  `nombreArtistico` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contenidoaudiovisual`
--

CREATE TABLE `contenidoaudiovisual` (
  `id` int(11) NOT NULL,
  `activo` bit(1) NOT NULL,
  `fechaLanzamiento` varchar(255) DEFAULT NULL,
  `genero` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `puntuacionEnEstrellas` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `contenidoaudiovisual`
--

INSERT INTO `contenidoaudiovisual` (`id`, `activo`, `fechaLanzamiento`, `genero`, `nombre`, `puntuacionEnEstrellas`) VALUES
(1, b'1', '1900-01-01', 'Desconocido', 'Twin Peaks', 0),
(2, b'1', '1900-01-01', 'Desconocido', 'Harry Potter', 0),
(3, b'1', '1900-01-01', 'Desconocido', 'Lalaland', 0),
(4, b'1', '1900-01-01', 'Desconocido', 'Blancanieves', 0),
(5, b'1', '1900-01-01', 'Desconocido', 'Harry Potter y la piedra filosofal', 0),
(6, b'1', '1900-01-01', 'Desconocido', 'Blue Velvet', 0),
(7, b'1', '1900-01-01', 'Desconocido', 'El Conjuro 4', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contenido_reparto`
--

CREATE TABLE `contenido_reparto` (
  `contenido_id` int(11) NOT NULL,
  `actor_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pelicula`
--

CREATE TABLE `pelicula` (
  `duracionEnMinutos` int(11) NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persona`
--

CREATE TABLE `persona` (
  `id` int(11) NOT NULL,
  `activo` bit(1) NOT NULL,
  `apellido` varchar(255) DEFAULT NULL,
  `fechaNacimiento` date DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `version` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `persona`
--

INSERT INTO `persona` (`id`, `activo`, `apellido`, `fechaNacimiento`, `nombre`, `version`) VALUES
(1, b'1', NULL, '2000-07-22', 'Luana', 6),
(2, b'1', NULL, '2000-12-12', 'ana', 1),
(3, b'1', NULL, '2999-11-11', 'Anabel', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `serie`
--

CREATE TABLE `serie` (
  `capitulosPorTemporada` int(11) NOT NULL,
  `temporadas` int(11) NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `clave` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nombreUsuario` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`clave`, `email`, `nombreUsuario`, `id`) VALUES
('$2a$10$lM3HukxNh9TxwWK/f5u5Hem6dHe0sRb93/59H0TI44GmgvAxQdBo2', 'lua@email.com', 'lua223', 1),
('$2a$10$uqHrWxDExOq80uIQbQxFJuemoKQtiSRA3MRFlJnl3fGkQ947iHICG', 'ana@gmail.com', 'ana', 2),
('$2a$10$6O2hxxvw96zyhacvKebAPOPOhN5U5bB0gMxkBtdQmdXTpi/ow3lA.', 'ana@email.com', 'ana', 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario_favoritos`
--

CREATE TABLE `usuario_favoritos` (
  `usuario_id` int(11) NOT NULL,
  `contenido_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario_favoritos`
--

INSERT INTO `usuario_favoritos` (`usuario_id`, `contenido_id`) VALUES
(2, 5),
(3, 5),
(3, 1),
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 7);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `visualizacion`
--

CREATE TABLE `visualizacion` (
  `id` int(11) NOT NULL,
  `activo` bit(1) NOT NULL,
  `fechaVisto` date DEFAULT NULL,
  `puntuacion` int(11) NOT NULL,
  `contenido_id` int(11) DEFAULT NULL,
  `usuario_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `visualizacion`
--

INSERT INTO `visualizacion` (`id`, `activo`, `fechaVisto`, `puntuacion`, `contenido_id`, `usuario_id`) VALUES
(1, b'1', '2025-09-17', 5, 1, 1),
(2, b'1', '2025-11-04', 0, 1, 2),
(3, b'1', '2025-11-04', 0, 1, 3),
(4, b'1', '2025-11-04', 0, 6, 3);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `actor`
--
ALTER TABLE `actor`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `contenidoaudiovisual`
--
ALTER TABLE `contenidoaudiovisual`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `contenido_reparto`
--
ALTER TABLE `contenido_reparto`
  ADD KEY `FKiufuybj8e8nfucc95yip6xovh` (`actor_id`),
  ADD KEY `FKm7sgabvbrkelkp8mb75l95to1` (`contenido_id`);

--
-- Indices de la tabla `pelicula`
--
ALTER TABLE `pelicula`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `persona`
--
ALTER TABLE `persona`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `serie`
--
ALTER TABLE `serie`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario_favoritos`
--
ALTER TABLE `usuario_favoritos`
  ADD KEY `FKf079a5ughnsg51bampp6tc0wx` (`contenido_id`),
  ADD KEY `FKggbhrfhjc5uj6004yurxynryj` (`usuario_id`);

--
-- Indices de la tabla `visualizacion`
--
ALTER TABLE `visualizacion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK9i8uqgbx34geu2kkw7tmdfvfb` (`contenido_id`),
  ADD KEY `FKbnv228knl4u4cxn8y594pdf08` (`usuario_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `contenidoaudiovisual`
--
ALTER TABLE `contenidoaudiovisual`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `persona`
--
ALTER TABLE `persona`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `visualizacion`
--
ALTER TABLE `visualizacion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `actor`
--
ALTER TABLE `actor`
  ADD CONSTRAINT `FK4bs0msd0tr0wp58yvx47eyvsl` FOREIGN KEY (`id`) REFERENCES `persona` (`id`);

--
-- Filtros para la tabla `contenido_reparto`
--
ALTER TABLE `contenido_reparto`
  ADD CONSTRAINT `FKiufuybj8e8nfucc95yip6xovh` FOREIGN KEY (`actor_id`) REFERENCES `actor` (`id`),
  ADD CONSTRAINT `FKm7sgabvbrkelkp8mb75l95to1` FOREIGN KEY (`contenido_id`) REFERENCES `contenidoaudiovisual` (`id`);

--
-- Filtros para la tabla `pelicula`
--
ALTER TABLE `pelicula`
  ADD CONSTRAINT `FKtc01s0dcae4i1677ds0up525` FOREIGN KEY (`id`) REFERENCES `contenidoaudiovisual` (`id`);

--
-- Filtros para la tabla `serie`
--
ALTER TABLE `serie`
  ADD CONSTRAINT `FK8xw07vaddgi338raahb33b09n` FOREIGN KEY (`id`) REFERENCES `contenidoaudiovisual` (`id`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `FKjrqrcophu7vdwqiwn7gif5t3a` FOREIGN KEY (`id`) REFERENCES `persona` (`id`);

--
-- Filtros para la tabla `usuario_favoritos`
--
ALTER TABLE `usuario_favoritos`
  ADD CONSTRAINT `FKf079a5ughnsg51bampp6tc0wx` FOREIGN KEY (`contenido_id`) REFERENCES `contenidoaudiovisual` (`id`),
  ADD CONSTRAINT `FKggbhrfhjc5uj6004yurxynryj` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`);

--
-- Filtros para la tabla `visualizacion`
--
ALTER TABLE `visualizacion`
  ADD CONSTRAINT `FK9i8uqgbx34geu2kkw7tmdfvfb` FOREIGN KEY (`contenido_id`) REFERENCES `contenidoaudiovisual` (`id`),
  ADD CONSTRAINT `FKbnv228knl4u4cxn8y594pdf08` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

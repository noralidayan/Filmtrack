-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 31-10-2025 a las 02:51:27
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
-- Base de datos: `filmtrack`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `actor`
--

CREATE TABLE `actor` (
  `id` int(11) NOT NULL,
  `nombreArtistico` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contenidoaudiovisual`
--

CREATE TABLE `contenidoaudiovisual` (
  `activo` bit(1) NOT NULL,
  `capitulosPorTemporada` int(11) DEFAULT NULL,
  `duracionEnMinutos` int(11) DEFAULT NULL,
  `fechaLanzamiento` date DEFAULT NULL,
  `id` int(11) NOT NULL,
  `puntuacionEnEstrellas` int(11) NOT NULL,
  `temporadas` int(11) DEFAULT NULL,
  `DTYPE` varchar(31) NOT NULL,
  `genero` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `contenidoaudiovisual`
--

INSERT INTO `contenidoaudiovisual` (`activo`, `capitulosPorTemporada`, `duracionEnMinutos`, `fechaLanzamiento`, `id`, `puntuacionEnEstrellas`, `temporadas`, `DTYPE`, `genero`, `nombre`) VALUES
(b'1', NULL, NULL, '1900-01-01', 1, 5, NULL, 'ContenidoAudiovisual', 'Desconocido', 'Twin Peaks');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contenido_reparto`
--

CREATE TABLE `contenido_reparto` (
  `actor_id` int(11) NOT NULL,
  `contenido_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persona`
--

CREATE TABLE `persona` (
  `activo` bit(1) NOT NULL,
  `fechaNacimiento` date DEFAULT NULL,
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `apellido` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `persona`
--

INSERT INTO `persona` (`activo`, `fechaNacimiento`, `id`, `version`, `apellido`, `nombre`) VALUES
(b'1', '1989-01-22', 1, 0, NULL, 'Lucia');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` int(11) NOT NULL,
  `clave` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nombreUsuario` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `clave`, `email`, `nombreUsuario`) VALUES
(1, '$2a$10$Zbcq.Zo2eipSjY9gIsCwWOLQmX2udpFhV2/NfG8j3Ah8L517qm7De', 'lu@email.com', 'luz');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario_favoritos`
--

CREATE TABLE `usuario_favoritos` (
  `contenido_id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `visualizacion`
--

CREATE TABLE `visualizacion` (
  `activo` bit(1) NOT NULL,
  `contenido_id` int(11) DEFAULT NULL,
  `fechaVisto` date DEFAULT NULL,
  `id` int(11) NOT NULL,
  `puntuacion` int(11) NOT NULL,
  `usuario_id` int(11) DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `visualizacion`
--

INSERT INTO `visualizacion` (`activo`, `contenido_id`, `fechaVisto`, `id`, `puntuacion`, `usuario_id`, `version`) VALUES
(b'1', 1, '2025-09-17', 1, 0, 1, NULL);

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
  ADD KEY `FKs6j4824br0dkvmn0i0o0s8j5c` (`contenido_id`);

--
-- Indices de la tabla `persona`
--
ALTER TABLE `persona`
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
  ADD KEY `FKmki3x0mpigsgt003i9i7g68a4` (`contenido_id`),
  ADD KEY `FKggbhrfhjc5uj6004yurxynryj` (`usuario_id`);

--
-- Indices de la tabla `visualizacion`
--
ALTER TABLE `visualizacion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK270btqa427eiumv1qbe170a9q` (`contenido_id`),
  ADD KEY `FKbnv228knl4u4cxn8y594pdf08` (`usuario_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `contenidoaudiovisual`
--
ALTER TABLE `contenidoaudiovisual`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `persona`
--
ALTER TABLE `persona`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `visualizacion`
--
ALTER TABLE `visualizacion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

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
  ADD CONSTRAINT `FKs6j4824br0dkvmn0i0o0s8j5c` FOREIGN KEY (`contenido_id`) REFERENCES `contenidoaudiovisual` (`id`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `FKjrqrcophu7vdwqiwn7gif5t3a` FOREIGN KEY (`id`) REFERENCES `persona` (`id`);

--
-- Filtros para la tabla `usuario_favoritos`
--
ALTER TABLE `usuario_favoritos`
  ADD CONSTRAINT `FKggbhrfhjc5uj6004yurxynryj` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `FKmki3x0mpigsgt003i9i7g68a4` FOREIGN KEY (`contenido_id`) REFERENCES `contenidoaudiovisual` (`id`);

--
-- Filtros para la tabla `visualizacion`
--
ALTER TABLE `visualizacion`
  ADD CONSTRAINT `FK270btqa427eiumv1qbe170a9q` FOREIGN KEY (`contenido_id`) REFERENCES `contenidoaudiovisual` (`id`),
  ADD CONSTRAINT `FKbnv228knl4u4cxn8y594pdf08` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

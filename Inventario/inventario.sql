-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 29-06-2023 a las 04:34:48
-- Versión del servidor: 5.7.33
-- Versión de PHP: 7.4.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `inventario`
--
CREATE DATABASE inventario;
USE inventario;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `brands`
--

CREATE TABLE `brands` (
  `id` int(11) NOT NULL,
  `name` varchar(60) COLLATE utf8_spanish_ci NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `brands`
--

INSERT INTO `brands` (`id`, `name`, `deleted`, `created_at`, `updated_at`) VALUES
(1, 'LTH', 0, '2023-06-12 16:22:11', '2023-06-12 16:22:11'),
(2, 'ITALIKA', 0, '2023-06-12 16:22:28', '2023-06-12 16:22:28');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorie_tools`
--

CREATE TABLE `categorie_tools` (
  `id` int(11) NOT NULL,
  `name` varchar(60) COLLATE utf8_spanish_ci NOT NULL,
  `description` text COLLATE utf8_spanish_ci NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `categorie_tools`
--

INSERT INTO `categorie_tools` (`id`, `name`, `description`, `deleted`, `created_at`, `updated_at`) VALUES
(1, 'Cintas', 'Cintas', 0, '2023-06-12 16:24:25', '2023-06-12 16:24:25'),
(2, 'Herramienta de corte', '.', 0, '2023-06-12 16:24:41', '2023-06-12 16:24:41');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clients`
--

CREATE TABLE `clients` (
  `id` int(11) NOT NULL,
  `name` varchar(40) COLLATE utf8_spanish_ci NOT NULL,
  `last_name` varchar(60) COLLATE utf8_spanish_ci NOT NULL,
  `email` varchar(80) COLLATE utf8_spanish_ci NOT NULL,
  `phone` varchar(15) COLLATE utf8_spanish_ci NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `clients`
--

INSERT INTO `clients` (`id`, `name`, `last_name`, `email`, `phone`, `deleted`, `created_at`, `updated_at`) VALUES
(1, 'FULANO', 'DE TAL', 'jaretzi2@gmail.com', '7891005104', 0, '2023-06-11 18:29:54', '2023-06-11 20:39:58'),
(4, 'SUTANO', 'DE TAL', 'jaqui27@webpoint.mx', '7891004920', 0, '2023-06-11 19:59:27', '2023-06-11 20:40:02');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inventory_tools`
--

CREATE TABLE `inventory_tools` (
  `id` int(11) NOT NULL,
  `categoryId` int(11) NOT NULL,
  `name` varchar(60) COLLATE utf8_spanish_ci NOT NULL,
  `description` text COLLATE utf8_spanish_ci NOT NULL,
  `quantity` int(11) NOT NULL,
  `status` enum('Buen Estado','Mal Estado','Dañadas') COLLATE utf8_spanish_ci NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `inventory_tools`
--

INSERT INTO `inventory_tools` (`id`, `categoryId`, `name`, `description`, `quantity`, `status`, `deleted`, `created_at`, `updated_at`) VALUES
(1, 1, 'Cinta Adhesiva2', 'Cinta Adhesiva desc', 1, 'Buen Estado', 0, '2023-06-28 07:03:27', '2023-06-28 07:19:57');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `brandId` int(11) NOT NULL,
  `name` varchar(60) COLLATE utf8_spanish_ci NOT NULL,
  `description` text COLLATE utf8_spanish_ci NOT NULL,
  `cost` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `selling_price` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `quantity` int(11) NOT NULL,
  `unit` enum('Par','Pieza','Juego','Kit','Conjunto','Set') COLLATE utf8_spanish_ci NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `products`
--

INSERT INTO `products` (`id`, `brandId`, `name`, `description`, `cost`, `selling_price`, `quantity`, `unit`, `deleted`, `created_at`, `updated_at`) VALUES
(1, 1, 'Bateria CTXSL-85', '.', '650', '750', 3, 'Pieza', 0, '2023-06-28 07:59:56', '2023-06-29 03:10:40');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `provided_services`
--

CREATE TABLE `provided_services` (
  `id` int(11) NOT NULL,
  `transactionProvidedServices` int(11) NOT NULL,
  `clientId` int(11) NOT NULL,
  `serviceId` int(11) NOT NULL,
  `currentPrice` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `note` text COLLATE utf8_spanish_ci NOT NULL,
  `delivered_date` varchar(120) COLLATE utf8_spanish_ci DEFAULT NULL,
  `limit_date` varchar(120) COLLATE utf8_spanish_ci DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `provided_services`
--

INSERT INTO `provided_services` (`id`, `transactionProvidedServices`, `clientId`, `serviceId`, `currentPrice`, `note`, `delivered_date`, `limit_date`, `deleted`, `created_at`, `updated_at`) VALUES
(1, 0, 1, 1, '200', 'ww', '01/07/24', '01/07/24', 0, '2023-06-29 04:27:07', '2023-06-29 04:27:07');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `services`
--

CREATE TABLE `services` (
  `id` int(11) NOT NULL,
  `name` varchar(60) COLLATE utf8_spanish_ci NOT NULL,
  `description` text COLLATE utf8_spanish_ci NOT NULL,
  `price` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `services`
--

INSERT INTO `services` (`id`, `name`, `description`, `price`, `deleted`, `created_at`, `updated_at`) VALUES
(1, 'Cambio de aceite', 'Cambio de aceite sencillo', '200', 0, '2023-06-12 16:26:16', '2023-06-12 16:26:16'),
(2, 'Cambio de balatas', '.', '500', 0, '2023-06-12 16:27:31', '2023-06-12 16:27:31');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `transactions`
--

CREATE TABLE `transactions` (
  `id` int(11) NOT NULL,
  `modelId` int(11) NOT NULL,
  `type` enum('entry','outgoing') COLLATE utf8_spanish_ci NOT NULL,
  `model` enum('tools','products') COLLATE utf8_spanish_ci NOT NULL,
  `quantity` int(11) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `transactions`
--

INSERT INTO `transactions` (`id`, `modelId`, `type`, `model`, `quantity`, `deleted`, `created_at`, `updated_at`) VALUES
(1, 1, 'entry', 'products', 1, 0, '2023-06-28 08:59:06', '2023-06-28 08:59:06'),
(4, 1, 'outgoing', 'products', 4, 0, '2023-06-29 03:09:35', '2023-06-29 03:09:35'),
(5, 1, 'outgoing', 'products', 3, 0, '2023-06-29 03:10:40', '2023-06-29 03:10:40');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `transactions_provided_services`
--

CREATE TABLE `transactions_provided_services` (
  `id` int(11) NOT NULL,
  `clientId` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `total` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `status` enum('Pendiente','En Proceso','Completado','Entregado') COLLATE utf8_spanish_ci NOT NULL,
  `paymentStatus` enum('Pendiente','Pagado','Cancelado') COLLATE utf8_spanish_ci NOT NULL,
  `paymentType` enum('Efectivo','Tarjeta') COLLATE utf8_spanish_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `transactions_provided_services`
--

INSERT INTO `transactions_provided_services` (`id`, `clientId`, `quantity`, `total`, `status`, `paymentStatus`, `paymentType`, `created_at`, `updated_at`) VALUES
(3, 1, 1, '200.0', 'Pendiente', 'Pagado', 'Efectivo', '2023-06-29 04:27:07', '2023-06-29 04:27:07');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `transactions_sales`
--

CREATE TABLE `transactions_sales` (
  `id` int(11) NOT NULL,
  `clientId` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `total` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `paymentStatus` enum('Pendiente','Pagado','Cancelado') COLLATE utf8_spanish_ci NOT NULL,
  `paymentType` enum('Efectivo','Tarjeta') COLLATE utf8_spanish_ci NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `transactions_sales`
--

INSERT INTO `transactions_sales` (`id`, `clientId`, `quantity`, `total`, `paymentStatus`, `paymentType`, `deleted`, `created_at`, `updated_at`) VALUES
(3, 1, 1, '750.0', 'Pagado', 'Efectivo', 0, '2023-06-29 03:09:35', '2023-06-29 03:09:35'),
(4, 1, 1, '750.0', 'Pagado', 'Efectivo', 0, '2023-06-29 03:10:39', '2023-06-29 03:10:39');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `transactions_sales_products`
--

CREATE TABLE `transactions_sales_products` (
  `id` int(11) NOT NULL,
  `transactionId` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  `currentPrice` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `quantity` int(11) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `transactions_sales_products`
--

INSERT INTO `transactions_sales_products` (`id`, `transactionId`, `productId`, `currentPrice`, `quantity`, `deleted`, `created_at`, `updated_at`) VALUES
(1, 0, 1, '750', 1, 0, '2023-06-29 03:06:18', '2023-06-29 03:06:18'),
(2, 0, 1, '750', 1, 0, '2023-06-29 03:07:02', '2023-06-29 03:07:02'),
(3, 3, 1, '750', 1, 0, '2023-06-29 03:09:35', '2023-06-29 03:09:35'),
(4, 4, 1, '750', 1, 0, '2023-06-29 03:10:40', '2023-06-29 03:10:40');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(40) COLLATE utf8_spanish_ci NOT NULL,
  `last_name` varchar(60) COLLATE utf8_spanish_ci NOT NULL,
  `email` varchar(120) COLLATE utf8_spanish_ci NOT NULL,
  `username` varchar(60) COLLATE utf8_spanish_ci NOT NULL,
  `password` text COLLATE utf8_spanish_ci NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`id`, `name`, `last_name`, `email`, `username`, `password`, `deleted`, `created_at`, `updated_at`) VALUES
(1, 'Admin', 'Admin', 'admin@mail.com', 'admin', 'Hir3ci7ih6LJ7PnhE+rE9A==', 0, '2023-06-29 04:33:53', '2023-06-29 04:34:00'),
(2, 'Fulano', 'De tal', 'fulano@mail.com', 'fulano', 'ii9jU5C+f7TJ7PnhE+rE9A==', 0, '2023-06-11 21:53:17', '2023-06-29 04:31:10');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `brands`
--
ALTER TABLE `brands`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `categorie_tools`
--
ALTER TABLE `categorie_tools`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `clients`
--
ALTER TABLE `clients`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email_unique_clients` (`email`);

--
-- Indices de la tabla `inventory_tools`
--
ALTER TABLE `inventory_tools`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `provided_services`
--
ALTER TABLE `provided_services`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `services`
--
ALTER TABLE `services`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `transactions_provided_services`
--
ALTER TABLE `transactions_provided_services`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `transactions_sales`
--
ALTER TABLE `transactions_sales`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `transactions_sales_products`
--
ALTER TABLE `transactions_sales_products`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email_unique` (`email`),
  ADD UNIQUE KEY `username_unique` (`username`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `brands`
--
ALTER TABLE `brands`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `categorie_tools`
--
ALTER TABLE `categorie_tools`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `clients`
--
ALTER TABLE `clients`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `inventory_tools`
--
ALTER TABLE `inventory_tools`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `provided_services`
--
ALTER TABLE `provided_services`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `services`
--
ALTER TABLE `services`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `transactions`
--
ALTER TABLE `transactions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `transactions_provided_services`
--
ALTER TABLE `transactions_provided_services`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `transactions_sales`
--
ALTER TABLE `transactions_sales`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `transactions_sales_products`
--
ALTER TABLE `transactions_sales_products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

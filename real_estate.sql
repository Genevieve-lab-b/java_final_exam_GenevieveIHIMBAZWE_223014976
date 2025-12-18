-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Dec 18, 2025 at 09:40 AM
-- Server version: 8.3.0
-- PHP Version: 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `real_estate`
--

-- --------------------------------------------------------

--
-- Table structure for table `images`
--

DROP TABLE IF EXISTS `images`;
CREATE TABLE IF NOT EXISTS `images` (
  `image_id` int NOT NULL AUTO_INCREMENT,
  `property_id` int DEFAULT NULL,
  `image_url` varchar(255) NOT NULL,
  `caption` varchar(100) DEFAULT NULL,
  `uploaded_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`image_id`),
  KEY `fk_property` (`property_id`)
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `images`
--

INSERT INTO `images` (`image_id`, `property_id`, `image_url`, `caption`, `uploaded_at`) VALUES
(1, 1, 'http://example.com/images/house1_exterior.jpg', 'Exterior view of the house in Tumba', '2025-10-22 10:14:25'),
(2, 1, 'http://example.com/images/house1_kitchen.jpg', 'Modern kitchen in the house in Tumba', '2025-10-22 10:14:25'),
(3, 2, 'http://example.com/images/apt2_livingroom.jpg', 'Living room in the Nyarugenge apartment', '2025-10-22 10:14:25'),
(4, 2, 'http://example.com/images/apt2_bedroom.jpg', 'Master bedroom in the Nyarugenge apartment', '2025-10-22 10:14:25'),
(5, 3, 'http://example.com/images/villa3_pool.jpg', 'Swimming pool at the villa in Remera', '2025-10-22 10:14:25'),
(6, 4, 'http://example.com/images/condo4_balcony.jpg', 'Balcony view from the Gisozi condominium', '2025-10-22 10:14:25'),
(7, 5, 'http://example.com/images/house5_garden.jpg', 'Garden behind the house in Kicukiro', '2025-10-22 10:14:25'),
(8, 6, 'http://example.com/images/house6_front.jpg', 'Front view of the Mukura house', '2025-10-22 10:14:25'),
(9, 7, 'http://example.com/images/apt7_view.jpg', 'View from the Rubavu apartment', '2025-10-22 10:14:25'),
(10, 8, 'http://example.com/images/house8_interior.jpg', 'Interior view of the Muhanga house', '2025-10-22 10:14:25'),
(11, 9, 'images/property9_front.jpg', 'Front view of property 9', '2025-10-22 10:31:50'),
(12, 9, 'images/property9_living.jpg', 'Living room of property 9', '2025-10-22 10:31:50'),
(13, 9, 'images/property9_kitchen.jpg', 'Kitchen of property 9', '2025-10-22 10:31:50'),
(14, 10, 'images/property10_front.jpg', 'Front view of property 10', '2025-10-22 10:31:50'),
(15, 10, 'images/property10_bedroom.jpg', 'Bedroom of property 10', '2025-10-22 10:31:50'),
(16, 10, 'images/property10_garden.jpg', 'Garden of property 10', '2025-10-22 10:31:50'),
(17, 20, 'C:\\Users\\user\\OneDrive\\Pictures\\Screenshots\\beniii.png', '', '2025-11-03 07:46:51');

-- --------------------------------------------------------

--
-- Table structure for table `locations`
--

DROP TABLE IF EXISTS `locations`;
CREATE TABLE IF NOT EXISTS `locations` (
  `location_id` int NOT NULL AUTO_INCREMENT,
  `Sector` varchar(20) DEFAULT NULL,
  `District` varchar(20) DEFAULT NULL,
  `City_Or_Province` varchar(100) NOT NULL,
  `country` varchar(100) NOT NULL,
  PRIMARY KEY (`location_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `locations`
--

INSERT INTO `locations` (`location_id`, `Sector`, `District`, `City_Or_Province`, `country`) VALUES
(1, 'Tumba', 'Huye', 'Southern Province', 'Rwanda'),
(2, 'Nyarugenge', 'Nyarugenge', 'Kigali', 'Rwanda'),
(3, 'Remera', 'Gasabo', 'Kigali', 'Rwanda'),
(4, 'Gisozi', 'Gasabo', 'Kigali', 'Rwanda'),
(5, 'Kicukiro', 'Kicukiro', 'Kigali', 'Rwanda'),
(6, 'Mukura', 'Huye', 'Southern Province', 'Rwanda'),
(7, 'Rubavu', 'Rubavu', 'Western Province', 'Rwanda'),
(8, 'Muhanga', 'Muhanga', 'Southern Province', 'Rwanda'),
(9, 'Musanze', 'Musanze', 'Northern Province', 'Rwanda'),
(10, 'Gatsibo', 'Gatsibo', 'Eastern Province', 'Rwanda');

-- --------------------------------------------------------

--
-- Table structure for table `properties`
--

DROP TABLE IF EXISTS `properties`;
CREATE TABLE IF NOT EXISTS `properties` (
  `property_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `location_id` int DEFAULT NULL,
  `bedrooms` int DEFAULT NULL,
  `bathrooms` int DEFAULT NULL,
  `property_type` varchar(50) DEFAULT NULL,
  `listing_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`property_id`),
  KEY `user_id` (`user_id`),
  KEY `location_id` (`location_id`)
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `properties`
--

INSERT INTO `properties` (`property_id`, `user_id`, `location_id`, `bedrooms`, `bathrooms`, `property_type`, `listing_date`) VALUES
(1, 1, 1, 3, 2, 'House', '2025-10-22 09:40:48'),
(2, 2, 2, 2, 1, 'Apartment', '2025-10-22 09:40:48'),
(3, 3, 3, 4, 3, 'Villa', '2025-10-22 09:40:48'),
(4, 4, 4, 2, 2, 'Condominium', '2025-10-22 09:40:48'),
(5, 6, 6, 3, 2, 'House', '2025-10-22 09:40:48'),
(6, 7, 7, 3, 2, 'Apartment', '2025-10-22 09:40:48'),
(7, 8, 8, 4, 3, 'House', '2025-10-22 09:40:48'),
(8, 9, 9, 3, 1, 'Apartment', '2025-10-22 09:40:48'),
(9, 10, 10, 4, 2, 'House', '2025-10-22 09:40:48'),
(10, 5, 5, 3, 2, 'House', '2025-10-22 09:40:52'),
(20, 11, 2, 4, 2, 'Residence House', '2025-11-03 07:40:25'),
(19, 11, 1, 4, 1, 'Aparts', '2025-11-03 07:36:53');

-- --------------------------------------------------------

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
CREATE TABLE IF NOT EXISTS `reviews` (
  `review_id` int NOT NULL AUTO_INCREMENT,
  `property_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `rating` int DEFAULT NULL,
  `comment` text,
  `review_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`review_id`),
  KEY `property_id` (`property_id`),
  KEY `user_id` (`user_id`)
) ;

--
-- Dumping data for table `reviews`
--

INSERT INTO `reviews` (`review_id`, `property_id`, `user_id`, `rating`, `comment`, `review_date`) VALUES
(1, 1, 2, 5, 'Great house! Very spacious and well-maintained. John Doe was a pleasure to work with.', '2025-10-22 10:24:45'),
(2, 2, 3, 4, 'Nice apartment with a good view. The location in Nyarugenge is very convenient.', '2025-10-22 10:24:45'),
(3, 3, 4, 5, 'The villa is stunning. The pool area is a great feature, and Mike Jones was very helpful throughout the process.', '2025-10-22 10:24:45'),
(4, 4, 5, 3, 'The condo was decent, but a little small for the price. The Gisozi area is very lively.', '2025-10-22 10:24:45'),
(5, 5, 6, 5, 'Loved the garden area! The house was in excellent condition. Peter Brown was a professional and courteous seller.', '2025-10-22 10:24:45'),
(6, 6, 7, 4, 'Solid property. Everything was as described, and Linda Davis was very responsive.', '2025-10-22 10:24:45'),
(7, 7, 8, 4, 'Good value for the apartment. The view from the balcony is a major plus. Mark Wilson handled the sale efficiently.', '2025-10-22 10:24:45'),
(8, 8, 9, 5, 'Beautiful house, and the transaction with Mary Taylor went very smoothly.', '2025-10-22 10:24:45'),
(9, 9, 10, 3, 'The Musanze apartment was fine, but had some minor maintenance issues. David Clark could have been more communicative.', '2025-10-22 10:24:45'),
(10, 10, 1, 5, 'Fantastic property and a fair price. Lisa White was an outstanding seller.', '2025-10-22 10:24:45');

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
CREATE TABLE IF NOT EXISTS `transactions` (
  `transaction_id` int NOT NULL AUTO_INCREMENT,
  `property_id` int DEFAULT NULL,
  `buyer_id` int DEFAULT NULL,
  `seller_id` int DEFAULT NULL,
  `transaction_date` date NOT NULL,
  `sale_price` int NOT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `property_id` (`property_id`),
  KEY `buyer_id` (`buyer_id`),
  KEY `seller_id` (`seller_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`transaction_id`, `property_id`, `buyer_id`, `seller_id`, `transaction_date`, `sale_price`) VALUES
(1, 1, 2, 1, '2025-09-15', 18000000),
(2, 2, 3, 2, '2025-09-20', 15000000),
(3, 3, 4, 3, '2025-09-22', 45000000),
(4, 4, 5, 4, '2025-09-25', 22000000),
(5, 5, 6, 5, '2025-09-28', 35000000),
(6, 6, 7, 6, '2025-10-01', 19500000),
(7, 7, 8, 7, '2025-10-05', 16000000),
(8, 8, 9, 8, '2025-10-10', 30000000),
(9, 9, 10, 9, '2025-10-12', 17000000),
(10, 10, 1, 10, '2025-10-15', 28000000);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `email` varchar(30) NOT NULL,
  `password` varchar(20) NOT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `role` enum('admin','agent','customer') DEFAULT 'customer',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `email`, `password`, `full_name`, `phone_number`, `created_at`, `role`) VALUES
(1, 'Shema ', 'shema12@gmail.com', 'pass1234', 'Umukunzi Derrick', '0788123456', '2025-10-22 09:24:26', 'customer'),
(2, 'Beyiii', 'jane@gmail.com', 'pass1234', 'Sezerano Briella', '0788234567', '2025-10-22 09:24:26', 'customer'),
(3, 'Taylor', 'manzi@gmail.com', 'pass1234', 'Nziza Steven', '0788345678', '2025-10-22 09:24:26', 'agent'),
(4, 'Emma', 'emma@gmail.com', 'pass1234', 'Rwibutso Emma', '0788456789', '2025-10-22 09:24:26', 'agent'),
(5, 'Giant', 'olivier@gmail.com', '12121', 'Olivier Habii', '0788567890', '2025-10-22 09:24:26', 'customer'),
(6, 'Ziggy', 'ihirwitanze@gmail.com', 'pass1234', 'Ihirwitanze Gentille', '0788678901', '2025-10-22 09:24:26', 'customer'),
(7, 'Miggy', 'izere@gmail.com', 'pass1234', 'Izere Uwase Sandrine', '0788789012', '2025-10-22 09:24:26', 'agent'),
(8, 'Flora', 'iradukunda@gmail.com', 'pass1234', 'Iradukunda Aimee', '0788890123', '2025-10-22 09:24:26', 'customer'),
(9, 'Genevieve', 'ihimbazwegenevieve123@gmail.co', '12345', 'Ihimbazwe Genevieve', '0788901234', '2025-10-22 09:24:26', 'admin'),
(10, 'Domiiiiiias', 'abayo@gmail.com.com', 'pass1234', 'Abayo Dominique', '0789012345', '2025-10-22 09:24:26', 'agent'),
(11, 'Royaume', 'imanzi@gmail.com', 'Nise1234', 'Nise Imanzi', '0792022386', '2025-10-25 08:33:27', 'agent'),
(14, 'Cedro', 'isingizwe@gmail.com', 'cedro1234', 'Isingizwe Cedrick', '0781038780', '2025-11-07 08:31:28', 'customer');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

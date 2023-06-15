DROP DATABASE IF EXISTS bitespeed;
CREATE DATABASE bitespeed;

USE bitespeed;

DROP TABLE IF EXISTS `contact`;
CREATE TABLE `contact` (
                           `id` INT unsigned NOT NULL AUTO_INCREMENT,
                           `phone_number` varchar(10) ,
                           `email` varchar(100) ,
                           `linked_id` INT unsigned,
                           `link_precedence`  ENUM('primary', 'secondary') NOT NULL,
                           `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           `deleted_at` timestamp DEFAULT NULL,
                           PRIMARY KEY (`id`)
);

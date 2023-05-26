-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1:3306
-- Thời gian đã tạo: Th5 21, 2023 lúc 03:25 AM
-- Phiên bản máy phục vụ: 8.0.31
-- Phiên bản PHP: 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `docshare`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `category`
--

DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category` (
  `category_id` bigint NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `category`
--

INSERT INTO `category` (`category_id`, `category_name`) VALUES
(1, 'cntt'),
(2, 'Luận Văn - Báo Cáo'),
(3, 'Kỹ Năng Mềm'),
(4, 'Mẫu Slide Kinh Doanh - Tiếp Thị'),
(5, 'Kinh Tế - Quản Lý'),
(6, 'Tài Chính - Ngân Hàng Biểu Mẫu - Văn Bản'),
(7, 'Giáo Dục - Đào Tạo'),
(8, 'Giáo án - Bài giảng'),
(9, 'Công Nghệ Thông Tin'),
(10, 'Kỹ Thuật - Công Nghệ'),
(11, 'Ngoại Ngữ'),
(12, 'Khoa Học Tự Nhiên'),
(13, 'Y Tế - Sức Khỏe'),
(14, 'Văn Hóa - Nghệ Thuật'),
(15, 'Nông - Lâm - Ngư'),
(16, 'Thể loại khác');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `file`
--

DROP TABLE IF EXISTS `file`;
CREATE TABLE IF NOT EXISTS `file` (
  `file_id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `file_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `file_size` bigint DEFAULT NULL,
  `file_type` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `link` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `upload_date` datetime DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`file_id`),
  KEY `FKliehgj4h8qwdp6iy221v5p6ss` (`category_id`),
  KEY `FKe70ql3orpo0ghvfmqccv27ng` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `file`
--

INSERT INTO `file` (`file_id`, `description`, `file_name`, `file_size`, `file_type`, `link`, `modify_date`, `upload_date`, `category_id`, `user_id`) VALUES
(1, 'HTV', 'DSSV_D19-TH01.pdf', 111539, 'application/pdf', NULL, NULL, '2023-05-08 21:51:08', 1, 1),
(2, 'HTV', 'DSSV_D19-TH01.pdf', 111539, 'application/pdf', NULL, NULL, '2023-05-08 21:51:22', NULL, 1),
(3, 'LuanVan', 'ThucTap.docx', 746, 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', NULL, NULL, '2023-05-08 21:58:09', NULL, 1),
(4, 'HTV', 'DSSV_D19-TH01.pdf', 108, 'application/pdf', NULL, NULL, '2023-05-08 21:59:12', NULL, 1),
(5, 'LuanVan', 'Hiện thực.docx', 3131, 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', NULL, NULL, '2023-05-08 22:00:52', NULL, 1),
(6, 'Duong Ngoc Nguyen', 'ThucTap.docx', 746, 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', NULL, NULL, '2023-05-08 22:21:21', NULL, 1),
(7, 'Huỳnh Thanh Vỉ', 'ThucTap.docx', 746, 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', NULL, NULL, '2023-05-09 20:40:13', NULL, 7),
(8, 'Huỳnh Thanh Vỉ', 'ThucTap.docx', 746, 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', NULL, NULL, '2023-05-09 20:42:15', NULL, 6),
(9, 'LuanVan', 'ThucTap.docx', 746, 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', NULL, NULL, '2023-05-09 20:52:07', NULL, 7),
(10, 'Huỳnh Thanh Vỉ', 'ThucTap.docx', 746, 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', NULL, NULL, '2023-05-09 20:53:07', NULL, 6),
(11, '1234567', 'htv', 122, 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', NULL, NULL, '2023-05-14 12:39:10', 1, 6),
(12, '1234567', 'htv', 122, 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', NULL, NULL, '2023-05-14 12:49:36', 1, 6),
(13, '1234567', 'htv', 122, 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', NULL, NULL, '2023-05-14 12:58:57', 1, 6),
(14, '1234567', 'htv', 122, 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', NULL, NULL, '2023-05-14 12:59:41', 1, 6),
(15, '1234567', 'htv', 122, 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'a', NULL, '2023-05-14 13:37:20', 1, 7),
(16, '1234567', 'htv', 122, 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'https://drive.google.com/file/d/1_qos3UcaEWJnEc1pmkxJcyHMrKZyJ39T', NULL, '2023-05-14 13:42:48', 1, 6),
(17, '1234567', 'htv', 122, 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'https://drive.google.com/file/d/1wrC-pJXub2VlRBJ1PvQjgqZNGD0g1DO_', NULL, '2023-05-14 13:46:06', 1, 6);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `file_tag`
--

DROP TABLE IF EXISTS `file_tag`;
CREATE TABLE IF NOT EXISTS `file_tag` (
  `file_id` bigint NOT NULL,
  `tag_id` bigint NOT NULL,
  PRIMARY KEY (`file_id`,`tag_id`),
  KEY `FK6d156ft7cdh7b90w5qrqnrgkc` (`tag_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `file_tag`
--

INSERT INTO `file_tag` (`file_id`, `tag_id`) VALUES
(1, 7),
(1, 8),
(2, 7),
(2, 8);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `id` bigint NOT NULL,
  `name` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nb4h0p6txrmfc0xbrd1kglp9t` (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'USER'),
(2, 'PM'),
(3, 'ADMIN');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tag`
--

DROP TABLE IF EXISTS `tag`;
CREATE TABLE IF NOT EXISTS `tag` (
  `tag_id` bigint NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`tag_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tag`
--

INSERT INTO `tag` (`tag_id`, `tag_name`) VALUES
(10, 'ab'),
(9, 'c'),
(8, 'b'),
(7, 'a');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `avatar` longtext COLLATE utf8mb4_general_ci,
  `email` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `password` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `username` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `verification_code` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `avatar`, `email`, `enabled`, `name`, `password`, `username`, `verification_code`) VALUES
(1, NULL, 'thanhvikk25@gmail.com', b'1', 'Nguyen Van A', '$2a$10$HLQhQvToL4vyWFyCbfwrceY9NP1l/Emx6RW0UDMhrSkSHBg9ujZ.m', 'Duong Ngoc Nguyen', NULL),
(7, NULL, 'dh51900990@student.stu.edu.vn', b'1', 'Huỳnh Thanh Vỉ', '$2a$10$REQrT1PmXCABdLqFJqEvW.vZONK7KDZIpRFxG9TzFtdq/LlfXzKxC', 'ThanhVi1', NULL),
(6, NULL, 'htvdongthap@gmail.com', b'1', 'ThanhVi1', '$2a$10$bEEO9HHJ1AvnMqxFDMWXded2gvWKbHskciwbABNoss4wJ7rYhY2o2', 'LuanVan', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_friends`
--

DROP TABLE IF EXISTS `user_friends`;
CREATE TABLE IF NOT EXISTS `user_friends` (
  `user_id` bigint NOT NULL,
  `friend_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`friend_id`),
  KEY `FK11y5boh1e7gh60rdqixyetv3x` (`friend_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `user_friends`
--

INSERT INTO `user_friends` (`user_id`, `friend_id`) VALUES
(1, 6),
(6, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_role`
--

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKt7e7djp752sqn6w22i6ocqy6q` (`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `user_role`
--

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
(1, 1),
(2, 3),
(3, 3),
(4, 3),
(5, 3),
(6, 1),
(7, 3);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

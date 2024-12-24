/*
 Navicat Premium Dump SQL

 Source Server         : 数据库测试
 Source Server Type    : MySQL
 Source Server Version : 80039 (8.0.39)
 Source Host           : localhost:3306
 Source Schema         : shop_db

 Target Server Type    : MySQL
 Target Server Version : 80039 (8.0.39)
 File Encoding         : 65001

 Date: 24/12/2024 16:45:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cart_items
-- ----------------------------
DROP TABLE IF EXISTS `cart_items`;
CREATE TABLE `cart_items`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `quantity` int NOT NULL DEFAULT 1,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  INDEX `product_id`(`product_id` ASC) USING BTREE,
  CONSTRAINT `cart_items_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `cart_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart_items
-- ----------------------------
INSERT INTO `cart_items` VALUES (5, 24, 25, 1, '2024-12-23 23:24:52', '2024-12-23 23:24:52');
INSERT INTO `cart_items` VALUES (16, 25, 29, 1, '2024-12-24 15:41:44', '2024-12-24 15:41:44');
INSERT INTO `cart_items` VALUES (17, 25, 30, 1, '2024-12-24 15:52:20', '2024-12-24 15:52:20');
INSERT INTO `cart_items` VALUES (18, 25, 25, 1, '2024-12-24 16:44:41', '2024-12-24 16:44:41');

-- ----------------------------
-- Table structure for categories
-- ----------------------------
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `parent_id` bigint NULL DEFAULT NULL,
  `level` int NULL DEFAULT 1,
  `sort_order` int NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of categories
-- ----------------------------
INSERT INTO `categories` VALUES (1, '手机数码', 0, 1, 10);
INSERT INTO `categories` VALUES (2, '电脑办公', 0, 1, 20);
INSERT INTO `categories` VALUES (3, '服装服饰', 0, 1, 30);
INSERT INTO `categories` VALUES (4, '食品生鲜', 0, 1, 40);
INSERT INTO `categories` VALUES (5, '家居家装', 0, 1, 50);
INSERT INTO `categories` VALUES (11, '手机', 1, 2, 11);
INSERT INTO `categories` VALUES (12, '平板电脑', 1, 2, 12);
INSERT INTO `categories` VALUES (13, '智能设备', 1, 2, 13);
INSERT INTO `categories` VALUES (14, '数码配件', 1, 2, 14);
INSERT INTO `categories` VALUES (21, '笔记本', 2, 2, 21);
INSERT INTO `categories` VALUES (22, '台式机', 2, 2, 22);
INSERT INTO `categories` VALUES (23, '办公设备', 2, 2, 23);
INSERT INTO `categories` VALUES (24, '电脑配件', 2, 2, 24);
INSERT INTO `categories` VALUES (31, '男装', 3, 2, 31);
INSERT INTO `categories` VALUES (32, '女装', 3, 2, 32);
INSERT INTO `categories` VALUES (33, '童装', 3, 2, 33);
INSERT INTO `categories` VALUES (34, '运动服装', 3, 2, 34);
INSERT INTO `categories` VALUES (41, '生鲜水果', 4, 2, 41);
INSERT INTO `categories` VALUES (42, '肉禽蛋品', 4, 2, 42);
INSERT INTO `categories` VALUES (43, '零食饮料', 4, 2, 43);
INSERT INTO `categories` VALUES (44, '粮油调味', 4, 2, 44);
INSERT INTO `categories` VALUES (51, '家具建材', 5, 2, 51);
INSERT INTO `categories` VALUES (52, '家纺布艺', 5, 2, 52);
INSERT INTO `categories` VALUES (53, '家居饰品', 5, 2, 53);
INSERT INTO `categories` VALUES (54, '厨房用品', 5, 2, 54);

-- ----------------------------
-- Table structure for order_items
-- ----------------------------
DROP TABLE IF EXISTS `order_items`;
CREATE TABLE `order_items`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `product_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` decimal(10, 2) NOT NULL,
  `quantity` int NOT NULL,
  `subtotal` decimal(10, 2) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_id`(`order_id` ASC) USING BTREE,
  INDEX `product_id`(`product_id` ASC) USING BTREE,
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_items
-- ----------------------------
INSERT INTO `order_items` VALUES (1, 1, 27, '联想笔记本', 4599.00, 1, 4599.00, '2024-12-23 23:57:04');
INSERT INTO `order_items` VALUES (2, 1, 25, 'iPhone 13', 5999.00, 1, 5999.00, '2024-12-23 23:57:04');
INSERT INTO `order_items` VALUES (3, 1, 26, '华为 Mate 40', 4999.00, 1, 4999.00, '2024-12-23 23:57:04');
INSERT INTO `order_items` VALUES (4, 2, 37, '华为电脑', 6999.00, 3, 20997.00, '2024-12-24 09:59:36');
INSERT INTO `order_items` VALUES (5, 2, 25, 'iPhone 13', 5999.00, 1, 5999.00, '2024-12-24 09:59:36');
INSERT INTO `order_items` VALUES (6, 3, 37, '华为电脑', 6999.00, 1, 6999.00, '2024-12-24 10:11:12');
INSERT INTO `order_items` VALUES (7, 4, 25, 'iPhone 13', 5999.00, 1, 5999.00, '2024-12-24 11:05:52');
INSERT INTO `order_items` VALUES (8, 5, 25, 'iPhone 13', 5999.00, 1, 5999.00, '2024-12-24 15:26:46');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` bigint NOT NULL,
  `shop_id` bigint NULL DEFAULT NULL,
  `total_amount` decimal(10, 2) NOT NULL,
  `status` int NOT NULL DEFAULT 1,
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `receiver_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `pay_time` timestamp NULL DEFAULT NULL,
  `pay_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `pay_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `review` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `order_no`(`order_no` ASC) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  INDEX `shop_id`(`shop_id` ASC) USING BTREE,
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`shop_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, '202412232357046526', 25, 2, 15597.00, 4, '广州', '17875646802', '成', '2024-12-23 23:57:06', 'alipay', 'PAY17349694264586019', '2024-12-23 23:57:04', '2024-12-24 15:26:08', 1);
INSERT INTO `orders` VALUES (2, '202412240959365536', 25, 2, 26996.00, 4, '广州', '17875646802', '陈', '2024-12-24 09:59:39', 'wechat', 'PAY17350055793376076', '2024-12-24 09:59:36', '2024-12-24 15:25:41', 1);
INSERT INTO `orders` VALUES (3, '202412241011122808', 25, 2, 6999.00, 4, 'Qqq', '111', '111', '2024-12-24 10:11:23', 'alipay', 'PAY17350062827902165', '2024-12-24 10:11:12', '2024-12-24 15:16:36', 1);
INSERT INTO `orders` VALUES (4, '202412241105525315', 25, 2, 5999.00, 2, '广州', '17875646802', '111', '2024-12-24 11:05:54', 'alipay', 'PAY17350095538227184', '2024-12-24 11:05:52', '2024-12-24 11:05:53', 0);
INSERT INTO `orders` VALUES (5, '202412241526466331', 25, 2, 5999.00, 2, '广州', '13245678741', '成', '2024-12-24 15:26:48', 'alipay', 'PAY17350252077081583', '2024-12-24 15:26:46', '2024-12-24 15:26:47', 0);

-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shop_id` bigint NOT NULL,
  `category_id` bigint NOT NULL,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `price` decimal(10, 2) NOT NULL,
  `stock` int NOT NULL DEFAULT 0,
  `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `status` int NULL DEFAULT 1 COMMENT '1-上架 0-下架',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `shop_id`(`shop_id` ASC) USING BTREE,
  INDEX `category_id`(`category_id` ASC) USING BTREE,
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`shop_id`) REFERENCES `shops` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `products_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of products
-- ----------------------------
INSERT INTO `products` VALUES (25, 2, 1, 'iPhone 13', '苹果最新款手机', 5999.00, 96, NULL, 1, '2024-12-23 23:21:56', '2024-12-24 15:26:46', '/uploads/products/product_1735004145818.png');
INSERT INTO `products` VALUES (26, 2, 1, '华为 Mate 40', '华为旗舰手机', 4999.00, 49, NULL, 1, '2024-12-23 23:21:56', '2024-12-24 09:35:54', '/uploads/products/product_1735004154464.png');
INSERT INTO `products` VALUES (27, 2, 2, '联想笔记本', '商务办公本', 4599.00, 29, NULL, 1, '2024-12-23 23:21:56', '2024-12-24 09:36:04', '/uploads/products/product_1735004164036.png');
INSERT INTO `products` VALUES (28, 3, 2, '机械键盘', '游戏机械键盘', 299.00, 200, NULL, 1, '2024-12-23 23:21:56', '2024-12-24 09:40:53', '/uploads/products/product_1735004453087.png');
INSERT INTO `products` VALUES (29, 3, 13, '男士T恤', '纯棉舒适', 99.00, 500, NULL, 1, '2024-12-23 23:21:56', '2024-12-24 09:41:04', '/uploads/products/product_1735004464834.png');
INSERT INTO `products` VALUES (30, 3, 13, '牛仔裤', '修身牛仔裤', 199.00, 300, NULL, 1, '2024-12-23 23:21:56', '2024-12-24 09:41:19', '/uploads/products/product_1735004479469.png');
INSERT INTO `products` VALUES (31, 2, 5, '床头柜', '实木床头柜', 399.00, 50, NULL, 1, '2024-12-23 23:21:56', '2024-12-24 16:33:53', '/uploads/products/product_1735004183670.png');
INSERT INTO `products` VALUES (32, 2, 53, '台灯', 'LED护眼台灯', 129.00, 200, NULL, 1, '2024-12-23 23:21:56', '2024-12-24 16:34:07', '/uploads/products/product_1735004190963.png');
INSERT INTO `products` VALUES (33, 3, 44, '有机大米', '东北有机大米', 59.00, 1000, NULL, 1, '2024-12-23 23:21:56', '2024-12-24 16:34:42', '/uploads/products/product_1735004491177.png');
INSERT INTO `products` VALUES (34, 3, 42, '进口牛肉', '澳洲进口牛肉', 199.00, 100, NULL, 1, '2024-12-23 23:21:56', '2024-12-24 16:34:58', '/uploads/products/product_1735004505934.png');
INSERT INTO `products` VALUES (35, 2, 1, '小米手机', '小米最新款', 2999.00, 150, NULL, 1, '2024-12-23 23:21:56', '2024-12-24 09:36:37', '/uploads/products/product_1735004197813.png');
INSERT INTO `products` VALUES (36, 3, 24, '游戏鼠标', '游戏专用鼠标', 199.00, 300, NULL, 1, '2024-12-23 23:21:56', '2024-12-24 16:34:50', '/uploads/products/product_1735004518453.png');
INSERT INTO `products` VALUES (37, 2, 1, '华为电脑', '遥遥领先', 6999.00, 996, NULL, 1, '2024-12-24 08:48:40', '2024-12-24 10:11:12', '/uploads/products/product_1735004137651.png');

-- ----------------------------
-- Table structure for reviews
-- ----------------------------
DROP TABLE IF EXISTS `reviews`;
CREATE TABLE `reviews`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `rating` int NOT NULL COMMENT '1-5星',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `images` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '逗号分隔的图片URL',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `product_id`(`product_id` ASC) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  INDEX `reviews_ibfk_1`(`order_id` ASC) USING BTREE,
  CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `reviews_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `reviews_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reviews
-- ----------------------------
INSERT INTO `reviews` VALUES (1, 3, 37, 25, 5, '哈哈哈', NULL, '2024-12-24 15:16:37');
INSERT INTO `reviews` VALUES (2, 2, 37, 25, 5, '非常好', NULL, '2024-12-24 15:25:42');
INSERT INTO `reviews` VALUES (3, 1, 27, 25, 5, '好好好', NULL, '2024-12-24 15:26:08');

-- ----------------------------
-- Table structure for shops
-- ----------------------------
DROP TABLE IF EXISTS `shops`;
CREATE TABLE `shops`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `shop_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `logo_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `status` int NULL DEFAULT 1 COMMENT '1-正常 0-关闭',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `shops_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shops
-- ----------------------------
INSERT INTO `shops` VALUES (1, 2, '优品商城', '优质商品专卖店', NULL, 1, '2024-12-23 20:54:55', '2024-12-23 20:54:55');
INSERT INTO `shops` VALUES (2, 3, '华为商城', '华为官方商城', NULL, 1, '2024-12-23 23:21:43', '2024-12-23 23:21:43');
INSERT INTO `shops` VALUES (3, 4, '联想商城', '联想官方商城', NULL, 1, '2024-12-23 23:21:43', '2024-12-23 23:21:43');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_type` int NOT NULL COMMENT '1-运营商 2-店铺 3-顾客 4-游客',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `address` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(1) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (2, 'shop1', 'shop123', 'shop1@example.com', 2, '张店长', '13800138000', '北京市朝阳区xxx街xxx号', '2024-12-23 20:54:55', '2024-12-23 21:58:59', 1);
INSERT INTO `users` VALUES (3, 'user3', '123456', 'user3@example.com', 2, '店铺四', '13512457388', '北京市朝阳区', '2024-12-23 23:21:35', '2024-12-24 08:28:12', 1);
INSERT INTO `users` VALUES (4, 'user4', '123456', 'user4@example.com', 2, '店铺三', '13512457388', '北京市朝阳区', '2024-12-23 23:21:35', '2024-12-24 08:27:58', 1);
INSERT INTO `users` VALUES (21, 'admin', 'admin123', 'admin@example.com', 1, '管理员', '13800000000', '北京市朝阳区', '2024-12-23 23:16:57', '2024-12-23 23:16:57', 1);
INSERT INTO `users` VALUES (22, 'shop2', 'shop123', 'shop1@example.com', 2, '店铺一', '13811111111', '上海市浦东新区', '2024-12-23 23:16:57', '2024-12-23 23:16:57', 1);
INSERT INTO `users` VALUES (23, 'shop3', 'shop123', 'shop2@example.com', 2, '店铺二', '13822222222', '广州市天河区', '2024-12-23 23:16:57', '2024-12-23 23:16:57', 1);
INSERT INTO `users` VALUES (24, 'customer1', 'cust123', 'customer1@example.com', 3, '张三', '13833333333', '深圳市南山区', '2024-12-23 23:16:57', '2024-12-23 23:16:57', 1);
INSERT INTO `users` VALUES (25, 'chen', '123', 'customer2@example.com', 3, '李四', '13844444444', '杭州市西湖区', '2024-12-23 23:16:57', '2024-12-23 23:29:46', 1);

SET FOREIGN_KEY_CHECKS = 1;

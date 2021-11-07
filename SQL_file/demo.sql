-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: restaurant
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `charge_discount`
--

DROP TABLE IF EXISTS `charge_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `charge_discount` (
  `id` int NOT NULL DEFAULT '0',
  `service_charge_percentage` double DEFAULT '10',
  `discount_off_percentage` double DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `charge_discount`
--

LOCK TABLES `charge_discount` WRITE;
/*!40000 ALTER TABLE `charge_discount` DISABLE KEYS */;
INSERT INTO `charge_discount` VALUES (0,10,5);
/*!40000 ALTER TABLE `charge_discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_status`
--

DROP TABLE IF EXISTS `customer_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_status` (
  `customer_table` int NOT NULL AUTO_INCREMENT,
  `id_customer` int DEFAULT '-1',
  `ispaid` int DEFAULT '1',
  `id_customer_next` int DEFAULT '-1',
  PRIMARY KEY (`customer_table`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_status`
--

LOCK TABLES `customer_status` WRITE;
/*!40000 ALTER TABLE `customer_status` DISABLE KEYS */;
INSERT INTO `customer_status` VALUES (1,1,1,6),(2,2,0,-1),(3,3,0,-1),(4,4,0,-1),(5,5,0,-1),(6,0,1,-1),(7,0,1,-1),(8,0,1,-1),(9,0,1,-1),(10,0,1,-1);
/*!40000 ALTER TABLE `customer_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `food_list`
--

DROP TABLE IF EXISTS `food_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `food_list` (
  `id_food` int NOT NULL AUTO_INCREMENT,
  `food_type` varchar(200) DEFAULT 'special',
  `food_name` varchar(200) DEFAULT 'name',
  `time_added` varchar(200) DEFAULT '00:00',
  `time_last_modified` varchar(200) DEFAULT '00:00',
  `price` double DEFAULT '9999',
  `egg` double DEFAULT '0',
  `oil_ml` double DEFAULT '0',
  `tomato` double DEFAULT '0',
  `mozzarella_cheese` double DEFAULT '0',
  `salt_g` double DEFAULT '0',
  `black_pepper_g` double DEFAULT '0',
  `aged_rib_eye_steak` double DEFAULT '0',
  `fish_stick` double DEFAULT '0',
  `lemon` double DEFAULT '0',
  `sugar_g` double DEFAULT '0',
  `tea_leave_g` double DEFAULT '0',
  `rice_kg` double DEFAULT '0',
  `cheese_cake_slice` double DEFAULT '0',
  `vanilla_extract_g` double DEFAULT '0',
  `caviar_low_grade_g` double DEFAULT '0',
  `biscuit_g` double DEFAULT '0',
  `caviar_premium_gram` double DEFAULT '0',
  `old_wines_100years_ml` double DEFAULT '0',
  PRIMARY KEY (`id_food`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food_list`
--

LOCK TABLES `food_list` WRITE;
/*!40000 ALTER TABLE `food_list` DISABLE KEYS */;
INSERT INTO `food_list` VALUES (1,'main_course','fried egg','05/11/2021 04.15.21','05/11/2021 04.15.21',10,1,4.5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),(2,'appetizer','Caprese Appetizer','05/11/2021 05.04.44','05/11/2021 05.04.44',5,0,0,1,1,0.5,0.2,0,0,0,0,0,0,0,0,0,0,0,0),(3,'appetizer','oiled mozzarella cheese','05/11/2021 05.15.26','05/11/2021 05.15.26',10,0,2,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0),(4,'main_course','aged rib eye steak','05/11/2021 05.20.08','05/11/2021 05.20.08',350,0,20,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0),(5,'main_course','fried fish stick','05/11/2021 05.22.27','05/11/2021 05.22.27',40,0,70,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0),(6,'beverage','lemon water','05/11/2021 05.27.13','05/11/2021 05.27.13',25,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0),(7,'beverage','lemon tea','05/11/2021 05.29.27','05/11/2021 05.29.27',25,0,0,0,0,0,0,0,0,0.5,7,7,0,0,0,0,0,0,0),(8,'main_course','1 bowl of rice','05/11/2021 05.31.21','05/11/2021 05.31.21',20,0,0,0,0,0,0,0,0,0,0,0,0.1,0,0,0,0,0,0),(9,'dessert','cheese cake slice','05/11/2021 05.32.31','05/11/2021 05.32.31',50,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0),(10,'dessert','Crème brûlée','05/11/2021 05.34.49','05/11/2021 05.34.49',70,2,0,0,0,0,0,0,0,0,50,0,0,0,0.1,0,0,0,0),(11,'others','caviar set:economic grade','05/11/2021 05.37.26','05/11/2021 05.37.56',230,0,0,0,0,0,0,0,0,0,0,0,0,0,0,30,50,0,0),(12,'others','caviar set: premium grade   ','05/11/2021 05.43.17','05/11/2021 05.43.17',3000,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,50,30,0),(13,'special','special service: open wine','05/11/2021 05.44.27','05/11/2021 05.44.27',50,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),(14,'special','special 100 years old wine(50ml)','05/11/2021 05.49.33','05/11/2021 05.49.33',5888,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,50);
/*!40000 ALTER TABLE `food_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingredient_temporary`
--

DROP TABLE IF EXISTS `ingredient_temporary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingredient_temporary` (
  `id_order` int NOT NULL DEFAULT '-99',
  `ismaking` int DEFAULT '0',
  `egg` double DEFAULT '0',
  `oil_ml` double DEFAULT '0',
  `tomato` double DEFAULT '0',
  `mozzarella_cheese` double DEFAULT '0',
  `salt_g` double DEFAULT '0',
  `black_pepper_g` double DEFAULT '0',
  `aged_rib_eye_steak` double DEFAULT '0',
  `fish_stick` double DEFAULT '0',
  `lemon` double DEFAULT '0',
  `sugar_g` double DEFAULT '0',
  `tea_leave_g` double DEFAULT '0',
  `rice_kg` double DEFAULT '0',
  `cheese_cake_slice` double DEFAULT '0',
  `vanilla_extract_g` double DEFAULT '0',
  `caviar_low_grade_g` double DEFAULT '0',
  `biscuit_g` double DEFAULT '0',
  `caviar_premium_gram` double DEFAULT '0',
  `old_wines_100years_ml` double DEFAULT '0',
  PRIMARY KEY (`id_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredient_temporary`
--

LOCK TABLES `ingredient_temporary` WRITE;
/*!40000 ALTER TABLE `ingredient_temporary` DISABLE KEYS */;
INSERT INTO `ingredient_temporary` VALUES (-1,0,58,1166,50,50,50,50,53,50,50,50,50,50,50,50,50,50,50,50),(3,1,0,-20,0,0,0,0,-1,0,0,0,0,0,0,0,0,0,0,0),(4,0,0,-20,0,0,0,0,-1,0,0,0,0,0,0,0,0,0,0,0),(5,0,0,-20,0,0,0,0,-1,0,0,0,0,0,0,0,0,0,0,0),(6,0,0,0,-2,-2,-1,-0.4,0,0,0,0,0,0,0,0,0,0,0,0),(7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-30,-50,0,0),(8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),(9,0,-1,-4.5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),(10,0,0,-70,0,0,0,0,0,-1,0,0,0,0,0,0,0,0,0,0),(11,0,0,0,-1,-1,-0.5,-0.2,0,0,0,0,0,0,0,0,0,0,0,0),(13,0,0,0,0,0,0,0,0,0,-1,0,0,0,0,0,0,0,0,0),(14,0,0,-70,0,0,0,0,0,-1,0,0,0,0,0,0,0,0,0,0),(15,0,0,-2,0,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
/*!40000 ALTER TABLE `ingredient_temporary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingredients_management`
--

DROP TABLE IF EXISTS `ingredients_management`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingredients_management` (
  `id_event` int NOT NULL AUTO_INCREMENT,
  `event` varchar(200) DEFAULT 'no',
  `time_added` varchar(200) DEFAULT '00:00',
  `egg` double DEFAULT '0',
  `oil_ml` double DEFAULT '0',
  `tomato` double DEFAULT '0',
  `mozzarella_cheese` double DEFAULT '0',
  `salt_g` double DEFAULT '0',
  `black_pepper_g` double DEFAULT '0',
  `aged_rib_eye_steak` double DEFAULT '0',
  `fish_stick` double DEFAULT '0',
  `lemon` double DEFAULT '0',
  `sugar_g` double DEFAULT '0',
  `tea_leave_g` double DEFAULT '0',
  `rice_kg` double DEFAULT '0',
  `cheese_cake_slice` double DEFAULT '0',
  `vanilla_extract_g` double DEFAULT '0',
  `caviar_low_grade_g` double DEFAULT '0',
  `biscuit_g` double DEFAULT '0',
  `caviar_premium_gram` double DEFAULT '0',
  `old_wines_100years_ml` double DEFAULT '0',
  PRIMARY KEY (`id_event`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredients_management`
--

LOCK TABLES `ingredients_management` WRITE;
/*!40000 ALTER TABLE `ingredients_management` DISABLE KEYS */;
INSERT INTO `ingredients_management` VALUES (1,'add stock                  ','05/11/2021 04.38.11',10,123,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),(3,'sum Up ingredient used in ingredient_temporary and upgrade it','05/11/2021 04.53.10',-2,-9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),(4,'add stock                     ','05/11/2021 04.54.25',0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),(5,'max 200 character(s)/symbol(s)                      ','06/11/2021 19.06.51',0,1000,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0),(6,'sum Up ingredient used in ingredient_temporary and upgrade it','06/11/2021 19.06.53',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),(7,'fill ingreident all +50','06/11/2021 19.54.14',50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50),(8,'sum Up ingredient used in ingredient_temporary and upgrade it','06/11/2021 19.54.20',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
/*!40000 ALTER TABLE `ingredients_management` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kitchen`
--

DROP TABLE IF EXISTS `kitchen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kitchen` (
  `id_order` int NOT NULL DEFAULT '-11',
  `customer_table` int DEFAULT '-1',
  `food_name` varchar(200) DEFAULT 'xxxxx',
  `amount` int DEFAULT '0',
  `ismaking` int DEFAULT '0',
  `isserved` int DEFAULT '0',
  `iscancel` int DEFAULT '0',
  PRIMARY KEY (`id_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kitchen`
--

LOCK TABLES `kitchen` WRITE;
/*!40000 ALTER TABLE `kitchen` DISABLE KEYS */;
INSERT INTO `kitchen` VALUES (4,2,'aged rib eye steak',1,0,0,0),(5,3,'aged rib eye steak',1,0,0,0),(6,2,'Caprese Appetizer',2,0,0,0),(7,2,'caviar set:economic grade',1,0,0,0),(8,3,'special service: open wine',1,0,0,0),(9,3,'fried egg',1,0,0,0),(10,4,'fried fish stick',1,0,0,0),(11,4,'Caprese Appetizer',1,0,0,0),(13,5,'lemon water',1,0,0,0),(14,5,'fried fish stick',1,0,0,0),(15,5,'oiled mozzarella cheese',1,0,0,0);
/*!40000 ALTER TABLE `kitchen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_record`
--

DROP TABLE IF EXISTS `order_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_record` (
  `id_order` int NOT NULL AUTO_INCREMENT,
  `id_customer` int DEFAULT '-1',
  `customer_table` int DEFAULT '-1',
  `id_food` int DEFAULT '-1',
  `food_type` varchar(200) DEFAULT 'type',
  `food_name` varchar(200) DEFAULT 'name',
  `amount` int DEFAULT '0',
  `summed_price` double DEFAULT '0',
  `time_added` varchar(200) DEFAULT '00:00',
  `time_last_modified` varchar(200) DEFAULT '00:00',
  `ismaking` int DEFAULT '1',
  `isserved` int DEFAULT '1',
  `iscancel` int DEFAULT '1',
  PRIMARY KEY (`id_order`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_record`
--

LOCK TABLES `order_record` WRITE;
/*!40000 ALTER TABLE `order_record` DISABLE KEYS */;
INSERT INTO `order_record` VALUES (1,1,1,1,'main_course','fried egg',1,10,'05/11/2021   04.52.00','05/11/2021   04.52.54',1,1,0),(2,1,1,1,'main_course','fried egg',1,10,'05/11/2021   04.52.00','05/11/2021   04.52.52',1,1,0),(3,1,1,4,'main_course','aged rib eye steak',1,350,'06/11/2021   19.48.15','06/11/2021   22.38.07',1,1,0),(4,2,2,4,'main_course','aged rib eye steak',1,350,'06/11/2021   19.48.17','06/11/2021   19.48.17',0,0,0),(5,3,3,4,'main_course','aged rib eye steak',1,350,'06/11/2021   19.48.18','06/11/2021   19.48.18',0,0,0),(6,2,2,2,'appetizer','Caprese Appetizer',2,10,'06/11/2021   19.58.21','06/11/2021   19.58.21',0,0,0),(7,2,2,11,'others','caviar set:economic grade',1,230,'06/11/2021   19.58.25','06/11/2021   19.58.25',0,0,0),(8,3,3,13,'special','special service: open wine',1,50,'06/11/2021   19.58.30','06/11/2021   19.58.30',0,0,0),(9,3,3,1,'main_course','fried egg',1,10,'06/11/2021   19.58.32','06/11/2021   19.58.32',0,0,0),(10,4,4,5,'main_course','fried fish stick',1,40,'06/11/2021   19.58.37','06/11/2021   19.58.37',0,0,0),(11,4,4,2,'appetizer','Caprese Appetizer',1,5,'06/11/2021   19.58.38','06/11/2021   19.58.38',0,0,0),(12,4,4,9,'dessert','cheese cake slice',1,50,'06/11/2021   19.58.39','06/11/2021   22.00.34',0,0,1),(13,5,5,6,'beverage','lemon water',1,25,'06/11/2021   19.58.41','06/11/2021   19.58.41',0,0,0),(14,5,5,5,'main_course','fried fish stick',1,40,'06/11/2021   19.58.45','06/11/2021   19.58.45',0,0,0),(15,5,5,3,'appetizer','oiled mozzarella cheese',1,10,'06/11/2021   19.58.46','06/11/2021   19.58.46',0,0,0);
/*!40000 ALTER TABLE `order_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_1`
--

DROP TABLE IF EXISTS `table_1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `table_1` (
  `id_order` int NOT NULL DEFAULT '-9',
  `food_name` varchar(200) DEFAULT 'nofood',
  `amount` int DEFAULT '0',
  `summed_price` double DEFAULT '0',
  `ismaking` int DEFAULT '0',
  `isserved` int DEFAULT '0',
  `iscancel` int DEFAULT '0',
  PRIMARY KEY (`id_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_1`
--

LOCK TABLES `table_1` WRITE;
/*!40000 ALTER TABLE `table_1` DISABLE KEYS */;
INSERT INTO `table_1` VALUES (1,'fried egg',1,10,1,1,0),(2,'fried egg',1,10,1,1,0),(3,'aged rib eye steak',1,350,1,1,0);
/*!40000 ALTER TABLE `table_1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_10`
--

DROP TABLE IF EXISTS `table_10`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `table_10` (
  `id_order` int NOT NULL DEFAULT '-9',
  `food_name` varchar(200) DEFAULT 'nofood',
  `amount` int DEFAULT '0',
  `summed_price` double DEFAULT '0',
  `ismaking` int DEFAULT '0',
  `isserved` int DEFAULT '0',
  `iscancel` int DEFAULT '0',
  PRIMARY KEY (`id_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_10`
--

LOCK TABLES `table_10` WRITE;
/*!40000 ALTER TABLE `table_10` DISABLE KEYS */;
/*!40000 ALTER TABLE `table_10` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_2`
--

DROP TABLE IF EXISTS `table_2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `table_2` (
  `id_order` int NOT NULL DEFAULT '-9',
  `food_name` varchar(200) DEFAULT 'nofood',
  `amount` int DEFAULT '0',
  `summed_price` double DEFAULT '0',
  `ismaking` int DEFAULT '0',
  `isserved` int DEFAULT '0',
  `iscancel` int DEFAULT '0',
  PRIMARY KEY (`id_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_2`
--

LOCK TABLES `table_2` WRITE;
/*!40000 ALTER TABLE `table_2` DISABLE KEYS */;
INSERT INTO `table_2` VALUES (4,'aged rib eye steak',1,350,0,0,0),(6,'Caprese Appetizer',2,10,0,0,0),(7,'caviar set:economic grade',1,230,0,0,0);
/*!40000 ALTER TABLE `table_2` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_3`
--

DROP TABLE IF EXISTS `table_3`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `table_3` (
  `id_order` int NOT NULL DEFAULT '-9',
  `food_name` varchar(200) DEFAULT 'nofood',
  `amount` int DEFAULT '0',
  `summed_price` double DEFAULT '0',
  `ismaking` int DEFAULT '0',
  `isserved` int DEFAULT '0',
  `iscancel` int DEFAULT '0',
  PRIMARY KEY (`id_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_3`
--

LOCK TABLES `table_3` WRITE;
/*!40000 ALTER TABLE `table_3` DISABLE KEYS */;
INSERT INTO `table_3` VALUES (5,'aged rib eye steak',1,350,0,0,0),(8,'special service: open wine',1,50,0,0,0),(9,'fried egg',1,10,0,0,0);
/*!40000 ALTER TABLE `table_3` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_4`
--

DROP TABLE IF EXISTS `table_4`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `table_4` (
  `id_order` int NOT NULL DEFAULT '-9',
  `food_name` varchar(200) DEFAULT 'nofood',
  `amount` int DEFAULT '0',
  `summed_price` double DEFAULT '0',
  `ismaking` int DEFAULT '0',
  `isserved` int DEFAULT '0',
  `iscancel` int DEFAULT '0',
  PRIMARY KEY (`id_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_4`
--

LOCK TABLES `table_4` WRITE;
/*!40000 ALTER TABLE `table_4` DISABLE KEYS */;
INSERT INTO `table_4` VALUES (10,'fried fish stick',1,40,0,0,0),(11,'Caprese Appetizer',1,5,0,0,0),(12,'cheese cake slice',1,50,0,0,1);
/*!40000 ALTER TABLE `table_4` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_5`
--

DROP TABLE IF EXISTS `table_5`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `table_5` (
  `id_order` int NOT NULL DEFAULT '-9',
  `food_name` varchar(200) DEFAULT 'nofood',
  `amount` int DEFAULT '0',
  `summed_price` double DEFAULT '0',
  `ismaking` int DEFAULT '0',
  `isserved` int DEFAULT '0',
  `iscancel` int DEFAULT '0',
  PRIMARY KEY (`id_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_5`
--

LOCK TABLES `table_5` WRITE;
/*!40000 ALTER TABLE `table_5` DISABLE KEYS */;
INSERT INTO `table_5` VALUES (13,'lemon water',1,25,0,0,0),(14,'fried fish stick',1,40,0,0,0),(15,'oiled mozzarella cheese',1,10,0,0,0);
/*!40000 ALTER TABLE `table_5` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_6`
--

DROP TABLE IF EXISTS `table_6`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `table_6` (
  `id_order` int NOT NULL DEFAULT '-9',
  `food_name` varchar(200) DEFAULT 'nofood',
  `amount` int DEFAULT '0',
  `summed_price` double DEFAULT '0',
  `ismaking` int DEFAULT '0',
  `isserved` int DEFAULT '0',
  `iscancel` int DEFAULT '0',
  PRIMARY KEY (`id_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_6`
--

LOCK TABLES `table_6` WRITE;
/*!40000 ALTER TABLE `table_6` DISABLE KEYS */;
/*!40000 ALTER TABLE `table_6` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_7`
--

DROP TABLE IF EXISTS `table_7`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `table_7` (
  `id_order` int NOT NULL DEFAULT '-9',
  `food_name` varchar(200) DEFAULT 'nofood',
  `amount` int DEFAULT '0',
  `summed_price` double DEFAULT '0',
  `ismaking` int DEFAULT '0',
  `isserved` int DEFAULT '0',
  `iscancel` int DEFAULT '0',
  PRIMARY KEY (`id_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_7`
--

LOCK TABLES `table_7` WRITE;
/*!40000 ALTER TABLE `table_7` DISABLE KEYS */;
/*!40000 ALTER TABLE `table_7` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_8`
--

DROP TABLE IF EXISTS `table_8`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `table_8` (
  `id_order` int NOT NULL DEFAULT '-9',
  `food_name` varchar(200) DEFAULT 'nofood',
  `amount` int DEFAULT '0',
  `summed_price` double DEFAULT '0',
  `ismaking` int DEFAULT '0',
  `isserved` int DEFAULT '0',
  `iscancel` int DEFAULT '0',
  PRIMARY KEY (`id_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_8`
--

LOCK TABLES `table_8` WRITE;
/*!40000 ALTER TABLE `table_8` DISABLE KEYS */;
/*!40000 ALTER TABLE `table_8` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_9`
--

DROP TABLE IF EXISTS `table_9`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `table_9` (
  `id_order` int NOT NULL DEFAULT '-9',
  `food_name` varchar(200) DEFAULT 'nofood',
  `amount` int DEFAULT '0',
  `summed_price` double DEFAULT '0',
  `ismaking` int DEFAULT '0',
  `isserved` int DEFAULT '0',
  `iscancel` int DEFAULT '0',
  PRIMARY KEY (`id_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_9`
--

LOCK TABLES `table_9` WRITE;
/*!40000 ALTER TABLE `table_9` DISABLE KEYS */;
/*!40000 ALTER TABLE `table_9` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-11-06 23:33:57

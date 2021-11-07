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
INSERT INTO `customer_status` VALUES (1,0,1,1),(2,0,1,-1),(3,0,1,-1),(4,0,1,-1),(5,0,1,-1),(6,0,1,-1),(7,0,1,-1),(8,0,1,-1),(9,0,1,-1),(10,0,1,-1);
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
  PRIMARY KEY (`id_food`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food_list`
--

LOCK TABLES `food_list` WRITE;
/*!40000 ALTER TABLE `food_list` DISABLE KEYS */;
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
  PRIMARY KEY (`id_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredient_temporary`
--

LOCK TABLES `ingredient_temporary` WRITE;
/*!40000 ALTER TABLE `ingredient_temporary` DISABLE KEYS */;
INSERT INTO `ingredient_temporary` VALUES (-1,0);
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
  PRIMARY KEY (`id_event`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredients_management`
--

LOCK TABLES `ingredients_management` WRITE;
/*!40000 ALTER TABLE `ingredients_management` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_record`
--

LOCK TABLES `order_record` WRITE;
/*!40000 ALTER TABLE `order_record` DISABLE KEYS */;
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

-- Dump completed on 2021-11-05  3:30:12

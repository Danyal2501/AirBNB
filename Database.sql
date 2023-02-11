-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: mydb
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking` (
  `bookingID` int NOT NULL AUTO_INCREMENT,
  `userID` int NOT NULL,
  `listingID` int NOT NULL,
  `price` int NOT NULL,
  `startDay` date NOT NULL,
  `endDay` date NOT NULL,
  `currStatus` int NOT NULL,
  PRIMARY KEY (`bookingID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES (1,1,5,50,'2022-04-13','2022-04-20',0),(3,2,1,300,'2003-12-01','2003-12-10',1),(4,2,4,2000,'2020-02-10','2020-02-20',0),(5,2,1,400,'2004-01-01','2004-01-10',1),(6,1,3,1000,'2000-01-03','2000-01-09',1),(7,5,7,3000,'2006-12-13','2006-12-15',0),(8,5,8,1000,'2023-02-20','2023-02-28',1),(9,5,13,99,'2006-02-04','2006-04-02',1),(10,5,17,200,'2007-01-01','2007-01-10',1);
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `calendar`
--

DROP TABLE IF EXISTS `calendar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `calendar` (
  `listingID` int NOT NULL,
  `calendarID` int NOT NULL AUTO_INCREMENT,
  `startDay` date NOT NULL,
  `endDay` date NOT NULL,
  `price` int NOT NULL,
  PRIMARY KEY (`calendarID`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calendar`
--

LOCK TABLES `calendar` WRITE;
/*!40000 ALTER TABLE `calendar` DISABLE KEYS */;
INSERT INTO `calendar` VALUES (1,1,'2003-11-25','2003-12-25',300),(1,2,'2004-01-01','2004-01-24',400),(2,3,'2005-03-03','2005-03-12',300),(2,4,'2009-02-15','2009-02-25',100),(2,5,'2020-03-03','2020-03-20',900),(3,6,'2000-01-01','2000-02-02',1000),(4,7,'2020-01-01','2020-03-04',2000),(5,8,'2022-01-21','2022-03-22',30),(5,9,'2022-04-12','2022-04-28',50),(6,10,'2004-03-03','2007-01-02',3000),(7,11,'2006-12-12','2006-12-20',3000),(8,12,'2023-02-15','2023-04-17',1000),(9,13,'2024-11-11','2024-12-20',400),(9,14,'2025-04-04','2025-06-16',9000),(10,15,'2019-01-20','2019-05-12',3000),(11,16,'2022-09-09','2022-10-14',2200),(11,17,'2023-01-04','2023-03-16',1000),(12,18,'2000-01-01','2000-05-02',4000),(13,19,'2006-01-01','2007-01-01',99),(14,20,'2023-02-02','2023-04-04',340),(15,21,'2022-05-05','2022-06-02',2451),(16,22,'2025-01-01','2025-02-02',520),(17,23,'2007-01-01','2008-01-02',200),(18,24,'2025-01-08','2025-01-19',232);
/*!40000 ALTER TABLE `calendar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hostreiew`
--

DROP TABLE IF EXISTS `hostreiew`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hostreiew` (
  `hostID` int NOT NULL,
  `renter` int NOT NULL,
  `commentWords` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`hostID`,`renter`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hostreiew`
--

LOCK TABLES `hostreiew` WRITE;
/*!40000 ALTER TABLE `hostreiew` DISABLE KEYS */;
/*!40000 ALTER TABLE `hostreiew` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hostreview`
--

DROP TABLE IF EXISTS `hostreview`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hostreview` (
  `hostID` int NOT NULL,
  `renter` int NOT NULL,
  `commentWords` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`hostID`,`renter`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hostreview`
--

LOCK TABLES `hostreview` WRITE;
/*!40000 ALTER TABLE `hostreview` DISABLE KEYS */;
/*!40000 ALTER TABLE `hostreview` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `listing`
--

DROP TABLE IF EXISTS `listing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `listing` (
  `listingID` int NOT NULL AUTO_INCREMENT,
  `userID` int NOT NULL,
  `placeType` int NOT NULL,
  `longitude` double NOT NULL,
  `latitude` double NOT NULL,
  `postalCode` varchar(50) NOT NULL,
  `city` varchar(50) NOT NULL,
  `country` varchar(50) NOT NULL,
  `amenities` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`listingID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listing`
--

LOCK TABLES `listing` WRITE;
/*!40000 ALTER TABLE `listing` DISABLE KEYS */;
INSERT INTO `listing` VALUES (1,1,3,150,-13.331,'M1B4S6','Toronto','Canada','[3, 6, 5]'),(2,1,2,20.232,-53.232,'M2S4l4','Toronto','Canada','[3, 8]'),(3,1,1,50,50,'M1B4G4','Toronto','Canada','[1, 2, 3]'),(4,1,2,40,40,'123123','New York','USA','[9]'),(5,1,2,30.0101,-2.334,'192939','Los Angeles','USA','[1, 2, 6, 8]'),(6,1,1,30,31.11,'M1B4S6','Toronto','Canada','[2, 4, 7, 9]'),(7,3,1,1.1,2.2,'123456','New York','USA','[2, 4]'),(8,3,2,4.4,5.5,'M1G4H6','Scarborough','Canada','[6, 10]'),(9,3,2,45.22,-34.62,'090909','London','England','[4, 7]'),(10,1,3,54.2,34.2,'M1B5J4','Scarborough','Canada','[3, 9]'),(11,1,2,80.223,-48.11,'M1G5J4','Scarborough','Canada','[3, 1]'),(12,1,2,15.223,-2.512,'M1BG4K','Scarborough','Canada','[3, 7]'),(13,4,2,23.23,23.23,'G5SF4G','Scarborough','Canada','[1, 7, 8, 9]'),(14,1,3,64.34,-23.1,'M3D5G6','Scarborough','Canada','[1]'),(15,1,1,24.521,-32.2,'M1B5J7','Scarborough','Canada','[4, 7]'),(16,1,2,23.22,53.1,'M5J4D3','Scarborough','Canada','[3, 4]'),(17,1,2,89.9,-89.9,'L2G5H5','Scarborough','Canada','[9, 2]'),(18,1,3,1.01,2.02,'M4H5H5','Scarborough','Canada','[1]');
/*!40000 ALTER TABLE `listing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `renterreiew`
--

DROP TABLE IF EXISTS `renterreiew`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `renterreiew` (
  `listingID` int NOT NULL,
  `renter` int NOT NULL,
  `commentWords` varchar(250) NOT NULL,
  `hostRating` int NOT NULL,
  `listingRating` int NOT NULL,
  PRIMARY KEY (`listingID`,`renter`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `renterreiew`
--

LOCK TABLES `renterreiew` WRITE;
/*!40000 ALTER TABLE `renterreiew` DISABLE KEYS */;
/*!40000 ALTER TABLE `renterreiew` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `userID` int NOT NULL AUTO_INCREMENT,
  `legalName` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `birthday` date NOT NULL,
  `occupation` varchar(50) NOT NULL,
  `sin` int NOT NULL,
  `paymentInfo` varchar(50) DEFAULT NULL,
  `typeUser` int NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `pass` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'new','new','2001-11-25','occupation',123123,'',2,'new','new'),(2,'dann','address','2000-01-01','none',12121212,'123231',1,'new2','new2'),(3,'host','address','2000-01-01','hostguys',123456,'',2,'host','host'),(4,'host2','address2','2000-01-01','occupation',123123,'',2,'host2','host2'),(5,'renter','renteraddress','2000-04-01','occ',54124,'492919',1,'renter','renter');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-08-08  9:43:57

-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: pharmathome
-- ------------------------------------------------------
-- Server version	8.0.20

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
-- Table structure for table `prescrizione`
--

DROP TABLE IF EXISTS `prescrizione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prescrizione` (
  `Id` int unsigned NOT NULL AUTO_INCREMENT,
  `NRE` int NOT NULL,
  `Id_medico` int unsigned DEFAULT NULL,
  `Id_paziente` int unsigned DEFAULT NULL,
  `Id_farmaco` int unsigned DEFAULT NULL,
  `Data` date NOT NULL,
  `Stato` varchar(15) NOT NULL DEFAULT 'Non Evasa',
  PRIMARY KEY (`Id`),
  KEY `ID_medico` (`Id_medico`),
  KEY `ID_paziente` (`Id_paziente`),
  KEY `ID_farmaco` (`Id_farmaco`),
  CONSTRAINT `prescrizione_ibfk_1` FOREIGN KEY (`Id_medico`) REFERENCES `utente` (`Id`) ON UPDATE CASCADE,
  CONSTRAINT `prescrizione_ibfk_2` FOREIGN KEY (`Id_paziente`) REFERENCES `utente` (`Id`) ON UPDATE CASCADE,
  CONSTRAINT `prescrizione_ibfk_3` FOREIGN KEY (`Id_farmaco`) REFERENCES `farmaco` (`Id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prescrizione`
--

LOCK TABLES `prescrizione` WRITE;
/*!40000 ALTER TABLE `prescrizione` DISABLE KEYS */;
/*!40000 ALTER TABLE `prescrizione` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-04 18:21:27

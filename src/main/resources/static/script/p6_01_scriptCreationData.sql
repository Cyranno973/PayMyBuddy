CREATE DATABASE  IF NOT EXISTS `paymybuddydatabase` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `paymybuddydatabase`;
-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: paymybuddydatabase
-- ------------------------------------------------------
-- Server version	8.0.21

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
-- Table structure for table `bank_account`
--

DROP TABLE IF EXISTS `bank_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bank_account` (
  `iban` varchar(34) NOT NULL,
  `bic` varchar(50) NOT NULL,
  `bankName` varchar(50) NOT NULL,
  `accountName` varchar(50) NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`iban`),
  KEY `fk_bank_account_user1_idx` (`user_id`),
  CONSTRAINT `fk_bank_account_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bank_account`
--

LOCK TABLES `bank_account` WRITE;
/*!40000 ALTER TABLE `bank_account` DISABLE KEYS */;
INSERT INTO `bank_account` VALUES ('IBANBUSH','BICBUSH','AXA','compte courant busha',2),('ibanJoseh','BICFally','LaPoste','compteEpargneJoseph',3),('IBANOBAMA','BICOBAMA','SOCIETE GENERALE','compte courant obama',1);
/*!40000 ALTER TABLE `bank_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `external_transfer`
--

DROP TABLE IF EXISTS `external_transfer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `external_transfer` (
  `transfer_id` int NOT NULL,
  `fees` decimal(9,2) NOT NULL,
  `bank_account_iban` varchar(34) NOT NULL,
  PRIMARY KEY (`transfer_id`),
  KEY `fk_external_transfer_bank_account1_idx` (`bank_account_iban`),
  CONSTRAINT `fk_external_transfer_bank_account1` FOREIGN KEY (`bank_account_iban`) REFERENCES `bank_account` (`iban`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_external_transfer_transfer1` FOREIGN KEY (`transfer_id`) REFERENCES `transfer` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `external_transfer`
--

LOCK TABLES `external_transfer` WRITE;
/*!40000 ALTER TABLE `external_transfer` DISABLE KEYS */;
INSERT INTO `external_transfer` VALUES (1,50.00,'IBANOBAMA'),(3,20.00,'ibanJoseh');
/*!40000 ALTER TABLE `external_transfer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `internal_transfer`
--

DROP TABLE IF EXISTS `internal_transfer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `internal_transfer` (
  `transfer_id` int NOT NULL,
  `sender` int NOT NULL,
  `receiver` int NOT NULL,
  PRIMARY KEY (`transfer_id`),
  KEY `fk_internal_transfer_user1_idx` (`sender`),
  KEY `fk_internal_transfer_user2_idx` (`receiver`),
  CONSTRAINT `fk_internal_transfer_tranfer1` FOREIGN KEY (`transfer_id`) REFERENCES `transfer` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_internal_transfer_user1` FOREIGN KEY (`sender`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_internal_transfer_user2` FOREIGN KEY (`receiver`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `internal_transfer`
--

LOCK TABLES `internal_transfer` WRITE;
/*!40000 ALTER TABLE `internal_transfer` DISABLE KEYS */;
INSERT INTO `internal_transfer` VALUES (2,1,2),(3,2,1),(4,1,2),(5,1,2),(6,1,2),(7,2,1),(8,2,1),(9,1,2),(10,1,2),(11,2,1),(12,1,2),(13,1,2),(14,1,2),(15,1,2),(16,1,2),(17,2,1),(18,2,1);
/*!40000 ALTER TABLE `internal_transfer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relation`
--

DROP TABLE IF EXISTS `relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `relation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `owner` int NOT NULL,
  `buddy` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKsuu8kov8jb0ylg1ol05tixp4b` (`owner`,`buddy`),
  KEY `fk_relation_user1_idx` (`owner`),
  KEY `fk_relation_user2_idx` (`buddy`),
  CONSTRAINT `fk_relation_user1` FOREIGN KEY (`owner`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_relation_user2` FOREIGN KEY (`buddy`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relation`
--

LOCK TABLES `relation` WRITE;
/*!40000 ALTER TABLE `relation` DISABLE KEYS */;
INSERT INTO `relation` VALUES (1,1,2),(3,1,3),(2,2,1);
/*!40000 ALTER TABLE `relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'USER'),(2,'ADMIN');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transfer`
--

DROP TABLE IF EXISTS `transfer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transfer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `amount` decimal(9,2) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `transactionDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transfer`
--

LOCK TABLES `transfer` WRITE;
/*!40000 ALTER TABLE `transfer` DISABLE KEYS */;
INSERT INTO `transfer` VALUES (1,1000.00,'versemnt pour charge payMyBuddy','2021-03-04 22:27:21','COMPLETED'),(2,500.00,'VERSETMENT RESTO BUSH','2021-03-04 22:33:33','COMPLETED'),(3,20.00,'loyer','2021-03-17 21:36:26','COMPLETED'),(4,100.00,'dette','2021-04-20 09:54:55','Completed'),(5,200.00,'dette','2021-04-20 10:05:27','Completed'),(6,200.00,'dette','2021-04-20 10:05:38','Completed'),(7,200.00,'dette','2021-04-20 10:11:07','Completed'),(8,200.00,'dette','2021-04-20 10:11:55','Completed'),(9,200.00,'dette','2021-04-20 10:12:09','Completed'),(10,200.00,'dette','2021-04-20 10:12:15','Completed'),(11,200.00,'dette','2021-04-20 10:16:28','Completed'),(12,200.00,'dette','2021-04-20 10:16:45','Completed'),(13,-200.00,'dette','2021-04-20 10:17:26','Completed'),(14,0.50,'dette','2021-04-20 10:17:49','Completed'),(15,0.50,'dette','2021-04-20 10:17:58','Completed'),(16,1.00,'dette','2021-04-20 10:18:08','Completed'),(17,1.00,'dette','2021-04-20 10:19:16','Completed'),(18,400.00,'dette','2021-04-20 10:21:35','Completed');
/*!40000 ALTER TABLE `transfer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `balance` decimal(9,2) NOT NULL DEFAULT '0.00',
  `createDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'barack','obama','obama@president.com','$2a$12$cXHSuj3Oi9kLw4.GE3DjgO.Wiya3jspRFgDz91KUFqIMMeA4ZLGoy',649.00,'2021-03-04 22:15:22'),(2,'george','bush','bush@president.com','123',301.00,'2021-03-04 22:18:40'),(3,'john','Soe','doe@hotmail.com','12345',800.00,'2021-03-16 22:44:02'),(5,'johnathan','Soe','jonathan@hotmail.com','12345',800.00,'2021-03-20 10:13:10'),(7,'Claude','Fracn','Claude@president.com','aaaaa',0.00,'2021-04-01 20:31:34'),(8,'Czzzlaude','Fracn','Clzzzude@president.com','aaaaa',0.00,'2021-04-01 20:33:44'),(10,'Czz==zlaude','Fracn','Clzzze@president.com','aaaaa',0.00,'2021-04-01 20:40:28'),(11,'asss','Fracn','ass@president.com','$2a$12$5IdZBu879/hIL95ZqpWhgu.is7CyeGxKTg2Adq3SaDTa/wvtO146G',0.00,'2021-04-01 22:01:31'),(12,'asss','Fracn','ss@president.com','$2a$12$e1P/UqWZbEK8ciwXzpAVYOLODQbZDafSbwOEzzd1EaPqepG86v8ba',0.00,'2021-04-02 07:24:03'),(13,'pierre','dumon','dumon@gmail.com','$2a$12$W2QZI8k90dvlswjD8xA/p.UHO6cWUmMREUCVlr7BH7mkvdAzXLoK2',0.00,'2021-04-02 07:46:54'),(14,'pierre','dumon','dumon1@gmail.com','$2a$12$MGMJ2SqvMCIdDjvHPyVz3.hleSSA8lB7GkqMZEE2bRXg0vS9gtUX.',0.00,'2021-04-02 07:49:59'),(15,'pierre','dumon','dumon3@gmail.com','$2a$12$/pjA/Kbq46AcPuUDIGivYOJ4rDz57feOxsBRDhFMrrG8aplWbnJV2',0.00,'2021-04-02 08:20:16'),(16,'pierGG','dumoNN','dumon4@gmail.com','$2a$12$kzmKKHnYeNd/i9knqY5vIOjmih/09V.vxrXns.0Dup6bjbs4PwuGW',0.00,'2021-04-02 10:25:12'),(18,'dalila','claude','dumon6@gmail.com','$2a$12$nTztAaDSxiZTHeBmGah3ueMS0IajB5MpQpv9JzE3cIOFFMtwIHM.W',0.00,'2021-04-03 21:31:44'),(19,'STEVE','GOUIN','aaaa@gmail.com','$2a$12$ENhJF7CFqEoOwA6FlUdS3evt/M7rlqMsBRaQQmn5dmEfyRNGhq.ee',0.00,'2021-05-01 15:54:18'),(20,'STEVEaaa','GOUIN','aaaaa','$2a$12$rt6op7CXRVyElMiccrdafeYmhjN3IAGbIamdvd2oQbhsztKiG/4rS',0.00,'2021-05-01 17:00:26'),(21,'STEVE','GOUIN','victoire@gmail.com','$2a$12$tX2bMf2yM9MphcoCjn5TVexuUI7mwBMAbnpg0vTlrWYahSbnTMZne',0.00,'2021-05-01 17:15:20'),(22,'Cynthia','MARIE-SAINTE','dz','$2a$12$LB7yoU2bh512n58QX8.7Oeriz6Mq8qRTGHV4GhUkfiCn1HhluPsPO',0.00,'2021-05-01 17:16:42'),(24,'aaaa','aaaa','bbbb@gmail.com','$2a$12$7ZMqbR28MClB1WUfC87oh.VNCw54HfqK8MaWwtiU90ve2b6d9.daS',0.00,'2021-05-01 19:15:56'),(25,'aaaa','aaaa','cccc@gmail.com','$2a$12$SlMgFj4r3KRkMx33rAnvtO7cFi0WHaZj4csfAmq.AC1cuDeN.PhtS',0.00,'2021-05-01 19:16:57'),(26,'cccc','cccc','cccd@gmail.com','$2a$12$BcxQqu3BCoA/mmkgKM8r0uYIc7o4ImGkzmrKfwWmCTqz4VeFIZK0K',0.00,'2021-05-01 19:19:17'),(27,'eeee','eeee','eeee@gmail.com','$2a$12$cXHSuj3Oi9kLw4.GE3DjgO.Wiya3jspRFgDz91KUFqIMMeA4ZLGoy',0.00,'2021-05-01 19:56:00'),(28,'sasa','sasas','rheny@msn.com','$2a$12$YeH3Y6YI4zhGuZZVBXBlHeYKhY7/lxsQfoZgRun3Eg.p5fpnQIHQy',0.00,'2021-05-01 20:37:07'),(29,'rrrr','rrrr','7msjci@gmail.com','$2a$12$jwJ5J8saL3wqU5eXQXJceOFNcHdYBMGaDJZsWk4e/Kb7eniyta5IK',0.00,'2021-05-01 21:14:44');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_roles` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  KEY `fk_users_roles_user1_idx` (`user_id`),
  KEY `fk_users_roles_role1_idx` (`role_id`),
  CONSTRAINT `fk_users_roles_role1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_users_roles_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (19,1),(20,1),(21,1),(22,1),(24,1),(25,1),(26,1),(27,1),(28,1),(29,1);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-02 15:55:50

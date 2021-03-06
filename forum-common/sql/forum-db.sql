CREATE DATABASE  IF NOT EXISTS `step_forum_db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `step_forum_db`;
-- MySQL dump 10.13  Distrib 8.0.11, for Win64 (x86_64)
--
-- Host: localhost    Database: step_forum_db
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `action`
--

DROP TABLE IF EXISTS `action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `action` (
  `id_action` int(11) NOT NULL AUTO_INCREMENT,
  `action_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_action`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `action`
--

LOCK TABLES `action` WRITE;
/*!40000 ALTER TABLE `action` DISABLE KEYS */;
INSERT INTO `action` VALUES (1,'addComment'),(2,'getComments'),(3,'newTopic'),(4,'topic'),(5,'register'),(6,'login'),(7,'logout'),(8,'addTopic'),(9,'getPopularTopics'),(10,'getAllActiveTopics'),(11,'getSimilarTopics'),(12,'doRegister'),(13,'doLogin'),(14,'activate');
/*!40000 ALTER TABLE `action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `comment` (
  `id_comment` int(11) NOT NULL AUTO_INCREMENT,
  `description` text,
  `write_date` datetime DEFAULT NULL,
  `id_topic` int(11) DEFAULT NULL,
  `id_user` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_comment`),
  KEY `fk_comment_user_idx` (`id_user`),
  KEY `fk_comment_topic_idx` (`id_topic`),
  CONSTRAINT `fk_comment_topic` FOREIGN KEY (`id_topic`) REFERENCES `topic` (`id_topic`) ON DELETE CASCADE,
  CONSTRAINT `fk_comment_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,'Typography helps you engage your audience and establish a distinct, unique personality on your website. Knowing how to use fonts to build character in your design is a powerful skill, and exploring the history and use of typefaces, as well as typogra...','2018-10-21 09:00:00',1,5),(2,'Thx for answer. But still got problems. Updated manifest and changed onStart to onStartCommand but does not override now','2018-10-21 09:11:00',1,3),(3,'Thx for answer. ','2018-10-21 10:15:00',1,1),(4,'Due to the difference in the power they are able to draw, phones will always be an order of magnitude slower than desktops. However, don \'t give up on Python for the mobile device, because typically only a fraction of the code is responsible for the processor-intensive work, and this fraction can be optimised by rewriting it in another language.','2018-09-17 16:23:00',3,1),(22,'Okay I\'ll try!','2018-11-09 13:59:46',1,3),(23,'Thanks! I\'ll try..','2018-11-10 22:24:31',3,3),(24,'learn another language','2018-11-10 22:26:26',3,3),(30,'I\'ll try tomorrow..Thanks','2018-12-07 18:59:39',12,28),(31,'It\'s wrong way!','2018-12-07 20:40:59',2,3);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `role` (
  `id_role` int(11) NOT NULL AUTO_INCREMENT,
  `role_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_role`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER'),(3,'ROLE_UNAUTHENTICATED');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_action`
--

DROP TABLE IF EXISTS `role_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `role_action` (
  `id_role_action` int(11) NOT NULL AUTO_INCREMENT,
  `id_role` int(11) DEFAULT NULL,
  `id_action` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_role_action`),
  KEY `fk_ra_role_idx` (`id_role`),
  KEY `fk_ra_action_idx` (`id_action`),
  CONSTRAINT `fk_ra_action` FOREIGN KEY (`id_action`) REFERENCES `action` (`id_action`),
  CONSTRAINT `fk_ra_role` FOREIGN KEY (`id_role`) REFERENCES `role` (`id_role`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_action`
--

LOCK TABLES `role_action` WRITE;
/*!40000 ALTER TABLE `role_action` DISABLE KEYS */;
INSERT INTO `role_action` VALUES (15,2,1),(16,2,2),(17,2,3),(18,2,4),(19,2,5),(20,2,6),(21,2,7),(22,2,8),(23,2,9),(24,2,10),(25,2,11),(26,2,12),(27,2,13),(28,2,14),(29,3,2),(30,3,4),(31,3,5),(32,3,6),(33,3,9),(34,3,12),(35,3,13),(36,3,14);
/*!40000 ALTER TABLE `role_action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topic`
--

DROP TABLE IF EXISTS `topic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `topic` (
  `id_topic` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `description` text,
  `share_date` datetime DEFAULT NULL,
  `view_count` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `id_user` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_topic`),
  KEY `fk_topic_user_idx` (`id_user`),
  CONSTRAINT `fk_topic_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topic`
--

LOCK TABLES `topic` WRITE;
/*!40000 ALTER TABLE `topic` DISABLE KEYS */;
INSERT INTO `topic` VALUES (1,'10 Kids Unaware of Their Halloween Costume','Today, we re looking at three particularly interesting stories. Pinterest added a new location-based feature on Wednesday that uses Place Pins as a way to map out vacations and favorite areas. Southwest Airlines is providing Wi-Fi access from gate to gate for $8 per day through an onboard hotspot. And in an effort to ramp up its user base, Google Wallet is offering a debit card that can take out cash from.','2018-10-21 03:00:00',329,1,1),(2,'josso integration with spring web srevices','I require help on integration of web service with JOSSO for authentication. Whenever a request comes from SOAP UI(client) to service, it should be authenticated against an Active Directory server, and if it succeeds then it should hit the service endpoint.','2018-10-20 08:15:00',336,1,3),(3,'Unusual Sign In notification with Spring Security','Some popular websites send email/push notifications to their users if they notice unusual or suspicious login activity. Examples include Google, Facebook, Mozilla, Booking.com. How to implement this feature in Java/Spring application?','2018-09-15 05:30:00',199,1,4),(12,'How to fix Maven, Maven Configuration and Maven Dependency Problems in Eclipse','I went to directory of my project MainDirectory/MyProject and I run the same command, once again it worked well.I run Eclipse and I wanted to import \"Existing Maven Projects\". I chose MainDirectory as RootDirectory and I got a warning.','2018-11-09 14:01:04',27,1,3);
/*!40000 ALTER TABLE `topic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `token` varchar(100) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `id_role` int(11) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `image` text,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_user_role_idx` (`id_role`),
  CONSTRAINT `fk_user_role` FOREIGN KEY (`id_role`) REFERENCES `role` (`id_role`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'elvin@gmail.com','WM2wmqp/RW55f/Ljs7IYmg==','123456',3,2,'Elvin','Xalafov','default.png'),(3,'senan@gmail.com','WM2wmqp/RW55f/Ljs7IYmg==','123456',1,2,'Senan','Kazimov','default.png'),(4,'fatime@gmail.com','WM2wmqp/RW55f/Ljs7IYmg==','123456',1,2,'Fatime','Gurbanova','default.png'),(5,'vuqar@gmail.com','WM2wmqp/RW55f/Ljs7IYmg==','123456',1,2,'Vuqar','Nesirov','default.png'),(28,'senan0144@gmail.com','WM2wmqp/RW55f/Ljs7IYmg==','911d3b98-1426-4fc0-957a-93d72fa4f9b8',1,1,'Senan','Kazimov','senan0144@gmail.com\\boy.png');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-11 22:33:47

-- MySQL dump 10.13  Distrib 5.7.24, for Linux (x86_64)
--
-- Host: localhost    Database: CYBER_LIFE
-- ------------------------------------------------------
-- Server version	5.7.24-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `HORARIO_LEMBRETE`
--

DROP TABLE IF EXISTS `HORARIO_LEMBRETE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `HORARIO_LEMBRETE` (
  `HL_CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `HL_HORARIO_INICIO` datetime DEFAULT NULL,
  `HL_HORARIO_FIM` datetime DEFAULT NULL,
  `HL_INTERVALO` int(11) DEFAULT NULL,
  `FK_LEMBRETE` int(11) DEFAULT NULL,
  PRIMARY KEY (`HL_CODIGO`),
  KEY `FK_CODIGO_LEMBRETE` (`FK_LEMBRETE`),
  CONSTRAINT `FK_CODIGO_LEMBRETE` FOREIGN KEY (`FK_LEMBRETE`) REFERENCES `LEMBRETE` (`LCOD_LEMBRETE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `HORARIO_LEMBRETE`
--

LOCK TABLES `HORARIO_LEMBRETE` WRITE;
/*!40000 ALTER TABLE `HORARIO_LEMBRETE` DISABLE KEYS */;
/*!40000 ALTER TABLE `HORARIO_LEMBRETE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LEMBRETE`
--

DROP TABLE IF EXISTS `LEMBRETE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LEMBRETE` (
  `LCOD_LEMBRETE` int(11) NOT NULL AUTO_INCREMENT,
  `LEMBRETE` text,
  `LDIA_TODO` bit(1) DEFAULT NULL,
  `L_STATUS` varchar(20) DEFAULT NULL,
  `FK_USUARIO` int(11) NOT NULL,
  PRIMARY KEY (`LCOD_LEMBRETE`),
  KEY `FK_USUARIO` (`FK_USUARIO`),
  CONSTRAINT `FK_USUARIO` FOREIGN KEY (`FK_USUARIO`) REFERENCES `USUARIO` (`UCODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LEMBRETE`
--

LOCK TABLES `LEMBRETE` WRITE;
/*!40000 ALTER TABLE `LEMBRETE` DISABLE KEYS */;
INSERT INTO `LEMBRETE` VALUES (1,NULL,NULL,'ATIVADO',2),(2,'TESTE DE TESTES TESTES',NULL,NULL,3),(3,'TESTE',NULL,NULL,3),(4,'LEMBRE-SE',NULL,'ATIVADO',3),(5,'LEMBRE-SE',_binary '\0','ATIVADO',3),(6,'LEMBRE-SE',_binary '','ATIVADO',3),(7,'OLHA AI',_binary '\0','ATIVADO',3);
/*!40000 ALTER TABLE `LEMBRETE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USUARIO`
--

DROP TABLE IF EXISTS `USUARIO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USUARIO` (
  `UCODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `UEMAIL` varchar(255) NOT NULL,
  `UNOME` varchar(255) DEFAULT NULL,
  `USOBRENOME` varchar(255) DEFAULT NULL,
  `USENHA` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`UCODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USUARIO`
--

LOCK TABLES `USUARIO` WRITE;
/*!40000 ALTER TABLE `USUARIO` DISABLE KEYS */;
INSERT INTO `USUARIO` VALUES (1,'EKEK','KAKA',NULL,'KAKA'),(2,'JEFTER.SANTIAGO66@GMAIL.COM','JEFTER SANTIAGO',NULL,'1234'),(3,'TESTE','TESTE',NULL,NULL);
/*!40000 ALTER TABLE `USUARIO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `VIEW_INICIA_SESSAO`
--

DROP TABLE IF EXISTS `VIEW_INICIA_SESSAO`;
/*!50001 DROP VIEW IF EXISTS `VIEW_INICIA_SESSAO`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `VIEW_INICIA_SESSAO` AS SELECT 
 1 AS `UCODIGO`,
 1 AS `UNOME`,
 1 AS `USOBRENOME`,
 1 AS `UEMAIL`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `VIEW_INICIA_SESSAO`
--

/*!50001 DROP VIEW IF EXISTS `VIEW_INICIA_SESSAO`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `VIEW_INICIA_SESSAO` AS select `USUARIO`.`UCODIGO` AS `UCODIGO`,`USUARIO`.`UNOME` AS `UNOME`,`USUARIO`.`USOBRENOME` AS `USOBRENOME`,`USUARIO`.`UEMAIL` AS `UEMAIL` from `USUARIO` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-07 10:29:10

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
-- Table structure for table `EVENTO`
--

DROP TABLE IF EXISTS `EVENTO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EVENTO` (
  `COD_EVENTO` int(11) NOT NULL AUTO_INCREMENT,
  `TITULO` varchar(100) NOT NULL,
  `DATA_INICIO` datetime NOT NULL,
  `DATA_FIM` datetime NOT NULL,
  `DIA_TODO` bit(1) NOT NULL,
  `LOCAL_EVENTO` varchar(150) DEFAULT NULL,
  `DESCRICAO` varchar(255) DEFAULT NULL,
  `TIPO_REPETICAO` int(11) DEFAULT NULL,
  `TIPO_FIM_REPETICAO` int(11) DEFAULT NULL,
  `ATIVO` bit(1) DEFAULT NULL,
  `FK_USUARIO` int(11) DEFAULT NULL,
  PRIMARY KEY (`COD_EVENTO`),
  KEY `FK_USUARIO` (`FK_USUARIO`),
  CONSTRAINT `EVENTO_ibfk_1` FOREIGN KEY (`FK_USUARIO`) REFERENCES `USUARIO` (`UCODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EVENTO`
--

LOCK TABLES `EVENTO` WRITE;
/*!40000 ALTER TABLE `EVENTO` DISABLE KEYS */;
INSERT INTO `EVENTO` VALUES (2,'testestestestesteste','2018-12-09 02:10:00','2018-12-08 00:00:00',_binary '','aqui','asfsafasfasfaffsasfaf',2,3,NULL,4),(3,'testando','2018-12-09 02:10:00','2018-12-10 20:10:00',_binary '\0','São Paulo','descricao decente ',2,0,NULL,3);
/*!40000 ALTER TABLE `EVENTO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `E_FIM_REPETICAO`
--

DROP TABLE IF EXISTS `E_FIM_REPETICAO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `E_FIM_REPETICAO` (
  `COD_FIM_REPETICAO` int(11) NOT NULL AUTO_INCREMENT,
  `DIA_FIM` datetime DEFAULT NULL,
  `QTD_RECORRENCIAS` int(11) DEFAULT NULL,
  `FK_EVENTO` int(11) DEFAULT NULL,
  PRIMARY KEY (`COD_FIM_REPETICAO`),
  UNIQUE KEY `UNIQUE_EVENTO` (`FK_EVENTO`),
  CONSTRAINT `E_FIM_REPETICAO_ibfk_1` FOREIGN KEY (`FK_EVENTO`) REFERENCES `EVENTO` (`COD_EVENTO`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `E_FIM_REPETICAO`
--

LOCK TABLES `E_FIM_REPETICAO` WRITE;
/*!40000 ALTER TABLE `E_FIM_REPETICAO` DISABLE KEYS */;
INSERT INTO `E_FIM_REPETICAO` VALUES (2,NULL,40,2),(3,NULL,0,3);
/*!40000 ALTER TABLE `E_FIM_REPETICAO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `E_REPETIR`
--

DROP TABLE IF EXISTS `E_REPETIR`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `E_REPETIR` (
  `COD_REPETICAO` int(11) NOT NULL AUTO_INCREMENT,
  `INTERVALO` int(11) NOT NULL,
  `DIAS_SEMANA` varchar(15) DEFAULT NULL,
  `FK_EVENTO` int(11) DEFAULT NULL,
  PRIMARY KEY (`COD_REPETICAO`),
  UNIQUE KEY `UNIQUE_EVENTO` (`FK_EVENTO`),
  CONSTRAINT `E_REPETIR_ibfk_1` FOREIGN KEY (`FK_EVENTO`) REFERENCES `EVENTO` (`COD_EVENTO`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `E_REPETIR`
--

LOCK TABLES `E_REPETIR` WRITE;
/*!40000 ALTER TABLE `E_REPETIR` DISABLE KEYS */;
INSERT INTO `E_REPETIR` VALUES (2,20,'1,1,1,1,1,0,0',2),(3,1,'0,1,1,0,0,1,0',3);
/*!40000 ALTER TABLE `E_REPETIR` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `HORARIO_LEMBRETE`
--

DROP TABLE IF EXISTS `HORARIO_LEMBRETE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `HORARIO_LEMBRETE` (
  `HL_CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `DATA_LEMBRETE` datetime DEFAULT NULL,
  `DATA_FINAL_LEMBRETE` datetime DEFAULT NULL,
  `HORARIO_INICIO` time DEFAULT NULL,
  `HORARIO_FIM` time DEFAULT NULL,
  `HL_INTERVALO_MINUTOS` int(11) DEFAULT NULL,
  `HL_RECORRENCIA` int(11) DEFAULT NULL,
  `HL_SEMANA_DIA` int(11) DEFAULT NULL,
  `HL_QTD_REPETE` int(11) DEFAULT NULL,
  `HL_ATIVO` bit(1) DEFAULT NULL,
  `FK_LEMBRETE` int(11) DEFAULT NULL,
  PRIMARY KEY (`HL_CODIGO`),
  KEY `FK_CODIGO_LEMBRETE` (`FK_LEMBRETE`),
  CONSTRAINT `FK_CODIGO_LEMBRETE` FOREIGN KEY (`FK_LEMBRETE`) REFERENCES `LEMBRETE` (`LCOD_LEMBRETE`)
) ENGINE=InnoDB AUTO_INCREMENT=2071 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `HORARIO_LEMBRETE`
--

LOCK TABLES `HORARIO_LEMBRETE` WRITE;
/*!40000 ALTER TABLE `HORARIO_LEMBRETE` DISABLE KEYS */;
INSERT INTO `HORARIO_LEMBRETE` VALUES (5,'2018-12-08 16:13:49',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3),(6,'2018-12-08 16:13:49',NULL,NULL,NULL,NULL,NULL,NULL,NULL,_binary '',4),(7,'2018-12-08 16:22:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3),(8,'2018-12-08 16:22:03',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4),(9,'2018-12-08 16:22:05',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6),(10,'2018-12-08 16:22:07',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6),(11,'2018-12-08 16:22:09',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3),(12,'2018-12-08 16:22:11',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,5),(13,'2018-12-08 16:22:13',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4),(14,'2018-12-08 16:22:16',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6),(15,'2018-12-08 16:22:20',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4),(2042,'2018-12-12 21:52:00','2019-01-08 00:00:00',NULL,NULL,NULL,4,0,NULL,_binary '',14),(2043,'2018-12-12 21:52:00','2019-01-08 00:00:00',NULL,NULL,NULL,4,0,NULL,_binary '',14),(2044,'2018-12-12 21:52:00','2019-01-08 00:00:00',NULL,NULL,NULL,4,0,NULL,_binary '',14),(2045,'2018-12-12 21:52:00','2019-01-08 00:00:00',NULL,NULL,NULL,4,0,NULL,_binary '',14),(2046,'2018-12-12 21:52:00','2019-01-08 00:00:00',NULL,NULL,NULL,4,0,NULL,_binary '',14),(2047,'2018-12-12 21:52:00','2019-01-08 00:00:00',NULL,NULL,NULL,4,0,NULL,_binary '',14),(2048,'2018-12-12 21:52:00','2019-01-08 00:00:00',NULL,NULL,NULL,4,0,NULL,_binary '',14),(2049,'2018-12-12 21:52:00','2019-01-08 00:00:00',NULL,NULL,NULL,4,0,NULL,_binary '',14),(2050,'2018-12-12 21:52:00','2019-01-08 00:00:00',NULL,NULL,NULL,4,0,NULL,_binary '',14),(2051,'2018-12-12 21:52:00','2019-01-08 00:00:00',NULL,NULL,NULL,4,0,NULL,_binary '',14),(2052,'2018-12-12 21:52:00','2019-01-08 00:00:00',NULL,NULL,NULL,4,0,NULL,_binary '',14),(2053,'2018-12-12 21:52:00','2019-01-08 00:00:00',NULL,NULL,NULL,4,0,NULL,_binary '',14),(2054,'2018-12-12 21:52:00','2019-01-08 00:00:00',NULL,NULL,NULL,4,0,NULL,_binary '',14),(2055,'2018-12-12 21:52:00','2019-01-08 00:00:00',NULL,NULL,NULL,4,1,NULL,_binary '',14),(2056,'2018-12-12 21:52:00','2019-01-08 00:00:00',NULL,NULL,NULL,4,1,NULL,_binary '',14),(2057,'2018-12-12 21:52:00','2019-01-08 00:00:00',NULL,NULL,NULL,4,2,NULL,_binary '',14),(2058,'2018-12-12 21:52:00','2019-01-08 00:00:00',NULL,NULL,NULL,4,2,NULL,_binary '',14),(2059,'2018-12-08 00:00:00',NULL,NULL,NULL,NULL,1,0,NULL,_binary '',15),(2060,'2018-12-08 00:00:00',NULL,NULL,NULL,NULL,1,1,NULL,_binary '',15),(2061,'2018-12-08 00:00:00',NULL,NULL,NULL,NULL,1,2,NULL,_binary '',15),(2062,'2018-12-09 00:00:00',NULL,NULL,NULL,NULL,1,0,NULL,_binary '',16),(2063,'2018-12-09 00:00:00',NULL,NULL,NULL,NULL,1,1,NULL,_binary '',16),(2064,'2018-12-09 00:00:00',NULL,NULL,NULL,NULL,1,2,NULL,_binary '',16),(2065,'2018-12-11 00:29:00',NULL,NULL,NULL,NULL,1,0,NULL,_binary '',17),(2066,'2018-12-11 18:29:00',NULL,NULL,NULL,NULL,1,0,NULL,_binary '',17),(2067,'2018-12-11 11:29:00',NULL,NULL,NULL,NULL,1,0,NULL,_binary '',17),(2068,'2018-12-11 00:29:00',NULL,NULL,NULL,NULL,1,1,NULL,_binary '',17),(2069,'2018-12-11 18:29:00',NULL,NULL,NULL,NULL,1,1,NULL,_binary '',17),(2070,'2018-12-11 11:29:00',NULL,NULL,NULL,NULL,1,1,NULL,_binary '',17);
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
  `TITULO` varchar(255) DEFAULT NULL,
  `ATIVO` bit(1) DEFAULT NULL,
  `TIPO_RECORRENCIA` int(11) DEFAULT NULL,
  `TIPO_REPETICAO` int(11) DEFAULT NULL,
  `FK_USUARIO` int(11) DEFAULT NULL,
  PRIMARY KEY (`LCOD_LEMBRETE`),
  KEY `FK_USUARIO` (`FK_USUARIO`),
  CONSTRAINT `FK_USUARIO` FOREIGN KEY (`FK_USUARIO`) REFERENCES `USUARIO` (`UCODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LEMBRETE`
--

LOCK TABLES `LEMBRETE` WRITE;
/*!40000 ALTER TABLE `LEMBRETE` DISABLE KEYS */;
INSERT INTO `LEMBRETE` VALUES (3,'TESTE',NULL,NULL,NULL,3),(4,'TESTEST',_binary '',0,0,3),(5,'DKKDKD',NULL,NULL,NULL,3),(6,'DSSSSSSS',NULL,NULL,NULL,3),(7,'DQQQQQQQQQQQ',NULL,NULL,NULL,3),(8,'DQ445232523FQ',NULL,NULL,NULL,3),(9,'DQAFSEQT2T326246HGDS',NULL,NULL,NULL,3),(10,'testestestestesrf',_binary '',2,0,3),(11,'teste',_binary '',2,2,3),(12,'fffffffffffffffffffffff',_binary '',2,2,3),(13,'fffffffffffffffffffffff',_binary '',2,2,3),(14,'yteetetet',_binary '',2,2,3),(15,'outro teste ',_binary '',2,0,3),(16,'ooooooo',_binary '',2,0,3),(17,'testezinho',_binary '',2,2,3);
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
  `UATIVO` bit(1) DEFAULT NULL,
  PRIMARY KEY (`UCODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USUARIO`
--

LOCK TABLES `USUARIO` WRITE;
/*!40000 ALTER TABLE `USUARIO` DISABLE KEYS */;
INSERT INTO `USUARIO` VALUES (3,'j','sssssss','ssssss','1',NULL),(4,'j',' SKKK','jefterf','1',NULL);
/*!40000 ALTER TABLE `USUARIO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `VIEW_CARREGAR_TODOS_LEMBRETES`
--

DROP TABLE IF EXISTS `VIEW_CARREGAR_TODOS_LEMBRETES`;
/*!50001 DROP VIEW IF EXISTS `VIEW_CARREGAR_TODOS_LEMBRETES`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `VIEW_CARREGAR_TODOS_LEMBRETES` AS SELECT 
 1 AS `UCODIGO`,
 1 AS `LCOD_LEMBRETE`,
 1 AS `HL_CODIGO`*/;
SET character_set_client = @saved_cs_client;

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
 1 AS `UEMAIL`,
 1 AS `USENHA`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `VIEW_LEMBRETES_DO_DIA`
--

DROP TABLE IF EXISTS `VIEW_LEMBRETES_DO_DIA`;
/*!50001 DROP VIEW IF EXISTS `VIEW_LEMBRETES_DO_DIA`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `VIEW_LEMBRETES_DO_DIA` AS SELECT 
 1 AS `UCODIGO`,
 1 AS `LCOD_LEMBRETE`,
 1 AS `TIPO_RECORRENCIA`,
 1 AS `HL_CODIGO`,
 1 AS `HL_RECORRENCIA`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `VIEW_CARREGAR_TODOS_LEMBRETES`
--

/*!50001 DROP VIEW IF EXISTS `VIEW_CARREGAR_TODOS_LEMBRETES`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `VIEW_CARREGAR_TODOS_LEMBRETES` AS select `USUARIO`.`UCODIGO` AS `UCODIGO`,`LEMBRETE`.`LCOD_LEMBRETE` AS `LCOD_LEMBRETE`,`HORARIO_LEMBRETE`.`HL_CODIGO` AS `HL_CODIGO` from ((`LEMBRETE` join `USUARIO` on((`LEMBRETE`.`FK_USUARIO` = `USUARIO`.`UCODIGO`))) join `HORARIO_LEMBRETE` on((`LEMBRETE`.`LCOD_LEMBRETE` = `HORARIO_LEMBRETE`.`FK_LEMBRETE`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

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
/*!50001 VIEW `VIEW_INICIA_SESSAO` AS select `USUARIO`.`UCODIGO` AS `UCODIGO`,`USUARIO`.`UNOME` AS `UNOME`,`USUARIO`.`USOBRENOME` AS `USOBRENOME`,`USUARIO`.`UEMAIL` AS `UEMAIL`,`USUARIO`.`USENHA` AS `USENHA` from `USUARIO` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `VIEW_LEMBRETES_DO_DIA`
--

/*!50001 DROP VIEW IF EXISTS `VIEW_LEMBRETES_DO_DIA`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `VIEW_LEMBRETES_DO_DIA` AS select `USUARIO`.`UCODIGO` AS `UCODIGO`,`L`.`LCOD_LEMBRETE` AS `LCOD_LEMBRETE`,`L`.`TIPO_RECORRENCIA` AS `TIPO_RECORRENCIA`,`HL`.`HL_CODIGO` AS `HL_CODIGO`,`HL`.`HL_RECORRENCIA` AS `HL_RECORRENCIA` from ((`LEMBRETE` `L` join `USUARIO` on((`L`.`FK_USUARIO` = `USUARIO`.`UCODIGO`))) join `HORARIO_LEMBRETE` `HL` on((`L`.`LCOD_LEMBRETE` = `HL`.`FK_LEMBRETE`))) where (((cast(`HL`.`DATA_LEMBRETE` as date) = cast(now() as date)) or (cast(`CALCULO_RECORRENCIA`(`HL`.`DATA_LEMBRETE`,`L`.`TIPO_RECORRENCIA`,`HL`.`HL_RECORRENCIA`) as date) = cast(now() as date))) and `HL`.`HL_ATIVO` and (`L`.`ATIVO` = TRUE)) */;
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

-- Dump completed on 2018-12-10  5:10:19

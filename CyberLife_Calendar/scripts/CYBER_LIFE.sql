create database CYBER_LIFE;
	use CYBER_LIFE;
    
DROP TABLE IF EXISTS `HORARIO_LEMBRETE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `HORARIO_LEMBRETE` (
  `RLCOD_REPETICAO` int(11) NOT NULL AUTO_INCREMENT,
  `RLHORARIO_LEMBRETE` time DEFAULT NULL,
  `FKCOD_LEMBRETE` int(11) DEFAULT NULL,
  PRIMARY KEY (`RLCOD_REPETICAO`),
  KEY `FK_LEMBRETE` (`FKCOD_LEMBRETE`),
  CONSTRAINT `FK_LEMBRETE` FOREIGN KEY (`FKCOD_LEMBRETE`) REFERENCES `LEMBRETE` (`LCOD_LEMBRETE`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `HORARIO_LEMBRETE` WRITE;
/*!40000 ALTER TABLE `HORARIO_LEMBRETE` DISABLE KEYS */;
INSERT INTO `HORARIO_LEMBRETE` VALUES (3,'00:21:38',1),(4,'00:22:36',1);
/*!40000 ALTER TABLE `HORARIO_LEMBRETE` ENABLE KEYS */;
UNLOCK TABLES;

DROP TABLE IF EXISTS `LEMBRETE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LEMBRETE` (
  `LCOD_LEMBRETE` int(11) NOT NULL AUTO_INCREMENT,
  `LNOME` varchar(45) DEFAULT NULL,
  `LDESCRICAO` text,
  `LDATE_LEMBRETE` date DEFAULT NULL,
  `LQTD_REPETE` int(11) DEFAULT NULL,
  `LDIA_TODO` bit(1) DEFAULT NULL,
  `FK_USUARIO` int(11) NOT NULL,
  PRIMARY KEY (`LCOD_LEMBRETE`),
  KEY `FK_USUARIO` (`FK_USUARIO`),
  CONSTRAINT `FK_USUARIO` FOREIGN KEY (`FK_USUARIO`) REFERENCES `USUARIO` (`UCODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `LEMBRETE` WRITE;
/*!40000 ALTER TABLE `LEMBRETE` DISABLE KEYS */;
INSERT INTO `LEMBRETE` VALUES (1,'TESTE',NULL,'2018-10-30',NULL,NULL,2);
/*!40000 ALTER TABLE `LEMBRETE` ENABLE KEYS */;
UNLOCK TABLES;

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

LOCK TABLES `USUARIO` WRITE;
/*!40000 ALTER TABLE `USUARIO` DISABLE KEYS */;
INSERT INTO `USUARIO` VALUES (1,'EKEK','KAKA',NULL,'KAKA'),(2,'JEFTER.SANTIAGO66@GMAIL.COM','JEFTER SANTIAGO',NULL,'1234'),(3,'TESTE','TESTE',NULL,NULL);
/*!40000 ALTER TABLE `USUARIO` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `ADICIONAR_USUARIO`( IN EMAIL VARCHAR(255), IN NOME VARCHAR(255),             
         IN SOBRENOME VARCHAR(255), IN SENHA VARCHAR(255))
BEGIN                                                                                  
                                                                                              
             IF SOBRENOME IS NULL OR SOBRENOME = '' THEN                                      
                                                                                              
                INSERT INTO USUARIO (UEMAIL,UNOME, USENHA) VALUES (EMAIL,NOME,SENHA);         
                                                                                              
             ELSE                                                                             
                                                                                              
                        INSERT INTO USUARIO (UEMAIL,UNOME,USOBRENOME,USENHA) VALUES           
                               (EMAIL,NOME,SOBRENOME,SENHA);                                  
              END IF;                                                                         
                                                                                              
                                                                                              
       END ;;
DELIMITER ;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `TEST`(IN STR VARCHAR(255), OUT VALUE BIT)
BEGIN 
      IF STR = "TESTE" THEN 
           SET VALUE = 1;
      ELSE 
           SET VALUE = 0;
      END IF ;
END ;;
DELIMITER ;
    

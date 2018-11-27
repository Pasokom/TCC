DELIMITER // 
CREATE PROCEDURE ADICIONAR_USUARIO( IN EMAIL VARCHAR(255), IN NOME VARCHAR(255),             
         IN SOBRENOME VARCHAR(255), IN SENHA VARCHAR(255))
BEGIN                                                                                  
             IF SOBRENOME IS NULL OR SOBRENOME = '' THEN                                      
                INSERT INTO USUARIO (UEMAIL,UNOME, USENHA) VALUES (EMAIL,NOME,SENHA);         
             ELSE                                                                             
                        INSERT INTO USUARIO (UEMAIL,UNOME,USOBRENOME,USENHA) VALUES           
                               (EMAIL,NOME,SOBRENOME,SENHA);                                  
              END IF;                                                                         
END 
//
DELIMITER // 
CREATE PROCEDURE EMAIL_EXISTE(IN EMAIL VARCHAR(255), OUT RESULT BIT)
BEGIN 
     DECLARE NUMERO_REGISTROS INT;  
     SELECT COUNT(0) INTO NUMERO_REGISTROS FROM USUARIO WHERE UEMAIL = EMAIL;        
           
    IF NUMERO_REGISTROS > 0 THEN               
        SET RESULT = 1;        
    ELSE                 
        SET RESULT = 0;        
   END IF;    
END 
//

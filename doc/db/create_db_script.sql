CREATE DATABASE IF NOT EXISTS chat
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS chat.chat (
timestamp varchar(100),
ip varchar(100),
port INT,
username varchar(100),
value varchar(100),
CONSTRAINT chat_PK PRIMARY KEY (timestamp)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS chat.login (
timestamp varchar(100),
ip varchar(100),
port INT,
username varchar(100),
value varchar(100),
CONSTRAINT chat_PK PRIMARY KEY (timestamp)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

/*
use chat;
show tables;
*/

CREATE USER IF NOT EXISTS 'pepe'@'%' IDENTIFIED BY 'pepa';
GRANT Delete ON chat.* TO 'pepe'@'%';
GRANT Insert ON chat.* TO 'pepe'@'%';
GRANT Select ON chat.* TO 'pepe'@'%';
GRANT Show view ON chat.* TO 'pepe'@'%';
GRANT Trigger ON chat.* TO 'pepe'@'%';
GRANT Update ON chat.* TO 'pepe'@'%';

/*
select * from mysql. user;
 */

/*
Ana --> **ANA**
Luis --> **LUIS**
Ignacio --> **IGNACIO**
Alberto --> **ALBERTO**
Elena --> **ELENA**
María --> **MARIA**
Julia --> **JULIA**
 */

CREATE TABLE IF NOT EXISTS `users` (
`name` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
`salt` varchar(128) COLLATE utf8_spanish2_ci NOT NULL,
`hash` varchar(64) COLLATE utf8_spanish2_ci NOT NULL,
PRIMARY KEY (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci AUTO_INCREMENT=8 ;
INSERT INTO `users` (`name`, `salt`, `hash`) VALUES
('Luis', '06a23953940a28265546a4d634653a1ea80c44a092bd58c92e5c5a8affc88c8ab165e6ddab4fd4f80c62981fb8f24017c93063ccf852e922fc5a242b574d6143', '5e18cc40f26536da7eb4f8e0329bc06a040ab1d7e246a8d190291cf36380f7bd'),
('María', '29018319872df692faa006fca305dbfa0199152ab6a834324e954b36458aa4e4884d8fda08e179cd4a7db083ac84c8b8db9e623e094e3fb016833a05a7de8367', '91ca6ae5024bf39f51e17465d1595711f88b21cf33c7df522d95fb405b3cf244'),
('Julia', 'ac09e91b51cf1d299a92f39a587b0675b9522b82c2ef7764e8dad6f38dd9659f5b7a1549c08c59974672577b50a9d999a5acdbf9ccd419d137af6f3630816a4d', '7d7d885fa75c44ad3a5f05d479ed9430f6683fc14bd39eefad822868d4085fab'),
('Elena', 'f7b69496ff14e73b804c6316aaab31ba6f7114606fadee2376ab11b6f5c9deab957c0f5a139408f196623e53421b26ef0b8f2a204a6073bb892c5af94eee2627', '612896a9f7d83b920bdee2296c4471f97bb592b96c34ef134c9120ecc60ada0a'),
('Ignacio', 'b65cad97d089e3bd970c839c07ab811dfc5f3b20c11830d62a95a3aa6ebf00b30659702bfa9524e2866c7aee3b58a839cc0f5133e00cb1e540c6126d418af6d2', '1f644c10e593d14aa1477d673327e94f51d7d759e339dc291f79bcaee728200c'),
('Alberto', '7f5d2e9450b0d6a50164f65a03eaee46baadea0877d6244ebe2fb42ca749774f296c99bf4e6cc2bf0d78cd2e2d339fdc70ebc0706ff8ef7b4cff3f609b5e25a5', 'ca8b92e94a9b7f40d5f5d3da885ab2e061337b1edd801cb1fe2f3f3bf156d281'),
('Ana', 'da685c9b48626908dc15aba7c7c5ad2134d6b17c3805950b6aeba1ea9b4c35fcf3876377f5d7b8102f4db25e7629a820003455f1f1b1e3b582811e5037b0ea8e', 'ac576432b901f0d63d88379df83188ba3ab760b5065b51756d1902b9982bd6ba');

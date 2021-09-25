-- Adminer 4.8.1 MySQL 8.0.25 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `contract_type`;
CREATE TABLE `contract_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `label` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `job_application`;
CREATE TABLE `job_application` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `submitted_at` datetime DEFAULT NULL,
  `job_id` bigint NOT NULL,
  `status_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3c08x8yc1j3x7bucss5kwtpfs` (`job_id`),
  KEY `FK88osyy39791g1jm218lc5hasp` (`status_id`),
  KEY `FKfg4uj6s4ousyexrh8h3li2kdj` (`user_id`),
  CONSTRAINT `FK3c08x8yc1j3x7bucss5kwtpfs` FOREIGN KEY (`job_id`) REFERENCES `job_posting` (`id`),
  CONSTRAINT `FK88osyy39791g1jm218lc5hasp` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`),
  CONSTRAINT `FKfg4uj6s4ousyexrh8h3li2kdj` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `job_posting`;
CREATE TABLE `job_posting` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `salary` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `company_id` bigint NOT NULL,
  `contract_type_id` bigint NOT NULL,
  `status_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKshbjmfuh3ad24qiunrpymtb1m` (`company_id`),
  KEY `FKc79pxlgqxq2v1xxy2mvjb3sjc` (`contract_type_id`),
  KEY `FKf7jk2oon5xsimjeh4xmbqwuri` (`status_id`),
  CONSTRAINT `FKc79pxlgqxq2v1xxy2mvjb3sjc` FOREIGN KEY (`contract_type_id`) REFERENCES `contract_type` (`id`),
  CONSTRAINT `FKf7jk2oon5xsimjeh4xmbqwuri` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`),
  CONSTRAINT `FKshbjmfuh3ad24qiunrpymtb1m` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `job_seeker`;
CREATE TABLE `job_seeker` (
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `FKead72nie619d4qb9xw2ularex` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `job_seeker` (`user_id`) VALUES
(1),
(2),
(3),
(4),
(5);

DROP TABLE IF EXISTS `recruiter`;
CREATE TABLE `recruiter` (
  `user_id` bigint NOT NULL,
  `company_id` bigint DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `FKpyw2brlomui73s0vau7qahfer` (`company_id`),
  CONSTRAINT `FKk1cki6qp45teiup1etc9rkcl4` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKpyw2brlomui73s0vau7qahfer` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `refresh_token`;
CREATE TABLE `refresh_token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `creation_date` datetime DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `relation`;
CREATE TABLE `relation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `confirmed_at` datetime DEFAULT NULL,
  `sent_at` datetime DEFAULT NULL,
  `receiver_id` bigint NOT NULL,
  `sender_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1clg41sc3p853ijhcj1ck0axa` (`receiver_id`),
  KEY `FKp69ul1mbjngny40iqyp4kalh9` (`sender_id`),
  CONSTRAINT `FK1clg41sc3p853ijhcj1ck0axa` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKp69ul1mbjngny40iqyp4kalh9` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `role` (`id`, `name`) VALUES
(1,	'USER'),
(2,	'ADMIN'),
(3,	'JOB_SEEKER'),
(4,	'RECRUITER');

DROP TABLE IF EXISTS `status`;
CREATE TABLE `status` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `label` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `training`;
CREATE TABLE `training` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `school` varchar(255) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdw3nkx71srj9qksp08g367t4q` (`user_id`),
  CONSTRAINT `FKdw3nkx71srj9qksp08g367t4q` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `activated_at` datetime DEFAULT NULL,
  `activation_key` varchar(255) DEFAULT NULL,
  `active` bit(1) NOT NULL,
  `birthday` date DEFAULT NULL,
  `contribution` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `expectation` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `hobbies` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `presentation` varchar(255) DEFAULT NULL,
  `purpose` varchar(255) DEFAULT NULL,
  `registered_at` datetime DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `user` (`id`, `activated_at`, `activation_key`, `active`, `birthday`, `contribution`, `email`, `expectation`, `firstname`, `hobbies`, `lastname`, `password`, `phone_number`, `presentation`, `purpose`, `registered_at`, `url`, `username`) VALUES
(1,	'2021-09-24 23:11:23',	'793d746c-36f8-4937-bda6-7d6222cf3f51',	CONV('1', 2, 10) + 0,	NULL,	NULL,	'matthieu@mail.com',	NULL,	'Matthieu',	NULL,	'Audemard',	'$2a$10$vmy1fqGnyW7ZN0RXww2j2uBue.A4/5CSEGX6rsROmWfpwr.0bTVWG',	NULL,	NULL,	NULL,	'2021-09-24 23:10:48',	NULL,	'matthieu'),
(2,	'2021-09-24 23:12:41',	'35254265-fa9d-4514-ae23-f167a5f6a831',	CONV('1', 2, 10) + 0,	NULL,	NULL,	'dylan@mail.com',	NULL,	'Dylan',	NULL,	'Brudey',	'$2a$10$s7u8ams6mKx4HlzczmTpDu2r.GWNwxkQzfHar1Rq9YkovEE2rzdCu',	NULL,	NULL,	NULL,	'2021-09-24 23:12:28',	NULL,	'dylan'),
(3,	'2021-09-24 23:14:33',	'88381beb-dd09-45a0-9e9f-66e468347ab9',	CONV('1', 2, 10) + 0,	NULL,	NULL,	'mel@mail.com',	NULL,	'Mélanie',	NULL,	'Da Costa',	'$2a$10$mLj1NYUHlajXwPurnR2h6eCshIRu8w1clempU07UfCSyh6Dcy3z6S',	NULL,	NULL,	NULL,	'2021-09-24 23:14:23',	NULL,	'melanie'),
(4,	'2021-09-24 23:15:21',	'5ee1b136-1a89-48eb-bce7-241ef06b79de',	CONV('1', 2, 10) + 0,	NULL,	NULL,	'paul@mail.com',	NULL,	'Paul',	NULL,	'Flumian',	'$2a$10$p1dW/T62V7C2vt2OK7dVruZVbQccT/GIetlSQc4Wh7jkariyBXq9W',	NULL,	NULL,	NULL,	'2021-09-24 23:15:11',	NULL,	'paul'),
(5,	'2021-09-24 23:17:43',	'b1e89b98-d2a3-4958-9a9c-270ab2c0439a',	CONV('1', 2, 10) + 0,	NULL,	NULL,	'm@thusha.com',	NULL,	'Mathusha',	NULL,	'Thirulogasingam',	'$2a$10$khA09HcY4cvY0B30.YVeJeVVqWjCMJ.0sfsvKCzmTKCnzQn5eV5b2',	NULL,	NULL,	NULL,	'2021-09-24 23:17:32',	NULL,	'mathusha'),
(6,	'2021-09-24 21:19:45',	'35412334-173f-48c8-bca5-2829a9e4c0e3',	CONV('1', 2, 10) + 0,	NULL,	NULL,	'admin@mail.com',	NULL,	'',	NULL,	'',	'$2a$10$QvalNDrZUPqhLmrF4/ZPTeFWSA0CoefXwQ/uCYxpRF/vlkTXlNx1G',	NULL,	NULL,	NULL,	'2021-09-24 23:19:08',	NULL,	'admin');

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  KEY `FKka3w3atry4amefp94rblb52n7` (`role_id`),
  KEY `FKhjx9nk20h4mo745tdqj8t8n9d` (`user_id`),
  CONSTRAINT `FKhjx9nk20h4mo745tdqj8t8n9d` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKka3w3atry4amefp94rblb52n7` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
(1,	1),
(1,	3),
(2,	1),
(2,	3),
(3,	1),
(3,	3),
(4,	1),
(4,	3),
(5,	1),
(5,	3),
(6,	1),
(6,	2);

DROP TABLE IF EXISTS `work_experience`;
CREATE TABLE `work_experience` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `company_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `finished_at` date DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `started_at` date DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9cm0xm0lomh536jvxdo0yowb7` (`user_id`),
  CONSTRAINT `FK9cm0xm0lomh536jvxdo0yowb7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- 2021-09-25 14:15:20

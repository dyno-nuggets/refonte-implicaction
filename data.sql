-- Adminer 4.8.1 MySQL 8.0.31 dump

SET NAMES utf8;
SET
time_zone = '+00:00';
SET
foreign_key_checks = 0;
SET
sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `text`       text,
    `post_id`    bigint   DEFAULT NULL,
    `user_id`    bigint   DEFAULT NULL,
    `created_at` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY          `FK8kcum44fvpupyw6f5baccx25c` (`user_id`),
    KEY          `FKs1slvnkuemjsq2kj4h3vhx7i1` (`post_id`),
    CONSTRAINT `FK8kcum44fvpupyw6f5baccx25c` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FKs1slvnkuemjsq2kj4h3vhx7i1` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `company`;
CREATE TABLE `company`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT,
    `description` longtext,
    `logo`        varchar(255) DEFAULT NULL,
    `name`        varchar(255) NOT NULL,
    `url`         varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`
(
    `id`           bigint NOT NULL,
    `content_type` varchar(255) DEFAULT NULL,
    `filename`     varchar(255) DEFAULT NULL,
    `url`          varchar(255) DEFAULT NULL,
    `uploader_id`  bigint       DEFAULT NULL,
    `object_key`   varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY            `FKgvpcrd2olmq75ujpppe0jn3cj` (`uploader_id`),
    CONSTRAINT `FKgvpcrd2olmq75ujpppe0jn3cj` FOREIGN KEY (`uploader_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`
(
    `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `ia_group`;
CREATE TABLE `ia_group`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `description` varchar(255) DEFAULT NULL,
    `name`        varchar(255) DEFAULT NULL,
    `user_id`     bigint       DEFAULT NULL,
    `created_at`  datetime     DEFAULT NULL,
    `image_id`    bigint       DEFAULT NULL,
    `valid`       bit(1)       DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY           `FKt88xyv8u7r6f3m1byxhrfj4ck` (`user_id`),
    KEY           `FKaav1cbg8lm2vr5fc20ksdva0s` (`image_id`),
    CONSTRAINT `FKaav1cbg8lm2vr5fc20ksdva0s` FOREIGN KEY (`image_id`) REFERENCES `file` (`id`),
    CONSTRAINT `FKt88xyv8u7r6f3m1byxhrfj4ck` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `ia_group` (`id`, `description`, `name`, `user_id`, `created_at`, `image_id`, `valid`)
VALUES (33, 'Region Ile de France desc', 'Region Ile de France', 6, '2021-11-12 22:34:32', 6, CONV('1', 2, 10) + 0),
       (34, '', 'test sans image', 6, '2021-11-12 23:38:39', NULL, CONV('1', 2, 10) + 0),
       (35, '', 'test', 6, '2021-11-13 01:02:10', NULL, CONV('1', 2, 10) + 0),
       (36, '', 'léo', 6, '2021-11-13 02:57:30', 7, CONV('1', 2, 10) + 0),
       (38, 'test', 'ecran', 1, '2021-11-14 02:55:42', 9, CONV('1', 2, 10) + 0),
       (39, 'fdsqfdsqfqsf', 'on fait des modifs !', 1, '2021-11-14 03:54:47', 10, CONV('1', 2, 10) + 0),
       (40, '', '', 1, '2021-11-14 09:05:51', 12, CONV('1', 2, 10) + 0),
       (41, '', 'github', 1, '2021-11-14 15:55:15', 13, CONV('1', 2, 10) + 0),
       (42, 'depuis rename', 'depuis rename', 1, '2021-11-26 10:33:17', 51, CONV('1', 2, 10) + 0),
       (43, 'noimage', 'noimage', 1, '2021-12-11 22:05:09', NULL, CONV('1', 2, 10) + 0),
       (44, 'test', 'test', 1, '2021-12-12 21:35:53', NULL, CONV('0', 2, 10) + 0),
       (45, 'test2', 'test2', 3, '2021-12-12 21:36:28', NULL, CONV('1', 2, 10) + 0),
       (46, 'test', 'test', 1, '2021-12-12 21:40:07', 55, CONV('0', 2, 10) + 0);

DROP TABLE IF EXISTS `job_application`;
CREATE TABLE `job_application`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `last_update` datetime     DEFAULT NULL,
    `status`      varchar(255) DEFAULT NULL,
    `job_id`      bigint NOT NULL,
    `user_id`     bigint NOT NULL,
    `archive`     tinyint(1) DEFAULT '0',
    PRIMARY KEY (`id`),
    KEY           `FKobel3nnbi451ftywx9q9m2qax` (`job_id`),
    KEY           `FKcbu1yb4kyxowejebm87crxtr8` (`user_id`),
    CONSTRAINT `FKcbu1yb4kyxowejebm87crxtr8` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FKobel3nnbi451ftywx9q9m2qax` FOREIGN KEY (`job_id`) REFERENCES `job_posting` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `job_posting`;
CREATE TABLE `job_posting`
(
    `id`                bigint       NOT NULL AUTO_INCREMENT,
    `description`       longtext,
    `keywords`          varchar(255) DEFAULT NULL,
    `location`          varchar(255) DEFAULT NULL,
    `salary`            varchar(255) DEFAULT NULL,
    `title`             varchar(255) NOT NULL,
    `company_id`        bigint       NOT NULL,
    `created_at`        datetime     DEFAULT NULL,
    `short_description` text,
    `archive`           bit(1)       DEFAULT NULL,
    `contract_type`     varchar(255) DEFAULT NULL,
    `active`            bit(1)       DEFAULT NULL,
    `business_sector`   varchar(255) DEFAULT NULL,
    `valid`             bit(1)       DEFAULT NULL,
    `posted_by`         bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY                 `FKd9euom18rv4nilau4hblm08cv` (`company_id`),
    KEY                 `FKd21ec91nnjftglc01m2dg70yr` (`posted_by`),
    CONSTRAINT `FKd21ec91nnjftglc01m2dg70yr` FOREIGN KEY (`posted_by`) REFERENCES `user` (`id`),
    CONSTRAINT `FKd9euom18rv4nilau4hblm08cv` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


DROP TABLE IF EXISTS `job_seeker`;
CREATE TABLE `job_seeker`
(
    `user_id` bigint NOT NULL,
    PRIMARY KEY (`user_id`),
    CONSTRAINT `FKt6no6vfq2vtvqbwlqyik9hyef` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification`
(
    `id`      bigint NOT NULL AUTO_INCREMENT,
    `date`    datetime     DEFAULT NULL,
    `message` varchar(255) DEFAULT NULL,
    `is_read` bit(1)       DEFAULT NULL,
    `is_sent` bit(1)       DEFAULT NULL,
    `title`   varchar(255) DEFAULT NULL,
    `type`    varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `created_at`  datetime     DEFAULT NULL,
    `description` longtext,
    `url`         varchar(255) DEFAULT NULL,
    `vote_count`  int          DEFAULT NULL,
    `group_id`    bigint       DEFAULT NULL,
    `user_id`     bigint       DEFAULT NULL,
    `name`        varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY           `FK72mt33dhhs48hf9gcqrq4fxte` (`user_id`),
    KEY           `FKmlnoks6ujgl9ynt53af0bx4pj` (`group_id`),
    CONSTRAINT `FK72mt33dhhs48hf9gcqrq4fxte` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FKmlnoks6ujgl9ynt53af0bx4pj` FOREIGN KEY (`group_id`) REFERENCES `ia_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `recruiter`;
CREATE TABLE `recruiter`
(
    `user_id`    bigint NOT NULL,
    `company_id` bigint DEFAULT NULL,
    PRIMARY KEY (`user_id`),
    KEY          `FKe5tll0cw7cnohojpxb8qjcr5y` (`company_id`),
    CONSTRAINT `FK63kq3uyt2p3i32pjo1nfin63a` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FKe5tll0cw7cnohojpxb8qjcr5y` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


DROP TABLE IF EXISTS `refresh_token`;
CREATE TABLE `refresh_token`
(
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `creation_date` datetime     DEFAULT NULL,
    `token`         varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


DROP TABLE IF EXISTS `relation`;
CREATE TABLE `relation`
(
    `id`           bigint NOT NULL AUTO_INCREMENT,
    `confirmed_at` datetime DEFAULT NULL,
    `sent_at`      datetime DEFAULT NULL,
    `receiver_id`  bigint NOT NULL,
    `sender_id`    bigint NOT NULL,
    PRIMARY KEY (`id`),
    KEY            `FKnfnboik5lv37rehgogdfthojk` (`receiver_id`),
    KEY            `FK38wce4d5fg7hv0o3t0lpfclom` (`sender_id`),
    CONSTRAINT `FK38wce4d5fg7hv0o3t0lpfclom` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FKnfnboik5lv37rehgogdfthojk` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `id`   bigint       NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


DROP TABLE IF EXISTS `training`;
CREATE TABLE `training`
(
    `id`      bigint NOT NULL AUTO_INCREMENT,
    `date`    date         DEFAULT NULL,
    `label`   varchar(255) DEFAULT NULL,
    `school`  varchar(255) DEFAULT NULL,
    `user_id` bigint NOT NULL,
    PRIMARY KEY (`id`),
    KEY       `FKfoy157kvf3lmam5gv5u2ty487` (`user_id`),
    CONSTRAINT `FKfoy157kvf3lmam5gv5u2ty487` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`             bigint NOT NULL AUTO_INCREMENT,
    `activated_at`   datetime     DEFAULT NULL,
    `activation_key` varchar(255) DEFAULT NULL,
    `active`         bit(1) NOT NULL,
    `birthday`       date         DEFAULT NULL,
    `contribution`   varchar(255) DEFAULT NULL,
    `email`          varchar(255) DEFAULT NULL,
    `expectation`    varchar(255) DEFAULT NULL,
    `firstname`      varchar(255) DEFAULT NULL,
    `hobbies`        varchar(255) DEFAULT NULL,
    `lastname`       varchar(255) DEFAULT NULL,
    `password`       varchar(255) DEFAULT NULL,
    `phone_number`   varchar(255) DEFAULT NULL,
    `presentation`   varchar(255) DEFAULT NULL,
    `purpose`        varchar(255) DEFAULT NULL,
    `registered_at`  datetime     DEFAULT NULL,
    `url`            varchar(255) DEFAULT NULL,
    `username`       varchar(255) DEFAULT NULL,
    `image_id`       bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
    UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`),
    KEY              `FK5v5h53roftm0e90x45m6bh7al` (`image_id`),
    CONSTRAINT `FK5v5h53roftm0e90x45m6bh7al` FOREIGN KEY (`image_id`) REFERENCES `file` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group`
(
    `user_id`  bigint NOT NULL,
    `group_id` bigint NOT NULL,
    KEY        `FK1kgi14tshuobvm55vfe4bmnmo` (`group_id`),
    KEY        `FK1c1dsw3q36679vaiqwvtv36a6` (`user_id`),
    CONSTRAINT `FK1c1dsw3q36679vaiqwvtv36a6` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FK1kgi14tshuobvm55vfe4bmnmo` FOREIGN KEY (`group_id`) REFERENCES `ia_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `user_notification`;
CREATE TABLE `user_notification`
(
    `user_id`         bigint NOT NULL,
    `notification_id` bigint NOT NULL,
    KEY               `FKi5naecliicmigrk01qx5me5sp` (`notification_id`),
    KEY               `FKnbuq84cli119n9cdakdw0kv5v` (`user_id`),
    CONSTRAINT `FKi5naecliicmigrk01qx5me5sp` FOREIGN KEY (`notification_id`) REFERENCES `notification` (`id`),
    CONSTRAINT `FKnbuq84cli119n9cdakdw0kv5v` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`
(
    `user_id` bigint NOT NULL,
    `role_id` bigint NOT NULL,
    KEY       `FKa68196081fvovjhkek5m97n3y` (`role_id`),
    KEY       `FK859n2jvi8ivhui0rl0esws6o` (`user_id`),
    CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


DROP TABLE IF EXISTS `vote`;
CREATE TABLE `vote`
(
    `id`        bigint NOT NULL AUTO_INCREMENT,
    `vote_type` int    DEFAULT NULL,
    `post_id`   bigint DEFAULT NULL,
    `user_id`   bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY         `FKcsaksoe2iepaj8birrmithwve` (`user_id`),
    KEY         `FKl3c067ewaw5xktl5cjvniv3e9` (`post_id`),
    CONSTRAINT `FKcsaksoe2iepaj8birrmithwve` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FKl3c067ewaw5xktl5cjvniv3e9` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `work_experience`;
CREATE TABLE `work_experience`
(
    `id`           bigint NOT NULL AUTO_INCREMENT,
    `company_name` varchar(255) DEFAULT NULL,
    `description`  varchar(255) DEFAULT NULL,
    `finished_at`  date         DEFAULT NULL,
    `label`        varchar(255) DEFAULT NULL,
    `started_at`   date         DEFAULT NULL,
    `user_id`      bigint NOT NULL,
    PRIMARY KEY (`id`),
    KEY            `FKhnxjamc0hrv0uok9w7aayk6kk` (`user_id`),
    CONSTRAINT `FKhnxjamc0hrv0uok9w7aayk6kk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


-- 2022-11-14 01:22:48

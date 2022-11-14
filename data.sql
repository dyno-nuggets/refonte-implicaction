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

INSERT INTO `comment` (`id`, `text`, `post_id`, `user_id`, `created_at`)
VALUES (105,
        '<p>Un commentaire qui présente le double intérêt de tester les commentaires et de vous remercier pour votre implication</p>',
        15, 15, '2021-11-26 07:52:15');

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

INSERT INTO `company` (`id`, `description`, `logo`, `name`, `url`)
VALUES (145,
        '<p><strong>ManpowerGroup</strong> est une entreprise multinationale spécialisée dans le conseil en ressources humaines, le recrutement, le travail intérimaire et les services aux infrastructures informatiques.</p>',
        'https://mobicheckin-assets.s3.amazonaws.com/uploads/events/5d358cc616cc81001fa53a0d/person_files/logo_new_YJ6QGMO_manpower.jpg',
        'Manpower', 'https://www.manpower.fr/'),
       (155,
        '<p>Amazon &nbsp;est une entreprise de commerce en ligne américaine basée à Seattle. Elle est l\'un des géants du Web,
        regroupés sous l\'acronyme GAFAM10, aux côtés de Google, Apple, Facebook et Microsoft. Créée par Jeff Bezos en juillet 1994, l\'entreprise a été introduite en bourse au NASDAQ en mai 1997.</p>',	'https://www.sportbuzzbusiness.fr/wp-content/uploads/2016/04/amazon-fr.png',	'Amazon',	'https://www.amazon.fr/'),
(165,	'<p>Spécialiste de la motoculture et du matériel pour espaces verts depuis plus de 20 ans,
        Greenmat vous propose une large gamme d’outils de qualité adaptés aux besoins des particuliers et des professionnels.</p>',	'https://media.cylex-locale.fr/companies/1309/9500/logo/logo.jpg',	'Greenmat',	'https://greenmat.eu/'),
(175,	'<p>Irisiôme est une entreprise de l’agglomération bordelaise créée en 2015,
        qui a conçu et aujourd’hui commercialise un dispositif médical de nouvelle génération visant un marché considérable en France,
        en Europe mais aussi dans le reste du monde.</p><p><br></p><p>Ce dispositif s’appuie sur des lasers innovants à base de fibres optiques et délivrant des impulsions ultracourtes.</p><p>Le marché visé est celui de la dermato-esthétique avec des applications bien implantées comme l’épilation laser ou promises à de belles croissance comme le détatouage.</p><p><br></p><p>Notre promesse de proposer des soins moins douloureux et plus rapides nous a d’ores et déjà permis de gagner la confiance de professionnels de santé de renommée internationale.</p>',	'https://www.apec.fr/files/live/mounts/images/media_entreprise/728145/logo_IRISIOME_728145_783558.png',	'Irisiome',	'http://irisiome.com/'),
(185,	'<p>Idée Blanche recrutement est&nbsp;
spécialisé
dans les domaines du recrutement et des prestations d
\'audit, d\'évaluation et de conseil, notre mission est d
\'aider nos clients à réussir en donnant du sens et de la valeur à chacune de nos actions.</p><p><br></p><p>A l\'écoute de votre projet au travers d
\'une compréhension juste de vos besoins, nous dessinons ensemble la stratégie et les outils adaptés afin de garantir des résultats à courts termes. Nous intervenons pour :</p><p><br></p><p>AUDITER et EXPERTISER les pratiques RH de l\'entreprise</p><p>RECRUTER et ACCOMPAGNER les équipes et leur management</p><p>SUIVRE et ACCOMPAGNER l
\'intégration des collaborateurs recrutés</p><p><br></p><p>La réussite d\'une politique RH au sein de l
\'entreprise est liée à la capacité de son équipe dirigeante à être courageuse.</p><p><br></p>',	'https://www.apec.fr/files/live/mounts/images/media_entreprise/716385/logo_ALFID_RECRUTEMENT_CONSEIL_716385_895962.png',	'Idée Blanche',	'http://ideeblanche.fr/'),
(186,	'<p>fdsf</p>',	'bla',	'nouvelle',	'bla'),
(187,	'<p>fdsqfqs</p>',	'fdsqf',	'fdsqf',	'fdsqf'),
(188,	'<p>fdsqfdqsf</p>',	'fdsqfdsq',	'fdsqf',	'fdqsfdsq'),
(189,	'<p>fdsqfdsfqf</p>',	'fdsf',	'fdsqf',	'fdsqfs'),
(190,	'<p>fdsqfdqsfqsf</p>',	'fdsqfdsq',	'fdqfdqs',	'fdsqf'),
(191,	'<p>fdsqfdsq</p>',	'fdsqf',	'fdsqf',	'fdsqf'),
(192,	'<p>fdsqfdsqf</p>',	'fdsqf',	'fdsqfdsqfdsq',	'fdsqfd'),
(193,	'<p>gfdsg</p>',	'nfdsq',	'nouvelle entreprise',	'reza'),
(194,	'<p>test</p>',	'test',	'test',	'test');

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

INSERT INTO `file` (`id`, `content_type`, `filename`, `url`, `uploader_id`, `object_key`)
VALUES (1, 'image/png', 'logo-region-ile-de-france.png',
        'http://127.0.0.1:4566/implicaction/bbee41f1-302b-4cef-a988-5da6ebd391e3png', NULL, NULL),
       (2, 'image/png', '86686823.png', 'http://127.0.0.1:4566/implicaction/c0bc4be0-1ba1-48fa-9973-a7d8a1271829png',
        NULL, NULL),
       (3, 'image/png', 'logo-region-ile-de-france.png',
        'http://127.0.0.1:4566/implicaction/848ead18-cc7b-4c9f-9f4c-af9810ec2aaapng', NULL, NULL),
       (4, 'image/png', 'logo-region-ile-de-france.png',
        'http://127.0.0.1:4566/implicaction/5e7da068-2f0c-4023-80e3-e4c787259e12png', NULL, NULL),
       (5, 'image/png', 'logo-region-ile-de-france.png',
        'http://127.0.0.1:4566/implicaction/74d5d994-e64e-4fa8-b08e-b4bfadae13dcpng', NULL, NULL),
       (6, 'image/png', 'logo-region-ile-de-france.png',
        'http://127.0.0.1:4566/implicaction/39967eca-e89e-4d0d-be79-b71137f7ae31png', NULL, NULL),
       (7, 'image/jpeg', 'leo.jpg',
        'http://127.0.0.1:4566/refonte-implicaction/27540f66-e1b2-456b-b9ba-71c9646d740fjpg', NULL, NULL),
       (8, 'image/jpeg', 'leo.jpg',
        'http://127.0.0.1:4566/refonte-implicaction/0c8bfbb0-dcd4-42e1-a415-a3fd53f7767cjpg', NULL, NULL),
       (9, 'image/png', 'Capture d’écran 2021-11-01 à 15.51.20.png',
        'http://127.0.0.1:4566/refonte-implicaction/466da1e1-0be5-4ba4-b910-616cb50c84bapng', NULL, NULL),
       (10, 'image/png', 'Capture d’écran 2021-11-13 à 15.06.22.png',
        'http://127.0.0.1:4566/refonte-implicaction/c9d5e86a-664a-4ae3-8c7d-ff722ed6ebccpng', NULL, NULL),
       (11, 'image/png', 'Capture d’écran 2021-11-13 à 15.06.22.png',
        'http://127.0.0.1:4566/refonte-implicaction/26f0d285-71ad-4679-893a-9651c1c673a2png', NULL, NULL),
       (12, 'image/png', 'Capture d’écran 2021-11-09 à 02.25.05.png',
        'http://127.0.0.1:4566/refonte-implicaction/98c9b8a6-7d86-4422-8f72-ea0ca88b1d4apng', NULL, NULL),
       (13, 'image/png', 'Capture d’écran 2021-11-13 à 15.06.22.png',
        'http://127.0.0.1:4566/refonte-implicaction/0e63fbc2-ffe6-439f-ac9d-73b2aec18b07png', NULL, NULL),
       (14, 'image/png', 'Capture d’écran 2021-11-13 à 15.06.22.png',
        'http://127.0.0.1:4566/refonte-implicaction/5df078b9-4905-4586-aae3-b9f03c2ffed6png', NULL, NULL),
       (15, 'image/png', 'Capture d’écran 2021-11-06 à 20.05.41.png',
        'http://127.0.0.1:4566/refonte-implicaction/b3886aab-8330-43a2-9468-4a8359c9ef77png', NULL, NULL),
       (16, 'image/png', 'Capture d’écran 2021-11-13 à 15.06.22.png',
        'http://127.0.0.1:4566/refonte-implicaction/d0f854df-ed4b-4e64-a3b6-32edf5b79c74png', NULL, NULL),
       (17, 'image/png', 'Capture d’écran 2021-11-06 à 21.29.58.png',
        'http://127.0.0.1:4566/refonte-implicaction/cb09561c-7d46-40ba-b576-8a545fd1f1dcpng', NULL, NULL),
       (18, 'image/png', 'Capture d’écran 2021-11-07 à 22.38.03.png',
        'http://127.0.0.1:4566/refonte-implicaction/c7b8c9dd-ea4f-4936-9d6c-04036f060e2bpng', NULL, NULL),
       (19, 'image/png', 'Capture d’écran 2021-11-04 à 20.05.03.png',
        'http://127.0.0.1:4566/refonte-implicaction/5ea2d5ee-242d-43a1-8c88-c6fdf97dd1cbpng', NULL, NULL),
       (20, 'image/jpeg', 'chemise-en-or.jpg',
        'http://127.0.0.1:4566/refonte-implicaction/eca116c0-8828-418e-bc9a-c72de7840bddjpg', NULL, NULL),
       (21, 'image/png', 'Capture d’écran 2021-11-13 à 15.06.22.png',
        'http://127.0.0.1:4566/refonte-implicaction/10935eee-80f4-4c52-8143-7c178f94af53png', NULL, NULL),
       (22, 'image/jpeg', 'chemise-en-or.jpg',
        'http://127.0.0.1:4566/refonte-implicaction/b337ad50-54a8-499b-a41e-4be71f17b0fajpg', NULL, NULL),
       (23, 'image/png', 'avatar-ia-group.png',
        'http://127.0.0.1:4566/refonte-implicaction/04ad96b2-7ba9-46aa-b138-4bd1143ae1f3png', NULL, NULL),
       (24, 'image/png', 'Capture d’écran 2021-11-14 à 17.54.51.png',
        'http://127.0.0.1:4566/refonte-implicaction/2280c427-749b-455f-a8b5-e32660320431png', NULL, NULL),
       (25, 'image/png', 'avatar-ia-group.png',
        'http://127.0.0.1:4566/refonte-implicaction/01e92137-d597-4bbb-884a-061580eb39bepng', NULL, NULL),
       (26, 'image/png', 'Capture d’écran 2021-11-14 à 17.54.51.png',
        'http://127.0.0.1:4566/refonte-implicaction/1feebbf4-e5c3-4468-af51-28e1db745cc0png', NULL, NULL),
       (27, 'image/png', 'avatar-ia-group.png',
        'http://127.0.0.1:4566/refonte-implicaction/9079e251-2395-4390-93fc-07036ab67a12png', NULL, NULL),
       (28, 'image/png', 'Capture d’écran 2021-11-13 à 15.06.22.png',
        'http://127.0.0.1:4566/refonte-implicaction/3ae4b530-f4ff-46ac-a78c-4807fff4f1e1png', NULL, NULL),
       (29, 'image/png', 'avatar-ia-group.png',
        'http://127.0.0.1:4566/refonte-implicaction/9466eea2-ce53-4088-a869-cc618c8a9c17png', NULL, NULL),
       (30, 'image/png', '86686823.png',
        'http://127.0.0.1:4566/refonte-implicaction/380bb348-bcd5-4ce0-a6d5-dc1593d4f83apng', NULL, NULL),
       (31, 'image/png', 'Capture d’écran 2021-11-13 à 15.06.22.png',
        'http://127.0.0.1:4566/refonte-implicaction/2767989d-f2b3-4dc3-af18-3cde2237cfb7png', NULL, NULL),
       (32, 'image/png', 'Capture d’écran 2021-11-06 à 21.29.58.png',
        'http://127.0.0.1:4566/refonte-implicaction/53b013e2-2c60-428b-80bb-a0618b6b1a56png', NULL, NULL),
       (33, 'image/png', '86686823.png',
        'http://127.0.0.1:4566/refonte-implicaction/cb329652-8b0d-4ea2-9562-fb14c8d33248png', NULL, NULL),
       (34, 'image/jpeg', 'leo.jpg',
        'http://127.0.0.1:4566/refonte-implicaction/c0871c77-5613-4850-9d9e-0265bdc6bfdbjpg', NULL, NULL),
       (35, 'image/png', 'Capture d’écran 2021-11-06 à 20.05.41.png',
        'http://127.0.0.1:4566/refonte-implicaction/ba3f3a29-6f51-45fa-b4bf-7ff1ad79ddeapng', NULL, NULL),
       (36, 'image/png', 'avatar-ia-group.png',
        'http://127.0.0.1:4566/refonte-implicaction/a1d34111-e043-4bed-a858-28e5b0ccb9ffpng', NULL, NULL),
       (37, 'image/png', 'Capture d’écran 2021-11-06 à 20.05.41.png',
        'http://127.0.0.1:4566/refonte-implicaction/0f06ce7a-2c4d-4510-afcf-3c687b5da34cpng', NULL, NULL),
       (38, 'image/png', 'Capture d’écran 2021-11-13 à 15.06.22.png',
        'http://127.0.0.1:4566/refonte-implicaction/cc8e4587-509f-4d3a-b2c6-b90417c9b007png', NULL, NULL),
       (39, 'image/png', 'Capture d’écran 2021-11-07 à 22.38.03.png',
        'http://127.0.0.1:4566/refonte-implicaction/c5649ad8-46ef-4e54-9bd9-c87264fbdb89png', NULL, NULL),
       (40, 'image/png', 'avatar-ia-group.png',
        'http://127.0.0.1:4566/refonte-implicaction/4ba644cb-6ff5-4fa6-b126-a8e1a5484c4bpng', NULL, NULL),
       (41, 'image/jpeg', 'photo identité.jpg',
        'http://127.0.0.1:4566/refonte-implicaction/d181e095-69eb-4b45-a59b-a21b4e2f9434jpg', NULL, NULL),
       (42, 'image/png', 'avatar.png',
        'http://127.0.0.1:4566/refonte-implicaction/a855fc2f-b042-44f8-82f6-154bcc41af5dpng', NULL, NULL),
       (43, 'image/png', 'avatar.png',
        'http://127.0.0.1:4566/refonte-implicaction/17e03f88-4c10-47fd-b60c-e752acb2803bpng', NULL,
        '17e03f88-4c10-47fd-b60c-e752acb2803bpng'),
       (44, 'image/png', 'avatar.png',
        'http://127.0.0.1:4566/refonte-implicaction/0d9670e1-186e-422b-bd7c-e4039745199fpng', NULL,
        '0d9670e1-186e-422b-bd7c-e4039745199fpng'),
       (45, 'image/png', 'avatar.png',
        'http://127.0.0.1:4566/refonte-implicaction/9652ef92-dcb5-4ea8-8d44-147ab94b2426png', NULL,
        '9652ef92-dcb5-4ea8-8d44-147ab94b2426png'),
       (46, 'image/png', 'avatar.png',
        'http://127.0.0.1:4566/refonte-implicaction/4d1b359f-7d0e-4899-a509-ead0b5c58571png', NULL,
        '4d1b359f-7d0e-4899-a509-ead0b5c58571png'),
       (47, 'image/png', 'avatar.png',
        'http://127.0.0.1:4566/refonte-implicaction/e3605d5d-0e8c-4031-8d63-a3ba96e560bapng', NULL,
        'e3605d5d-0e8c-4031-8d63-a3ba96e560bapng'),
       (48, 'image/png', 'avatar.png',
        'http://127.0.0.1:4566/refonte-implicaction/ebefbaad-1f12-4afb-ad7e-8c2c5c116928png', NULL,
        'ebefbaad-1f12-4afb-ad7e-8c2c5c116928png'),
       (49, 'image/png', 'avatar.png',
        'http://127.0.0.1:4566/refonte-implicaction/3cdd42a0-efe4-43d3-834f-90663404fc32png', NULL,
        '3cdd42a0-efe4-43d3-834f-90663404fc32png'),
       (50, 'image/png', 'avatar.png',
        'http://127.0.0.1:4566/refonte-implicaction/ad09b8e5-3bbd-4469-a03d-d0249e11ba38png', NULL,
        'ad09b8e5-3bbd-4469-a03d-d0249e11ba38png'),
       (51, 'image/png', 'carte stationnement.png',
        'http://127.0.0.1:4566/refonte-implicaction/7e1a417b-7810-451a-ad05-cdde6bf5e2eapng', NULL,
        '7e1a417b-7810-451a-ad05-cdde6bf5e2eapng'),
       (52, 'image/png', 'avatar.png',
        'http://127.0.0.1:4566/refonte-implicaction/5c750922-199a-46f4-a8f5-05303cba0b89png', NULL,
        '5c750922-199a-46f4-a8f5-05303cba0b89png'),
       (55, 'image/png', 'Capture d’écran 2021-11-25 à 20.53.38.png',
        'http://127.0.0.1:4566/refonte-implicaction/9fc3c0b6-e40b-4a49-b245-0984cc7f400cpng', NULL,
        '9fc3c0b6-e40b-4a49-b245-0984cc7f400cpng'),
       (56, 'image/png', 'avatar.png',
        'http://127.0.0.1:4566/refonte-implicaction/0ee8f2c9-8288-4b7f-be07-6d1654569227png', NULL,
        '0ee8f2c9-8288-4b7f-be07-6d1654569227png');

DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`
(
    `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `hibernate_sequence` (`next_val`)
VALUES (57);

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

INSERT INTO `job_application` (`id`, `last_update`, `status`, `job_id`, `user_id`, `archive`)
VALUES (55, '2022-06-03 03:21:49', 'SENT', 165, 1, 0),
       (56, '2022-10-24 09:36:31', 'CHASED', 155, 1, 0);

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

INSERT INTO `job_posting` (`id`, `description`, `keywords`, `location`, `salary`, `title`, `company_id`, `created_at`,
                           `short_description`, `archive`, `contract_type`, `active`, `business_sector`, `valid`,
                           `posted_by`)
VALUES (125, '<h2>L\'entreprise</h2><p>Manpower Conseil Recrutement recrute pour un de ses clients, <strong> un
        technicien de maintenance - électromécanicien de maintenance H / F en CDI</ strong></ p><p><br></ p><p>Notre
        client est un industriel spécialisé dans l\'agroalimentaire reconnue pour la qualité de ses produits et son efficience industrielle.</p><p><br></p><h2>Poste proposé</h2><p>Au sein de l\'
        équipe Maintenance,
        vous avez comme mission principale <strong>la réalisation des travaux de maintenance préventive et corrective des installations de production et ce afin de maintenir l’outil de production en état de fonctionnement,
        de sécurité et performance.</strong></p><p><br></p><p>Pour ce faire, vous assurez&nbsp;
:
</p><ul><li>le réglage des lignes de production.</li><li>l
’entretien de l
’outil de production selon le plan de maintenance préventive.</li><li>le dépannage des installations et des matériels en cas de pannes mécanique, pneumatique et électrique.</li><li>le reporting sous le système GMAO et MES des interventions.</li></ul><p><br></p><p>A ce poste, vous travaillez seul ou en équipe et en étroite collaboration avec les équipes de production et d
’amélioration continue.</p><h2>Conditions de travail</h2><p>Poste en 3*8.</p><p>Travail week end et jours fériés selon impératifs d
\'activité.</p><h2>Profil recherché</h2><p>De formation de BAC STI Génie mécanique, complétée idéalement par un BTS Maintenance Industrielle ou Maintenance Automatismes Industriels, vous justifiez une expérience de 3 ans minimum à un poste d’agent ou de technicien de maintenance industrielle.</p><p><br></p><p><strong>Vous êtes reconnu(e) pour vos compétences techniques en mécanique, électricité et&nbsp;pneumatique. Des notions de soudure TIG représentent un réel plus.</strong></p><p><br></p><p>Polyvalent(e) et autonome, vous faîtes preuve de rigueur et d\'organisation dans l
\'exécution du travail.</p><p>Réactif (ve) et Volontaire, vous savez vous adapter pour faire face aux aléas d\'activité avec calme et réflexion</p><p>Force de proposition, vous n
\'hésitez pas à préconiser de nouvelles solutions dans un esprit d\'amélioration continue.</p><h2>Salaire et accessoires</h2><p>Salaire fixe à négocier selon profil et expérience.</p><p>Avantages d
’entreprise :&nbsp;13ème
mois. Prime annuelle sur objectifs. Participation. Mutuelle.</p><p><br></p>',	'',	'
55100 Verdun',	'A négocier',	'Technicien de maintenance - électromécanicien de maintenance H/F',	145,	NULL,	'<p>Manpower Conseil Recrutement recrute pour un de ses clients,<strong> un technicien de maintenance - électromécanicien de maintenance H/F en CDI</strong></p>',	CONV('
0', 2, 10) + 0,	'INTERIM',	NULL,	'AGROALIMENTAIRE',	CONV('
1', 2, 10) + 0,	NULL),
(135,	'<p>Amazon recherche un(e) Technicien(ne) de maintenance pour rejoindre l
\'équipe de l\'un de ses centres de distribution et y travailler dans un environnement dynamique.</p><p><br></p><p>Le/La Technicien(ne) de maintenance veille au respect des directives et réglementations de sécurité, est chargé(e) de coordonner l
\'entretien préventif des installations dans les délais prévus et à un niveau d\'exigence élevé, et garantit également la haute disponibilité des équipements par la mise en place d
\'actions d\'amélioration continue.</p><ul><li>Faire des meilleures pratiques de santé et de sécurité une priorité sur l
\'ensemble des projets.</li><li>Réaliser des tâches planifiées d\'entretien préventif sur l
\'ensemble des équipements du site.</li><li>Intervenir rapidement en cas de panne, communiquer clairement avec les acteurs concernés et travailler efficacement à la résolution du problème.</li><li>Encourager les pratiques d\'amélioration continue en tirant des leçons des pannes précédentes et en transmettant des avis et des suggestions d
\'amélioration via le responsable hiérarchique.</li><li>Garantir la haute disponibilité des équipements pour nos clients internes.</li><li>Assister les Technicien(ne)s de maintenance confirmé(e)s et se former à leurs côtés.</li></ul><h2>Votre profil</h2><ul><li>Compétences polyvalentes en électricité et en mécanique.</li><li>Connaissance pratique des systèmes de planification d\'entretiens préventifs.</li><li>Expérience en recherche de pannes dans les systèmes d
\'automatisation/équipements de manutention.</li><li>Aptitude à lire et comprendre des schémas mécaniques et électriques.</li><li>Compétences de base en gestion de plates-formes.</li><li>Expérience dans l\'entretien de convoyeurs, de commandes de moteur et d
\'onduleurs.</li><li>Expérience de travail dans le respect des normes et réglementations de santé et de sécurité en vigueur.</li><li>Expérience en gestion de prestataires de sous-traitance.</li><li>Capacité à travailler par équipe et en rotation dans un environnement disponible 24 h/24, 7 jours/7.</li></ul><h2>Qualifications appréciées</h2><ul><li>Expérience en commutation haute tension.</li><li>Expérience en machines de tri.</li><li>Expérience en gestion/configuration de lecteurs de codes-barres.</li><li>Expérience en machines d\'impression-pose.</li></ul><p><br></p><p>Nous attendons ta candidature avec impatience (de préférence en anglais). Poste ouvert aux personnes en situation de handicap.</p><p><br></p>',	'',	'Montélimar, ARA',	'A négocier',	'Technicien de Maintenance (H/F)',	155,	'
2021-11-15 06:01:39
',	'<p>Amazon recherche un(e) Technicien(ne) de maintenance pour rejoindre l
\'équipe de l\'un de ses centres de distribution et y travailler dans un environnement dynamique.</p>',	CONV('
0', 2, 10) + 0,	'CDI',	NULL,	'ASSURANCE',	CONV('
1', 2, 10) + 0,	NULL),
(145,	'<h2>Description du poste</h2><p>La société GREENMAT spécialisée dans la vente et l
’entretien de matériels de parcs et jardins depuis plus de 25 ans recherche un mécanicien (H/F) pour renforcer son équipe de SAV. L
’entreprise est concessionnaire des marques suivantes : Iseki, Stihl, Reform, etc
… .</p><p><br></p><p>Le mécanicien sera chargé de l
’entretien et la réparation du matériel de nos clients. Le technicien sera amené à travailler dans la zone d
’activité de l
’entreprise ou sur les lieux de panne des matériels. Le technicien sera chargé d
’assurer une relation de confiance entre le client et le Service Après-Vente de la concession. Les postes sont à pouvoir en CDI pour nos magasins situés à Chailly-en-Bière (77930) </p><p><br></p><h2>Qualités Requises :</h2><ul><li>Connaissance des moteurs essences et diesel</li><li>Connaissance en hydraulique</li><li>Connaissance en électricité</li><li>Savoir manipuler les principaux matériels</li><li>Goût du relationnel</li><li>Autonome, Responsable</li><li>Méticuleux, méthodique</li><li>S
’adapter et avoir l
’envie de se former aux nouvelles technologies et aux nouveaux matériels</li><li>Diplomate, être à l
’écoute des problématiques des clients</li></ul>',	'',	'Chailly-en-Bière 77930
',	'2500
',	'Spécialiste vente',	165,	NULL,	'<p>La société GREENMAT spécialisée dans la vente et l
’entretien de matériels de parcs et jardins depuis plus de 25 ans recherche un mécanicien (H/F) pour renforcer son équipe de SAV. L
’entreprise est concessionnaire des marques suivantes : Iseki, Stihl, Reform, etc
…dsqdf</p>',	CONV('
0', 2, 10) + 0,	'CDD',	NULL,	'BANQUE',	CONV('
1', 2, 10) + 0,	NULL),
(155,	'<h2>Descriptif du poste</h2><p>Au titre d
’ingénieur(e) de production, vous êtes responsable d
’un atelier de fabrication. Vous participez au respect des contraintes de coûts, de qualité, de délais et de la réglementation dans la production des systèmes. Vous supervisez des lignes de production et encadrez une équipe. Vous devez mettre en place et contrôler les processus de fabrication, et devez trouver des solutions pour atteindre les objectifs fixés.</p><p><br></p><p>Vous serez ainsi amené(e) à :</p><ul><li>&nbsp;Organiser
un atelier et une ligne de production, suivre les outils de productions et de métrologie (Calibration, maintenance).</li><li>Organiser et suivre les approvisionnements.</li><li>Assurer le suivi de la sous-traitance pour la conception électronique ou mécanique.</li><li>Suivre le bon déroulement de la fabrication</li><li>Optimiser les processus de fabrication et de l
’appareil de production</li><li>Encadrer une équipe de production</li><li>Gérer l
’activité de production et assurer le reporting hebdomadaire.</li><li>Faire remonter les problèmes de production à la direction technique et proposer de nouveaux développements dans leur passage en production (Rédaction documentation interne, instructions de travail).</li><li><br></li></ul><p>Vous évoluez au contact de collaborateurs multidisciplinaires et dans un contexte international. Vous serez amené(e) à communiquer régulièrement en Anglais.</p><h2>Profil recherché</h2><p>De formation niveau Bac + 4 ou 5 (écoles d
’ingénieurs généralistes)&nbsp;ou
technicien supérieur justifiant d
’une solide expérience, vous disposez d
’une expérience dans l
’industrie.</p><p><br></p><p>Vous êtes polyvalent avec une volonté de maîtriser à la fois des compétences techniques et managériales. Vous êtes rigoureux et méthodique avec des qualités organisationnelles et relationnelles qui vous permettent de gérer le travail des équipes et les relations avec les interlocuteurs internes et externes à l
\'entreprise.</p><p><br></p><p>Vous devrez fait preuve de dynamisme et de réactivité pour faire face aux situations imprévues.</p><p><br></p><p>Vous êtes autonome et doté(e) d’un esprit d’équipe, vous avez une bonne habilité à communiquer. Vous êtes force de propositions dans les tâches qui vous sont confiées.</p><h2>Entreprise</h2><p>Irisiôme est une entreprise de l’agglomération bordelaise créée en 2015, qui a conçu et aujourd’hui commercialise un dispositif médical de nouvelle génération visant un marché considérable en France, en Europe mais aussi dans le reste du monde.</p><p>Ce dispositif s’appuie sur des lasers innovants à base de fibres optiques et délivrant des impulsions ultracourtes.</p><p>Le marché visé est celui de la dermato-esthétique avec des applications bien implantées comme l’épilation laser ou promises à de belles croissance comme le détatouage.</p><p>Notre promesse de proposer des soins moins douloureux et plus rapides nous a d’ores et déjà permis de gagner la confiance de professionnels de santé de renommée internationale.</p><p><br></p><p><br></p><p><br></p>',	'',	'Pessac - 33',	'A partir de 37 k€ brut annuel',	'Ingénieur de production F/H',	175,	'2021-11-15 06:18:49',	'<p>Au titre d’ingénieur(e) de production, vous êtes responsable d’un atelier de fabrication. Vous participez au respect des contraintes de coûts, de qualité, de délais et de la réglementation dans la production des systèmes. Vous supervisez des lignes de production et encadrez une équipe. Vous devez mettre en place et contrôler les processus de fabrication, et devez trouver des solutions pour atteindre les objectifs fixés.</p>',	CONV('0', 2, 10) + 0,	'CDI',	NULL,	'BTP',	CONV('1', 2, 10) + 0,	NULL),
(165,	'<h2>Descriptif du poste</h2><p>Idée Blanche recrutement recherche pour un de ses clients, groupe industriel spécialiste des procédés spéciaux et de l’usinage dans le domaine aéronautique/énergie/automobile, un opérateur commande numérique.</p><h2>Votre mission</h2><p>Intégré au sein de l’équipe production,&nbsp;vos missions seront les suivantes&nbsp;:</p><ul><li>Prendre connaissance de l’ensemble des documents fournis&nbsp;: fiche suiveuse, plan, gamme d’usinage…</li><li>Monter et régler les outillages de fabrication</li><li>Monter / démonter les pièces</li><li>Connaitre / analyser les arrêts machines</li><li>Savoir choisir et utiliser les moyens de contrôles nécessaires.</li><li>Contrôler la production</li></ul><p>Réglage/ usinage / changement de série</p><ul><li>Réglage du moyen de production dans le respect des règles (Dossier d’instruction / Fiche d’instruction)&nbsp;:</li><li>Maitriser&nbsp;: Centrage, dégauchissage.</li><li>Assurer le suivi et la fréquence de Changement d’outils</li><li>Compréhension et lecture d’un programme (Connaitre les codes «&nbsp;iso&nbsp;» dans les programmes).</li><li>Assurer les modifications de programmation&nbsp;après aval du responsable. </li></ul><p>Gestion d’anomalies</p><ul><li>Gestion des Non-Conformités&nbsp;:</li><li>Identifier&nbsp;: repérer et étiqueter les Non-Conformités suivant les règles qualités</li><li>Trier&nbsp;: vérifier la conformité des pièces précédentes</li><li>Corriger pour supprimer la cause du défaut</li><li>Ouverture/participation au conseil de décision (en cas de non-conformité)</li><li>Appréhender le risque sécurité</li><li>Gérer les arrêts machines / rendre compte au responsable direct.</li></ul><h2>Profil recherché</h2><p>De formation supérieure Bac+2/+3 en mécanique, vous justifiez d\'une expérience professionnelle significative dans une fonction similaire au sein d
’un groupe industriel reconnu.</p><h2>Entreprise</h2><p>Idée Blanche recrutement est&nbsp;spécialisé dans les domaines du recrutement et des prestations d
\'audit, d\'évaluation et de conseil, notre mission est d
\'aider nos clients à réussir en donnant du sens et de la valeur à chacune de nos actions.</p><p><br></p><p>A l\'écoute de votre projet au travers d
\'une compréhension juste de vos besoins, nous dessinons ensemble la stratégie et les outils adaptés afin de garantir des résultats à courts termes. Nous intervenons pour :</p><p><br></p><p>AUDITER et EXPERTISER les pratiques RH de l\'entreprise</p><p>RECRUTER et ACCOMPAGNER les équipes et leur management</p><p>SUIVRE et ACCOMPAGNER l
\'intégration des collaborateurs recrutés</p><p><br></p><p>La réussite d\'une politique RH au sein de l
\'entreprise est liée à la capacité de son équipe dirigeante à être courageuse.</p><p><br></p>',	'',	'Marmande - 47',	'A partir de 25 k€ brut annuel',	'OPÉRATEUR COMMANDE NUMÉRIQUE F/H',	185,	'2021-11-15 06:27:28',	'<p>Idée Blanche recrutement recherche pour un de ses clients, groupe industriel spécialiste des procédés spéciaux et de l’usinage dans le domaine aéronautique/énergie/automobile, un opérateur commande numérique.</p>',	CONV('0', 2, 10) + 0,	'CDI',	NULL,	'CHIMIE',	CONV('1', 2, 10) + 0,	NULL);

DROP TABLE IF EXISTS `job_seeker`;
CREATE TABLE `job_seeker`
(
    `user_id` bigint NOT NULL,
    PRIMARY KEY (`user_id`),
    CONSTRAINT `FKt6no6vfq2vtvqbwlqyik9hyef` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO `job_seeker` (`user_id`)
VALUES (1),
       (2),
       (3),
       (4),
       (5),
       (15),
       (25),
       (35),
       (43),
       (55);

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

INSERT INTO `notification` (`id`, `date`, `message`, `is_read`, `is_sent`, `title`, `type`)
VALUES (7, '2021-12-12 18:46:01', 'Une nouvelle offre a été publiée sur le site implic\'action : <a href=\"http://localhost:8080//api/job-postings/167\">valide moi stp</a> est désormais actif.',	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	'[Implic\'action] Une nouvelle offre a été publiée',	'JOB_ACTIVATION'),
(8,	'2021-12-12 18:48:57',	'Une nouvelle offre a été publiée sur le site implic\'action : <a href=\"http://localhost:8080//api/job-postings/170\">serais je notifié ?</a> est désormais actif.',	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	'[Implic\'action] Une nouvelle offre a été publiée',	'JOB_ACTIVATION'),
(9,	'2021-12-12 18:50:01',	'Une nouvelle offre a été publiée sur le site implic\'action : <a href=\"http://localhost:8080/api/job-postings/171\">j\'ai tjs un slash en trop ?</a>.',	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	'[Implic\'action] Une nouvelle offre a été publiée',	'JOB_ACTIVATION'),
(10,	'2021-12-12 18:52:38',	'Une nouvelle offre a été publiée sur le site implic\'action : <a href=\"http://localhost:8080/jobs/168\">valide moi stp 2</a>.',	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	'[Implic\'action] Une nouvelle offre a été publiée',	'JOB_ACTIVATION'),
(12,	'2021-12-12 19:20:12',	'Félicitation, votre compte <a href=\"http://localhost:8080/auth/login\">implicaction</a> est désormais actif.',	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	'[Implicaction] Activation de votre compte',	'USER_ACTIVATION'),
(13,	'2021-12-12 19:20:45',	'Une nouvelle offre a été publiée sur le site implic\'action : <a href=\"http://localhost:8080/jobs/172\">pas d\'exception</a>.',	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	'[Implic\'action] Une nouvelle offre a été publiée',	'POST_CREATION'),
(18,	'2021-12-12 19:40:53',	'Une nouvelle discussion a été publiée sur le site implic\'action : <a href=\"http://localhost:8080/discussions/20\">j\'envoie un message</a>.',	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	'[Implic\'action] Une nouvelle discussion a été publiée',	'POST_CREATION'),
(19,	'2021-12-12 20:02:00',	'matthieu a publié un commentaire sur le site implic\'action : <a href=\"http://localhost:8080/discussions/20\">j\'envoie un message</a>.',	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	'[Implic\'action] Un nouveau commentaire a été publié',	'COMMENT_ADD'),
(20,	'2021-12-12 20:04:34',	'matthieu a publié un commentaire sur le site implic\'action : <a href=\"http://localhost:8080/discussions/20\">j\'envoie un message</a>.',	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	'[Implic\'action] Un nouveau commentaire a été publié',	'COMMENT_ADD'),
(21,	'2021-12-12 20:05:35',	'matthieu a publié un commentaire sur le site implic\'action : <a href=\"http://localhost:8080/discussions/20\">j\'envoie un message</a>.',	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	'[Implic\'action] Un nouveau commentaire a été publié',	'COMMENT_ADD'),
(22,	'2021-12-12 20:06:37',	'matthieu a publié un commentaire sur le site implic\'action : <a href=\"http://localhost:8080/discussions/20\">j\'envoie un message</a>.',	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	'[Implic\'action] Un nouveau commentaire a été publié',	'COMMENT_ADD'),
(23,	'2021-12-12 20:09:49',	'matthieu a publié un commentaire sur le site implic\'action : <a href=\"http://localhost:8080/discussions/20\">j\'envoie un message</a>.',	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	'[Implic\'action] Un nouveau commentaire a été publié',	'COMMENT_ADD'),
(24,	'2021-12-12 20:10:42',	'matthieu a publié un commentaire sur le site implic\'action : <a href=\"http://localhost:8080/discussions/20\">j\'envoie un message</a>.',	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	'[Implic\'action] Un nouveau commentaire a été publié',	'COMMENT_ADD'),
(25,	'2021-12-12 20:14:24',	'matthieu a publié un commentaire sur le site implic\'action : <a href=\"http://localhost:8080/discussions/20\">j\'envoie un message</a>.',	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	'[Implic\'action] Un nouveau commentaire a été publié',	'COMMENT_ADD'),
(26,	'2021-12-12 21:02:25',	'matthieu a publié un commentaire sur le site implic\'action : <a href=\"http://localhost:8080/discussions/20\">j\'envoie un message</a>.',	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	'[Implic\'action] Un nouveau commentaire a été publié',	'COMMENT_ADD'),
(27,	'2021-12-12 21:03:04',	'matthieu a publié un commentaire sur le site implic\'action : <a href=\"http://localhost:8080/discussions/20\">j\'envoie un message</a>.',	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	'[Implic\'action] Un nouveau commentaire a été publié',	'COMMENT_ADD'),
(28,	'2021-12-12 21:06:37',	'matthieu a publié un commentaire sur le site implic\'action : <a href=\"http://localhost:8080/discussions/20\">j\'envoie un message</a>.',	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	'[Implic\'action] Un nouveau commentaire a été publié',	'COMMENT_ADD'),
(29,	'2021-12-12 21:21:31',	'Félicitation, votre compte <a href=\"http://localhost:8080/auth/login\">implicaction</a> est désormais actif.',	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	'[Implicaction] Activation de votre compte',	'USER_ACTIVATION'),
(30,	'2021-12-13 00:55:03',	'Une nouvelle offre a été publiée sur le site implic\'action : <a href=\"http://localhost:8080/jobs/173\">une offre à valider</a>.',	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	'[Implic\'action] Une nouvelle offre a été publiée',	'JOB_ACTIVATION'),
(31,	'2022-01-01 02:13:21',	'Une nouvelle offre a été publiée sur le site implic\'action : <a href=\"http://localhost:8080/jobs/174\">test</a>.',	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	'[Implic\'action] Une nouvelle offre a été publiée',	'JOB_ACTIVATION'),
(32,	'2022-01-01 02:13:41',	'Une nouvelle offre a été publiée sur le site implic\'action : <a href=\"http://localhost:8080/jobs/175\">test</a>.',	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	'[Implic\'action] Une nouvelle offre a été publiée',	'JOB_ACTIVATION'),
(33,	'2022-01-01 02:14:00',	'Une nouvelle offre a été publiée sur le site implic\'action : <a href=\"http://localhost:8080/jobs/176\">test</a>.',	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	'[Implic\'action] Une nouvelle offre a été publiée',	'JOB_ACTIVATION'),
(34,	'2022-10-24 23:33:26',	'Une nouvelle offre a été publiée sur le site implic\'action : <a href=\"http://localhost:8080/jobs/166\">fhgf</a>.',	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	'[Implic\'action] Une nouvelle offre a été publiée',	'JOB_ACTIVATION');

DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `description` longtext,
  `url` varchar(255) DEFAULT NULL,
  `vote_count` int DEFAULT NULL,
  `group_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK72mt33dhhs48hf9gcqrq4fxte` (`user_id`),
  KEY `FKmlnoks6ujgl9ynt53af0bx4pj` (`group_id`),
  CONSTRAINT `FK72mt33dhhs48hf9gcqrq4fxte` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKmlnoks6ujgl9ynt53af0bx4pj` FOREIGN KEY (`group_id`) REFERENCES `ia_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `post` (`id`, `created_at`, `description`, `url`, `vote_count`, `group_id`, `user_id`, `name`) VALUES
(15,	'2021-11-25 16:02:17',	'<p>L\'Association Implic\'Action remercie le général Pierre de Villiers pour sa superbe conférence que notre association a pu</p><p>co-organiser hier soir avec l\'Université Catholique d\'Angers.</p><p>\"L\'exercice de l\'autorité ou comment être un chef dans un monde complexe\"</p><p><br></p><p>un sujet d\'actualité et des conseils avisés et pertinents que nos dirigeants pourraient suivre.</p><p>Les étudiants de l\'UCO en nombre assez important ont énormément apprécié si on en juge par les questions qui ont été posées.</p><p>Un grand merci à La France Mutualiste notre fidèle partenaire.</p><p>L\'ANOCR ( Association Nationale des Officiers de Carrière en Retraite et son président 49 Didier SIMON également délégué regional du groupe Implic\'Action 49</p><p>Le président de notre association Hubert Tregou a pu pendant quelques minutes présenter l\'association et ses actions bénévoles d\'entraide à la reconversion des personnels de la Défense</p><p><br></p><p><img src=\"data:image/jpeg;base64,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2xFYaDavmC6v9iBYgLPohd6sG3JMOHGLtfaPIeuYENp2rG+5SZOekXZCpgIs+SdzsWKXXHeDFXzRRitXNi6iRknDCNDohVypwB2nvHwtA5thBAB5PEapRKj/AKg755DIbXE2++JiY1geqiqTS8v/ALF9S+tMwkENir1UqOSNsOY0NsuFGwigiXLfWZGqSr7VUDDuxoSWfdJbJh3tmL8TMpFFXaqmJGTzhsmIM1mZcRLFdo7iyHR4qfWk0rX/AJANm3qPYwZVXhPupzAhL8J8qglOp+KglALuqUu6L7wA0VNKhv0qalFdqiBSWQ1DHsTn87EVsHhU0AeifkhUpd1mYkpT4htEDsQBdG4AvBmGgX0m13slEpCu0/H4QAKNSyoL7ERsDKWNZIVRDyTbi3GfUKaS1IQD68qfm4IGgHybvHmhX/8AiBznf//aAAwDAQACAAMAAAAQ8888888888888888888888888888888888888888888888888888888444EY88w80cUw4cEk00UME8400w4cM4408888888888888888880M8sM8MMkkMso0888s4k4s88sMss8gcsc88888888888888888U8sU0sscEss0o4MAA4AMIkssEEEgUY8sAoc8888888888888488M88888420/V94y460900/8888888c888888884888888888o40888888qeXQJ+1HA8XXv73MW88888888888888o8888888884YoPQxxz0qwn330M9glD1/luVc08888888888888o8888888886eNZqmwV8YA6aucX/Wt1ngvZB+U8888888888888o888888888oisHO3B18k/x2X+n/cqCVC5lYw88888888888888o8888888884y2aVhMX8dnUopqe6Qsr9y4isx08888888888888o888888888+ccAvRAYZ43+PxgeCsnHKfCtFXc8888888888888o888888888u58VNMuC9blHfEpmaTD8/fCBRF08888888888888o888888888rQHjVjr84XjSfyiLgUIKeTMqFB8888888k888888o888888888vF0vcimUWgike8+Q84S2UxFEXN888884+Pf88888o88888888848sQqD18t8lXMdbv4gxImOUKDu888884d9f88888o8888888888T9twVS8mQLdBsGcsXk2jn8iuW88888hmHR+8888o888888888og0umnM8RQG6Yy88o1KLv3PrlQ04yyyf2WvC0888o888888888ouSm5ea88cFNj1888Xt1/OEg1/fv8A/wD+ubxikLzzyjzzzzzzzzzz7RQAx7zyetzzzzyyvKadpPJ3/wD/AKfd/uagG+eGtKPPPPPPPPPKCrRxHdPMAYdfPPPKTyLsrqvv/wD/ADnh9qnkWYDntXo888888888816iviU8s+P888884EJ5TlL/ANVbT30zV2FCYyqjlaPPPPPPPPPPOsw9N466/wCXnxcoKoZqwGp6ZrnNmNpWPz1qxfiLAjzzzzzzzzzrkgtl8p6P98KivwDl9WlcPyyU+6mM0qZf60cXkxYvzzzzzzzzy9xPmM2iXe3uiGU6UvP5EQYFHsNEnXqcsV6xANkMyzzzzzzzzzysXMwHLuqJLaqevptx2BBRwPg6AD3IpLwhtnC5eRt/zzzzzzzzy9ftXf8AfhXtQqStel4e7omWfKbEM/GH6qbdxiXXRaX888888888/RzYvx5saIKjMdO7d3ZSTVU2AzWviCvnLqReEDYY+8888888885iC12pckU11nxCeKvUsr79eCvKWZ89YhSbb2E1xze8888888885s+/CiaxbYjUlJnBqXhf6/UK/Q3pqIEG1Lv4wa2Vf888888888rgYYO+yUosh/Hmb6J8UYpMyc8SZiBPFJpqqSLTrj8888888888+5jsl9U638LzpiufWrU89+KyAGzIOgcttw80wNPNe888888888uWFFAJvJaHYH+Aj6TW+wI2K61N2Zto+7TZspqUtJ0888888888pPHYIRRopGAJdmBNgZmUbNSsPe1yY/As3Y8LdGVO9888888888ot9py1T30XO23hGMN63hYVGs09tRqZYLx64vEkk+Y8888888888uzUnMwwCBBwKaRuTdlH4sY0JVXjG7O6x8UdKS4Dz888888888or8x2BMdxa4DEUoLzjbHwcaYGP0SW6gJM+FGtX8gV8888888888vwEG/lqBwExrZembmCdCUzSN3+7N30SEAbz+j+qB88888888880Adxcg7AUo5l1epYXF2jsn4XwAdRd3rqvAk4R5qJ8888888888iJXOm6TaYAlhc+FFveuNh+DdCwzdR3nB+aZwaAlB8888888888t9IqyU0mUozI+u5gcO6W+W0lmbNBt5mjyM8RnXjG8ww08888888wkQwYoosgk0oUwAEIIckM8888MccMs888sYc888sss8888888404408088w88048888888888884kAsQQc0QIAM8MIg888888884csscMsscssMMMs88888888888cc88cMcssMcs8MMc88888888888888888888888888888888888888888888888888888/8QAIhEBAQEAAwACAwEBAQEAAAAAAQARECExIEEwUWFAUHFw/9oACAEDAQE/EP8A6xttvy3/AJTwT/xNOK+JLgUe/Mn3/hkdcNjXd2OI+/lsX3y8ZZ1FmzE/692C0kPbax/feuc7szwX38D2ZmWQ+54Y/wBQEsngH2Qi3Pbw3S3wyL7tKOJM4y7cdRwx/q6EiBJE3hwztwTLPuWI9kMDtj2EYWJk7Lhj/UIeX8r+MfplPq3ycP6XSfJNngvUsZBlo4ZYymcHA/0+iYLl0+o/m0wFp1mg5W3SyFncecsnsJlhmPd/GX9XuOB/pPYPBwt/otPuExc7bS/nAe76jjYhSSGynAssOcD5+xnuhjn+H6tiJeb5aluZa2W98HGJki6c4W3su/wvKxEktd/w7yNsu+X9zI9dTD38FnDDvA8T8zreSbA6kz/D/wCxA2Z1ZeuEsxs49j8DkXfHePf4b1JpGrI/f+DeHif5jU85YHGQfIWHO5Upj3+BxCwkNI6d2H58IXh1JgyAfA9n18zkbPdkfg8ztt0werU7KHU9+pX5VoziT04UPwJvcWQ78T2WuCzvCR8M+CbwxHbWQvBBls/kZbAu2Xe229fDbtHD47k6l/UO8feT0g/Dm2WcLq9cRn5N2rdqw95xnqe3jG1a4MI54GDueMssbOM5P1FB+Chp1eoafk1x1blw7dqXH8XT6gf1d2NjI/qxkmILD3yyP/JX9WzkE/aSD7smaJz2J1YbAXRAsOL3+LwsR1fyv5X8p/VP6ob6v4X8o/VP6L+F/K/hfwv4T+q6sLXtJeRfyv5X8rB5dZ44F1PDwNcl2fmeEeTbyl6xPnCxbO/bAN4eHogXW/WTg3qPJ84/F4jh4YgWsBdy0D8zwvEyw22Q6SaSdmUOF5rLtdmydbRDpsQtQXux2vAeDl+bxZ8DI3dP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\"></p>',	NULL,	2,	33,	55,	'Conférence Général de de Villiers à l\'UCO d\'Angers le 23 noembre'),
(25,	'2021-12-11 16:37:31',	'<p>Bienvenue à tout le monde ^^ </p>',	NULL,	1,	45,	3,	'Petite publication pour souhaiter la bienvenue à tout le réseau d\'implic\'action'),
(26,	'2022-04-01 12:07:35',	'string',	'string',	0,	33,	1,	'string');

DROP TABLE IF EXISTS `recruiter`;
CREATE TABLE `recruiter` (
  `user_id` bigint NOT NULL,
  `company_id` bigint DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `FKe5tll0cw7cnohojpxb8qjcr5y` (`company_id`),
  CONSTRAINT `FK63kq3uyt2p3i32pjo1nfin63a` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKe5tll0cw7cnohojpxb8qjcr5y` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


DROP TABLE IF EXISTS `refresh_token`;
CREATE TABLE `refresh_token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `creation_date` datetime DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO `refresh_token` (`id`, `creation_date`, `token`) VALUES
(45,	'2021-10-02 02:25:49',	'881240e9-d84b-42b3-aeb9-feeb70b15aaf'),
(55,	'2021-10-02 08:33:04',	'6a5a968f-ea3c-477d-b6f6-9ccb2a23a50e'),
(85,	'2021-10-02 12:19:46',	'7425ffc9-66fa-42da-8215-2697a49ff0cb'),
(95,	'2021-10-02 21:49:39',	'8b8156d8-1543-4ea2-ae31-9502215da435'),
(135,	'2021-10-03 16:32:45',	'69eb309c-4eb8-464b-92d1-067ee2cd6591'),
(165,	'2021-10-04 00:28:06',	'53e061d9-ea20-4d8b-b65e-9f519f9b3d77'),
(195,	'2021-10-04 15:51:28',	'9a48ab4a-b6e0-438e-9f36-ab92229599d8'),
(235,	'2021-10-05 00:22:48',	'd96dce4e-cd8a-410a-b303-4218764f7475'),
(245,	'2021-10-05 08:59:37',	'4538ffa2-5efc-4d36-9be4-80863067db32'),
(265,	'2021-10-05 17:27:03',	'e3f47e31-39e6-4634-8750-cef4a98b5da3'),
(275,	'2021-10-05 18:49:26',	'991d8030-99cd-4a2b-9334-b70f63e426d2'),
(305,	'2021-10-05 19:05:25',	'90151942-e420-4119-9ff6-3bea86336688'),
(325,	'2021-10-05 19:36:32',	'acd1dca1-8a56-4df6-b2f1-6be68af7e1e6'),
(355,	'2021-10-05 21:19:27',	'664f3586-c601-4b9b-b003-e7f03f4d26c7'),
(375,	'2021-10-06 09:59:49',	'73a19e5e-2f51-4934-a099-ccb8b2d0f6e8'),
(385,	'2021-10-09 00:11:32',	'9330cbe6-fa55-4431-a014-18973ae3b23e'),
(395,	'2021-10-09 00:54:13',	'5c7db655-369b-4b64-9991-69e1b8d2b83c'),
(405,	'2021-10-09 06:56:29',	'f2b72c3d-0079-4ec4-bf08-78513250750f'),
(415,	'2021-10-09 08:21:45',	'9882fa88-b5a7-46f5-9a08-556a6f7422e5'),
(425,	'2021-10-09 08:59:30',	'd90b5185-b291-4727-b529-732497c06a0d'),
(435,	'2021-10-09 10:59:16',	'dd9902b1-bf19-4f1b-a744-d17b7ecfe1af'),
(445,	'2021-10-09 11:13:48',	'33a53da4-ba80-494a-a47c-215a9a482a7f'),
(455,	'2021-10-09 12:51:09',	'53e063a0-37c8-44f4-9db4-ba5ba468e0f7'),
(465,	'2021-10-10 01:54:27',	'db0787a5-9f6f-416a-9bb8-06ba5cdfe4ef'),
(475,	'2021-10-10 16:19:19',	'537a535a-3997-4851-8fb2-8ad30cd8311e'),
(485,	'2021-10-10 16:19:59',	'935f7005-6941-4e27-bc6c-a926ac505ec0'),
(495,	'2021-10-10 16:20:09',	'93a55ab0-0888-4767-9d30-2403d73c03d6'),
(505,	'2021-10-10 16:21:04',	'0c9389aa-9961-4fb8-8bbf-a30484819c67'),
(515,	'2021-10-10 17:07:17',	'e6ee8a17-0206-40c4-84fd-af39fdfa78de'),
(525,	'2021-10-11 08:17:43',	'b243082b-c568-439e-9cf2-fb0b32b5ffa7'),
(535,	'2021-10-11 08:48:55',	'82b32c5e-bfbf-4bb6-8a73-1508a75ac96a'),
(545,	'2021-10-11 09:11:04',	'e971f424-6e63-4340-a9c7-1e5994674a5a'),
(555,	'2021-10-11 11:34:09',	'd9e0f7db-f251-4766-b334-00350e7d5ac8'),
(565,	'2021-10-11 11:35:04',	'bd6dcadd-a931-46eb-aa34-8dbf693db5ee'),
(575,	'2021-10-11 14:14:15',	'8584deb4-0461-45ae-8a2c-111b33c8d24b'),
(585,	'2021-10-11 17:18:44',	'8395bc05-66c4-43eb-986f-7924c7a149d8'),
(595,	'2021-10-13 21:01:39',	'b24b3dac-f18a-42bf-9479-5e25218b94bd'),
(605,	'2021-10-13 21:01:59',	'070797bc-42e6-4d34-a2c5-081fc2bd1056'),
(615,	'2021-10-13 21:02:05',	'2fa47c45-5e4e-49ce-91e8-8015fc266f80'),
(625,	'2021-10-15 15:42:45',	'32c115a9-1994-4e5b-9370-9d7eaee90480'),
(635,	'2021-10-15 15:51:00',	'3aa60bf1-3077-4f7b-9e08-563fd585e606'),
(645,	'2021-10-15 15:59:25',	'76427d51-b4db-4e8c-a060-06f26098790b'),
(655,	'2021-10-15 18:37:14',	'b8ae38c7-cdec-431c-a3aa-a3c6e319ce4e'),
(665,	'2021-10-17 18:11:04',	'1981e24d-7bf4-496a-87a5-393df2cbffec'),
(675,	'2021-10-18 22:28:18',	'0398a7b1-ed43-48e3-8471-36fc79137d61'),
(685,	'2021-10-19 18:16:41',	'263e68a6-fa5f-4451-993a-4033695920c5'),
(695,	'2021-10-19 19:09:23',	'dea83861-0c5f-4df9-840c-2b5407312fce'),
(705,	'2021-10-19 19:10:33',	'9299cb33-90d0-4225-9b83-85e4613f9e25'),
(715,	'2021-10-19 19:15:32',	'9caa6ded-4da0-4fd7-9336-4a44fdbf8f55'),
(725,	'2021-10-19 19:15:53',	'e57b12da-737c-47cc-9724-7ed2548d50de'),
(735,	'2021-10-19 19:16:45',	'928af07e-6982-43c7-9193-72685d7eb38f'),
(745,	'2021-10-19 19:21:56',	'8421ef0f-25d3-4596-b750-d7c35a8e067d'),
(755,	'2021-10-19 19:39:00',	'810ef5d1-9e0d-4ae7-950f-e20f74931c78'),
(765,	'2021-10-20 14:34:09',	'd2c26a5d-95d4-47d7-b81e-af04187cbb4a'),
(775,	'2021-10-23 12:31:15',	'3cb9c44c-1fe8-4efa-8fe7-41585efeecfe'),
(785,	'2021-11-03 11:19:57',	'ce7b4d63-88e3-4c54-adcd-b9c1510d7f19'),
(786,	'2021-11-04 21:59:36',	'e752b222-a4ef-4d2b-aacd-6311a3761153'),
(787,	'2021-11-04 21:59:59',	'aad6ab5d-0509-449e-8560-22dbe959adeb'),
(788,	'2021-11-06 17:41:32',	'4b4ef499-b34f-4d51-9373-4ef4c074d38c'),
(789,	'2021-11-06 17:53:55',	'27845e74-f5d4-4984-bf2e-d6e94705a4c3'),
(790,	'2021-11-06 17:54:11',	'552bccee-3556-42eb-ad97-2869ad8954e2'),
(791,	'2021-11-06 17:57:23',	'717f5741-1ca7-49fe-b500-64cb3ff5b6e6'),
(792,	'2021-11-07 02:30:42',	'29b0a9f3-f04e-4ac8-b4ed-30dde975f500'),
(793,	'2021-11-08 18:09:30',	'7e9cb45e-6377-4a63-87bb-8963c23ae6f8'),
(794,	'2021-11-09 02:15:37',	'7fadd03e-1ad5-46b7-a871-be0dd36548b6'),
(795,	'2021-11-09 02:39:31',	'020ca8b2-ffac-42f9-8ffc-d420ec90a542'),
(796,	'2021-11-09 04:36:05',	'798ca38b-1872-4de3-b8a0-fe5c0818567f'),
(797,	'2021-11-09 04:36:44',	'87d36909-eb56-4222-902d-bff6032a43bc'),
(798,	'2021-11-09 12:35:46',	'5f4b422f-a85c-44f2-a7e9-9dd24686a934'),
(799,	'2021-11-09 17:59:42',	'a63973a4-c1bb-42cc-bd12-3286c0bec422'),
(800,	'2021-11-09 21:27:08',	'51e0b7c0-d906-4372-ac57-7a49709b7c9f'),
(801,	'2021-11-11 19:29:17',	'67299d99-b8b0-415d-818d-5ddf6685ab6c'),
(802,	'2021-11-11 19:57:14',	'd06e4eea-7580-4d77-b890-b64f5dd094e2'),
(803,	'2021-11-12 04:10:01',	'f8989c11-c082-45b0-82b3-204a258a72e6'),
(804,	'2021-11-12 04:34:25',	'dadc1135-33d2-47bc-93f0-a28010187e95'),
(805,	'2021-11-12 05:16:36',	'c90e54df-77d0-4ffd-8e75-6da38c2ee7ed'),
(806,	'2021-11-12 07:08:01',	'b8f864b6-18d7-4f16-b7b9-e1d21f9b20d8'),
(807,	'2021-11-13 08:03:19',	'98b3afa8-b3d0-4bce-ae97-29858d80aa67'),
(808,	'2021-11-13 21:43:42',	'96032611-48f7-4b31-bd64-b6926f6fdda4'),
(809,	'2021-11-13 21:48:21',	'c51e7e10-d46d-4993-bfbd-029322e2e581'),
(810,	'2021-11-13 22:02:45',	'15a1d2da-b605-4735-a6e0-87cc1a5780c0'),
(811,	'2021-11-13 22:11:36',	'5d21a16f-cad0-472e-99c9-c6266c9c4656'),
(812,	'2021-11-13 22:15:48',	'7afdea20-3718-4fe6-a73c-ab0b75433994'),
(813,	'2021-11-14 03:30:43',	'3dda2005-10be-4568-8704-ba05f3976ce0'),
(814,	'2021-11-14 07:11:54',	'f8d9be67-9ab7-4b26-a935-ea28f37d7bc1'),
(815,	'2021-11-14 07:27:53',	'43442d83-1bf5-4811-b23c-55801230d229'),
(816,	'2021-11-14 07:28:39',	'a7c51020-dd68-48ca-a3ee-3bb23eb11d3c'),
(817,	'2021-11-14 16:01:00',	'e1f0054e-bc21-49e3-a8c9-d9533d4372b3'),
(818,	'2021-11-14 16:50:19',	'88966bd2-e57f-4647-81ff-d453497efaf3'),
(819,	'2021-11-14 16:52:54',	'3a2465c9-83f3-4f53-a0cf-5232a482c77a'),
(820,	'2021-11-14 17:58:42',	'd1ccb72d-f660-4478-9e42-f35687d63b7c'),
(821,	'2021-11-14 17:59:40',	'ce535b0e-58bc-48e6-a25e-55546b792fe5'),
(822,	'2021-11-14 18:02:49',	'ecca2b54-f5ce-47b8-ab62-a4ae91192bbd'),
(823,	'2021-11-14 18:03:40',	'238de071-f511-41fd-9947-e3d080487641'),
(824,	'2021-11-14 18:04:13',	'ecc280b9-a0fe-415e-96f5-8c10013e23af'),
(825,	'2021-11-14 18:05:12',	'7707e8ad-d8ea-47da-bb57-2a28110d6533'),
(826,	'2021-11-14 18:06:48',	'e17b6244-45d2-419c-98d0-4047cfffb28f'),
(827,	'2021-11-14 18:09:15',	'312ba382-795d-4c18-95bd-5b471d259215'),
(828,	'2021-11-14 18:17:24',	'be200d95-6e98-424b-a056-c25a8d3531b7'),
(829,	'2021-11-14 18:22:58',	'4e5af551-e9a0-4708-ab09-618653d314f6'),
(830,	'2021-11-14 18:35:47',	'b2690e1a-b3a6-4be3-bc3f-7551a3ce51f1'),
(831,	'2021-11-14 18:36:17',	'ec48f1ef-a9b2-4350-914a-4e7513f753d5'),
(832,	'2021-11-14 18:48:07',	'bfb6037a-2de9-4bcb-81a6-641ec41a10dc'),
(833,	'2021-11-14 18:48:40',	'5b6b1226-3674-4708-8958-61a1b1fdcd5e'),
(834,	'2021-11-15 05:03:44',	'd7acf707-aaf6-45d1-be2d-5422e634aa11'),
(835,	'2021-11-15 05:03:56',	'7252d057-948b-497e-a535-d2a8bcd10d44'),
(836,	'2021-11-15 05:04:27',	'5be07650-0ba0-47f1-9f0c-05fd863f3c3d'),
(837,	'2021-11-15 05:05:50',	'afbbb707-c0d2-4de7-a8fb-2af50bc33677'),
(838,	'2021-11-15 05:06:25',	'7253b801-f9ef-443e-a435-a4fb311f84da'),
(839,	'2021-11-15 05:07:17',	'f5f35e59-dcbc-49c5-a0b3-020d18a693e3'),
(840,	'2021-11-15 05:08:26',	'48a9dea1-2663-449e-aee4-831261c2add0'),
(841,	'2021-11-15 05:16:05',	'c0b38b20-c78c-4a96-905d-050ac4dfdbb1'),
(842,	'2021-11-15 05:20:06',	'4728c063-e737-44b2-80f6-ffdcddc26493'),
(843,	'2021-11-15 05:34:23',	'cfa32965-e0f4-4464-b08b-bfafd2f71243'),
(844,	'2021-11-15 10:02:15',	'10478232-7524-4ee6-b2dc-3241da82d19f'),
(845,	'2021-11-15 10:21:18',	'4b9255e5-c6fa-419f-948f-fc18e2aa512d'),
(846,	'2021-11-15 11:04:26',	'bdb0bbae-b082-4968-8a43-d46427313d05'),
(847,	'2021-11-15 11:05:03',	'efafce43-75e3-4c37-8ec2-6d6e55219aed'),
(848,	'2021-11-15 11:55:28',	'25bec27d-3522-4a5a-8ca1-013a8475187b'),
(849,	'2021-11-15 22:34:31',	'fb6fc2c6-0d43-4050-89e1-405fbf7730ae'),
(850,	'2021-11-15 23:02:30',	'148ba9f0-e09a-4016-ad6c-cbac2ccf0189'),
(851,	'2021-11-17 00:11:03',	'adb67b28-d155-4f94-a849-3f015a5112d2'),
(852,	'2021-11-17 00:20:48',	'83eca040-c0e3-41f1-bfbf-2fe8115a5c45'),
(853,	'2021-11-17 00:59:01',	'007426e8-ac50-40d1-a0a2-c91735a7a091'),
(854,	'2021-11-18 01:31:34',	'12d11c33-945c-4511-983c-f7e9937299ef'),
(855,	'2021-11-18 01:31:34',	'c875a237-d9fe-4edf-a757-bc6a5e5d1332'),
(857,	'2021-11-18 21:54:07',	'3f55f64e-bb51-4fdd-98e1-4e06b4bbfee7'),
(858,	'2021-11-18 22:24:45',	'f6b40a6e-f80e-43b7-b43c-f2eeb8c58b93'),
(859,	'2021-11-18 23:03:44',	'58a4ed18-de97-4fcb-8bfb-754ff5fe97af'),
(860,	'2021-11-18 23:16:56',	'e3d85643-7f35-436d-b5a9-6e8faf6c5346'),
(861,	'2021-11-18 23:17:49',	'6f653a0c-1b45-4b37-b55a-61c7db9b8adb'),
(862,	'2021-11-19 01:56:15',	'4f91bc0f-9e5e-496a-8b27-5216ecd34ec8'),
(863,	'2021-11-19 02:00:29',	'93812996-8449-4255-9714-5ea36a9dccad'),
(864,	'2021-11-19 02:06:54',	'e00041c5-30af-4e6a-80db-f9b66c4f1838'),
(865,	'2021-11-19 06:50:04',	'b5ea8091-cb5f-42a1-9c4c-32f602495f7b'),
(866,	'2021-11-19 08:05:36',	'7395fdf8-7bc8-4724-b9f7-33cff3d2d162'),
(867,	'2021-11-19 08:05:51',	'70ed33b2-9511-41f3-9941-24a3eecf1670'),
(868,	'2021-11-19 08:14:10',	'57dfa442-c1d5-4303-9a73-798b55bc27b3'),
(869,	'2021-11-19 12:49:27',	'be99fd4e-fbae-418b-9b17-0470a09de39d'),
(870,	'2021-11-19 13:45:37',	'b0fec7b9-2a71-40a7-a296-beb1db55d2dc'),
(871,	'2021-11-19 13:45:58',	'9a53a4f4-d3da-4d59-a821-31c6e1cbeebe'),
(872,	'2021-11-19 16:20:02',	'f6a0e3c2-453e-49fc-8299-f3d2d061f1e5'),
(873,	'2021-11-19 19:39:31',	'5470a2ba-90e9-4a75-a433-7be0da96e735'),
(874,	'2021-11-19 19:40:29',	'6807b56b-3843-4234-aa7d-883cdc9a2fd4'),
(875,	'2021-11-19 19:43:26',	'd6c4fec1-9468-4812-bc3b-3dfc2feea104'),
(876,	'2021-11-19 19:44:16',	'bbbdaec0-6ada-4956-99d7-eba6b2129484'),
(877,	'2021-11-19 23:23:37',	'b7d27b6d-2802-4e59-9d44-d1529d74ba66'),
(878,	'2021-11-20 05:55:18',	'8ec2c389-fe1f-459d-b4a3-42eee979cd4b'),
(879,	'2021-11-20 06:03:28',	'b584a595-61fe-4c97-b87e-b94085bf496e'),
(880,	'2021-11-20 06:08:21',	'bc811412-a7d8-44fb-859b-8160f4fdbcec'),
(881,	'2021-11-20 06:46:56',	'a9066ea3-b8b6-4ce5-b304-5044b0f6ec06'),
(882,	'2021-11-21 09:20:49',	'3bddbf47-ca31-4be4-abdf-7ae3f5ddb132'),
(883,	'2021-11-22 05:26:17',	'16fd673d-6c0e-4593-9881-185f85ea0488'),
(884,	'2021-11-22 06:21:28',	'99e0a53b-7705-4f2b-acf0-e4604c3fa154'),
(885,	'2021-11-22 06:27:27',	'70e3b310-8711-4548-b9ba-a784b096a7b8'),
(886,	'2021-11-22 06:28:16',	'd471b7a6-d176-4407-a6bf-0a6afd9dcda0'),
(887,	'2021-11-22 06:31:34',	'801c3c51-da6c-454c-a198-37a6b2bb48a1'),
(888,	'2021-11-22 06:32:11',	'ae9d73b5-ba3f-465b-b84d-f3e58da45387'),
(889,	'2021-11-22 06:33:04',	'2e6f428b-5f87-49dd-b750-4794c6290b6d'),
(890,	'2021-11-22 07:05:47',	'ffc2693e-4d65-4e25-a125-4259c5aad0b4'),
(892,	'2021-11-22 19:01:25',	'd6f731f0-49b0-45a0-a277-099f569d994d'),
(893,	'2021-11-23 00:37:17',	'96549419-aa5b-47e2-93bb-1985e7ea8145'),
(894,	'2021-11-23 10:06:24',	'a8d5aa97-544e-415c-8c59-21987ed9cfe8'),
(895,	'2021-11-23 12:26:13',	'd893b446-278a-40f3-a9e7-e08c733f4375'),
(896,	'2021-11-23 12:30:40',	'f1880c78-3472-4d5c-a4e7-7ff8b5bbfda0'),
(897,	'2021-11-23 12:35:29',	'2a0727c7-52c2-44f3-89fa-cbe120d1dfd8'),
(898,	'2021-11-23 12:43:04',	'170506db-ed67-4aba-bca2-94e93b2a7cba'),
(899,	'2021-11-23 12:49:04',	'0007318f-90bc-4233-9c74-98632170243a'),
(900,	'2021-11-24 03:31:50',	'd31691d0-4cec-4d43-8283-623de26dcdc5'),
(901,	'2021-11-24 03:33:01',	'b185b25a-1c93-4ddb-a076-34ed22ee9497'),
(902,	'2021-11-24 16:20:31',	'752aa90a-1c0e-4ad8-bd89-544d3f35062b'),
(903,	'2021-11-24 17:57:52',	'03cc6586-1009-41f6-b7b1-fb78eacf0576'),
(904,	'2021-11-24 17:59:18',	'f9d70d5a-9790-4762-b6cb-77e71e6d2d0b'),
(905,	'2021-11-25 10:24:24',	'a669fde3-8f5d-4850-ae0e-5ca35c3b98dc'),
(906,	'2021-11-25 10:24:35',	'240208d3-78b5-4d75-ac29-b5928c0d9706'),
(907,	'2021-11-25 11:23:43',	'50b03daa-ece0-457f-af13-73d5d6d03945'),
(908,	'2021-11-25 19:24:14',	'9458be80-7d19-403d-ac1c-b036ee730e51'),
(909,	'2021-11-26 04:55:36',	'578f482d-f71d-4813-9d21-722158ec0359'),
(910,	'2021-11-26 06:17:08',	'f0bb16f9-b4e8-47f8-be6b-3c7e06afc318'),
(911,	'2021-11-26 06:22:19',	'c5f2efdc-e262-446f-abaf-d9fe227ef5ba'),
(912,	'2021-11-26 06:22:32',	'4bf907f4-e67a-422b-aaf8-834e0386ab20'),
(913,	'2021-11-26 06:28:54',	'9cf3539a-dbe9-47e1-bbca-5629c3e3604e'),
(914,	'2021-11-26 06:29:26',	'4977949b-a501-4f4d-90d9-ee96ca3827f4'),
(915,	'2021-11-26 06:30:23',	'cb6b5399-abfc-416d-aff2-870c0d10b146'),
(916,	'2021-11-26 06:32:23',	'a8a4dff7-74c6-4913-82f3-fcf6793a70ed'),
(917,	'2021-11-26 06:32:37',	'eddd9dc9-1dfc-41da-80b8-c818507a580f'),
(918,	'2021-11-26 06:32:51',	'36c75a9b-9af3-44f2-94d5-bdefb8578313'),
(919,	'2021-11-26 06:34:22',	'20b39aba-8d28-425c-99f6-48141b769af1'),
(920,	'2021-11-26 06:35:15',	'41131135-cd48-41cf-bde0-57b729c55a4c'),
(921,	'2021-11-26 06:36:25',	'a71a0d78-1c56-4617-bfe9-4bf596f4bfa6'),
(922,	'2021-11-26 06:43:16',	'f8e1e07a-9284-43e2-b6f6-e8cff62cadfa'),
(925,	'2021-11-26 06:54:12',	'1dc2cf62-8fe0-4138-85c8-dbe2edbeffa6'),
(926,	'2021-11-26 06:55:19',	'930588a5-1331-4019-a6a9-252b8e6de8b2'),
(927,	'2021-11-26 07:01:13',	'7eccfd49-21d7-4ec6-a168-e18cedcf9f05'),
(928,	'2021-11-26 07:57:32',	'6c996c7a-9cc7-4d64-92e1-cbd3df7d3b23'),
(931,	'2021-11-26 08:37:38',	'f8194aec-ef89-4466-b75b-c0b19325af66'),
(933,	'2021-11-26 17:50:16',	'72db6e9c-43f3-4d1a-a937-6935669010cc'),
(936,	'2021-11-28 15:13:42',	'2e4167e3-30f2-49b9-ae39-4b6d941101cf'),
(937,	'2021-11-30 11:52:38',	'8945f810-ca22-44d2-adef-119850233c7b'),
(939,	'2021-12-04 19:10:06',	'a4bee542-0491-4c94-a907-6cc0b1dfb938'),
(940,	'2021-12-09 22:29:51',	'f78ff313-9974-4d23-afbc-76c2a31207c1'),
(942,	'2021-12-12 14:20:02',	'a7606e8f-20a1-4350-b41b-a23d2b609f3f'),
(943,	'2021-12-12 16:15:00',	'0288c486-5330-49e1-95a9-c0165ea4ee78'),
(944,	'2021-12-12 18:53:02',	'dcdef6db-9402-4bd4-9e96-948f27056bcd'),
(958,	'2021-12-13 01:14:41',	'fb9591e0-b046-4db9-960f-046ad21d4af8'),
(962,	'2021-12-14 11:14:55',	'36cab4c0-0e9a-4fc9-8ff3-90a09aa58b18'),
(963,	'2021-12-14 22:40:20',	'e8e411dc-3824-4847-95ba-bee0c39af6fa'),
(978,	'2021-12-26 05:45:44',	'5ffba92c-bd71-4cf4-b5bc-02a71ba7b156'),
(979,	'2021-12-26 12:50:20',	'2b2a1902-b3da-4863-848b-070a97b51169'),
(981,	'2022-03-30 11:38:25',	'd8cf6b0d-a3e8-447a-b81f-827c63ce3cd4'),
(982,	'2022-03-30 11:39:20',	'6a308a86-02d6-47c0-9cd0-a828e0dca155'),
(983,	'2022-03-30 11:50:10',	'092305e0-1484-4e2f-9343-0899647deb35'),
(984,	'2022-03-30 11:57:18',	'7eed7c99-7444-4f20-bd56-097bd3580538'),
(985,	'2022-03-30 12:06:03',	'7fab1b33-f8c0-4386-84cb-a75a5eaa67b1'),
(986,	'2022-03-31 09:33:13',	'0df8a3c8-0627-4525-a491-4caa1abeab16'),
(987,	'2022-03-31 10:25:42',	'c9d5dc89-4420-4876-8439-8b9b70c0f4ac'),
(988,	'2022-03-31 10:25:52',	'2f3e9b11-08fc-402a-9a8c-ee2fb364a92a'),
(990,	'2022-03-31 10:42:35',	'ba2d2493-d440-4063-9b0a-3f8878a66677'),
(991,	'2022-03-31 15:24:29',	'1527e9fd-5bb1-4477-8c2e-6061d1e4fa5b'),
(993,	'2022-03-31 15:30:53',	'0b031d92-2286-4123-913c-4b0f47b0eaa2'),
(994,	'2022-03-31 15:32:39',	'273b1c29-54eb-4afe-be2e-5c9831b2cd83'),
(995,	'2022-03-31 15:46:32',	'a23a6b5f-4d12-4dd6-bfb0-18c7c5c5665e'),
(997,	'2022-04-01 11:55:35',	'7e6c8133-3560-4a08-ba0e-5f2378ad2886'),
(998,	'2022-04-01 11:56:15',	'9ce24398-5b59-4ea1-9660-f5fd4642ca8d'),
(999,	'2022-04-01 12:00:24',	'2c3e5c56-6041-4a55-a56b-24b460c1ef0d'),
(1000,	'2022-04-01 12:01:30',	'28f995b5-3452-434f-9121-697e4faf2843'),
(1001,	'2022-04-01 12:07:35',	'9a260d3d-2bc5-4931-b982-a1f992289689'),
(1005,	'2022-05-28 18:40:25',	'9b341a5b-364e-4d89-bad6-f2993cbf2858'),
(1006,	'2022-10-24 02:52:01',	'32961949-9ef3-4358-b2bc-8797dcf53ecc'),
(1007,	'2022-10-24 09:34:18',	'80d94cf0-d2a6-492c-9398-e68fdec59a6f'),
(1009,	'2022-11-14 02:50:22',	'079d95d9-c834-48d7-9ebe-a7ccb27eb53b'),
(1011,	'2022-11-14 11:26:29',	'ce23f241-322b-4208-b790-ec5d682ab011'),
(1012,	'2022-11-14 11:27:02',	'03baab3a-6ff9-418e-8307-d550672ceadf'),
(1013,	'2022-11-14 11:42:20',	'b264781c-d888-43fb-ba7a-9188799a2ca3'),
(1014,	'2022-11-14 11:43:33',	'20d6445c-f659-4644-acd8-ddbf428a7fc5'),
(1015,	'2022-11-14 12:28:14',	'282e7400-4dfb-420e-8039-5c487c401c8c');

DROP TABLE IF EXISTS `relation`;
CREATE TABLE `relation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `confirmed_at` datetime DEFAULT NULL,
  `sent_at` datetime DEFAULT NULL,
  `receiver_id` bigint NOT NULL,
  `sender_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnfnboik5lv37rehgogdfthojk` (`receiver_id`),
  KEY `FK38wce4d5fg7hv0o3t0lpfclom` (`sender_id`),
  CONSTRAINT `FK38wce4d5fg7hv0o3t0lpfclom` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKnfnboik5lv37rehgogdfthojk` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO `relation` (`id`, `confirmed_at`, `sent_at`, `receiver_id`, `sender_id`) VALUES
(35,	'2021-10-03 16:21:14',	'2021-10-02 21:50:39',	4,	3),
(45,	'2021-10-05 19:35:54',	'2021-10-02 21:50:40',	5,	3),
(65,	'2021-12-26 03:29:38',	'2021-10-03 16:21:08',	2,	4),
(75,	'2021-10-05 19:35:55',	'2021-10-03 16:21:10',	5,	4),
(115,	'2021-10-17 18:11:26',	'2021-10-05 19:06:56',	15,	4),
(125,	'2021-10-17 18:11:28',	'2021-10-05 19:36:00',	15,	5),
(145,	'2021-12-26 04:04:03',	'2021-10-17 18:11:22',	2,	15),
(155,	NULL,	'2021-10-17 18:11:24',	3,	15),
(162,	NULL,	'2021-12-26 04:04:11',	43,	2),
(164,	NULL,	'2021-12-26 04:19:17',	3,	1),
(165,	NULL,	'2021-12-26 05:45:36',	1,	2);

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO `role` (`id`, `name`) VALUES
(1,	'ROLE_USER'),
(2,	'ROLE_ADMIN'),
(3,	'ROLE_JOB_SEEKER'),
(4,	'ROLE_RECRUITER'),
(5,	'ROLE_PREMIUM');

DROP TABLE IF EXISTS `training`;
CREATE TABLE `training` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `school` varchar(255) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfoy157kvf3lmam5gv5u2ty487` (`user_id`),
  CONSTRAINT `FKfoy157kvf3lmam5gv5u2ty487` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO `training` (`id`, `date`, `label`, `school`, `user_id`) VALUES
(15,	'2022-09-30',	'Mastère Expert en système d\'information',	'SUP DE VINCI',	3),
(25,	'2020-09-30',	'Bachelor Web',	'Hetic',	3),
(35,	'2018-08-31',	'Diplôme universitaire Informatique (DUT)',	'Institut universitaire Informatique (IUT) d\'Orsay',	3),
(45,	'2015-08-31',	'Baccalauréat SVT Série S spécialité Physique-Chimie',	'Lycée A. Einstein',	3),
(55,	'2022-09-29',	'Master Expert en systèmes d\'informations',	'Sup de Vinci',	2),
(65,	'2020-06-01',	'Licence en informatique',	'Université Paris Descartes',	2),
(75,	'2022-09-30',	'Titre RNCP de Niveau 7 Expert en Systèmes d’Informations',	'CFA Sup de Vinci',	1),
(85,	'2019-10-31',	'Titre RNCP de Niveau 6 Concepteur Développeur d\'Applications Numériques',	'IPI Paris',	1),
(95,	'2006-06-30',	'DUT d\'informatique option génie logiciel',	'IUT d\'Orsay',	1),
(105,	'2019-09-30',	'BTS SIO',	'Lycée polyvalent de Cachan',	5),
(115,	'2020-09-29',	'Concepteur développeur d\'applications',	'Simplon.co',	5),
(125,	'2022-09-29',	'Master en informatique',	'Sup de Vinci',	5),
(126,	'2002-05-31',	'Baccalauréat Scientifique',	'Lycée A. Einstein',	1);

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
  `image_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`),
  KEY `FK5v5h53roftm0e90x45m6bh7al` (`image_id`),
  CONSTRAINT `FK5v5h53roftm0e90x45m6bh7al` FOREIGN KEY (`image_id`) REFERENCES `file` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO `user` (`id`, `activated_at`, `activation_key`, `active`, `birthday`, `contribution`, `email`, `expectation`, `firstname`, `hobbies`, `lastname`, `password`, `phone_number`, `presentation`, `purpose`, `registered_at`, `url`, `username`, `image_id`) VALUES
(1,	'2021-09-24 23:11:23',	'793d746c-36f8-4937-bda6-7d6222cf3f51',	CONV('1', 2, 10) + 0,	'1983-09-06',	'Mon expertise dans le domaine du développement logiciel',	'matthieu@mail.com',	'Acquérir une expérience supplémentaire dans l\'élaboration de solutions informatiques',	'Matthieu',	'Equitation, programmation, snowboard',	'Audemard',	'$2a$10$vmy1fqGnyW7ZN0RXww2j2uBue.A4/5CSEGX6rsROmWfpwr.0bTVWG',	'0617243258',	'Passionné par le développement, je cherche toujours à trouver les solutions les plus élégantes aux problèmes qui me sont donnés.',	'Avant tout je cherche à m\'épanouir dans mon métier',	'2021-09-24 23:10:48',	NULL,	'matthieu',	56),
(2,	'2021-09-24 23:12:41',	'35254265-fa9d-4514-ae23-f167a5f6a831',	CONV('1', 2, 10) + 0,	'2021-10-07',	'My knowledge as an Elf, having lived for thousands of years.',	'dylan@mail.com',	'To help me throw the ring in Mount Doom.',	'Dylan',	'F1, Badminton, Pétanque, Anime',	'Brudey',	'$2a$10$s7u8ams6mKx4HlzczmTpDu2r.GWNwxkQzfHar1Rq9YkovEE2rzdCu',	'0920100451',	'I was there, 3000 years ago.',	'Get the ring.',	'2021-09-24 23:12:28',	NULL,	'dylan',	NULL),
(3,	'2021-09-24 23:14:33',	'88381beb-dd09-45a0-9e9f-66e468347ab9',	CONV('1', 2, 10) + 0,	'1996-07-26',	'De la joie et de la bonne humeur et de l\'expertise et mon réseau.',	'mel@mail.com',	'De la joie et de la bonne humeur :)',	'Mélanie',	'Badminton,\nPing pong,\njeux video,\nSéries',	'Da Costa',	'$2a$10$mLj1NYUHlajXwPurnR2h6eCshIRu8w1clempU07UfCSyh6Dcy3z6S',	'0606969696',	'Geek dans l\'ame, je suis en train de terminer mon Mastère Expert en système d\'information',	'Réussir dans la vie :)',	'2021-09-24 23:14:23',	NULL,	'melanie',	NULL),
(4,	'2021-09-24 23:15:21',	'5ee1b136-1a89-48eb-bce7-241ef06b79de',	CONV('1', 2, 10) + 0,	'1996-10-06',	'De la bonne humeur et des compétences',	'paul@mail.com',	'Trouver un emploi',	'Paul',	'Judo\nPhotographie',	'Flumian',	'$2a$10$p1dW/T62V7C2vt2OK7dVruZVbQccT/GIetlSQc4Wh7jkariyBXq9W',	'0606060606',	NULL,	'Développeur informatique',	'2021-09-24 23:15:11',	NULL,	'paul',	NULL),
(5,	'2021-09-24 23:17:43',	'b1e89b98-d2a3-4958-9a9c-270ab2c0439a',	CONV('1', 2, 10) + 0,	NULL,	NULL,	'm@thusha.com',	NULL,	'Mathusha',	NULL,	'Thirulogasingam',	'$2a$10$khA09HcY4cvY0B30.YVeJeVVqWjCMJ.0sfsvKCzmTKCnzQn5eV5b2',	NULL,	NULL,	NULL,	'2021-09-24 23:17:32',	NULL,	'mathusha',	NULL),
(6,	'2021-09-24 21:19:45',	'35412334-173f-48c8-bca5-2829a9e4c0e3',	CONV('1', 2, 10) + 0,	NULL,	NULL,	'admin@mail.com',	NULL,	'',	NULL,	'',	'$2a$10$QvalNDrZUPqhLmrF4/ZPTeFWSA0CoefXwQ/uCYxpRF/vlkTXlNx1G',	NULL,	NULL,	NULL,	'2021-09-24 23:19:08',	NULL,	'admin',	NULL),
(15,	'2021-10-05 19:04:50',	'1256b301-d43b-457f-8a47-449b043efad6',	CONV('1', 2, 10) + 0,	'1979-07-02',	'Bonne humeur, joie de vivre ainsi que de délicieuses spécialités venues des Balkans',	'vlad.leban@gmail.com',	'Une main tendue riche d\'amitié',	'Vlad',	'Chasse, pêche et philatélie ',	'LEBAN',	'$2a$10$v4LPrpkbwYh9PNoFMzd.juJAQyafdpOLc0dmd6a8uaEVPdhUqysMS',	'0651299200',	'Dans ma branche d\'activité il est de coutume de ne pas se présenter. ',	' ',	'2021-10-05 19:04:20',	NULL,	'Vlad',	NULL),
(25,	'2021-11-28 15:53:23',	'ec806d76-344b-41c2-a71e-cd54c755a455',	CONV('1', 2, 10) + 0,	NULL,	NULL,	'rodrig@aud.com',	NULL,	'Steve',	NULL,	'Rodrig',	'$2a$10$NwZ6EknuC6FA/rxOKonHEuDC3bUIOjBvirj.MZmT1wjzgsuGA79xe',	NULL,	NULL,	NULL,	'2021-10-06 09:55:50',	NULL,	'Rodrig',	NULL),
(35,	'2021-12-12 21:21:31',	'61785276-25b7-4e96-b723-2660402ad277',	CONV('1', 2, 10) + 0,	NULL,	NULL,	'jean-francois.rouault@implicaction.eu',	NULL,	'Jean-François',	NULL,	'Rouault',	'$2a$10$a25umCRgEC4/Y.xv8V0Wd.wZgqIrm/pvXxZu8ZXLVQOSDOdhqtvaa',	NULL,	NULL,	NULL,	'2021-10-11 14:16:00',	NULL,	'jfrouault',	NULL),
(43,	'2021-12-12 19:20:12',	'87891381-716a-46e6-8f5f-a33f1014f3d9',	CONV('1', 2, 10) + 0,	NULL,	NULL,	'test@mails2.com',	NULL,	'test',	NULL,	'test',	'$2a$10$d4rCa/hCtorYM2.GUKYwzeXwthPuN23fOuTzcmgm8yFiuMxsmjW/i',	NULL,	NULL,	NULL,	'2021-11-28 19:33:18',	NULL,	'test',	NULL),
(55,	'2021-11-25 14:42:24',	'63957093-d079-4184-b0c6-d17bf74a2775',	CONV('1', 2, 10) + 0,	NULL,	NULL,	'jmlebec@yahho.fr',	NULL,	'Jean-Michel',	NULL,	'Lebec',	'$2a$10$VVRMyH.JuhCEMeCGt5J.mOzRhOUqb8jBWDKvNaYVihx0QOpMKxJyO',	NULL,	NULL,	NULL,	'2021-11-25 12:55:53',	NULL,	'Jean-Michel Lebec',	NULL);

DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group` (
  `user_id` bigint NOT NULL,
  `group_id` bigint NOT NULL,
  KEY `FK1kgi14tshuobvm55vfe4bmnmo` (`group_id`),
  KEY `FK1c1dsw3q36679vaiqwvtv36a6` (`user_id`),
  CONSTRAINT `FK1c1dsw3q36679vaiqwvtv36a6` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK1kgi14tshuobvm55vfe4bmnmo` FOREIGN KEY (`group_id`) REFERENCES `ia_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `user_group` (`user_id`, `group_id`) VALUES
(1,	34),
(3,	34),
(43,	34);

DROP TABLE IF EXISTS `user_notification`;
CREATE TABLE `user_notification` (
  `user_id` bigint NOT NULL,
  `notification_id` bigint NOT NULL,
  KEY `FKi5naecliicmigrk01qx5me5sp` (`notification_id`),
  KEY `FKnbuq84cli119n9cdakdw0kv5v` (`user_id`),
  CONSTRAINT `FKi5naecliicmigrk01qx5me5sp` FOREIGN KEY (`notification_id`) REFERENCES `notification` (`id`),
  CONSTRAINT `FKnbuq84cli119n9cdakdw0kv5v` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `user_notification` (`user_id`, `notification_id`) VALUES
(1,	7),
(1,	8),
(1,	9),
(1,	10),
(1,	13),
(1,	18),
(1,	21),
(1,	22),
(1,	23),
(1,	24),
(1,	25),
(1,	26),
(1,	27),
(1,	28),
(1,	30),
(1,	31),
(1,	32),
(1,	33),
(1,	34),
(2,	7),
(2,	8),
(2,	9),
(2,	10),
(2,	13),
(2,	30),
(2,	31),
(2,	32),
(2,	33),
(2,	34),
(3,	7),
(3,	8),
(3,	9),
(3,	10),
(3,	13),
(3,	19),
(3,	20),
(3,	21),
(3,	30),
(3,	31),
(3,	32),
(3,	33),
(3,	34),
(4,	7),
(4,	8),
(4,	9),
(4,	10),
(4,	13),
(4,	30),
(4,	31),
(4,	32),
(4,	33),
(4,	34),
(5,	7),
(5,	8),
(5,	9),
(5,	10),
(5,	13),
(5,	30),
(5,	31),
(5,	32),
(5,	33),
(5,	34),
(6,	7),
(6,	8),
(6,	9),
(6,	10),
(6,	13),
(6,	30),
(6,	31),
(6,	32),
(6,	33),
(6,	34),
(15,	7),
(15,	8),
(15,	9),
(15,	10),
(15,	13),
(15,	30),
(15,	31),
(15,	32),
(15,	33),
(15,	34),
(25,	7),
(25,	8),
(25,	9),
(25,	10),
(25,	13),
(25,	30),
(25,	31),
(25,	32),
(25,	33),
(25,	34),
(35,	7),
(35,	8),
(35,	9),
(35,	10),
(35,	13),
(35,	29),
(35,	30),
(35,	31),
(35,	32),
(35,	33),
(35,	34),
(43,	7),
(43,	8),
(43,	9),
(43,	10),
(43,	12),
(43,	13),
(43,	30),
(43,	31),
(43,	32),
(43,	33),
(43,	34),
(55,	34);

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

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
(6,	2),
(15,	1),
(15,	3),
(25,	1),
(25,	3),
(35,	1),
(35,	3),
(1,	5),
(2,	5),
(3,	5),
(5,	5),
(1,	2),
(43,	1),
(43,	3),
(55,	1),
(55,	3),
(55,	5),
(55,	2);

DROP TABLE IF EXISTS `vote`;
CREATE TABLE `vote` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `vote_type` int DEFAULT NULL,
  `post_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcsaksoe2iepaj8birrmithwve` (`user_id`),
  KEY `FKl3c067ewaw5xktl5cjvniv3e9` (`post_id`),
  CONSTRAINT `FKcsaksoe2iepaj8birrmithwve` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKl3c067ewaw5xktl5cjvniv3e9` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


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
  KEY `FKhnxjamc0hrv0uok9w7aayk6kk` (`user_id`),
  CONSTRAINT `FKhnxjamc0hrv0uok9w7aayk6kk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO `work_experience` (`id`, `company_name`, `description`, `finished_at`, `label`, `started_at`, `user_id`) VALUES
(15,	'Ivalua',	'Alternance',	'2022-09-30',	'Ingénieur informaticien c#',	'2018-09-04',	3),
(25,	'SustainEcho',	'Développe une application d\'analyse de bilan carbone pour les entreprises du bâtiment.',	'2021-09-29',	'Développeur Frontend',	'2021-09-28',	2),
(35,	'Société Générale',	'Concevoir un portail d\'achat de serveurs et de pods en interne.',	'2021-08-29',	'Développeur FullStack',	'2020-09-30',	2),
(55,	'CGI',	'En prestation au sein de l\'Office Nationale des Forêts, projet de maintenance évolutive et corrective d\'une application de gestion de flux opérationnels.\n\n#java7 - #javaEE - #oracle - #svn ',	'2019-11-30',	'Développeur JavaEE (alternance)',	'2017-12-01',	1),
(65,	'Universal Music Mobile',	'Maintenance évolutive et corrective d\'un intranet et d\'un outil de distribution de contenu numérique\n\n#sql-server - #visual-basic - #c-sharp',	'2006-06-30',	'Développeur .NET (stage)',	'2006-04-03',	1),
(75,	'Laboratoire de recherche en informatique d\'Orsay',	'Conception et création d\'outils d\'accès à une base de données de segments d\'ARN\n\n#php - #mysql - #bio-informatique',	'2005-07-01',	'Développeur PHP (stage)',	'2005-04-04',	1),
(85,	'Lycée polyvalent de Cachan',	'Refonte du site de Loisirs-sur-mesure en PHP/HTML/CSS/CAKEPHP et suivi avec Google Analytics\n',	'2018-07-31',	'Stagiaire développement web',	'2018-05-31',	5),
(95,	'Loisirs-sur-mesure',	'Refonte du site du Lycée Polyvalent de Cachan à l\'aide de wo',	'2019-02-28',	'Stagiaire développement web',	'2018-12-31',	5),
(105,	'Idemia',	'',	'2021-10-04',	'Dev WEB',	'2020-10-11',	4),
(115,	'Wavestone',	'',	'2020-01-25',	'Consultant',	'2019-10-05',	4),
(125,	'COGIP',	'En tant que chef d\'étage à moustaches, ma tâche consistait essentiellement à motiver le personnel en instaurant des moments privilégiés à la cantine autour d\'un céleri rémoulade ou d\'un couscous de la mer.',	'2021-09-10',	'Chef d\'étage à moustaches',	'2021-02-28',	15),
(127,	'Société Générale',	'Au sein d\'une équipe scrum, j\'ai en charge la conception d\'une application de gestion de projet collaboratif.\n\n#git #java11 - #spring-boot - #angular12 - #mongodb',	NULL,	'Développeur Fullstack Java Angular (alternance)',	'2020-09-06',	1);

-- 2022-11-14 11:35:51

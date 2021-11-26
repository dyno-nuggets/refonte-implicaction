-- Adminer 4.8.1 MySQL 8.0.26 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

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
    KEY `FK8kcum44fvpupyw6f5baccx25c` (`user_id`),
    KEY `FKs1slvnkuemjsq2kj4h3vhx7i1` (`post_id`),
    CONSTRAINT `FK8kcum44fvpupyw6f5baccx25c` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FKs1slvnkuemjsq2kj4h3vhx7i1` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `comment` (`id`, `text`, `post_id`, `user_id`, `created_at`)
VALUES (100, '<p>test</p>', 9, 1, '2021-11-15 23:19:30');

DROP TABLE IF EXISTS `company`;
CREATE TABLE `company`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT,
    `description` longtext,
    `logo`        varchar(255) DEFAULT NULL,
    `name`        varchar(255) NOT NULL,
    `url`         varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

INSERT INTO `company` (`id`, `description`, `logo`, `name`, `url`)
VALUES (145,
        '<p><strong>ManpowerGroup</strong> est une entreprise multinationale spécialisée dans le conseil en ressources humaines, le recrutement, le travail intérimaire et les services aux infrastructures informatiques.</p>',
        'https://mobicheckin-assets.s3.amazonaws.com/uploads/events/5d358cc616cc81001fa53a0d/person_files/logo_new_YJ6QGMO_manpower.jpg', 'Manpower', 'https://www.manpower.fr/'),
       (155,
        '<p>Amazon &nbsp;est une entreprise de commerce en ligne américaine basée à Seattle. Elle est l\'un des géants du Web, regroupés sous l\'acronyme GAFAM10, aux côtés de Google, Apple, Facebook et Microsoft. Créée par Jeff Bezos en juillet 1994, l\'entreprise a été introduite en bourse au NASDAQ en mai 1997.</p>',
        'https://www.sportbuzzbusiness.fr/wp-content/uploads/2016/04/amazon-fr.png', 'Amazon', 'https://www.amazon.fr/'),
       (165,
        '<p>Spécialiste de la motoculture et du matériel pour espaces verts depuis plus de 20 ans, Greenmat vous propose une large gamme d’outils de qualité adaptés aux besoins des particuliers et des professionnels.</p>',
        'https://media.cylex-locale.fr/companies/1309/9500/logo/logo.jpg', 'Greenmat', 'https://greenmat.eu/'),
       (175,
        '<p>Irisiôme est une entreprise de l’agglomération bordelaise créée en 2015, qui a conçu et aujourd’hui commercialise un dispositif médical de nouvelle génération visant un marché considérable en France, en Europe mais aussi dans le reste du monde.</p><p><br></p><p>Ce dispositif s’appuie sur des lasers innovants à base de fibres optiques et délivrant des impulsions ultracourtes.</p><p>Le marché visé est celui de la dermato-esthétique avec des applications bien implantées comme l’épilation laser ou promises à de belles croissance comme le détatouage.</p><p><br></p><p>Notre promesse de proposer des soins moins douloureux et plus rapides nous a d’ores et déjà permis de gagner la confiance de professionnels de santé de renommée internationale.</p>',
        'https://www.apec.fr/files/live/mounts/images/media_entreprise/728145/logo_IRISIOME_728145_783558.png', 'Irisiome', 'http://irisiome.com/'),
       (185,
        '<p>Idée Blanche recrutement est&nbsp;spécialisé dans les domaines du recrutement et des prestations d\'audit, d\'évaluation et de conseil, notre mission est d\'aider nos clients à réussir en donnant du sens et de la valeur à chacune de nos actions.</p><p><br></p><p>A l\'écoute de votre projet au travers d\'une compréhension juste de vos besoins, nous dessinons ensemble la stratégie et les outils adaptés afin de garantir des résultats à courts termes. Nous intervenons pour :</p><p><br></p><p>AUDITER et EXPERTISER les pratiques RH de l\'entreprise</p><p>RECRUTER et ACCOMPAGNER les équipes et leur management</p><p>SUIVRE et ACCOMPAGNER l\'intégration des collaborateurs recrutés</p><p><br></p><p>La réussite d\'une politique RH au sein de l\'entreprise est liée à la capacité de son équipe dirigeante à être courageuse.</p><p><br></p>',
        'https://www.apec.fr/files/live/mounts/images/media_entreprise/716385/logo_ALFID_RECRUTEMENT_CONSEIL_716385_895962.png', 'Idée Blanche', 'http://ideeblanche.fr/'),
       (186, '<p>fdsf</p>', 'bla', 'nouvelle', 'bla'),
       (187, '<p>fdsqfqs</p>', 'fdsqf', 'fdsqf', 'fdsqf'),
       (188, '<p>fdsqfdqsf</p>', 'fdsqfdsq', 'fdsqf', 'fdqsfdsq'),
       (189, '<p>fdsqfdsfqf</p>', 'fdsf', 'fdsqf', 'fdsqfs'),
       (190, '<p>fdsqfdqsfqsf</p>', 'fdsqfdsq', 'fdqfdqs', 'fdsqf'),
       (191, '<p>fdsqfdsq</p>', 'fdsqf', 'fdsqf', 'fdsqf'),
       (192, '<p>fdsqfdsqf</p>', 'fdsqf', 'fdsqfdsqfdsq', 'fdsqfd'),
       (193, '<p>gfdsg</p>', 'nfdsq', 'nouvelle entreprise', 'reza'),
       (194, '<p>test</p>', 'test', 'test', 'test');

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
    KEY `FKgvpcrd2olmq75ujpppe0jn3cj` (`uploader_id`),
    CONSTRAINT `FKgvpcrd2olmq75ujpppe0jn3cj` FOREIGN KEY (`uploader_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`
(
    `next_val` bigint DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `hibernate_sequence` (`next_val`)
VALUES (50);

DROP TABLE IF EXISTS `job_application`;
CREATE TABLE `job_application`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `last_update` datetime     DEFAULT NULL,
    `status`      varchar(255) DEFAULT NULL,
    `job_id`      bigint NOT NULL,
    `user_id`     bigint NOT NULL,
    `archive`     tinyint(1)   DEFAULT '0',
    PRIMARY KEY (`id`),
    KEY `FKobel3nnbi451ftywx9q9m2qax` (`job_id`),
    KEY `FKcbu1yb4kyxowejebm87crxtr8` (`user_id`),
    CONSTRAINT `FKcbu1yb4kyxowejebm87crxtr8` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FKobel3nnbi451ftywx9q9m2qax` FOREIGN KEY (`job_id`) REFERENCES `job_posting` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

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
    PRIMARY KEY (`id`),
    KEY `FKd9euom18rv4nilau4hblm08cv` (`company_id`),
    CONSTRAINT `FKd9euom18rv4nilau4hblm08cv` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

INSERT INTO `job_posting` (`id`, `description`, `keywords`, `location`, `salary`, `title`, `company_id`, `created_at`, `short_description`, `archive`, `contract_type`)
VALUES (125,
        '<h2>L\'entreprise</h2><p>Manpower Conseil Recrutement recrute pour un de ses clients,<strong> un technicien de maintenance - électromécanicien de maintenance H/F en CDI</strong></p><p><br></p><p>Notre client est un industriel spécialisé dans l\'agroalimentaire reconnue pour la qualité de ses produits et son efficience industrielle.</p><p><br></p><h2>Poste proposé</h2><p>Au sein de l\'équipe Maintenance, vous avez comme mission principale <strong>la réalisation des travaux de maintenance préventive et corrective des installations de production et ce afin de maintenir l’outil de production en état de fonctionnement, de sécurité et performance.</strong></p><p><br></p><p>Pour ce faire, vous assurez&nbsp;:</p><ul><li>le réglage des lignes de production.</li><li>l’entretien de l’outil de production selon le plan de maintenance préventive.</li><li>le dépannage des installations et des matériels en cas de pannes mécanique, pneumatique et électrique.</li><li>le reporting sous le système GMAO et MES des interventions.</li></ul><p><br></p><p>A ce poste, vous travaillez seul ou en équipe et en étroite collaboration avec les équipes de production et d’amélioration continue.</p><h2>Conditions de travail</h2><p>Poste en 3*8.</p><p>Travail week end et jours fériés selon impératifs d\'activité.</p><h2>Profil recherché</h2><p>De formation de BAC STI Génie mécanique, complétée idéalement par un BTS Maintenance Industrielle ou Maintenance Automatismes Industriels, vous justifiez une expérience de 3 ans minimum à un poste d’agent ou de technicien de maintenance industrielle.</p><p><br></p><p><strong>Vous êtes reconnu(e) pour vos compétences techniques en mécanique, électricité et&nbsp;pneumatique. Des notions de soudure TIG représentent un réel plus.</strong></p><p><br></p><p>Polyvalent(e) et autonome, vous faîtes preuve de rigueur et d\'organisation dans l\'exécution du travail.</p><p>Réactif (ve) et Volontaire, vous savez vous adapter pour faire face aux aléas d\'activité avec calme et réflexion</p><p>Force de proposition, vous n\'hésitez pas à préconiser de nouvelles solutions dans un esprit d\'amélioration continue.</p><h2>Salaire et accessoires</h2><p>Salaire fixe à négocier selon profil et expérience.</p><p>Avantages d’entreprise :&nbsp;13ème mois. Prime annuelle sur objectifs. Participation. Mutuelle.</p><p><br></p>',
        '', '55100 Verdun', 'A négocier', 'Technicien de maintenance - électromécanicien de maintenance H/F', 145, NULL,
        '<p>Manpower Conseil Recrutement recrute pour un de ses clients,<strong> un technicien de maintenance - électromécanicien de maintenance H/F en CDI</strong></p>', CONV('0', 2, 10) + 0,
        'INTERIM'),
       (135,
        '<p>Amazon recherche un(e) Technicien(ne) de maintenance pour rejoindre l\'équipe de l\'un de ses centres de distribution et y travailler dans un environnement dynamique.</p><p><br></p><p>Le/La Technicien(ne) de maintenance veille au respect des directives et réglementations de sécurité, est chargé(e) de coordonner l\'entretien préventif des installations dans les délais prévus et à un niveau d\'exigence élevé, et garantit également la haute disponibilité des équipements par la mise en place d\'actions d\'amélioration continue.</p><ul><li>Faire des meilleures pratiques de santé et de sécurité une priorité sur l\'ensemble des projets.</li><li>Réaliser des tâches planifiées d\'entretien préventif sur l\'ensemble des équipements du site.</li><li>Intervenir rapidement en cas de panne, communiquer clairement avec les acteurs concernés et travailler efficacement à la résolution du problème.</li><li>Encourager les pratiques d\'amélioration continue en tirant des leçons des pannes précédentes et en transmettant des avis et des suggestions d\'amélioration via le responsable hiérarchique.</li><li>Garantir la haute disponibilité des équipements pour nos clients internes.</li><li>Assister les Technicien(ne)s de maintenance confirmé(e)s et se former à leurs côtés.</li></ul><h2>Votre profil</h2><ul><li>Compétences polyvalentes en électricité et en mécanique.</li><li>Connaissance pratique des systèmes de planification d\'entretiens préventifs.</li><li>Expérience en recherche de pannes dans les systèmes d\'automatisation/équipements de manutention.</li><li>Aptitude à lire et comprendre des schémas mécaniques et électriques.</li><li>Compétences de base en gestion de plates-formes.</li><li>Expérience dans l\'entretien de convoyeurs, de commandes de moteur et d\'onduleurs.</li><li>Expérience de travail dans le respect des normes et réglementations de santé et de sécurité en vigueur.</li><li>Expérience en gestion de prestataires de sous-traitance.</li><li>Capacité à travailler par équipe et en rotation dans un environnement disponible 24 h/24, 7 jours/7.</li></ul><h2>Qualifications appréciées</h2><ul><li>Expérience en commutation haute tension.</li><li>Expérience en machines de tri.</li><li>Expérience en gestion/configuration de lecteurs de codes-barres.</li><li>Expérience en machines d\'impression-pose.</li></ul><p><br></p><p>Nous attendons ta candidature avec impatience (de préférence en anglais). Poste ouvert aux personnes en situation de handicap.</p><p><br></p>',
        '', 'Montélimar, ARA', 'A négocier', 'Technicien de Maintenance (H/F)', 155, '2021-11-15 06:01:39',
        '<p>Amazon recherche un(e) Technicien(ne) de maintenance pour rejoindre l\'équipe de l\'un de ses centres de distribution et y travailler dans un environnement dynamique.</p>',
        CONV('0', 2, 10) + 0, 'CDI'),
       (145,
        '<h2>Description du poste</h2><p>La société GREENMAT spécialisée dans la vente et l’entretien de matériels de parcs et jardins depuis plus de 25 ans recherche un mécanicien (H/F) pour renforcer son équipe de SAV. L’entreprise est concessionnaire des marques suivantes : Iseki, Stihl, Reform, etc… .</p><p><br></p><p>Le mécanicien sera chargé de l’entretien et la réparation du matériel de nos clients. Le technicien sera amené à travailler dans la zone d’activité de l’entreprise ou sur les lieux de panne des matériels. Le technicien sera chargé d’assurer une relation de confiance entre le client et le Service Après-Vente de la concession. Les postes sont à pouvoir en CDI pour nos magasins situés à Chailly-en-Bière (77930) </p><p><br></p><h2>Qualités Requises :</h2><ul><li>Connaissance des moteurs essences et diesel</li><li>Connaissance en hydraulique</li><li>Connaissance en électricité</li><li>Savoir manipuler les principaux matériels</li><li>Goût du relationnel</li><li>Autonome, Responsable</li><li>Méticuleux, méthodique</li><li>S’adapter et avoir l’envie de se former aux nouvelles technologies et aux nouveaux matériels</li><li>Diplomate, être à l’écoute des problématiques des clients</li></ul>',
        '', 'Chailly-en-Bière 77930', '2500', 'Spécialiste vente', 165, NULL,
        '<p>La société GREENMAT spécialisée dans la vente et l’entretien de matériels de parcs et jardins depuis plus de 25 ans recherche un mécanicien (H/F) pour renforcer son équipe de SAV. L’entreprise est concessionnaire des marques suivantes : Iseki, Stihl, Reform, etc…dsqdf</p>',
        CONV('0', 2, 10) + 0, 'CDD'),
       (155,
        '<h2>Descriptif du poste</h2><p>Au titre d’ingénieur(e) de production, vous êtes responsable d’un atelier de fabrication. Vous participez au respect des contraintes de coûts, de qualité, de délais et de la réglementation dans la production des systèmes. Vous supervisez des lignes de production et encadrez une équipe. Vous devez mettre en place et contrôler les processus de fabrication, et devez trouver des solutions pour atteindre les objectifs fixés.</p><p><br></p><p>Vous serez ainsi amené(e) à :</p><ul><li>&nbsp;Organiser un atelier et une ligne de production, suivre les outils de productions et de métrologie (Calibration, maintenance).</li><li>Organiser et suivre les approvisionnements.</li><li>Assurer le suivi de la sous-traitance pour la conception électronique ou mécanique.</li><li>Suivre le bon déroulement de la fabrication</li><li>Optimiser les processus de fabrication et de l’appareil de production</li><li>Encadrer une équipe de production</li><li>Gérer l’activité de production et assurer le reporting hebdomadaire.</li><li>Faire remonter les problèmes de production à la direction technique et proposer de nouveaux développements dans leur passage en production (Rédaction documentation interne, instructions de travail).</li><li><br></li></ul><p>Vous évoluez au contact de collaborateurs multidisciplinaires et dans un contexte international. Vous serez amené(e) à communiquer régulièrement en Anglais.</p><h2>Profil recherché</h2><p>De formation niveau Bac + 4 ou 5 (écoles d’ingénieurs généralistes)&nbsp;ou technicien supérieur justifiant d’une solide expérience, vous disposez d’une expérience dans l’industrie.</p><p><br></p><p>Vous êtes polyvalent avec une volonté de maîtriser à la fois des compétences techniques et managériales. Vous êtes rigoureux et méthodique avec des qualités organisationnelles et relationnelles qui vous permettent de gérer le travail des équipes et les relations avec les interlocuteurs internes et externes à l\'entreprise.</p><p><br></p><p>Vous devrez fait preuve de dynamisme et de réactivité pour faire face aux situations imprévues.</p><p><br></p><p>Vous êtes autonome et doté(e) d’un esprit d’équipe, vous avez une bonne habilité à communiquer. Vous êtes force de propositions dans les tâches qui vous sont confiées.</p><h2>Entreprise</h2><p>Irisiôme est une entreprise de l’agglomération bordelaise créée en 2015, qui a conçu et aujourd’hui commercialise un dispositif médical de nouvelle génération visant un marché considérable en France, en Europe mais aussi dans le reste du monde.</p><p>Ce dispositif s’appuie sur des lasers innovants à base de fibres optiques et délivrant des impulsions ultracourtes.</p><p>Le marché visé est celui de la dermato-esthétique avec des applications bien implantées comme l’épilation laser ou promises à de belles croissance comme le détatouage.</p><p>Notre promesse de proposer des soins moins douloureux et plus rapides nous a d’ores et déjà permis de gagner la confiance de professionnels de santé de renommée internationale.</p><p><br></p><p><br></p><p><br></p>',
        '', 'Pessac - 33', 'A partir de 37 k€ brut annuel', 'Ingénieur de production F/H', 175, '2021-11-15 06:18:49',
        '<p>Au titre d’ingénieur(e) de production, vous êtes responsable d’un atelier de fabrication. Vous participez au respect des contraintes de coûts, de qualité, de délais et de la réglementation dans la production des systèmes. Vous supervisez des lignes de production et encadrez une équipe. Vous devez mettre en place et contrôler les processus de fabrication, et devez trouver des solutions pour atteindre les objectifs fixés.</p>',
        CONV('0', 2, 10) + 0, 'CDI'),
       (165,
        '<h2>Descriptif du poste</h2><p>Idée Blanche recrutement recherche pour un de ses clients, groupe industriel spécialiste des procédés spéciaux et de l’usinage dans le domaine aéronautique/énergie/automobile, un opérateur commande numérique.</p><h2>Votre mission</h2><p>Intégré au sein de l’équipe production,&nbsp;vos missions seront les suivantes&nbsp;:</p><ul><li>Prendre connaissance de l’ensemble des documents fournis&nbsp;: fiche suiveuse, plan, gamme d’usinage…</li><li>Monter et régler les outillages de fabrication</li><li>Monter / démonter les pièces</li><li>Connaitre / analyser les arrêts machines</li><li>Savoir choisir et utiliser les moyens de contrôles nécessaires.</li><li>Contrôler la production</li></ul><p>Réglage/ usinage / changement de série</p><ul><li>Réglage du moyen de production dans le respect des règles (Dossier d’instruction / Fiche d’instruction)&nbsp;:</li><li>Maitriser&nbsp;: Centrage, dégauchissage.</li><li>Assurer le suivi et la fréquence de Changement d’outils</li><li>Compréhension et lecture d’un programme (Connaitre les codes «&nbsp;iso&nbsp;» dans les programmes).</li><li>Assurer les modifications de programmation&nbsp;après aval du responsable. </li></ul><p>Gestion d’anomalies</p><ul><li>Gestion des Non-Conformités&nbsp;:</li><li>Identifier&nbsp;: repérer et étiqueter les Non-Conformités suivant les règles qualités</li><li>Trier&nbsp;: vérifier la conformité des pièces précédentes</li><li>Corriger pour supprimer la cause du défaut</li><li>Ouverture/participation au conseil de décision (en cas de non-conformité)</li><li>Appréhender le risque sécurité</li><li>Gérer les arrêts machines / rendre compte au responsable direct.</li></ul><h2>Profil recherché</h2><p>De formation supérieure Bac+2/+3 en mécanique, vous justifiez d\'une expérience professionnelle significative dans une fonction similaire au sein d’un groupe industriel reconnu.</p><h2>Entreprise</h2><p>Idée Blanche recrutement est&nbsp;spécialisé dans les domaines du recrutement et des prestations d\'audit, d\'évaluation et de conseil, notre mission est d\'aider nos clients à réussir en donnant du sens et de la valeur à chacune de nos actions.</p><p><br></p><p>A l\'écoute de votre projet au travers d\'une compréhension juste de vos besoins, nous dessinons ensemble la stratégie et les outils adaptés afin de garantir des résultats à courts termes. Nous intervenons pour :</p><p><br></p><p>AUDITER et EXPERTISER les pratiques RH de l\'entreprise</p><p>RECRUTER et ACCOMPAGNER les équipes et leur management</p><p>SUIVRE et ACCOMPAGNER l\'intégration des collaborateurs recrutés</p><p><br></p><p>La réussite d\'une politique RH au sein de l\'entreprise est liée à la capacité de son équipe dirigeante à être courageuse.</p><p><br></p>',
        '', 'Marmande - 47', 'A partir de 25 k€ brut annuel', 'OPÉRATEUR COMMANDE NUMÉRIQUE F/H', 185, '2021-11-15 06:27:28',
        '<p>Idée Blanche recrutement recherche pour un de ses clients, groupe industriel spécialiste des procédés spéciaux et de l’usinage dans le domaine aéronautique/énergie/automobile, un opérateur commande numérique.</p>',
        CONV('0', 2, 10) + 0, 'CDI');

DROP TABLE IF EXISTS `job_seeker`;
CREATE TABLE `job_seeker`
(
    `user_id` bigint NOT NULL,
    PRIMARY KEY (`user_id`),
    CONSTRAINT `FKt6no6vfq2vtvqbwlqyik9hyef` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

INSERT INTO `job_seeker` (`user_id`)
VALUES (1),
       (2),
       (3),
       (4),
       (5),
       (15),
       (25),
       (35);

DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `date`        datetime     DEFAULT NULL,
    `message`     varchar(255) DEFAULT NULL,
    `is_read`     bit(1)       DEFAULT NULL,
    `type`        varchar(255) DEFAULT NULL,
    `receiver_id` bigint       DEFAULT NULL,
    `sender_id`   bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKmlidwdldgmdw67l7pbrval0un` (`receiver_id`),
    KEY `FKnbt1hengkgjqru2q44q8rlc2c` (`sender_id`),
    CONSTRAINT `FKmlidwdldgmdw67l7pbrval0un` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FKnbt1hengkgjqru2q44q8rlc2c` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;


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
    KEY `FK72mt33dhhs48hf9gcqrq4fxte` (`user_id`),
    KEY `FKmlnoks6ujgl9ynt53af0bx4pj` (`group_id`),
    CONSTRAINT `FK72mt33dhhs48hf9gcqrq4fxte` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FKmlnoks6ujgl9ynt53af0bx4pj` FOREIGN KEY (`group_id`) REFERENCES `ia_group` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `post` (`id`, `created_at`, `description`, `url`, `vote_count`, `group_id`, `user_id`, `name`)
VALUES (8, '2021-11-14 22:07:14', '<p>fsdqfdsqf</p>', NULL, 0, 34, 6, 'dsqdq'),
       (9, '2021-11-15 05:25:02',
        '<h2>Le bilan de compétences</h2><p><br></p><p>Ce bilan réunit&nbsp;<strong>vos compétences, vos capacités</strong> dans certains domaines.</p><p>Il permet de savoir ce que vous savez déjà faire, sans prendre en compte les études qui vous aurez peut-être à faire.Vous aurez certaines capacités, soit parce que vous vous y serez intéressé, vous avez donc des facilités, soit parce que vous avez effectué des études qui vous auront donné ces capacités.</p><p><br></p><h2>Le test d’orientation</h2><p><br></p><p>Il arrive après le bilan et <strong>détermine les différents métiers qui correspondent à vos compétences</strong>. Il vous oriente vers des idées de futurs emplois, en fonction de ce que vous aimez faire mais également de ce que vous savez déjà faire.</p><p>Il s’agit, bien évidemment, de résultats théoriques. Rien ne dis que vous ne pourrez faire que ces métiers-ci, il s’agit essentiellement d’une aide quand on ne sait pas comment choisir son métier. Un test ne remplacera jamais votre volonté !</p><p><br></p><p>Il est possible qu’au moment où vous optez pour une reconversion professionnelle, vous ne sachiez pas, encore, vers quel métier vous tourner.&nbsp;</p><p>Quand on prend la décision de faire une reconversion, on sait que l’on veut changer de métier, que l’on veut voir et faire autre chose que ce que l’on faisait jusqu’à présent.</p><p><br></p><p>À partir de là, soit on a une idée fixe, ou plus ou moins arrêtée, de ce que l’on souhaite faire, soit on l’ignore totalement.</p><p><br></p><p><strong>Remarque: </strong>pour ceux qui savent vers quoi ils vont se tourner, le test d’orientation peut être moins intéressant que le bilan de compétences. Le test peut néanmoins s’avérer utile pour vous conforter dans votre idée.</p><p><br></p>',
        NULL, 0, 35, 6, 'treztz'),
       (10, '2021-11-15 12:40:08', '<p>blabal</p>', NULL, 0, 34, 1, 'blablabla');

DROP TABLE IF EXISTS `recruiter`;
CREATE TABLE `recruiter`
(
    `user_id`    bigint NOT NULL,
    `company_id` bigint DEFAULT NULL,
    PRIMARY KEY (`user_id`),
    KEY `FKe5tll0cw7cnohojpxb8qjcr5y` (`company_id`),
    CONSTRAINT `FK63kq3uyt2p3i32pjo1nfin63a` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FKe5tll0cw7cnohojpxb8qjcr5y` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;


DROP TABLE IF EXISTS `refresh_token`;
CREATE TABLE `refresh_token`
(
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `creation_date` datetime     DEFAULT NULL,
    `token`         varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

INSERT INTO `refresh_token` (`id`, `creation_date`, `token`)
VALUES (45, '2021-10-02 02:25:49', '881240e9-d84b-42b3-aeb9-feeb70b15aaf'),
       (55, '2021-10-02 08:33:04', '6a5a968f-ea3c-477d-b6f6-9ccb2a23a50e'),
       (85, '2021-10-02 12:19:46', '7425ffc9-66fa-42da-8215-2697a49ff0cb'),
       (95, '2021-10-02 21:49:39', '8b8156d8-1543-4ea2-ae31-9502215da435'),
       (135, '2021-10-03 16:32:45', '69eb309c-4eb8-464b-92d1-067ee2cd6591'),
       (165, '2021-10-04 00:28:06', '53e061d9-ea20-4d8b-b65e-9f519f9b3d77'),
       (195, '2021-10-04 15:51:28', '9a48ab4a-b6e0-438e-9f36-ab92229599d8'),
       (235, '2021-10-05 00:22:48', 'd96dce4e-cd8a-410a-b303-4218764f7475'),
       (245, '2021-10-05 08:59:37', '4538ffa2-5efc-4d36-9be4-80863067db32'),
       (265, '2021-10-05 17:27:03', 'e3f47e31-39e6-4634-8750-cef4a98b5da3'),
       (275, '2021-10-05 18:49:26', '991d8030-99cd-4a2b-9334-b70f63e426d2'),
       (305, '2021-10-05 19:05:25', '90151942-e420-4119-9ff6-3bea86336688'),
       (325, '2021-10-05 19:36:32', 'acd1dca1-8a56-4df6-b2f1-6be68af7e1e6'),
       (355, '2021-10-05 21:19:27', '664f3586-c601-4b9b-b003-e7f03f4d26c7'),
       (375, '2021-10-06 09:59:49', '73a19e5e-2f51-4934-a099-ccb8b2d0f6e8'),
       (385, '2021-10-09 00:11:32', '9330cbe6-fa55-4431-a014-18973ae3b23e'),
       (395, '2021-10-09 00:54:13', '5c7db655-369b-4b64-9991-69e1b8d2b83c'),
       (405, '2021-10-09 06:56:29', 'f2b72c3d-0079-4ec4-bf08-78513250750f'),
       (415, '2021-10-09 08:21:45', '9882fa88-b5a7-46f5-9a08-556a6f7422e5'),
       (425, '2021-10-09 08:59:30', 'd90b5185-b291-4727-b529-732497c06a0d'),
       (435, '2021-10-09 10:59:16', 'dd9902b1-bf19-4f1b-a744-d17b7ecfe1af'),
       (445, '2021-10-09 11:13:48', '33a53da4-ba80-494a-a47c-215a9a482a7f'),
       (455, '2021-10-09 12:51:09', '53e063a0-37c8-44f4-9db4-ba5ba468e0f7'),
       (465, '2021-10-10 01:54:27', 'db0787a5-9f6f-416a-9bb8-06ba5cdfe4ef'),
       (475, '2021-10-10 16:19:19', '537a535a-3997-4851-8fb2-8ad30cd8311e'),
       (485, '2021-10-10 16:19:59', '935f7005-6941-4e27-bc6c-a926ac505ec0'),
       (495, '2021-10-10 16:20:09', '93a55ab0-0888-4767-9d30-2403d73c03d6'),
       (505, '2021-10-10 16:21:04', '0c9389aa-9961-4fb8-8bbf-a30484819c67'),
       (515, '2021-10-10 17:07:17', 'e6ee8a17-0206-40c4-84fd-af39fdfa78de'),
       (525, '2021-10-11 08:17:43', 'b243082b-c568-439e-9cf2-fb0b32b5ffa7'),
       (535, '2021-10-11 08:48:55', '82b32c5e-bfbf-4bb6-8a73-1508a75ac96a'),
       (545, '2021-10-11 09:11:04', 'e971f424-6e63-4340-a9c7-1e5994674a5a'),
       (555, '2021-10-11 11:34:09', 'd9e0f7db-f251-4766-b334-00350e7d5ac8'),
       (565, '2021-10-11 11:35:04', 'bd6dcadd-a931-46eb-aa34-8dbf693db5ee'),
       (575, '2021-10-11 14:14:15', '8584deb4-0461-45ae-8a2c-111b33c8d24b'),
       (585, '2021-10-11 17:18:44', '8395bc05-66c4-43eb-986f-7924c7a149d8'),
       (595, '2021-10-13 21:01:39', 'b24b3dac-f18a-42bf-9479-5e25218b94bd'),
       (605, '2021-10-13 21:01:59', '070797bc-42e6-4d34-a2c5-081fc2bd1056'),
       (615, '2021-10-13 21:02:05', '2fa47c45-5e4e-49ce-91e8-8015fc266f80'),
       (625, '2021-10-15 15:42:45', '32c115a9-1994-4e5b-9370-9d7eaee90480'),
       (635, '2021-10-15 15:51:00', '3aa60bf1-3077-4f7b-9e08-563fd585e606'),
       (645, '2021-10-15 15:59:25', '76427d51-b4db-4e8c-a060-06f26098790b'),
       (655, '2021-10-15 18:37:14', 'b8ae38c7-cdec-431c-a3aa-a3c6e319ce4e'),
       (665, '2021-10-17 18:11:04', '1981e24d-7bf4-496a-87a5-393df2cbffec'),
       (675, '2021-10-18 22:28:18', '0398a7b1-ed43-48e3-8471-36fc79137d61'),
       (685, '2021-10-19 18:16:41', '263e68a6-fa5f-4451-993a-4033695920c5'),
       (695, '2021-10-19 19:09:23', 'dea83861-0c5f-4df9-840c-2b5407312fce'),
       (705, '2021-10-19 19:10:33', '9299cb33-90d0-4225-9b83-85e4613f9e25'),
       (715, '2021-10-19 19:15:32', '9caa6ded-4da0-4fd7-9336-4a44fdbf8f55'),
       (725, '2021-10-19 19:15:53', 'e57b12da-737c-47cc-9724-7ed2548d50de'),
       (735, '2021-10-19 19:16:45', '928af07e-6982-43c7-9193-72685d7eb38f'),
       (745, '2021-10-19 19:21:56', '8421ef0f-25d3-4596-b750-d7c35a8e067d'),
       (755, '2021-10-19 19:39:00', '810ef5d1-9e0d-4ae7-950f-e20f74931c78'),
       (765, '2021-10-20 14:34:09', 'd2c26a5d-95d4-47d7-b81e-af04187cbb4a'),
       (775, '2021-10-23 12:31:15', '3cb9c44c-1fe8-4efa-8fe7-41585efeecfe'),
       (785, '2021-11-03 11:19:57', 'ce7b4d63-88e3-4c54-adcd-b9c1510d7f19'),
       (786, '2021-11-04 21:59:36', 'e752b222-a4ef-4d2b-aacd-6311a3761153'),
       (787, '2021-11-04 21:59:59', 'aad6ab5d-0509-449e-8560-22dbe959adeb'),
       (788, '2021-11-06 17:41:32', '4b4ef499-b34f-4d51-9373-4ef4c074d38c'),
       (789, '2021-11-06 17:53:55', '27845e74-f5d4-4984-bf2e-d6e94705a4c3'),
       (790, '2021-11-06 17:54:11', '552bccee-3556-42eb-ad97-2869ad8954e2'),
       (791, '2021-11-06 17:57:23', '717f5741-1ca7-49fe-b500-64cb3ff5b6e6'),
       (792, '2021-11-07 02:30:42', '29b0a9f3-f04e-4ac8-b4ed-30dde975f500'),
       (793, '2021-11-08 18:09:30', '7e9cb45e-6377-4a63-87bb-8963c23ae6f8'),
       (794, '2021-11-09 02:15:37', '7fadd03e-1ad5-46b7-a871-be0dd36548b6'),
       (795, '2021-11-09 02:39:31', '020ca8b2-ffac-42f9-8ffc-d420ec90a542'),
       (796, '2021-11-09 04:36:05', '798ca38b-1872-4de3-b8a0-fe5c0818567f'),
       (797, '2021-11-09 04:36:44', '87d36909-eb56-4222-902d-bff6032a43bc'),
       (798, '2021-11-09 12:35:46', '5f4b422f-a85c-44f2-a7e9-9dd24686a934'),
       (799, '2021-11-09 17:59:42', 'a63973a4-c1bb-42cc-bd12-3286c0bec422'),
       (800, '2021-11-09 21:27:08', '51e0b7c0-d906-4372-ac57-7a49709b7c9f'),
       (801, '2021-11-11 19:29:17', '67299d99-b8b0-415d-818d-5ddf6685ab6c'),
       (802, '2021-11-11 19:57:14', 'd06e4eea-7580-4d77-b890-b64f5dd094e2'),
       (803, '2021-11-12 04:10:01', 'f8989c11-c082-45b0-82b3-204a258a72e6'),
       (804, '2021-11-12 04:34:25', 'dadc1135-33d2-47bc-93f0-a28010187e95'),
       (805, '2021-11-12 05:16:36', 'c90e54df-77d0-4ffd-8e75-6da38c2ee7ed'),
       (806, '2021-11-12 07:08:01', 'b8f864b6-18d7-4f16-b7b9-e1d21f9b20d8'),
       (807, '2021-11-13 08:03:19', '98b3afa8-b3d0-4bce-ae97-29858d80aa67'),
       (808, '2021-11-13 21:43:42', '96032611-48f7-4b31-bd64-b6926f6fdda4'),
       (809, '2021-11-13 21:48:21', 'c51e7e10-d46d-4993-bfbd-029322e2e581'),
       (810, '2021-11-13 22:02:45', '15a1d2da-b605-4735-a6e0-87cc1a5780c0'),
       (811, '2021-11-13 22:11:36', '5d21a16f-cad0-472e-99c9-c6266c9c4656'),
       (812, '2021-11-13 22:15:48', '7afdea20-3718-4fe6-a73c-ab0b75433994'),
       (813, '2021-11-14 03:30:43', '3dda2005-10be-4568-8704-ba05f3976ce0'),
       (814, '2021-11-14 07:11:54', 'f8d9be67-9ab7-4b26-a935-ea28f37d7bc1'),
       (815, '2021-11-14 07:27:53', '43442d83-1bf5-4811-b23c-55801230d229'),
       (816, '2021-11-14 07:28:39', 'a7c51020-dd68-48ca-a3ee-3bb23eb11d3c'),
       (817, '2021-11-14 16:01:00', 'e1f0054e-bc21-49e3-a8c9-d9533d4372b3'),
       (818, '2021-11-14 16:50:19', '88966bd2-e57f-4647-81ff-d453497efaf3'),
       (819, '2021-11-14 16:52:54', '3a2465c9-83f3-4f53-a0cf-5232a482c77a'),
       (820, '2021-11-14 17:58:42', 'd1ccb72d-f660-4478-9e42-f35687d63b7c'),
       (821, '2021-11-14 17:59:40', 'ce535b0e-58bc-48e6-a25e-55546b792fe5'),
       (822, '2021-11-14 18:02:49', 'ecca2b54-f5ce-47b8-ab62-a4ae91192bbd'),
       (823, '2021-11-14 18:03:40', '238de071-f511-41fd-9947-e3d080487641'),
       (824, '2021-11-14 18:04:13', 'ecc280b9-a0fe-415e-96f5-8c10013e23af'),
       (825, '2021-11-14 18:05:12', '7707e8ad-d8ea-47da-bb57-2a28110d6533'),
       (826, '2021-11-14 18:06:48', 'e17b6244-45d2-419c-98d0-4047cfffb28f'),
       (827, '2021-11-14 18:09:15', '312ba382-795d-4c18-95bd-5b471d259215'),
       (828, '2021-11-14 18:17:24', 'be200d95-6e98-424b-a056-c25a8d3531b7'),
       (829, '2021-11-14 18:22:58', '4e5af551-e9a0-4708-ab09-618653d314f6'),
       (830, '2021-11-14 18:35:47', 'b2690e1a-b3a6-4be3-bc3f-7551a3ce51f1'),
       (831, '2021-11-14 18:36:17', 'ec48f1ef-a9b2-4350-914a-4e7513f753d5'),
       (832, '2021-11-14 18:48:07', 'bfb6037a-2de9-4bcb-81a6-641ec41a10dc'),
       (833, '2021-11-14 18:48:40', '5b6b1226-3674-4708-8958-61a1b1fdcd5e'),
       (834, '2021-11-15 05:03:44', 'd7acf707-aaf6-45d1-be2d-5422e634aa11'),
       (835, '2021-11-15 05:03:56', '7252d057-948b-497e-a535-d2a8bcd10d44'),
       (836, '2021-11-15 05:04:27', '5be07650-0ba0-47f1-9f0c-05fd863f3c3d'),
       (837, '2021-11-15 05:05:50', 'afbbb707-c0d2-4de7-a8fb-2af50bc33677'),
       (838, '2021-11-15 05:06:25', '7253b801-f9ef-443e-a435-a4fb311f84da'),
       (839, '2021-11-15 05:07:17', 'f5f35e59-dcbc-49c5-a0b3-020d18a693e3'),
       (840, '2021-11-15 05:08:26', '48a9dea1-2663-449e-aee4-831261c2add0'),
       (841, '2021-11-15 05:16:05', 'c0b38b20-c78c-4a96-905d-050ac4dfdbb1'),
       (842, '2021-11-15 05:20:06', '4728c063-e737-44b2-80f6-ffdcddc26493'),
       (843, '2021-11-15 05:34:23', 'cfa32965-e0f4-4464-b08b-bfafd2f71243'),
       (844, '2021-11-15 10:02:15', '10478232-7524-4ee6-b2dc-3241da82d19f'),
       (845, '2021-11-15 10:21:18', '4b9255e5-c6fa-419f-948f-fc18e2aa512d'),
       (846, '2021-11-15 11:04:26', 'bdb0bbae-b082-4968-8a43-d46427313d05'),
       (847, '2021-11-15 11:05:03', 'efafce43-75e3-4c37-8ec2-6d6e55219aed'),
       (848, '2021-11-15 11:55:28', '25bec27d-3522-4a5a-8ca1-013a8475187b'),
       (849, '2021-11-15 22:34:31', 'fb6fc2c6-0d43-4050-89e1-405fbf7730ae'),
       (850, '2021-11-15 23:02:30', '148ba9f0-e09a-4016-ad6c-cbac2ccf0189'),
       (851, '2021-11-17 00:11:03', 'adb67b28-d155-4f94-a849-3f015a5112d2'),
       (852, '2021-11-17 00:20:48', '83eca040-c0e3-41f1-bfbf-2fe8115a5c45'),
       (853, '2021-11-17 00:59:01', '007426e8-ac50-40d1-a0a2-c91735a7a091'),
       (854, '2021-11-18 01:31:34', '12d11c33-945c-4511-983c-f7e9937299ef'),
       (855, '2021-11-18 01:31:34', 'c875a237-d9fe-4edf-a757-bc6a5e5d1332'),
       (857, '2021-11-18 21:54:07', '3f55f64e-bb51-4fdd-98e1-4e06b4bbfee7'),
       (858, '2021-11-18 22:24:45', 'f6b40a6e-f80e-43b7-b43c-f2eeb8c58b93'),
       (859, '2021-11-18 23:03:44', '58a4ed18-de97-4fcb-8bfb-754ff5fe97af'),
       (860, '2021-11-18 23:16:56', 'e3d85643-7f35-436d-b5a9-6e8faf6c5346'),
       (861, '2021-11-18 23:17:49', '6f653a0c-1b45-4b37-b55a-61c7db9b8adb'),
       (862, '2021-11-19 01:56:15', '4f91bc0f-9e5e-496a-8b27-5216ecd34ec8'),
       (863, '2021-11-19 02:00:29', '93812996-8449-4255-9714-5ea36a9dccad'),
       (864, '2021-11-19 02:06:54', 'e00041c5-30af-4e6a-80db-f9b66c4f1838'),
       (865, '2021-11-19 06:50:04', 'b5ea8091-cb5f-42a1-9c4c-32f602495f7b'),
       (866, '2021-11-19 08:05:36', '7395fdf8-7bc8-4724-b9f7-33cff3d2d162'),
       (867, '2021-11-19 08:05:51', '70ed33b2-9511-41f3-9941-24a3eecf1670'),
       (868, '2021-11-19 08:14:10', '57dfa442-c1d5-4303-9a73-798b55bc27b3'),
       (869, '2021-11-19 12:49:27', 'be99fd4e-fbae-418b-9b17-0470a09de39d'),
       (870, '2021-11-19 13:45:37', 'b0fec7b9-2a71-40a7-a296-beb1db55d2dc'),
       (871, '2021-11-19 13:45:58', '9a53a4f4-d3da-4d59-a821-31c6e1cbeebe'),
       (872, '2021-11-19 16:20:02', 'f6a0e3c2-453e-49fc-8299-f3d2d061f1e5'),
       (873, '2021-11-19 19:39:31', '5470a2ba-90e9-4a75-a433-7be0da96e735'),
       (874, '2021-11-19 19:40:29', '6807b56b-3843-4234-aa7d-883cdc9a2fd4'),
       (875, '2021-11-19 19:43:26', 'd6c4fec1-9468-4812-bc3b-3dfc2feea104'),
       (876, '2021-11-19 19:44:16', 'bbbdaec0-6ada-4956-99d7-eba6b2129484'),
       (877, '2021-11-19 23:23:37', 'b7d27b6d-2802-4e59-9d44-d1529d74ba66'),
       (878, '2021-11-20 05:55:18', '8ec2c389-fe1f-459d-b4a3-42eee979cd4b'),
       (879, '2021-11-20 06:03:28', 'b584a595-61fe-4c97-b87e-b94085bf496e'),
       (880, '2021-11-20 06:08:21', 'bc811412-a7d8-44fb-859b-8160f4fdbcec'),
       (881, '2021-11-20 06:46:56', 'a9066ea3-b8b6-4ce5-b304-5044b0f6ec06'),
       (882, '2021-11-21 09:20:49', '3bddbf47-ca31-4be4-abdf-7ae3f5ddb132'),
       (883, '2021-11-22 05:26:17', '16fd673d-6c0e-4593-9881-185f85ea0488'),
       (884, '2021-11-22 06:21:28', '99e0a53b-7705-4f2b-acf0-e4604c3fa154'),
       (885, '2021-11-22 06:27:27', '70e3b310-8711-4548-b9ba-a784b096a7b8'),
       (886, '2021-11-22 06:28:16', 'd471b7a6-d176-4407-a6bf-0a6afd9dcda0'),
       (887, '2021-11-22 06:31:34', '801c3c51-da6c-454c-a198-37a6b2bb48a1'),
       (888, '2021-11-22 06:32:11', 'ae9d73b5-ba3f-465b-b84d-f3e58da45387'),
       (889, '2021-11-22 06:33:04', '2e6f428b-5f87-49dd-b750-4794c6290b6d'),
       (890, '2021-11-22 07:05:47', 'ffc2693e-4d65-4e25-a125-4259c5aad0b4');

DROP TABLE IF EXISTS `relation`;
CREATE TABLE `relation`
(
    `id`           bigint NOT NULL AUTO_INCREMENT,
    `confirmed_at` datetime DEFAULT NULL,
    `sent_at`      datetime DEFAULT NULL,
    `receiver_id`  bigint NOT NULL,
    `sender_id`    bigint NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKnfnboik5lv37rehgogdfthojk` (`receiver_id`),
    KEY `FK38wce4d5fg7hv0o3t0lpfclom` (`sender_id`),
    CONSTRAINT `FK38wce4d5fg7hv0o3t0lpfclom` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FKnfnboik5lv37rehgogdfthojk` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

INSERT INTO `relation` (`id`, `confirmed_at`, `sent_at`, `receiver_id`, `sender_id`)
VALUES (15, '2021-10-02 22:38:38', '2021-10-02 21:50:34', 1, 3),
       (25, NULL, '2021-10-02 21:50:35', 2, 3),
       (35, '2021-10-03 16:21:14', '2021-10-02 21:50:39', 4, 3),
       (45, '2021-10-05 19:35:54', '2021-10-02 21:50:40', 5, 3),
       (55, '2021-10-02 22:38:41', '2021-10-02 21:59:53', 1, 2),
       (65, NULL, '2021-10-03 16:21:08', 2, 4),
       (75, '2021-10-05 19:35:55', '2021-10-03 16:21:10', 5, 4),
       (85, '2021-10-04 07:36:32', '2021-10-03 16:21:12', 1, 4),
       (105, '2021-10-05 19:06:47', '2021-10-05 19:05:50', 15, 1),
       (115, '2021-10-17 18:11:26', '2021-10-05 19:06:56', 15, 4),
       (125, '2021-10-17 18:11:28', '2021-10-05 19:36:00', 15, 5),
       (135, '2021-10-09 00:11:52', '2021-10-06 10:00:22', 1, 25),
       (145, NULL, '2021-10-17 18:11:22', 2, 15),
       (155, NULL, '2021-10-17 18:11:24', 3, 15);

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `id`   bigint       NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

INSERT INTO `role` (`id`, `name`)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN'),
       (3, 'ROLE_JOB_SEEKER'),
       (4, 'ROLE_RECRUITER'),
       (5, 'ROLE_PREMIUM');

DROP TABLE IF EXISTS `ia_group`;
CREATE TABLE `ia_group`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `description` varchar(255) DEFAULT NULL,
    `name`        varchar(255) DEFAULT NULL,
    `user_id`     bigint       DEFAULT NULL,
    `created_at`  datetime     DEFAULT NULL,
    `image_id`    bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKt88xyv8u7r6f3m1byxhrfj4ck` (`user_id`),
    KEY `FKaav1cbg8lm2vr5fc20ksdva0s` (`image_id`),
    CONSTRAINT `FKaav1cbg8lm2vr5fc20ksdva0s` FOREIGN KEY (`image_id`) REFERENCES `file` (`id`),
    CONSTRAINT `FKt88xyv8u7r6f3m1byxhrfj4ck` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `ia_group` (`id`, `description`, `name`, `user_id`, `created_at`, `image_id`)
VALUES (33, 'Region Ile de France desc', 'Region Ile de France', 6, '2021-11-12 22:34:32', NULL),
       (34, '', 'test sans image', 6, '2021-11-12 23:38:39', NULL),
       (35, '', 'test', 6, '2021-11-13 01:02:10', NULL),
       (36, '', 'léo', 6, '2021-11-13 02:57:30', NULL),
       (38, 'test', 'ecran', 1, '2021-11-14 02:55:42', NULL),
       (39, 'fdsqfdsqfqsf', 'on fait des modifs !', 1, '2021-11-14 03:54:47', NULL),
       (40, '', '', 1, '2021-11-14 09:05:51', NULL),
       (41, '', 'github', 1, '2021-11-14 15:55:15', NULL);

DROP TABLE IF EXISTS `training`;
CREATE TABLE `training`
(
    `id`      bigint NOT NULL AUTO_INCREMENT,
    `date`    date         DEFAULT NULL,
    `label`   varchar(255) DEFAULT NULL,
    `school`  varchar(255) DEFAULT NULL,
    `user_id` bigint NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKfoy157kvf3lmam5gv5u2ty487` (`user_id`),
    CONSTRAINT `FKfoy157kvf3lmam5gv5u2ty487` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

INSERT INTO `training` (`id`, `date`, `label`, `school`, `user_id`)
VALUES (15, '2022-09-30', 'Mastère Expert en système d\'information', 'SUP DE VINCI', 3),
       (25, '2020-09-30', 'Bachelor Web', 'Hetic', 3),
       (35, '2018-08-31', 'Diplôme universitaire Informatique (DUT)', 'Institut universitaire Informatique (IUT) d\'Orsay', 3),
       (45, '2015-08-31', 'Baccalauréat SVT Série S spécialité Physique-Chimie', 'Lycée A. Einstein', 3),
       (55, '2022-09-29', 'Master Expert en systèmes d\'informations', 'Sup de Vinci', 2),
       (65, '2020-06-01', 'Licence en informatique', 'Université Paris Descartes', 2),
       (75, '2022-09-30', 'Titre RNCP de Niveau 7 Expert en Systèmes d’Informations', 'Sup de Vinci', 1),
       (85, '2019-10-31', 'Titre RNCP de Niveau 6 Concepteur Développeur d\'Applications Numériques', 'IPI Paris', 1),
       (95, '2006-06-30', 'DUT d\'informatique option génie logiciel', 'IUT d\'Orsay', 1),
       (105, '2019-09-30', 'BTS SIO', 'Lycée polyvalent de Cachan', 5),
       (115, '2020-09-29', 'Concepteur développeur d\'applications', 'Simplon.co', 5),
       (125, '2022-09-29', 'Master en informatique', 'Sup de Vinci', 5);

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
    KEY `FK5v5h53roftm0e90x45m6bh7al` (`image_id`),
    CONSTRAINT `FK5v5h53roftm0e90x45m6bh7al` FOREIGN KEY (`image_id`) REFERENCES `file` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

INSERT INTO `user` (`id`, `activated_at`, `activation_key`, `active`, `birthday`, `contribution`, `email`, `expectation`, `firstname`, `hobbies`, `lastname`, `password`, `phone_number`,
                    `presentation`, `purpose`, `registered_at`, `url`, `username`, `image_id`)
VALUES (1, '2021-09-24 23:11:23', '793d746c-36f8-4937-bda6-7d6222cf3f51', CONV('1', 2, 10) + 0, '1983-09-06', 'Mon expertise dans le domaine du développement logiciel', 'matthieu@mail.com',
        'Acquérir une expérience supplémentaire dans l\'élaboration de solutions informatiques', 'Matthieu', 'Equitation, programmation, snowboard', 'Audemard',
        '$2a$10$vmy1fqGnyW7ZN0RXww2j2uBue.A4/5CSEGX6rsROmWfpwr.0bTVWG', '0617243256',
        'Passionné par le développement, je cherche toujours à trouver les solutions les plus élégantes aux problèmes qui me sont donnés.', 'Avant tout je cherche à m\'épanouir dans mon métier',
        '2021-09-24 23:10:48', NULL, 'matthieu', NULL),
       (2, '2021-09-24 23:12:41', '35254265-fa9d-4514-ae23-f167a5f6a831', CONV('1', 2, 10) + 0, '2021-10-07', 'My knowledge as an Elf, having lived for thousands of years.', 'dylan@mail.com',
        'To help me throw the ring in Mount Doom.', 'Dylan', 'F1, Badminton, Pétanque, Anime', 'Brudey', '$2a$10$s7u8ams6mKx4HlzczmTpDu2r.GWNwxkQzfHar1Rq9YkovEE2rzdCu', '0920100451',
        'I was there, 3000 years ago.', 'Get the ring.', '2021-09-24 23:12:28', NULL, 'dylan', NULL),
       (3, '2021-09-24 23:14:33', '88381beb-dd09-45a0-9e9f-66e468347ab9', CONV('1', 2, 10) + 0, '1996-07-26', 'De la joie et de la bonne humeur et de l\'expertise et mon réseau.', 'mel@mail.com',
        'De la joie et de la bonne humeur :)', 'Mélanie', 'Badminton,\nPing pong,\njeux video,\nSéries', 'Da Costa', '$2a$10$mLj1NYUHlajXwPurnR2h6eCshIRu8w1clempU07UfCSyh6Dcy3z6S', '0606969696',
        'Geek dans l\'ame, je suis en train de terminer mon Mastère Expert en système d\'information', 'Réussir dans la vie :)', '2021-09-24 23:14:23', NULL, 'melanie', NULL),
       (4, '2021-09-24 23:15:21', '5ee1b136-1a89-48eb-bce7-241ef06b79de', CONV('1', 2, 10) + 0, '1996-10-06', 'De la bonne humeur et des compétences', 'paul@mail.com', 'Trouver un emploi', 'Paul',
        'Judo\nPhotographie', 'Flumian', '$2a$10$p1dW/T62V7C2vt2OK7dVruZVbQccT/GIetlSQc4Wh7jkariyBXq9W', '0606060606', NULL, 'Développeur informatique', '2021-09-24 23:15:11', NULL, 'paul', NULL),
       (5, '2021-09-24 23:17:43', 'b1e89b98-d2a3-4958-9a9c-270ab2c0439a', CONV('1', 2, 10) + 0, NULL, NULL, 'm@thusha.com', NULL, 'Mathusha', NULL, 'Thirulogasingam',
        '$2a$10$khA09HcY4cvY0B30.YVeJeVVqWjCMJ.0sfsvKCzmTKCnzQn5eV5b2', NULL, NULL, NULL, '2021-09-24 23:17:32', NULL, 'mathusha', NULL),
       (6, '2021-09-24 21:19:45', '35412334-173f-48c8-bca5-2829a9e4c0e3', CONV('1', 2, 10) + 0, NULL, NULL, 'admin@mail.com', NULL, '', NULL, '',
        '$2a$10$QvalNDrZUPqhLmrF4/ZPTeFWSA0CoefXwQ/uCYxpRF/vlkTXlNx1G', NULL, NULL, NULL, '2021-09-24 23:19:08', NULL, 'admin', NULL),
       (15, '2021-10-05 19:04:50', '1256b301-d43b-457f-8a47-449b043efad6', CONV('1', 2, 10) + 0, '1979-07-02', 'Bonne humeur, joie de vivre ainsi que de délicieuses spécialités venues des Balkans',
        'vlad.leban@gmail.com', 'Une main tendue riche d\'amitié', 'Vlad', 'Chasse, pêche et philatélie ', 'LEBAN', '$2a$10$v4LPrpkbwYh9PNoFMzd.juJAQyafdpOLc0dmd6a8uaEVPdhUqysMS', '0651299200',
        'Dans ma branche d\'activité il est de coutume de ne pas se présenter. ', ' ', '2021-10-05 19:04:20', NULL, 'Vlad', NULL),
       (25, '2021-10-06 09:57:38', 'ec806d76-344b-41c2-a71e-cd54c755a455', CONV('1', 2, 10) + 0, NULL, NULL, 'rodrig@aud.com', NULL, 'Steve', NULL, 'Rodrig',
        '$2a$10$NwZ6EknuC6FA/rxOKonHEuDC3bUIOjBvirj.MZmT1wjzgsuGA79xe', NULL, NULL, NULL, '2021-10-06 09:55:50', NULL, 'Rodrig', NULL),
       (35, '2021-10-16 14:31:16', '61785276-25b7-4e96-b723-2660402ad277', CONV('1', 2, 10) + 0, NULL, NULL, 'jean-francois.rouault@implicaction.eu', NULL, 'Jean-François', NULL, 'Rouault',
        '$2a$10$a25umCRgEC4/Y.xv8V0Wd.wZgqIrm/pvXxZu8ZXLVQOSDOdhqtvaa', NULL, NULL, NULL, '2021-10-11 14:16:00', NULL, 'jfrouault', NULL);

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`
(
    `user_id` bigint NOT NULL,
    `role_id` bigint NOT NULL,
    KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
    KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`),
    CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

INSERT INTO `user_role` (`user_id`, `role_id`)
VALUES (1, 1),
       (1, 3),
       (2, 1),
       (2, 3),
       (3, 1),
       (3, 3),
       (4, 1),
       (4, 3),
       (5, 1),
       (5, 3),
       (6, 1),
       (6, 2),
       (15, 1),
       (15, 3),
       (25, 1),
       (25, 3),
       (35, 1),
       (35, 3),
       (1, 5),
       (2, 5),
       (3, 5),
       (4, 5),
       (5, 5);

DROP TABLE IF EXISTS `vote`;
CREATE TABLE `vote`
(
    `id`        bigint NOT NULL AUTO_INCREMENT,
    `vote_type` int    DEFAULT NULL,
    `post_id`   bigint DEFAULT NULL,
    `user_id`   bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKcsaksoe2iepaj8birrmithwve` (`user_id`),
    KEY `FKl3c067ewaw5xktl5cjvniv3e9` (`post_id`),
    CONSTRAINT `FKcsaksoe2iepaj8birrmithwve` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FKl3c067ewaw5xktl5cjvniv3e9` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


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
    KEY `FKhnxjamc0hrv0uok9w7aayk6kk` (`user_id`),
    CONSTRAINT `FKhnxjamc0hrv0uok9w7aayk6kk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

INSERT INTO `work_experience` (`id`, `company_name`, `description`, `finished_at`, `label`, `started_at`, `user_id`)
VALUES (15, 'Ivalua', 'Alternance', '2022-09-30', 'Ingénieur informaticien c#', '2018-09-04', 3),
       (25, 'SustainEcho', 'Développe une application d\'analyse de bilan carbone pour les entreprises du bâtiment.', '2021-09-29', 'Développeur Frontend', '2021-09-28', 2),
       (35, 'Société Générale', 'Concevoir un portail d\'achat de serveurs et de pods en interne.', '2021-08-29', 'Développeur FullStack', '2020-09-30', 2),
       (45, 'Société Générale',
        'Au sein d\'une équipe scrum, j\'ai en charge la conception d\'une application de gestion de projet collaboratif.\n\n#git #java11 - #spring-boot -  #angular12 - #mongodb', NULL,
        'Développeur Fullstack (alternance)', '2020-10-05', 1),
       (55, 'CGI',
        'En prestation au sein de l\'Office Nationale des Forêts, projet de maintenance évolutive et corrective d\'une application de gestion de flux opérationnels.\n\n#java7 - #javaEE - #oracle - #svn ',
        '2019-11-30', 'Développeur JavaEE (alternance)', '2017-12-01', 1),
       (65, 'Universal Music Mobile', 'Maintenance évolutive et corrective d\'un intranet et d\'un outil de distribution de contenu numérique\n\n#sql-server - #visual-basic - #c-sharp', '2006-06-30',
        'Dévelppeur .NET (stage)', '2006-04-03', 1),
       (75, 'Laboratoire de recherche en informatique d\'Orsay', 'Conception et création d\'outils d\'accès à une base de données de segments d\'ARN\n\n#php - #mysql - #bio-informatique',
        '2005-07-01', 'Développeur PHP (stage)', '2005-04-04', 1),
       (85, 'Lycée polyvalent de Cachan', 'Refonte du site de Loisirs-sur-mesure en PHP/HTML/CSS/CAKEPHP et suivi avec Google Analytics\n', '2018-07-31', 'Stagiaire développement web', '2018-05-31',
        5),
       (95, 'Loisirs-sur-mesure', 'Refonte du site du Lycée Polyvalent de Cachan à l\'aide de wo', '2019-02-28', 'Stagiaire développement web', '2018-12-31', 5),
       (105, 'Idemia', '', '2021-10-04', 'Dev WEB', '2020-10-11', 4),
       (115, 'Wavestone', '', '2020-01-25', 'Consultant', '2019-10-05', 4),
       (125, 'COGIP',
        'En tant que chef d\'étage à moustaches, ma tâche consistait essentiellement à motiver le personnel en instaurant des moments privilégiés à la cantine autour d\'un céleri rémoulade ou d\'un couscous de la mer.',
        '2021-09-10', 'Chef d\'étage à moustaches', '2021-02-28', 15);


ALTER TABLE `job_posting`
    ADD `short_description` longtext NULL AFTER `description`;


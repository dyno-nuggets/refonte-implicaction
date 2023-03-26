-- Adminer 4.8.1 MySQL 8.0.32 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `text` text,
                           `post_id` bigint DEFAULT NULL,
                           `user_id` bigint DEFAULT NULL,
                           `created_at` datetime DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           KEY `FK8kcum44fvpupyw6f5baccx25c` (`user_id`),
                           KEY `FKs1slvnkuemjsq2kj4h3vhx7i1` (`post_id`),
                           CONSTRAINT `FK8kcum44fvpupyw6f5baccx25c` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                           CONSTRAINT `FKs1slvnkuemjsq2kj4h3vhx7i1` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `description` longtext,
                           `logo` varchar(255) DEFAULT NULL,
                           `name` varchar(255) NOT NULL,
                           `url` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO `company` (`id`, `description`, `logo`, `name`, `url`) VALUES
                                                                       (145,	'<p><strong>ManpowerGroup</strong> est une entreprise multinationale spécialisée dans le conseil en ressources humaines, le recrutement, le travail intérimaire et les services aux infrastructures informatiques.</p>',	'https://i1.wp.com/www.ilcao.org/wp-content/uploads/2019/09/manpower-logo.png?resize=400%2C400&ssl=1',	'Manpower',	'https://www.manpower.fr/'),
                                                                       (155,	'<p>Amazon &nbsp;est une entreprise de commerce en ligne américaine basée à Seattle. Elle est l\'un des géants du Web,\r\n        regroupés sous l\'acronyme GAFAM10, aux côtés de Google, Apple, Facebook et Microsoft. Créée par Jeff Bezos en juillet 1994, l\'entreprise a été introduite en bourse au NASDAQ en mai 1997.</p>',	'https://www.sportbuzzbusiness.fr/wp-content/uploads/2016/04/amazon-fr.png',	'Amazon',	'https://www.amazon.fr/'),
(165,	'<p>Spécialiste de la motoculture et du matériel pour espaces verts depuis plus de 20 ans,\r\n        Greenmat vous propose une large gamme d’outils de qualité adaptés aux besoins des particuliers et des professionnels.</p>',	'https://media.cylex-locale.fr/companies/1309/9500/logo/logo.jpg',	'Greenmat',	'https://greenmat.eu/'),
(175,	'<p>Irisiôme est une entreprise de l’agglomération bordelaise créée en 2015,\r\n        qui a conçu et aujourd’hui commercialise un dispositif médical de nouvelle génération visant un marché considérable en France,\r\n        en Europe mais aussi dans le reste du monde.</p><p><br></p><p>Ce dispositif s’appuie sur des lasers innovants à base de fibres optiques et délivrant des impulsions ultracourtes.</p><p>Le marché visé est celui de la dermato-esthétique avec des applications bien implantées comme l’épilation laser ou promises à de belles croissance comme le détatouage.</p><p><br></p><p>Notre promesse de proposer des soins moins douloureux et plus rapides nous a d’ores et déjà permis de gagner la confiance de professionnels de santé de renommée internationale.</p>',	'https://www.apec.fr/files/live/mounts/images/media_entreprise/728145/logo_IRISIOME_728145_783558.png',	'Irisiome',	'http://irisiome.com/'),
(185,	'<p>Idée Blanche recrutement est&nbsp;\r\nspécialisé\r\ndans les domaines du recrutement et des prestations d\r\n\'audit, d\'évaluation et de conseil, notre mission est d\r\n\'aider nos clients à réussir en donnant du sens et de la valeur à chacune de nos actions.</p><p><br></p><p>A l\'écoute de votre projet au travers d\r\n\'une compréhension juste de vos besoins, nous dessinons ensemble la stratégie et les outils adaptés afin de garantir des résultats à courts termes. Nous intervenons pour :</p><p><br></p><p>AUDITER et EXPERTISER les pratiques RH de l\'entreprise</p><p>RECRUTER et ACCOMPAGNER les équipes et leur management</p><p>SUIVRE et ACCOMPAGNER l\r\n\'intégration des collaborateurs recrutés</p><p><br></p><p>La réussite d\'une politique RH au sein de l\r\n\'entreprise est liée à la capacité de son équipe dirigeante à être courageuse.</p><p><br></p>',	'https://www.apec.fr/files/live/mounts/images/media_entreprise/716385/logo_ALFID_RECRUTEMENT_CONSEIL_716385_895962.png',	'Idée Blanche',	'http://ideeblanche.fr/');

DROP TABLE IF EXISTS `feature`;
CREATE TABLE `feature` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `active` bit(1) DEFAULT NULL,
                           `feature_key` int NOT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `feature` (`id`, `active`, `feature_key`) VALUES
    (1,	CONV('1', 2, 10) + 0,	0);

DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
                        `id` bigint NOT NULL,
                        `content_type` varchar(255) DEFAULT NULL,
                        `filename` varchar(255) DEFAULT NULL,
                        `url` varchar(255) DEFAULT NULL,
                        `uploader_id` bigint DEFAULT NULL,
                        `object_key` varchar(255) DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        KEY `FKgvpcrd2olmq75ujpppe0jn3cj` (`uploader_id`),
                        CONSTRAINT `FKgvpcrd2olmq75ujpppe0jn3cj` FOREIGN KEY (`uploader_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
    `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
    (63);

DROP TABLE IF EXISTS `ia_group`;
CREATE TABLE `ia_group` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `description` varchar(255) DEFAULT NULL,
                            `name` varchar(255) DEFAULT NULL,
                            `user_id` bigint DEFAULT NULL,
                            `created_at` datetime DEFAULT NULL,
                            `image_id` bigint DEFAULT NULL,
                            `valid` bit(1) DEFAULT NULL,
                            `profile_user_id` bigint DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            KEY `FKt88xyv8u7r6f3m1byxhrfj4ck` (`user_id`),
                            KEY `FKaav1cbg8lm2vr5fc20ksdva0s` (`image_id`),
                            KEY `FKj6ody68ev0aysn7ph67c6g21w` (`profile_user_id`),
                            CONSTRAINT `FKaav1cbg8lm2vr5fc20ksdva0s` FOREIGN KEY (`image_id`) REFERENCES `file` (`id`),
                            CONSTRAINT `FKj6ody68ev0aysn7ph67c6g21w` FOREIGN KEY (`profile_user_id`) REFERENCES `profile` (`user_id`),
                            CONSTRAINT `FKt88xyv8u7r6f3m1byxhrfj4ck` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `job_application`;
CREATE TABLE `job_application` (
                                   `id` bigint NOT NULL AUTO_INCREMENT,
                                   `last_update` datetime DEFAULT NULL,
                                   `status` varchar(255) DEFAULT NULL,
                                   `job_id` bigint NOT NULL,
                                   `user_id` bigint NOT NULL,
                                   `archive` tinyint(1) DEFAULT '0',
                                   PRIMARY KEY (`id`),
                                   KEY `FKobel3nnbi451ftywx9q9m2qax` (`job_id`),
                                   KEY `FKcbu1yb4kyxowejebm87crxtr8` (`user_id`),
                                   CONSTRAINT `FKcbu1yb4kyxowejebm87crxtr8` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                                   CONSTRAINT `FKobel3nnbi451ftywx9q9m2qax` FOREIGN KEY (`job_id`) REFERENCES `job_posting` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `job_application` (`id`, `last_update`, `status`, `job_id`, `user_id`, `archive`) VALUES
                                                                                                  (55,	'2023-03-21 18:59:10',	'PENDING',	165,	1,	0),
                                                                                                  (56,	'2022-10-24 09:36:31',	'CHASED',	155,	1,	0);

DROP TABLE IF EXISTS `job_posting`;
CREATE TABLE `job_posting` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `description` longtext,
                               `keywords` varchar(255) DEFAULT NULL,
                               `location` varchar(255) DEFAULT NULL,
                               `salary` varchar(255) DEFAULT NULL,
                               `title` varchar(255) NOT NULL,
                               `company_id` bigint NOT NULL,
                               `created_at` datetime DEFAULT NULL,
                               `short_description` text,
                               `archive` bit(1) DEFAULT NULL,
                               `contract_type` varchar(255) DEFAULT NULL,
                               `active` bit(1) DEFAULT NULL,
                               `business_sector` varchar(255) DEFAULT NULL,
                               `valid` bit(1) DEFAULT NULL,
                               `posted_by` bigint DEFAULT NULL,
                               PRIMARY KEY (`id`),
                               KEY `FKd9euom18rv4nilau4hblm08cv` (`company_id`),
                               KEY `FKd21ec91nnjftglc01m2dg70yr` (`posted_by`),
                               CONSTRAINT `FKd21ec91nnjftglc01m2dg70yr` FOREIGN KEY (`posted_by`) REFERENCES `user` (`id`),
                               CONSTRAINT `FKd9euom18rv4nilau4hblm08cv` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO `job_posting` (`id`, `description`, `keywords`, `location`, `salary`, `title`, `company_id`, `created_at`, `short_description`, `archive`, `contract_type`, `active`, `business_sector`, `valid`, `posted_by`) VALUES
    (125,	'<h2>L\'entreprise</h2><p>Manpower Conseil Recrutement recrute pour un de ses clients, <strong> un\r\n        technicien de maintenance - électromécanicien de maintenance H / F en CDI</ strong></ p><p><br></ p><p>Notre\r\n        client est un industriel spécialisé dans l\'agroalimentaire reconnue pour la qualité de ses produits et son efficience industrielle.</p><p><br></p><h2>Poste proposé</h2><p>Au sein de l\'\r\n        équipe Maintenance,\r\n        vous avez comme mission principale <strong>la réalisation des travaux de maintenance préventive et corrective des installations de production et ce afin de maintenir l’outil de production en état de fonctionnement,\r\n        de sécurité et performance.</strong></p><p><br></p><p>Pour ce faire, vous assurez&nbsp;\r\n:\r\n</p><ul><li>le réglage des lignes de production.</li><li>l\r\n’entretien de l\r\n’outil de production selon le plan de maintenance préventive.</li><li>le dépannage des installations et des matériels en cas de pannes mécanique, pneumatique et électrique.</li><li>le reporting sous le système GMAO et MES des interventions.</li></ul><p><br></p><p>A ce poste, vous travaillez seul ou en équipe et en étroite collaboration avec les équipes de production et d\r\n’amélioration continue.</p><h2>Conditions de travail</h2><p>Poste en 3*8.</p><p>Travail week end et jours fériés selon impératifs d\r\n\'activité.</p><h2>Profil recherché</h2><p>De formation de BAC STI Génie mécanique, complétée idéalement par un BTS Maintenance Industrielle ou Maintenance Automatismes Industriels, vous justifiez une expérience de 3 ans minimum à un poste d’agent ou de technicien de maintenance industrielle.</p><p><br></p><p><strong>Vous êtes reconnu(e) pour vos compétences techniques en mécanique, électricité et&nbsp;pneumatique. Des notions de soudure TIG représentent un réel plus.</strong></p><p><br></p><p>Polyvalent(e) et autonome, vous faîtes preuve de rigueur et d\'organisation dans l\r\n\'exécution du travail.</p><p>Réactif (ve) et Volontaire, vous savez vous adapter pour faire face aux aléas d\'activité avec calme et réflexion</p><p>Force de proposition, vous n\r\n\'hésitez pas à préconiser de nouvelles solutions dans un esprit d\'amélioration continue.</p><h2>Salaire et accessoires</h2><p>Salaire fixe à négocier selon profil et expérience.</p><p>Avantages d\r\n’entreprise :&nbsp;13ème\r\nmois. Prime annuelle sur objectifs. Participation. Mutuelle.</p><p><br></p>',	'',	'\r\n55100 Verdun',	'A négocier',	'Technicien de maintenance - électromécanicien de maintenance H/F',	145,	NULL,	'<p>Manpower Conseil Recrutement recrute pour un de ses clients,<strong> un technicien de maintenance - électromécanicien de maintenance H/F en CDI</strong></p>',	CONV('0', 2, 10) + 0,	'INTERIM',	NULL,	'AGROALIMENTAIRE',	CONV('1', 2, 10) + 0,	NULL),
(135,	'<p>Amazon recherche un(e) Technicien(ne) de maintenance pour rejoindre l\r\n\'équipe de l\'un de ses centres de distribution et y travailler dans un environnement dynamique.</p><p><br></p><p>Le/La Technicien(ne) de maintenance veille au respect des directives et réglementations de sécurité, est chargé(e) de coordonner l\r\n\'entretien préventif des installations dans les délais prévus et à un niveau d\'exigence élevé, et garantit également la haute disponibilité des équipements par la mise en place d\r\n\'actions d\'amélioration continue.</p><ul><li>Faire des meilleures pratiques de santé et de sécurité une priorité sur l\r\n\'ensemble des projets.</li><li>Réaliser des tâches planifiées d\'entretien préventif sur l\r\n\'ensemble des équipements du site.</li><li>Intervenir rapidement en cas de panne, communiquer clairement avec les acteurs concernés et travailler efficacement à la résolution du problème.</li><li>Encourager les pratiques d\'amélioration continue en tirant des leçons des pannes précédentes et en transmettant des avis et des suggestions d\r\n\'amélioration via le responsable hiérarchique.</li><li>Garantir la haute disponibilité des équipements pour nos clients internes.</li><li>Assister les Technicien(ne)s de maintenance confirmé(e)s et se former à leurs côtés.</li></ul><h2>Votre profil</h2><ul><li>Compétences polyvalentes en électricité et en mécanique.</li><li>Connaissance pratique des systèmes de planification d\'entretiens préventifs.</li><li>Expérience en recherche de pannes dans les systèmes d\r\n\'automatisation/équipements de manutention.</li><li>Aptitude à lire et comprendre des schémas mécaniques et électriques.</li><li>Compétences de base en gestion de plates-formes.</li><li>Expérience dans l\'entretien de convoyeurs, de commandes de moteur et d\r\n\'onduleurs.</li><li>Expérience de travail dans le respect des normes et réglementations de santé et de sécurité en vigueur.</li><li>Expérience en gestion de prestataires de sous-traitance.</li><li>Capacité à travailler par équipe et en rotation dans un environnement disponible 24 h/24, 7 jours/7.</li></ul><h2>Qualifications appréciées</h2><ul><li>Expérience en commutation haute tension.</li><li>Expérience en machines de tri.</li><li>Expérience en gestion/configuration de lecteurs de codes-barres.</li><li>Expérience en machines d\'impression-pose.</li></ul><p><br></p><p>Nous attendons ta candidature avec impatience (de préférence en anglais). Poste ouvert aux personnes en situation de handicap.</p><p><br></p>',	'',	'Montélimar, ARA',	'A négocier',	'Technicien de Maintenance (H/F)',	155,	'2021-11-15 06:01:39',	'<p>Amazon recherche un(e) Technicien(ne) de maintenance pour rejoindre l\r\n\'équipe de l\'un de ses centres de distribution et y travailler dans un environnement dynamique.</p>',	CONV('0', 2, 10) + 0,	'CDI',	NULL,	'ASSURANCE',	CONV('1', 2, 10) + 0,	NULL),
(145,	'<h2>Description du poste</h2><p>La société GREENMAT spécialisée dans la vente et l\r\n’entretien de matériels de parcs et jardins depuis plus de 25 ans recherche un mécanicien (H/F) pour renforcer son équipe de SAV. L\r\n’entreprise est concessionnaire des marques suivantes : Iseki, Stihl, Reform, etc\r\n… .</p><p><br></p><p>Le mécanicien sera chargé de l\r\n’entretien et la réparation du matériel de nos clients. Le technicien sera amené à travailler dans la zone d\r\n’activité de l\r\n’entreprise ou sur les lieux de panne des matériels. Le technicien sera chargé d\r\n’assurer une relation de confiance entre le client et le Service Après-Vente de la concession. Les postes sont à pouvoir en CDI pour nos magasins situés à Chailly-en-Bière (77930) </p><p><br></p><h2>Qualités Requises :</h2><ul><li>Connaissance des moteurs essences et diesel</li><li>Connaissance en hydraulique</li><li>Connaissance en électricité</li><li>Savoir manipuler les principaux matériels</li><li>Goût du relationnel</li><li>Autonome, Responsable</li><li>Méticuleux, méthodique</li><li>S\r\n’adapter et avoir l\r\n’envie de se former aux nouvelles technologies et aux nouveaux matériels</li><li>Diplomate, être à l\r\n’écoute des problématiques des clients</li></ul>',	'',	'Chailly-en-Bière 77930\r\n',	'2500\r\n',	'Spécialiste vente',	165,	NULL,	'<p>La société GREENMAT spécialisée dans la vente et l\r\n’entretien de matériels de parcs et jardins depuis plus de 25 ans recherche un mécanicien (H/F) pour renforcer son équipe de SAV. L\r\n’entreprise est concessionnaire des marques suivantes : Iseki, Stihl, Reform, etc\r\n…dsqdf</p>',	CONV('0', 2, 10) + 0,	'CDD',	NULL,	'BANQUE',	CONV('1', 2, 10) + 0,	NULL),
(155,	'<h2>Descriptif du poste</h2><p>Au titre d\r\n’ingénieur(e) de production, vous êtes responsable d\r\n’un atelier de fabrication. Vous participez au respect des contraintes de coûts, de qualité, de délais et de la réglementation dans la production des systèmes. Vous supervisez des lignes de production et encadrez une équipe. Vous devez mettre en place et contrôler les processus de fabrication, et devez trouver des solutions pour atteindre les objectifs fixés.</p><p><br></p><p>Vous serez ainsi amené(e) à :</p><ul><li>&nbsp;Organiser\r\nun atelier et une ligne de production, suivre les outils de productions et de métrologie (Calibration, maintenance).</li><li>Organiser et suivre les approvisionnements.</li><li>Assurer le suivi de la sous-traitance pour la conception électronique ou mécanique.</li><li>Suivre le bon déroulement de la fabrication</li><li>Optimiser les processus de fabrication et de l\r\n’appareil de production</li><li>Encadrer une équipe de production</li><li>Gérer l\r\n’activité de production et assurer le reporting hebdomadaire.</li><li>Faire remonter les problèmes de production à la direction technique et proposer de nouveaux développements dans leur passage en production (Rédaction documentation interne, instructions de travail).</li><li><br></li></ul><p>Vous évoluez au contact de collaborateurs multidisciplinaires et dans un contexte international. Vous serez amené(e) à communiquer régulièrement en Anglais.</p><h2>Profil recherché</h2><p>De formation niveau Bac + 4 ou 5 (écoles d\r\n’ingénieurs généralistes)&nbsp;ou\r\ntechnicien supérieur justifiant d\r\n’une solide expérience, vous disposez d\r\n’une expérience dans l\r\n’industrie.</p><p><br></p><p>Vous êtes polyvalent avec une volonté de maîtriser à la fois des compétences techniques et managériales. Vous êtes rigoureux et méthodique avec des qualités organisationnelles et relationnelles qui vous permettent de gérer le travail des équipes et les relations avec les interlocuteurs internes et externes à l\r\n\'entreprise.</p><p><br></p><p>Vous devrez fait preuve de dynamisme et de réactivité pour faire face aux situations imprévues.</p><p><br></p><p>Vous êtes autonome et doté(e) d’un esprit d’équipe, vous avez une bonne habilité à communiquer. Vous êtes force de propositions dans les tâches qui vous sont confiées.</p><h2>Entreprise</h2><p>Irisiôme est une entreprise de l’agglomération bordelaise créée en 2015, qui a conçu et aujourd’hui commercialise un dispositif médical de nouvelle génération visant un marché considérable en France, en Europe mais aussi dans le reste du monde.</p><p>Ce dispositif s’appuie sur des lasers innovants à base de fibres optiques et délivrant des impulsions ultracourtes.</p><p>Le marché visé est celui de la dermato-esthétique avec des applications bien implantées comme l’épilation laser ou promises à de belles croissance comme le détatouage.</p><p>Notre promesse de proposer des soins moins douloureux et plus rapides nous a d’ores et déjà permis de gagner la confiance de professionnels de santé de renommée internationale.</p><p><br></p><p><br></p><p><br></p>',	'',	'Pessac - 33',	'A partir de 37 k€ brut annuel',	'Ingénieur de production F/H',	175,	'2021-11-15 06:18:49',	'<p>Au titre d’ingénieur(e) de production, vous êtes responsable d’un atelier de fabrication. Vous participez au respect des contraintes de coûts, de qualité, de délais et de la réglementation dans la production des systèmes. Vous supervisez des lignes de production et encadrez une équipe. Vous devez mettre en place et contrôler les processus de fabrication, et devez trouver des solutions pour atteindre les objectifs fixés.</p>',	CONV('0', 2, 10) + 0,	'CDI',	NULL,	'BTP',	CONV('1', 2, 10) + 0,	NULL),
(165,	'<h2>Descriptif du poste</h2><p>Idée Blanche recrutement recherche pour un de ses clients, groupe industriel spécialiste des procédés spéciaux et de l’usinage dans le domaine aéronautique/énergie/automobile, un opérateur commande numérique.</p><h2>Votre mission</h2><p>Intégré au sein de l’équipe production,&nbsp;vos missions seront les suivantes&nbsp;:</p><ul><li>Prendre connaissance de l’ensemble des documents fournis&nbsp;: fiche suiveuse, plan, gamme d’usinage…</li><li>Monter et régler les outillages de fabrication</li><li>Monter / démonter les pièces</li><li>Connaitre / analyser les arrêts machines</li><li>Savoir choisir et utiliser les moyens de contrôles nécessaires.</li><li>Contrôler la production</li></ul><p>Réglage/ usinage / changement de série</p><ul><li>Réglage du moyen de production dans le respect des règles (Dossier d’instruction / Fiche d’instruction)&nbsp;:</li><li>Maitriser&nbsp;: Centrage, dégauchissage.</li><li>Assurer le suivi et la fréquence de Changement d’outils</li><li>Compréhension et lecture d’un programme (Connaitre les codes «&nbsp;iso&nbsp;» dans les programmes).</li><li>Assurer les modifications de programmation&nbsp;après aval du responsable. </li></ul><p>Gestion d’anomalies</p><ul><li>Gestion des Non-Conformités&nbsp;:</li><li>Identifier&nbsp;: repérer et étiqueter les Non-Conformités suivant les règles qualités</li><li>Trier&nbsp;: vérifier la conformité des pièces précédentes</li><li>Corriger pour supprimer la cause du défaut</li><li>Ouverture/participation au conseil de décision (en cas de non-conformité)</li><li>Appréhender le risque sécurité</li><li>Gérer les arrêts machines / rendre compte au responsable direct.</li></ul><h2>Profil recherché</h2><p>De formation supérieure Bac+2/+3 en mécanique, vous justifiez d\'une expérience professionnelle significative dans une fonction similaire au sein d\r\n’un groupe industriel reconnu.</p><h2>Entreprise</h2><p>Idée Blanche recrutement est&nbsp;spécialisé dans les domaines du recrutement et des prestations d\r\n\'audit, d\'évaluation et de conseil, notre mission est d\r\n\'aider nos clients à réussir en donnant du sens et de la valeur à chacune de nos actions.</p><p><br></p><p>A l\'écoute de votre projet au travers d\r\n\'une compréhension juste de vos besoins, nous dessinons ensemble la stratégie et les outils adaptés afin de garantir des résultats à courts termes. Nous intervenons pour :</p><p><br></p><p>AUDITER et EXPERTISER les pratiques RH de l\'entreprise</p><p>RECRUTER et ACCOMPAGNER les équipes et leur management</p><p>SUIVRE et ACCOMPAGNER l\r\n\'intégration des collaborateurs recrutés</p><p><br></p><p>La réussite d\'une politique RH au sein de l\r\n\'entreprise est liée à la capacité de son équipe dirigeante à être courageuse.</p><p><br></p>',	'',	'Marmande - 47',	'A partir de 25 k€ brut annuel',	'OPÉRATEUR COMMANDE NUMÉRIQUE F/H',	185,	'2021-11-15 06:27:28',	'<p>Idée Blanche recrutement recherche pour un de ses clients, groupe industriel spécialiste des procédés spéciaux et de l’usinage dans le domaine aéronautique/énergie/automobile, un opérateur commande numérique.</p>',	CONV('0', 2, 10) + 0,	'CDI',	NULL,	'CHIMIE',	CONV('1', 2, 10) + 0,	NULL);

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
                                                                                                               (25,	'2021-12-11 16:37:31',	'<p>Bienvenue à tout le monde ^^ </p>',	NULL,	1,	NULL,	3,	'Petite publication pour souhaiter la bienvenue à tout le réseau d\'implic\'action'),
                                                                                                               (26,	'2022-04-01 12:07:35',	'string',	'string',	0,	NULL,	1,	'string');

DROP TABLE IF EXISTS `profile`;
CREATE TABLE `profile` (
                           `user_id` bigint NOT NULL,
                           `contribution` varchar(255) DEFAULT NULL,
                           `expectation` varchar(255) DEFAULT NULL,
                           `hobbies` varchar(255) DEFAULT NULL,
                           `phone_number` varchar(255) DEFAULT NULL,
                           `presentation` varchar(255) DEFAULT NULL,
                           `purpose` varchar(255) DEFAULT NULL,
                           `avatar_id` bigint DEFAULT NULL,
                           PRIMARY KEY (`user_id`),
                           KEY `FKpi27i7jdpqrmdegvmuytpg512` (`avatar_id`),
                           CONSTRAINT `FKawh070wpue34wqvytjqr4hj5e` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                           CONSTRAINT `FKpi27i7jdpqrmdegvmuytpg512` FOREIGN KEY (`avatar_id`) REFERENCES `file` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `profile` (`user_id`, `contribution`, `expectation`, `hobbies`, `phone_number`, `presentation`, `purpose`, `avatar_id`) VALUES
                                                                                                                                        (1,	'Mon expertise dans le domaine du développement logiciel',	'Acquérir une expérience supplémentaire dans l\'élaboration de solutions informatiques',	'Equitation, programmation, snowboard',	'066666688',	'Passionné par le développement, je cherche toujours à trouver les solutions les plus élégantes aux problèmes qui me sont donnés.',	'Avant tout je cherche à m\'épanouir dans mon métier',	NULL),
                                                                                                                                        (2,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL),
                                                                                                                                        (3,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL),
                                                                                                                                        (4,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL),
                                                                                                                                        (5,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL),
                                                                                                                                        (15,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL),
                                                                                                                                        (68,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL),
                                                                                                                                        (69,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL);

DROP TABLE IF EXISTS `profile_group`;
CREATE TABLE `profile_group` (
                                 `user_id` bigint NOT NULL,
                                 `group_id` bigint NOT NULL,
                                 KEY `FK3x2p8ddpd02gslxp97iky7t32` (`group_id`),
                                 KEY `FKckufgfo983775dypmevc580qe` (`user_id`),
                                 CONSTRAINT `FK3x2p8ddpd02gslxp97iky7t32` FOREIGN KEY (`group_id`) REFERENCES `ia_group` (`id`),
                                 CONSTRAINT `FKckufgfo983775dypmevc580qe` FOREIGN KEY (`user_id`) REFERENCES `profile` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `refresh_token`;
CREATE TABLE `refresh_token` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `creation_date` datetime DEFAULT NULL,
                                 `token` varchar(255) DEFAULT NULL,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


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
                            CONSTRAINT `FKnfnboik5lv37rehgogdfthojk` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`),
                            CONSTRAINT `FKrhgbl85ljn51jddbxme1jvlxc` FOREIGN KEY (`sender_id`) REFERENCES `profile` (`user_id`),
                            CONSTRAINT `FKsjyuirawvaqtb16yu78e23fxm` FOREIGN KEY (`receiver_id`) REFERENCES `profile` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO `relation` (`id`, `confirmed_at`, `sent_at`, `receiver_id`, `sender_id`) VALUES
                                                                                         (35,	'2021-10-03 16:21:14',	'2021-10-02 21:50:39',	4,	3),
                                                                                         (45,	'2021-10-05 19:35:54',	'2021-10-02 21:50:40',	5,	3),
                                                                                         (65,	'2021-12-26 03:29:38',	'2021-10-03 16:21:08',	2,	4),
                                                                                         (75,	'2021-10-05 19:35:55',	'2021-10-03 16:21:10',	5,	4),
                                                                                         (115,	'2021-10-17 18:11:26',	'2021-10-05 19:06:56',	15,	4),
                                                                                         (125,	'2021-10-17 18:11:28',	'2021-10-05 19:36:00',	15,	5),
                                                                                         (145,	'2021-12-26 04:04:03',	'2021-10-17 18:11:22',	2,	15),
                                                                                         (170,	'2023-02-28 03:44:52',	'2023-02-28 04:44:14',	15,	1),
                                                                                         (172,	NULL,	'2023-02-28 04:45:51',	2,	3),
                                                                                         (191,	'2023-03-04 01:16:18',	'2023-02-28 23:47:34',	5,	1),
                                                                                         (199,	NULL,	'2023-03-05 16:51:30',	2,	1),
                                                                                         (201,	'2023-03-05 16:52:56',	'2023-03-05 16:52:50',	1,	3);

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
                            CONSTRAINT `FKafcthwfefx5jq45oldbnxyhlv` FOREIGN KEY (`user_id`) REFERENCES `profile` (`user_id`),
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
  `enabled` bit(1) NOT NULL,
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
  `email_verified` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`),
  KEY `FK5v5h53roftm0e90x45m6bh7al` (`image_id`),
  CONSTRAINT `FK5v5h53roftm0e90x45m6bh7al` FOREIGN KEY (`image_id`) REFERENCES `file` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO `user` (`id`, `activated_at`, `activation_key`, `enabled`, `birthday`, `contribution`, `email`, `expectation`, `firstname`, `hobbies`, `lastname`, `password`, `phone_number`, `presentation`, `purpose`, `registered_at`, `url`, `username`, `image_id`, `email_verified`) VALUES
(1,	'2021-09-24 23:11:23',	'793d746c-36f8-4937-bda6-7d6222cf3f51',	CONV('1', 2, 10) + 0,	'1983-09-06',	'Mon expertise dans le domaine du développement logiciel',	'matthieu@mail.fr',	'Acquérir une expérience supplémentaire dans l\'élaboration de solutions informatiques',	'Matthieu',	'Equitation, programmation, snowboard',	'Audemard',	'$2a$10$vmy1fqGnyW7ZN0RXww2j2uBue.A4/5CSEGX6rsROmWfpwr.0bTVWG',	'0617243258',	'Passionné par le développement, je cherche toujours à trouver les solutions les plus élégantes aux problèmes qui me sont donnés.',	'Avant tout je cherche à m\'épanouir dans mon métier',	'2021-09-24 23:10:48',	NULL,	'matthieu',	NULL,	CONV('0', 2, 10) + 0),
(2,	'2021-09-24 23:12:41',	'35254265-fa9d-4514-ae23-f167a5f6a831',	CONV('1', 2, 10) + 0,	'2021-10-07',	'My knowledge as an Elf, having lived for thousands of years.',	'dylan@mail.com',	'To help me throw the ring in Mount Doom.',	'Dylan',	'F1, Badminton, Pétanque, Anime',	'Brudey',	'$2a$10$s7u8ams6mKx4HlzczmTpDu2r.GWNwxkQzfHar1Rq9YkovEE2rzdCu',	'0920100451',	'I was there, 3000 years ago.',	'Get the ring.',	'2021-09-24 23:12:28',	NULL,	'dylan',	NULL,	CONV('0', 2, 10) + 0),
(3,	'2021-09-24 23:14:33',	'88381beb-dd09-45a0-9e9f-66e468347ab9',	CONV('1', 2, 10) + 0,	'1996-07-26',	'De la joie et de la bonne humeur et de l\'expertise et mon réseau.',	'mel@mail.com',	'De la joie et de la bonne humeur :)',	'Mélanie',	'Badminton,\nPing pong,\njeux video,\nSéries',	'Da Costa',	'$2a$10$mLj1NYUHlajXwPurnR2h6eCshIRu8w1clempU07UfCSyh6Dcy3z6S',	'0606969696',	'Geek dans l\'ame, je suis en train de terminer mon Mastère Expert en système d\'information',	'Réussir dans la vie :)',	'2021-09-24 23:14:23',	NULL,	'melanie',	NULL,	CONV('0', 2, 10) + 0),
                                                                        (4,	'2021-09-24 23:15:21',	'5ee1b136-1a89-48eb-bce7-241ef06b79de',	CONV('1', 2, 10) + 0,	'1996-10-06',	'De la bonne humeur et des compétences',	'paul@mail.com',	'Trouver un emploi',	'Paul',	'Judo\nPhotographie',	'Flumian',	'$2a$10$p1dW/T62V7C2vt2OK7dVruZVbQccT/GIetlSQc4Wh7jkariyBXq9W',	'0606060606',	NULL,	'Développeur informatique',	'2021-09-24 23:15:11',	NULL,	'paul',	NULL,	CONV('0', 2, 10) + 0),
                                                                        (5,	'2021-09-24 23:17:43',	'b1e89b98-d2a3-4958-9a9c-270ab2c0439a',	CONV('1', 2, 10) + 0,	NULL,	NULL,	'm@thusha.com',	NULL,	'Mathusha',	NULL,	'Thirulogasingam',	'$2a$10$khA09HcY4cvY0B30.YVeJeVVqWjCMJ.0sfsvKCzmTKCnzQn5eV5b2',	NULL,	NULL,	NULL,	'2021-09-24 23:17:32',	NULL,	'mathusha',	NULL,	CONV('0', 2, 10) + 0),
                                                                        (15,	'2021-10-05 19:04:50',	'1256b301-d43b-457f-8a47-449b043efad6',	CONV('1', 2, 10) + 0,	'1979-07-02',	'Bonne humeur, joie de vivre ainsi que de délicieuses spécialités venues des Balkans',	'vlad.leban@gmail.com',	'Une main tendue riche d\'amitié',	'Vlad',	'Chasse, pêche et philatélie ',	'LEBAN',	'$2a$10$v4LPrpkbwYh9PNoFMzd.juJAQyafdpOLc0dmd6a8uaEVPdhUqysMS',	'0651299200',	'Dans ma branche d\'activité il est de coutume de ne pas se présenter. ',	' ',	'2021-10-05 19:04:20',	NULL,	'Vlad',	NULL,	CONV('0', 2, 10) + 0),
                                                                        (68,	NULL,	'bf22a919-3690-4b82-8339-09f98dfe3ba6',	CONV('1', 2, 10) + 0,	NULL,	NULL,	'gillout@mail.com',	NULL,	'Gilles',	NULL,	'Taignieres',	'$2a$10$TuHuT5RLRhGNlPlBuQrnLegUfmelrROWhhmdpMN9JAGb./VmrOMZS',	NULL,	NULL,	NULL,	'2023-03-26 16:15:34',	NULL,	'gillout',	NULL,	CONV('0', 2, 10) + 0),
                                                                        (69,	NULL,	'c772844e-c5d0-4fff-b29d-6a2f2f612224',	CONV('1', 2, 10) + 0,	NULL,	NULL,	'test@mail.com',	NULL,	'Test',	NULL,	'Test',	'$2a$10$ySJmy9WlWT25fGbpl9d9v.B7gcoGoKXTeOuEcc4ky5lOI3bV8/E4q',	NULL,	NULL,	NULL,	'2023-03-26 17:11:06',	NULL,	'testeur',	NULL,	CONV('0', 2, 10) + 0);

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
                                                   (15,	1),
                                                   (15,	3),
                                                   (1,	5),
                                                   (2,	5),
                                                   (3,	5),
                                                   (5,	5),
                                                   (1,	2),
                                                   (68,	1),
                                                   (69,	1);

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
                                   CONSTRAINT `FKhnxjamc0hrv0uok9w7aayk6kk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                                   CONSTRAINT `FKmvwwuast0ha2nl9hg7wd2r5mo` FOREIGN KEY (`user_id`) REFERENCES `profile` (`user_id`)
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
(127,	'Société Générale',	'Au sein d\'une équipe scrum, j\'ai en charge la conception d\'une application de gestion de projet collaboratif.\n\n#git #java11 - #spring-boot - #angular12 - #mongodb',	'2022-09-30',	'Développeur Fullstack Java Angular (alternance)',	'2020-09-07',	1);

-- 2023-03-26 15:18:42

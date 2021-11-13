# refonte-implicaction

## Quick Start

### dépendances

* [java](https://java.com/fr/download/help/download_options.html) (v11)
* [node](https://nodejs.org/en/) (14 ou supérieure)
* [docker](https://docs.docker.com/engine/install/) (v17.09 min) pour les tests

### lancement de la bd

```shell
docker-compose up
```

la commande va créer 2 containers : une base de données MySQL sur le port 3307 et une instance d'adminer accessible à
l'adresse http://localhost:9000. Utiliser les identifiants suivants :

* utilisateur : root
* mot de passe : password
* base de données : implicaction

### lancement de l'application

```shell
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

* l'application est accessbible à l'adresse http://localhost:8080.
* documentation de l'api : http://localhost:8080/swagger-ui.html

### lancement du front (pour le développement)

```shell
ng serve
```

l'application est accessible à l'adresse http://localhost:4200/

## Quickless Start

Refonte-Implicaction est un projet qui s'appuie sur les technologies spring et angular. Tout son processus de build est
géré par maven.

### Structure du projet

Il se découpe en 3 projets maven :

```
+-- refonte-implicaction/
    +-- backend-implicaction/
    |   +-- src/main/resources
    |   |   +-- application.yml
    |   |   +-- application-local.yml
    |   |       ...
    |   +-- src/test/resources
    |   |   +-- application-test.properties
    |   |       •••
    |   +-- pom.xml 
    +-- frontend-implicaction/
    |   +-- pom.xml
    |       •••
    +-- pom.xml  (root)
```

* le projet backend-implicaction
* le projet frontend-implicaction
* le projet root, qui pilote le build des 2 projets

la commande `mvn clean install` depuis le répertoire racine va donc lancer le build des 2 projets
(ng build --prod pour le front et mvn clean install sur le back) et packager le tout au sein d'un même
jar `backend-implicaction/target/backend-implicaction-[version].jar`. il suffira d'executer la
commande `mvn spring-boot:run [-Dspring-boot.run.profiles=nom_profil]` (pour un build en prod, pas besoin de fournir un
profil, c'est celui par défaut qui est défini). Il est également possible de lancer le jar 'à la main' à l'aide de la
commande `java -jar backend-implicaction/target/backend-implicaction-num_version.jar [-Dspring-boot.run.profiles=nom_profil]`
. Il est également possible de définir la variable d'environnement `spring-boot.run.profiles` pour définir un profil par
défaut.

### les profils

3 profils ont été configurés

* le profil par défaut à utiliser pour la version de production (
  cf `backend-implicaction/src/main/resources/application.yml` et `application.properties`)
* le profil 'local' pour lancer le projet en local (cf `backend-implicaction/src/main/resources/applicaction-local.yml`)
* le profil 'test' à utiliser pour lancer les tests (
  cf `backend-implicaction/src/test/resources/applicaction-test.properties`)

### les variables d'environnement à définir*

* PORT : port du serveur
* DB_URI : adresse de la base de données (ex: mysql://domain:port/db_name)
* DB_USER : utilisateur de la base de données
* DB_PASS : mot de passe de la base de données
* SMTP_HOST : adresse du serveur smtp
* SMTP_PORT : port du serveur smtp
* SMTP_USER : user du serveur smtp
* SMTP_PASS : password du serveur smtp
* KS_PATH : chemin vers le fichier JKS
* KS_NAME : nom du certificat de sécurité (par défaut mettre implicaction)
* KS_PASSWORD : mot de passe du certificat
* APP_URL : url de l'applicaction
* CONTACT_EMAIL : adresse mail du contact
* AWS_ACCESS_KEY(*): access key du compte aws
* AWS_SECRET_KEY(*): client secret du compte aws
* AWS_REGION(*): region du compte aws

### lancement des tests

A l'heure actuelle, seuls les tests du projet back ont été définis. Pour les lancer il suffit d'utiliser la commande
suivante :
`mvn test -Dspring.profiles.active=test` depuis le répertoire racine.

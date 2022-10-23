# refonte-implicaction

## Quick Start

### dépendances

* [java](https://java.com/fr/download/help/download_options.html) (v11)
* [node](https://nodejs.org/en/) (v16)
* [Angular](https://angular.io/guide/setup-local) : `npm install -g @angular/cli`
* [docker](https://docs.docker.com/engine/install/) (v17.09 min) pour les tests

### lancement de la bd

```shell
docker-compose up
```

### environnement local

* BD : mysql://root:password@localhost:3306/implicaction
* Adminer : http://localhost:9030
* Backend : http://localhost:8080
* Frontend : http://localhost:4200
* swagger : http://localhost:8080/swagger-ui.html

### lancement de l'application

#### en ligne de commande

* backend

```shell
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

* frontend

```shell
ng serve
```

#### depuis IntelliJ-IDEA

## Détail du projet

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
    |   +-- pom.xml (backend)
    +-- frontend-implicaction/
    |   +-- pom.xml (frontend)
    |       •••
    +-- pom.xml  (root)
```

* le projet backend-implicaction
* le projet frontend-implicaction
* le projet root, qui pilote le build des 2 projets

### les profils

3 profils ont été configurés

* le profil par défaut à utiliser pour la version de production (
  cf `backend-implicaction/src/main/resources/application.yml` et `application.properties`)
* le profil 'local' pour lancer le projet en local (cf `backend-implicaction/src/main/resources/applicaction-local.yml`)
* le profil 'test' à utiliser pour lancer les tests (
  cf `backend-implicaction/src/test/resources/applicaction-test.properties`)

### les variables d'environnement à définir

* PORT : port du serveur
* DB_URI : adresse de la base de données (ex: mysql://domain:port/db_name)
* DB_USER : nom d'utilisateur de la base de données
* DB_PASS : mot de passe de la base de données
* SMTP_HOST : adresse du serveur smtp
* SMTP_PORT : port du serveur smtp
* SMTP_USER : nom d'utilisateur du serveur smtp
* SMTP_PASS : mot de passe du serveur smtp
* KS_PATH : chemin du fichier KeyStore
* KS_NAME : nom du certificat de sécurité (par défaut mettre implicaction)
* KS_PASSWORD : mot de passe du certificat
* APP_URL : url de l'applicaction
* CONTACT_EMAIL : adresse mail du contact
* AWS_ACCESS_KEY(*): access key du compte aws
* AWS_SECRET_KEY(*): client secret du compte aws
* AWS_REGION(*): region du compte aws

(*) mandatory

### lancement des tests

A l'heure actuelle, seuls les tests du projet back ont été définis. Pour les lancer il suffit d'utiliser la commande
suivante :
`mvn test -Dspring.profiles.active=test` depuis le répertoire racine.

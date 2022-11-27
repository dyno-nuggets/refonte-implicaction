# refonte-implicaction

| :warning:  IMPORTANT                                                                                                                                                               |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Pour que la CI fonctionne correctement vous devez désactiver l'analyse automatique. Pour cela rendez vous sur la page [sonarcloud](https://sonarcloud.io/) de votre **projet** > Administration > Analysis Method > ![image](https://user-images.githubusercontent.com/4210719/204150398-7893dc5c-a72b-4dd0-9ac5-711248239347.png)|

## Quick Start

### Dépendances

* [java](https://java.com/fr/download/help/download_options.html) (v11)
* [node](https://nodejs.org/en/) (v16)
* [docker](https://docs.docker.com/engine/install/) (v17.09 min) pour les tests

### Environnement local

* pour lancer les conteneurs (bdd, adminer, aws)

```shell
docker-compose up
```

* Pour lancer le back

```shell
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

* pour avoir accès aux commandes `ng`

```shell
npm install -g @angular/cli
```

* pour lancer le front

```shell
ng serve
```

* BD : mysql://root:password@localhost:3306/implicaction
* Adminer : http://localhost:9030
* Backend : http://localhost:8080
* Frontend : http://localhost:4200
* swagger : http://localhost:8080/swagger-ui.html

## Configuration IntelliJ-IDEA

### Launchers

#### Backend

![Capture d’écran 2022-10-24 à 01 10 31](https://user-images.githubusercontent.com/4210719/197422713-4974e17d-fecb-4a36-b2b7-a620ed7d4403.png)

#### Frontend

![image](https://user-images.githubusercontent.com/4210719/197422825-8d56268f-d9f7-4900-8b60-529889dd2be9.png)

### Codestyle et formatage

* dezipper et installer le
  fichier [implicaction-cs.xml.zip](https://github.com/dyno-nuggets/refonte-implicaction/files/9847801/implicaction-cs.xml.zip)
  comme suit :
  ![image](https://user-images.githubusercontent.com/4210719/197423670-27a3ba53-81ad-469c-92b2-afdbd88d1a3a.png)

* installer le plugin Save Actions et le configurer comme illustré :
  ![image](https://user-images.githubusercontent.com/4210719/197423803-7d6bad6e-9fc0-4e44-864c-9c2b3395bc67.png)

* configurer l'outil Actions on save
  ![image](https://user-images.githubusercontent.com/4210719/197423865-11cf1517-5ab2-4227-a335-60ca9f0e06f2.png)

## Gestion d'un développement

### Création de la branche

* mise à jour de son dépôt local : `git fetch origin`
* création de la branche de développement :
  `git checkout -b features/RI-XXXX origin/develop` (où RI-XXX est la référence à la JIRA associée)

### Commit sur la branche

Il est important de créer des commits avec le moins de changements possible afin de faciliter la compréhension pour le
reviewer

* sélection des changements à commit
* écriture du message de commit :

```
RI-XXX : titre du commit explicite

* liste de détails éventuels
```

Il est important d'envoyer ses commits sur le dépôt régulièrement

* `git push origin features/RI-XXX`
    * si on a modifié un commit précédemment envoyé, il faut forcer le push : `git push -f origin features/RI-XXXX`

### Création de la pull request

Une fois que le branche est prête à être revue on créée la PR sur github.

* envoyer les commits sur le distant : `git push origin features/RI-XXX`
* choisir **develop** comme **base**
* choisir **features/RI-XXX** comme **compare**
* cliquer sur le bouton **create pull request**

see
also : [documentation GitHub](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/creating-a-pull-request)

### Merge de la branche

Il est important de squash les différents commits de la branche afin de conserver un historique git cohérent et
intelligible. Veillez à sélectionner l'option Squash and merge avan de merger votre branche.

![image](https://user-images.githubusercontent.com/4210719/197425462-13607e85-f747-47dc-8253-9bd5dd3b61fb.png)

## Détails du projet

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

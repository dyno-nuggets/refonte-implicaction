# refonte-implicaction

## dépendances

* [java](https://java.com/fr/download/help/download_options.html) (11 ou supérieure)
* [node](https://nodejs.org/en/) (14 ou supérieure)
* [docker](https://docs.docker.com/engine/install/) (v17.09 min) pour les tests

## Procédure de lancement

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

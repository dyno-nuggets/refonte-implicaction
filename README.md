# refonte-implicaction

## lancement de la BD

```shell
docker-compose up
```
la commande va créer 2 containers : une base de données MySQL sur le port 3307 et une instance d'adminer à l'adresse
http://localhost:9000


## lancement du back

```shell
mvn spring-boot:run
```
le serveur est lancé sur l'adresse http://localhost:8080/

## lancement du front

```shell
ng serve
```
le serveur est lancé sur l'adresse http://localhost:4200/

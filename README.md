# ReadMe du projet Café de l'équipe 4

Voici le ReadMe du projet Café de l'équipe 4 Ift-6002.

## Description de notre architecture

Pour implémenter la gestion du café, nous avons choisi d'utiliser l'architecture hexagonale, avec une couche domaine, une couche applicative et une couche infrastructure. 

De manière générale, nous avons utilisé les patterns *factory* et *repository* pour améliorer la maintenabilité du code. Les définitions des différents repositories sont situées dans la couche domaine tandis que leurs implémentations sont situées dans la couche infrastructure. Pour le moment, nous avons une seule implémentation de chaque repository, qui correspond à une implémentation "In Memory" mais notre design permettrait d'implémenter d'autres technologies de persistance.

### Couche domaine 

Ainsi, les comportements concernant la logique d'affaires de restauration sont situées dans la package *domain*. Plus particulièrement, nous avons divisé le domaine en ces différents packages :

- bill : gestion des factures ;
- cooking : gestion des ingrédients présents dans la cuisine ;
- cube : objets concernant la structure organisationnelle d'un café, où un cube représente une table ;
- customer : gestion des clients ;
- inventory : gestion de l'inventaire d'un café ;
- layout : objets permettant de montrer la disposition des clients dans un café ;
- order : gestion des commandes ;
- recipe : informations sur les recettes ;
- reservation : gestion des réservations et des différentes stratégies possibles ;
- seat : objets qui concernent un siège ;
- seating : objets qui concernent l'attribution des sièges selon l'organisation du café.

Pour pallier le problème d'obsession de la primitive, nous avons créé des objets SeatId, CustomerId qui permettraient éventuellement de changer le type d'identification d'un siège ou d'un client. Pour l'instant, un siège est identifié par un Integer et un client par une String.
De même, nous avons introduit des classes Ingredient plutôt que d'identifier un ingrédient par une String.

Nous avons présentement les classes TipRate et TaxRate qui sont très similaires. Nous avons choisi d'implémenter deux classes pour avoir plus de flexibilité dans le cas ou un des types de taux devait changer. Cela permet également de bien séparer le calcul de taxes et de pourboires.

Nous avons aussi choisi de créer un système de taxes dans un repository qui pour l'instant, contient les taxes associées au système du Canada (divisé en provinces) et au système américain (divisé en états). Nous avons fait le choix de mettre toutes les informations dans le même repository.

### Couche applicative

De la même façon, nous avons séparé les différents concepts dans la couche applicative, qui est propre au café "Les 4 fées". Ces différents services nous permettent de faire le lien entre nos différents repository car nous avons fait le choix d'avoir un repository dédié à un service particulier plutôt que de n'avoir qu'un repository où seraient stockées toutes les informations. C'est pourquoi certains comportement liant les différents repository sont situés dans la couche applicative. Ceci nous est particulièrement bénéfique pour régler les problèmes de TDA et de faciliter les tests des services. Cependant, le fait de séparer les concepts de cette façon peut laisser penser que notre domaine est assez anémique.

## Intégration Docker

Un Dockerfile est également fourni si vous désirez essayer de rouler votre code sur les mêmes images docker que nous utiliserons.

Pour ce faire:

```bash
docker build -t application-glo4002 .
docker run -p 8181:8181 application-glo4002
```

## Démarrer le projet

Vous pouvez démarrer le serveur (CafeServer).

Le `main()` ne demande pas d'argument.

Vous pouvez également rouler le serveur via maven :

```bash
mvn clean install
mvn exec:java -pl cafe-api
```

## Test Postman

Une collection de tests Postman est fournie avec le fichier 
`Cafe-Postman-Test.postman_collection.json`. Les tests Postman nous permettent de tester les différentes ressources de notre API.
Nous avons divisé les tests en plusieurs catégories correspondant aux différentes stories.

Pour l'utiliser, vous devez avoir installé Postman et importé la collection.

## Note concernant les tests
Actuellement, nous testons les comportements des couches domaine, applicative et des implémentations des repository situés dans la couche infrastructure. Seules les ressources ne sont pas testées, car nous avons adapté la stratégie "end-to-end" via nos différents tests Postman.

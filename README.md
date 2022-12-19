# Chess Game

## Démarche

### Choix de la structure de donnée
Il existe de nombreuses possibilités, nous avons choisies de représenter l'echiquier
avec un tableau d'entier à une dimension de 10x12, idéal pour générer les coups valides, et 
les déplacements.

### décomposition du problème en sous problème
**le package Model sera composé des classes<br>**
* Piece : représente les différentes pièces du jeu, nous donnes les caractéristiques
commune des pièces (couleur déplacement).
* Board : qui représente la grille ainsi que les caractéristqiues du jeu (si échec et mat,
pat, la liste des pièces des joueurs.)
* Player : qui représente les joueurs de la partie (couleur, pièce capturer,
déplace les pieces dans le jeu.)
* les classes Command : qui permettent de créer les coups à éxecuter

**Le package View sera composé des classes :**
* MainMenu : qui permet de démarrer le jeu.
* GameFrame : la vue principal lors d'une partie.

**Le package Controller sera composé des classes :**
* GameManager : façade qui se comportera comme un game manager.
* MiddleMan : façade pour simplifier le passage du controleur à la vue.

### design pattern
* Factory pattern qui assiste à la création des pièces.
* strategy pattern qui permet d'appliquer un type de mouvement à une pièce.
* façade qui à pour but de simplifier les interactions entre les classes.
* observer pour mettre a jour la vue.

## Suivie

* Dans un premier temps nous avons choisie la structure de donnée pour représenter l'echiquier.
* Ensuite nous avons décomposer le problème en sous problème dans le but de trouver 
les objets idéals à utiliser.
* Nous avons commencer par décrire le package model sans nous préocuper des packages
view et controller, ce n'est pas une bonne manière de travailler mais ne connaissant
pas les possibilités offertes par java swing. je profiterai des vacances de noel 
* pour apprendre à l'utiliser.
* Ensuite nous avons écris quelques test pour le déplacement des pièces.
* Enfin nous avons écris les classes dans le package view (ne respecte pas le design mvc)
afin d'avoir une interface graphique pour se faire une idée du résultat.


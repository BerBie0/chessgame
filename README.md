# Chess Game
## Package Vue
Il est constitué des classes :

* MainMenu : qui affiche le menu principal et instancie les modèles (Player(x2), Board et MoveLog), le contrôleur (GameManager) et la vue (EnterNameMenu). On ajoute un observateur pour chaque joueur afin d'observer EnterNameMenu.
* EnterNameMenu : ces observateurs permettent de mettre à jour le nom des joueurs. Ils notifient la vue en affichant les noms des joueurs dans les JLabel. Cet observateur n'est pas utile, car il n'est utilisé qu'une seule fois. C'était un premier essai pour avoir une idée de la manière à implémenter. Cette classe crée une instance de GameFrame2.
* GameFrame2 : C'est la vue principale dans notre application. Elle connaît les modèles et le contrôleur. Elle est constituée des classes TakenPiecePanel, GameHistoryPanel et des classes internes : BoardPanel, Tile, BoardDirection2 et Menu. Classe interne pour essayer de comprendre le fonctionnement et les avantages. Cette classe implémente les interfaces des observateurs afin de mettre à jour la vue. GameFrame2 délègue aux autres classes de la vue.
* Tile : permet de dessiner une case selon les informations des modèles.
* BoardPanel : permet de dessiner l'ensemble des cases.
* BoardDirection2 : permet de retourner le sens du plateau.
* TakenPiecePanel : classe qui permet d'afficher les pièces capturées.
* GamehistoryPanel : classe qui permet d'afficher l'historique des coups.
* Les classes qui composent la vue principale possèdent chacune une méthode "redo" qui est appelée par la vue principale pour mettre à jour l'affichage.

## Package Model
Il est constitué de plusieurs Packages :

* Board
   * Board : classe qui contient la liste des pièces, le plateau de jeu représenté par un tableau de 120 éléments. Les positions valides sont les index de [21 à 28], [31, 38] ... [91, 98], les autres indices sont des positions non valides. Boîte aux lettres des échecs.
   * IBoardObserver : interface qui permet de mettre à jour le plateau en affichant les coups possibles pour une pièce.
* Move :
   * AttackMove : classe qui sert à capturer une pièce adverse.
   * CastleMove : classe qui permet le petit et grand roque.
   * SimpleMove : classe qui permet le mouvement simple d'une pièce.
   * IMove : implémente Move permet de mettre à jour MoveLog.
   * Move : classe abstraite avec les caractéristiques communes des mouvements.
* Pieces
   * Piece : classe abstraite avec les caractéristiques communes des pièces. Elle possède un attribut contexte qui est défini d'après les classes filles.
   * StrategyMovement : interface qui permet aux classes filles de trouver l'offset de déplacement des pièces.
* Player :
   * Player : possède une liste d'observateurs de 2 types décrits plus bas. La classe notifie la vue lorsqu'un déplacement est effectué ou qu'une pièce est capturée.
   * IPlayerObserver : observateur du nom des joueurs.
   * IObserverGame : observateur du déplacement des pièces et des pièces capturées.
* Utils :
   * Color2 : Enum des couleurs.
   * MoveLog : classe qui garde l'historique des mouvements.
   * PieceFactory : classe qui permet d'instancier les pièces.
   * IObserverMoveLog : interface liée à la notification de la vue pour mettre à jour GamehistoryPanel lorsque un coup est joué.

## Package Controler
* GameController : classe qui permet de mettre à jour le modèle.

## Problèmes
* ...

## Lancer l'application
Java 17.
Ouvrir un terminal dans le dossier jar à la racine.
Exécuter la commande : "java -jar a31-chessgame.jar" (fonctionne sur Ubuntu et Windows).
Dans IntelliJ :
Dossier de test : "test"
Dossier source : "src"
Ressources : dossier "resources"

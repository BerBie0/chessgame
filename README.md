# Chess Gameeee

## Package View

Il est constitué des classes :
* MainMenu : qui affiche le menu principal et instancie les modèles (Player(x2), Board et MoveLog), le controller (GameManager) et la vue (EnterNameMenu). On ajoute un observer pour chaque joueur afin d'observer EnterNameMenu.
* EnterNameMenu : ces observers permettent de mettre à jours le nom des joueurs il notifie la vue en affichant le nom des joueurs dans les JLabel. Cette observer n'est pas utile, car il n'est utilisé qu'une seule fois. c'était un premier essai pour avoir une idée de la manière à implémenter. cette classe crée une instance de GameFrame2
* GameFrame2 : C'est la vue principale dans notre application. Elle connait les modèles et le contrôler. Elle est constituée des classes TakenPiecePanel, GameHistoryPanel et des classes internes, BoardPanel, Tile, BoardDirection2 et Menu. Classe interne pour essayer de comprendre le fonctionnement et les avantages. Cette classe implémente les interfaces des observers afin de mettre la vue à jour, GameFrame2 délégue aux autres classe de la vue.
* Tile : permets de dessiner une case selon les informations des modèles
* BoardPanel : permets de dessiner l'ensemble des cases.
* BoardDirection2 : permets de retourner le sens du plateau.
* TakenPiecePanel : classe qui permet d'afficher les pièces capturées.
* GamehistoryPanel : classe qui permet d'afficher l'historique des coups.
* les classes qui composent la vue principale possèdent chacune une méthode redo qui sont appelées par la vue principale pour mettre à jour l'affichage.


## Package Model
Il est constitué de plusieurs Packages :
* Board
  * Board : classe qui contient la liste des pièces, le plateau de jeu représenté par un tableau de 120 éléments. Les positions valides sont les index de [21 à 28], [31,38] ... [91,98], les autres indexes sont des positions non valides. mailbox chess.
  * IBoardObserver : interface qui permet de mettre à jour le plateau en affichant les coups possibles pour une pièces.
* Move :
  * AttackMove : classe qui sert a capturer une pièce adverse.
  * CastleMove : classe qui permet le petit et grand roque.
  * SimpleMove : classe qui permet le mouvement simple d'une pièce.
  * IMove : implémente Move permet de mettre à jours MoveLog
  * Move : classe abstraite avec les caractéristiques communes des mouvements.
* Pieces
  * Piece : classe abstraite avec les caractéristiques communes des pièces. Elle possède un attribut contexte qui est défini d'après les classes filles.
  * StrategyMovement : interface qui permet aux classes filles de trouver l'offset de déplacement des pièces
* Player :
  * Player : possède une liste d'observer des 2 types décrits plus bas. La classe notifie la vue lorsqu'un déplacement est effectué ou qu'une pièce est capturée.
  * IPlayerObserver : observer du nom des joueurs
  * IObserverGame : observer du déplacement des pièces et des pièces capturées.
* utils :
  * Color2 : Enum des couleurs
  * MoveLog : classe qui garde l'historique des mouvements.
  * PieceFactory : classe qui permet d'instancier les pièces.
  * IObserverMoveLog : interface liée à la notification de la vue mettre à jour GamehistoryPanel lorsque un coup est joué.

## package Controller
* GameManager : classe qui permet de mettre à jour le modèle.


## problèmes
* public boolean anyValidMove(Color2 color) et public boolean willMoveResultInCheck(Piece piece, int newPos2) devraient être dans le modèle, la classe board par exemple. Le modèle devrait contenir une variable isCheckMate.
* Exemple pour le mat du berger, le cavalier noir est en H6 pour défendre F7, la reine blanche est en F7 le fou blanc en C4. le contrôleur considère que la partie est terminée, victoire pour les blancs. Ou ne détecte pas échec et mat.
* parfois le contrôler fait office de modèle.
* la partie promotion des pions n'est pas terminée.
* le grand roque et petit roque fonctionne même si le roi a déjà bougé.
* les affichages sont dans la console sauf pour les échec et échec et mat
* ...

## lancer l'application
* java 17.
* ouvrir un terminal dans le dossier jar à la racine.
* java -jar a31-chessgame.jar (fonctionne sur ubuntu)
* intellij :
  * test : dossier test
  * src : dossier src
  * resources : dossier ressource

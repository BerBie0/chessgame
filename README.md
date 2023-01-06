# Chess Gameeee

## Package View

Il est constitué des classes :
* MainMenu : qui affiche le menu principal et instantie les modèles (Player(x2), Board et MoveLog), le controller (GameManager) et la vue (EnterNameMenu). on ajoute un observer pour chaque joueur afin d'observer EnterNameMenu. 
* EnterNameMenu : ces observers permettent de mettre à jours le nom des joueurs il notifie la vue en affichant le noms des joueurs dans les JLabel. Cette observer n'est pas utile car il n'est utiliser qu'une seule fois. c'était un premier essaie pour avoir une idée de la manière à implémenter. cette classe créee une instance de GameFrame2
* GameFrame2 : C'est la vue principale dans notre application. elle connait les modèles et le controller. Elle est constitué des classes TakenPiecePanel, GameHistoryPanel et des classes interne BoardPanel, Tile, BoardDirection2 et Menu. classe interne pour essayer de comprendre le focntionnement et les avantages. cette classe implémente les interfaces des observers afin de mettre la vue à jours, GameFrame2 délégue aux autres classe de la vue.
* Tile : permet de dessiner une case selon les informations des modèles
* BoardPanel : permet de dessiner l'ensemble des cases.
* BoardDirection2 : permet de retourner le sens du plateau.
* TakenPiecePanel : classe qui permet d'afficher les pièces capturé.
* GamehistoryPanel : classe qui permet d'afficher l'historique des coups.
* les classes qui composent la vue principales possèdent chacune une méthodes redo qui sont appelées par la vue principale pour mettre à jour l'affichage.


## Package Model
Il est constitué de plusieurs Package :
* Board
  * Board : classe qui contient la liste des pièces, le plateau de jeu reprénsenter par un tableau de 120 éléments. les positions valide sont les indexs de [21 à 28], [31,38] ... [91,98], les autres indexes sont des positions non valide. mailbox chess.
  * IBoardObserver : interface qui permet de mettre à jour le plateau en affichant les coups possibles pour une pièces.
* Move : 
  * AttackMove : classe qui sert a capturer une pièce adverse.
  * CastleMove : classe qui permet le petit et grand roque.
  * SimpleMove : classe qui permet le mouvement simple d'une piece.
  * IMove : implémente Move permet de mettre à jours MoveLog
  * Move : classe abstraite avec les caractéristiques communes des mouvements.
* Pieces
  * Piece : classe abstraite avec les caractéristiques communes des pieces. elle possède un attribut contexte qui est définit d'après les classes filles.
  * StrategyMovement : interface qui permet aux classes filles de trouver l'offset de déplacement des pieces
* Player : 
  * Player : possède une liste d'observer des 2 types décrit plus bas. la classe notifie la vue lorseque qu'un déplacement est effectué ou qu'une pièce est capturé.
  * IPlayerObserver : observer du nom des joueurs
  * IObserverGame : observer du déplacement des pièces et des pièces capturé.
* utils :
  * Color2 : Enum des couleurs
  * MoveLog : classe qui garde l'historique des mouvements.
  * PieceFactory : classe qui permet d'instantier les pièces.
  * IObserverMoveLog : interface lié à la notification de la vue mettre à jour GamehistoryPanel lorseque un coup est joué.

## package Controller
* GameManager : classe qui permet de mettre à jours le modèle.


## problèmes
* public boolean anyValidMove(Color2 color) et public boolean willMoveResultInCheck(Piece piece, int newPos2) devraient être dans le modèle, la classe board par exemple. le modèle devrait contenir une variable isCheckMate.
* exemple pour le mat du berger, le cavalier noir est en H6 pour défendre F7, la reine blanche est en F7 le fou blanc en C4. le controlleur considère que la partie est terminé, victoire pour les blancs. ou ne detecte pas echec et mat.
* parfois le controller fait office de modèle.
* la partie promotion des pions n'est pas terminé.
* le grand roque et petit roque fonctionne même si le roi à déjà bouger.
* ...

## lancer l'application
* java 17.

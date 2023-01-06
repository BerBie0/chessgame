package Model.Move;

import Model.Board.Board;
import Model.MoveLog;
import Model.Pieces.Piece;
import Model.Player.Player;

public class SimpleMove extends Move {

    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/
    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

    public SimpleMove(int op, int np, Piece pi, Player pl, Board b) {
        super(op, np, pi, pl, b);
    }

    /*---------------------------------------------GET SET------------------------------------------------------------*/
    /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/
    /*-------------------------------------------INTERFACE METHOD-----------------------------------------------------*/
    @Override
    public void execute() {
        //FixMe pas besoin du trhow
        /*
        if( piece.getColor() != player.getColor() )
            throw new IllegalArgumentException("SimpleMove.java : The piece is not ur");
         */
        board.validateSimpleMove(piece, newPos);
        //todo pas la bonne classe qui fait le mouvement chnger observer
        //board.move pour modifier le tableau
        //player.move pour faire le mouvement et notifie observer mais ne connais pas la position et la grille
        board.move(piece, newPos);
        player.move(piece, newPos);

    }

    @Override
    public void undo(MoveLog moveLog) {
        board.move(piece, oldPos);
        player.move(piece, oldPos);
    }
    /*------------------------------------------------METHOD----------------------------------------------------------*/
}

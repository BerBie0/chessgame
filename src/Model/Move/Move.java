package Model.Move;

import Model.Board.Board;
import Model.Pieces.Piece;
import Model.Player.Player;

public abstract class Move implements IMove{
    protected int newPos;
    protected int oldPos;
    protected Piece piece;
    protected Player player;
    protected Board board;

    public Move(int np, int op, Piece pi, Player p, Board b) {
        newPos = np;
        oldPos = op;
        piece = pi;
        player = p;
        board = b;
    }
}

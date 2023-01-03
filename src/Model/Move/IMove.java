package Model.Move;

import Model.Pieces.Piece;

public interface IMove
{
    public void execute();
    public void undo();
    public Piece getPiece();
    public int getNewPos();
}

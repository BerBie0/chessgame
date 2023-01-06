package Model.Move;

import Model.utils.MoveLog;
import Model.Pieces.Piece;

public interface IMove
{
    public void execute();
    public void undo(MoveLog moveLog);
    public Piece getPiece();
    public int getNewPos();
}

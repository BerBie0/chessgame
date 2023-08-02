package Model.Move;

import Model.Pieces.Piece;

public interface IMove {
    public void execute() throws Exception;
    public void undo(MoveLog moveLog);

}

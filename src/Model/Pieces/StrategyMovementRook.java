package Model.Pieces;

public class StrategyMovementRook implements IStrategyMovement{
    @Override
    public int[] offset() {
        return new int[]{ -10,  -1,  1, 10};
    }
}

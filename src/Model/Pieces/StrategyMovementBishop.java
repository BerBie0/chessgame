package Model.Pieces;

public class StrategyMovementBishop implements IStrategyMovement{
    @Override
    public int[] offset() {
        return new int[]{ -11,  -9,  9, 11 };
    }
}

package Model.Pieces;

public class StrategyMovementPawn implements IStrategyMovement{
    @Override
    public int[] offset() {
        return new int[]{ 10, -10};
    }
}

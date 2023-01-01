package Model.Pieces;

public class StrategyMovementPawn implements StrategyMovement{
    @Override
    public int[] offset()
    {
        return new int[]{ -10, 10};
    }
}

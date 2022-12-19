package Model.Pieces;

public class StrategyMovementKnight implements StrategyMovement{
    @Override
    public int[] offset()
    {
        return new int[]{ -21, -19,-12, -8, 8, 12, 19, 21 };
    }
}

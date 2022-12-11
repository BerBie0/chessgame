package Model;

public class StrategyMovementRook implements StrategyMovement{
    @Override
    public int[] offset()
    {
        return new int[]{ -10,  -1,  1, 10};
    }
}

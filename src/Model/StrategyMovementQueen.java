package Model;

public class StrategyMovementQueen implements StrategyMovement{
    @Override
    public int[] offset()
    {
        return new int[]{-11, -10, -9, -1, 1, 9, 10, 11};
    }
}
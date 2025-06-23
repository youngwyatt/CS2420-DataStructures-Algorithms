package lab04;

public class BetterRandomNumberGenerator implements RandomNumberGenerator
{
    private long seed;
    @Override
    public int nextInt(int max) {

        return 0;
    }

    @Override
    public void setSeed(long seed) {
        this.seed = seed;
    }

    @Override
    public void setConstants(long const1, long const2) {

    }
}

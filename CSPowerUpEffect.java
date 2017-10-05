
public abstract class CSPowerUpEffect
{
    private long startTime;
    public long timeLimit = 10000L;

    /**
     * Set up the powerup by copying the player's affected variables
     * so they can be restored when the time expires.
     * @param player
     */
    public abstract void setUp(CSCharCornShark player);

    public abstract void expire(CSCharCornShark player);

    public void start()
    {
        startTime = System.currentTimeMillis();
    }


    public long getTimeRemaining()
    {
        return timeLimit - (System.currentTimeMillis() - startTime);
    }


    /**
     * @return the startTime
     */
    public long getStartTime()
    {
        return startTime;
    }


    /**
     * @return the timeLimit
     */
    public long getTimeLimit()
    {
        return timeLimit;
    }


    /**
     * @param timeLimit the timeLimit to set
     */
    public void setTimeLimit(final long timeLimit)
    {
        this.timeLimit = timeLimit;
    }


    public boolean isFinished()
    {
        if (System.currentTimeMillis() - startTime >= timeLimit)
            return true;

        return false;
    }
}

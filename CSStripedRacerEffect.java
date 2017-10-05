/**
 * @author Michael
 *
 */
public class CSStripedRacerEffect extends CSPowerUpEffect
{
    private double oldMaxHSpeed = 0;
    private double oldMaxVSpeed = 0;

    /**
     *
     */
    public CSStripedRacerEffect()
    {
        timeLimit = 15000L;
    }


    /* (non-Javadoc)
     * @see CSPowerUpEffect#expire()
     */
    @Override
    public void expire(final CSCharCornShark player)
    {
        player.setMaxHSpeed(oldMaxHSpeed);
        player.setMaxVSpeed(oldMaxVSpeed);
    }


    /* (non-Javadoc)
     * @see CSPowerUpEffect#setUp(CSCharCornShark)
     */
    @Override
    public void setUp(final CSCharCornShark player)
    {
        oldMaxHSpeed = player.getMaxHSpeed();
        oldMaxVSpeed = player.getMaxVSpeed();

        player.setMaxHSpeed(oldMaxHSpeed * 2);
        player.setMaxVSpeed(oldMaxVSpeed * 2);
    }

}

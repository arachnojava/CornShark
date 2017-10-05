
public class CSCharSeaChicken extends CSFishBase
{
    private static final int DIVING = 0;
    private static final int CLIMBING = 1;
    private static final int WAITING = 2;

    private int state = WAITING;
    private final int speed = 16;
    private final int minY, maxY;
    private final int waitTimeLimit = 128;
    private int waitTime = waitTimeLimit;

    public CSCharSeaChicken(final CSGameWorldController c)
    {
        super(c);

        setImageGroup(data.igSeaChicken);

        minY = -200;
        maxY = 0;

        setLocation(0, minY);
        setHealth(512);
    }


    @Override
    public boolean isDisposable()
    {
        return false;
    }


    /* (non-Javadoc)
     * @see CSFishBase#advance()
     */
    @Override
    public void advance()
    {
        setLocation(getX() + getHorizontalSpeed(), getY() + getVerticalSpeed());

        switch (state)
        {
            case WAITING:
                waitTime--;
                if (data.getPlayer(controller).getY() < 256 && waitTimeExpired())
                {
                    state = DIVING;
                    setHorizontalSpeed(0-speed*2);
                    setX( data.getPlayer(controller).getX() + data.getPlayer(controller).getHorizontalSpeed() + 350 );
                    setVerticalSpeed(speed);
                    setFrameNumber(DIVING);
                }
                break;

            case DIVING:
                if (getY() >= maxY)
                {
                    state = CLIMBING;
                    setHorizontalSpeed(0-speed);
                    setVerticalSpeed(0-speed);
                    setY(maxY-1);
                    setFrameNumber(CLIMBING);
                }
                break;

            case CLIMBING:
                if (getY() <= minY)
                {
                    state = WAITING;
                    waitTime = waitTimeLimit;
                    setHorizontalSpeed(0);
                    setVerticalSpeed(0);
                    setY(minY);
                }
                break;
        }

        getOffenseArea().setRect(getBounds());
        getDefenseArea().setRect(0,0,1,1);

        //System.out.println(this);
    }


    @Override
    public String toString()
    {
        String s = "Sea Chicken ";

        if (state == WAITING) s += "waiting";
        else if (state == DIVING) s += "diving";
        else if (state == CLIMBING) s += "climbing";
        else s += "<state unknown>";

        s += String.format("(%.2f, %.2f)", getX(), getY());

        return s;
    }


    @Override
    public int getDamage()
    {
        return 50;
    }

    private boolean waitTimeExpired()
    {
        return (waitTime < 0);
    }


    @Override
    public void bite(final CSFishBase target)
    {
        target.decreaseHealth(getDamage());
        data.playSound(data.idNPCBite);
    }


    @Override
    public void checkBounds(final CSFishBase fish)
    {
    }


    @Override
    public void findTarget()
    {
    }
}
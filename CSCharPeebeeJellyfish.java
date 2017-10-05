import java.awt.Color;


public class CSCharPeebeeJellyfish extends CSFishBase
{
    public CSCharPeebeeJellyfish(final CSGameWorldController c)
    {
        super(c);

        particleColors = new Color[]
                    {
                        Color.WHITE,
                        Color.LIGHT_GRAY,
                        Color.MAGENTA
                    };


        this.setImageGroup(data.igPeebeeJellyfish);
        final int minLvl = c.getFishLevelData().getMinLevel(CSFishLevelData.PEEBEE_JELLYFISH);
        final int maxLvl = c.getFishLevelData().getMaxLevel(CSFishLevelData.PEEBEE_JELLYFISH);
        this.setLevel(minLvl + (int)(Math.random() * (maxLvl-minLvl)));
        this.setMaxHSpeed(6);
        //this.setMaxVSpeed(getMaxHSpeed()/4);
        this.setHorizontalSpeed(getMaxHSpeed());//+Math.random()*2);

        setLocation(200+Math.random()*(data.getWorldWidth()-400), 100+Math.random()*(data.getWorldHeight()-200));
        final double v = 32 + Math.random() * 60;

        sbSwimRight = new CSAISineSwimRight(this);
        ((CSAISineSwimRight)sbSwimRight).vertical = v;

        sbSwimLeft = new CSAISineSwimLeft(this);
        ((CSAISineSwimLeft)sbSwimLeft).vertical = v;

        if (Math.random() < 0.5)
            setStateBehavior(sbSwimRight);
        else
            setStateBehavior(sbSwimLeft);

        this.setAcceleration(1);
        this.setDrag(1);

        setMaxHealth(calculateHealth());
        setHealth(getMaxHealth());

        updateScale();
    }

    /* (non-Javadoc)
     * @see CSFishBase#advance()
     */
    @Override
    public void advance()
    {
        final double lastY = getY();

        super.advance();

        if (!isDead())
        {
            if (getY() < lastY) // going up
                setFrameNumber(0);
            else
                setFrameNumber(1);
        }

        final double x = getX();
        final double y = getY();
        final double w = getScaledBounds().getWidth();
        final double h = getScaledBounds().getHeight();

        final double defenseHeight = h/3;
        final double offenseHeight = h - defenseHeight;

        getOffenseArea().setRect(x, y+defenseHeight, w, offenseHeight);
        getDefenseArea().setRect(x, y, w, defenseHeight);
    }

    @Override
    public void findTarget()
    {
    }

}

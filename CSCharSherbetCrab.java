import java.awt.Color;

public class CSCharSherbetCrab extends CSFishBase
{
    static int count = 0;

    public CSCharSherbetCrab(final CSGameWorldController c)
    {
        super(c);

        particleColors = new Color[3];
        particleColors[0] = Color.ORANGE;
        particleColors[1] = Color.MAGENTA;
        particleColors[2] = Color.RED;

        this.number = count++;

        this.setImageGroup(data.igSherbetCrab);
        final int minLvl = c.getFishLevelData().getMinLevel(CSFishLevelData.SHERBET_CRAB);
        final int maxLvl = c.getFishLevelData().getMaxLevel(CSFishLevelData.SHERBET_CRAB);
        this.setLevel(minLvl + (int)(Math.random() * (maxLvl-minLvl)));
        this.setMaxHSpeed(6);
        this.setHorizontalSpeed(getMaxHSpeed());

        final double v = 96 + Math.random() * 64;

        setLocation(Math.random()*data.getWorldWidth(), data.getWorldHeight()-100);

        sbSwimRight = new CSAISineSwimRight(this);
        ((CSAISineSwimRight)sbSwimRight).vertical = v;

        sbSwimLeft = new CSAISineSwimLeft(this);
        ((CSAISineSwimLeft)sbSwimLeft).vertical = v;

        if (getX() % 2 == 0)
            setStateBehavior(sbSwimRight);
        else
            setStateBehavior(sbSwimLeft);

        this.setAcceleration(1);
        this.setDrag(1);

        setMaxHealth(calculateHealth());
        setHealth(getMaxHealth());

        updateScale();
    }


    @Override
    public void advance()
    {
        super.advance();

        final double x = getX();
        final double y = getY();
        final double w = getScaledBounds().getWidth();
        final double h = getScaledBounds().getHeight();

        final double offenseWidth = w/3;
        final double defenseWidth = w - offenseWidth;

        // Process left and right
        if (getDirFacing() == CSFishBase.FACING_RIGHT)
        {
            getOffenseArea().setRect(x+defenseWidth, y, offenseWidth, h);
            getDefenseArea().setRect(x, y, defenseWidth, h);
        }
        else
        {
            getOffenseArea().setRect(x, y, offenseWidth, h);
            getDefenseArea().setRect(x+offenseWidth, y, defenseWidth, h);
        }

        if (Math.random() < 0.01)
        {
            if (getDirFacing() == CSFishBase.FACING_RIGHT)
                setStateBehavior(sbSwimLeft);
            else
                setStateBehavior(sbSwimRight);
        }
    }

    @Override
    public void findTarget()
    {
    }

}

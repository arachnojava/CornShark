import java.awt.Color;

public class CSCharHamburgerheadShark extends CSFishBase
{
    static int count = 0;

    public CSCharHamburgerheadShark(final CSGameWorldController c)
    {
        super(c);

        particleColors = new Color[]
        {
            Color.YELLOW, Color.BLACK,
            Color.ORANGE, Color.RED, Color.WHITE
        };

        this.number = count++;

        this.setImageGroup(data.igHamburgerheadShark);
        final int minLvl = c.getFishLevelData().getMinLevel(CSFishLevelData.HAMBURGERHEAD_SHARK);
        final int maxLvl = c.getFishLevelData().getMaxLevel(CSFishLevelData.HAMBURGERHEAD_SHARK);
        this.setLevel(minLvl + (int)(Math.random() * (maxLvl-minLvl)));
        this.setMaxHSpeed(10);
        this.setMaxVSpeed(getMaxHSpeed()/4);
        this.setHorizontalSpeed(Math.random()*getMaxHSpeed());

        setLocation(Math.random()*data.getWorldWidth(), Math.random()*data.getWorldHeight());
        if (getX() % 2 == 0)
        {
            setStateBehavior(sbSwimRight);
            setAnimationSequence(CSFishBase.ACTION_SWIM_RIGHT);
        }
        else
        {
            setStateBehavior(sbSwimLeft);
            setAnimationSequence(CSFishBase.ACTION_SWIM_LEFT);
            setHorizontalSpeed(getHorizontalSpeed() * -1);
        }

        this.setVerticalSpeed(Math.random()*getHorizontalSpeed()/4);

        this.setAcceleration(1);
        this.setDrag(1);

        setMaxHealth(calculateHealth());
        setHealth(getMaxHealth());

        updateScale();
    }
}

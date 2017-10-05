import java.awt.Color;

public class CSCharWhalemelon extends CSFishBase
{
    static int count = 0;

    public CSCharWhalemelon(final CSGameWorldController c)
    {
        super(c);

        this.number = count++;

        particleColors = new Color[3];
        particleColors[0] = Color.RED;
        particleColors[1] = Color.GREEN;
        particleColors[2] = Color.BLACK;

        this.setImageGroup(data.igWhalemelon);
        final int minLvl = c.getFishLevelData().getMinLevel(CSFishLevelData.WHALEMELON);
        final int maxLvl = c.getFishLevelData().getMaxLevel(CSFishLevelData.WHALEMELON);
        this.setLevel(minLvl + (int)(Math.random() * (maxLvl-minLvl)));
        this.setMaxHSpeed(6);
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

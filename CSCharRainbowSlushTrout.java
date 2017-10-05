import java.awt.Color;

public class CSCharRainbowSlushTrout extends CSFishBase
{
    public CSCharRainbowSlushTrout(final CSGameWorldController c)
    {
        super(c);

        particleColors = new Color[8];
        particleColors[0] = Color.WHITE;
        particleColors[1] = Color.LIGHT_GRAY;
        particleColors[2] = Color.CYAN;
        particleColors[3] = Color.GREEN;
        particleColors[4] = Color.MAGENTA;
        particleColors[5] = Color.ORANGE;
        particleColors[6] = Color.PINK;
        particleColors[7] = Color.RED;

        this.setImageGroup(data.igRainbowSlushTrout);
        final int minLvl = c.getFishLevelData().getMinLevel(CSFishLevelData.RAINBOW_SLUSH_TROUT);
        final int maxLvl = c.getFishLevelData().getMaxLevel(CSFishLevelData.RAINBOW_SLUSH_TROUT);
        this.setLevel(minLvl + (int)(Math.random() * (maxLvl-minLvl)));
        this.setMaxHSpeed(8);
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

        this.setVerticalSpeed(Math.random()*getHorizontalSpeed());
        this.setAcceleration(1);
        this.setDrag(1);

        setMaxHealth(calculateHealth());
        setHealth(getMaxHealth());

        updateScale();

    }
}

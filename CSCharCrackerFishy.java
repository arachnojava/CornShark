import java.awt.Color;

public class CSCharCrackerFishy extends CSFishBase
{
    static int count = 0;

    public CSCharCrackerFishy(final CSGameWorldController c)
    {
        super(c);

        particleColors = new Color[2];
        particleColors[0] = Color.YELLOW;
        particleColors[1] = Color.ORANGE;

        this.number = count++;

        this.setImageGroup(data.igCrackerFishy);
        final int minLvl = c.getFishLevelData().getMinLevel(CSFishLevelData.CRACKER_FISHY);
        final int maxLvl = c.getFishLevelData().getMaxLevel(CSFishLevelData.CRACKER_FISHY);
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

        this.setVerticalSpeed(Math.random()*getHorizontalSpeed()/4);

        this.setAcceleration(1);
        this.setDrag(1);

        setMaxHealth(calculateHealth());
        setHealth(getMaxHealth());

        updateScale();
    }
}

import java.awt.Color;

public class CSCharPearahna extends CSFishBase
{

    public CSCharPearahna(final CSGameWorldController c)
    {
        super(c);

        particleColors = new Color[]
                                   {
                                       Color.GREEN, Color.BLACK,
                                       Color.WHITE
                                   };

        this.setImageGroup(data.igPearahna);
        final int minLvl = c.getFishLevelData().getMinLevel(CSFishLevelData.PEARAHNA);
        final int maxLvl = c.getFishLevelData().getMaxLevel(CSFishLevelData.PEARAHNA);
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

        this.setVerticalSpeed(Math.random()*getHorizontalSpeed());

        this.setAcceleration(1);
        this.setDrag(1);

        setMaxHealth(calculateHealth());
        setHealth(getMaxHealth());

        updateScale();
    }

}

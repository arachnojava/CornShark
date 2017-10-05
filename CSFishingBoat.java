import java.awt.Color;
import java.awt.Graphics2D;
import mhframework.MHActor;

public class CSFishingBoat extends MHActor
{
    CSGameWorldController controller;
    private int baitRespawnCountdown = 0;
    private boolean baitExists = true;

    public CSFishingBoat(final CSGameWorldController c)
    {
        controller = c;

        final CSDataModel data = CSDataModel.getInstance();
        this.setImageGroup(data.igFishingBoat);

        setLocation((int)(data.getWorldWidth()/2 - getScaledBounds().getWidth()/2), -70);
    }


    @Override
    public void advance()
    {
        super.advance();
        baitRespawnCountdown--;

        if (baitRespawnCountdown <= 0 && !baitExists)
            controller.fishList.add(getBait());
    }

    public void scheduleBaitRespawn(final int frameCount)
    {
        baitRespawnCountdown = frameCount;
        baitExists = false;
    }


    public CSFishBase getBait()
    {
        baitExists = true;
        return new Bait(this);
    }

}


class Bait extends CSFishBase //CSCharCrackerFishy
{
    private final CSFishingBoat boat;
    //private final CSGameWorldController controller;

    private boolean eatenByPlayer = false;
    private int fishHookTimer = 0;

    public Bait(final CSFishingBoat b)
    {
        super(b.controller);
        boat = b;
        //controller = b.controller;
        setLocation(boat.getX(), data.getWorldBottomBound()/2);
        setImageGroup(CSDataModel.getInstance().igCrackerFishy);
        setAnimationSequence(CSFishBase.ACTION_SWIM_RIGHT);
        setHorizontalSpeed(1);
        setVerticalSpeed(0);
        setHealth(1);
        setMaxHealth(1);
        setStateBehavior(sbSwimRight);
    }


    @Override
    public void advance()
    {
        super.advance();

        final double leftBound = boat.getX()-boat.getImage().getWidth(null);
        final double rightBound = boat.getX()+boat.getImage().getWidth(null)*2;

        if (getX() < leftBound)
        {
            setX(leftBound);
            setAnimationSequence(CSFishBase.ACTION_SWIM_RIGHT);
            setDirFacing(FACING_RIGHT);
            setStateBehavior(sbSwimRight);
            setHorizontalSpeed(Math.abs(getHorizontalSpeed()));
        }
        else if (getX() > rightBound)
        {
            setX(rightBound);
            setAnimationSequence(CSFishBase.ACTION_SWIM_LEFT);
            setDirFacing(FACING_LEFT);
            setStateBehavior(sbSwimLeft);
            setHorizontalSpeed(Math.abs(getHorizontalSpeed())*-1);
        }
    }

    @Override
    public void render(final Graphics2D g, final int x, final int y)
    {
        if (Math.random() < 0.01 || controller.skillLevelData.getSkillLevel() == CSSkillLevel.EASY)
        {
            g.setColor(Color.WHITE);
            g.drawLine((int)(getScaledBounds().getCenterX()-controller.gameScreen.screenRect.getX()),
                            (int)(getScaledBounds().getCenterY()-controller.gameScreen.screenRect.getY()),
                            (int)((boat.getX()+boat.getImage().getWidth(null)/2)-controller.gameScreen.screenRect.getX()),
                            (int)(boat.getY()-controller.gameScreen.screenRect.getY()));
        }
        super.render(g, x, y);
        if (getHealth() <= 0 && eatenByPlayer)
        {
            final int hx = (400 - data.imgFishHook.getWidth(null)/2);
            final int hy = (300 - data.imgFishHook.getHeight(null)/2);
            g.drawImage(data.imgFishHook, hx, hy, null);
            fishHookTimer--;
            if (fishHookTimer <= 0)
                eatenByPlayer = false;
        }
    }


    @Override
    public void gotEatenBy(final CSCharCornShark attacker)
    {
        if (attacker.hasPowerUp())
        {
            final CSTextGrowEffect textEffect = new CSTextGrowEffect("Protected!", (int)(getX()+(getScaledBounds().getWidth()/2)), (int)getY(), controller);
            textEffect.setColor(Color.CYAN);
            textEffect.setSize(16);
            textEffect.setFinalSize(320);
            textEffect.setDelta(1.1);
            controller.foregroundList.add(textEffect);
        }
        else
        {
            eatenByPlayer = true;
            fishHookTimer = 70;

            attacker.setHealth(0);
            attacker.setLevel(attacker.getLevel()-1);

            final CSTextGrowEffect textEffect = new CSTextGrowEffect("You took the bait!", (int)(getX()+(getScaledBounds().getWidth()/2)), (int)getY(), controller);
            textEffect.setColor(Color.RED);
            textEffect.setSize(256);
            textEffect.setFinalSize(8);
            textEffect.setDelta(0.96);
            controller.foregroundList.add(textEffect);
        }

        boat.scheduleBaitRespawn(28 * 20);
    }


    @Override
    public void gotEatenBy(final CSFishBase attacker)
    {
        eatenByPlayer = false;

        //super.gotEatenBy(attacker);
        attacker.setHealth(0);
        attacker.setLevel(-1);
        boat.scheduleBaitRespawn(28 * 20);
    }


    @Override
    public int getDamage()
    {
        return 0;
    }

    @Override
    public void findTarget()
    {
    }
}
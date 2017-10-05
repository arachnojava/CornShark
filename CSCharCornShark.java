import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

public class CSCharCornShark extends CSFishBase
{
    public boolean swimmingRight = false;
    public boolean swimmingLeft  = false;
    public boolean swimmingUp    = false;
    public boolean swimmingDown  = false;

    public CSStateBehavior sbSwimUp = new SwimUp(this);
    public CSStateBehavior sbSwimDown = new SwimDown(this);
    public CSStateBehavior sbIdle = new Idle(this);

    CSPhatFishEffect multiplier;
    CSStripedRacerEffect speedBoost;
    CSShellFishEffect invincibility;

    private final ArrayList<CSPowerUpGlitter> glitter;

    public CSCharCornShark(final CSGameWorldController c)
    {
        super(c);

        particleColors = new Color[] {Color.YELLOW, Color.GREEN};

        sbSwimRight = new SwimRight(this);
        sbSwimLeft = new SwimLeft(this);

        glitter = new ArrayList<CSPowerUpGlitter>();

        this.setImageGroup(CSDataModel.getInstance().igCornShark);
        this.setLocation(Math.random()*data.getWorldWidth(), Math.random()*data.getWorldHeight());
        this.setMaxHSpeed(16);
        this.setMaxVSpeed(getMaxHSpeed()/2);
        this.setAcceleration(1.08);
        this.setDrag(0.96);
        this.setStateBehavior(sbIdle);

        sbBite = new CSAIBite(this, getStateBehavior());

        this.setLevel(1);
        this.setHealth((int)(c.getFishLevelData().getWidth(1)/2));
        this.setHealth(0);

        sbHunt = null;

    }


    @Override
    public void render(final Graphics2D g, final int rx, final int ry)
    {
        super.render(g, rx, ry);

        for (final CSPowerUpGlitter effect : glitter)
            effect.render(g, (int)controller.gameScreen.screenRect.getX(), (int)controller.gameScreen.screenRect.getY());
    }


    @Override
    public void advance()
    {
        //hasTarget = true;
        for (int i = 0; i < glitter.size(); i++)
        {
            glitter.get(i).advance();
            if (glitter.get(i).isAnimationFinished())
                glitter.remove(i);
        }

        if (hasPowerUp())
            glitter.add(new CSPowerUpGlitter(this));

        super.advance();

        if ( (multiplier != null && multiplier.isFinished())
           ||(speedBoost != null && speedBoost.isFinished())
           ||(invincibility != null && invincibility.isFinished())
           )
        {
            data.playSound(data.idLosePowerUp);

            final CSTextGrowEffect textEffect = new CSTextGrowEffect("Power-up Expired", (int)(getX()+(getScaledBounds().getWidth()/2)), (int)getY(), controller);
            textEffect.setColor(Color.MAGENTA);
            textEffect.setSize(128);
            textEffect.setFinalSize(8);
            textEffect.setDelta(0.90);
            controller.foregroundList.add(textEffect);

            if (multiplier != null)
            {
                multiplier.expire(this);
                multiplier = null;
            }
            else if (speedBoost != null)
            {
                speedBoost.expire(this);
                speedBoost = null;
            }
            else if (invincibility != null)
            {
                invincibility.expire(this);
                invincibility = null;
            }
        }

        if (biting)
        {}
        else if (swimmingRight)
            setStateBehavior(this.sbSwimRight);
        else if (swimmingLeft)
            setStateBehavior(this.sbSwimLeft);
        else if (swimmingUp)
            setStateBehavior(this.sbSwimUp);
        else if (swimmingDown)
            setStateBehavior(this.sbSwimDown);
        else
            setStateBehavior(this.sbIdle);

        updateScale();

        //if (getStateBehavior() == null)
        //    setStateBehavior(sbIdle);

        //getStateBehavior().advance();

        checkBounds(this);
    }


    @Override
    public void findTarget()
    {

    }


    /**
     * @param score the score to set
     */
    @Override
    public void setHealth(final int s)
    {
        if (s < 0)
        {
            setLevel(getLevel() - 1);

            if (getLevel() >= 0)
            {
                super.setHealth(getPointsForLevel(getLevel()+1) + s);

                data.playSound(data.idLevelDown);

                final CSTextGrowEffect textEffect = new CSTextGrowEffect("Level Down", (int)(getX()+(getScaledBounds().getWidth()/2)), (int)getY(), controller);
                textEffect.setColor(Color.RED);
                textEffect.setDelta(0.8);
                textEffect.setSize(256);
                textEffect.setFinalSize(1);
                controller.foregroundList.add(textEffect);
            }
            else
                super.setHealth(0);
        }
        else if (s >= getPointsForLevel(getLevel()))
        {
            super.setHealth(s - getPointsForLevel(getLevel()));

            data.playSound(data.idLevelUp);

            setLevel(getLevel() + 1);
            final CSTextGrowEffect textEffect = new CSTextGrowEffect("Level Up", (int)(getX()+(getScaledBounds().getWidth()/2)), (int)getY(), controller);
            textEffect.setColor(Color.GREEN);
            textEffect.setDelta(1.2);
            textEffect.setFinalSize(256);
            controller.foregroundList.add(textEffect);
        }
        else
            super.setHealth(s);
    }

    /* (non-Javadoc)
     * @see CSFishBase#decreaseHealth(int)
     */
    @Override
    public void decreaseHealth(final int damage)
    {
        if (invincibility != null)
            return;

        setHealth(getHealth() - damage);

        final CSTextGrowEffect textEffect = new CSTextGrowEffect("-"+damage, (int)(getX()+(getScaledBounds().getWidth()/2)), (int)getY(), controller);
        textEffect.setColor(Color.RED);
        controller.foregroundList.add(textEffect);

        gotBit = true;
        timeBit = System.currentTimeMillis();

        data.playSound(data.idCornSharkGetBit);

        final Random rand = new Random();
        for (int p = 0; p < 4; p++)
        {
            // Choose color
            Color c = particleColors[rand.nextInt(particleColors.length)];
            // Spawn particle
            CSParticleManager.spawnParticle((int)getCenterX(), (int)getCenterY(), c);
            // Choose color
            c = particleColors[rand.nextInt(particleColors.length)];
            // Spawn particle
            CSParticleManager.spawnParticle((int)getCenterX(), (int)getCenterY(), c);
        }
    }

    @Override
    public void bite()
    {
        if (!biting)
        {
            biting = true;
            setStateBehavior(new CSAIBite(this, getStateBehavior()));
        }
    }

    @Override
    public boolean isDead()
    {
        return (getHealth() <= 0 && getLevel() <= 0);
    }


    @Override
    public void bite(final CSFishBase target)
    {
        if (!biting)
        {
            bite();
            target.decreaseHealth(this.getDamage());
            if (target.isDead())
                target.gotEatenBy(this);
        }
    }


    public boolean hasPowerUp()
    {
        if (multiplier != null || speedBoost != null || invincibility != null)
            return true;

        return false;
    }



    /* (non-Javadoc)
     * @see CSFishBase#drawStatusBar(java.awt.Graphics)
     */
    @Override
    protected void drawStatusBar(final Graphics2D g)
    {
    }


    /**
     * @return the nextLevelScore
     */
    public int getNextLevelScore()
    {
        return getPointsForLevel(getLevel() + 1);
    }


    @Override
    public int getDamage()
    {
    	double w = controller.getFishLevelData().getWidth(getLevel()) * 2;
    	//double h = getBounds().getHeight();

    	if (CSOptionsData.getInstance().getSkillLevel() == CSSkillLevel.EASY)
    	    w *= 1.5;

        return (int)(w);
    }


class SwimRight extends CSStateBehavior
{

    public SwimRight(final CSFishBase actor)
    {
        super(actor);
    }

    @Override
    public void advance()
    {
        double speed;

        if (swimmingRight)
        {
            setDirFacing(CSFishBase.FACING_RIGHT);

            if (swimmingUp)
            {
                setAnimationSequence(CSFishBase.ACTION_SWIM_UP_RIGHT);
                setVerticalSpeed(getMaxVSpeed() * -1);
            }
            else if (swimmingDown)
            {
                setAnimationSequence(CSFishBase.ACTION_SWIM_DOWN_RIGHT);
                setVerticalSpeed(getMaxVSpeed());
            }
            else
            {
                setAnimationSequence(CSFishBase.ACTION_SWIM_RIGHT);
                final double vs = getVerticalSpeed() * (getDrag()/2);

                if (Math.abs(vs) < 1)
                    setVerticalSpeed(0);
                else
                    setVerticalSpeed(vs);
            }

            if (getHorizontalSpeed() <= 0)
            {
                //setHorizontalSpeed(getHorizontalSpeed()*(getDrag()/3));
                setHorizontalSpeed(getHorizontalSpeed()*-1);
                if ((int)getHorizontalSpeed() == 0)
                    setHorizontalSpeed(1);
            }
            else
            {
                speed = getHorizontalSpeed() * getAcceleration();
                if (speed <= getMaxHSpeed())
                    setHorizontalSpeed(speed);
                else
                    setHorizontalSpeed(getMaxHSpeed());
            }
        }
    }
}

class SwimLeft extends CSStateBehavior
{

    public SwimLeft(final CSCharCornShark actor)
    {
        super(actor);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void advance()
    {
        double speed;

        if (swimmingLeft)
        {
            setDirFacing(CSFishBase.FACING_LEFT);

            if (swimmingUp)
            {
                setAnimationSequence(CSFishBase.ACTION_SWIM_UP_LEFT);
                setVerticalSpeed(getMaxVSpeed() * -1);
            }
            else if (swimmingDown)
            {
                setAnimationSequence(CSFishBase.ACTION_SWIM_DOWN_LEFT);
                setVerticalSpeed(getMaxVSpeed());
            }
            else
            {
                setAnimationSequence(CSFishBase.ACTION_SWIM_LEFT);
                final double vs = getVerticalSpeed() * (getDrag()/2);

                if (Math.abs(vs) < 1)
                    setVerticalSpeed(0);
                else
                    setVerticalSpeed(vs);
            }

            if (getHorizontalSpeed() >= 0)
            {
                //setHorizontalSpeed(getHorizontalSpeed()*(getDrag()/3));
                setHorizontalSpeed(getHorizontalSpeed()*-1);
                if ((int)getHorizontalSpeed() == 0)
                    setHorizontalSpeed(-1);
            }
            else
            {
                speed = getHorizontalSpeed() * getAcceleration();
                if (speed >= (getMaxHSpeed() * -1))
                    setHorizontalSpeed(speed);
                else
                    setHorizontalSpeed(getMaxHSpeed() * -1);
            }

        }
    } // advance()

}

class SwimUp extends CSStateBehavior
{

    public SwimUp(final CSCharCornShark actor)
    {
        super(actor);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void advance()
    {
        double speed = getHorizontalSpeed();

        if (getVerticalSpeed() > getMaxVSpeed() * -1)
            setVerticalSpeed(getVerticalSpeed() - 1);

        if (getDirFacing() == CSFishBase.FACING_RIGHT)
        {
            setAnimationSequence(CSFishBase.ACTION_SWIM_UP_RIGHT);
            speed = getHorizontalSpeed() * getAcceleration();
            if (speed < 1) speed = 1;
            if (speed <= getMaxHSpeed())
                setHorizontalSpeed(speed);
            else
                setHorizontalSpeed(getMaxHSpeed());
        }
        else
        {
            setAnimationSequence(CSFishBase.ACTION_SWIM_UP_LEFT);
            speed = getHorizontalSpeed() * getAcceleration();
            if (speed > -1) speed = -1;
            if (speed >= (getMaxHSpeed() * -1))
                setHorizontalSpeed(speed);
            else
                setHorizontalSpeed(getMaxHSpeed() * -1);
        }

    }

}

class SwimDown extends CSStateBehavior
{

    public SwimDown(final CSFishBase actor)
    {
        super(actor);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void advance()
    {
        double speed = getHorizontalSpeed() * getDrag();

        if (Math.abs(speed) < 1.0)
            speed = 0;

        setHorizontalSpeed(speed);

        if (getVerticalSpeed() < 0)
            setVerticalSpeed(getAcceleration());

        if (getVerticalSpeed() < getMaxVSpeed())
            setVerticalSpeed(getVerticalSpeed() * getAcceleration());

        setVerticalSpeed(getMaxVSpeed());

        if (getDirFacing() == CSFishBase.FACING_RIGHT)
        {
            setAnimationSequence(CSFishBase.ACTION_SWIM_DOWN_RIGHT);
            speed = getHorizontalSpeed() * getAcceleration();
            if (speed < 1) speed = 1;
            if (speed <= getMaxHSpeed())
                setHorizontalSpeed(speed);
            else
                setHorizontalSpeed(getMaxHSpeed());
        }
        else
        {
            setAnimationSequence(CSFishBase.ACTION_SWIM_DOWN_LEFT);
            speed = getHorizontalSpeed() * getAcceleration();
            if (speed > -1) speed = -1;
            if (speed >= (getMaxHSpeed() * -1))
                setHorizontalSpeed(speed);
            else
                setHorizontalSpeed(getMaxHSpeed() * -1);
        }

    }

}

class Idle extends CSStateBehavior
{

    public Idle(final CSFishBase actor)
    {
        super(actor);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void advance()
    {
        double hspeed = getHorizontalSpeed() * getDrag();
        double vspeed = getVerticalSpeed() * getDrag();

        if (Math.abs(hspeed) < 1.0)
            hspeed = 0;
        if (Math.abs(vspeed) < 1.0)
            vspeed = 0;

        setHorizontalSpeed(hspeed);
        setVerticalSpeed(vspeed);

        if (getDirFacing() == CSFishBase.FACING_RIGHT)
            setAnimationSequence(CSFishBase.ACTION_IDLE_RIGHT);
        else
            setAnimationSequence(CSFishBase.ACTION_IDLE_LEFT);
    }
}





}


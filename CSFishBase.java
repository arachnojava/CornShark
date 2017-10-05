import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import mhframework.MHActor;


public class CSFishBase extends MHActor
{
    public static final int ACTION_SWIM_RIGHT      = 0;
    public static final int ACTION_SWIM_LEFT       = 1;
    public static final int ACTION_BITE_RIGHT      = 2;
    public static final int ACTION_BITE_LEFT       = 3;
    public static final int ACTION_DIE             = 4;
    public static final int ACTION_SWIM_DOWN_RIGHT = 5;
    public static final int ACTION_SWIM_DOWN_LEFT  = 6;
    public static final int ACTION_SWIM_UP_RIGHT   = 7;
    public static final int ACTION_SWIM_UP_LEFT    = 8;
    public static final int ACTION_IDLE_RIGHT      = 9;
    public static final int ACTION_IDLE_LEFT       = 10;

    public static final int FACING_RIGHT = 0;
    public static final int FACING_LEFT = 1;

    private Rectangle2D offenseArea, defenseArea, sensorRange;
    private int level;
    private double maxHSpeed, maxVSpeed, acceleration, drag;
    private int dirFacing;
    public boolean biting        = false;

    public int number;

    public boolean gotBit = false;
    protected long timeBit;
    protected long bitTimeLimit = 750L;

    //boolean hasTarget = false;
    public long timeTargetAcquired = System.currentTimeMillis();
    long targetTimeLimit = 5000L;

    public final CSGameWorldController controller;
    public final CSDataModel data = CSDataModel.getInstance();

    private CSStateBehavior stateBehavior;

    public CSStateBehavior sbSwimLeft;
    public CSStateBehavior sbSwimRight;
    public CSAIHunt sbHunt;
    public CSStateBehavior sbFlee;
    public CSAIBite sbBite;
    protected Color particleColors[] = new Color[] {Color.RED};


    public CSFishBase(final CSGameWorldController c)
    {
        final double x = getX();
        final double y = getY();

        controller = c;
        sbSwimRight = new CSAISwimRight(this);
        sbSwimLeft = new CSAISwimLeft(this);
        sbBite = new CSAIBite(this);
        sbHunt = new CSAIHunt(this);
        sbFlee = new CSAIFlee(this);
        final double s = c.skillLevelData.getSensorRangeSize();
        setSensorRange(new Rectangle2D.Double(x-s/2, y-s/2, s, s));
    }


    /****************************************************************
     * @return the point value
     */
    public int getPointValue()
    {
        final CSDataModel data = CSDataModel.getInstance();
        final int baseValue = (int)(controller.getFishLevelData().getWidth(getLevel())/4)+getLevel();

        int pointValue = baseValue;

        if (this.getLevel() < data.getPlayer(controller).getLevel())
            pointValue = baseValue / (data.getPlayer(controller).getLevel() - getLevel());
        //else if (this.getLevel() > data.getPlayer(controller).getLevel())
        //    pointValue = baseValue * (getLevel() - data.getPlayer(controller).getLevel());

        return pointValue;
    }


    public void gotEatenBy(final CSFishBase attacker)
    {
    }


    public void gotEatenBy(final CSCharCornShark attacker)
    {
        int multiplier = 1;

        if (attacker.multiplier != null)
            multiplier = attacker.multiplier.multiplier;

        final int pts = getPointValue() * multiplier;

        final CSTextGrowEffect textEffect = new CSTextGrowEffect("+"+pts, (int)(getX()+(getScaledBounds().getWidth()/2)), (int)getY(), controller);
        textEffect.setColor(Color.YELLOW);
        textEffect.setFinalSize(200);
        textEffect.setDelta(textEffect.getDelta()+0.10);
        controller.foregroundList.add(textEffect);

        attacker.setHealth(attacker.getHealth() + pts);
    }


    public void findTarget()
    {
        final CSCharCornShark player = data.getPlayer(controller);

        if (getSensorRange().intersects(player.getScaledBounds()))
        {
            timeTargetAcquired = System.currentTimeMillis();

            if (player.getLevel() < this.getLevel())
                setStateBehavior(sbHunt);
            else if (player.getLevel() > this.getLevel())
                setStateBehavior(sbFlee);
        }
    }

    @Override
    public void advance()
    {
        final long currentTime = System.currentTimeMillis();

        if (gotBit && currentTime - timeBit >= bitTimeLimit)
            gotBit = false;

        // Perform basic MHActor animation.
        super.advance();

        if (isDead()) return;

        getStateBehavior().advance();

        updateScale();
        checkBounds(this);

        if (Math.abs(currentTime - timeTargetAcquired) > targetTimeLimit)
            findTarget();

        final double x = getX();
        final double y = getY();
        final double w = getScaledBounds().getWidth();
        final double h = getScaledBounds().getHeight() / 3;

        final double offenseWidth = w/5;
        final double defenseWidth = w - offenseWidth;

        final int s = controller.skillLevelData.getSensorRangeSize();
        setSensorRange(new Rectangle2D.Double(x+getBounds().getWidth()/2.0-s/2.0, y+getBounds().getHeight()/2.0-s/2.0, s, s));

        final int action = getAnimationSequenceNumber(); // optimization to reduce method calls

        // Process left and right
        if (dirFacing == CSFishBase.FACING_RIGHT)
        {
            getOffenseArea().setRect(x+defenseWidth, y+h, offenseWidth, h);
            getDefenseArea().setRect(x, y+h, defenseWidth, h);

            if (action == ACTION_SWIM_UP_RIGHT)
                getOffenseArea().setRect(x+defenseWidth, y, offenseWidth, h);
            else if (action == ACTION_SWIM_DOWN_RIGHT)
                getOffenseArea().setRect(x+defenseWidth, y+h*2, offenseWidth, h);
        }
        else
        {
            getOffenseArea().setRect(x, y+h, offenseWidth, h);
            getDefenseArea().setRect(x+offenseWidth, y+h, defenseWidth, h);

            if (action == ACTION_SWIM_UP_LEFT)
                getOffenseArea().setRect(x, y, offenseWidth, h);
            else if (action == ACTION_SWIM_DOWN_LEFT)
                getOffenseArea().setRect(x, y+h*2, offenseWidth, h);
        }
    }

    /* (non-Javadoc)
     * @see mhframework.MHStaticActor#render(java.awt.Graphics)
     */
    @Override
    public void render(final Graphics2D g)
    {
        super.render(g);

        if (controller.skillLevelData.getSkillLevel() == CSSkillLevel.EASY)
        {
            // If skill level is Easy, display fish level.
            g.setColor(Color.WHITE);
            g.setFont(new Font("SansSerif", 8, Font.PLAIN));
            g.drawString("Level "+getLevel(),
                            (int)(getScaledBounds().getCenterX()-controller.gameScreen.screenRect.getX()),
                            (int)(getY() + getScaledBounds().getHeight()-controller.gameScreen.screenRect.getY()));
//            g.drawLine((int)(getScaledBounds().getCenterX()-controller.gameScreen.screenRect.getX()),
//                            (int)(getScaledBounds().getCenterY()-controller.gameScreen.screenRect.getY()),
//                            (int)((boat.getX()+boat.getImage().getWidth(null)/2)-controller.gameScreen.screenRect.getX()),
//                            (int)(boat.getY()-controller.gameScreen.screenRect.getY()));
        }
    }




    /* (non-Javadoc)
     * @see mhframework.MHStaticActor#render(java.awt.Graphics, int, int)
     */
    @Override
    public void render(final Graphics2D g, final int rx, final int ry)
    {
        super.render(g, rx, ry);

        if (controller.skillLevelData.getSkillLevel() == CSSkillLevel.EASY)
        {
            // If skill level is Easy, display fish level.
            g.setColor(Color.WHITE);
            g.setFont(new Font("SansSerif", Font.BOLD, 20));
            g.drawString(""+getLevel(),
                            (int)(getScaledBounds().getCenterX()-controller.gameScreen.screenRect.getX()),
                            (int)(getY() + getScaledBounds().getHeight()/2 - controller.gameScreen.screenRect.getY()));
        }

        if (shouldDrawHealthBar())
            drawStatusBar(g);
    }


    private boolean shouldDrawHealthBar()
    {
        final int option = CSOptionsData.getInstance().getHealthBarOption();

        return ((option == CSOptionsData.HEALTH_BAR_AUTO && gotBit)
               || option == CSOptionsData.HEALTH_BAR_ON)
               && !isDead();
    }

    public void bite()
    {
        if (!biting)
        {
            biting = true;
            sbBite.setPrevState(getStateBehavior());
            setStateBehavior(this.sbBite);
            setFrameNumber(0);
        }
    }


    public void bite(final CSFishBase target)
    {
        if (!biting)
        {
            bite();
            target.decreaseHealth(getDamage());
            if (target.isDead())
                target.gotEatenBy(this);
            else
                data.playSound(data.idNPCBite);
        }
    }


    /****************************************************************
     * Keep the input fish from escaping the world bounds.
     *
     * @param fish
     */
    public void checkBounds(final CSFishBase fish)
    {
        final double x = fish.getX();
        final double y = fish.getY();
        final double w = fish.getScaledBounds().getWidth();
        final double h = fish.getScaledBounds().getHeight();

        if (x < data.getWorldLeftBound())
            fish.setX(data.getWorldLeftBound());
        if (x > data.getWorldRightBound() - w)
            fish.setX(data.getWorldRightBound() - w);
        if (y < data.getWorldTopBound())
            fish.setY(data.getWorldTopBound());
        if (y > data.getWorldBottomBound() - h)
            fish.setY(data.getWorldBottomBound() - h);
    }


    /****************************************************************
     */
    protected void drawStatusBar(final Graphics2D g)
    {
        final double healthPct = ((double)getHealth() / (double)getMaxHealth());
        final int totalWidth = 50;
        final int width = (int)Math.ceil(totalWidth * healthPct);
        final int barHeight = 2;
        final int screenX =  (int) (getCenterX() - controller.gameScreen.screenRect.getX())-totalWidth/2;
        final int screenY = (int) (getY() - controller.gameScreen.screenRect.getY())-barHeight;

        g.setColor(Color.BLACK);
        g.drawRect(screenX, screenY, totalWidth, barHeight);

        Color c;

        if (healthPct >= 0.75)
            c = Color.GREEN;
        else if (healthPct >= 0.5)
            c = Color.YELLOW;
        else if (healthPct >= 0.25)
            c = Color.ORANGE;
        else
            c = Color.RED;

        g.setColor(c);
        g.fillRect(screenX, screenY, width, barHeight);
    }


    public void updateScale()
    {
        final double widthForLevel = controller.getFishLevelData().getWidth(getLevel());
        final double originalWidth = this.getImage().getWidth(null);

        setScale(widthForLevel / originalWidth);
    }

    /**
     * @return the offenseArea
     */
    public Rectangle2D getOffenseArea()
    {
        if (offenseArea == null)
            offenseArea = new Rectangle2D.Double();

        return offenseArea;
    }

    /**
     * @param offenseArea the offenseArea to set
     */
    public void setOffenseArea(final Rectangle2D offenseArea)
    {
        this.offenseArea = offenseArea;
    }

    /**
     * @return the defenseArea
     */
    public Rectangle2D getDefenseArea()
    {
        if (defenseArea == null)
            defenseArea = new Rectangle2D.Double();

        return defenseArea;
    }

    /**
     * @param defenseArea the defenseArea to set
     */
    public void setDefenseArea(final Rectangle2D defenseArea)
    {
        this.defenseArea = defenseArea;
    }

    /**
     * @return the level
     */
    public int getLevel()
    {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(final int lvl)
    {
        if (lvl >= CSFishLevelData.NUM_LEVELS)
            level = CSFishLevelData.NUM_LEVELS - 1;
        else
            level = lvl;

        updateScale();
    }

    /**
     * @return the sensorRange
     */
    public Rectangle2D getSensorRange()
    {
        return sensorRange;
    }

    /**
     * @param sensorRange the sensorRange to set
     */
    public void setSensorRange(final Rectangle2D sensorRange)
    {
        this.sensorRange = sensorRange;
    }

    /**
     * @return the maxHSpeed
     */
    public double getMaxHSpeed()
    {
        return maxHSpeed;
    }

    /**
     * @param maxHSpeed the maxHSpeed to set
     */
    public void setMaxHSpeed(final double maxHSpeed)
    {
        this.maxHSpeed = maxHSpeed;
    }

    /**
     * @return the maxVSpeed
     */
    public double getMaxVSpeed()
    {
        return maxVSpeed;
    }

    /**
     * @param maxVSpeed the maxVSpeed to set
     */
    public void setMaxVSpeed(final double maxVSpeed)
    {
        this.maxVSpeed = maxVSpeed;
    }

    /**
     * @return the acceleration
     */
    public double getAcceleration()
    {
        return acceleration;
    }

    /**
     * @param acceleration the acceleration to set
     */
    public void setAcceleration(final double acceleration)
    {
        this.acceleration = acceleration;
    }

    /**
     * @return the drag
     */
    public double getDrag()
    {
        return drag;
    }

    /**
     * @param drag the drag to set
     */
    public void setDrag(final double drag)
    {
        this.drag = drag;
    }

    /**
     * @return the stateBehavior
     */
    public CSStateBehavior getStateBehavior()
    {
        return stateBehavior;
    }

    /**
     * @param stateBehavior the stateBehavior to set
     */
    public void setStateBehavior(final CSStateBehavior stateBehavior)
    {
        this.stateBehavior = stateBehavior;
    }

    /**
     * @return the dirFacing
     */
    public int getDirFacing()
    {
        return dirFacing;
    }

    /**
     * @param dirFacing the dirFacing to set
     */
    public void setDirFacing(final int dirFacing)
    {
        this.dirFacing = dirFacing;
    }

    public int getDamage()
    {
        int damage = (getLevel() * 2);

        if (CSOptionsData.getInstance().getSkillLevel() == CSSkillLevel.EASY)
            damage /= 2;

        return damage + 1;
    }

    public boolean isDead()
    {
        return getHealth() <= 0;
    }

    public boolean isDisposable()
    {
        return isDead() && isAnimationFinished();
    }

    public void decreaseHealth(final int damage)
    {
        final Random rand = new Random();

        setHealth(getHealth() - damage);

        if (isDead())
        {
            data.playSound(data.idNPCDie);
            if (!isPowerUp())
            {
                setAnimationSequence(CSFishBase.ACTION_DIE);
                setFrameNumber(0);

                // Loop partially unrolled for optimization.
                for (int p = 0; p < 6; p++)
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
        }
        else
        {
            data.playSound(data.idGetBit);
            final CSTextGrowEffect textEffect = new CSTextGrowEffect("-"+damage, (int)(getX()+(getScaledBounds().getWidth()/2)), (int)getY(), controller);
            textEffect.setColor(Color.RED);
            controller.foregroundList.add(textEffect);

            gotBit = true;
            timeBit = System.currentTimeMillis();

            // Loop partially unrolled for optimization.
            for (int p = 0; p < 3; p++)
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
    }


    public final int getPointsForLevel(final int level)
    {
        return (int)(controller.getFishLevelData().getWidth(level));
    }


    public int calculateHealth()
    {
    	int h = 0;

    	for (int i = getLevel(); i > 0; i--)
    		h += controller.getFishLevelData().getWidth(i);

    	return h;
    }

    public boolean isPowerUp()
    {
        return false;
    }
}

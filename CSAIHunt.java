public class CSAIHunt extends CSStateBehavior
{
    private double prevHSpeed, prevVSpeed;
    private int substate = 0;

    public CSAIHunt(final CSFishBase actor)
    {
        super(actor);
    }

    public void advance(final Object a)
    {
        final CSFishBase actor = getActor();
        final CSCharCornShark player = data.getPlayer(actor.controller);

        if (substate == 0)
        {
            prevHSpeed = actor.getHorizontalSpeed();
            prevVSpeed = actor.getVerticalSpeed();
            substate++;
        }

        if (closeEnough(actor.getOffenseArea().getCenterY(), player.getDefenseArea().getCenterY(), actor.getVerticalSpeed()))
        {
            actor.setVerticalSpeed(0);
            actor.setY(player.getDefenseArea().getY() - (actor.getOffenseArea().getY() - actor.getY()));

            if (closeEnough(actor.getOffenseArea().getCenterX(), player.getDefenseArea().getCenterX(), actor.getHorizontalSpeed()))
            {
                reset(actor);
            }
            else if (actor.getOffenseArea().getCenterX() < player.getDefenseArea().getCenterX())
            {
                if (player.getDirFacing() == CSFishBase.FACING_RIGHT)
                    actor.setHorizontalSpeed(actor.getMaxHSpeed());
                else
                    actor.setHorizontalSpeed(0.0);

                actor.setAnimationSequence(CSFishBase.ACTION_SWIM_RIGHT);
                actor.setDirFacing(CSFishBase.FACING_RIGHT);
            }
            else if (actor.getOffenseArea().getCenterX() > player.getDefenseArea().getCenterX())
            {
                if (player.getDirFacing() == CSFishBase.FACING_RIGHT)
                    actor.setHorizontalSpeed(0.0);
                else
                    actor.setHorizontalSpeed(actor.getMaxHSpeed() * -1);

                actor.setAnimationSequence(CSFishBase.ACTION_SWIM_LEFT);
                actor.setDirFacing(CSFishBase.FACING_LEFT);
            }
        }
        else if (actor.getOffenseArea().getCenterY() > player.getDefenseArea().getCenterY())
            actor.setVerticalSpeed(actor.getMaxVSpeed() * -1);
        else
            actor.setVerticalSpeed(actor.getMaxVSpeed());

        if (!actor.getSensorRange().intersects(player.getScaledBounds()))
        {
            reset(actor);
        }  // if !sensorRange...
    }


    @Override
    public void advance()
    {
        final CSFishBase actor = getActor();
        final CSCharCornShark player = data.getPlayer(actor.controller);

        if (substate == 0)
        {
            prevHSpeed = actor.getHorizontalSpeed();
            prevVSpeed = actor.getVerticalSpeed();
            substate++;
        }

        if (closeEnough(actor.getOffenseArea().getCenterY(), player.getDefenseArea().getCenterY(), actor.getVerticalSpeed()))
        {
            actor.setVerticalSpeed(0);
            actor.setY(player.getDefenseArea().getY() - (actor.getOffenseArea().getY() - actor.getY()));

            if (closeEnough(actor.getOffenseArea().getCenterX(), player.getDefenseArea().getCenterX(), actor.getScaledBounds().getWidth()/2))//actor.getHorizontalSpeed()*4))
            {
                reset(actor);
                actor.timeTargetAcquired = System.currentTimeMillis();
                //actor.setHorizontalSpeed(0);
            }
            else if (actor.getOffenseArea().getCenterX() < player.getDefenseArea().getCenterX())
            {
                actor.setHorizontalSpeed(actor.getMaxHSpeed());
                actor.setAnimationSequence(CSFishBase.ACTION_SWIM_RIGHT);
                actor.setDirFacing(CSFishBase.FACING_RIGHT);
            }
            else if (actor.getOffenseArea().getCenterX() > player.getDefenseArea().getCenterX())
            {
                actor.setHorizontalSpeed(actor.getMaxHSpeed() * -1);
                actor.setAnimationSequence(CSFishBase.ACTION_SWIM_LEFT);
                actor.setDirFacing(CSFishBase.FACING_LEFT);
            }
        }
        else if (actor.getOffenseArea().getCenterY() > player.getDefenseArea().getCenterY())
            actor.setVerticalSpeed(actor.getMaxVSpeed() * -1);
        else
            actor.setVerticalSpeed(actor.getMaxVSpeed());

        if (!actor.getSensorRange().intersects(player.getScaledBounds()))
        {
            reset(actor);
        }  // if !sensorRange...
    }


    private boolean closeEnough(final double pos1, final double pos2, final double distance)
    {
        return (Math.abs(pos1 - pos2) < Math.abs(distance));
    }


    public void reset(final CSFishBase actor)
    {
        substate = 0;
        actor.setVerticalSpeed(prevVSpeed);

        if (actor.getHorizontalSpeed() >= 0)
        {
            actor.setHorizontalSpeed(Math.abs(prevHSpeed));
            actor.setStateBehavior(actor.sbSwimRight);
        }
        else
        {
            actor.setHorizontalSpeed(Math.abs(prevHSpeed)*-1);
            actor.setStateBehavior(actor.sbSwimLeft);
        }
    }

}

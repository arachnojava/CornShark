public class CSAIFlee extends CSStateBehavior
{
    private double prevHSpeed, prevVSpeed;

    private int substate = 0;

    public CSAIFlee(final CSFishBase actor)
    {
        super(actor);
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
            if (actor.getDefenseArea().getCenterX() >= player.getOffenseArea().getCenterX())
                swimRight(actor);
            else
                swimLeft(actor);
        }

        if (Math.random() < 0.01)
            turnAround(actor);
        else if (Math.random() < 0.3)
        {
            if (actor.getDefenseArea().getCenterX() >= player.getOffenseArea().getCenterX())
                swimRight(actor);
            else
                swimLeft(actor);
        }

        if (actor.getDefenseArea().getCenterY() > player.getOffenseArea().getCenterY())
            actor.setVerticalSpeed(actor.getMaxVSpeed());
        else
            actor.setVerticalSpeed(actor.getMaxVSpeed() * -1);

        if (!actor.getSensorRange().intersects(player.getScaledBounds()))
        {
            actor.setVerticalSpeed(prevVSpeed);
            substate = 0;

            if (actor.getHorizontalSpeed() > 0)
            {
                actor.setStateBehavior(actor.sbSwimRight);
                actor.setHorizontalSpeed(Math.abs(prevHSpeed));
            }
            else
            {
                actor.setStateBehavior(actor.sbSwimLeft);
                actor.setHorizontalSpeed(Math.abs(prevHSpeed)*-1);
            }
        }
    }

    private void swimRight(final CSFishBase actor)
    {
        actor.setHorizontalSpeed(actor.getMaxHSpeed());
        actor.setAnimationSequence(CSFishBase.ACTION_SWIM_RIGHT);
        actor.setDirFacing(CSFishBase.FACING_RIGHT);
    }


    private void swimLeft(final CSFishBase actor)
    {
        actor.setHorizontalSpeed(actor.getMaxHSpeed() * -1);
        actor.setAnimationSequence(CSFishBase.ACTION_SWIM_LEFT);
        actor.setDirFacing(CSFishBase.FACING_LEFT);
    }

    private void turnAround(final CSFishBase actor)
    {
        if (actor.getDirFacing() == CSFishBase.FACING_LEFT)
            swimRight(actor);
        else
            swimLeft(actor);
    }
}

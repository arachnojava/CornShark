public class CSAISwimRight extends CSStateBehavior
{

    public CSAISwimRight(final CSFishBase actor)
    {
        super(actor);
    }

    @Override
    public void advance()
    {
        final CSFishBase actor = getActor();

        super.advance();

        if (actor.isDead() || actor.biting) return;

        actor.setDirFacing(CSFishBase.FACING_RIGHT);

        if (actor.getAnimationSequenceNumber() != CSFishBase.ACTION_SWIM_RIGHT)
            actor.setAnimationSequence(CSFishBase.ACTION_SWIM_RIGHT);

        if (actor.getHorizontalSpeed() < 0)
            actor.setHorizontalSpeed(Math.abs(actor.getHorizontalSpeed()));

        if (actor.getX() + actor.getScaledBounds().getWidth() >= data.getWorldRightBound()-20
            || Math.random() < 0.01)
            actor.setStateBehavior(actor.sbSwimLeft);

        if (actor.getY() <= data.getWorldTopBound()+10 || Math.random() < 0.01)
            actor.setVerticalSpeed(Math.abs(actor.getVerticalSpeed()));
        else if (actor.getY() >= data.getWorldBottomBound()-10 || Math.random() < 0.01)
            actor.setVerticalSpeed(Math.abs(actor.getVerticalSpeed()) * -1);

        super.advance();
    }
}
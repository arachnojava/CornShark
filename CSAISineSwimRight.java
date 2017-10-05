public class CSAISineSwimRight extends CSStateBehavior
{

    public double vertical = 10.0;
    private double originalY = 300;

    public CSAISineSwimRight(final CSFishBase actor)
    {
        super(actor);
        originalY = actor.getY();
    }

    @Override
    public void advance()
    {
        final CSFishBase actor = getActor();

        super.advance();

        if (actor.isDead() /*|| actor.biting*/) return;

        actor.setDirFacing(CSFishBase.FACING_RIGHT);

        if (actor.getAnimationSequenceNumber() != CSFishBase.ACTION_SWIM_RIGHT)
            actor.setAnimationSequence(CSFishBase.ACTION_SWIM_RIGHT);

        if (actor.getHorizontalSpeed() < 0)
            actor.setHorizontalSpeed(Math.abs(actor.getHorizontalSpeed()));

        if (actor.getX() + actor.getScaledBounds().getWidth() >= data.getWorldRightBound()-10)
            actor.setStateBehavior(actor.sbSwimLeft);

        actor.setY(originalY + Math.sin(actor.getX()) * vertical);
        //actor.setY(actor.getY() + Math.sin(actor.getX()) * vertical);

        super.advance();
    }
}
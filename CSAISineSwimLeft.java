public class CSAISineSwimLeft extends CSStateBehavior
{

    public double vertical = 16.0;
    private double originalY = 300;

    public CSAISineSwimLeft(final CSFishBase actor)
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

        actor.setDirFacing(CSFishBase.FACING_LEFT);

        if (actor.getAnimationSequenceNumber() != CSFishBase.ACTION_SWIM_LEFT)
            actor.setAnimationSequence(CSFishBase.ACTION_SWIM_LEFT);

        if (actor.getHorizontalSpeed() > 0)
            actor.setHorizontalSpeed(Math.abs(actor.getHorizontalSpeed()) * -1);

        if (actor.getX() <= data.getWorldLeftBound()+10)
            actor.setStateBehavior(actor.sbSwimRight);

        actor.setY(originalY + Math.sin(actor.getX()) * vertical);

        super.advance();
    }
}
public class CSAISwimLeft extends CSStateBehavior
{

    public CSAISwimLeft(final CSFishBase actor)
    {
        super(actor);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void advance()
    {
        final CSFishBase actor = getActor();

        super.advance();

        if (actor.isDead() || actor.biting) return;

        actor.setDirFacing(CSFishBase.FACING_LEFT);

        if (actor.getAnimationSequenceNumber() != CSFishBase.ACTION_SWIM_LEFT)
            actor.setAnimationSequence(CSFishBase.ACTION_SWIM_LEFT);

        if (actor.getHorizontalSpeed() > 0)
            actor.setHorizontalSpeed(actor.getHorizontalSpeed() * -1);

        if (actor.getX() <= data.getWorldLeftBound()+10 || Math.random() < 0.01)
            actor.setStateBehavior(actor.sbSwimRight);

        if (actor.getY() <= data.getWorldTopBound()+10 || Math.random() < 0.01)
            actor.setVerticalSpeed(Math.abs(actor.getVerticalSpeed()));
        else if (actor.getY() >= data.getWorldBottomBound()-10 || Math.random() < 0.01)
            actor.setVerticalSpeed(Math.abs(actor.getVerticalSpeed()) * -1);

    }

}

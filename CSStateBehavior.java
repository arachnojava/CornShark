
public abstract class CSStateBehavior
{
    private CSFishBase actor;
    protected CSDataModel data = CSDataModel.getInstance();

    public CSStateBehavior(final CSFishBase actor)
    {
        setActor(actor);
    }

    /**
     * @return the actor
     */
    public CSFishBase getActor()
    {
        return actor;
    }

    /**
     * @param actor the actor to set
     */
    public void setActor(final CSFishBase actor)
    {
        this.actor = actor;
    }

    public void advance()
    {
        if (getActor().getY() + getActor().getScaledBounds().getHeight() >= data.getWorldHeight()-80)
        {
            getActor().setY(data.getWorldHeight()- getActor().getScaledBounds().getHeight() -80);
            getActor().setVerticalSpeed(Math.abs(getActor().getVerticalSpeed()) * -1);
        }
        if (getActor().getY() < 80)
        {
            getActor().setY(80);
            getActor().setVerticalSpeed(Math.abs(getActor().getVerticalSpeed()));
        }
    }

}

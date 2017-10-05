import java.awt.Graphics2D;
import mhframework.MHActor;

public class CSWaveAnimation extends MHActor
{
    private final CSDataModel data = CSDataModel.getInstance();
    private final float speed = -1.0f;

    public CSWaveAnimation()
    {
        setImageGroup(data.igTop);
    }

    @Override
    public void advance()
    {
        setX(getX() + speed);

        if (getX() < getBounds().getWidth() * -1)
            setX(0 - (float)Math.abs((getBounds().getWidth()*-1) - getX()));

        super.advance();
    }

    @Override
    public void render(final Graphics2D g)
    {
        super.render(g);
        super.render(g,
                  (int)(getX() + getImage().getWidth(null)),
                  (int) getY());
    }
}

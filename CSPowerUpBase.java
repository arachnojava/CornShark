import java.awt.Graphics2D;
import java.util.ArrayList;


public class CSPowerUpBase extends CSFishBase
{
    private final ArrayList<CSPowerUpGlitter> glitter;

    public CSPowerUpBase(final CSGameWorldController c)
    {
        super(c);
        glitter = new ArrayList<CSPowerUpGlitter>();
    }

    /* (non-Javadoc)
     * @see CSFishBase#gotEatenBy(CSFishBase)
     */
    @Override
    public void gotEatenBy(final CSFishBase attacker)
    {
        setHealth(60);
    }

    /* (non-Javadoc)
     * @see CSFishBase#updateScale()
     */
    @Override
    public void updateScale()
    {
        setScale(1.0);
    }


    /* (non-Javadoc)
     * @see CSFishBase#bite()
     */
    @Override
    public void bite()
    {
    }

    /* (non-Javadoc)
     * @see CSFishBase#bite(CSFishBase)
     */
    @Override
    public void bite(final CSFishBase target)
    {
    }

    /* (non-Javadoc)
     * @see mhframework.MHStaticActor#render(java.awt.Graphics, int, int)
     */
    @Override
    public void render(final Graphics2D g, final int rx, final int ry)
    {
        g.drawImage(getImage(), rx, ry, null);

        //try
        //{
            for (final CSPowerUpGlitter effect : glitter)
                effect.render(g, (int)controller.gameScreen.screenRect.getX(), (int)controller.gameScreen.screenRect.getY());
        //}
        //catch (final ConcurrentModificationException e)
        //{
        //    System.err.println("ERROR:  Concurrent modification exception in CSPowerUpBase.render()");
            //e.printStackTrace();
        //}
    }

    @Override
    public void advance()
    {
        for (int i = 0; i < glitter.size(); i++)
        {
            glitter.get(i).advance();
            if (glitter.get(i).isAnimationFinished())
            {
                glitter.remove(i);
            }
        }

        glitter.add(new CSPowerUpGlitter(this));

        super.advance();
    }

    @Override
    public boolean isPowerUp()
    {
        return true;
    }

    @Override
    public boolean isDisposable()
    {
        return isDead();
    }


    /**
     * Overridden to prevent power-up fish from fleeing.
     *
     * @see CSFishBase#findTarget()
     */
    @Override
    public void findTarget()
    {
        sbHunt = null;
        sbFlee = null;
    }

    @Override
    public void gotEatenBy(final CSCharCornShark attacker)
    {
        // TODO Auto-generated method stub
        return;
    }

}

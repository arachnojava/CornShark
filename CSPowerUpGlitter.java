import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;
import mhframework.MHActor;
import mhframework.MHDisplayModeChooser;


public class CSPowerUpGlitter extends MHActor
{
    int numParticles = 8;
    ArrayList<CSPowerUpGlitterParticle> particles;
    private boolean flip;

    public CSPowerUpGlitter(final CSFishBase fish)
    {
        particles = new ArrayList<CSPowerUpGlitterParticle>();
        for (int i = 0; i < numParticles; i++)
        {
            final int x = (int)(fish.getX() + Math.random() * fish.getScaledBounds().getWidth());
            final int y = (int)(fish.getY() + Math.random() * fish.getScaledBounds().getHeight());

            final CSPowerUpGlitterParticle p = new CSPowerUpGlitterParticle(x, y);
            particles.add(p);
        }
    }

    @Override
    public boolean isAnimationFinished()
    {
        for (final CSPowerUpGlitterParticle p : particles)
        {
            if (!p.isAnimationFinished())
                return false;
        }

        return true;
    }

    @Override
    public void render(final Graphics2D g, final int rx, final int ry)
    {
        int i;
        final int start = (flip ? 0 : 1);

        for (i = start; i < particles.size(); i+=2)
            particles.get(i).render(g, rx, ry);

        flip = !flip;
    }


    @Override
    public void advance()
    {
        for (final CSPowerUpGlitterParticle p : particles)
            p.advance();
    }
}

class CSPowerUpGlitterParticle
{
    int x, y;
    private int r, g, b;
    private double size;
    private final double delta = 0.98;
    private final boolean filled;
    Random random = new Random();

    public CSPowerUpGlitterParticle(final int ox, final int oy)
    {
        x = ox;
        y = oy;
        r = 127 + random.nextInt(127);
        g = 127 + random.nextInt(127);
        b = 127 + random.nextInt(127);
        size = 2 + random.nextInt(6);
        filled = (int)((Math.random() * 10000) % 2) == 0;
    }


    public void render(final Graphics2D g2d, final int sx, final int sy)
    {
        g2d.setColor(new Color(r, g, b));

        final int s = (int) size;

        if (filled)
            g2d.fillOval(x-sx+MHDisplayModeChooser.DISPLAY_X, y-sy+MHDisplayModeChooser.DISPLAY_Y, s, s);
        else
            g2d.drawOval(x-sx+MHDisplayModeChooser.DISPLAY_X, y-sy+MHDisplayModeChooser.DISPLAY_Y, s, s);
    }


    public void advance()
    {
        r = (int)(Math.random() * 255);
        g = (int)(Math.random() * 255);
        b = (int)(Math.random() * 255);
        size *= delta;
    }

    public boolean isAnimationFinished()
    {
        if (size < 1)
            return true;

        return false;

    }

}
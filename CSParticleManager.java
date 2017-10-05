import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;


public class CSParticleManager
{
    public static ArrayList<Particle> particleList = new ArrayList<Particle>();

    public static void spawnParticle(final int x, final int y, final Color color)
    {
        particleList.add(new Particle(x, y, 16f, color));
    }


    public static void render(final Graphics g, final double screenX, final double screenY)
    {
        for (final Particle p: particleList)
        {
            p.render(g, screenX, screenY);
        }
    }


    public static void advance()
    {
        try
        {
            for (final Particle p: particleList)
            {
                p.advance();

                if (p.dead)
                    particleList.remove(p);
            }
        }
        catch (final Exception e) {}
    }
}

class Particle
{
    final float GRAVITY_FACTOR = 0.5f;

    Color color = Color.RED;
    float x, y;
    float dx, dy;
    float size;

    boolean dead = false;

    static final Random random = new Random();

    public Particle(final float x, final float y, final float size, final Color color)
    {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;

        dx = -5.0f + (random.nextFloat() * 10.0f);
        dy = -10.0f + (random.nextFloat() * 10.0f);
    }

    public void advance()
    {
        dy += GRAVITY_FACTOR;

        x += dx;
        y += dy;

        size *= 0.96f;

        if (size < 1.0f)
            this.dead = true;
    }

    public void render(final Graphics g, final double sX, final double sY)
    {
        g.setColor(color);
        g.fillOval((int)(x - sX), (int)(y - sY), (int)(size*Math.random())+1, (int)size);
    }
}

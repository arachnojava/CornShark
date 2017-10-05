import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import mhframework.MHActor;


public class CSGameTimer extends MHActor
{
    private final Font font = new Font("SansSerif", Font.BOLD, 32);

    private long gameTimeTotal = 0;
    private long startTime = 0;
    private long pauseTimeTotal = 0;

    private long seconds;

    private long minutes;

    private long hours;

    private long milliseconds;

    private boolean paused = true;

    public void start()
    {
        startTime = System.currentTimeMillis();// - (59500 * 60);  //FOR TESTING
        paused = false;
    }


    public void pause()
    {
        gameTimeTotal += System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();
        paused = true;
    }


    public void resume()
    {
        pauseTimeTotal += System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();
        paused = false;
    }


    public void reset()
    {
        startTime = gameTimeTotal = pauseTimeTotal = 0;
    }


    public long getSeconds()
    {
        return seconds;
    }


    public long getMinutes()
    {
        return minutes;
    }


    public long getHours()
    {
        return hours;
    }


    public long getMilliseconds()
    {
        return milliseconds;
    }


    public long getGameTimeTotal()
    {
        return gameTimeTotal;
    }

    @Override
    public String toString()
    {
        if (!paused) calculate();

        final String h = (getHours()   < 10 ? "0" : "") + getHours();
        final String m = (getMinutes() < 10 ? "0" : "") + getMinutes();
        final String s = (getSeconds() < 10 ? "0" : "") + getSeconds();

        //final long msl = getMilliseconds();
        //String ms = "" + msl;

        //if (msl < 10) ms = "00" + ms;
        //else if (msl < 100) ms = "0" + ms;

        return h + ":" + m + ":" + s; // + "." + ms;
    }


    @Override
    public void render(final Graphics2D g)
    {
        g.setColor(Color.WHITE);
        g.setFont(font);
        final FontMetrics fm = g.getFontMetrics();
        final String s = toString();
        g.drawString(s, 400-fm.stringWidth(s)/2, 580);
    }


    private void calculate()
    {
        final long total = gameTimeTotal + (System.currentTimeMillis() - startTime);

        //milliseconds = total % 1000;
        seconds = (total / 1000) % 60;
        minutes = (total / 60000) % 60;
        hours = total / 3600000;
    }

    public static void main(final String args[])
    {
        final CSGameTimer timer = new CSGameTimer();

        timer.start();

        for (int i = 0; i < 180; i++)
        {
            System.out.println(timer.toString() + " -- " + (timer.gameTimeTotal + (System.currentTimeMillis() - timer.startTime)));
            if (i == 20)
            {
                System.out.println("\tPausing...");
                timer.pause();
            }
            if (i == 40)
            {
                System.out.println("\tResuming...");
                timer.resume();
            }

            try
            {
                Thread.sleep(500);
            }
            catch (final InterruptedException e)
            {
            }
        }
    }
}

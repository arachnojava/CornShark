
import java.io.Serializable;

public class CSHighScoreRecord implements Serializable
{
    ////////////////////////
    ////  Data members  ////
    ////////////////////////

    /**
     *
     */
    private static final long serialVersionUID = -2239710803155707374L;

    /** The name of a player with a high score */
    public String name;

    /** The player's score */
    public long score;

    /** The skill level played when score was earned. */
    public String skillLevel;

    /**
     *  Constructor
     */
    public CSHighScoreRecord()
    {
        name = "--------";
        score = 1000 * 60 * 60 * 24;
        skillLevel = "----";
    }

    @Override
    public String toString()
    {
        return String.format("%.30s \t %s \t %s", name, getTimeString(), skillLevel);
    }

    public String getTimeString()
    {
        final long seconds = (score / 1000) % 60;
        final long minutes = (score / 60000) % 60;
        final long hours = score / 3600000;

        final String h = (hours < 10 ? "0" : "") + hours;
        final String m = (minutes < 10 ? "0" : "") + minutes;
        final String s = (seconds < 10 ? "0" : "") + seconds;

        final String time = h + ":" + m + ":" + s;
        return time;
    }
}


import java.awt.event.KeyEvent;


public class CSKeyCommands
{
    // Player movement controls
    public final static int SWIM_RIGHT  = KeyEvent.VK_RIGHT;
    public final static int SWIM_LEFT   = KeyEvent.VK_LEFT;
    public final static int SWIM_UP     = KeyEvent.VK_UP;
    public final static int SWIM_DOWN   = KeyEvent.VK_DOWN;

    // Other player commands
    public final static int PAUSE       = KeyEvent.VK_ESCAPE;
    public final static int HELP        = KeyEvent.VK_F1;

    // "Cheat" controls
    public final static int DEBUG_MODE  = KeyEvent.VK_F5;
    public final static int SPAWN_PHAT  = KeyEvent.VK_F10;
    public final static int SPAWN_RACER = KeyEvent.VK_F11;
    public final static int SPAWN_SHELL = KeyEvent.VK_F12;
}

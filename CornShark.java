import mhframework.MHAppLauncher;
import mhframework.MHGameApplication;
import mhframework.MHScreen;
import mhframework.MHVideoSettings;

public class CornShark
{
    private static final long serialVersionUID = 1L;
    public static final boolean DEBUG = true;

    public static void main(final String args[])
    {
        final MHScreen screen = new CSMainMenuScreen();

        final MHVideoSettings settings = new MHVideoSettings();
        settings.displayWidth = 800;
        settings.displayHeight = 600;
        settings.bitDepth = 32;
        settings.fullScreen = MHAppLauncher.showDialog(null);
        settings.windowCaption = "CornShark";

        new MHGameApplication(screen, settings);

        System.exit(0);
    }
}

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import mhframework.MHDisplayModeChooser;
import mhframework.MHScreen;

public class CSGameScreen extends MHScreen
{
    public CSGameWorldController controller;
    public Rectangle2D screenRect;
    private final CSDebugDisplay debug;
    private final CSGameHUD hud;
    public boolean gameOver = false;
    public int endGameCountdown;
    //private final boolean renderFrames = false;
    private final CSDataModel data = CSDataModel.getInstance();
    private double
            scrW = MHDisplayModeChooser.getScreenSize().getWidth(),  // getScreenBounds().getWidth(),
            scrH = MHDisplayModeChooser.getScreenSize().getHeight();
    private float alpha = 0;

    private enum GameStatus
    {
        WON, LOST, IN_PROGRESS;
    }

    private GameStatus status = GameStatus.IN_PROGRESS;

    public CSGameScreen()
    {
        screenRect = new Rectangle2D.Double(0, 0, scrW, scrH);
        debug = new CSDebugDisplay(this);
        data.resetImageCount();
        data.loadFishImages();
        controller = new CSGameWorldController(this);
        controller.resetGame();
        hud = new CSGameHUD(this);
        endGameCountdown = 80;

        alpha = 1 - (endGameCountdown / 80.0f);

        data.timer.reset();
    }

    @Override
    public void load()
    {
        data.stopMusic();
        scrW = MHDisplayModeChooser.getScreenSize().getWidth();
        scrH = MHDisplayModeChooser.getScreenSize().getHeight();
        screenRect = new Rectangle2D.Double(0, 0, scrW, scrH);

        this.setFinished(false);
        this.setNextScreen(null);

        data.playMusic(data.gameMusic);
    }


    @Override
    public void unload()
    {
        data.stopMusic();
    }


    @Override
    public void advance()
    {
        if (!data.doneLoading())
            return;

        if (!gameOver)
            controller.advance();

        if (CSDataModel.debug)
        {
            final int scrollSpeed = 64;
            if (CSDataModel.scrollingRight)
                screenRect.setRect(screenRect.getX()+scrollSpeed, screenRect.getY(), scrW, scrH);
            if (CSDataModel.scrollingLeft)
                screenRect.setRect(screenRect.getX()-scrollSpeed, screenRect.getY(), scrW, scrH);
            if (CSDataModel.scrollingUp)
                screenRect.setRect(screenRect.getX(), screenRect.getY()-scrollSpeed, scrW, scrH);
            if (CSDataModel.scrollingDown)
                screenRect.setRect(screenRect.getX(), screenRect.getY()+scrollSpeed, scrW, scrH);
        }
        else
        {
            final CSCharCornShark p = data.getPlayer(controller);
            final double x = (p.getX()+(p.getScaledBounds().getWidth()/2)) - (screenRect.getWidth()/2);
            final double y = (p.getY()+(p.getScaledBounds().getHeight()/2)) - (screenRect.getHeight()/2);
            screenRect.setRect(x, y, scrW, scrH);
        }

        getHud().advance();

        clipBounds();

        super.advance();

        if (data.getPlayer(controller).getLevel() == 16 && status != GameStatus.WON)
        {
            status = GameStatus.WON;

            final CSGameOverScreen screen = new CSGameOverScreen();
            screen.setGameWon(true);
            this.setNextScreen(screen);
            gameOver = true;
            data.timer.pause();

        }
        else if (data.getPlayer(controller).getLevel() <= 0 &&
                        data.getPlayer(controller).getHealth() <= 0 && status != GameStatus.LOST)
        {
            status = GameStatus.LOST;

            final CSGameOverScreen screen = new CSGameOverScreen();
            screen.setGameWon(false);
            this.setNextScreen(screen);
            data.timer.pause();
            gameOver = true;
        }


        if (gameOver)
        {
            endGameCountdown--;

            alpha = 1 - (endGameCountdown / 80.0f);

            if (endGameCountdown < 0)
            {
                this.setDisposable(true);
                this.setFinished(true);
            }
        }
    }


    private void clipBounds()
    {
        double x = screenRect.getX(), y = screenRect.getY();

        if (screenRect.getX() < 0)
            x = 0;
        if (screenRect.getY() < 0)
            y = 0;
        if (screenRect.getX() + scrW > data.getWorldWidth())
            x = data.getWorldWidth() - scrW;
        if (screenRect.getY() + screenRect.getHeight() > data.getWorldHeight())
            y = data.getWorldHeight() - screenRect.getHeight();

        screenRect.setRect(x, y, screenRect.getWidth(), screenRect.getHeight());
    }

    @Override
    public void render(final Graphics2D g2d)
    {
        final int logoX = 800 - data.gameLogo.getWidth(null);
        final int logoY = 600 - data.gameLogo.getHeight(null);
        final int x0 = MHDisplayModeChooser.DISPLAY_X;
        final int y0 = MHDisplayModeChooser.DISPLAY_Y;


        if (!data.doneLoading())
        {
            final Font labelFont = new Font("TimesRoman", Font.BOLD, 64);

            //g2d.drawImage(data.igBackground.getImage(0, 0), 0, 0, null);
            g2d.setColor(Color.BLACK);
            g2d.fillRect(x0, y0, MHDisplayModeChooser.getScreenSize().width, MHDisplayModeChooser.getScreenSize().height);
            g2d.drawImage(data.gameLogo, logoX+x0, logoY+y0, null);

            g2d.setFont(labelFont);
            centerText(g2d, "Loading...", 300+y0, Color.CYAN, false, 10);

            //final double pct = (data.countLoadedImages() / (double)data.getImageCount());
            //centerText(g2d, (int)pct+"%", 400, Color.YELLOW, true, 10);

            data.renderAllFrames(g2d);

            data.timer.start();

            return;
        }

        controller.render(g2d);

        if (CSDataModel.debug)
            debug.render(g2d);

        if (gameOver)
        {
            g2d.setColor(new Color(0, 0, 0, (alpha>1?1:alpha)));
            g2d.fillRect(x0, y0, 800, 600);
        }

        getHud().render(g2d);
        g2d.drawImage(data.gameLogo, logoX+x0, logoY+y0, null);

        super.render(g2d);
    }


    public void actionPerformed(final ActionEvent e)
    {
    }


    @Override
    public void keyPressed(final KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_ESCAPE:
                data.playSound(data.idButtonClick);
                this.setNextScreen(new CSPauseScreen());
                this.setFinished(true);
                break;
            case CSKeyCommands.DEBUG_MODE:
                CSDataModel.debug = !CSDataModel.debug;
                break;
            case CSKeyCommands.SWIM_RIGHT:
                if (CSDataModel.debug)
                    CSDataModel.scrollingRight = true;
                else
                    data.getPlayer(controller).swimmingRight=true;
                break;
            case CSKeyCommands.SWIM_LEFT:
                if (CSDataModel.debug)
                    CSDataModel.scrollingLeft = true;
                else
                	data.getPlayer(controller).swimmingLeft=true;
                break;
            case CSKeyCommands.SWIM_UP:
                if (CSDataModel.debug)
                    CSDataModel.scrollingUp = true;
                else
                	data.getPlayer(controller).swimmingUp = true;
                break;
            case CSKeyCommands.SWIM_DOWN:
                if (CSDataModel.debug)
                    CSDataModel.scrollingDown = true;
                else
                	data.getPlayer(controller).swimmingDown = true;
                break;
            case KeyEvent.VK_B:
            	data.getPlayer(controller).bite();
                break;
            case KeyEvent.VK_LESS:
            case KeyEvent.VK_COMMA:
            	data.getPlayer(controller).setLevel(data.getPlayer(controller).getLevel()-1);
                break;
            case KeyEvent.VK_GREATER:
            case KeyEvent.VK_PERIOD:
            	data.getPlayer(controller).setLevel(data.getPlayer(controller).getLevel()+1);
                break;
        }
    } // keyPressed()

    @Override
    public void keyReleased(final KeyEvent e)
    {
        switch (e.getKeyCode())
        {
        case CSKeyCommands.SWIM_RIGHT:
            if (CSDataModel.debug)
                CSDataModel.scrollingRight = false;
            else
            	data.getPlayer(controller).swimmingRight=false;
            break;
        case CSKeyCommands.SWIM_LEFT:
            if (CSDataModel.debug)
                CSDataModel.scrollingLeft = false;
            else
            	data.getPlayer(controller).swimmingLeft=false;
            break;
        case CSKeyCommands.SWIM_UP:
            if (CSDataModel.debug)
                CSDataModel.scrollingUp = false;
            else
            	data.getPlayer(controller).swimmingUp = false;
            break;
        case CSKeyCommands.SWIM_DOWN:
            if (CSDataModel.debug)
                CSDataModel.scrollingDown = false;
            else
            	data.getPlayer(controller).swimmingDown = false;
            break;
        }
    }

    /**
     * @return the hud
     */
    public CSGameHUD getHud()
    {
        return hud;
    }

}

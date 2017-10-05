import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import mhframework.MHActor;
import mhframework.MHDisplayModeChooser;
import mhframework.MHGame;
import mhframework.MHScreen;
import mhframework.gui.MHGUIButton;
import mhframework.media.MHResourceManager;

public class CSMainMenuScreen extends MHScreen
{
    private final CSDataModel data = CSDataModel.getInstance();
    private final MHGUIButton btnExit, btnOptions, btnPlay;
    private boolean showAttractLoop = false;
    private final CSAttractLoop attractLoop;
    private final long timeToIdle = 10000L;
    private long lastActionTime = System.currentTimeMillis();

    private final MHActor waves;

    private int bgX = 0;

    public CSMainMenuScreen()
    {
        this.setFinished(false);
        this.setDisposable(false);

        attractLoop = new CSAttractLoop();

        waves = new CSWaveAnimation();

        btnExit = new MHGUIButton(data.imgExit0, data.imgExit1, data.imgExit2);
        btnExit.setText(" ");
        btnExit.setType(MHGUIButton.TYPE_IMAGE_BUTTON);
        btnExit.setPosition(272+256, 400);
        btnExit.addActionListener(this);

        btnOptions = new MHGUIButton(data.imgOptions0, data.imgOptions1, data.imgOptions2);
        btnOptions.setText(" ");
        btnOptions.setType(MHGUIButton.TYPE_IMAGE_BUTTON);
        btnOptions.setPosition(272, 280);
        btnOptions.addActionListener(this);

        btnPlay = new MHGUIButton(data.imgPlay0, data.imgPlay1, data.imgPlay2);
        btnPlay.setText(" ");
        btnPlay.setType(MHGUIButton.TYPE_IMAGE_BUTTON);
        btnPlay.setPosition(272-256, 170);
        btnPlay.addActionListener(this);

        this.add(btnPlay);
        this.add(btnOptions);
        this.add(btnExit);
    }

    @Override
    public void load()
    {
        if (CornShark.DEBUG)
            System.out.println("CSMainMenuScreen.load()");

        data.setProgramOver(false);
        this.setFinished(false);

        data.playMusic(data.uiMusic);

        lastActionTime = System.currentTimeMillis();
    }

    @Override
    public void unload()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void advance()
    {
        attractLoop.advance();

        if (System.currentTimeMillis() - lastActionTime >= timeToIdle)
            showAttractLoop = true;

        waves.advance();
        bgX-=2;

        if (bgX <= -800)
            bgX += 800;
    }


    @Override
    public void render(final Graphics2D g2d)
    {
        if (showAttractLoop)
        {
            attractLoop.render(g2d);
            return;
        }

        final Image bg = data.igBackground.getImage(0, 0);
        final Image oceanFloor = data.igBottom.getImage(0, 0);
        final Image plant = data.igPlant.getImage(0, 0);
        final int x0 = MHDisplayModeChooser.DISPLAY_X;
        final int y0 = MHDisplayModeChooser.DISPLAY_Y;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(bg, bgX+x0, y0, null);
        g2d.drawImage(bg, bgX+800+x0, y0, null);
        g2d.drawImage(oceanFloor, x0,
                        MHDisplayModeChooser.getScreenSize().height
                        - oceanFloor.getHeight(null)+y0, null);
        g2d.drawImage(plant, x0+80, MHDisplayModeChooser.getScreenSize().height - plant.getHeight(null) - 25+y0, null);
        waves.render(g2d);

        g2d.drawImage(data.lblMainMenu, MHDisplayModeChooser.getScreenSize().width - data.lblMainMenu.getWidth(null) - 40+x0, 160+y0, null);
        g2d.drawImage(data.gameLogo, MHDisplayModeChooser.getScreenSize().width - data.gameLogo.getWidth(null) - 40+x0, 100+y0, null);

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 18));
        final FontMetrics fm = g2d.getFontMetrics();

        g2d.drawString(CSDataModel.VERSION, 790-fm.stringWidth(CSDataModel.VERSION)+x0, 590+y0);

        super.render(g2d);  // Superclass call to display GUI components.
    }


    private MHScreen getGameScreen()
    {
        final MHScreen s = new CSGameScreen();
        return s;
    }


    public void actionPerformed(final ActionEvent e)
    {
        data.playSound(data.idButtonClick);

        if (e.getSource() == btnPlay)
        {
            this.setNextScreen(getGameScreen());
            this.setFinished(true);
            this.setDisposable(false);
        }
        else if (e.getSource() == btnExit)
        {
            this.setFinished(true);
            MHGame.setProgramOver(true);
        }
        else if (e.getSource() == btnOptions)
        {
            this.setFinished(true);
            this.setDisposable(false);
            this.setNextScreen(new CSOptionsScreen());
        }
    }

    @Override
    public void keyPressed(final KeyEvent e)
    {
        lastActionTime = System.currentTimeMillis();
        showAttractLoop = false;

        super.keyPressed(e);
    }


    @Override
    public void mousePressed(final MouseEvent e)
    {
        lastActionTime = System.currentTimeMillis();
        showAttractLoop = false;

        super.mousePressed(e);
    }


    @Override
    public void mouseMoved(final MouseEvent e)
    {
        lastActionTime = System.currentTimeMillis();
        showAttractLoop = false;

        super.mouseMoved(e);
    }
}


class CSAttractLoop
{
    private final ArrayList<Image> images;
    private int currentImage = 0;
    private final long msPerImage = 10000;
    private long lastChangeTime = System.currentTimeMillis();

    public CSAttractLoop()
    {
        images = new ArrayList<Image>();

        images.add(MHResourceManager.loadImage("images/CornSharkURL.jpg"));
        images.add(MHResourceManager.loadImage("images/CornSharkCredits.jpg"));
        images.add(MHResourceManager.loadImage("images/WinnersDontUseDrugs.jpg"));
        images.add(MHResourceManager.loadImage("images/CornSharkURL.jpg"));
        images.add(MHResourceManager.loadImage("images/PoweredByMHFramework.jpg"));
        images.add(MHResourceManager.loadImage("images/RecycleItDontTrashIt.jpg"));
    }


    public void advance()
    {
        if (System.currentTimeMillis() - lastChangeTime >= msPerImage)
        {
            currentImage = (currentImage + 1) % images.size();
            lastChangeTime = System.currentTimeMillis();
        }
    }


    public void render(final Graphics2D g)
    {
        g.drawImage(images.get(currentImage), MHDisplayModeChooser.DISPLAY_X, MHDisplayModeChooser.DISPLAY_Y, MHDisplayModeChooser.getScreenSize().width, MHDisplayModeChooser.getScreenSize().height, null);
    }

}
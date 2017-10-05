import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import mhframework.MHDisplayModeChooser;
import mhframework.MHScreen;
import mhframework.gui.MHGUIButton;

public class CSPauseScreen extends MHScreen
{
    private final CSDataModel data = CSDataModel.getInstance();

    private static final int STATE_PAUSING = 10;
    private static final int STATE_PAUSED = 0;
    private static final int STATE_RESUMING = 0-(STATE_PAUSING*2);
    private static final int MAX_FADE_ALPHA = 127;

    private final MHGUIButton btnSound, btnMusic;
    private final MHGUIButton btnQuit;
    private final MHGUIButton btnReturn;

    private String strSound, strMusic;
    private final Color onColor, offColor, shadowColor;
    private Color soundColor, musicColor;
    private final Font labelFont = new Font("TimesRoman", Font.BOLD, 64);

    private int labelX = 50;
    private int labelY = 300;
    private double deltaX = 1 + Math.random() * 2;
    private double deltaY = 1 + Math.random() * 2;
    private int state=0, alpha=0;

    public CSPauseScreen()
    {
        onColor = Color.GREEN;
        offColor = Color.RED;
        shadowColor = new Color(0, 0, 32, 100);
        state = STATE_PAUSING;

        setLabels();

        final int h = 114;
        final int sp = 8;
        int y = 60;
        final int x = (MHDisplayModeChooser.getScreenSize().width/2)-32;

        btnSound = new MHGUIButton(data.imgSound0, data.imgSound1, data.imgSound2);
        btnSound.setType(MHGUIButton.TYPE_IMAGE_BUTTON);
        btnSound.setPosition(x, y);
        btnSound.addActionListener(this);
        y += h + sp;
        btnMusic = new MHGUIButton(data.imgMusic0, data.imgMusic1, data.imgMusic2);
        btnMusic.setType(MHGUIButton.TYPE_IMAGE_BUTTON);
        btnMusic.setPosition(x, y);
        btnMusic.addActionListener(this);
        y += h + sp;
        btnQuit = new MHGUIButton(data.imgQuit0, data.imgQuit1, data.imgQuit2);
        btnQuit.setType(MHGUIButton.TYPE_IMAGE_BUTTON);
        btnQuit.setPosition(x, y);
        btnQuit.addActionListener(this);
        y += h + sp;
        btnReturn = new MHGUIButton(data.imgReturn0, data.imgReturn1, data.imgReturn2);
        btnReturn.setType(MHGUIButton.TYPE_IMAGE_BUTTON);
        btnReturn.setPosition(x, y);
        btnReturn.addActionListener(this);

        this.add(btnReturn);
        this.add(btnSound);
        this.add(btnMusic);
        this.add(btnQuit);
    }

    private void setLabels()
    {

        if (CSOptionsData.getInstance().isSoundOn())
        {
            strSound = "On";
            soundColor = onColor;
        }
        else
        {
            strSound = "Off";
            soundColor = offColor;
        }

        if ( CSOptionsData.getInstance().isMusicOn() )
        {
            strMusic =  "On";
            musicColor = onColor;
        }
        else
        {
            strMusic = "Off";
            musicColor = offColor;
        }

    }

    /* (non-Javadoc)
     * @see mhframework.MHScreen#advance()
     */
    @Override
    public void advance()
    {
        alpha += state;

        if (state == STATE_PAUSING && alpha >= MAX_FADE_ALPHA)
        {
            state = STATE_PAUSED;
            alpha = MAX_FADE_ALPHA;
        }

        if (state == STATE_RESUMING && alpha <= 0)
        {
            this.setDisposable(true);
            this.setNextScreen(null);
            this.setFinished(true);
        }

        if (labelX < 10)
        {
            labelX = 10;
            deltaX *= -1;
        }
        if (labelX + data.lblGamePaused.getWidth(null) > this.btnReturn.getX())
        {
            labelX = this.btnReturn.getX() - data.lblGamePaused.getWidth(null);
            deltaX *= -1;
        }

        if (labelY < 60)
        {
            labelY = 60;
            deltaY *= -1;
        }
        if (labelY + data.lblGamePaused.getHeight(null) > 540)
        {
            labelY = 540 - data.lblGamePaused.getHeight(null);
            deltaY *= -1;
        }

        labelX += deltaX;
        labelY += deltaY;
    }

    /* (non-Javadoc)
     * @see mhframework.MHScreen#keyReleased(java.awt.event.KeyEvent)
     */
    @Override
    public void keyPressed(final KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_ESCAPE:
                this.setDisposable(true);
                this.setNextScreen(null);
                this.setFinished(true);
                break;
        }
    }

    /* (non-Javadoc)
     * @see mhframework.MHScreen#load()
     */
    @Override
    public void load()
    {
        this.setFinished(false);
        data.timer.pause();
    }

    /* (non-Javadoc)
     * @see mhframework.MHScreen#render(java.awt.Graphics)
     */
    @Override
    public void render(final Graphics2D g)
    {
        final Graphics2D g2d = g;
        final int logoX = 800 - data.gameLogo.getWidth(null);
        final int logoY = 600 - data.gameLogo.getHeight(null);
        final int x0 = MHDisplayModeChooser.DISPLAY_X;
        final int y0 = MHDisplayModeChooser.DISPLAY_Y;

        getPreviousScreen().render(g2d);

        // Fade out and back in when pausing/resuming.
        g2d.setColor(new Color(255, 255, 255, (alpha<0?0:alpha)));
        g2d.fillRect(x0, y0, 800, 600);

        g2d.drawImage(data.gameLogo, logoX+x0, logoY+y0, null);
        g2d.drawImage(data.lblGamePaused, labelX+x0, labelY+y0, null);

        g2d.setFont(this.labelFont);

        // Shadows
        g2d.setColor(shadowColor);
        final int dist = 6;
        g2d.drawString(strSound, (int)(btnSound.getX() + btnSound.getBounds().getWidth()+20)+dist, btnSound.getY()+75+dist);
        g2d.drawString(strMusic, (int)(btnMusic.getX() + btnMusic.getBounds().getWidth()+20)+dist, btnMusic.getY()+75+dist);

        // Labels
        g2d.setColor(soundColor);
        g2d.drawString(strSound, (int)(btnSound.getX() + btnSound.getBounds().getWidth()+20), btnSound.getY()+75);
        g2d.setColor(musicColor);
        g2d.drawString(strMusic, (int)(btnMusic.getX() + btnMusic.getBounds().getWidth()+20), btnMusic.getY()+75);

        //g2d.drawImage(data.gameLogo, logoX, logoY, null);

        super.render(g2d);
    }

    /* (non-Javadoc)
     * @see mhframework.MHScreen#unload()
     */
    @Override
    public void unload()
    {
        data.timer.resume();
    }

    public void actionPerformed(final ActionEvent e)
    {
        if (state == STATE_RESUMING || state == STATE_PAUSING) return;

        data.playSound(data.idButtonClick);

        if (e.getSource() == btnReturn)
        {
            state = STATE_RESUMING;
        }
        else if (e.getSource() == btnSound)
        {
            CSOptionsData.getInstance().setSoundOn(!CSOptionsData.getInstance().isSoundOn());
        }
        else if (e.getSource() == btnMusic)
        {
            CSOptionsData.getInstance().setMusicOn(!CSOptionsData.getInstance().isMusicOn());
        }
        else if (e.getSource() == btnQuit)
        {
            ((CSGameScreen) getPreviousScreen()).gameOver = true;
            ((CSGameScreen) getPreviousScreen()).endGameCountdown = 0;
            this.setDisposable(true);
            this.setNextScreen(null);
            this.setFinished(true);
        }
        setLabels();

    }


}

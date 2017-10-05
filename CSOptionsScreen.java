import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import mhframework.MHDisplayModeChooser;
import mhframework.MHScreen;
import mhframework.gui.MHGUIButton;

public class CSOptionsScreen extends MHScreen
{
    private final static int BUTTON_HEIGHT = 114;

    private final CSDataModel data = CSDataModel.getInstance();
    private final MHGUIButton btnSound, btnMusic;
    private final MHGUIButton btnHealthBars;
    private final MHGUIButton btnDifficulty;
    private final MHGUIButton btnReturn;
    private final MHGUIButton btnPlay;

    private final CSWaveAnimation waves;
    private int bgX = 0;

    private String strSound, strMusic, strHealthBars, strDifficulty;
    private final Color onColor, offColor, skillColor, shadowColor;
    private Color soundColor, musicColor, healthBarOptionColor;
    private final Font labelFont = new Font("TimesRoman", Font.BOLD, 64);

    public CSOptionsScreen()
    {
        onColor = Color.GREEN;
        offColor = Color.RED;
        skillColor = Color.CYAN;
        shadowColor = new Color(0, 0, 32);
        setLabels();

        waves = new CSWaveAnimation();

        int lastY = 90;

        btnPlay = new MHGUIButton(data.imgPlay0, data.imgPlay1, data.imgPlay2);
        btnPlay.setType(MHGUIButton.TYPE_IMAGE_BUTTON);
        btnPlay.setPosition(530, lastY + (BUTTON_HEIGHT*2));
        btnPlay.addActionListener(this);

        btnReturn = new MHGUIButton(data.imgReturn0, data.imgReturn1, data.imgReturn2);
        btnReturn.setType(MHGUIButton.TYPE_IMAGE_BUTTON);
        btnReturn.setPosition(btnPlay.getX(), btnPlay.getY()+BUTTON_HEIGHT);
        btnReturn.addActionListener(this);

        btnSound = new MHGUIButton(data.imgSound0, data.imgSound1, data.imgSound2);
        btnSound.setType(MHGUIButton.TYPE_IMAGE_BUTTON);
        btnSound.setPosition(20, lastY);
        btnSound.addActionListener(this);

        lastY += BUTTON_HEIGHT;

        btnMusic = new MHGUIButton(data.imgMusic0, data.imgMusic1, data.imgMusic2);
        btnMusic.setType(MHGUIButton.TYPE_IMAGE_BUTTON);
        btnMusic.setPosition(btnSound.getX(), lastY);
        btnMusic.addActionListener(this);

        lastY += BUTTON_HEIGHT;

        btnHealthBars = new MHGUIButton(data.imgHealthBars0, data.imgHealthBars1, data.imgHealthBars2);
        btnHealthBars.setType(MHGUIButton.TYPE_IMAGE_BUTTON);
        btnHealthBars.setPosition(btnMusic.getX(), lastY);
        btnHealthBars.addActionListener(this);

        lastY += BUTTON_HEIGHT;

        btnDifficulty = new MHGUIButton(data.imgDifficulty0, data.imgDifficulty1, data.imgDifficulty2);
        btnDifficulty.setType(MHGUIButton.TYPE_IMAGE_BUTTON);
        btnDifficulty.setPosition(btnHealthBars.getX(), lastY);
        btnDifficulty.addActionListener(this);

        this.add(btnPlay);
        this.add(btnReturn);
        this.add(btnSound);
        this.add(btnMusic);
        this.add(btnHealthBars);
        this.add(btnDifficulty);
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

        if (CSOptionsData.getInstance().getHealthBarOption() == CSOptionsData.HEALTH_BAR_AUTO)
        {
            strHealthBars = "Auto";
            healthBarOptionColor = skillColor;
        }
        else if (CSOptionsData.getInstance().getHealthBarOption() == CSOptionsData.HEALTH_BAR_ON)
        {
            strHealthBars = "On";
            healthBarOptionColor = onColor;
        }
        else if (CSOptionsData.getInstance().getHealthBarOption() == CSOptionsData.HEALTH_BAR_OFF)
        {
            strHealthBars = "Off";
            healthBarOptionColor = offColor;
        }

        if (CSOptionsData.getInstance().getSkillLevel() == CSSkillLevel.EASY)
            strDifficulty = "Easy";
        else if (CSOptionsData.getInstance().getSkillLevel() == CSSkillLevel.NORMAL)
            strDifficulty = "Normal";
        else
            strDifficulty = "Hard";
    }

    @Override
    public void load()
    {
        if (CornShark.DEBUG)
            System.out.println("CSOptionsScreen.load()");

        this.setFinished(false);
    }


    @Override
    public void unload()
    {
        if (CornShark.DEBUG)
            System.out.println("CSOptionsScreen.unload()");
    }


    @Override
    public void render(final Graphics2D g2d)
    {
        final Image bg = data.igBackground.getImage(0, 0);
        final Image oceanFloor = data.igBottom.getImage(0, 0);
        final int x0 = MHDisplayModeChooser.DISPLAY_X;
        final int y0 = MHDisplayModeChooser.DISPLAY_Y;


        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(bg, bgX+x0, y0, null);
        g2d.drawImage(bg, bgX+800+x0, y0, null);
        g2d.drawImage(oceanFloor, x0,
                        MHDisplayModeChooser.getScreenSize().height
                        - oceanFloor.getHeight(null)+y0, null);
        waves.render(g2d);

        g2d.drawImage(data.lblOptionsMenu, MHDisplayModeChooser.getScreenSize().width - data.lblMainMenu.getWidth(null) - 40+x0, 160+y0, null);
        g2d.drawImage(data.gameLogo, MHDisplayModeChooser.getScreenSize().width - data.gameLogo.getWidth(null) - 40+x0, 100+y0, null);

        g2d.setFont(this.labelFont);

        // Shadows
        g2d.setColor(shadowColor);
        final int dist = 6;
        g2d.drawString(strSound, (int)(btnSound.getX() + btnSound.getBounds().getWidth()+20)+dist+x0, btnSound.getY()+75+dist+y0);
        g2d.drawString(strMusic, (int)(btnMusic.getX() + btnMusic.getBounds().getWidth()+20)+dist+x0, btnMusic.getY()+75+dist+y0);
        g2d.drawString(strHealthBars, (int)(btnHealthBars.getX() +btnHealthBars.getBounds().getWidth()+20)+dist+x0, btnHealthBars.getY()+75+dist+y0);
        g2d.drawString(strDifficulty, (int)(btnDifficulty.getX() + btnDifficulty.getBounds().getWidth()+20)+dist+x0, btnDifficulty.getY()+75+dist+y0);

        // Labels
        g2d.setColor(soundColor);
        g2d.drawString(strSound, (int)(btnSound.getX() + btnSound.getBounds().getWidth()+20)+x0, btnSound.getY()+75+y0);
        g2d.setColor(musicColor);
        g2d.drawString(strMusic, (int)(btnMusic.getX() + btnMusic.getBounds().getWidth()+20)+x0, btnMusic.getY()+75+y0);
        g2d.setColor(healthBarOptionColor);
        g2d.drawString(strHealthBars, (int)(btnHealthBars.getX() + btnHealthBars.getBounds().getWidth()+20)+x0, btnHealthBars.getY()+75+y0);
        g2d.setColor(skillColor);
        g2d.drawString(strDifficulty, (int)(btnDifficulty.getX() + btnDifficulty.getBounds().getWidth()+20)+x0, btnDifficulty.getY()+75+y0);

        super.render(g2d);
    }


    @Override
    public void advance()
    {
        waves.advance();
        bgX-=2;

        if (bgX <= -800)
            bgX += 800;
    }


    @Override
    public void keyPressed(final KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            this.setDisposable(true);
            this.setNextScreen(null);
            this.setFinished(true);
        }
    }

    @Override
    public void actionPerformed(final ActionEvent e)
    {
        data.playSound(data.idButtonClick);

        if (e.getSource() == btnReturn)
        {
            this.setDisposable(true);
            this.setNextScreen(null);
            this.setFinished(true);
        }
        else if (e.getSource() == btnPlay)
        {
            this.setDisposable(true);
            this.setNextScreen(new CSGameScreen());
            this.setFinished(true);
        }
        else if (e.getSource() == btnSound)
        {
            CSOptionsData.getInstance().setSoundOn(!CSOptionsData.getInstance().isSoundOn());
        }
        else if (e.getSource() == btnMusic)
        {
            CSOptionsData.getInstance().setMusicOn(!CSOptionsData.getInstance().isMusicOn());
            if (!CSOptionsData.getInstance().isMusicOn())
                data.stopMusic();
            else
                data.playMusic(data.uiMusic);
        }
        else if (e.getSource() == btnHealthBars)
        {
            if (CSOptionsData.getInstance().getHealthBarOption() == CSOptionsData.HEALTH_BAR_AUTO)
                CSOptionsData.getInstance().setHealthBarOption(CSOptionsData.HEALTH_BAR_ON);
            else if (CSOptionsData.getInstance().getHealthBarOption() == CSOptionsData.HEALTH_BAR_ON)
                CSOptionsData.getInstance().setHealthBarOption(CSOptionsData.HEALTH_BAR_OFF);
            else if (CSOptionsData.getInstance().getHealthBarOption() == CSOptionsData.HEALTH_BAR_OFF)
                CSOptionsData.getInstance().setHealthBarOption(CSOptionsData.HEALTH_BAR_AUTO);
        }
        else if (e.getSource() == btnDifficulty)
        {
            if (CSOptionsData.getInstance().getSkillLevel() == CSSkillLevel.EASY)
                CSOptionsData.getInstance().setSkillLevel(CSSkillLevel.NORMAL);
            else if (CSOptionsData.getInstance().getSkillLevel() == CSSkillLevel.NORMAL)
                CSOptionsData.getInstance().setSkillLevel(CSSkillLevel.HARD);
            else if (CSOptionsData.getInstance().getSkillLevel() == CSSkillLevel.HARD)
                CSOptionsData.getInstance().setSkillLevel(CSSkillLevel.EASY);
        }
        setLabels();

    }

}

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import mhframework.MHDisplayModeChooser;
import mhframework.MHScreen;

public class CSHighScoresScreen extends MHScreen
{

    private final CSDataModel data = CSDataModel.getInstance();
    private final CSWaveAnimation waves = new CSWaveAnimation();

    // High score list variables
    private final Color topNumberColor = Color.YELLOW, //Color.WHITE,
                  topNameColor   = Color.GREEN,
                  topScoreColor  = Color.ORANGE, //Color.YELLOW,
                  topSkillColor  = Color.CYAN;
    private final Font textFont = new Font("SansSerif", Font.PLAIN, 34);
    private final Font numberFont = new Font("MonoSpaced", Font.BOLD, 34);
    private final int shadowDistance = 2;
    private final int topY = 210;
    private final int numberX = 40;
    private final int nameX = 110;
    private final int scoreX = 460;
    private final int skillX = 650;
    private final int lineHeightMin = 40;
    private int lineHeight = 0;
    private final double lineHeightDelta = 0.5;
    private double lineHeightFloat = 0.0;
    //private final int alpha = 0;
    private final int alphaValues[] = new int[10]; // Alpha values for each high scores list entry.
    private int minFadeIndex = 9; // Used to fade in one high scores entry at a time, starting at the bottom.

    private int bgX = 0;

    private int timeOut;


    @Override
    public void load()
    {
        data.playMusic(data.uiMusic);

        timeOut = 1000;
    }


    @Override
    public void unload()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(final Graphics2D g2d)
    {
            final Image bg = data.igBackground.getImage(0, 0);
            final Image oceanFloor = data.igBottom.getImage(0, 0);
            //final Image plant = data.igPlant.getImage(0, 0);
            final int x0 = MHDisplayModeChooser.DISPLAY_X;
            final int y0 = MHDisplayModeChooser.DISPLAY_Y;


            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.drawImage(bg, bgX+x0, 0, null);
            g2d.drawImage(bg, bgX+800+x0, 0, null);
            g2d.drawImage(oceanFloor, x0,
                            MHDisplayModeChooser.getScreenSize().height
                            - oceanFloor.getHeight(null)+y0, null);
            //g2d.drawImage(plant, 80, MHDisplayModeChooser.getScreenSize().height - plant.getHeight(null) - 25, null);
            waves.render(g2d);

            //final int logoX = 800 - data.gameLogo.getWidth(null);
            //final int logoY = 600 - data.gameLogo.getHeight(null);
            //g2d.drawImage(data.gameLogo, logoX, logoY, null);
            g2d.drawImage(data.gameLogo, MHDisplayModeChooser.getScreenSize().width - data.gameLogo.getWidth(null) - 40 + x0, 100 + y0, null);

            g2d.drawImage(data.lblFastestWins, 30+x0, 80+y0, null);

            // Color c = new Color();
            // Display high scores.
            for (int i = 0; i < 10; i++)
            {
                final CSHighScoreRecord rec = data.highScoresList.getRecord(i);

                final Color shadowColor = new Color(0, 0, 0, (int)(alphaValues[i]*0.75));

                // Render name and skill level strings.
                g2d.setFont(textFont);

                g2d.setColor(shadowColor);
                g2d.drawString(rec.name, nameX+shadowDistance+x0, topY + (i*lineHeight)+shadowDistance + y0);
                g2d.setColor(new Color(topNameColor.getRed(), topNameColor.getGreen(), topNameColor.getBlue(), alphaValues[i]));
                g2d.drawString(rec.name, nameX+x0, topY + (i*lineHeight)+y0);

                g2d.setColor(shadowColor);
                g2d.drawString(rec.skillLevel, skillX+shadowDistance+x0, topY + (i*lineHeight)+shadowDistance+y0);
                g2d.setColor(new Color(topSkillColor.getRed(), topSkillColor.getGreen(), topSkillColor.getBlue(), alphaValues[i]));
                g2d.drawString(rec.skillLevel, skillX+x0, topY + (i*lineHeight)+y0);

                // Render index and time numbers.
                g2d.setFont(numberFont);

                g2d.setColor(shadowColor);
                final String numberString = ((i+1)<10 ? " " : "") + (i+1) + ".";
                g2d.drawString(numberString, numberX+shadowDistance+x0, topY + (i*lineHeight)+shadowDistance+y0);
                g2d.setColor(new Color(topNumberColor.getRed(), topNumberColor.getGreen(), topNumberColor.getBlue(), alphaValues[i]));
                g2d.drawString(numberString, numberX+x0, topY + (i*lineHeight)+y0);

                g2d.setColor(shadowColor);
                g2d.drawString(rec.getTimeString(), scoreX+shadowDistance+x0, topY + (i*lineHeight)+shadowDistance+y0);
                g2d.setColor(new Color(topScoreColor.getRed(), topScoreColor.getGreen(), topScoreColor.getBlue(), alphaValues[i]));
                g2d.drawString(rec.getTimeString(), scoreX+x0, topY + (i*lineHeight)+y0);
            }

            super.render(g2d);
    }


    @Override
    public void advance()
    {
        waves.advance();
        bgX-=2;

        if (bgX <= -800)
            bgX += 800;

        final int alphaDelta = 6;

        for (int i = alphaValues.length-1; i >= minFadeIndex; i--)
        {
            if (alphaValues[i] + alphaDelta > 255)
                alphaValues[i] = 255;
            else
                alphaValues[i] += alphaDelta;
        }

        if (alphaValues[minFadeIndex] > 40 && minFadeIndex > 0)
            minFadeIndex--;

        lineHeightFloat += lineHeightDelta;
        lineHeight = (int)lineHeightFloat;
        if (lineHeight > lineHeightMin)
            lineHeight = lineHeightMin;

        timeOut--;

        if (timeOut < 0)
        {
            this.setDisposable(true);
            this.setFinished(true);
        }
    }

    public void actionPerformed(final ActionEvent arg0)
    {
    }


    @Override
    public void mousePressed(final MouseEvent e)
    {
        this.setDisposable(true);
        this.setFinished(true);
    }


    @Override
    public void keyPressed(final KeyEvent e)
    {
        this.setDisposable(true);
        this.setFinished(true);
    }
}

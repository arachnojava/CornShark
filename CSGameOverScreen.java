import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import mhframework.MHDisplayModeChooser;
import mhframework.MHScreen;
import mhframework.gui.MHGUIButton;


public class CSGameOverScreen extends MHScreen
{
    private final Font font = new Font("SansSerif", Font.BOLD+Font.ITALIC, 42);

    private boolean gameWon = true;
    private final CSDataModel data = CSDataModel.getInstance();
    private MHGUIButton quitButton;
    private int imageY = -600;
    private int timeY = 640;
    int logoX = 800 - data.gameLogo.getWidth(null);
    int logoY = 600 - data.gameLogo.getHeight(null);
    private float alpha = 1.0f;

    @Override
    public void load()
    {
        quitButton = new MHGUIButton(data.imgQuit0, data.imgQuit1, data.imgQuit2);
        quitButton.setType(MHGUIButton.TYPE_IMAGE_BUTTON);
        quitButton.setText("");
        quitButton.setPosition(-150, 450);
        quitButton.addActionListener(this);
        add(quitButton);

        if (gameWon)
            data.gameOverImage = data.loadImage(data.IMAGE_DIR + "VictoryScreen.png");
        else
            data.gameOverImage = data.loadImage(data.IMAGE_DIR + "DefeatScreen.png");
    }


    @Override
    public void unload()
    {
        // TODO Auto-generated method stub

    }


    /* (non-Javadoc)
     * @see mhframework.MHScreen#render(java.awt.Graphics)
     */
    @Override
    public void render(final Graphics2D g)
    {
        final int bX = 780 - 256;
        final int timeYDest = (int)(450+quitButton.getBounds().getHeight()/2+(font.getSize()/2));
        final int x0 = MHDisplayModeChooser.DISPLAY_X;
        final int y0 = MHDisplayModeChooser.DISPLAY_Y;

        if (logoX <= 20)
            logoX = 20;
        else
            logoX -= (int)(logoX * 0.06);

        if (logoY <= 20)
            logoY = 20;
        else
            logoY -= (int)(logoY * 0.06);

        //this.getPreviousScreen().render(g);
        g.drawImage(data.igBackground.getImage(0, 0), x0, y0, null);

        if (alpha > 0f)
        {
            g.setColor(new Color(0f, 0f, 0f, alpha));
            g.fillRect(x0, y0, 800, 600);
            alpha -= 0.025f;
        }

        if (imageY+22 < -30)
        {
            imageY += 22;
            g.drawImage(data.gameOverImage, x0, imageY+y0, 800, 600, null);
        }
        else
            g.drawImage(data.gameOverImage, x0, -30+y0, 800, 600, null);

        g.drawImage(data.gameLogo, logoX+x0, logoY+y0, null);

        if (quitButton.getX() < bX)
            quitButton.setPosition(quitButton.getX()+16, 450);
        else
            quitButton.setPosition(bX, 450);

        if (timeY-2 > timeYDest)
            timeY -= 2;
        else
            timeY = timeYDest;

        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString("Game Time:  " + data.timer.toString(), 20+4+x0, timeY+4+y0);
        g.setColor(Color.WHITE);
        g.drawString("Game Time:  " + data.timer.toString(), 20+x0, timeY+y0);

        super.render(g);

    }


    public void actionPerformed(final ActionEvent arg0)
    {
        data.playSound(data.idButtonClick);

        // if player won
        if (gameWon)
        {
            // if player has high score (fast victory time)
            if (data.timer.getGameTimeTotal() <= data.highScoresList.getLowScore())
                this.setNextScreen(new CSHighScoreEntryScreen());
        }
        else
            this.setNextScreen(new CSHighScoresScreen());

        this.setDisposable(true);
        this.setFinished(true);
    }


    /**
     * @param gameWon the gameWon to set
     */
    public void setGameWon(final boolean gameWon)
    {
        this.gameWon = gameWon;
    }

}

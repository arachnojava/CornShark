import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import mhframework.MHActor;
import mhframework.MHDisplayModeChooser;
import mhframework.gui.MHGUIDialogScreen;
import mhframework.gui.MHGUILabel;

public class CSHighScoreEntryScreen extends MHGUIDialogScreen
{
    private final MHGUILabel _txtInput = new MHGUILabel();
    private final StringBuffer input;
    private final CSDataModel data = CSDataModel.getInstance();

    private final MHActor waves;

    public CSHighScoreEntryScreen()
    {
        //setTitle("Fast Victory!");
        setTitle("");
        setMessage("Please type your name and press <ENTER>:");
        setOKButton(null);
        setCancelButton(null);

        waves = new CSWaveAnimation();

        final int fontSize = 32;
        input = new StringBuffer();

        _txtInput.setText(input.toString());
        _txtInput.setPaint(Color.ORANGE);
        _txtInput.setFont(new Font("SansSerif", Font.BOLD, fontSize));
        _txtInput.setPosition(50, 280);
        _txtInput.setSize(720, fontSize+10);

        add(_txtInput);
    }


//    @Override
/*    public void drawTitle(final Graphics2D g2d, final int x, final int y)
    {
        super.drawTitle(g2d, x, y+80);
    }
*/

    @Override
    public void drawMessage(final Graphics2D g2d, int x, int y)
    {
        x += MHDisplayModeChooser.DISPLAY_X;
        y += MHDisplayModeChooser.DISPLAY_Y;

        super.drawMessage(g2d, x, y+80);
    }


    @Override
    public void render(final Graphics2D g2d)
    {
            final Image bg = data.igBackground.getImage(0, 0);
            final Image oceanFloor = data.igBottom.getImage(0, 0);
            final Image plant = data.igPlant.getImage(0, 0);

            final int x0 = MHDisplayModeChooser.DISPLAY_X;
            final int y0 = MHDisplayModeChooser.DISPLAY_Y;

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            super.setBackgroundImage(bg);

            super.render(g2d);

            g2d.drawImage(oceanFloor, x0,
                            MHDisplayModeChooser.getScreenSize().height
                            - oceanFloor.getHeight(null)+y0, null);
            g2d.drawImage(plant, 80+x0, MHDisplayModeChooser.getScreenSize().height - plant.getHeight(null) - 25+y0, null);
            waves.render(g2d);

            g2d.drawImage(data.lblFastVictory, 20+x0, 100+y0, null);
            g2d.drawImage(data.gameLogo, MHDisplayModeChooser.getScreenSize().width - data.gameLogo.getWidth(null) - 40+x0, 100+y0, null);

            g2d.setColor(Color.GRAY);
            g2d.draw3DRect(x0+_txtInput.getX()-10, y0+(int)(_txtInput.getY()-_txtInput.getBounds().getHeight())+10, (int)_txtInput.getBounds().getWidth()+10, (int)_txtInput.getBounds().getHeight(), false);
    }

    @Override
    public void advance()
    {
        waves.advance();
    }

    @Override
    public void keyPressed(final KeyEvent e)
    {
        final char keyChar = e.getKeyChar();
        final int  keyCode = e.getKeyCode();

        if ((Character.isLetterOrDigit(keyChar) || keyCode == KeyEvent.VK_SPACE) && input.length() <= 20)
            input.append(keyChar);
        else if (keyCode == KeyEvent.VK_BACK_SPACE)
        {
            if (input.length() > 0)
                input.deleteCharAt(input.length()-1);
        }
        else if (keyCode == KeyEvent.VK_ENTER)
        {
            final String skillLvl = CSOptionsData.getInstance().getSkillLevel().toString();
            data.highScoresList.addToList(input.toString(), data.timer.getGameTimeTotal(), skillLvl);
            this.setFinished(true);
            this.setDisposable(true);
            this.setNextScreen(new CSHighScoresScreen());
        }
        else
            super.keyPressed(e);

        _txtInput.setText(input.toString());

    }
}
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import mhframework.MHDisplayModeChooser;
import mhframework.gui.MHGUIStatusIndicator;


public class CSGameHUD
{
    CSGameScreen screen;
    boolean colorUp = true;
    int colorLevel = 0;
    MHGUIStatusIndicator gameProgress, levelProgress, powerUpProgress;
    CSDataModel data = CSDataModel.getInstance();
    CSGameWorldController controller;

    CSPowerUpEffect powerUpEffect;

    public CSGameHUD(final CSGameScreen s)
    {
        screen = s;

        controller = s.controller;

        gameProgress = new MHGUIStatusIndicator();
        gameProgress.setSize(300, 20);
        gameProgress.setPosition(800 - 340, 30);
        gameProgress.setFont(new Font("Arial", Font.BOLD, 16));
        gameProgress.setTextColor(Color.BLACK);
        gameProgress.setPaint(new GradientPaint(400F, 20F, Color.GREEN, 400F, 40F, new Color(0, 100, 0)));
        gameProgress.setMaxValue(16);
        gameProgress.update(data.getPlayer(controller).getLevel());
        gameProgress.setText(data.getPlayer(controller).getLevel() + " / " + (int)gameProgress.getMaxValue());
        screen.add(gameProgress);

        levelProgress = new MHGUIStatusIndicator();
        levelProgress.setSize(300, 20);
        levelProgress.setPosition(800 - 340, 60);
        levelProgress.setFont(new Font("Arial", Font.BOLD, 16));
        levelProgress.setTextColor(Color.BLACK);
        levelProgress.setPaint(new GradientPaint(400F, 50F, new Color(255,255,0), 400F, 70F, new Color(100, 100, 0)));
        levelProgress.setMaxValue((int)(s.controller.getFishLevelData().getWidth(data.getPlayer(controller).getLevel()+1)*0.75));
        levelProgress.update(data.getPlayer(controller).getHealth());
        levelProgress.setText((int)(levelProgress.getMaxValue() - data.getPlayer(controller).getHealth()) +  " to go...");
        screen.add(levelProgress);


    }


    public void setPowerUpTimer(final CSPowerUpEffect timer)
    {
        powerUpEffect = timer;

        powerUpProgress = new MHGUIStatusIndicator();
        //powerUpProgress.setSize(450, 20);
        //powerUpProgress.setPosition(20, 600-40);
        powerUpProgress.setSize(300, 20);
        powerUpProgress.setPosition(800-340, 90);
        powerUpProgress.setFont(new Font("Arial", Font.BOLD, 16));
        powerUpProgress.setTextColor(Color.BLACK);
        //powerUpProgress.setBarColor(Color.WHITE);
        powerUpProgress.setPaint(new GradientPaint(460F, 90F, new Color(32,32,32), 760F, 110F, new Color(255, 255, 255)));
        powerUpProgress.setMaxValue((int)timer.getTimeLimit());
        powerUpProgress.update(timer.getTimeRemaining());
        powerUpProgress.setText(""+timer.getTimeRemaining()/1000);
        screen.add(powerUpProgress);
    }


    public void render (final Graphics2D g2d)
    {
        int anchorX = 20 + MHDisplayModeChooser.DISPLAY_X;
        final int anchorY = 20 + MHDisplayModeChooser.DISPLAY_Y;
        final int labelWidth = data.lblLevel.getWidth(null);
        final int digitWidth = data.digits[0].getWidth(null);

        g2d.drawImage(data.lblLevel , anchorX, anchorY, null);

        anchorX += labelWidth + 10;

        final int lvl = data.getPlayer(controller).getLevel();

        if (lvl >= 10)
        {
            final Image tens = data.digits[lvl / 10];
            g2d.drawImage(tens, anchorX, anchorY, null);
            anchorX += digitWidth - 10;
        }
        final int onesPlace = (lvl % 10 >= 0 ? lvl % 10 : 0);

        final Image ones = data.digits[onesPlace];
        g2d.drawImage(ones, anchorX, anchorY, null);

        data.timer.render(g2d);
    }

    public void advance()
    {
        gameProgress.update(data.getPlayer(controller).getLevel());
        gameProgress.setText("Current Level:  " + data.getPlayer(controller).getLevel() + " / " + (int)gameProgress.getMaxValue());

        levelProgress.setMaxValue(data.getPlayer(controller).getNextLevelScore());
        levelProgress.update(data.getPlayer(controller).getHealth());
        levelProgress.setText("To Next Level:  " + data.getPlayer(controller).getHealth() + " / " +(int)(levelProgress.getMaxValue()));

        if (powerUpProgress != null)
        {
            powerUpProgress.update(powerUpEffect.getTimeRemaining());
            powerUpProgress.setText("Power-up time:  "+((powerUpEffect.getTimeRemaining()/1000)+1) + " sec.");

            if (powerUpEffect.isFinished())
            {
                screen.remove(powerUpProgress);
                powerUpProgress = null;
            }
        }


        if (colorUp)
            colorLevel++;
        else
            colorLevel--;

        if (colorLevel >= 255)
            colorUp = false;
        else if (colorLevel <= 0)
            colorUp = true;


        gameProgress.setTextColor(new Color(colorLevel, colorLevel, colorLevel));
        levelProgress.setTextColor(new Color(255-colorLevel, 255-colorLevel, 255-colorLevel));
    }
}

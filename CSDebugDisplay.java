import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import mhframework.MHActorList;
import mhframework.MHDisplayModeChooser;
import mhframework.MHRenderable;

public class CSDebugDisplay implements MHRenderable
{
    private final Color c = Color.WHITE;
    private final CSGameScreen screen;
    private final CSDataModel data = CSDataModel.getInstance();
    private final java.text.NumberFormat nf = java.text.NumberFormat.getInstance();

    public static final Font font = new Font("SanSerif", Font.PLAIN, 12);

    public CSDebugDisplay(final CSGameScreen view)
    {
        screen = view;
    }

    public void render(final Graphics2D g)
    {
        final int x0 = MHDisplayModeChooser.DISPLAY_X;
        final int y0 = MHDisplayModeChooser.DISPLAY_Y;

        String s = "";

        g.setColor(c);
        g.setFont(font);
        g.drawString("DEBUG MODE", 10+x0, 590+y0);

        s = "Screen Anchor:  (" + (int)screen.screenRect.getX() + ", " + (int)screen.screenRect.getY() + ")";
        g.drawString(s, 20+x0, 20+y0);
        s = "Screen Size:  (" + (int)screen.screenRect.getWidth() + ", " + (int)screen.screenRect.getHeight() + ")";
        g.drawString(s, 20+x0, 35+y0);
        s = "World Size:  (" + (int)data.getWorldWidth() + ", " + (int)data.getWorldHeight() + ")";
        g.drawString(s, 20+x0, 50+y0);

        final MHActorList list = screen.controller.fishList;

        for (int i = 0; i < list.getSize(); i++)
        {
            final CSFishBase a = (CSFishBase) list.get(i);
            if (a.getScaledBounds().intersects(screen.screenRect))
                displayDebugInfo(g, a);
        }

    }

    private void displayDebugInfo(final Graphics g, final CSFishBase fish)
    {
        final int x = (int)(fish.getX() - screen.screenRect.getX()) + MHDisplayModeChooser.DISPLAY_X;
        final int y = (int)(fish.getY() - screen.screenRect.getY()) + MHDisplayModeChooser.DISPLAY_Y;
        int currY = (int)(y + fish.getScaledBounds().getHeight()) + 12;

        //g.setFont(CSDebugDisplay.font);
        g.setColor(c);
        g.drawString(fish.getClass().getSimpleName() + " " + fish.number + ", Level " + fish.getLevel(), x, currY);
        currY += 12;
        g.drawString(fish.getStateBehavior().getClass().getSimpleName()+":  Facing " + (fish.getDirFacing() == CSFishBase.FACING_RIGHT?"right":"left"), x, currY);
        currY += 12;
        g.drawString("Damage:  " + fish.getDamage(), x, currY);
        currY += 12;
        g.drawString("Health:  " + fish.getHealth(), x, currY);
        currY += 12;
        g.drawString("Point value:  " + fish.getPointValue(), x, currY);
        currY += 12;
        g.drawString("World:  (" + (int)fish.getX() + ", " + (int)fish.getY() + ")", x, currY);
        currY += 12;
        g.drawString("Screen:  (" + x + ", " + y + ")", x, currY);
        currY += 12;
        g.drawString("Delta H:[" + nf.format(fish.getHorizontalSpeed()) + "]  V:[" + nf.format(fish.getVerticalSpeed()) + "]", x, currY);
        currY += 12;
        g.drawString("Scale:  " + nf.format(fish.getScale()), x, currY);
        currY += 12;
        g.drawString("Size:  " + (int)fish.getScaledBounds().getWidth() + " x " + (int)fish.getScaledBounds().getHeight(), x, currY);
        g.setColor(Color.RED);
        drawRect(g, fish.getOffenseArea(), fish.getY());
        g.setColor(Color.GREEN);
        drawRect(g, fish.getDefenseArea(), fish.getY()-12);
        g.setColor(Color.CYAN);
        drawRect(g, fish.getSensorRange(), fish.getY()-24);

    }

    private void drawRect(final Graphics g, final Rectangle2D r, double dy)
    {
        final int x = (int)(r.getX() - screen.screenRect.getX()) + MHDisplayModeChooser.DISPLAY_X;
        final int y = (int)(r.getY() - screen.screenRect.getY()) + MHDisplayModeChooser.DISPLAY_Y;
        dy -= screen.screenRect.getY();

        g.drawRect(x, y, (int)r.getWidth(), (int)r.getHeight());
        g.drawString("(" + (int)r.getX() + "," + (int)r.getY() + ")", x, (int)dy);
    }

    public void advance()
    {

    }
}

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import mhframework.MHActor;

public class CSTextGrowEffect extends MHActor
{
    private String text;
    private double delta;
    private double size;
    private Font font;
    private Color color;
    private double finalSize;
    private boolean disposable = false;
    private final CSGameWorldController controller;

    public CSTextGrowEffect(final String msg, final int cx, final int cy, final CSGameWorldController c)
    {
        setText(msg);
        setX(cx);
        setY(cy);

        setDelta(1.2);
        setSize(4);
        finalSize = 50;
        setFont(new Font("SansSerif", Font.BOLD, (int)size));

        setColor(Color.WHITE);

        controller = c;
    }


    @Override
    public void advance()
    {
        size *= delta;
        setFont(new Font("SansSerif", Font.BOLD, (int)size));

        if ((size >= finalSize && delta > 1)
            || (size <= finalSize && delta < 1))
            setDisposable(true);
    }


    @Override
    public void render(final Graphics2D g)
    {
        final FontMetrics fm = g.getFontMetrics(font);

        getBounds().setRect(getX(), getY(), fm.stringWidth(text), size);

        final int w = fm.stringWidth(text)/2;
        final double screenX = getX() - controller.gameScreen.screenRect.getX();
        final double screenY = getY() - controller.gameScreen.screenRect.getY();

        final Color c = getColor();
        int alpha;
        if (finalSize > size)
            alpha = (int)((1-(size/finalSize)) * 255);
        else
            alpha = (int)((1-(finalSize/size)) * 255);
        g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha));
        g.setFont(font);
        g.drawString(text, (int)(screenX-w), (int)screenY);
    }


    /**
     * @return the text
     */
    public String getText()
    {
        return text;
    }


    /**
     * @param text the text to set
     */
    public void setText(final String text)
    {
        this.text = text;
    }


    /**
     * @return the delta
     */
    public double getDelta()
    {
        return delta;
    }


    /**
     * @param delta the delta to set
     */
    public void setDelta(final double delta)
    {
        this.delta = delta;
    }


    /**
     * @return the startSize
     */
    public double getSize()
    {
        return size;
    }


    /**
     * @param startSize the startSize to set
     */
    public void setSize(final double size)
    {
        this.size = size;
    }


    /**
     * @return the font
     */
    public Font getFont()
    {
        return font;
    }


    /**
     * @param font the font to set
     */
    public void setFont(final Font font)
    {
        this.font = font;
    }


    /**
     * @return the startColor
     */
    public Color getColor()
    {
        return color;
    }


    /**
     * @param startColor the startColor to set
     */
    public void setColor(final Color color)
    {
        this.color = color;
    }


    /**
     * @return the disposable
     */
    public boolean isDisposable()
    {
        return disposable;
    }


    /**
     * @param disposable the disposable to set
     */
    public void setDisposable(final boolean disposable)
    {
        this.disposable = disposable;
    }


    /**
     * @return the finalSize
     */
    public double getFinalSize()
    {
        return finalSize;
    }


    /**
     * @param finalSize the finalSize to set
     */
    public void setFinalSize(final double finalSize)
    {
        this.finalSize = finalSize;
    }

}

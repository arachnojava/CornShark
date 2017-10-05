
import java.awt.Color;



public class CSCharSharkcut extends CSPowerUpBase
{

    public CSCharSharkcut(final CSGameWorldController c)
    {
        super(c);

        setHealth(60);
        setMaxHealth(60);
        setImageGroup(CSDataModel.getInstance().igSharkcut);
        setLocation(Math.random()*data.getWorldWidth(), Math.random()*data.getWorldHeight());
        setMaxHSpeed(12);
        setHorizontalSpeed(getMaxHSpeed());
        setMaxVSpeed(getMaxHSpeed()/3);
        setVerticalSpeed(Math.random() * getMaxVSpeed());
        this.setStateBehavior(sbSwimRight);
    }

    @Override
    public void gotEatenBy(final CSCharCornShark attacker)
    {
        data.playSound(data.idGetPowerUp);

        attacker.setLevel(attacker.getLevel() + 1);
        attacker.setHealth(0);
        data.playSound(data.idLevelUp);

        final CSTextGrowEffect textEffect = new CSTextGrowEffect("Instant Level Up", (int)(attacker.getX()+(attacker.getScaledBounds().getWidth()/2)), (int)attacker.getY(), controller);
        textEffect.setColor(Color.MAGENTA);
        textEffect.setFinalSize(256);
        textEffect.setDelta(1.25);
        controller.foregroundList.add(textEffect);
    }

}

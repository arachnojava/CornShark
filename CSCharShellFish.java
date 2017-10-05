
import java.awt.Color;


public class CSCharShellFish extends CSPowerUpBase
{

    public CSCharShellFish(final CSGameWorldController c)
    {
        super(c);

        setHealth(60);
        setMaxHealth(60);
        setImageGroup(CSDataModel.getInstance().igShellFish);
        setLocation(Math.random()*data.getWorldWidth(), Math.random()*data.getWorldHeight());
        setMaxHSpeed(8);
        setHorizontalSpeed(getMaxHSpeed());
        setMaxVSpeed(getMaxHSpeed()/2);
        setVerticalSpeed(Math.random() * getMaxVSpeed());
        this.setStateBehavior(sbSwimRight);
    }

    @Override
    public void gotEatenBy(final CSCharCornShark attacker)
    {
        if (attacker.hasPowerUp())
        {
            setHealth(60);
            return;
        }

        data.playSound(data.idGetPowerUp);

        attacker.invincibility = new CSShellFishEffect();
        attacker.invincibility.setUp(attacker);
        attacker.controller.gameScreen.getHud().setPowerUpTimer(attacker.invincibility);
        attacker.invincibility.start();

        final CSTextGrowEffect textEffect = new CSTextGrowEffect("Invincibility", (int)(attacker.getX()+(attacker.getScaledBounds().getWidth()/2)), (int)attacker.getY(), controller);
        textEffect.setColor(Color.MAGENTA);
        textEffect.setFinalSize(256);
        textEffect.setDelta(1.25);
        controller.foregroundList.add(textEffect);
    }
}

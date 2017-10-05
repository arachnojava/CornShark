import java.awt.Color;

public class CSCharStripedRacer extends CSPowerUpBase
{

    public CSCharStripedRacer(final CSGameWorldController c)
    {
        super(c);

        setHealth(60);
        setMaxHealth(60);
        setImageGroup(CSDataModel.getInstance().igStripedRacer);
        setLocation(Math.random()*data.getWorldWidth(), Math.random()*data.getWorldHeight());
        setMaxHSpeed(14);
        setHorizontalSpeed(getMaxHSpeed());
        setMaxVSpeed(getMaxHSpeed()/4);
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

        attacker.speedBoost = new CSStripedRacerEffect();
        attacker.speedBoost.setUp(attacker);
        attacker.controller.gameScreen.getHud().setPowerUpTimer(attacker.speedBoost);
        attacker.speedBoost.start();

        final CSTextGrowEffect textEffect = new CSTextGrowEffect("Speed Boost", (int)(attacker.getX()+(attacker.getScaledBounds().getWidth()/2)), (int)attacker.getY(), controller);
        textEffect.setColor(Color.MAGENTA);
        textEffect.setFinalSize(256);
        textEffect.setDelta(1.25);
        controller.foregroundList.add(textEffect);
    }

}

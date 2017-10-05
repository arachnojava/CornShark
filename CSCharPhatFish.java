import java.awt.Color;

public class CSCharPhatFish extends CSPowerUpBase
{

    public CSCharPhatFish(final CSGameWorldController c)
    {
        super(c);

        setHealth(60);
        setMaxHealth(60);
        setImageGroup(CSDataModel.getInstance().igPhatFish);
        setLocation(Math.random()*data.getWorldWidth(), 80 + Math.random()*data.getWorldHeight() - 160);
        setMaxHSpeed(6);
        setHorizontalSpeed(getMaxHSpeed());
        //setMaxVSpeed(getMaxHSpeed()/2);
        //setVerticalSpeed(Math.random() * getMaxVSpeed());

        final double v = 10 + Math.random() * 50;

        sbSwimRight = new CSAISineSwimRight(this);
        ((CSAISineSwimRight)sbSwimRight).vertical = v;

        sbSwimLeft = new CSAISineSwimLeft(this);
        ((CSAISineSwimLeft)sbSwimLeft).vertical = v;

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

        attacker.multiplier = new CSPhatFishEffect();
        attacker.multiplier.setUp(attacker);
        attacker.controller.gameScreen.getHud().setPowerUpTimer(attacker.multiplier);
        attacker.multiplier.start();

        final CSTextGrowEffect textEffect = new CSTextGrowEffect("Score x2", (int)(attacker.getX()+(attacker.getScaledBounds().getWidth()/2)), (int)attacker.getY(), controller);
        textEffect.setColor(Color.MAGENTA);
        textEffect.setFinalSize(256);
        textEffect.setDelta(1.25);
        controller.foregroundList.add(textEffect);
    }

}


import java.awt.Graphics2D;
import java.util.ArrayList;
import mhframework.MHActor;
import mhframework.MHActorList;
import mhframework.MHDisplayModeChooser;
import mhframework.MHRenderable;
import mhframework.media.MHImageGroup;

public class CSGameWorldController implements MHRenderable
{
    private final MHActorList[] actorLists;
    public MHActorList fishList, foregroundList, backgroundList;
    public static CSGameScreen gameScreen;
    private CSFishLevelData fishLevelData;
    private CSCharCornShark player;
    public final CSSkillLevelData skillLevelData;
    CSFishingBoat boat;

    private CSPowerUpEffect powerUpEffect;

    private boolean bitFlag = true;

    private final CSDataModel data = CSDataModel.getInstance();

    /****************************************************************
     *
     * @param view
     */
    public CSGameWorldController(final CSGameScreen view)
    {
        gameScreen = view;
        fishLevelData = new CSFishLevelData();

        skillLevelData = new CSSkillLevelData(CSOptionsData.getInstance().getSkillLevel());

        backgroundList = new MHActorList();
        fishList = new MHActorList();
        foregroundList = new MHActorList();

        player = data.getPlayer(this);

        actorLists = new MHActorList[]
        {
            backgroundList,
            fishList,
            foregroundList
        };

        resetGame();
    }

    /****************************************************************
     *
     */
    public void resetGame()
    {
        data.initializeBackground();
        createScenery();
        initializeFish();
    }

    /****************************************************************
     *
     */
    public CSFishLevelData getFishLevelData()
    {
        if (fishLevelData == null)
            fishLevelData = new CSFishLevelData();

        return fishLevelData;

    }

    /****************************************************************
     *
     */
    private void initializeFish()
    {
        final float fishPerScreen = 0.8f;
        final int numScreens = (int)((data.getWorldWidth() / this.gameScreen.screenRect.getWidth()) * (data.getWorldHeight() / gameScreen.screenRect.getHeight()));
        final int totalFish = (int)(numScreens * fishPerScreen);

        skillLevelData.setNumFish(totalFish);

        fishList.clear();

        player = data.resetPlayer(this);
        fishList.add(player);

        fishList.add(new CSCharSeaChicken(this));

        spawnRandomPowerUp();

        for (int i = 0; i < totalFish; i++)
        {
            // Choose and instantiate one
            final CSFishBase newFish = spawnFish((Math.random() <= this.skillLevelData.getPredatorOdds()));

            // Add it to the list
            fishList.add(newFish);
        }

        // Bait
        fishList.add(boat.getBait());

    }

    /****************************************************************
     *
     */
    public CSFishBase spawnFish(final boolean predator)
    {
        ArrayList<Integer> fishChoices;

        if (predator)
            fishChoices = listPredators();
        else
        	fishChoices = listPrey();

        // Choose one at random
        final int fish = fishChoices.get((int)(Math.random() * fishChoices.size())).intValue();

        // Instantiate it and make sure the level is > player's level
        return createFish(fish);
    }


    /****************************************************************
     *
     */
    private CSFishBase createFish(final int fish)
    {
        CSFishBase newFish;

        // Make the fish
        switch(fish)
        {
            case CSFishConstants.CRACKER_FISHY:
                newFish = new CSCharCrackerFishy(this);
                break;
            case CSFishConstants.PEARAHNA:
                newFish = new CSCharPearahna(this);
                break;
            case CSFishConstants.RAINBOW_SLUSH_TROUT:
                newFish = new CSCharRainbowSlushTrout(this);
                break;
            case CSFishConstants.PHAT_FISH:
                newFish = new CSCharPhatFish(this);
                break;
            case CSFishConstants.SHARKCUT:
                newFish = new CSCharSharkcut(this);
                break;
            case CSFishConstants.STRIPED_RACER:
                newFish = new CSCharStripedRacer(this);
                break;
            case CSFishConstants.SHELLFISH:
                newFish = new CSCharShellFish(this);
                break;
            case CSFishConstants.HAMBURGERHEAD_SHARK:
                newFish = new CSCharHamburgerheadShark(this);
                break;
            case CSFishConstants.WHALEMELON:
                newFish = new CSCharWhalemelon(this);
                break;
            case CSFishConstants.CHIPSEN_FISH:
                newFish = new CSCharChipsenFish(this);
                break;
            case CSFishConstants.BANANACUDA:
                newFish = new CSCharBananacuda(this);
                break;
            case CSFishConstants.PEEBEE_JELLYFISH:
                newFish = new CSCharPeebeeJellyfish(this);
                break;
            case CSFishConstants.SHERBET_CRAB:
                newFish = new CSCharSherbetCrab(this);
                break;
            default:
                newFish = new CSCharCrackerFishy(this);
                break;
        }

        if (gameScreen.screenRect.getCenterX() >= data.getWorldWidth()/2)
            newFish.setX(Math.random() * gameScreen.screenRect.getX());
        else
            newFish.setX(gameScreen.screenRect.getX() + gameScreen.screenRect.getWidth() + newFish.getBounds().getWidth());


        return newFish;
    }


    /****************************************************************
     *
     */
    private ArrayList<Integer> listPredators()
    {
        final ArrayList<Integer> fish = new ArrayList<Integer>();

        for (int i = 0; i < CSFishLevelData.NUM_FISHES; i++)
        {
            if (fishLevelData.getMaxLevel(i) >= player.getLevel())
            {
                fish.add(i);
            }
        }

        return fish;
    }


    /****************************************************************
     *
     */
    private ArrayList<Integer> listPrey()
    {
        final ArrayList<Integer> fish = new ArrayList<Integer>();

        for (int i = 0; i < fishLevelData.NUM_FISHES; i++)
        {
            if (fishLevelData.getMinLevel(i) <= player.getLevel())
            {
                fish.add(i);
            }
        }

        return fish;
    }


    /****************************************************************
     *
     */
    public void advance()
    {
        for (final MHActorList list : actorLists)
            list.advance();

        for (int f = 0; f < foregroundList.getSize(); f++)
        {
            if (foregroundList.get(f) instanceof CSTextGrowEffect)
            {
                if (((CSTextGrowEffect)foregroundList.get(f)).isDisposable())
                    foregroundList.remove(f);
            }
        }

        for (int i = 0; i < fishList.getSize(); i++)
        {
            final CSFishBase fish = (CSFishBase) fishList.get(i);;

            if (fish.isDisposable())
            {
                fishList.remove(i);

                if (fish != data.getPlayer(this))
                {
                if (fish.isPowerUp())
                    spawnRandomPowerUp();
                else
                {
                    // Choose and instantiate a new fish to replace the
                    // one that just died.
                    if (!fish.getClass().getName().equals("Bait"))
                    {
                        final CSFishBase newFish = spawnFish((Math.random() <= skillLevelData.getPredatorOdds()));
                        fishList.add(newFish);
                    }
                }
               }
            }
            else
            {
                // Seek and process collisions
                if (fish.getBounds().intersects(gameScreen.screenRect) && !fish.isPowerUp() && !fish.isDead())
                    findCollisions(fish);
            }
        }

    }


    private void spawnRandomPowerUp()
    {
        final double rnd = Math.random();
        final double pct = 0.25;

        if (rnd < pct)
            fishList.add(createFish(CSFishConstants.PHAT_FISH));
        else if (rnd < pct * 2)
            fishList.add(createFish(CSFishConstants.SHARKCUT));
        else if (rnd < pct * 3)
            fishList.add(createFish(CSFishConstants.STRIPED_RACER));
        else
            fishList.add(createFish(CSFishConstants.SHELLFISH));
    }


    /****************************************************************
     *
     */
    private void findCollisions(final CSFishBase source)
    {
        if ((source.biting || source.gotBit) && source != data.getPlayer(this))
            return;

        for (int i = 0; i < fishList.getSize(); i++)
        {
            final CSFishBase target = (CSFishBase) fishList.get(i);
            if (target.gotBit || target.isDead() || target == source) continue;

            if (target.getScaledBounds().intersects(gameScreen.screenRect))
            {
                // If source's offense area is colliding with target, it's a hit.
                if (source.getOffenseArea().intersects(target.getDefenseArea()))
                {
                    source.bite(target);
                    source.timeTargetAcquired = System.currentTimeMillis();
                }
            }
        }
    }


    /****************************************************************
     *
     */
    public void render(final Graphics2D g)
    {
        final int x0 = MHDisplayModeChooser.DISPLAY_X;
        final int y0 = MHDisplayModeChooser.DISPLAY_Y;

        bitFlag = !bitFlag;
        for (final MHActorList list : actorLists)
        {
            for (int i = 0; i < list.getSize(); i++)
            {
                final MHActor a = list.get(i);

                final double screenX = a.getX() - gameScreen.screenRect.getX() + x0;
                final double screenY = a.getY() - gameScreen.screenRect.getY() + y0;

                if (a instanceof CSTextGrowEffect)
                    a.render(g);
                //else if (a instanceof CSPowerUpBase)
                //    a.render(g, (int)screenX, (int)screenY);
                else if (a.getBounds().intersects(gameScreen.screenRect) || a instanceof CSPowerUpBase)
                {
                    if (list == fishList)
                    {
                        if (((CSFishBase) a).gotBit && bitFlag);
                        else
                            a.render(g, (int)screenX, (int)screenY);
                    }
                    else
                        g.drawImage(a.getImage(), (int)screenX, (int)screenY, null);
                }
            }
        }

        CSParticleManager.advance();
        CSParticleManager.render(g, gameScreen.screenRect.getX()+x0, gameScreen.screenRect.getY()+y0);
    }


    /****************************************************************
     *
     */
    private void createScenery()
    {
        backgroundList.clear();
        foregroundList.clear();

        // Background water
        final MHImageGroup water = data.igBackground;
        int x=0, y=0;
        while (y < data.getWorldHeight())
        {
            while (x < data.getWorldWidth())
            {
                final MHActor a = new MHActor();
                a.setImageGroup(water);
                a.setLocation(x, y);
                backgroundList.add(a);

                x += water.getImage(0, 0).getWidth(null);
            }
            y += water.getImage(0, 0).getHeight(null);
            x = 0;
        }

        // Waves
        final MHImageGroup waves = data.igTop;
        x = 0;
        while (x < data.getWorldWidth())
        {
            final MHActor a = new MHActor();
            a.setImageGroup(waves);
            a.setLocation(x, 0);
            backgroundList.add(a);

            x += waves.getImage(0, 0).getWidth(null);
        }

        // Walls
        final MHImageGroup walls = data.igWall;
        y = 0;
        while (y < data.getWorldHeight())
        {
            final MHActor a = new MHActor(), b = new MHActor();
            a.setImageGroup(walls);
            b.setImageGroup(walls);
            a.setLocation(0-(walls.getImage(0, 0).getWidth(null)/2), y);
            b.setLocation(data.getWorldWidth()-(walls.getImage(0, 0).getWidth(null)/2), y);
            backgroundList.add(a);
            backgroundList.add(b);

            y += walls.getImage(0, 0).getHeight(null);
        }

        // Ground
        final MHImageGroup sand = data.igBottom;
        x = 0;
        y = (int)(data.getWorldHeight() - sand.getImage(0,0).getHeight(null));
        while (x < data.getWorldWidth())
        {
            final MHActor a = new MHActor();
            a.setImageGroup(sand);
            a.setLocation(x, y);
            foregroundList.add(a);

            x += sand.getImage(0, 0).getWidth(null);
        }

        // Plants
        final MHImageGroup plant = CSDataModel.getInstance().igPlant;
        final MHImageGroup plant2 = CSDataModel.getInstance().igPlant2;
        final int numPlants = (int)(data.getWorldWidth() / (800/2));
        for (int i = 0; i < numPlants; i++)
        {
            x = (int)(Math.random() * data.getWorldWidth());
            y = (int)(data.getWorldHeight() - plant.getImage(0,0).getHeight(null) - (Math.random()*30));
            MHActor a = new MHActor();
            a.setImageGroup(plant);
            a.setLocation(x, y);
            foregroundList.add(a);

            x = (int)(Math.random() * data.getWorldWidth());
            y = (int)(data.getWorldHeight() - plant2.getImage(0,0).getHeight(null) - (Math.random()*30));
            a = new MHActor();
            a.setImageGroup(plant2);
            a.setLocation(x, y);
            backgroundList.add(a);
        }
        // Boat
        boat = new CSFishingBoat(this);
        backgroundList.add(boat);
    }

    /**
     * @return the powerUpEffect
     */
    public CSPowerUpEffect getPowerUpEffect()
    {
        return powerUpEffect;
    }

    /**
     * @param powerUpEffect the powerUpEffect to set
     */
    public void setPowerUpEffect(final CSPowerUpEffect powerUpEffect)
    {
        this.powerUpEffect = powerUpEffect;
        this.gameScreen.getHud().setPowerUpTimer(powerUpEffect);
    }


}


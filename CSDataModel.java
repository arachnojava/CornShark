
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import javax.sound.midi.Sequence;
import mhframework.MHDataModel;
import mhframework.MHDisplayModeChooser;
import mhframework.MHGame;
import mhframework.media.MHImageGroup;
import mhframework.media.MHSoundManager;

public class CSDataModel extends MHDataModel
{
    public static final String IMAGE_DIR = "/images/";
    public static final String SOUND_DIR = "audio/";
    //public static final String SOUND_DIR = "";
    public static final String VERSION = "Beta Version";

    private static CSDataModel instance;

    private CSCharCornShark player;

    // Scenery image groups
    public MHImageGroup igBackground, igBottom, igTop, igWall, igPlant, igPlant2, igPauseBG;

    //public MHImageGroup igFishHook;
    public Image imgFishHook;

    // Character image groups
    public MHImageGroup igCornShark, igCrackerFishy, igPearahna, igPeebeeJellyfish;
    public MHImageGroup igRainbowSlushTrout, igPhatFish, igSharkcut, igSherbetCrab;
    public MHImageGroup igStripedRacer, igShellFish, igHamburgerheadShark, igSeaChicken;
    public MHImageGroup igWhalemelon, igChipsenFish, igBananacuda, igFishingBoat;

    // UI Images
    public final Image gameLogo, lblMainMenu, lblOptionsMenu, lblGamePaused;
    public final Image lblFastVictory, lblFastestWins;
    public final Image imgExit0, imgExit1, imgExit2;
    public final Image imgOptions0, imgOptions1, imgOptions2;
    public final Image imgPlay0, imgPlay1, imgPlay2;
    public final Image imgSound0, imgSound1, imgSound2;
    public final Image imgMusic0, imgMusic1, imgMusic2;
    public final Image imgDifficulty0, imgDifficulty1, imgDifficulty2;
    public final Image imgHealthBars0, imgHealthBars1, imgHealthBars2;
    public final Image imgReturn0, imgReturn1, imgReturn2;
    public final Image imgQuit0, imgQuit1, imgQuit2;
    public Image gameOverImage;
    public Image lblLevel;
    public Image[] digits;

    public final CSGameTimer timer = new CSGameTimer();

    public final CSHighScoresList highScoresList = new CSHighScoresList();


    private Rectangle2D.Double worldRect;

    public static boolean debug = false;

    public static boolean scrollingRight = false,
                          scrollingLeft  = false,
                          scrollingUp    = false,
                          scrollingDown  = false;

    // Sounds
    String fileButtonClick     = SOUND_DIR + "ButtonClick.wav";
    public int idButtonClick;
    String fileGetPowerUp      = SOUND_DIR + "GetPowerUp.wav";
    public int idGetPowerUp;
    String fileLosePowerUp     = SOUND_DIR + "LosePowerUp.wav";
    public int idLosePowerUp;
    String fileLevelDown       = SOUND_DIR + "LoseLevel.wav";
    public int idLevelDown;
    String fileGetBit          = SOUND_DIR + "GetBit.wav";
    public int idGetBit;
    String fileCornSharkGetBit = SOUND_DIR + "CornSharkGetBit.wav";
    public int idCornSharkGetBit;
    String fileNPCBite         = SOUND_DIR + "NPCBite.wav";
    public int idNPCBite;
    String fileNPCDie          = SOUND_DIR + "NPCDie.wav";
    public int idNPCDie;
    String fileLevelUp         = SOUND_DIR + "LevelUp.wav";
    public int idLevelUp;

    // Music
    public String fileGameMusic       = SOUND_DIR + "GameMusic.mid";
    public Sequence gameMusic;
    public String fileUIMusic         = SOUND_DIR + "UIMusic.mid";
    public Sequence uiMusic;

    /****************************************************************
     *
     */
    private CSDataModel()
    {
        Image img;

        igBackground = new MHImageGroup();
        igBackground.addSequence(0);
        img = loadImage(IMAGE_DIR + "Background.jpg");
        igBackground.addFrame(0, img, 0);

        igPauseBG = new MHImageGroup();
        igPauseBG.addSequence(0);
        img = loadImage(IMAGE_DIR + "PauseMenuBackground.png");
        igPauseBG.addFrame(0, img, 0);

        igBottom = new MHImageGroup();
        igBottom.addSequence(0);
        img = loadImage(IMAGE_DIR + "OceanFloor.gif");
        igBottom.addFrame(0, img, 0);

        igTop = new MHImageGroup();
        igTop.addSequence(0);
        img = loadImage(IMAGE_DIR + "Waves.gif");
        final Image imgb = loadImage(IMAGE_DIR + "Waves1.gif");
        igTop.addFrame(0, img, 8);
        igTop.addFrame(0, imgb, 16);
        img = loadImage(IMAGE_DIR + "Waves2.gif");
        igTop.addFrame(0, img, 10);
        igTop.addFrame(0, imgb, 16);

        igWall = new MHImageGroup();
        igWall.addSequence(0);
        img = loadImage(IMAGE_DIR + "Wall.gif");
        igWall.addFrame(0, img, 0);

        igPlant = new MHImageGroup();
        igPlant.addSequence(0);
        img = loadImage(IMAGE_DIR + "Plant.gif");
        igPlant.addFrame(0, img, 0);

        igPlant2 = new MHImageGroup();
        igPlant2.addSequence(0);
        img = loadImage(IMAGE_DIR + "Plant2.gif");
        igPlant2.addFrame(0, img, 0);

        gameLogo = loadImage(IMAGE_DIR + "CornSharkLogo.gif");

        lblMainMenu = loadImage(IMAGE_DIR + "lblMainMenu.gif");
        lblOptionsMenu = loadImage(IMAGE_DIR + "lblOptions.gif");
        lblGamePaused = loadImage(IMAGE_DIR + "lblGamePaused.gif");
        lblFastVictory = loadImage(IMAGE_DIR + "lblFastVictory.gif");
        lblFastestWins = loadImage(IMAGE_DIR + "lblFastestWins.gif");

        imgExit0 = loadImage(IMAGE_DIR + "btnExit0.gif");
        imgExit1  = loadImage(IMAGE_DIR + "btnExit1.gif");
        imgExit2   = loadImage(IMAGE_DIR + "btnExit2.gif");
        imgOptions0 = loadImage(IMAGE_DIR + "btnOptions0.gif");
        imgOptions1 = loadImage(IMAGE_DIR + "btnOptions1.gif");
        imgOptions2 = loadImage(IMAGE_DIR + "btnOptions2.gif");
        imgPlay0   = loadImage(IMAGE_DIR + "btnPlay0.gif");
        imgPlay1  = loadImage(IMAGE_DIR + "btnPlay1.gif");
        imgPlay2  = loadImage(IMAGE_DIR + "btnPlay2.gif");
        imgSound0 = loadImage(IMAGE_DIR + "btnSound0.gif");
        imgSound1 = loadImage(IMAGE_DIR + "btnSound1.gif");
        imgSound2  = loadImage(IMAGE_DIR + "btnSound2.gif");
        imgMusic0   = loadImage(IMAGE_DIR + "btnMusic0.gif");
        imgMusic1    = loadImage(IMAGE_DIR + "btnMusic1.gif");
        imgMusic2     = loadImage(IMAGE_DIR + "btnMusic2.gif");
        imgDifficulty0 = loadImage(IMAGE_DIR + "btnDifficulty0.gif");
        imgDifficulty1 = loadImage(IMAGE_DIR + "btnDifficulty1.gif");
        imgDifficulty2 = loadImage(IMAGE_DIR + "btnDifficulty2.gif");
        imgHealthBars0   = loadImage(IMAGE_DIR + "btnHealthBars0.gif");
        imgHealthBars1   = loadImage(IMAGE_DIR + "btnHealthBars1.gif");
        imgHealthBars2   = loadImage(IMAGE_DIR + "btnHealthBars2.gif");
        imgReturn0   = loadImage(IMAGE_DIR + "btnReturn0.gif");
        imgReturn1  = loadImage(IMAGE_DIR + "btnReturn1.gif");
        imgReturn2 = loadImage(IMAGE_DIR + "btnReturn2.gif");
        imgQuit0  = loadImage(IMAGE_DIR + "btnQuit0.gif");
        imgQuit1 = loadImage(IMAGE_DIR + "btnQuit1.gif");
        imgQuit2 = loadImage(IMAGE_DIR + "btnQuit2.gif");

        lblLevel = loadImage(IMAGE_DIR + "lblLevel.png");
        digits = new Image[10];
        for (int i = 0; i <= 9; i++)
            digits[i] = loadImage(IMAGE_DIR + i + ".png");

        // Fish hook
        imgFishHook = loadImage(IMAGE_DIR + "FishHook.png");
        //igFishHook = new MHImageGroup();
        //igFishHook.addSequence(0);
        //igFishHook.addFrame(0, IMAGE_DIR + "FishHook.png", 1);

        // Sounds
        idButtonClick = getSoundManager().addSound(fileButtonClick);
        idGetPowerUp = getSoundManager().addSound(fileGetPowerUp);
        idLosePowerUp = getSoundManager().addSound(fileLosePowerUp);
        idLevelDown = getSoundManager().addSound(fileLevelDown);
        idGetBit =  getSoundManager().addSound(fileGetBit);
        idCornSharkGetBit =  getSoundManager().addSound(fileCornSharkGetBit);
        idNPCBite = getSoundManager().addSound(fileNPCBite);
        idNPCDie = getSoundManager().addSound(fileNPCDie);
        idLevelUp = getSoundManager().addSound(fileLevelUp);

        // Music
        gameMusic = getMidiPlayer().getSequence(fileGameMusic);
        uiMusic = getMidiPlayer().getSequence(fileUIMusic);
    }


    /****************************************************************
     *
     */
    public void loadFishImages()
    {
        final CSCharacterLoader loader = new CSCharacterLoader();

        igCornShark = loader.loadCornShark();
        igCrackerFishy = loader.loadCrackerFishy();
        igPearahna = loader.loadPearahna();
        igRainbowSlushTrout = loader.loadRainbowSlushTrout();
        igPhatFish = loader.loadPhatFish();
        igSharkcut = loader.loadSharkcut();
        igStripedRacer = loader.loadStripedRacer();
        igShellFish = loader.loadShellFish();
        igHamburgerheadShark = loader.loadHamburgerheadShark();
        igWhalemelon = loader.loadWhalemelon();
        igChipsenFish = loader.loadChipsenFish();
        igBananacuda = loader.loadBananacuda();
        igFishingBoat = loader.loadFishingBoat();
        igPeebeeJellyfish = loader.loadPeebeeJellyfish();
        igSherbetCrab = loader.loadSherbetCrab();
        igSeaChicken = loader.loadSeaChicken();

    }

    public void playSound(final int soundID)
    {
        if (CSOptionsData.getInstance().isSoundOn())
            getSoundManager().play(soundID, false, MHSoundManager.AUTO_ASSIGN_CHANNEL);
    }


    public void playMusic(final Sequence midiSequence)
    {
        if (CSOptionsData.getInstance().isMusicOn())
            getMidiPlayer().play(midiSequence, true);
    }

    public void stopMusic()
    {
        getMidiPlayer().stop();
    }

    /****************************************************************
     *
     */
    public static CSDataModel getInstance()
    {
        if (instance == null)
            instance = new CSDataModel();

        return instance;
    }


    /****************************************************************
     *
     */
    public CSCharCornShark getPlayer(final CSGameWorldController c)
    {
    	if (player == null)
    		player = resetPlayer(c);

    	return player;
    }


    /****************************************************************
     *
     */
    public CSCharCornShark resetPlayer(final CSGameWorldController c)
    {
   		player = new CSCharCornShark(c);

    	return player;
    }



    /****************************************************************
     *
     */
    private void setWorldSize(int width, int height)
    {
        final int screenWidth = MHDisplayModeChooser.getScreenSize().width;
        final int screenHeight = MHDisplayModeChooser.getScreenSize().height;

        if (width < screenWidth)
            width = screenWidth;

        if (height < screenHeight)
            height = screenHeight;

        worldRect = new Rectangle2D.Double(0, 0, width, height);

    }


    /****************************************************************
     *
     */
    public void initializeBackground()
    {
        if (CSOptionsData.getInstance().getSkillLevel() == CSSkillLevel.EASY)
            setWorldSize(800*2, 600*2);
        else if (CSOptionsData.getInstance().getSkillLevel() == CSSkillLevel.HARD)
            setWorldSize(800*10, 600*8);
        else
            setWorldSize(800*6, 600*4);
    }


    /****************************************************************
     *
     */
    public Rectangle2D.Double getWorldRect()
    {
        if (worldRect == null)
            this.initializeBackground();

        return worldRect;
    }


    /****************************************************************
     *
     */
    public double getWorldWidth()
    {
        return getWorldRect().width;
    }


    /****************************************************************
     *
     */
    public double getWorldHeight()
    {
        return getWorldRect().height;
    }


    /****************************************************************
     *
     */
    public double getWorldTopBound()
    {
        return this.igTop.getImage(0, 0).getHeight(null);
    }


    /****************************************************************
     *
     */
    public double getWorldBottomBound()
    {
        return getWorldHeight() - this.igBottom.getImage(0, 0).getHeight(null)/2.0;
    }


    /****************************************************************
     *
     */
    public double getWorldRightBound()
    {
        return getWorldWidth() - this.igWall.getImage(0, 0).getWidth(null)/2.0;
    }


    /****************************************************************
     *
     */
    public double getWorldLeftBound()
    {
        return igWall.getImage(0, 0).getWidth(null)/2.0;
    }


    /****************************************************************
     *  This method seems idempotent, but it eliminates the
     *  occasional stutter when an image is drawn for the first time.
     *  It is called from the code that handles the initial loading
     *  of the game screen.  In fact, this method really should have
     *  been defined there.
     */
    public void renderAllFrames(final Graphics2D g)
    {
        renderImageGroup(g, this.igBackground);
        renderImageGroup(g, this.igBottom);
        renderImageGroup(g, this.igCornShark);
        renderImageGroup(g, this.igCrackerFishy);
        renderImageGroup(g, this.igRainbowSlushTrout);
        renderImageGroup(g, this.igPearahna);
        renderImageGroup(g, this.igChipsenFish);
        renderImageGroup(g, this.igHamburgerheadShark);
        renderImageGroup(g, this.igPhatFish);
        renderImageGroup(g, this.igSharkcut);
        renderImageGroup(g, this.igShellFish);
        renderImageGroup(g, this.igStripedRacer);
        renderImageGroup(g, this.igWhalemelon);
        renderImageGroup(g, this.igPlant);
        renderImageGroup(g, this.igPlant2);
        renderImageGroup(g, this.igTop);
        renderImageGroup(g, this.igWall);
    }


    /****************************************************************
     *  Utility method called by the <code>renderAllFrames()</code>
     *  method.
     */
    private void renderImageGroup(final Graphics2D g, final MHImageGroup ig)
    {
        for (int s = 0; s < ig.getNumSequences(); s++)
            for (int f = 0; f < ig.getFrameCount(s); f++)
                g.drawImage(ig.getImage(s, f), -99999, -99999, null);
    }


    public void resetImageCount()
    {
        MHDataModel.getMediaTracker().reset();
    }


    public boolean doneLoading()
    {
        return MHDataModel.getMediaTracker().doneLoading();
    }


    public void setProgramOver(final boolean b)
    {
        MHGame.setProgramOver(b);
    }
}

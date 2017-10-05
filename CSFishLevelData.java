
public class CSFishLevelData implements CSFishConstants
{
    public static final int NUM_FISHES = 9;
    public static final int NUM_LEVELS = 17;

    private final FishLevelInfo[] levels;
    private final double[] sizes;

    public CSFishLevelData()
    {
        sizes = new double[NUM_LEVELS];

        // Fish's level determines its size
        sizes[0] = 60.0;
        for (int s = 1; s < NUM_LEVELS; s++)
        {
            sizes[s] = sizes[s-1] * 1.1104;
            if (CornShark.DEBUG)
                System.out.println("Level " + s + ":  " + sizes[s] + " px");
        }

        levels = new FishLevelInfo[NUM_FISHES];
        levels[this.CRACKER_FISHY]       = new FishLevelInfo( 0,  3);
        levels[this.PEEBEE_JELLYFISH]    = new FishLevelInfo( 1,  3);
        levels[this.SHERBET_CRAB]        = new FishLevelInfo( 2,  5);
        levels[this.CHIPSEN_FISH]        = new FishLevelInfo( 3,  6);
        levels[this.RAINBOW_SLUSH_TROUT] = new FishLevelInfo( 6,  8);
        levels[this.BANANACUDA]          = new FishLevelInfo( 8, 10);
        levels[this.PEARAHNA]            = new FishLevelInfo(10, 12);
        levels[this.HAMBURGERHEAD_SHARK] = new FishLevelInfo(13, 15);
        levels[this.WHALEMELON]          = new FishLevelInfo(15, 16);
    }


    public int getMinLevel(final int fishType)
    {
        return levels[fishType].minLevel;
    }


    public int getMaxLevel(final int fishType)
    {
        return levels[fishType].maxLevel;
    }


    public double getWidth(final int fishLevel)
    {
        if (fishLevel >= 0 && fishLevel < NUM_LEVELS)
            return sizes[fishLevel];

        return 0.0;
    }


    private static class FishLevelInfo
    {
        int minLevel, maxLevel;

        public FishLevelInfo(final int min, final int max)
        {
            minLevel = min;
            maxLevel = max;
        }
    }
}

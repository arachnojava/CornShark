import java.awt.Image;
import mhframework.media.MHImageGroup;

public class CSCharacterLoader
{
    private static final String IMAGE_DIR = CSDataModel.IMAGE_DIR;
    private final CSDataModel d = CSDataModel.getInstance();

    private Image ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9, ex10;



    public CSCharacterLoader()
    {
        loadExplosionAnimation();
    }

    private void loadExplosionAnimation()
    {
        final String fb = IMAGE_DIR + "explosion9-";

        ex1  = d.loadImage(fb + "01.gif");
        ex2  = d.loadImage(fb + "02.gif");
        ex3  = d.loadImage(fb + "03.gif");
        ex4  = d.loadImage(fb + "04.gif");
        ex5  = d.loadImage(fb + "05.gif");
        ex6  = d.loadImage(fb + "06.gif");
        ex7  = d.loadImage(fb + "07.gif");
        ex8  = d.loadImage(fb + "08.gif");
        ex9  = d.loadImage(fb + "09.gif");
        ex10 = d.loadImage(fb + "10.gif");
    }

    private void setExplosionAnimation(final MHImageGroup ig)
    {
        final int duration = 1;

        ig.addSequence(CSFishBase.ACTION_DIE);
        ig.addFrame(CSFishBase.ACTION_DIE, ex1, duration);
        ig.addFrame(CSFishBase.ACTION_DIE, ex2, duration);
        ig.addFrame(CSFishBase.ACTION_DIE, ex3, duration);
        ig.addFrame(CSFishBase.ACTION_DIE, ex4, duration);
        ig.addFrame(CSFishBase.ACTION_DIE, ex5, duration);
        ig.addFrame(CSFishBase.ACTION_DIE, ex6, duration);
        ig.addFrame(CSFishBase.ACTION_DIE, ex7, duration);
        ig.addFrame(CSFishBase.ACTION_DIE, ex8, duration);
        ig.addFrame(CSFishBase.ACTION_DIE, ex9, duration);
        ig.addFrame(CSFishBase.ACTION_DIE, ex10, duration);
        ig.addFrame(CSFishBase.ACTION_DIE, ex10, duration);
    }


    public MHImageGroup loadCornShark()
    {
        final Image baseR = d.loadImage(IMAGE_DIR + "Char-Cornshark-R.gif");
        final Image baseL = d.loadImage(IMAGE_DIR + "Char-Cornshark-L.gif");

        final Image swimRP1 = d.loadImage(IMAGE_DIR + "Char-Cornshark-R+1.png");
        final Image swimRP2 = d.loadImage(IMAGE_DIR + "Char-Cornshark-R+2.png");
        final Image swimRM1 = d.loadImage(IMAGE_DIR + "Char-Cornshark-R-1.png");
        final Image swimRM2 = d.loadImage(IMAGE_DIR + "Char-Cornshark-R-2.png");

        final Image swimLP1 = d.loadImage(IMAGE_DIR + "Char-Cornshark-L+1.png");
        final Image swimLP2 = d.loadImage(IMAGE_DIR + "Char-Cornshark-L+2.png");
        final Image swimLM1 = d.loadImage(IMAGE_DIR + "Char-Cornshark-L-1.png");
        final Image swimLM2 = d.loadImage(IMAGE_DIR + "Char-Cornshark-L-2.png");

        final MHImageGroup igCornShark = new MHImageGroup();
        final int s = 2;

        // Swim right
        igCornShark.addSequence(CSFishBase.ACTION_SWIM_RIGHT);
        igCornShark.addFrame(CSFishBase.ACTION_SWIM_RIGHT, baseR, s);
        igCornShark.addFrame(CSFishBase.ACTION_SWIM_RIGHT, swimRP1, s);
        igCornShark.addFrame(CSFishBase.ACTION_SWIM_RIGHT, swimRP2, s);
        igCornShark.addFrame(CSFishBase.ACTION_SWIM_RIGHT, swimRP1, s);
        igCornShark.addFrame(CSFishBase.ACTION_SWIM_RIGHT, baseR, s);
        igCornShark.addFrame(CSFishBase.ACTION_SWIM_RIGHT, swimRM1, s);
        igCornShark.addFrame(CSFishBase.ACTION_SWIM_RIGHT, swimRM2, s);
        igCornShark.addFrame(CSFishBase.ACTION_SWIM_RIGHT, swimRM1, s);
        // Swim left
        igCornShark.addSequence(CSFishBase.ACTION_SWIM_LEFT);
        igCornShark.addFrame(CSFishBase.ACTION_SWIM_LEFT, baseL, s);
        igCornShark.addFrame(CSFishBase.ACTION_SWIM_LEFT, swimLP1, s);
        igCornShark.addFrame(CSFishBase.ACTION_SWIM_LEFT, swimLP2, s);
        igCornShark.addFrame(CSFishBase.ACTION_SWIM_LEFT, swimLP1, s);
        igCornShark.addFrame(CSFishBase.ACTION_SWIM_LEFT, baseL, s);
        igCornShark.addFrame(CSFishBase.ACTION_SWIM_LEFT, swimLM1, s);
        igCornShark.addFrame(CSFishBase.ACTION_SWIM_LEFT, swimLM2, s);
        igCornShark.addFrame(CSFishBase.ACTION_SWIM_LEFT, swimLM1, s);
        // Bite right
        igCornShark.addSequence(CSFishBase.ACTION_BITE_RIGHT);
        igCornShark.addFrame(CSFishBase.ACTION_BITE_RIGHT, IMAGE_DIR + "Char-Cornshark-BiteR0.gif", 3);
        igCornShark.addFrame(CSFishBase.ACTION_BITE_RIGHT, IMAGE_DIR + "Char-Cornshark-BiteR1.gif", 6);
        igCornShark.addFrame(CSFishBase.ACTION_BITE_RIGHT, baseR, 1);
        // Bite left
        igCornShark.addSequence(CSFishBase.ACTION_BITE_LEFT);
        igCornShark.addFrame(CSFishBase.ACTION_BITE_LEFT, IMAGE_DIR + "Char-Cornshark-BiteL0.gif", 3);
        igCornShark.addFrame(CSFishBase.ACTION_BITE_LEFT, IMAGE_DIR + "Char-Cornshark-BiteL1.gif", 6);
        igCornShark.addFrame(CSFishBase.ACTION_BITE_LEFT, baseL, 1);
        // Die
        this.setExplosionAnimation(igCornShark);
        // Swim down-right
        igCornShark.addSequence(CSFishBase.ACTION_SWIM_DOWN_RIGHT);
        igCornShark.addFrame(CSFishBase.ACTION_SWIM_DOWN_RIGHT, IMAGE_DIR + "Char-Cornshark-DR.png", 1);
        // Swim down-left
        igCornShark.addSequence(CSFishBase.ACTION_SWIM_DOWN_LEFT);
        igCornShark.addFrame(CSFishBase.ACTION_SWIM_DOWN_LEFT, IMAGE_DIR + "Char-Cornshark-DL.png", 1);
        // Swim up-right
        igCornShark.addSequence(CSFishBase.ACTION_SWIM_UP_RIGHT);
        igCornShark.addFrame(CSFishBase.ACTION_SWIM_UP_RIGHT, IMAGE_DIR + "Char-Cornshark-UR-1.gif", 1);
        igCornShark.addFrame(CSFishBase.ACTION_SWIM_UP_RIGHT, IMAGE_DIR + "Char-Cornshark-UR.png", 1);
        igCornShark.addFrame(CSFishBase.ACTION_SWIM_UP_RIGHT, IMAGE_DIR + "Char-Cornshark-UR+1.gif", 1);
        igCornShark.addFrame(CSFishBase.ACTION_SWIM_UP_RIGHT, IMAGE_DIR + "Char-Cornshark-UR.png", 1);
        // Swim up-left
        igCornShark.addSequence(CSFishBase.ACTION_SWIM_UP_LEFT);
        igCornShark.addFrame(CSFishBase.ACTION_SWIM_UP_LEFT, IMAGE_DIR + "Char-Cornshark-UL-1.gif", 1);
        igCornShark.addFrame(CSFishBase.ACTION_SWIM_UP_LEFT, IMAGE_DIR + "Char-Cornshark-UL.png", 1);
        igCornShark.addFrame(CSFishBase.ACTION_SWIM_UP_LEFT, IMAGE_DIR + "Char-Cornshark-UL+1.gif", 1);
        igCornShark.addFrame(CSFishBase.ACTION_SWIM_UP_LEFT, IMAGE_DIR + "Char-Cornshark-UL.png", 1);
        // Idle right
        igCornShark.addSequence(CSFishBase.ACTION_IDLE_RIGHT);
        igCornShark.addFrame(CSFishBase.ACTION_IDLE_RIGHT, baseR, 200);
        igCornShark.addFrame(CSFishBase.ACTION_IDLE_RIGHT, swimRP1, 1);
        igCornShark.addFrame(CSFishBase.ACTION_IDLE_RIGHT, swimRP2, 1);
        igCornShark.addFrame(CSFishBase.ACTION_IDLE_RIGHT, swimRP1, 1);
        igCornShark.addFrame(CSFishBase.ACTION_IDLE_RIGHT, baseR, 200);
        igCornShark.addFrame(CSFishBase.ACTION_IDLE_RIGHT, IMAGE_DIR + "Char-Cornshark-BiteR1.gif", 64);
        igCornShark.addFrame(CSFishBase.ACTION_IDLE_RIGHT, baseR, 200);
        igCornShark.addFrame(CSFishBase.ACTION_IDLE_RIGHT, swimRM1, 1);
        igCornShark.addFrame(CSFishBase.ACTION_IDLE_RIGHT, swimRM2, 1);
        igCornShark.addFrame(CSFishBase.ACTION_IDLE_RIGHT, swimRM1, 1);
        // Idle left
        igCornShark.addSequence(CSFishBase.ACTION_IDLE_LEFT);
        igCornShark.addFrame(CSFishBase.ACTION_IDLE_LEFT, baseL, 200);
        igCornShark.addFrame(CSFishBase.ACTION_IDLE_LEFT, swimLM1, 1);
        igCornShark.addFrame(CSFishBase.ACTION_IDLE_LEFT, swimLM2, 1);
        igCornShark.addFrame(CSFishBase.ACTION_IDLE_LEFT, swimLM1, 1);
        igCornShark.addFrame(CSFishBase.ACTION_IDLE_LEFT, baseL, 200);
        igCornShark.addFrame(CSFishBase.ACTION_IDLE_LEFT, IMAGE_DIR + "Char-Cornshark-BiteL1.gif", 64);
        igCornShark.addFrame(CSFishBase.ACTION_IDLE_LEFT, baseL, 200);
        igCornShark.addFrame(CSFishBase.ACTION_IDLE_LEFT, swimLP1, 1);
        igCornShark.addFrame(CSFishBase.ACTION_IDLE_LEFT, swimLP2, 1);
        igCornShark.addFrame(CSFishBase.ACTION_IDLE_LEFT, swimLP1, 1);

        return igCornShark;
    }

    public MHImageGroup loadCrackerFishy()
    {
        final MHImageGroup ig = new MHImageGroup();
        final Image baseR = d.loadImage(IMAGE_DIR + "Char-CrackerFishy-R.gif");
        final Image baseL = d.loadImage(IMAGE_DIR + "Char-CrackerFishy-L.gif");

        ig.addSequence(CSFishBase.ACTION_SWIM_RIGHT);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, IMAGE_DIR + "Char-CrackerFishy-R+1.png", 2);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, baseR, 2);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, IMAGE_DIR + "Char-CrackerFishy-R-1.png", 2);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, baseR, 2);

        ig.addSequence(CSFishBase.ACTION_SWIM_LEFT);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, IMAGE_DIR + "Char-CrackerFishy-L+1.png", 2);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, baseL, 2);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, IMAGE_DIR + "Char-CrackerFishy-L-1.png", 2);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, baseL, 2);

        ig.addSequence(CSFishBase.ACTION_BITE_RIGHT);
        ig.addFrame(CSFishBase.ACTION_BITE_RIGHT, IMAGE_DIR + "Char-CrackerFishy-BiteR.png", 8);
        ig.addFrame(CSFishBase.ACTION_BITE_RIGHT, baseR, 2);

        ig.addSequence(CSFishBase.ACTION_BITE_LEFT);
        ig.addFrame(CSFishBase.ACTION_BITE_LEFT, IMAGE_DIR + "Char-CrackerFishy-BiteL.png", 8);
        ig.addFrame(CSFishBase.ACTION_BITE_LEFT, baseL, 2);

        this.setExplosionAnimation(ig);

        return ig;
    }

    public MHImageGroup loadPearahna()
    {
        final MHImageGroup ig = new MHImageGroup();
        final Image baseR = d.loadImage(IMAGE_DIR + "Char-Pearahna-R.gif");
        final Image baseL = d.loadImage(IMAGE_DIR + "Char-Pearahna-L.gif");

        ig.addSequence(CSFishBase.ACTION_SWIM_RIGHT);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, baseR, 2);

        ig.addSequence(CSFishBase.ACTION_SWIM_LEFT);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, baseL, 2);

        ig.addSequence(CSFishBase.ACTION_BITE_RIGHT);
        ig.addFrame(CSFishBase.ACTION_BITE_RIGHT, IMAGE_DIR + "Char-Pearahna-BiteR.png", 12);
        ig.addFrame(CSFishBase.ACTION_BITE_RIGHT, baseR, 4);

        ig.addSequence(CSFishBase.ACTION_BITE_LEFT);
        ig.addFrame(CSFishBase.ACTION_BITE_LEFT, IMAGE_DIR + "Char-Pearahna-BiteL.png", 12);
        ig.addFrame(CSFishBase.ACTION_BITE_LEFT, baseL, 4);

        this.setExplosionAnimation(ig);

        return ig;
    }

    public MHImageGroup loadRainbowSlushTrout()
    {
        final MHImageGroup ig = new MHImageGroup();
        final int s = 4;

        final Image baseR = d.loadImage(IMAGE_DIR + "Char-RainbowSlushTrout-R.gif");
        final Image baseL = d.loadImage(IMAGE_DIR + "Char-RainbowSlushTrout-L.gif");

        final Image swimRP1 = d.loadImage(IMAGE_DIR + "Char-RainbowSlushTrout-R+1.png");
        final Image swimRP2 = d.loadImage(IMAGE_DIR + "Char-RainbowSlushTrout-R+2.png");
        final Image swimRM1 = d.loadImage(IMAGE_DIR + "Char-RainbowSlushTrout-R-1.png");
        final Image swimRM2 = d.loadImage(IMAGE_DIR + "Char-RainbowSlushTrout-R-2.png");

        final Image swimLP1 = d.loadImage(IMAGE_DIR + "Char-RainbowSlushTrout-L+1.png");
        final Image swimLP2 = d.loadImage(IMAGE_DIR + "Char-RainbowSlushTrout-L+2.png");
        final Image swimLM1 = d.loadImage(IMAGE_DIR + "Char-RainbowSlushTrout-L-1.png");
        final Image swimLM2 = d.loadImage(IMAGE_DIR + "Char-RainbowSlushTrout-L-2.png");

        ig.addSequence(CSFishBase.ACTION_SWIM_RIGHT);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, baseR, s);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, swimRP1, s);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, swimRP2, s);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, swimRP1, s);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, baseR, s);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, swimRM1, s);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, swimRM2, s);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, swimRM1, s);

        ig.addSequence(CSFishBase.ACTION_SWIM_LEFT);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, baseL, s);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, swimLP1, s);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, swimLP2, s);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, swimLP1, s);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, baseL, s);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, swimLM1, s);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, swimLM2, s);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, swimLM1, s);

        ig.addSequence(CSFishBase.ACTION_BITE_RIGHT);
        ig.addFrame(CSFishBase.ACTION_BITE_RIGHT, IMAGE_DIR + "Char-RainbowSlushTrout-BiteR.png", 8);
        ig.addFrame(CSFishBase.ACTION_BITE_RIGHT, baseR, 2);

        ig.addSequence(CSFishBase.ACTION_BITE_LEFT);
        ig.addFrame(CSFishBase.ACTION_BITE_LEFT, IMAGE_DIR + "Char-RainbowSlushTrout-BiteL.png", 8);
        ig.addFrame(CSFishBase.ACTION_BITE_LEFT, baseL, 2);

        this.setExplosionAnimation(ig);
        return ig;
    }

    public MHImageGroup loadPhatFish()
    {
        final MHImageGroup ig = new MHImageGroup();

        ig.addSequence(CSFishBase.ACTION_SWIM_RIGHT);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, IMAGE_DIR + "Char-PhatFish-R.gif", 2);

        ig.addSequence(CSFishBase.ACTION_SWIM_LEFT);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, IMAGE_DIR + "Char-PhatFish-L.gif", 2);

        //this.setExplosionAnimation(ig);

        return ig;
    }

    public MHImageGroup loadSharkcut()
    {
        final MHImageGroup ig = new MHImageGroup();

        ig.addSequence(CSFishBase.ACTION_SWIM_RIGHT);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, IMAGE_DIR + "Char-Sharkcut-R.gif", 2);

        ig.addSequence(CSFishBase.ACTION_SWIM_LEFT);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, IMAGE_DIR + "Char-Sharkcut-L.gif", 2);

        //this.setExplosionAnimation(ig);

        return ig;
    }

    public MHImageGroup loadStripedRacer()
    {
        final MHImageGroup ig = new MHImageGroup();

        ig.addSequence(CSFishBase.ACTION_SWIM_RIGHT);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, IMAGE_DIR + "Char-StripedRacer-R.gif", 2);

        ig.addSequence(CSFishBase.ACTION_SWIM_LEFT);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, IMAGE_DIR + "Char-StripedRacer-L.gif", 2);

        //this.setExplosionAnimation(ig);

        return ig;
    }

    public MHImageGroup loadShellFish()
    {
        final MHImageGroup ig = new MHImageGroup();

        ig.addSequence(CSFishBase.ACTION_SWIM_RIGHT);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, IMAGE_DIR + "Char-ShellFish-R.gif", 2);

        ig.addSequence(CSFishBase.ACTION_SWIM_LEFT);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, IMAGE_DIR + "Char-ShellFish-L.gif", 2);

        //this.setExplosionAnimation(ig);

        return ig;
    }

    public MHImageGroup loadHamburgerheadShark()
    {
        final MHImageGroup ig = new MHImageGroup();
        final Image baseR = d.loadImage(IMAGE_DIR + "Char-HamburgerheadShark-R.gif");
        final Image baseL = d.loadImage(IMAGE_DIR + "Char-HamburgerheadShark-L.gif");

        ig.addSequence(CSFishBase.ACTION_SWIM_RIGHT);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, baseR, 2);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, IMAGE_DIR + "Char-HamburgerheadShark-R-1.gif", 4);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, baseR, 2);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, IMAGE_DIR + "Char-HamburgerheadShark-R+1.gif", 4);

        ig.addSequence(CSFishBase.ACTION_SWIM_LEFT);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, baseL, 2);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, IMAGE_DIR + "Char-HamburgerheadShark-L-1.gif", 4);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, baseL, 2);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, IMAGE_DIR + "Char-HamburgerheadShark-L+1.gif", 4);

        ig.addSequence(CSFishBase.ACTION_BITE_RIGHT);
        ig.addFrame(CSFishBase.ACTION_BITE_RIGHT, IMAGE_DIR + "Char-HamburgerheadShark-BiteR.gif", 4);
        ig.addFrame(CSFishBase.ACTION_BITE_RIGHT, baseR, 4);
        ig.addFrame(CSFishBase.ACTION_BITE_RIGHT, IMAGE_DIR + "Char-HamburgerheadShark-BiteR.gif", 4);
        ig.addFrame(CSFishBase.ACTION_BITE_RIGHT, baseR, 4);

        ig.addSequence(CSFishBase.ACTION_BITE_LEFT);
        ig.addFrame(CSFishBase.ACTION_BITE_LEFT, IMAGE_DIR + "Char-HamburgerheadShark-BiteL.gif", 4);
        ig.addFrame(CSFishBase.ACTION_BITE_LEFT, baseL, 4);
        ig.addFrame(CSFishBase.ACTION_BITE_LEFT, IMAGE_DIR + "Char-HamburgerheadShark-BiteL.gif", 4);
        ig.addFrame(CSFishBase.ACTION_BITE_LEFT, baseL, 4);

        this.setExplosionAnimation(ig);

        return ig;
    }

    public MHImageGroup loadWhalemelon()
    {
        final MHImageGroup ig = new MHImageGroup();
        final Image baseR = d.loadImage(IMAGE_DIR + "Char-Whalemelon-R.gif");
        final Image baseL = d.loadImage(IMAGE_DIR + "Char-Whalemelon-L.gif");

        ig.addSequence(CSFishBase.ACTION_SWIM_RIGHT);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, baseR, 2);

        ig.addSequence(CSFishBase.ACTION_SWIM_LEFT);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, baseL, 2);

        ig.addSequence(CSFishBase.ACTION_BITE_RIGHT);
        ig.addFrame(CSFishBase.ACTION_BITE_RIGHT, IMAGE_DIR + "Char-Whalemelon-BiteR0.gif", 4);
        ig.addFrame(CSFishBase.ACTION_BITE_RIGHT, IMAGE_DIR + "Char-Whalemelon-BiteR1.gif", 4);
        ig.addFrame(CSFishBase.ACTION_BITE_RIGHT, IMAGE_DIR + "Char-Whalemelon-BiteR0.gif", 4);
        ig.addFrame(CSFishBase.ACTION_BITE_RIGHT, baseR, 4);

        ig.addSequence(CSFishBase.ACTION_BITE_LEFT);
        ig.addFrame(CSFishBase.ACTION_BITE_LEFT, IMAGE_DIR + "Char-Whalemelon-BiteL0.gif", 4);
        ig.addFrame(CSFishBase.ACTION_BITE_LEFT, IMAGE_DIR + "Char-Whalemelon-BiteL1.gif", 4);
        ig.addFrame(CSFishBase.ACTION_BITE_LEFT, IMAGE_DIR + "Char-Whalemelon-BiteL0.gif", 4);
        ig.addFrame(CSFishBase.ACTION_BITE_LEFT, baseL, 4);

        this.setExplosionAnimation(ig);

        return ig;
    }

    public MHImageGroup loadChipsenFish()
    {
        final MHImageGroup ig = new MHImageGroup();
        final Image baseR = d.loadImage(IMAGE_DIR + "Char-ChipsenFish-R.gif");
        final Image baseL = d.loadImage(IMAGE_DIR + "Char-ChipsenFish-L.gif");

        ig.addSequence(CSFishBase.ACTION_SWIM_RIGHT);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, baseR, 5);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, IMAGE_DIR + "Char-ChipsenFish-R-1.gif", 5);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, baseR, 5);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, IMAGE_DIR + "Char-ChipsenFish-R+1.gif", 5);

        ig.addSequence(CSFishBase.ACTION_SWIM_LEFT);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, baseL, 5);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, IMAGE_DIR + "Char-ChipsenFish-L-1.gif", 5);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, baseL, 5);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, IMAGE_DIR + "Char-ChipsenFish-L+1.gif", 5);

        ig.addSequence(CSFishBase.ACTION_BITE_RIGHT);
        ig.addFrame(CSFishBase.ACTION_BITE_RIGHT, IMAGE_DIR + "Char-ChipsenFish-BiteR.gif", 10);
        ig.addFrame(CSFishBase.ACTION_BITE_RIGHT, baseR, 4);

        ig.addSequence(CSFishBase.ACTION_BITE_LEFT);
        ig.addFrame(CSFishBase.ACTION_BITE_LEFT, IMAGE_DIR + "Char-ChipsenFish-BiteL.gif", 10);
        ig.addFrame(CSFishBase.ACTION_BITE_LEFT, baseL, 4);

        this.setExplosionAnimation(ig);

        return ig;
    }

    public MHImageGroup loadBananacuda()
    {
        final MHImageGroup ig = new MHImageGroup();
        final Image baseR = d.loadImage(IMAGE_DIR + "Char-Bananacuda-R.gif");
        final Image baseL = d.loadImage(IMAGE_DIR + "Char-Bananacuda-L.gif");

        ig.addSequence(CSFishBase.ACTION_SWIM_RIGHT);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, baseR, 4);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, IMAGE_DIR + "Char-Bananacuda-R+1.gif", 4);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, baseR, 4);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, IMAGE_DIR + "Char-Bananacuda-R-1.gif", 4);

        ig.addSequence(CSFishBase.ACTION_SWIM_LEFT);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, baseL, 4);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, IMAGE_DIR + "Char-Bananacuda-L+1.gif", 4);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, baseL, 4);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, IMAGE_DIR + "Char-Bananacuda-L-1.gif", 4);

        ig.addSequence(CSFishBase.ACTION_BITE_RIGHT);
        ig.addFrame(CSFishBase.ACTION_BITE_RIGHT, IMAGE_DIR + "Char-Bananacuda-BiteR.gif", 12);
        ig.addFrame(CSFishBase.ACTION_BITE_RIGHT, baseR, 4);

        ig.addSequence(CSFishBase.ACTION_BITE_LEFT);
        ig.addFrame(CSFishBase.ACTION_BITE_LEFT, IMAGE_DIR + "Char-Bananacuda-BiteL.gif", 12);
        ig.addFrame(CSFishBase.ACTION_BITE_LEFT, baseL, 4);

        this.setExplosionAnimation(ig);

        return ig;
    }

    public MHImageGroup loadSherbetCrab()
    {
        final MHImageGroup ig = new MHImageGroup();
        final Image baseR = d.loadImage(IMAGE_DIR + "Char-SherbetCrab-R.gif");
        final Image baseL = d.loadImage(IMAGE_DIR + "Char-SherbetCrab-L.gif");

        ig.addSequence(CSFishBase.ACTION_SWIM_RIGHT);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, baseR, 4);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, IMAGE_DIR + "Char-SherbetCrab-R-1.gif", 4);

        ig.addSequence(CSFishBase.ACTION_SWIM_LEFT);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, baseL, 4);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, IMAGE_DIR + "Char-SherbetCrab-L-1.gif", 4);

        ig.addSequence(CSFishBase.ACTION_BITE_RIGHT);
        ig.addFrame(CSFishBase.ACTION_BITE_RIGHT, IMAGE_DIR + "Char-SherbetCrab-R.gif", 12);
        ig.addFrame(CSFishBase.ACTION_BITE_RIGHT, baseR, 4);

        ig.addSequence(CSFishBase.ACTION_BITE_LEFT);
        ig.addFrame(CSFishBase.ACTION_BITE_LEFT, IMAGE_DIR + "Char-SherbetCrab-L.gif", 12);
        ig.addFrame(CSFishBase.ACTION_BITE_LEFT, baseL, 4);

        this.setExplosionAnimation(ig);

        return ig;
    }

    public MHImageGroup loadPeebeeJellyfish()
    {
        final MHImageGroup ig = new MHImageGroup();
        final Image base0 = d.loadImage(IMAGE_DIR + "Char-PeebeeJellyfish-0.gif");
        final Image base1 = d.loadImage(IMAGE_DIR + "Char-PeebeeJellyfish-1.gif");

        ig.addSequence(CSFishBase.ACTION_SWIM_RIGHT);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, base0, 2);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, base1, 2);

        ig.addSequence(CSFishBase.ACTION_SWIM_LEFT);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, base0, 2);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, base1, 2);

        ig.addSequence(CSFishBase.ACTION_BITE_RIGHT);
        ig.addFrame(CSFishBase.ACTION_BITE_RIGHT, base0, 2);
        ig.addFrame(CSFishBase.ACTION_BITE_RIGHT, base1, 2);

        ig.addSequence(CSFishBase.ACTION_BITE_LEFT);
        ig.addFrame(CSFishBase.ACTION_BITE_LEFT, base0, 2);
        ig.addFrame(CSFishBase.ACTION_BITE_LEFT, base1, 2);

        this.setExplosionAnimation(ig);

        return ig;
    }

    public MHImageGroup loadSeaChicken()
    {
        final MHImageGroup ig = new MHImageGroup();
        final Image base0 = d.loadImage(IMAGE_DIR + "Char-SeaChicken-0.gif");
        final Image base1 = d.loadImage(IMAGE_DIR + "Char-SeaChicken-1.gif");

        ig.addSequence(CSFishBase.ACTION_SWIM_RIGHT);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, base0, 2);
        ig.addFrame(CSFishBase.ACTION_SWIM_RIGHT, base1, 2);

        ig.addSequence(CSFishBase.ACTION_SWIM_LEFT);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, base0, 2);
        ig.addFrame(CSFishBase.ACTION_SWIM_LEFT, base1, 2);

        ig.addSequence(CSFishBase.ACTION_BITE_RIGHT);
        ig.addFrame(CSFishBase.ACTION_BITE_RIGHT, base0, 2);
        ig.addFrame(CSFishBase.ACTION_BITE_RIGHT, base1, 2);

        ig.addSequence(CSFishBase.ACTION_BITE_LEFT);
        ig.addFrame(CSFishBase.ACTION_BITE_LEFT, base0, 2);
        ig.addFrame(CSFishBase.ACTION_BITE_LEFT, base1, 2);

        this.setExplosionAnimation(ig);

        return ig;
    }

    public MHImageGroup loadFishingBoat()
    {
        final MHImageGroup ig = new MHImageGroup();
        final Image[] frames = new Image[8];

        for (int i = 0; i < frames.length; i++)
            frames[i] = d.loadImage(IMAGE_DIR + "Boat" + i + ".gif");

        ig.addSequence(0);
        ig.addFrame(0, frames[0], 2);
        ig.addFrame(0, frames[1], 2);
        ig.addFrame(0, frames[2], 2);
        ig.addFrame(0, frames[3], 2);
        ig.addFrame(0, frames[4], 2);
        ig.addFrame(0, frames[5], 2);
        ig.addFrame(0, frames[6], 2);
        ig.addFrame(0, frames[7], 2);
        ig.addFrame(0, frames[6], 2);
        ig.addFrame(0, frames[5], 2);
        ig.addFrame(0, frames[4], 2);
        ig.addFrame(0, frames[3], 2);
        ig.addFrame(0, frames[2], 2);
        ig.addFrame(0, frames[1], 2);

        return ig;
    }

}

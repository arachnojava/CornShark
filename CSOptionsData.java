

public class CSOptionsData
{
    public static final int HEALTH_BAR_AUTO = 0;
    public static final int HEALTH_BAR_ON   = 1;
    public static final int HEALTH_BAR_OFF  = 2;


    private boolean soundOn = true;
    private boolean musicOn = true;
    private int healthBarOption = HEALTH_BAR_AUTO;
    private CSSkillLevel skillLevel = CSSkillLevel.NORMAL;

    private static CSOptionsData instance;

    private CSOptionsData()
    {
        setSoundOn(true);
        setMusicOn(true);
        setHealthBarOption(HEALTH_BAR_AUTO);
        setSkillLevel(CSSkillLevel.NORMAL);
    }

    public static CSOptionsData getInstance()
    {
        if (instance == null)
            instance = new CSOptionsData();

        return instance;
    }

    /**
     * @return the soundOn
     */
    public boolean isSoundOn()
    {
        return soundOn;
    }

    /**
     * @param soundOn the soundOn to set
     */
    public void setSoundOn(final boolean soundOn)
    {
        this.soundOn = soundOn;
    }

    /**
     * @return the musicOn
     */
    public boolean isMusicOn()
    {
        return musicOn;
    }

    /**
     * @param musicOn the musicOn to set
     */
    public void setMusicOn(final boolean musicOn)
    {
        this.musicOn = musicOn;

        if (!musicOn)
            CSDataModel.getInstance().stopMusic();
    }

    /**
     * @return the skillLevel
     */
    public CSSkillLevel getSkillLevel()
    {
        return skillLevel;
    }

    /**
     * @param skillLevel the skillLevel to set
     */
    public void setSkillLevel(final CSSkillLevel skillLevel)
    {
        this.skillLevel = skillLevel;
    }

    public int getHealthBarOption()
    {
        return healthBarOption;
    }

    public void setHealthBarOption(final int healthBarOption)
    {
        this.healthBarOption = healthBarOption;
    }

}

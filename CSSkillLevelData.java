
public class CSSkillLevelData
{
	private CSSkillLevel skillLevel;
	private double predatorOdds = 0.2;
	private int numFish = 8;
	private int sensorRangeSize = 100;


	/****************************************************************
	 *
	 */
	public CSSkillLevelData(final CSSkillLevel skillLvl)
	{
		setSkillLevel(skillLvl);
	}


	/****************************************************************
	 *
	 */
	public CSSkillLevel getSkillLevel()
	{
		return skillLevel;
	}


	/****************************************************************
	 *
	 */
	public void setSkillLevel(final CSSkillLevel skillLevel)
	{
		this.skillLevel = skillLevel;

		switch(skillLevel)
		{
			case EASY:
				predatorOdds = 0.2;
				sensorRangeSize = 300;
				break;
			case NORMAL:
				predatorOdds = 0.4;
                sensorRangeSize = 600;
				break;
			case HARD:
				predatorOdds = 0.5;
                sensorRangeSize = 1200;
				break;
		}
	}


	/****************************************************************
	 *
	 */
	public double getPredatorOdds()
	{
		return predatorOdds;
	}


	/****************************************************************
	 *
	 */
	public int getNumFish()
	{
		return numFish;
	}


	/****************************************************************
	 *
	 */
	public void setNumFish(final int numFish)
	{
		this.numFish = numFish;
	}


    /**
     * @return the sensorRangeSize
     */
    public int getSensorRangeSize()
    {
        return sensorRangeSize;
    }


    /**
     * @param sensorRangeSize the sensorRangeSize to set
     */
    public void setSensorRangeSize(final int sensorRangeSize)
    {
        this.sensorRangeSize = sensorRangeSize;
    }


    /**
     * @param predatorOdds the predatorOdds to set
     */
    public void setPredatorOdds(final double predatorOdds)
    {
        this.predatorOdds = predatorOdds;
    }

}

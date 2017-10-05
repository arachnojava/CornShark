
public enum CSSkillLevel
{
    EASY,
    NORMAL,
    HARD;

    @Override
    public String toString()
    {
        if (this == EASY) return "Easy";
        if (this == HARD) return "Hard";

        return "Normal";
    }
}

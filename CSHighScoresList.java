import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

public class CSHighScoresList
{
    public static final int MAX_SCORES = 10;
    private static final String FILE_NAME = "HISCORES.DAT";

    private final CSHighScoreRecord[] highList;
    private ObjectOutputStream output;
    private ObjectInputStream input;


    public CSHighScoresList()
    {
        highList = new CSHighScoreRecord[MAX_SCORES];

        // if high scores file does not exist, create one

        readScoresFile();
    }


    public CSHighScoreRecord getRecord(final int recNum)
    {
        return highList[recNum];
    }


    public boolean addToList(final String playerName, final long playerScore, final String skillLvl)
    {
        boolean added = false;

        sort();

        if (highList[highList.length-1].score < playerScore)
        {
            added = false;
        }
        else
        {
            highList[highList.length-1].name = playerName;
            highList[highList.length-1].score = playerScore;
            highList[highList.length-1].skillLevel = skillLvl;
            sort();
            saveScoresFile();
            added = true;
        }

        return added;
    }


    private void readScoresFile()
    {
        // Create a new blank list of high score records
        for (int i = 0; i < highList.length; i++)
            highList[i] = new CSHighScoreRecord();

        // Open file
        try
        {
            input = new ObjectInputStream(new FileInputStream(FILE_NAME));

            // If file exists, read scores into list
            if (input != null)
            {
                for (int i = 0; i < highList.length; i++)
                {
                    try
                    {
                        highList[i] = (CSHighScoreRecord) input.readObject();
                    }
                    catch(final ClassNotFoundException e)
                    {}
                }

                input.close();
            }

            // Sort highList
            sort();
        }
        catch (final IOException ioe)
        {
            System.out.println("ERROR:  Cannot open " + FILE_NAME + " for reading.");
        }
    }


    public long getLowScore()
    {
        //sort();
        return highList[highList.length-1].score;
    }


    public void print()
    {
        for (final CSHighScoreRecord element : highList)
            System.out.println(element.toString());
    }



    private void sort()
    {
        for (int pass = highList.length; pass > 0; pass--)
        {
            for (int element = highList.length-1; element > 0; element--)
            {
                if (highList[element].score < highList[element-1].score)
                {
                    swap(highList, element, element-1);
                }
            }
        }
    }


    private void swap(final CSHighScoreRecord list[], final int a, final int b)
    {
        final CSHighScoreRecord temp = list[a];
        list[a] = list[b];
        list[b] = temp;
    }


    private void saveScoresFile()
    {

        try
        {
            output = new ObjectOutputStream(new FileOutputStream(FILE_NAME));

            for (int i = 0; i < MAX_SCORES; i++)
            {
                output.writeObject(highList[i]);
            }

            output.close();
        }
        catch (final IOException ioe)
        {
            System.err.println("ERROR:  Cannot save " + FILE_NAME);
            JOptionPane.showMessageDialog(null,
                                          "ERROR:  Cannot save " + FILE_NAME,
                                          "I/O Exception",
                                          JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void main(final String args[])
    {
        final CSHighScoresList list = new CSHighScoresList();

        //list.addToList("Dude", 407320, "Hard");
        //list.addToList("Man", 1034224, "Normal");
        //list.addToList("I. Suck", 6253423, "Easy");

        list.print();

        //list.saveScoresFile();
    }

}



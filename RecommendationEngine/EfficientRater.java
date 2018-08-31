
/**
 * Write a description of class Rater here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class EfficientRater implements Rater
{
    private String myID;
    private Map<String, Rating> myRatings;

    public EfficientRater(String id)
    {
        myID = id;
        myRatings = new HashMap<>();
    }

    public void addRating(String item, double rating)
    {
        myRatings.put(item, new Rating(item,rating));
    }

    public boolean hasRating(String item)
    {
        return myRatings.containsKey(item);
    }

    public String getID()
    {
        return myID;
    }

    /**
     * @returns the rating if it exists, or -1 otherwise.
     */
    public double getRating(String item)
    {
        if (hasRating(item))
        {
            return myRatings.get(item).getValue();
        }
        
        return -1;
    }

    public int numRatings()
    {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated()
    {
        assert myRatings != null;
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Rating> entry : myRatings.entrySet()){
            list.add(entry
                .getValue()
                .getItem());
        }
        
        return list;
    }
}

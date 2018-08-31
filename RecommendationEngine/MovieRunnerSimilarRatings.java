import java.util.*;

/**
 * Write a description of MovieRunnerSimilarRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MovieRunnerSimilarRatings
{
    private final boolean TEST = true;
    
    public void printAverageRatings(int minimumRaters)
    {
        MovieDatabase.initialize(TEST);
        FourthRatings fourthRatings = new FourthRatings(TEST);
        
        System.out.println("======================================");
        
        System.out.println("Number of raters: " + RaterDatabase.size());
        
        System.out.println("__________________");
        ArrayList<Rating> avgRatings = fourthRatings.getAverageRatings(minimumRaters);
        avgRatings.sort(new RatingComparator());
        System.out.println("Found this many movies: " + avgRatings.size());
        for (Rating rating : avgRatings)
        {
            System.out.println(rating.getValue() + "\t" + MovieDatabase.getTitle(rating.getItem()));
        }
    }
    
    private int dotProduct(Rater me, Rater r)
    {
        int sum = 0;
        
        ArrayList<String> items = me.getItemsRated();
        for (String item : items)
        {
            if (r.hasRating(item))
            {
                sum += (me.getRating(item) - 5) * (r.getRating(item) - 5);
            }
        }
        
        return sum;
    }
    
    private ArrayList<Rating> getSimilarities(String id)
    {
        ArrayList<Rating> similarRatings = new ArrayList<>();
        Rater me = RaterDatabase.getRater(id);
        
        for (Rater r : RaterDatabase.getRaters())
        {
            if (r.equals(me)) continue;
            
            int dot = dotProduct(me, r);
            if (dot > 0)
            {
                similarRatings.add(new Rating(r.getID(), dot));
            }
        }
        
        Collections.sort(similarRatings, Collections.reverseOrder());
        return similarRatings;
    }
    
    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimumRaters)
    {
        return getSimilarRatingsByFilter(id, numSimilarRaters, minimumRaters, new TrueFilter());
    }
    
    public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters, int minimumRaters, Filter filterCriteria)
    {
        ArrayList<Rating> similarRatings = new ArrayList<>();
        
        ArrayList<Rating> similars = getSimilarities(id);
        
        for (String movieName : MovieDatabase.filterBy(new TrueFilter()))
        {
            double movieScore = 0;
            int numRaters = 0;
            
            for (int i = 0; i < numSimilarRaters; i++)
            {
                String similarId = similars.get(i).getItem();
                double similarWeight = similars.get(i).getValue();
                
                if (RaterDatabase.getRater(similarId).hasRating(movieName))
                {
                    numRaters++;
                    movieScore += (RaterDatabase.getRater(similarId).getRating(movieName) * similarWeight);
                }
            }
            
            movieScore /= numRaters;

            if (numRaters >= numSimilarRaters)
            {
                similarRatings.add(new Rating(movieName, movieScore));
            }
        }
        
        Collections.sort(similarRatings, Collections.reverseOrder());
        return similarRatings;
    }
    
    // Compares two Ratings by their values.
    class RatingComparator implements Comparator<Rating>
    {
        @Override
        public int compare(Rating a, Rating b)
        {
             return Double.compare(a.getValue(), b.getValue());
        }
    }
}

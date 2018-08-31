import java.util.*;

/**
 * Write a description of MovieRunnerWithFilters here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MovieRunnerWithFilters
{
    private static final boolean TEST = false;
    
    public void printAverageRatings(int minimumRaters)
    {
        MovieDatabase.initialize(TEST);
        ThirdRatings thirdRatings = new ThirdRatings(TEST);
        
        System.out.println("======================================");
        
        System.out.println("Number of raters: " + thirdRatings.getRaterSize());
        
        System.out.println("__________________");
        ArrayList<Rating> avgRatings = thirdRatings.getAverageRatings(minimumRaters);
        avgRatings.sort(new RatingComparator());
        System.out.println("Found this many movies: " + avgRatings.size());
        for (Rating rating : avgRatings)
        {
            System.out.println(rating.getValue() + "\t" + MovieDatabase.getTitle(rating.getItem()));
        }
    }
    
    public void printAverageRatingsByFilter(int minimumRaters, Filter filter)
    {
        MovieDatabase.initialize(TEST);
        ThirdRatings secondRatings = new ThirdRatings(TEST);
        
        System.out.println("===============FILTER===============");
        
        System.out.println("Number of raters: " + secondRatings.getRaterSize());
        
        System.out.println("__________________");
        ArrayList<Rating> avgRatings = secondRatings.getAverageRatingsByFilter(minimumRaters, filter);
        avgRatings.sort(new RatingComparator());
        System.out.println("Found this many movies: " + avgRatings.size());
        for (Rating rating : avgRatings)
        {
            System.out.println("--\n" + 
                                MovieDatabase.getTitle(rating.getItem()) + "\n" + 
                                rating.getValue() + "\t" + MovieDatabase.getYear(rating.getItem()) + "\t" + MovieDatabase.getGenres(rating.getItem()) + "\t" + MovieDatabase.getMinutes(rating.getItem()) + "\t" + MovieDatabase.getDirector(rating.getItem())
                                );
        }
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

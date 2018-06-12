import java.util.*;

/**
 * Write a description of MovieRunnerAverage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MovieRunnerAverage
{
    public void printAverageRatings()
    {
        SecondRatings secondRatings = new SecondRatings();
        
        System.out.println("======================================");
        
        System.out.println("Number of movies: " + secondRatings.getMovieSize());
        System.out.println("Number of raters: " + secondRatings.getRaterSize());
        
        System.out.println("__________________");
        ArrayList<Rating> avgRatings = secondRatings.getAverageRatings(12);
        avgRatings.sort(new RatingComparator());
        for (Rating rating : avgRatings)
        {
            System.out.println(rating.getValue() + "\t" + secondRatings.getTitle(rating.getItem()));
        }
        
    }
    
    public void getAverageRatingOneMovie()
    {
        SecondRatings secondRatings = new SecondRatings();
        
        System.out.println(secondRatings.getAverageById(secondRatings.getId("Vacation"), 1));
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

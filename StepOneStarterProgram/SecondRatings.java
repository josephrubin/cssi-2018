
/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class SecondRatings
{
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    
    public SecondRatings()
    {
        this("ratedmoviesfull.csv", "ratings.csv");
    }
    
    public SecondRatings(boolean test)
    {
        this("ratedmovies_short.csv", "ratings_short.csv");
    }
    
    private SecondRatings(String moviefile, String ratingsfile)
    {
        FirstRatings firstRatings = new FirstRatings();
        this.myMovies = firstRatings.loadMovies(moviefile);
        this.myRaters = firstRatings.loadRaters(ratingsfile);
    }
    
    public int getMovieSize()
    {
        return myMovies.size();
    }
    
    public int getRaterSize()
    {
        return myRaters.size();
    }
    
    public ArrayList<Rating> getAverageRatings(int minimumRaters)
    {
        ArrayList<Rating> avgRatings = new ArrayList<>();
        for (Movie movie : myMovies)
        {
            double avg = getAverageById(movie.getID(), minimumRaters);
            if (avg > -0.5)
            {
                Rating rating = new Rating(movie.getID(), avg);
                avgRatings.add(rating);
            }
        }
        return avgRatings;
    }
    
    public String getId(String title)
    {
        for (Movie movie : myMovies)
        {
            if (movie.getTitle().equals(title))
            {
                return movie.getID();
            }
        }
        return "NO SUCH MOVIE TITLE";
    }
    
    public String getTitle(String id)
    {
        for (Movie movie : myMovies)
        {
            if (movie.getID().equals(id.trim()))
            {
                return movie.getTitle();
            }
        }
        return "Movie with supplied id was not found.";
    }
    
    // Returns the avg, or -1.0 if there are fewer than the minimum number of Raters
    // for this movie id.
    public double getAverageById(String id, int minimumRaters)
    {
        id = id.trim();
        
        int raterCount = 0;
        int totalRating = 0;
        for (Rater rater : myRaters)
        {
            if (rater.hasRating(id))
            {
                raterCount++;
                totalRating += rater.getRating(id);
            }
        }
        
        return raterCount >= minimumRaters ? ((double) totalRating) / raterCount : -1.0;
    }
}

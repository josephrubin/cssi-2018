import java.util.*;

public class ThirdRatings
{
    private ArrayList<Rater> myRaters;
    
    public ThirdRatings(boolean test)
    {
        String ratingsFile;
        if (test)
        {
            ratingsFile = "ratings_test.csv";
        }
        else
        {
            ratingsFile = "ratings.csv";
        }
        
        FirstRatings firstRatings = new FirstRatings();
        this.myRaters = firstRatings.loadRaters(ratingsFile);
    }
    
    public int getRaterSize()
    {
        return myRaters.size();
    }
    
    public ArrayList<Rating> getAverageRatings(int minimumRaters)
    {
        return getAverageRatingsByFilter(minimumRaters, new TrueFilter());
    }
    
    public ArrayList<Rating> getAverageRatingsByFilter(int minimumRaters, Filter filter)
    {
        ArrayList<Rating> avgRatings = new ArrayList<>();
        for (String id : MovieDatabase.filterBy(filter))
        {
            Movie movie = MovieDatabase.getMovie(id);
            double avg = getAverageById(movie.getID(), minimumRaters);
            if (avg > -0.5)
            {
                Rating rating = new Rating(movie.getID(), avg);
                avgRatings.add(rating);
            }
        }
        return avgRatings;
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

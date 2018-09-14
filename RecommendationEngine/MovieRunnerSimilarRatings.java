import java.util.*;

/**
 * Write a description of MovieRunnerSimilarRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MovieRunnerSimilarRatings
{
    private final boolean TEST = false;
    
    public void printSimilarRatings()
    {
        MovieDatabase.initialize(TEST);
        RaterDatabase.initialize(TEST);
        FourthRatings fourthRatings = new FourthRatings(TEST);
        
        ArrayList<Rating> similarRatings = getSimilarRatings("71", 20, 5);
        for (Rating rating : similarRatings)
        {
            System.out.println(MovieDatabase.getMovie(rating.getItem()).getTitle() + " " + rating.getValue());
        }
    }
    
    public void printSimilarRatingsByGenre()
    {
        MovieDatabase.initialize(TEST);
        RaterDatabase.initialize(TEST);
        FourthRatings fourthRatings = new FourthRatings(TEST);
        
        ArrayList<Rating> similarRatings = getSimilarRatingsByFilter("964", 20, 5,
            new GenreFilter("Mystery"));
        for (Rating rating : similarRatings)
        {
            System.out.println(MovieDatabase.getMovie(rating.getItem()).getTitle() + " " + rating.getValue()
            + "\n" + MovieDatabase.getMovie(rating.getItem()).getGenres() + "\n");
        }
    }
    
    public void printSimilarRatingsByDirector()
    {
        MovieDatabase.initialize(TEST);
        RaterDatabase.initialize(TEST);
        FourthRatings fourthRatings = new FourthRatings(TEST);
        
        ArrayList<Rating> similarRatings = getSimilarRatingsByFilter("120", 10, 2,
            new DirectorsFilter("Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh"));
        for (Rating rating : similarRatings)
        {
            System.out.println(MovieDatabase.getMovie(rating.getItem()).getTitle() + " " + rating.getValue()
            + "\n" + MovieDatabase.getMovie(rating.getItem()).getDirector() + "\n");
        }
    }
    
    public void printSimilarRatingsByGenreAndMinutes()
    {
        MovieDatabase.initialize(TEST);
        RaterDatabase.initialize(TEST);
        FourthRatings fourthRatings = new FourthRatings(TEST);
        
        AllFilters f = new AllFilters();
        f.addFilter(new GenreFilter("Drama"));
        f.addFilter(new MinutesFilter(80, 160));
        
        ArrayList<Rating> similarRatings = getSimilarRatingsByFilter("168", 10, 3,
            f);
        for (Rating rating : similarRatings)
        {
            System.out.println(MovieDatabase.getMovie(rating.getItem()).getTitle() + " " + MovieDatabase.getMovie(rating.getItem()).getMinutes() + " " + rating.getValue()
            + "\n" + MovieDatabase.getMovie(rating.getItem()).getGenres() + "\n");
        }
    }
    
    public void printSimilarRatingsByYearAndMinutes()
    {
        MovieDatabase.initialize(TEST);
        RaterDatabase.initialize(TEST);
        FourthRatings fourthRatings = new FourthRatings(TEST);
        
        AllFilters f = new AllFilters();
        f.addFilter(new YearAfterFilter(1975));
        f.addFilter(new MinutesFilter(70, 200));
        
        ArrayList<Rating> similarRatings = getSimilarRatingsByFilter("314", 10, 5,
            f);
        for (Rating rating : similarRatings)
        {
            System.out.println(MovieDatabase.getMovie(rating.getItem()).getTitle() + " " + MovieDatabase.getMovie(rating.getItem()).getYear() + " " + MovieDatabase.getMovie(rating.getItem()).getMinutes() + " " + rating.getValue() + "\n");
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
        assert me != null;
        
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
        //System.out.println(similars);
        
        for (String movieName : MovieDatabase.filterBy(filterCriteria))
        {
            double movieScore = 0;
            int numRaters = 0;
            
            for (int i = 0; i < numSimilarRaters && i < similars.size(); i++)
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

            if (numRaters >= minimumRaters)
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

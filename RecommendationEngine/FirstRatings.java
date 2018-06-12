import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;

/**
 * Write a description of FirstRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FirstRatings
{
    public static ArrayList<Movie> loadMovies(String fileName)
    {
        ArrayList<Movie> movies = new ArrayList<>();
        
        // Load the file...
        FileResource file = new FileResource("data/" + fileName);
        // ...and create a CSVParser from it.
        CSVParser parser = file.getCSVParser();
        
        // Iterate through every line of the CSV.
        for (CSVRecord record : parser)
        {
            // Collect the record data.
            String id       = record.get("id");
            String title    = record.get("title");
            String year     = record.get("year");
            String country  = record.get("country");
            String genre    = record.get("genre");
            String director = record.get("director");
            int    minutes  = Integer.parseInt(record.get("minutes").trim());
            String poster   = record.get("poster");
            
            // Make the new Movie and save it.
            Movie movie = new Movie(id, title, year, genre, director, country, poster, minutes);
            movies.add(movie);
        }
        
        return movies;
    }
    
    public static ArrayList<Rater> loadRaters(String fileName)
    {
        Map<String, Rater> raters = new HashMap<>();
        
        // Load the file...
        FileResource file = new FileResource("data/" + fileName);
        // ...and create a CSVParser from it.
        CSVParser parser = file.getCSVParser();
        
        // Iterate through every line of the CSV.
        for (CSVRecord record : parser)
        {
            // Collect the record data.
            String raterId = record.get("rater_id").trim();
            String movieId = record.get("movie_id").trim();
            double rating  = Double.parseDouble(record.get("rating").trim());
            
            // Make a new Rater if we did not already.
            if (!raters.containsKey(raterId))
            {
                Rater rater = new Rater(raterId);
                raters.put(raterId, rater);
            }
            
            raters.get(raterId).addRating(movieId, rating);
        }
        
        return new ArrayList<Rater>(raters.values());
    }
    
    public static void testLoadMovies()
    {
        ArrayList<Movie> movies = loadMovies("ratedmoviesfull.csv");
        
        System.out.println("Number of Movies loaded: " + movies.size());
        //System.out.println("\n" + movies);
        
        // Comedies check.
        int comedies = 0;
        for (Movie movie : movies)
        {
            String[] genres = movie.getGenres().split("\\s*,\\s*");
            for (String genre : genres)
            {
                if (genre.equalsIgnoreCase("comedy"))
                {
                    comedies++;
                }
            }
        }
        System.out.println("There are " + comedies + " comedies.");
        
        // Length check.
        int longMovies = 0;
        for (Movie movie : movies)
        {
            int minutes = movie.getMinutes();
            if (minutes > 150)
            {
                longMovies++;
            }
        }
        System.out.println("There are " + longMovies + " movies longer than 150 minutes.");
        
        HashMap<String, Integer> moviesByDirector = new HashMap<>();
        for (Movie movie : movies)
        {
            String[] directors = movie.getDirector().split("\\s*,\\s*");
            for (String director : directors)
            {
                String d = director.trim();
                if (moviesByDirector.containsKey(d))
                {
                    moviesByDirector.put(d, moviesByDirector.get(d) + 1);
                }
                else
                {
                    moviesByDirector.put(d, 1);
                }
            }
        }
        System.out.println("By director: " + moviesByDirector);
        ArrayList<Integer> directeds = new ArrayList<>(moviesByDirector.values());
        directeds.sort(null);
        System.out.println("Sorted: " + directeds);
        int maxD = directeds.get(directeds.size() - 1);
        System.out.println("Max directed: " + maxD + " movies, by director: ");
        for (Map.Entry<String, Integer> entry : moviesByDirector.entrySet())
        {
            if (entry.getValue() == maxD)
            {
                System.out.println(entry.getKey());
            }
        }
    }
    
    public static void testLoadRaters()
    {
        ArrayList<Rater> raters = loadRaters("ratings.csv");
        System.out.println("=========");
        System.out.println("Number of distinct Raters: " + raters.size());
        
        /*
        for (Rater rater : raters)
        {
            System.out.println(rater.getID() + " " + rater.numRatings());
            StringBuilder pairs = new StringBuilder();
            for (String id : rater.getItemsRated())
            {
                pairs.append(id);
                pairs.append(" ");
                pairs.append(rater.getRating(id));
                pairs.append(" | ");
            }
            pairs.append("\n");
            System.out.println(pairs);
        }
        */
       
        
        String givenId = "193";
        System.out.println("Rater " + givenId + " has " + numRatings(raters, givenId) + " ratings.");
        
        // Find Raters with most ratings.
        Map<String, Integer> rates = new HashMap<>();
        for (Rater rater : raters)
        {
            rates.put(rater.getID(), rater.numRatings());
        }
        ArrayList<Integer> numRatings = new ArrayList<>(rates.values());
        numRatings.sort(null);
        
        if (numRatings.size() > 0)
        {
            int maxRatings = numRatings.get(numRatings.size() - 1);
            System.out.println("The maximum number of ratings by any Rater is " + maxRatings);
            System.out.println("The following people are those max Raters:");
            
            for (Rater rater : raters)
            {
                if (rater.numRatings() >= maxRatings)
                {
                    System.out.println(rater.getID());
                }
            }
        }
        
        System.out.println("----------------------~~---------------------");
        
        Map<String, Integer> moviesByRatings = moviesByRatings(raters);
        String movieId = "1798709";
        System.out.println("Movie " + movieId + " was rated by " + moviesByRatings.get(movieId) + " raters.");
        System.out.println("In total, there are " + moviesByRatings.size() + " movies rated");
    }
    
    // Given an id, find the number of ratings, or -1 if that rater does not exist.
    private static int numRatings(Collection<Rater> raters, String givenId)
    {
        for (Rater rater : raters)
        {
            if (rater.getID().equals(givenId))
            {
                return rater.numRatings();
            }
        }
        return -1;
    }
    
    // Movies by number of ratings.
    private static Map<String, Integer> moviesByRatings(Collection<Rater> raters)
    {
        Map<String, Integer> moviesByRatings = new HashMap<>();
        for (Rater rater : raters)
        {
            for (String movieId : rater.getItemsRated())
            {
                if (!moviesByRatings.containsKey(movieId))
                {
                    moviesByRatings.put(movieId, 1);
                }
                else
                {
                    moviesByRatings.put(movieId, moviesByRatings.get(movieId) + 1);
                }
            }
        }
        return moviesByRatings;
    }
    
}

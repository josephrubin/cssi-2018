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
        ArrayList<Movie> movies = loadMovies("ratedmovies_short.csv");
        
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
    }
    
    public static void testLoadRaters()
    {
        ArrayList<Rater> raters = loadRaters("ratings_short.csv");
        
        System.out.println("Number of distinct Raters: " + raters.size());
        
        for (Rater rater : raters)
        {
            System.out.println(rater.getID() + " " + rater.numRatings());
            System.out.println(rater.getItemsRated() + "\n");
        }
    }
}

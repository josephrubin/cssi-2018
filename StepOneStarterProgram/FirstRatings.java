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
    
    public static void testLoadMovies()
    {
        ArrayList<Movie> tst = loadMovies("ratedmoviesfull.csv");
        
        System.out.println("Number of Movies loaded: " + tst.size());
        System.out.println("\n" + tst);
    }
}

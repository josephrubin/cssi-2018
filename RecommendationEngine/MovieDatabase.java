import java.util.*;
import org.apache.commons.csv.*;
import edu.duke.FileResource;

public class MovieDatabase {
    private static HashMap<String, Movie> ourMovies;

    public static void initialize(String moviefile) {
        if (ourMovies == null) {
            ourMovies = new HashMap<String,Movie>();
            loadMovies("data/" + moviefile);
        }
    }

    public static void initialize(boolean test) {
        if (ourMovies == null)
        {
            if (test)
            {
                MovieDatabase.initialize("movies_test.csv");
            }
            else
            {
                MovieDatabase.initialize("movies.csv");
            }
        }
    }	

	
    private static void loadMovies(String filename) {
        FirstRatings fr = new FirstRatings();
        ArrayList<Movie> list = fr.loadMovies(filename);
        for (Movie m : list) {
            ourMovies.put(m.getID(), m);
        }
    }

    public static boolean containsId(String id) {
        return ourMovies.containsKey(id);
    }

    public static int getYear(String id) {
        return ourMovies.get(id).getYear();
    }

    public static String getGenres(String id) {
        return ourMovies.get(id).getGenres();
    }

    public static String getTitle(String id) {
        return ourMovies.get(id).getTitle();
    }

    public static Movie getMovie(String id) {
        return ourMovies.get(id);
    }

    public static String getPoster(String id) {
        return ourMovies.get(id).getPoster();
    }

    public static int getMinutes(String id) {
        return ourMovies.get(id).getMinutes();
    }

    public static String getCountry(String id) {
        return ourMovies.get(id).getCountry();
    }

    public static String getDirector(String id) {
        return ourMovies.get(id).getDirector();
    }

    public static int size() {
        return ourMovies.size();
    }

    public static ArrayList<String> filterBy(Filter f) {
        ArrayList<String> list = new ArrayList<String>();
        for(String id : ourMovies.keySet()) {
            if (f.satisfies(id)) {
                list.add(id);
            }
        }
        
        return list;
    }

}

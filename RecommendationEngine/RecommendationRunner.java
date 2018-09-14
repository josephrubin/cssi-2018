import java.util.*;

/**
 * Write a description of RecommendationRunner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RecommendationRunner implements Recommender
{
    @Override
    public ArrayList<String> getItemsToRate()
    {
        ArrayList<String> items = new ArrayList<>();
        
        ArrayList<String> all = MovieDatabase.filterBy(new TrueFilter());
        Random rng = new Random();
        
        for (int i = 0; i < 8; i++)
        {
            items.add(all.get(rng.nextInt(all.size())));
        }
        
        return items;
    }
    
    @Override
    public void printRecommendationsFor(String webRaterID)
    {
        System.out.print("<style>.content {width: 60% !important;}</style>");
        System.out.print("<br><h1>You may enjoy these titles!</h1>" +
                           "<br>");
                           
        ArrayList<Rating> similarRatings = new MovieRunnerSimilarRatings().getSimilarRatings(webRaterID, 20, 5);
        if (similarRatings.size() < 1)
        {
            System.out.println("Sorry, there are no recommendations at this time. Try again with a new set of movies to rate?");
            return;
        }
        
        int n = 0;
        
        for (Rating rating : similarRatings)
        {
            if (n >= 11)
            {
                break;
            }
            if (n == 0)
            {
                System.out.print("<h3 style='text-align:left'>");
            }
            System.out.print("<b>" + MovieDatabase.getMovie(rating.getItem()).getTitle() + "</b>");
            if (rating.getValue() > 30)
            {
                System.out.println(" (close match!)");
            }
            System.out.print("<br>");
            System.out.print(MovieDatabase.getMovie(rating.getItem()).getGenres());
            if (n == 0)
            {
                System.out.print("</h3>");
            }
            System.out.print("<br><br>");
            n++;
        }
        
        System.out.println("Thank you!");
    }
}

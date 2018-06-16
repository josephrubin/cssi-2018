public class DirectorsFilter implements Filter
{
    private String[] directors;
    
    public DirectorsFilter(String directors)
    {
        this.directors = directors.split(" *, *");
    }
    
    public boolean satisfies(String id)
    {
        String[] movieDirectors = MovieDatabase.getDirector(id).split(" *, *");
        
        for (int i = 0; i < movieDirectors.length; i++)
        {
            for (int j = 0; j < directors.length; j++)
            {
                if (movieDirectors[i].equals(directors[j]))
                {
                    return true;
                }
            }
        }
        
        return false;
    }
}

public class MinutesFilter implements Filter
{
    private int min;
    private int max;
    
    public MinutesFilter(int min, int max)
    {
        this.min = min;
        this.max = max;
    }
    
    public boolean satisfies(String id)
    {
        int mins = MovieDatabase.getMinutes(id);
        return mins >= min
                &&
               mins <= max;
    }
}

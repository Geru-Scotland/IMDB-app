package models;
import java.util.ArrayList;

public class Artist implements Comparable<Artist>, Entity {

    private ArrayList<Film> films;
    private int filmsNum;
    private double rating;
    private String name;

    public Artist(){
        films = new ArrayList<>();
    }

    public double getRating() {
        computeRating();
        return rating;
    }
    /**
     * Calcula y asigna el rating del intérprete en base al rating de sus películas
     */
    public void computeRating(){

        double filmsRating = 0.0;
        int votes = 0;
        for(Film film : films){
            if(film.getRating() != -1){
                filmsRating += film.getTotalRating();
                votes += film.getVotes();
            }
        }

        rating = filmsRating / votes;
    }

    /**
     * Añade una película al intérprete
     * @param pel Película a añadir
     * POST: El rating del intérprete NO se modifica en este momento */
    public void addFilm(Film pel){
        films.add(pel);
        filmsNum++;
    }

    public int getFilmsNum(){ return filmsNum; }

    public ArrayList<Film> getFilms(){
        return films;
    }

    public String getName() {
        return name;
    }

    @Override
    public void populateInfo(String info){
        name = info;
    }

    @Override
    public int compareTo(Artist o) {
        return getIdentifier().compareTo(o.getIdentifier());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Artist && ((Artist) o).getIdentifier().equals(getIdentifier());
    }

    @Override
    public String getIdentifier() {
        return name;
    }
}

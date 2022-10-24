package models;
import java.util.ArrayList;

public class Artist implements Comparable<Artist>, Entity {

    private ArrayList<Film> films;
    private double rating;
    private String name;

    public Artist(){
        films = new ArrayList<>();
    }

    public void setRating(double rat){
        this.rating = rat;
    }

    /**
     * Calcula y asigna el rating del intérprete en base al rating de sus películas
     */
    public void calcularRating(){

        double totalRating = 0.0;
        int totalVotos = 0;
        double total = 0.0;
        for (int i = 0; i < films.size(); i++){
            if (films.get(i).getRating() != -1){
                totalRating += films.get(i).getRating();
                totalVotos += films.get(i).getVotes();
            }
        }

        total = totalRating / totalVotos;
        this.setRating(total);
    }

    /**
     * Añade una película al intérprete
     * @param pel Película a añadir
     * POST: El rating del intérprete NO se modifica en este momento */
    public void addFilm(Film pel){
        films.add(pel);
    }

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

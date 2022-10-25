package entities;
import entities.models.Entity;

import java.util.ArrayList;

public class Artist implements Comparable<Artist>, Entity {

    private final ArrayList<Film> films;
    private int filmsNum;
    private double rating;
    private String name;

    public Artist(){
        films = new ArrayList<>();
    }

    public void computeRating(){

        double filmsRating = 0.0;
        int votes = 0;
        for(Film film : films){
            if(film.getRating(false) != -1){
                filmsRating += film.getRating(true);
                votes += film.getVotes();
            }
        }

        rating = filmsRating / votes;
    }

    public void addData(Object obj){
        if(!(obj instanceof Film))
            return;
        films.add((Film)obj);
        filmsNum++;
    }

    public int getFilmsNum(){ return filmsNum; }
    public ArrayList<Film> getFilms(){
        return films;
    }

    /**
     * Overrides
     */

    @Override
    public double getRating(boolean opt) {
        if(opt)
            computeRating();
        return rating;
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

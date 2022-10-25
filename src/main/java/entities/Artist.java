package entities;
import entities.models.Entity;
import exceptions.EmptyDataException;

import java.util.ArrayList;

public class Artist<T> implements Comparable<Artist<T>>, Entity<T> {

    private final ArrayList<T> films;
    private int filmsNum;
    private double rating;
    private String name;

    public Artist(){ films = new ArrayList<>(); }

    public void computeRating(){

        double filmsRating = 0.0;
        int votes = 0;

        for(Object film : films){
            if(((Film)film).getRating(false) != -1){
                filmsRating += ((Film)film).getRating(true);
                votes += ((Film)film).getVotes();
            }
        }

        rating = filmsRating / votes;
    }

    /**
     * Overrides
     */

    @Override
    public void populateInfo(String info){ name = info; }

    @Override
    public void addData(T obj){
        if(!(obj instanceof Film))
            return;
        films.add(obj);
        filmsNum++;
    }

    @Override
    public String getIdentifier() { return name; }

    @Override
    public int getDataNum(){ return filmsNum; }

    @Override
    public double getRating(boolean opt) {
        if(opt)
            computeRating();
        return rating;
    }

    @Override
    public ArrayList<T> getDataList() throws EmptyDataException {
        if(filmsNum == 0)
            throw new EmptyDataException("Este artista no pertenece a ninguna pelicula.");
        return films;
    }

    @Override
    public int compareTo(Artist o) { return getIdentifier().compareTo(o.getIdentifier()); }

    @Override
    public boolean equals(Object o) {
        return o instanceof Artist && ((Artist)o).getIdentifier().equals(getIdentifier());
    }
}

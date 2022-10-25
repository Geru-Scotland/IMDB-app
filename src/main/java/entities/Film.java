package entities;
import entities.models.Entity;
import exceptions.EmptyDataException;

import java.util.ArrayList;

/**
 * Clase que gestiona lo relacionado a las peliculas. Implementa Comparable para
 * poder comparar sus elementos e implementa a la interfaz Entity. La cual define
 * conceptos genéricos que las entidades que estamos tratando deben de adoptar.
 * @param <T> Tipo de los elementos correspondientes a la lista que
 *           nuestra clase Film almacenará (Artistas, pero podrían ser otros)
 */
public class Film<T> implements Comparable<Film<T>>, Entity<T> {

    private final ArrayList<T> casting;
    private String title;
    private int year;
    private double rating;
    private int votes;
    private int artistNum;

    public Film(){ casting = new ArrayList<>(); }

    public int getYear(){ return year; }
    public int getVotes() {return votes; }

    /**
     * Método para agregar un nuevo voto a una determinada pelicula y actualizar
     * su rating.
     * De manera adicional recalculará los ratings de aquellos artistas que
     * actúen en la misma.
     * @param score voto/puntuación.
     */
    public void addVote(float score) {
        votes++;
        if (rating == -1){
            votes++;
            rating = score;
        }else
            rating = (rating*(votes-1) + score)/votes;

        for(T entity : casting)
            ((Artist)entity).computeRating();
    }

    /**
     * Overrides
     */

    @Override
    public void populateInfo(String info){
        String[] elem = info.split("\\t");
        title = elem[0];
        year = Integer.parseInt(elem[1]);
        rating = Double.parseDouble(elem[2]);
        votes = Integer.parseInt(elem[3]);
    }

    @Override
    public void addData(T obj){
        if(!(obj instanceof Artist))
            return;
        casting.add(obj);
        artistNum++;
    }

    @Override
    public String getIdentifier() { return title; }

    @Override
    public int getDataNum() { return artistNum; }

    @Override
    public double getRating(boolean weighted) { return weighted ? rating * votes : rating; }

    @Override
    public ArrayList<T> getDataList() throws EmptyDataException{
        if(artistNum == 0)
            throw new EmptyDataException("Esta pelicula no contiene ningún artista.");
        return casting;
    }

    @Override
    public int compareTo(Film o) {
        if(o.getIdentifier().compareTo(title) > 0)
            return 1;
        return 0;
    }
}


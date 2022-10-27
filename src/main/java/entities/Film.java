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
public class Film<T extends Comparable<T>> extends Entity<T> implements Comparable<Film<?>> {

    private int year;
    private double rating;
    private int votes;

    public Film(){
        dataList = (ArrayList<T>) new ArrayList<Film<T>>(); }

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

        for(T entity : dataList)
            ((Artist)entity).computeRating();
    }

    /**
     * Overrides
     */

    @Override
    public void populateInfo(String info){
        String[] elem = info.split("\\t");
        identifier = elem[0];
        year = Integer.parseInt(elem[1]);
        rating = Double.parseDouble(elem[2]);
        votes = Integer.parseInt(elem[3]);
    }

    @Override
    public void addData(T obj){
        if(!(obj instanceof Artist))
            return;
        dataList.add(obj);
    }

    @Override
    public String getIdentifier() { return identifier; }

    @Override
    public int getDataNum() { return dataList.size(); }

    @Override
    public double getRating(boolean weighted) { return weighted ? rating * votes : rating; }

    @Override
    public ArrayList<T> getDataList() throws EmptyDataException{
        if(dataList.size() == 0)
            throw new EmptyDataException("Esta pelicula no contiene ningún artista.");
        return dataList;
    }

    @Override
    public int compareTo(Film o) {
        if(o.getIdentifier().compareTo(identifier) > 0)
            return 1;
        return 0;
    }
}


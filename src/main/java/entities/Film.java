package entities;
import entities.models.Entity;
import exceptions.EmptyDataException;

import java.util.ArrayList;

/**
 * Clase que gestiona lo relacionado a las peliculas. Implementa Comparable para
 * poder comparar sus elementos e implementa a la interfaz Entity. La cual define
 * conceptos gen�ricos que las entidades que estamos tratando deben de adoptar.
 * @param <T> Tipo de los elementos correspondientes a la lista que
 *           nuestra clase Film almacenar� (Artistas, pero podr�an ser otros)
 */
public class Film<T extends Comparable<T>> extends Entity<T> implements Comparable<Film<?>> {

    private int year;
    private double rating;
    private int votes;

    public Film(){}

    public int getYear(){ return year; }
    public int getVotes() {return votes; }

    /**
     * M�todo para agregar un nuevo voto a una determinada pelicula y actualizar
     * su rating.
     * De manera adicional recalcular� los ratings de aquellos artistas que
     * act�en en la misma.
     * @param score voto/puntuaci�n.
     */
    public void addVote(float score) {
        votes++;
        if (rating == -1){
            votes++;
            rating = score;
        }else
            rating = (rating*(votes-1) + score)/votes;

        ArrayList<Entity<T>> dataList = linealWrapper.getList();
        for(Entity<T> item: dataList)
            ((Artist<?>)item).computeRating();
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
        linealWrapper.add((Artist)obj);
    }

    @Override
    public String getIdentifier() { return identifier; }

    @Override
    public int getDataNum() { return linealWrapper.size(); }

    @Override
    public double getRating(boolean weighted) { return weighted ? rating * votes : rating; }

    @Override
    public ArrayList<Entity<T>> getDataList() throws EmptyDataException{
        if(linealWrapper.size() == 0)
            throw new EmptyDataException("Esta pelicula no contiene ning�n artista.");
        return linealWrapper.getList();
    }

    @Override
    public int compareTo(Film o) {
        if(o.getIdentifier().compareTo(identifier) > 0)
            return 1;
        return 0;
    }
}


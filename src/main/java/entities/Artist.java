package entities;
import entities.models.Entity;
import exceptions.EmptyDataException;
import templates.DataWrapper;

import java.util.ArrayList;

/**
 * Clase que gestiona lo relacionado a los artistas. Implementa Comparable para
 * poder comparar sus elementos e implementa a la interfaz Entity. La cual define
 * conceptos genéricos que las entidades que estamos tratando deben de adoptar.
 * @param <T> Tipo de los elementos correspondientes a la lista que
 *           nuestra clase Artist almacenará (Films, pero podrían ser otros)
 */
public class Artist<T extends Comparable<T>> extends Entity<T> implements Comparable<Artist<?>>{

    public Artist(){}

    public void computeRating(){

        double filmsRating = 0.0;
        int votes = 0;

        for(Object film : dataWrapper.getList()){
            if(((Film<?>)film).getRating(false) != -1){
                filmsRating += ((Film<?>)film).getRating(true);
                votes += ((Film<?>)film).getVotes();
            }
        }

        rating = filmsRating / votes;
    }

    public DataWrapper<?> getWrapper(){
        return dataWrapper;
    }

    /**
     * Overrides
     */

    @Override
    public void populateInfo(String info){ identifier = info; }

    @Override
    public void addData(T obj){
        if(!(obj instanceof Film))
            return;
        dataWrapper.add((Film)obj);
    }

    @Override
    public String getIdentifier() { return identifier; }

    @Override
    public int getDataNum(){ return dataWrapper.getList().size(); }

    @Override
    public double getRating(boolean opt) {
        if(opt)
            computeRating();
        return rating;
    }

    @Override
    public ArrayList<Entity<T>> getDataList() throws EmptyDataException {
        if(dataWrapper.size() == 0)
            throw new EmptyDataException("Este artista no pertenece a ninguna pelicula.");
        return dataWrapper.getList();
    }

    @Override
    public int compareTo(Artist o) { return getIdentifier().compareTo(o.getIdentifier()); }

    @Override
    public boolean equals(Object o) {
        return o instanceof Artist && ((Artist<?>)o).getIdentifier().equals(getIdentifier());
    }
}

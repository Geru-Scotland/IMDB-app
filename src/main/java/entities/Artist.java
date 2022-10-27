package entities;
import entities.models.Entity;
import exceptions.EmptyDataException;

import java.util.ArrayList;

/**
 * Clase que gestiona lo relacionado a los artistas. Implementa Comparable para
 * poder comparar sus elementos e implementa a la interfaz Entity. La cual define
 * conceptos gen�ricos que las entidades que estamos tratando deben de adoptar.
 * @param <T> Tipo de los elementos correspondientes a la lista que
 *           nuestra clase Artist almacenar� (Films, pero podr�an ser otros)
 */
public class Artist<T extends Comparable<T>> extends Entity<T> implements Comparable<Artist<?>>{

    public Artist(){ dataList = new ArrayList<>(); }

    public void computeRating(){

        double filmsRating = 0.0;
        int votes = 0;

        for(Object film : dataList){
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
    public void populateInfo(String info){ identifier = info; }

    @Override
    public void addData(T obj){
        if(!(obj instanceof Film))
            return;
        dataList.add(obj);
    }

    @Override
    public String getIdentifier() { return identifier; }

    @Override
    public int getDataNum(){ return dataList.size(); }

    @Override
    public double getRating(boolean opt) {
        if(opt)
            computeRating();
        return rating;
    }

    @Override
    public ArrayList<T> getDataList() throws EmptyDataException {
        if(dataList.size() == 0)
            throw new EmptyDataException("Este artista no pertenece a ninguna pelicula.");
        return dataList;
    }

    @Override
    public int compareTo(Artist o) { return getIdentifier().compareTo(o.getIdentifier()); }

    @Override
    public boolean equals(Object o) {
        return o instanceof Artist && ((Artist)o).getIdentifier().equals(getIdentifier());
    }
}

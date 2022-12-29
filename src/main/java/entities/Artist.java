package entities;
import entities.models.Entity;
import exceptions.EmptyDataException;
import templates.LinealWrapper;

import java.util.ArrayList;
import java.util.HashSet;

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

        for(Object film : linealWrapper.getList()){
            if(((Film<?>)film).getRating(false) != -1){
                filmsRating += ((Film<?>)film).getRating(true);
                votes += ((Film<?>)film).getVotes();
            }
        }

        rating = votes > 0 ? filmsRating / votes : 0;
    }

    public HashSet<Artist> getAdjacents(){
        HashSet<Artist> hashSet = new HashSet<>();

        /*
         * No es necesario comprobar si existe o no cada actor que se quiera agregar.
         * HashSet utiliza una función hash para comprobar que no esté duplicado, en
         * el momento de agregar un nuevo elemento al conjunto.
         */
        for(Object film: linealWrapper.getList())
            for(Object artist : ((Film<?>)film).getWrapper().getList())
                if(!((Artist)artist).getIdentifier().equals(identifier))
                    hashSet.add((Artist)artist);

        return hashSet;
    }

    /**
     * Overrides
     */

    @Override
    public LinealWrapper<Entity<T>> getWrapper(){ return linealWrapper; }

    @Override
    public void populateInfo(String info){ identifier = info; }

    @Override
    public void addData(T obj){
        if(!(obj instanceof Film))
            return;
        linealWrapper.add((Film)obj);
    }

    @Override
    public String getIdentifier() { return identifier; }

    @Override
    public int getDataNum(){ return linealWrapper.getList().size(); }

    @Override
    public double getRating(boolean opt) {
        if(opt)
            computeRating();
        return rating;
    }

    @Override
    public ArrayList<Entity<T>> getDataList() throws EmptyDataException {
        if(linealWrapper.size() == 0)
            throw new EmptyDataException("Este artista no pertenece a ninguna pelicula.");
        return linealWrapper.getList();
    }

    @Override
    public int compareTo(Artist o) { return getIdentifier().compareTo(o.getIdentifier()); }

    @Override
    public boolean equals(Object o) {
        return o instanceof Artist && ((Artist<?>)o).getIdentifier().equals(getIdentifier());
    }

    @Override
    public String toString() {
        return getIdentifier();
    }

    @Override
    public int getHash(){ return identifier.hashCode(); }
}

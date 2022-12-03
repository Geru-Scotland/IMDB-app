package templates;

import entities.models.DataCollection;
import entities.models.Entity;
import exceptions.EntityNotFoundException;

import java.util.ArrayList;

/**
 * Clase genérica encargada de gestionar colecciones de datos LINEALES. Hereda métodos de
 * búsqueda de la clase abstracta Search Engine.
 * @param <T> El tipo T debe de heredar de Comparable e implementar la interfaz Entity.
 *
 */
public class LinealWrapper<T extends Entity<?>> extends SearchEngine<T> implements DataCollection<T> {
    private final ArrayList<T> genericList;

    public LinealWrapper(){
        genericList = new ArrayList<>();
    }

    public T get(int pos) { return genericList.get(pos); }
    public ArrayList<T> getList() { return genericList; }

    /**
     * Búsqueda lineal a través de la lista genérica.
     * @param key Elemento a buscar.
     * @return el elemento encontrado.
     * @throws EntityNotFoundException lanzamos excepción en caso de no haber encontrado el elemento.
     */
    public T linealSearch(String key) throws EntityNotFoundException {
        for(T item : genericList)
            if(item.getIdentifier().equalsIgnoreCase(key))
                return item;
        throw new EntityNotFoundException("[EXCEPTION] [SEARCH ENGINE] Entity not found: " + key);
    }

    /**
     * DataCollection overrides
     */

    @Override
    public void add(T elemento){
        genericList.add(elemento);
    }

    @Override
    public int size(){ return genericList.size(); }

    @Override
    public T remove(String str){ return null; }

    /**
     * Búsqueda binaria o dicotómica sobre la lista genérica.
     * @param key Elemento a buscar.
     * @return Elemento encontrado.
     * @throws EntityNotFoundException lanzamos excepción en caso de no haber encontrado el elemento.
     */
    public T search(String key) throws EntityNotFoundException {

        int first = 0;
        int last = genericList.size() - 1;
        int middle = (first + last)/2;

        while(first <= last){
            if (genericList.get(middle).getIdentifier().compareTo(key) < 0)
                first = middle + 1;
            else if (genericList.get(middle).getIdentifier().equals(key))
                return genericList.get(middle);
            else
                last = middle - 1;

            middle = (first + last)/2;
        }
        throw new EntityNotFoundException("[EXCEPTION] [SEARCH ENGINE] Entity not found: " + key);
    }
}

package templates;

import entities.models.Entity;
import exceptions.EntityNotFoundException;

import java.util.ArrayList;

public class DataWrapper<T extends Comparable<T> & Entity> extends SearchEngine<T> {
    private final ArrayList<T> genericList;
    private int amount;

    public DataWrapper(){
        genericList = new ArrayList<>();
        amount = 0;
    }

    public void add(T elemento){
        genericList.add(elemento);
        amount++;
    }

    public int getSize(){ return genericList.size(); }
    public T get(int pos) { return genericList.get(pos); }
    public ArrayList<T> getList() { return genericList; }

    public T search(String key) throws EntityNotFoundException {
        for(T item : genericList)
            if(item.getIdentifier().equalsIgnoreCase(key))
                return item;
        throw new EntityNotFoundException("[EXCEPTION] [SEARCH ENGINE] Entity not found: " + key);
    }

    public T binarySearch(String key) throws EntityNotFoundException {

        int first = 0;
        int last = amount-1;
        int middle = (first + last)/2;

        while( first <= last ){

            if ( genericList.get(middle).getIdentifier().compareTo(key) < 0 )
                first = middle + 1;
            else if ( genericList.get(middle).getIdentifier().equals(key))
                return genericList.get(middle);
            else
                last = middle - 1;

            middle = (first + last)/2;
        }
        throw new EntityNotFoundException("[EXCEPTION] [SEARCH ENGINE] Entity not found: " + key);
    }
}

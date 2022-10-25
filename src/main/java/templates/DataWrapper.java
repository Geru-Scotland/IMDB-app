package templates;

import entities.models.Entity;
import java.util.ArrayList;

public class DataWrapper<T extends Comparable<T> & Entity> extends SearchEngine<T> {
    private ArrayList<T> genericList;
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

    /**
     * @param str
     * @return
     */
    public T search(String str) {
        for(T item : genericList)
            if(item.getIdentifier().equalsIgnoreCase(str))
                return item;
        return null;
    }

    /**
     * Búsqueda dicotómica
     * @param key
     * @return
     */
    public T binarySearch(String key){

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

        return null;
    }
}

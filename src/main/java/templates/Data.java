package templates;

import modelos.Model;

import java.util.ArrayList;

public class Data<T extends Comparable<T> & Model> {
    private ArrayList<T> listaGenerica;

    public Data(){
        listaGenerica = new ArrayList<>();
    }

    public void add(T elemento){
        listaGenerica.add(elemento);
    }

    public int getSize(){ return listaGenerica.size(); }

    public T get(int pos) { return listaGenerica.get(pos); }

    /**
     * Búsqueda dicotómica
     * @param str
     * @return
     */
    public T buscar(String str) {
        //Temporal, para hacer unas pruebas.
        for(T item : listaGenerica){
            if(item.getIdentifier().equals(str))
                return item;
        }
        return null;
    }
}

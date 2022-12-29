package entities.models;

import exceptions.EmptyDataException;
import templates.LinealWrapper;

import java.util.ArrayList;

/**
 * Caracter�sticas conceptuales y m�todos que toda entidad que vaya a ser wrapeada con la clase
 * LinealWrapper debe de tener.
 * @param <T> Objeto de tipo T, que pertenecer� a una colecci�n de la entidad en cuesti�n.
 */
public abstract class Entity<T extends Comparable<T>> {
    /**
     * Variables miembros
     */
    protected LinealWrapper<Entity<T>> linealWrapper;
    protected String identifier;
    protected double rating;

    protected  Entity() { linealWrapper = new LinealWrapper<>(); }

    /**
     * M�todo encargado de poblar la informaci�n relevante a la entidad.
     * @param info String que contendr� informaci�n intr�nseca a la clase. Habr� que splitear en el caso de que la entidad
     *             sea Films, para acceder a todos sus par�metros.
     */
    protected abstract void populateInfo(String info);

    /**
     * M�todo encargado de agregar un elemento del tipo T a la colecci�n de datos
     * que tendr� la entidad.
     * @param obj Objeto de tipo T que ser� agregado a una colecci�n de tipo ArrayList.
     */
    protected abstract void addData(T obj);

    /**
     * M�todo "getter" encargado de obtener el par�metro que act�e como identificador para cada
     * entidad.
     * @return String que act�a como identificador de la identidad en cuesti�n.
     */
    public abstract String getIdentifier();

    /**
     * M�todo "getter" que nos devolver� el n�mero de posiciones ocupadas de la colecci�n
     * que disponga la Entidad.
     * @return integer con el valor de elementos reales dentro del ArrayList.
     */
    protected abstract int getDataNum();

    /**
     * M�todo "getter" que nos devolver� el rating correspondiente a la entidad.
     * @param weighted variable que indicar� si se ha de aplicar alg�n tipo de peso al rating promedio.
     * @return variable de tipo double con el rating correspondiente.
     */
    protected abstract double getRating(boolean weighted);

    /**
     * M�todo "getter" que nos devolver�
     * @return Referencia a la colecci�n del tipo ArrayList<T> que posea la entidad en cuesti�n.
     * @throws EmptyDataException Excepci�n que ser� lanzada en caso de no existir ning�n dato.
     */
    public abstract ArrayList<Entity<T>> getDataList() throws EmptyDataException;

    public abstract LinealWrapper<Entity<T>> getWrapper();

    /**
     * Hashing
     */
    public abstract int getHash();
}
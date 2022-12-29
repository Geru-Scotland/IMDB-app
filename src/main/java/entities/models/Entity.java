package entities.models;

import exceptions.EmptyDataException;
import templates.LinealWrapper;

import java.util.ArrayList;

/**
 * Características conceptuales y métodos que toda entidad que vaya a ser wrapeada con la clase
 * LinealWrapper debe de tener.
 * @param <T> Objeto de tipo T, que pertenecerá a una colección de la entidad en cuestión.
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
     * Método encargado de poblar la información relevante a la entidad.
     * @param info String que contendrá información intrínseca a la clase. Habrá que splitear en el caso de que la entidad
     *             sea Films, para acceder a todos sus parámetros.
     */
    protected abstract void populateInfo(String info);

    /**
     * Método encargado de agregar un elemento del tipo T a la colección de datos
     * que tendrá la entidad.
     * @param obj Objeto de tipo T que será agregado a una colección de tipo ArrayList.
     */
    protected abstract void addData(T obj);

    /**
     * Método "getter" encargado de obtener el parámetro que actúe como identificador para cada
     * entidad.
     * @return String que actúa como identificador de la identidad en cuestión.
     */
    public abstract String getIdentifier();

    /**
     * Método "getter" que nos devolverá el número de posiciones ocupadas de la colección
     * que disponga la Entidad.
     * @return integer con el valor de elementos reales dentro del ArrayList.
     */
    protected abstract int getDataNum();

    /**
     * Método "getter" que nos devolverá el rating correspondiente a la entidad.
     * @param weighted variable que indicará si se ha de aplicar algún tipo de peso al rating promedio.
     * @return variable de tipo double con el rating correspondiente.
     */
    protected abstract double getRating(boolean weighted);

    /**
     * Método "getter" que nos devolverá
     * @return Referencia a la colección del tipo ArrayList<T> que posea la entidad en cuestión.
     * @throws EmptyDataException Excepción que será lanzada en caso de no existir ningún dato.
     */
    public abstract ArrayList<Entity<T>> getDataList() throws EmptyDataException;

    public abstract LinealWrapper<Entity<T>> getWrapper();

    /**
     * Hashing
     */
    public abstract int getHash();
}
package entities.models;

import entities.Artist;
import entities.Film;
import templates.hashing.HashMapWrapper;

/**
 * Junto con Entity, esta clase conformara el modelo de datos
 * que gestionara el Catálogo.
 */
public abstract class DataModel {

    protected static DataCollection<Film<Artist<?>>> films;
    protected static DataCollection<Artist<?>> casting;

    public DataModel(){}
}

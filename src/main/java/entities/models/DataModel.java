package entities.models;

import entities.Artist;
import entities.Film;
import templates.DataWrapper;

/**
 * Junto con Entity, esta clase conformara el modelo de datos
 * que gestionara el Catalogo.
 */
public abstract class DataModel {

    protected static DataWrapper<Film<Artist<?>>> films;
    protected static DataWrapper<Artist<Film<?>>> casting;

    public DataModel(){}
}

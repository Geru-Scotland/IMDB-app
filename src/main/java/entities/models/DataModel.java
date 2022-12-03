package entities.models;

import entities.Artist;
import entities.Film;
import templates.FilmWrapper;

/**
 * Junto con Entity, esta clase conformara el modelo de datos
 * que gestionara el Catalogo.
 */
public abstract class DataModel {

    protected static FilmWrapper<Film<Artist<?>>> films;
    protected static FilmWrapper<Artist<Film<?>>> casting;

    public DataModel(){}
}

package entities.models;

import entities.Artist;
import entities.Film;
import templates.*;
import templates.scalable.BTreeWrapper;

/**
 * Junto con Entity, esta clase conformara el modelo de datos
 * que gestionara el Catalogo.
 */
public abstract class DataModel {

    protected static DataCollection<Film<Artist<?>>> films;
    protected static DataCollection<Artist<Film<?>>> casting;

    public DataModel(){}
}

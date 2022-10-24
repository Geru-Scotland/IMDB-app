package entities.models;

import entities.Artist;
import entities.Film;
import templates.DataWrapper;

public abstract class DataModel {

    protected static DataWrapper<Film> films;
    protected static DataWrapper<Artist> casting;

    public DataModel(){}
}

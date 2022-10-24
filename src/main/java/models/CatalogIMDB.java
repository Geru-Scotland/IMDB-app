package modelos;

import managers.*;
import templates.Data;

public class CatalogIMDB {
    private static CatalogIMDB instance;

    protected Data<Film> films;
    protected Data<Artist> casting;

    private SearchEngine se;

    protected CatalogIMDB(){
        se = new SearchEngine();
        films = new Data<>();
        casting = new Data<>();
    }

    public static CatalogIMDB getInstance() {
        if(instance == null)
            instance = new CatalogIMDB();
        return instance;
    }

    protected void addToCatalog(String[] info, int type){

    }

    /**
     * Imprime por pantalla el no de intérpretes de una película y sus nombres
     * @param titulo Título de la película
     */
    public void imprimirInfoPelicula(String titulo) {}
    /**
     * Imprime por pantalla el nombre del intérprete, su rating y los títulos
     * de sus películas.
     * @param nombre Nombre del intérprete
     */
    public void imprimirInfoInterprete(String nombre) {}
    /**
     * Añade un nuevo voto a una película
     * PRE: el valor del voto está entre 0.0 y 10.0.
     * @param titulo Título de la película
     * @param voto Valor del voto
     */
    public void anadirVoto(String titulo, float voto) {}
}

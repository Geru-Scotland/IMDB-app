package modelos;

import managers.*;
import templates.Data;

public class CatalogoIMDB {
    private static CatalogoIMDB instance;

    protected Data<Pelicula> peliculas;
    protected Data<Interprete> interpretes;

    private SearchEngine se;

    protected CatalogoIMDB(){
        se = new SearchEngine();
        peliculas = new Data<>();
        interpretes = new Data<>();
    }

    public static CatalogoIMDB getInstance() {
        if(instance == null)
            instance = new CatalogoIMDB();
        return instance;
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

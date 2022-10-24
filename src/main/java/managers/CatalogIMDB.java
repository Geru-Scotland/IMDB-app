package managers;

import libs.Stopwatch;
import models.Artist;
import models.Film;
import templates.Data;

public class CatalogIMDB {
    private static CatalogIMDB instance;

    protected static Data<Film> films;
    protected static Data<Artist> casting;

    protected CatalogIMDB(){
        films = new Data<>();
        casting = new Data<>();
    }

    public static CatalogIMDB getInstance() {
        if(instance == null)
            instance = new CatalogIMDB();
        return instance;
    }

    protected void addVotetoFilm(Film film, float vote){

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
    public void imprimirInfoInterprete(String nombre) {
        Stopwatch sw = new Stopwatch();
        Artist artist = casting.binarySearch(nombre);
        if(artist == null)
            return;
        System.out.println("Busqueda finalizada en " + sw.elapsedTime() + " segundos.");
        System.out.println("El artista "+ nombre + " ha participado en las siguientes peliculas: ");
        for(Film film : artist.getFilms())
            System.out.println(film.getIdentifier());

    }
    /**
     * Añade un nuevo voto a una película
     * PRE: el valor del voto está entre 0.0 y 10.0.
     * @param titulo Título de la película
     * @param voto Valor del voto
     */
    public void anadirVoto(String titulo, float voto) {}
}

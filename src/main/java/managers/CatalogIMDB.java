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

    public void addVotetoFilm(String filmName, float vote){
        Film film = films.binarySearch(filmName);
        if(film == null)
            return;
        film.addVote(vote);
    }

    public void displayFilm(String titulo) {}

    public void displayArtist(String nombre) {
        Stopwatch sw = new Stopwatch();
        Artist artist = casting.binarySearch(nombre);
        if(artist == null)
            return;
        System.out.println("Busqueda finalizada en " + sw.elapsedTime() + " segundos.");
        System.out.println("Nombre: "+ nombre);
        System.out.println("Rating: " + artist.getRating());
        System.out.println("Peliculas ("+ artist.getFilmsNum()+") ");
        for(Film film : artist.getFilms())
            System.out.println(film.getIdentifier());

    }
}

package managers;

import libs.Stopwatch;
import entities.Artist;
import entities.Film;
import entities.models.DataModel;
import templates.DataWrapper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CatalogIMDB extends DataModel {
    private static CatalogIMDB instance;

    protected CatalogIMDB(){
        films = new DataWrapper<>();
        casting = new DataWrapper<>();
    }

    public static CatalogIMDB getInstance() {
        if(instance == null)
            instance = new CatalogIMDB();
        return instance;
    }

    public void addVoteToFilm(String filmName, float vote){
        Film film = films.binarySearch(filmName);
        if(film == null)
            return;
        film.addVote(vote);
    }

    public void displayFilmInfo(String titulo) {}

    public void displayArtistInfo(String nombre) {
        Stopwatch sw = new Stopwatch();
        Artist artist = casting.binarySearch(nombre);
        if(artist == null)
            return;
        System.out.println("Busqueda finalizada en " + sw.elapsedTime() + " segundos.");
        System.out.println("Nombre: "+ nombre);
        try{
            System.out.println("Rating: " + BigDecimal.valueOf(artist.getRating(true)).setScale(2, RoundingMode.FLOOR));
        } catch(NumberFormatException e){
            System.out.println("Rating: -");
        }
        System.out.println("Peliculas ("+ artist.getFilmsNum()+") ");
        for(Film film : artist.getFilms())
            System.out.println(film.getIdentifier() + " [r=" + film.getRating(false)+ ", v="+ film.getVotes()+"]");

    }
}

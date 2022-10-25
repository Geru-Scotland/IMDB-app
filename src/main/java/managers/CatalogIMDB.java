package managers;

import exceptions.NonValidInputValue;
import libs.Stopwatch;
import entities.Artist;
import entities.Film;
import entities.models.DataModel;
import templates.DataWrapper;

import java.math.BigDecimal;
import java.math.RoundingMode;

import exceptions.EntityNotFoundException;

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

    public void addFilmVote(String filmName, float score) throws IllegalArgumentException, NonValidInputValue {
        try{
            if(score < 0 || score > 10)
                throw new NonValidInputValue();
            Film film = films.binarySearch(filmName);
            film.addVote(score);
        } catch(EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public void displayFilmInfo(String titulo) throws EntityNotFoundException {
        try{
            Stopwatch sw = new Stopwatch();
            Film film = films.binarySearch(titulo);

            System.out.println("Busqueda finalizada en " + sw.elapsedTime() + " segundos.");
            System.out.println("Titulo: "+ titulo);
            try{
                System.out.println("Rating: " + BigDecimal.valueOf(film.getRating(false)).setScale(2, RoundingMode.FLOOR));
            } catch(NumberFormatException e){
                System.out.println("Rating: -");
            }
            System.out.println("Actores ("+ film.getArtistNum() +") ");
            for(Artist artist : film.getCasting()){
                try{
                    System.out.println(artist.getIdentifier() + " [r=" + BigDecimal.valueOf(film.getRating(false)).setScale(2, RoundingMode.FLOOR)+ ", v="+ film.getVotes()+"]");
                } catch(NumberFormatException e){
                    System.out.println("Rating: -");
                }
            }
        } catch(EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public void displayArtistInfo(String nombre) throws EntityNotFoundException {
        try{
            Stopwatch sw = new Stopwatch();
            Artist artist = casting.binarySearch(nombre);

            System.out.println("Busqueda finalizada en " + sw.elapsedTime() + " segundos.");
            System.out.println("Nombre: "+ nombre);
            try{
                System.out.println("Rating: " + BigDecimal.valueOf(artist.getRating(true)).setScale(2, RoundingMode.FLOOR));
            } catch(NumberFormatException e){
                System.out.println("Rating: -");
            }
            System.out.println("Peliculas ("+ artist.getFilmsNum()+") ");
            for(Film film : artist.getFilms()){
                try{
                    System.out.println(film.getIdentifier() + " [r=" + BigDecimal.valueOf(film.getRating(false)).setScale(2, RoundingMode.FLOOR)+ ", v="+ film.getVotes()+"]");
                } catch(NumberFormatException e){
                    System.out.println("Rating: -");
                }
            }
        } catch(EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}

package managers;

import entities.Artist;
import entities.Film;
import entities.models.DataCollection;
import entities.models.DataModel;
import entities.models.Entity;
import exceptions.EmptyDataException;
import exceptions.EntityNotFoundException;
import exceptions.NonValidInputValue;
import libs.Stopwatch;
import templates.LinealWrapper;
import templates.scalable.BTreeWrapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Clase catálogo, cuyo objetivo es el de hacer de interfaz para el usuario, entre
 * los datos y sus estructuras.
 */
public class CatalogIMDB extends DataModel {
    private static CatalogIMDB instance;

    protected CatalogIMDB(){
        films = new LinealWrapper<>();
        casting = new BTreeWrapper<>();
    }

    public static CatalogIMDB getInstance() {
        if(instance == null)
            instance = new CatalogIMDB();
        return instance;
    }

    public DataCollection<Film<Artist<?>>> getFilms() { return films; }
    public DataCollection<Artist<?>> getCasting() { return casting; }

    public static void setCasting(DataCollection<Artist<?>> newCast){
        casting = newCast;
    }

    /**
     * Método para agregar un voto a una película
     * @param filmName Nombre de la pelicula.
     * @param score Puntuación del voto.
     * @throws IllegalArgumentException
     * @throws NonValidInputValue Excepción que será lanzada en caso de introducir un valor válido como voto.
     */
    public void addFilmVote(String filmName, float score) throws NonValidInputValue, EntityNotFoundException {
        if(score < 0 || score > 10)
            throw new NonValidInputValue();

        Film<?> film = films.search(filmName);
        film.addVote(score);
    }

    /**
     * Vuelca información de la película.
     * @param title Título de la pelicula
     * @throws EntityNotFoundException Se lanzará en el caso de no encontrarse en base de datos.
     */
    public void displayFilmInfo(String title) throws EntityNotFoundException {
        try{
            displayData(films, title);
        } catch(EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Vuelca información del artista.
     * @param name Nombre del artista.
     */
    public void displayArtistInfo(String name){
        try{
            displayData(casting, name);
        } catch(EntityNotFoundException e){
            System.out.println(e.getMessage());
        } catch(NullPointerException e){
            System.out.println("[EXCEPTION] Arbol de artistas vacío.");
        }
    }

    /**
     * Método encargado de mostrar la información relacionada al elemento encontrado en la colleción con elementos de tipo T
     * que le pasemos.
     * @param collection Colección de datos donde realizar la búsqueda.
     * @param identifier Identificador de la entidad.
     * @throws EntityNotFoundException Excepción que será lanzada en caso de no encontrar la entidad en cuestión.
     */
    public void displayData(DataCollection<?> collection, String identifier) throws EntityNotFoundException, NullPointerException {

        try{
            Stopwatch sw = new Stopwatch();
            Object entity = collection.search(identifier);
            System.out.println("Busqueda finalizada en " + sw.elapsedTime() + " segundos.");
            System.out.println("Nombre: "+ identifier);

            try{
                double rating = 0.0;
                String dataText = "";
                String dataText2 = "";
                ArrayList<?> entityList = null;

                if(entity instanceof Artist){
                    rating = ((Artist<?>)entity).getRating(true);
                    dataText = "Peliculas: " + ((Artist<?>)entity).getDataNum();
                    try{
                        entityList = ((Artist<?>)entity).getDataList();
                    } catch(EmptyDataException e){
                        System.out.println(e.getMessage());
                    }
                }else if(entity instanceof Film){
                    rating = ((Film<?>)entity).getRating(false);
                    dataText = "Artistas: " + ((Film<?>)entity).getDataNum();
                    dataText2 = " | votos: " + ((Film<?>)entity).getVotes();
                    try{
                        entityList = ((Film<?>)entity).getDataList();
                    } catch(EmptyDataException e){
                        System.out.println(e.getMessage());
                    }
                }

                System.out.println(dataText);
                System.out.println("Rating: " + BigDecimal.valueOf(rating).setScale(2, RoundingMode.FLOOR) + dataText2);
                System.out.println("_____________");
                System.out.println("_____________");

                if(entityList == null)
                    throw new NullPointerException();

                for(Object subEntity : entityList){
                    try{
                        if(subEntity instanceof Artist)
                            System.out.println(((Artist<?>)subEntity).getIdentifier() + " [r=" + BigDecimal.valueOf(((Artist<?>)subEntity).getRating(true)).setScale(2, RoundingMode.FLOOR)+ "]");
                        else if(subEntity instanceof Film)
                            System.out.println(((Film<?>)subEntity).getIdentifier() + " [r=" + BigDecimal.valueOf(((Film<?>)subEntity).getRating(false)).setScale(2, RoundingMode.FLOOR)+ ", v="+ ((Film<?>)subEntity).getVotes()+"]");
                    } catch(NumberFormatException e){
                        System.out.println("Rating: -");
                    }
                }

                System.out.println("_____________");
                System.out.println("_____________");

            } catch(NumberFormatException e){
                System.out.println("Rating: -");
            }

        } catch(EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Remove film
     */
    public Film<?> removeFilm(String str) throws EntityNotFoundException, EmptyDataException {
        Film<?> deletedFilm = films.remove(str);
        try{
            for(Entity<?> artist : deletedFilm.getDataList()){
                LinealWrapper<?> wrapper = artist.getWrapper();
                wrapper.remove(str);
                if(wrapper.isEmpty())
                    casting.remove(artist.getIdentifier());
                else
                    ((Artist<?>)artist).computeRating();
            }
            System.out.println("[EXCEPTION] Se ha borrado la pelicula: " + deletedFilm.getIdentifier());
        } catch(EmptyDataException e){
            System.out.println("[EXCEPTION] No tiene artistas");
        }
        return deletedFilm;
    }

    public StringBuffer showStatusAfterDeletion(Film<?> film){
        StringBuffer buff = new StringBuffer();
        buff.append("Se ha borrado la pelicula");
        buff.append(film.getIdentifier());
        buff.append(". En el catálogo quedan ");
        buff.append(films.size());
        buff.append(" peliculas y ");
        buff.append(casting.size());
        buff.append(" actores");
        return buff;
    }
}

package managers;

import entities.Artist;
import entities.Film;
import entities.models.DataCollection;
import entities.models.DataModel;
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
 * Clase cat�logo, cuyo objetivo es el de hacer de interfaz para el usuario, entre
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

    /**
     * M�todo para agregar un voto a una pel�cula
     * @param filmName Nombre de la pelicula.
     * @param score Puntuaci�n del voto.
     * @throws IllegalArgumentException
     * @throws NonValidInputValue Excepci�n que ser� lanzada en caso de introducir un valor v�lido como voto.
     */
    
    public void addFilmVote(String filmName, float score) throws NonValidInputValue, EntityNotFoundException {
        if(score < 0 || score > 10)
            throw new NonValidInputValue();

        Film<?> film = films.search(filmName);
        film.addVote(score);
    }

    /**
     * Vuelca informaci�n de la pel�cula.
     * @param title T�tulo de la pelicula
     * @throws EntityNotFoundException Se lanzar� en el caso de no encontrarse en base de datos.
     */
    public void displayFilmInfo(String title) throws EntityNotFoundException {
        try{
            displayData(films, title);
        } catch(EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Vuelca informaci�n del artista.
     * @param name Nombre del artista.
     */
    public void displayArtistInfo(String name){
        try{
            displayData(casting, name);
        } catch(EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * M�todo encargado de mostrar la informaci�n relacionada al elemento encontrado en la colleci�n con elementos de tipo T
     * que le pasemos.
     * @param collection Colecci�n de datos donde realizar la b�squeda.
     * @param identifier Identificador de la entidad.
     * @throws EntityNotFoundException Excepci�n que ser� lanzada en caso de no encontrar la entidad en cuesti�n.
     */
    public void displayData(DataCollection<?> collection, String identifier) throws EntityNotFoundException {

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
                    dataText = "Peliculas (" + ((Artist<?>)entity).getDataNum();
                    try{
                        entityList = ((Artist<?>)entity).getDataList();
                    } catch(EmptyDataException e){
                        System.out.println(e.getMessage());
                    }
                }else if(entity instanceof Film){
                    rating = ((Film<?>)entity).getRating(false);
                    dataText = "Actores (" + ((Film<?>)entity).getDataNum();
                    dataText2 = " | votos: " + ((Film<?>)entity).getVotes();
                    try{
                        entityList = ((Film<?>)entity).getDataList();
                    } catch(EmptyDataException e){
                        System.out.println(e.getMessage());
                    }
                }

                System.out.println(dataText + ") ");
                System.out.println("Rating: " + BigDecimal.valueOf(rating).setScale(2, RoundingMode.FLOOR) + dataText2);
                System.out.println("_____________");
                System.out.println("_____________");

                assert entityList != null;
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
}

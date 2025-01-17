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
import templates.hashing.HashMapWrapper;
import templates.scalable.BTreeWrapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Clase cat�logo, cuyo objetivo es el de hacer de interfaz para el usuario, entre
 * los datos y sus estructuras.
 */
public class CatalogIMDB extends DataModel {
    private static CatalogIMDB instance;

    protected CatalogIMDB(){
        films = new LinealWrapper<>();
        casting = new HashMapWrapper<>();
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
        } catch(NullPointerException e){
            System.out.println("[EXCEPTION] Colleci�n de artistas vac�o.");
        }
    }

    /**
     * M�todo encargado de mostrar la informaci�n relacionada al elemento encontrado en la colleci�n con elementos de tipo T
     * que le pasemos.
     * @param collection Colecci�n de datos donde realizar la b�squeda.
     * @param identifier Identificador de la entidad.
     * @throws EntityNotFoundException Excepci�n que ser� lanzada en caso de no encontrar la entidad en cuesti�n.
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
                System.out.println("Rating: " + BigDecimal.valueOf(rating).setScale(4, RoundingMode.FLOOR) + dataText2);
                System.out.println("_____________");
                System.out.println("_____________");

                if(entityList == null)
                    throw new NullPointerException();

                for(Object subEntity : entityList){
                    try{
                        if(subEntity instanceof Artist)
                            System.out.println(((Artist<?>)subEntity).getIdentifier() + " [r=" + BigDecimal.valueOf(((Artist<?>)subEntity).getRating(true)).setScale(4, RoundingMode.FLOOR)+ "]");
                        else if(subEntity instanceof Film)
                            System.out.println(((Film<?>)subEntity).getIdentifier() + " [r=" + BigDecimal.valueOf(((Film<?>)subEntity).getRating(false)).setScale(4, RoundingMode.FLOOR)+ ", v="+ ((Film<?>)subEntity).getVotes()+"]");
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
     * M�todo encargado de borrar una pel�cula tanto de la lista/wrapper de pel�culas
     * como de las listas/wrappers propios de cada uno de los artistas que han participado
     * en la misma. Se fuerza una actualizaci�n del rating en dichos artistas si a�n existen
     * pel�culas que hayan participado. Se eliminan los artistas en caso contrario.
     * @param str Nombre de la pel�cula a eliminar.
     * @return devuelve referencia a objeto con la pel�cula eliminada.
     * @throws EntityNotFoundException en caso de que no exista la pel�cula/artista buscado.
     * @throws EmptyDataException Si se intenta realizar la operaci�n sobre una estructura de artistas vac�a.
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

    public void printAdjacents(String name) {
        try{
            HashSet<Artist> adjList = casting.search(name).getAdjacents();
            System.out.println("Adjacency list size: " + adjList.size());
            for(Artist artist : adjList){
                System.out.println(" | " + artist.getIdentifier());
            }
        } catch(EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Distancia entre 2 artistas.
     * No estamos trabajando con un grafo en s�, pero vamos
     * a simular su funcionamiento.
     *
     * Algoritmo: Bread-first search.
     */
    public int getGraphDistance(String str1, String str2) throws EntityNotFoundException {
        HashSet<Artist> visited = new HashSet<>();
        Queue<Artist> queue = new LinkedList<>();
        HashMap<Artist, Integer> distancesMap = new HashMap<>();

        Artist init = casting.search(str1);
        Artist dest = casting.search(str2);
        queue.add(init);
        distancesMap.put(init, 0);

        while(!queue.isEmpty()){
            Artist currentArtist = queue.poll();

            HashSet<Artist> adjacents = currentArtist.getAdjacents();
            for(Object adjArtist : adjacents){
                if(!visited.contains((Artist)adjArtist)){
                    if(((Artist)adjArtist).getIdentifier().equals(dest.getIdentifier()))
                        return distancesMap.get(currentArtist) + 1;
                    queue.add((Artist)adjArtist);
                    visited.add((Artist)adjArtist);
                    distancesMap.put((Artist)adjArtist, distancesMap.get(currentArtist) + 1);
                }
            }
        }
        throw new EntityNotFoundException("Something went wrong.");
    }

    public void displayShortestPath(String init, String dest){
        try{
            System.out.println("[BSF] Shortest path: ");
            for(Artist v : computeShortestPath(init, dest)){
                System.out.print( v.getIdentifier() +" - ");
            }
            System.out.println();
        } catch(EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public LinkedList<Artist> computeShortestPath(String str1, String str2) throws EntityNotFoundException {
        Queue<Artist> queue = new LinkedList<>();
        HashMap<Artist, Artist> backTraceMap = new HashMap<>();

        Artist init = casting.search(str1);
        Artist dest = casting.search(str2);

        queue.add(init);
        backTraceMap.put(init, null);

        while(!queue.isEmpty()){
            Artist current = queue.poll();

            for(Object adj : current.getAdjacents()){
                if(!backTraceMap.containsKey((Artist)adj)){
                    if(((Artist)adj).equals(dest)){
                        backTraceMap.put((Artist)adj, current);
                        return backTraceShortestPath(backTraceMap, (Artist)adj);
                    }
                    queue.add((Artist)adj);
                    backTraceMap.put((Artist)adj, current);
                }
            }
        }
        throw new EntityNotFoundException("[EXCEPTION] Entity not found");
    }

    public LinkedList<Artist> backTraceShortestPath(HashMap<Artist, Artist> backTraceMap, Artist init){
        LinkedList<Artist> shortestPath = new LinkedList<>();

        while(init != null){
            shortestPath.add(init);
            init = backTraceMap.get(init);
        }
        Collections.reverse(shortestPath);
        return shortestPath;
    }

    public StringBuffer showStatusAfterDeletion(Film<?> film){
        StringBuffer buff = new StringBuffer();
        buff.append("Se ha borrado la pelicula");
        buff.append(film.getIdentifier());
        buff.append(". En el cat�logo quedan ");
        buff.append(films.size());
        buff.append(" peliculas y ");
        buff.append(casting.size());
        buff.append(" actores");
        return buff;
    }
}

package managers;

import entities.Artist;
import entities.Film;
import entities.models.DataModel;
import exceptions.EntityNotFoundException;
import exceptions.LoadMgrException;
import libs.Stopwatch;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Scanner;


public class LoadMgr extends DataModel {

    /**
     * TODO: Path/File selector.
     */
    final String filmFile = "files/films";
    final String castFile = "files/cast";

    public LoadMgr(){ }

    /**
     *
     * Método encargado de cargar toda la información.
     *
     * 1) Carga las películas de manera secuencial.
     * 2) Carga los artistas y relaciona con las peliculas ya cargadas.
     *
     * Hemos desarrollado dos opciones, dejando como base la primera:
     *
     * Opción 1: Ordenar el arraylist de casting para poder hacer búsquedas binarias
     * posteriormente. De ésta manera agregamos ~3.5s extra en la carga inicial (ficheros grandes)
     * pero reducimos drásticamente el tiempo de búsqueda en cada una de las mismas.
     *
     * Opción 2: NO ordenar el arraylist de casting y en la búsqueda utilizar el método "search"
     * del SearchEngine. Eliminamos el tiempo extra de carga que necesita el ordenado, pero se incrementa
     * el tiempo por cada búsqueda.
     *
     * Ambas son válidas y funcionales.
     */
    public void loadData() throws LoadMgrException{
        try{
            Stopwatch sw = new Stopwatch();
            System.out.println(" ");
            System.out.println("Loading Films database...[O(n)]");
            loadFilms();
            System.out.println("Successfully loaded " + films.getSize() + " films in " + sw.elapsedTime() + " seconds.");
            System.out.println("Loading Casting database and linking data...[Algorithm: Binary Search | O(n(mlogp))]");
            sw.reset();
            loadCast();
            System.out.println("Successfully loaded " + casting.getSize() + " artists in " + sw.elapsedTime() + " seconds.");
            System.out.println("Sorting casting list...");
            sw.reset();
            Collections.sort(casting.getList());
            System.out.println("Casting list sorted in " + sw.elapsedTime() + " seconds [Algorithm: It. Mergesort (Collections.sort) | O(nlogn)]");;
            System.out.println(" ");
            System.out.println("");
        } catch(IOException e){
            System.out.println(e.getMessage());
        } catch(LoadMgrException e){
            throw new LoadMgrException(e.getMessage());
        }
    }

    /**
     * Carga las películas de manera secuencial.
     * @throws IOException Excepción lanzada en caso de existir los problemas típicos.
     * @throws LoadMgrException Tratamiento para cualquier otro tipo de problema al abrir el fichero.
     */
    private void loadFilms() throws IOException, LoadMgrException {
        try{
            Scanner sc = openFile(filmFile);
            String line = null;

            while (sc.hasNextLine()) {
                line = sc.nextLine();
                try{
                    Film film = new Film();
                    film.populateInfo(line);
                    films.add(film);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println(e.getMessage());
                }
            }
            sc.close();
        } catch(LoadMgrException e){
            throw new LoadMgrException(e.getMessage());
        }
    }

    /**
     * Carga los artistas de manera secuencial y realiza el proceso de link/relación con las películas.
     * @throws IOException Excepción lanzada en caso de existir los problemas típicos.
     * @throws LoadMgrException Tratamiento para cualquier otro tipo de problema al abrir el fichero.
     */
    private void loadCast() throws IOException, LoadMgrException  {
        try{
            Scanner sc = openFile(castFile);
            String line = null;

            while (sc.hasNextLine()) {
                line = sc.nextLine();
                try{
                    Artist artist = new Artist();
                    String[] info = line.split("->");
                    artist.populateInfo(info[0]);

                    String[] filmList = null;

                    if(info[1].contains("||")){
                        filmList = info[1].split("\\|\\|");
                        for (String film : filmList)
                            linkData(film, artist);

                    } else
                        linkData(info[1], artist);

                    try{
                        casting.add(artist);
                    } catch (IndexOutOfBoundsException e){
                        System.out.println(e.getMessage());
                    }
                } catch (NullPointerException | NumberFormatException | ArrayIndexOutOfBoundsException ignored) {}
            }
            sc.close();
        } catch(LoadMgrException e){
            throw new LoadMgrException(e.getMessage());
        }
    }

    /**
     * Método encargado de abrir el fichero y lanzar la excepción apropiada en caso de error.
     * @param file Nombre del fichero a cargar.
     * @return Referencia al scanner con el fichero abierto.
     * @throws LoadMgrException Lanza esta excepción ante cualquier problema.
     */
    private Scanner openFile(String file) throws LoadMgrException {
        try {
            return new Scanner(new FileReader("./data/" + file + ".txt"));
        } catch (FileNotFoundException e) {
            System.out.println("No existe ningún fichero llamado " + file);
        }
        throw new LoadMgrException("[EXCEPTION] [LOADMGR] Ha habido un problem al abrir el fichero.");
    }

    /**
     * Proceso de relación entre Artista - película (para cada uno de los artistas cargados de manera secuencial)
     * Para ello, se analiza la lista de películas de cada artista, y todas ellas se buscan mediante el algoritmo de
     * búsqueda binaria en la estructura de datos del Wrapper.
     * @param filmName Nombre de la película a buscar.
     * @param artist Nombre del artista.
     */
    private void linkData(String filmName, Artist artist){
        try{
            Film currFilm = films.binarySearch(filmName);
            artist.addData(currFilm);
            currFilm.addData(artist);
        } catch (EntityNotFoundException ignore){}
    }
}

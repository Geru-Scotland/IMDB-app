package managers;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.Scanner;

import exceptions.EntityNotFoundException;
import exceptions.LoadMgrException;

import libs.Stopwatch;
import entities.models.DataModel;
import entities.Artist;
import entities.Film;


public class LoadMgr extends DataModel {

    /**
     * TODO: Path/File selector.
     */
    final String filmFile = "files/films";
    final String castFile = "files/cast";

    public LoadMgr(){ }

    /**
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
            System.out.println("Loading Casting database...[Algorithm: Binary Search | O(nlogn)]");
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
        } catch(LoadMgrException e){
            throw new LoadMgrException(e.getMessage());
        }
    }

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

                    } else{
                        linkData(info[1], artist);
                    }

                    try{
                        casting.add(artist);
                    } catch (IndexOutOfBoundsException e){
                        System.out.println(e.getMessage());
                    }
                } catch (NullPointerException | NumberFormatException | ArrayIndexOutOfBoundsException ignored) {}
            }
        } catch(LoadMgrException e){
            throw new LoadMgrException(e.getMessage());
        }
    }

    private Scanner openFile(String file) throws LoadMgrException {
        try {
            return new Scanner(new FileReader("./data/" + file + ".txt"));
        } catch (FileNotFoundException e) {
            System.out.println("No existe ningún fichero llamado " + file);
        }
        throw new LoadMgrException("[EXCEPTION] [LOADMGR] Ha habido un problem al abrir el fichero.");
    }

    private void linkData(String filmName, Artist artist){
        try{
            Film currFilm = films.binarySearch(filmName);
            artist.addData(currFilm);
            currFilm.addData(artist);
        } catch (EntityNotFoundException ignore){}
    }
}

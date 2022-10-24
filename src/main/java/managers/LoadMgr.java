package managers;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.Scanner;

import libs.Stopwatch;
import models.*;


public class LoadMgr extends CatalogIMDB {

    final String filmFile = "files/films";
    final String castFile = "files/cast";

    public LoadMgr(){

    }

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
    public void loadData(){
        try{
            Stopwatch sw = new Stopwatch();
            System.out.println(" ");
            System.out.println("Loading Films database...");
            loadFilms();
            System.out.println("Successfully loaded " + films.getSize() + " films in " + sw.elapsedTime() + " seconds.");
            System.out.println("Loading Casting database...");
            sw.reset();
            loadCast();
            System.out.println("Successfully loaded " + casting.getSize() + " artists in " + sw.elapsedTime() + " seconds.");
            System.out.println("Sorting casting list...");
            sw.reset();
            Collections.sort(casting.getList());
            System.out.println("Casting list sorted in " + sw.elapsedTime() + " seconds.");;
            System.out.println(" ");
            System.out.println("");
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void loadFilms() throws IOException {
        Scanner sc = openFile(filmFile);
        String line = null;

        if(sc == null)
            throw new IOException();

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

    }

    private void loadCast() throws IOException {
        Scanner sc = openFile(castFile);
        String line = null;

        if(sc == null)
            throw new IOException();

        while (sc.hasNextLine()) {
            line = sc.nextLine();
            try{
                Artist artist = new Artist();
                String[] info = line.split("->");
                artist.populateInfo(info[0]);

                String[] filmList = null;

                if(info[1].contains("||")){
                    filmList = info[1].split("\\|");
                    for (String film : filmList)
                        if(film.length() > 0){
                            Film currFilm = films.binarySearch(film);
                            artist.addFilm(currFilm);
                            currFilm.addArtist(artist);
                        }

                } else{
                    Film currFilm = films.binarySearch(info[1]);
                    artist.addFilm(currFilm);
                    currFilm.addArtist(artist);
                }

                try{
                    casting.add(artist);
                } catch (IndexOutOfBoundsException e){
                    System.out.println(e.getMessage());
                }
            } catch (NullPointerException | NumberFormatException | ArrayIndexOutOfBoundsException ignored) {

            }
        }
    }

    private Scanner openFile(String file){
        try {
            return new Scanner(new FileReader("./data/" + file + ".txt"));
        } catch (FileNotFoundException e) {
            System.out.println("No existe ningún fichero llamado " + file);
        }
        return null;
    }
}

package managers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import modelos.CatalogoIMDB;
import modelos.Pelicula;

import java.io.IOException;

public class LoadMgr extends CatalogoIMDB {

    final String filmFile = "/smallerfiles/films_small";
    final String castFile = "";

    public LoadMgr(){

    }

    public void loadData(){
        try{
            loadFilms();
            loadCast();
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void loadFilms() throws IOException {
        Scanner sc = openFile(filmFile);
        String line = null;
        Pelicula film = new Pelicula();

        if(sc == null)
            throw new IOException();

        while (sc.hasNextLine()) {
            line = sc.nextLine();
            try{
                film.populateInfo(line.split("\\t"));
                peliculas.add(film);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
        }
        
    }

    private void loadCast(){

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

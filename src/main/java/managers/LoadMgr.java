package managers;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import modelos.CatalogoIMDB;
import modelos.*;


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

        if(sc == null)
            throw new IOException();

        while (sc.hasNextLine()) {
            line = sc.nextLine();
            try{
                Pelicula film = new Pelicula();
                film.populateInfo(line);
                peliculas.add(film);
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
                Interprete actor = new Interprete();
                String[] info = line.split("->");
                actor.populateInfo(info[0]);
                String[] films = info[0].split("\\||");
                try{
                    int i = 0;
                    while(films[i] != null){

                        peliculas.buscar(films[i]);
                        i++;
                    }
                } catch (IndexOutOfBoundsException e){
                    System.out.println(e.getMessage());
                }
                interpretes.add(actor);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
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

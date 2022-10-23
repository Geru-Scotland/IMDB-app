package managers;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import modelos.CatalogoIMDB;
import modelos.*;


public class LoadMgr extends CatalogoIMDB {

    final String filmFile = "/files/films";
    final String castFile = "smallerfiles/cast_tiny";

    public LoadMgr(){

    }

    public void loadData(){
        try{
            System.out.println("Loading Films database...");
            loadFilms();
            System.out.println("Films database fully loaded.");
            System.out.println("Loading Casting database...");
            loadCast();
            System.out.println("Casting database fully loaded.");
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
                Interprete casting = new Interprete();
                String[] info = line.split("->");
                casting.populateInfo(info[0]);

                String[] films = null;

                if(info[1].contains("||")){
                    films = info[1].split("\\|");
                    for (String film : films)
                        if(film.length() > 0)
                            casting.agregarPelicula(peliculas.buscar(film));

                } else
                    casting.agregarPelicula(peliculas.buscar(info[1]));

                try{
                    interpretes.add(casting);
                } catch (IndexOutOfBoundsException e){
                    System.out.println(e.getMessage());
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Actor SIZE: " + interpretes.getSize());
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

import java.util.NoSuchElementException;
import java.util.Scanner;

import exceptions.EntityNotFoundException;
import managers.LoadMgr;
import managers.CatalogIMDB;

public class AplicacionIMDB {

    public static void main(String[] args){

        CatalogIMDB cat = CatalogIMDB.getInstance();

        LoadMgr loadMgr = new LoadMgr();
        loadMgr.loadData();

        //Menu
        Scanner sc = new Scanner(System.in);
        int opcion =- 1;

        while(opcion != 0) {
            System.out.println("Escoja una opcion:");
            System.out.println("1. Mostrar informacion de pelicula");
            System.out.println("2. Mostrar informacion de interprete");
            System.out.println("3. Anadir voto a pelicula [0-10]");

            System.out.println("0. Salir");
            opcion = Integer.parseInt(sc.nextLine());
            switch(opcion) {
                case 1:
                    try{
                        System.out.println("Introduce el nombre de la pelicula: ");
                        String film = sc.nextLine();
                        try{
                            cat.displayFilmInfo(film);
                        } catch(EntityNotFoundException e){
                            System.out.println(e.getMessage());
                        }
                    } catch(NoSuchElementException | IllegalStateException ignore){}
                    break;
                case 2:
                    /*
                    Test, mejorar esto y gestionar bien posibles excepciones
                    Tanto de input, como posibles nullptr en busqueda.
                    */
                    try{
                        System.out.println("Introduce el nombre del artista: ");
                        String artist = sc.nextLine();
                        try{
                            cat.displayArtistInfo(artist);
                        } catch(EntityNotFoundException e){
                            System.out.println(e.getMessage());
                        }
                    } catch(NoSuchElementException | IllegalStateException ignore){}
                    break;
                case 3:
                    /*
                    Test, mejorar esto y gestionar bien posibles excepciones
                    Tanto de input, como posibles nullptr en busqueda.
                    */
                    System.out.println("Introduce el nombre de la pelicula: ");
                    String film = sc.nextLine();
                    System.out.println("Introduce tu voto: ");
                    try{
                        cat.addFilmVote(film, Integer.parseInt(sc.nextLine()));
                    } catch (IllegalArgumentException e){
                        System.out.println("Introduce un n�mero entre 0-10, por favor");
                    }
                    break;
                default:
                    break;
                // TO DO
            }
        }
        sc.close();
    }

}
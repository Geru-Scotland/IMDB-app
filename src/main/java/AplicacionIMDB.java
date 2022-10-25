import java.util.NoSuchElementException;
import java.util.Scanner;

import exceptions.EntityNotFoundException;
import exceptions.LoadMgrException;
import exceptions.NonValidInputValue;
import managers.LoadMgr;
import managers.CatalogIMDB;

public class AplicacionIMDB {

    public static void main(String[] args){

        try{
            CatalogIMDB cat = CatalogIMDB.getInstance();

            LoadMgr loadMgr = new LoadMgr();
            loadMgr.loadData();

            Scanner sc = new Scanner(System.in);
            int option =- 1;

            while(option != 0) {
                System.out.println("Escoja una opcion:");
                System.out.println("-----------------");
                System.out.println("[1] Mostrar informacion de pelicula.");
                System.out.println("[2] Mostrar informacion de interprete.");
                System.out.println("[3] Anadir voto a pelicula [0-10].");

                System.out.println("[0] Salir.");
                System.out.print(">> ");
                try{
                    option = Integer.parseInt(sc.nextLine());
                    clearConsole();
                    if(option < 0 || option > 3)
                        throw new NonValidInputValue();

                } catch(NumberFormatException | NonValidInputValue e){
                    clearConsole();
                    System.out.println("[EXCEPTION] Input incorrecto. Introduce un numero entre el 0-3.");
                    continue;
                }
                switch(option) {
                    case 1:
                        try{
                            System.out.println("[1] Introduce el nombre de la pelicula: ");
                            System.out.print(">> ");
                            String film = sc.nextLine();
                            clearConsole();
                            try{
                                cat.displayFilmInfo(film);
                            } catch(EntityNotFoundException e){
                                System.out.println(e.getMessage());
                            }
                        } catch(NoSuchElementException | IllegalStateException ignore){}
                        break;
                    case 2:
                        try{
                            System.out.println("[2] Introduce el nombre del artista: ");
                            System.out.print(">> ");
                            String artist = sc.nextLine();
                            clearConsole();
                            cat.displayArtistInfo(artist);
                        } catch(NoSuchElementException | IllegalStateException ignore){}
                        break;
                    case 3:
                        try{
                            System.out.println("[3] Introduce el nombre de la pelicula: ");
                            System.out.print(">> ");
                            String film = sc.nextLine();
                            System.out.println("[3] Introduce tu voto: ");
                            System.out.print(">> ");
                            cat.addFilmVote(film, Integer.parseInt(sc.nextLine()));
                            clearConsole();
                        } catch (NonValidInputValue | IllegalArgumentException | NoSuchElementException e){
                            clearConsole();
                            System.out.println("[EXCEPTION] Introduce un numero entre 0-10, por favor");
                        }
                        break;
                    default:
                        break;
                }
            }
            sc.close();
        } catch (LoadMgrException e){
            System.out.println(e.getMessage());
        }
    }

    public static void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
                Runtime.getRuntime().exec("cls");
            else if(os.contains("Linux") || os.contains("Mac"))
                Runtime.getRuntime().exec("clear");

            //Por si acaso, ya que si estamos en la consola del IDE no funciona.
            for(int i=0; i <= 20; i++)
                System.out.println(" ");
        }
        catch (final Exception ignore){}
    }
}
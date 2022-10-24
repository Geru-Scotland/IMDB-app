import java.util.Scanner;

import managers.LoadMgr;
import models.CatalogIMDB;

public class AplicacionIMDB {


    public static void main(String[] args){

        CatalogIMDB cat = CatalogIMDB.getInstance();

        LoadMgr loadMgr = new LoadMgr();
        loadMgr.loadData();

        //Menu
        Scanner sc = new Scanner(System.in);
        int opcion=-1;

        while(opcion!=0) {
            System.out.println("Escoja una opcion:");
            System.out.println("1. Mostrar informacion de pelicula");
            System.out.println("2. Mostrar informacion de interprete");
            System.out.println("3. Anadir voto a pelicula");

            System.out.println("0. Salir");
            opcion = Integer.parseInt(sc.nextLine());
            switch(opcion) {
                case 1:
                    //TO DO
                    break;
                // TO DO
            }
        }
        sc.close();
    }

}
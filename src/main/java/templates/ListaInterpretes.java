package templates;

import java.util.ArrayList;
import models.*;

public class ListaInterpretes {

    private ArrayList<Artist> listaArtists;
   
   
   public ListaInterpretes(){
        listaArtists = new ArrayList<Artist>();
   }
   
    /**
     * Añade un intérprete a la lista
     * @param inter Intérprete a añadir
     */
    public void añadirInterprete(Artist inter){
            int i = 0;
            boolean existe = false;
            while(i< listaArtists.size() && !existe) {
                if (!listaArtists.get(i).equals(inter)) {
                    i++;
                }
                else {
                    existe = true;
                }
            }
            if (existe = false) listaArtists.add(inter);
            else System.out.println("El intérprete ya forma parte de la lista");
	}


    /**
     * Busca un intérprete en la lista y lo devuelve
     * @param nombre Nombre del intérprete a buscar
     * @return el Artist (si está en la lista), null en caso contrario */
    public Artist buscarInterprete(String nombre){
        int i = 0;
		while(i< listaArtists.size()) {
			if (!listaArtists.get(i).getName().equals(nombre)) {
				i++;
			}
			else {
				return listaArtists.get(i);
			}
		}
        return null;
    }
}

/*Caca */

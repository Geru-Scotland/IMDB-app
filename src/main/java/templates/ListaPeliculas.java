package templates;
import models.*;
import java.util.ArrayList;

public class ListaPeliculas {

    private ArrayList<Film> listaFilms;
    
    public ListaPeliculas(){
        listaFilms = new ArrayList<Film>();
    }
    /**
     * Añade una película a la lista
     * @param pel Película a añadir
     */
    public void anadirPelicula(Film pel){
       int i = 0;
		boolean existe = false;
		while(i< listaFilms.size() && !existe) {
			if (!listaFilms.get(i).equals(pel)) {
				i++;
			}
			else {
				existe = true;
			}
		}
		if (existe = false) listaFilms.add(pel);
		else System.out.println("La película ya forma parte de la lista"); 
    }

    /**
     * Busca una película en la lista y la devuelve
     * @param titulo Título de la película a buscar
     * @return la Película (si está en la lista), null en caso contrario */
    public Film buscarPelicula(String titulo){
        int i = 0;
		while(i< listaFilms.size()) {
			if (!listaFilms.get(i).getTitle().equals(titulo)) {
				i++;
			}
			else {
				return listaFilms.get(i);
			}
		}
        return null;
    }
}


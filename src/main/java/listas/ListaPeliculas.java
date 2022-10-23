package listas;
import modelos.*;
import java.util.ArrayList;

public class ListaPeliculas {

    private ArrayList<Pelicula> listaPeliculas;
    
    public ListaPeliculas(){
        listaPeliculas = new ArrayList<Pelicula>();
    }
    /**
     * Añade una película a la lista
     * @param pel Película a añadir
     */
    public void anadirPelicula(Pelicula pel){
       int i = 0;
		boolean existe = false;
		while(i<listaPeliculas.size() && !existe) {
			if (!listaPeliculas.get(i).equals(pel)) {
				i++;
			}
			else {
				existe = true;
			}
		}
		if (existe = false) listaPeliculas.add(pel);
		else System.out.println("La película ya forma parte de la lista"); 
    }

    /**
     * Busca una película en la lista y la devuelve
     * @param titulo Título de la película a buscar
     * @return la Película (si está en la lista), null en caso contrario */
    public Pelicula buscarPelicula(String titulo){
        int i = 0;
		while(i<listaPeliculas.size()) {
			if (!listaPeliculas.get(i).getTitulo().equals(titulo)) {
				i++;
			}
			else {
				return listaPeliculas.get(i);
			}
		}
        return null;
    }
}


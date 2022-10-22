package listas;
import modelos.*;

public class ListaInterpretes {

    private ArrayList<Interprete> listaInterpretes;
   
   
   public ListaInterpretes(){
        listaInterpretes = new ArrayList<Interprete>();
   }
   
    /**
     * Añade un intérprete a la lista
     * @param inter Intérprete a añadir
     */
    public void añadirInterprete(Interprete inter){
            int i = 0;
            boolean existe = false;
            while(i<listaInterpretes.size() && !existe) {
                if (!listaInterpretes.get(i).equals(inter)) {
                    i++;
                }
                else {
                    existe = true;
                }
            }
            if (existe = false) listaInterpretes.add(inter);
            else System.out.println("El intérprete ya forma parte de la lista");
	}


    /**
     * Busca un intérprete en la lista y lo devuelve
     * @param nombre Nombre del intérprete a buscar
     * @return el Interprete (si está en la lista), null en caso contrario */
    public Interprete buscarInterprete(String nombre){
        int i = 0;
		while(i<listaInterpretes.size()) {
			if (!listaInterpretes.get(i).getNombre().equals(nombre)) {
				i++;
			}
			else {
				return listaInterpretes.get(i);
			}
		}
        return null;
    }
}

/*Caca */

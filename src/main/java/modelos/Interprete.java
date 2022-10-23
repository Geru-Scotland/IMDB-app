package modelos;
import java.util.ArrayList;

public class Interprete implements Comparable<Interprete> {

    private ArrayList<Pelicula> peliculasParticip;
    private double rating;

    public void setRating(double rat){
        this.rating = rat;
    }
    /**
     * Calcula y asigna el rating del intérprete en base al rating de sus películas
     */
    public void calcularRating(){

        double totalRating = 0.0;
        int totalVotos = 0;
        double total = 0.0;
        for (int i = 0; i < peliculasParticip.size(); i++){
            if (peliculasParticip.get(i).getRating() != -1){
                totalRating += peliculasParticip.get(i).getRating();
                totalVotos += peliculasParticip.get(i).getNumVotos();
            }
        }

        total = totalRating / totalVotos;
        this.setRating(total);
    }

    /**
     * Añade una película al intérprete
     * @param pel Película a añadir
     * POST: El rating del intérprete NO se modifica en este momento */
    public void anadirPelicula(Pelicula pel){
        int i = 0;
        boolean existe = false;
        while(i<peliculasParticip.size() && !existe) {
            if (!peliculasParticip.get(i).equals(pel)) {
                i++;
            }
            else {
                existe = true;
            }
        }
        if (existe = false) peliculasParticip.add(pel);
        else System.out.println("La película ya forma parte de la lista");
    }

    public String getNombre() {
        return null;
    }

    @Override
    public int compareTo(Interprete o) {
        return 0;
    }
}

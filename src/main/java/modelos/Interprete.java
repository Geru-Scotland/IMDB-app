package modelos;
import java.util.ArrayList;

public class Interprete extends Entity implements Comparable<Interprete>, Model {

    private ArrayList<Pelicula> listaPeliculas;
    private double rating;
    private String name;

    public Interprete(){
        listaPeliculas = new ArrayList<>();
    }

    @Override
    public void populateInfo(String info){
        name = info;
    }


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
        for (int i = 0; i < listaPeliculas.size(); i++){
            if (listaPeliculas.get(i).getRating() != -1){
                totalRating += listaPeliculas.get(i).getRating();
                totalVotos += listaPeliculas.get(i).getVotes();
            }
        }

        total = totalRating / totalVotos;
        this.setRating(total);
    }

    /**
     * Añade una película al intérprete
     * @param pel Película a añadir
     * POST: El rating del intérprete NO se modifica en este momento */
    public void agregarPelicula(Pelicula pel){
        listaPeliculas.add(pel);
    }

    public String getNombre() {
        return null;
    }

    @Override
    public int compareTo(Interprete o) {
        return 0;
    }

    @Override
    public String getIdentifier() {
        return name;
    }
}

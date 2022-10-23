package modelos;
import java.util.ArrayList;

public class Pelicula implements Comparable<Pelicula> {
    
    private double rating;
    private int numVotos;
    private String titulo;

    private ArrayList <Interprete> interpreteParticip;

    public double getRating(){
        return rating;
    }

    public void setRating(double rat){
        this.rating = rat;
    }

    public int getNumVotos(){
        return numVotos;
    }

    public void setNumVotos(int vot){
        this.numVotos = vot;
    }

    public String getTitulo(){
        return titulo;
    }

    public Pelicula(){
        interpreteParticip = new ArrayList<Interprete>(); 
    }

    /**
     * Añade un intérprete a la película
     * @param inter Intérprete a añadir
     */
    public void anadirInterprete(Interprete inter){
        int i = 0;
        boolean existe = false;
        while(i<interpreteParticip.size() && !existe) {
            if (!interpreteParticip.get(i).equals(inter)) {
                i++;
            }
            else {
                existe = true;
            }
        }
        if (existe = false) interpreteParticip.add(inter);
        else System.out.println("El interprete ya forma parte de la lista");
    }

    /**
     * Añade un nuevo voto a la película.
     * POST: se han recalculado los ratings de sus intérpretes
     * @param voto
     */
    public void anadirVoto(float voto) {
        if (this.getRating()!=-1){
            this.setRating((this.getRating()*this.getNumVotos() + voto)/(this.getNumVotos() + 1));
        } else {
            this.setNumVotos(1);
            this.setRating(voto);
        }
        
        for(int i = 0; i<interpreteParticip.size(); i++){
            interpreteParticip.get(i).calcularRating();
        }
    }

    @Override
    public int compareTo(Pelicula o) {
        return 0;
    }
}

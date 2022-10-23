package modelos;
import java.util.ArrayList;

public class Pelicula implements Comparable<Pelicula> {

    private String title;
    private int year;
    private double rating;
    private int votes;

    private ArrayList <Interprete> interpreteParticip;

    public Pelicula(){
        interpreteParticip = new ArrayList<Interprete>(); 
    }

    public void populateInfo(String[] info){
        title = info[0];
        year = Integer.parseInt(info[1]);
        rating = Double.parseDouble(info[2]);
        votes = Integer.parseInt(info[3]);
    }

    public String getTitle(){ return title; }

    public int getYear(){ return year; }

    public double getRating(){ return rating; }

    public int getVotes(){ return votes; }

    public void setRating(double rat){
        this.rating = rat;
    }

    public void setVotes(int vot){
        this.votes = vot;
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
            this.setRating((this.getRating()*this.getVotes() + voto)/(this.getVotes() + 1));
        } else {
            this.setVotes(1);
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

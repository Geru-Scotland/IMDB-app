package modelos;
import java.util.ArrayList;

public class Film implements Comparable<Film>, Model {

    private String title;
    private int year;
    private double rating;
    private int votes;

    private ArrayList <Artist> artistParticip;

    public Film(){
        artistParticip = new ArrayList<Artist>();
    }

    public Film(String title){
        this.title = title;
    }

    @Override
    public void populateInfo(String info){
        String[] elem = info.split("\\t");
        title = elem[0];
        year = Integer.parseInt(elem[1]);
        rating = Double.parseDouble(elem[2]);
        votes = Integer.parseInt(elem[3]);
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
    public void anadirInterprete(Artist inter){
        int i = 0;
        boolean existe = false;
        while(i< artistParticip.size() && !existe) {
            if (!artistParticip.get(i).equals(inter)) {
                i++;
            }
            else {
                existe = true;
            }
        }
        if (existe = false) artistParticip.add(inter);
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
        
        for(int i = 0; i< artistParticip.size(); i++){
            artistParticip.get(i).calcularRating();
        }
    }

    @Override
    public int compareTo(Film o) {
        if(o.getTitle().compareTo(getTitle()) > 0)
            return 1;
        return 0;
    }

    @Override
    public String getIdentifier() {
        return title;
    }
}


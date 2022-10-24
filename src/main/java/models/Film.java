package models;
import java.util.ArrayList;

public class Film implements Comparable<Film>, Entity {

    private String title;
    private int year;
    private double rating;
    private int votes;

    private ArrayList<Artist> casting;

    public Film(){
        casting = new ArrayList<Artist>();
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
    public double getTotalRating(){ return rating * votes; }
    public int getVotes(){ return votes; }
    public void setRating(double rat){
        rating = rat;
    }
    public void setVotes(int vot){
        votes = vot;
    }

    /**
     * Añade un nuevo voto a la película.
     * POST: se han recalculado los ratings de sus intérpretes
     * @param voto
     */
    public void addVote(float voto) {
        if (this.getRating()!=-1){
            this.setRating((this.getRating()*this.getVotes() + voto)/(this.getVotes() + 1));
        } else {
            this.setVotes(1);
            this.setRating(voto);
        }

        for(Artist artist : casting)
            artist.computeRating();
    }

    public void addArtist(Artist art){
        casting.add(art);
    }

    public ArrayList<Artist> getCasting(){
        return casting;
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


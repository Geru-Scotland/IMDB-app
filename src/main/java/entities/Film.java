package entities;
import entities.models.Entity;

import java.util.ArrayList;

public class Film implements Comparable<Film>, Entity {

    private String title;
    private int year;
    private double rating;
    private int votes;
    private int artistNum;

    private final ArrayList<Artist> casting;

    public Film(){ casting = new ArrayList<Artist>(); }

    public int getYear(){ return year; }
    public int getVotes() {return votes; }

    public void addVote(float score) {
        if (rating!=-1){
            rating = (rating*votes + score)/(votes + 1);
        } else {
            votes = 1;
            rating = score;
        }

        /**
         * Actualizar el rating para todos los artistas.
         */
        for(Artist artist : casting)
            artist.computeRating();
    }


    public ArrayList<Artist> getCasting(){
        return casting;
    }

    /**
     * Overrides
     */

    @Override
    public void addData(Object obj){
        if(!(obj instanceof Artist))
            return;
        casting.add((Artist)obj);
        artistNum++;
    }

    @Override
    public double getRating(boolean weighted) { return weighted ? rating * votes : rating; }

    @Override
    public void populateInfo(String info){
        String[] elem = info.split("\\t");
        title = elem[0];
        year = Integer.parseInt(elem[1]);
        rating = Double.parseDouble(elem[2]);
        votes = Integer.parseInt(elem[3]);
    }

    @Override
    public int compareTo(Film o) {
        if(o.getIdentifier().compareTo(title) > 0)
            return 1;
        return 0;
    }

    @Override
    public String getIdentifier() {
        return title;
    }
}


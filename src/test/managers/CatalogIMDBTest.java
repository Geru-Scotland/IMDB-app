package managers;

import exceptions.LoadMgrException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class CatalogIMDBTest {

    CatalogIMDB cat;

    @BeforeEach
    void setUp() {
        cat = CatalogIMDB.getInstance();
        try{
            LoadMgr loadMgr = new LoadMgr();
            loadMgr.loadData();
        } catch(LoadMgrException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void getInstance() {
    }

    @Test
    void checkNonExistantFilmsFromArtists() {
    }

    @Test
    void addFilmVote() {
    }

    @Test
    void displayFilmInfo() {
    }

    @Test
    void displayArtistInfo() {
    }

    @Test
    void displayData() {
    }

    @Test
    public void addFilmVoteTest(){

    }

    @Test
    public void newTest(){

    }
}
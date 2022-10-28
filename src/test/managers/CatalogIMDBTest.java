package managers;

import exceptions.EntityNotFoundException;
import exceptions.LoadMgrException;
import exceptions.NonValidInputValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class CatalogIMDBTest {

    static CatalogIMDB cat;

    @BeforeAll
    static void setUp() {
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
        Assertions.assertThrows(EntityNotFoundException.class, () -> cat.addFilmVote("Non existent film", 8));
        Assertions.assertThrows(NonValidInputValue.class, () -> cat.addFilmVote("Fight Club", 12));

        Assertions.assertDoesNotThrow(()-> cat.addFilmVote("Fight Club", 9));
        Assertions.assertDoesNotThrow(()-> cat.addFilmVote("I Love Sydney", 4));
    }
}
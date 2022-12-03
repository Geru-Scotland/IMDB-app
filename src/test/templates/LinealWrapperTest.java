package templates;

import exceptions.LoadMgrException;
import managers.CatalogIMDB;
import managers.LoadMgr;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinealWrapperTest {

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
    void add() {
    }

    @Test
    void getList() {
    }

    @Test
    void search() {
    }

    @Test
    void binarySearch() {
    }
}
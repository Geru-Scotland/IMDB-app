package managers;

import entities.Film;
import exceptions.EmptyDataException;
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
            LoadMgr loadMgr = new LoadMgr("smallerfiles/films_tiny", "smallerfiles/cast_tiny");
            loadMgr.loadData();
        } catch(LoadMgrException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void addFilmVoteTest(){
        Assertions.assertThrows(EntityNotFoundException.class, () -> cat.addFilmVote("Non existent film", 8));
        Assertions.assertThrows(NonValidInputValue.class, () -> cat.addFilmVote("Fight Club", 12));

        Assertions.assertDoesNotThrow(()-> cat.addFilmVote("Fight Club", 9));

        // Se necesitan los ficheros grandes:
        Assertions.assertDoesNotThrow(()-> cat.addFilmVote("I Love Sydney", 4));
    }

    @Test
    void setCastingTest() {
    }

    @Test
    void removeFilmTest() throws EmptyDataException, EntityNotFoundException {

        /**
         * Intento de eliminaci�n de peliculas no existentes, deben lanzar excepci�n.
         */
        Assertions.assertThrows(EntityNotFoundException.class, () -> cat.removeFilm("Wrong film"));
        Assertions.assertThrows(EntityNotFoundException.class, () -> cat.removeFilm("Filmatronss"));

        /**
         * Me aseguro de la existencia de un artista en concreto.
         */
        Assertions.assertThrows(EntityNotFoundException.class, () -> cat.getCasting().search("Chiesatypo, Ricardo"));
        Assertions.assertDoesNotThrow(() -> cat.getCasting().search("Vera, Brandon"));
        Assertions.assertDoesNotThrow(() -> cat.getCasting().search("Chiesa, Ricardo"));

        /**
         * Eliminaci�n de pel�cula existente, no debe de lanzar exepci�n.
         */
        Assertions.assertDoesNotThrow(() -> cat.removeFilm("Fights"));

        /**
         * Borramos una pel�cula m�s.
         */
        Film<?> film = cat.removeFilm("Filmatron");

        /**
         * Despues de las 2 eliminaciones, debemos tener 998 peliculas (films_tiny.txt)
         */
        Assertions.assertEquals(998, cat.getFilms().size());

        /**
         * El artista Chiesa, Rocardo - al �nicamente haber participado en la pelicula anterior
         */
        Assertions.assertThrows(EntityNotFoundException.class, () -> cat.getCasting().search("Chiesa, Ricardo"));

    }

    @Test
    void showStatusAfterDeletionTest() {
    }
}
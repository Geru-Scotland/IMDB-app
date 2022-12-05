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
         * Intento de eliminación de peliculas no existentes, deben lanzar excepción.
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
         * Eliminación de película existente, no debe de lanzar exepción.
         */
        Assertions.assertDoesNotThrow(() -> cat.removeFilm("Fights"));

        /**
         * Borramos una película más.
         */
        Film<?> film = cat.removeFilm("Filmatron");

        /**
         * Despues de las 2 eliminaciones, debemos tener 998 peliculas (films_tiny.txt)
         */
        Assertions.assertEquals(998, cat.getFilms().size());

        /**
         * El artista Chiesa, Rocardo - al únicamente haber participado en la pelicula anterior
         */
        Assertions.assertThrows(EntityNotFoundException.class, () -> cat.getCasting().search("Setton, Carolina"));

    }

    @Test
    void showStatusAfterDeletionTest() {
    }
}
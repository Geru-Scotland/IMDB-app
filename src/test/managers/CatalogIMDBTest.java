package managers;

import entities.Film;
import exceptions.EmptyDataException;
import exceptions.EntityNotFoundException;
import exceptions.LoadMgrException;
import exceptions.NonValidInputValue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class CatalogIMDBTest {

    static CatalogIMDB cat;

    @BeforeAll
    static void setUp() {
        cat = CatalogIMDB.getInstance();
        try{
            LoadMgr loadMgr = new LoadMgr("files/films", "files/cast");
            loadMgr.loadData();
        } catch(LoadMgrException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void addFilmVoteTest(){
        Assertions.assertThrows(EntityNotFoundException.class, () -> cat.addFilmVote("Non existent film", 8));
        Assertions.assertThrows(NonValidInputValue.class, () -> cat.addFilmVote("Fight Club", 12));

        // Se necesitan los ficheros grandes:
        Assertions.assertDoesNotThrow(()-> cat.addFilmVote("Fight Club", 9));
        Assertions.assertDoesNotThrow(()-> cat.addFilmVote("I Love Sydney", 4));
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
         * NOTA: Necesita los ficheros grandes.
         * El artista Rotstein, Sebastian ha participado en 2 peliculas.
         */
        Assertions.assertEquals(3, cat.getCasting().search("Rotstein, Sebastian").getDataList().size());

        /**
         * Nos aseguramos da la existencia de una pelicula.
         */
        Assertions.assertDoesNotThrow(() -> cat.getFilms().search("Filmatron"));

        /**
         * La borramos.
         */
        Film<?> film = cat.removeFilm("Filmatron");

        /**
         * Buscamos la pelicula de manera explicita, se ha de lanzar una excepción.
         */
        Assertions.assertThrows(EntityNotFoundException.class, () -> cat.getFilms().search(film.getIdentifier()));

        /**
         * Despues de las 2 eliminaciones, debemos tener 998 peliculas (films_tiny.txt).
         * Si se utilizan los ficheros grandes: 692084 peliculas y 2792967 artistas.
         */
        Assertions.assertEquals(692084, cat.getFilms().size());
        Assertions.assertEquals(2792967, cat.getCasting().size());

        /**
         * El artista Chiesa, Rocardo - al únicamente haber participado en la pelicula anterior.
         */
        Assertions.assertThrows(EntityNotFoundException.class, () -> cat.getCasting().search("Chiesa, Ricardo"));

        /**
         * Después del borrado de Filmatron, en nuestro sistema ha de constar que Rotstein, Sebastian
         * ha participado en 2 peliculas.
         */
        Assertions.assertEquals(2, cat.getCasting().search("Rotstein, Sebastian").getDataList().size());
    }

    @AfterAll
    static void showInfo(){
        System.out.println(" ");
        System.out.println("[POST-TESTS]");
        System.out.println("Peliculas: " + cat.getFilms().size() + " | Artistas: " + cat.getCasting().size());
    }
}
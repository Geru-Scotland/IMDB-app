package exceptions;

/**
 * Excepción a tratar cuando el motor de búsqueda no
 * encuentre una entidad en una determinada colección
 * de datos.
 */
public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String error){ super(error); }
}
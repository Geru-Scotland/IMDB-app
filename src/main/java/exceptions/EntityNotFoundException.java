package exceptions;

/**
 * Excepci�n a tratar cuando el motor de b�squeda no
 * encuentre una entidad en una determinada colecci�n
 * de datos.
 */
public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String error){ super(error); }
}
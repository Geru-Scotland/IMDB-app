package exceptions;

/**
 * Excepción a tratar cuando la colección de datos de una entidad
 * esté vacía.
 */
public class EmptyDataException extends Exception {
    public EmptyDataException (String error) { super(error); }
}

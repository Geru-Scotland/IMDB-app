package exceptions;

/**
 * Excepci�n a tratar cuando la colecci�n de datos de una entidad
 * est� vac�a.
 */
public class EmptyDataException extends Exception {
    public EmptyDataException (String error) { super(error); }
}

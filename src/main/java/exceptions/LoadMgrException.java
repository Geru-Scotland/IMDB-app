package exceptions;

/**
 * Excepción que deberá de ser tratada cuando ocurra algún error
 * relacionado a la carga inicial de información.
 */
public class LoadMgrException extends Exception {
    public LoadMgrException(String error){ super(error); }
}

package exceptions;

/**
 * Excepci�n que deber� de ser tratada cuando ocurra alg�n error
 * relacionado a la carga inicial de informaci�n.
 */
public class LoadMgrException extends Exception {
    public LoadMgrException(String error){ super(error); }
}

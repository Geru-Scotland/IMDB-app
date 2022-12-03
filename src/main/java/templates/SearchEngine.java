package templates;

import exceptions.EntityNotFoundException;

/**
 * Clase abstracta que define los métodos que toda subclase
 * debe implementar.
 * @param <T> Tipo del Elemento encontrado.
 */
public abstract class SearchEngine<T> {
    public SearchEngine() {}
    public abstract T linealSearch(String str) throws EntityNotFoundException;
    public abstract T search(String str) throws EntityNotFoundException;
}
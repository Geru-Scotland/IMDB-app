package templates;

import exceptions.EntityNotFoundException;

public abstract class SearchEngine<T> {
    public SearchEngine() {}
    public abstract T search(String str) throws EntityNotFoundException;
    public abstract T binarySearch(String str) throws EntityNotFoundException;
}

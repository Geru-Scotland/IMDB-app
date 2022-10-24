package templates;

public abstract class SearchEngine<T> {
    public SearchEngine() {}
    public abstract T search(String str);
    public abstract T binarySearch(String str);
}

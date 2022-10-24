package entities.models;

public interface Entity {
    void populateInfo(String info);
    String getIdentifier();
    double getRating(boolean opt);
    void addData(Object o);
}
package entities.models;

import exceptions.EmptyDataException;

import java.util.ArrayList;

public interface Entity<T> {
    void populateInfo(String info);
    String getIdentifier();
    double getRating(boolean opt);
    int getDataNum();
    ArrayList<T> getDataList() throws EmptyDataException;
    void addData(T o);
}
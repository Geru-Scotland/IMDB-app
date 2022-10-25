package entities.models;

import exceptions.EmptyDataException;

import java.util.ArrayList;

public interface Entity<T> {
    void populateInfo(String info);
    void addData(T obj);
    String getIdentifier();
    int getDataNum();
    double getRating(boolean opt);
    ArrayList<T> getDataList() throws EmptyDataException;
}
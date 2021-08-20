package ru.denis.financeApp.interfaces;

public interface interfaceFavorites {
    void showDetailsFromCache(String ticker);
    void refresh();
    void setAdapter();
}

package ru.denis.financeApp.interfaces;

import java.io.IOException;

import ru.denis.financeApp.presenter.controller;

public interface forModel {
    Integer loadStock(String str) throws IOException;
    void addToFavoritesAft(String ticker, String[] str);
    void removeFromFavorites(String ticker);
    String[] addToFavoritesPre(String ticker, int id) throws IOException;
    String[][] getHistory(String ticker, int id) throws IOException;
    String[][] getDataFromYahoo() throws IOException;
    String setListFromCache(Integer i, String id);
    Integer setImageForFav(Integer i);
    void attachView(controller controller);
}

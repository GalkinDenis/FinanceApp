package ru.denis.financeApp.interfaces;

import java.io.IOException;

public interface interfaceViews {
    void loadStartList(String ticker, String[] str, int count) throws IOException;
    void showHistory(String v);
}

package ru.denis.financeApp.model;

import android.app.Activity;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.denis.financeApp.interfaces.forModel;
import ru.denis.financeApp.presenter.controller;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;

//Активити модели.
public class model extends Activity implements forModel {
    Disposable run;
    controller controller;

    float setFloatValue;
    int getPercentValueOfTotalArrayVolume;

    private final String[] str = new String[4];
    private final String[][]setForHistoryList = new String[13][5];
    private final String[][]setForTrendList = new String[23][5];

    public void attachView(controller controller) {
        this.controller = controller;
    }

    //Запрос лейбла из кеша.
    public Integer setImageForFav(Integer i) {
        return tickerPlusLabel.choiceLabel(tickerPlusLabel.tickerList.get(i));
    }

    //Запрос данных из кеша.
    @Override
    public String setListFromCache(Integer i, String id) {
        switch(id) {
            case "change":
            return tickerPlusLabel.changeList.get(i);

            case "price":
            return tickerPlusLabel.priceList.get(i) + "$";

            case "index":
            return String.valueOf(Float.parseFloat(String.valueOf(tickerPlusLabel.changeIndexList.get(i))));

            case "ticker":
            return tickerPlusLabel.tickerList.get(i);

            case "name":
            return tickerPlusLabel.stockNameStack.get(tickerPlusLabel.tickerList.get(i));

            default:
                return "";
        }
    }

    //Запрос данных с yahoo-finance.
    @Override
    public String[][] getDataFromYahoo() throws IOException {
        for(int i = 0; i < tickerPlusLabel.tickers.length; i++) {
            Stock stock = YahooFinance.get(tickerPlusLabel.tickers[i]);
            setForTrendList[i][0] = "(" + stock.getQuote().getChangeInPercent() + "%)";
            setForTrendList[i][1] = stock.getQuote().getPrice() + "$";
            setForTrendList[i][2] = String.valueOf(Float.parseFloat(String.valueOf(stock.getQuote().getChange())));
            setForTrendList[i][3] = tickerPlusLabel.tickers[i];
            setForTrendList[i][4] = tickerPlusLabel.stockNameStack.get(tickerPlusLabel.tickers[i]);
            setFloatValue = i+1;
            getPercentValueOfTotalArrayVolume = (int) ((setFloatValue/tickerPlusLabel.tickers.length)*100);
            run = Single.fromCallable(() -> getPercentValueOfTotalArrayVolume)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(db -> controller.setProgressBar(db));
        }
        return setForTrendList;
    }

    //Запрос данных с yahoo-finance для добавления в избранное.
    @Override
    public String[] addToFavoritesPre(String ticker, int id) throws IOException {
        Stock stock = YahooFinance.get(ticker);
        str[0] = String.valueOf(stock.getQuote().getChangeInPercent());
        str[1] = String.valueOf(stock.getQuote().getPrice());
        str[2] = String.valueOf(Float.parseFloat(String.valueOf(stock.getQuote().getChange())));
        if(id == 2) {
            BigDecimal percent = stock.getDividend().getAnnualYieldPercent();
            if(percent != null) {
                str[3] = String.valueOf(percent);
                return str;
            }else{
                str[3] = "-";
                return str;
            }
        }
        return str;
    }

    //Добавление в избранное.
    @Override
    public void addToFavoritesAft(String ticker, String[] str) {
        //Добавление в кеш.
        if (!tickerPlusLabel.tickerList.contains(ticker)) {
            tickerPlusLabel.tickerList.add(ticker);
            tickerPlusLabel.priceList.add(String.valueOf(str[1]));
            tickerPlusLabel.changeList.add(String.valueOf(str[0]));
            tickerPlusLabel.changeIndexList.add(String.valueOf(str[2]));
            if (str[3] == null) {
                tickerPlusLabel.dividentList.add("-");
            } else {
                tickerPlusLabel.dividentList.add(str[3]);
            }
        }
    }

    //Запрос исторической сводки с yahoo-finance.
    @Override
    public String[][] getHistory(String ticker, int id) throws IOException {
        List<HistoricalQuote> history = YahooFinance.get(ticker).getHistory();
        for(int i = 0; i < history.size(); i++) {
            HistoricalQuote quote = history.get(i);
            setForHistoryList[i][0] = quote.getSymbol();
            setForHistoryList[i][1] = convertDate(quote.getDate());
            setForHistoryList[i][2] = String.valueOf(quote.getHigh());
            setForHistoryList[i][3] = String.valueOf(quote.getLow());
            setForHistoryList[i][4] = String.valueOf(quote.getClose());
        }
        return setForHistoryList;
    }

    //Функция форматирования даты, полученной с yahoo-finance.
    private String convertDate(Calendar cal) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(cal.getTime());
    }

    //Удаления позиции из избранного(кеша).
    @Override
    public void removeFromFavorites(String ticker) {
        int index = tickerPlusLabel.tickerList.indexOf(ticker);
        tickerPlusLabel.tickerList.remove(index);
        tickerPlusLabel.priceList.remove(index);
        tickerPlusLabel.changeList.remove(index);
        tickerPlusLabel.changeIndexList.remove(index);
        tickerPlusLabel.dividentList.remove(index);
    }

    //Проверка на пустую сторку в поиске и существование запрашиваемого тикера.
    @Override
    public Integer loadStock(String str) throws IOException {
        if(!str.isEmpty() && YahooFinance.get(str) != null) {
            return 1;
        }else {
            return 0;
        }
    }
}




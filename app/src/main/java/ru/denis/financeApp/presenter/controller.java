package ru.denis.financeApp.presenter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.denis.financeApp.interfaces.forModel;
import ru.denis.financeApp.interfaces.interfaceFavorites;
import ru.denis.financeApp.interfaces.interfaceHistory;
import ru.denis.financeApp.interfaces.interfaceSearch;
import ru.denis.financeApp.interfaces.interfaceShowDetails;
import ru.denis.financeApp.interfaces.interfaceViews;
import ru.denis.financeApp.model.model;
import ru.denis.financeApp.trend_fragment.TrendingListFragment;

//Активити контроллера.
public class controller extends Activity {
    Disposable run;

    TrendingListFragment trendView;
    interfaceViews views;
    interfaceSearch viewsSearch;
    interfaceFavorites viewsFavorites;
    interfaceShowDetails viewsShowDetails;
    interfaceHistory viewsHistory;

    public forModel modelView;

    //Конструктор контроллера.
    public controller(model model) {
        this.modelView = model;
        modelView.attachView(this);
    }

    //Сохранение ссылок на различных views.
    public void attachView(TrendingListFragment view) {
        this.trendView = view;
    }
    public void attachView(interfaceViews view) { this.views = view; }
    public void attachView(interfaceSearch view) {
        this.viewsSearch = view;
    }
    public void attachView(interfaceFavorites view) {
        this.viewsFavorites = view;
    }
    public void attachView(interfaceShowDetails view) {
        this.viewsShowDetails = view;
    }
    public void attachView(interfaceHistory view) {
        this.viewsHistory = view;
    }
    //

    //Функция для загрузки данных с yahoo-finance
    // и для кеширования.
    public void loadST(String ticker, int count, int id) {
        if(id == 5) {
            //Загрузка данных.
            run = Single.fromCallable(() -> modelView.getHistory(ticker, id))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(db -> viewsHistory.loadHistory(db));
        }else{
            //Кеширование во временные массивы.
            run = Single.fromCallable(() -> modelView.addToFavoritesPre(ticker, id))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(db -> views.loadStartList(ticker, db, count));
        }
    }

    //Вывод активити с исторической сводкой.
    public void controllerClickShowDetails(String ticker, View v, int i) {
        if(i == 1) {
            //Запуск активити historyList.java.
            trendView.find(ticker);
        }else if(i == 2) {
            //Проверка на пустую сторку в поиске и существование запрашиваемого тикера.
            run = Single.fromCallable(() -> modelView.loadStock(ticker))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(db -> viewsSearch.findStock(ticker, db));
        }else if(i == 3) {
            //Запуск активити historyList.java.
            viewsShowDetails.showHistory(ticker);
        }
        else if(i == 4) {
            views.showHistory(ticker);
        }
    }

    //Добавление в избранное(кеш).
    public void controllerClickAddToFavorites(String ticker) {
        run = Single.fromCallable(() -> modelView.addToFavoritesPre(ticker, 0))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(db -> modelView.addToFavoritesAft(ticker, db));
    }

    //Удаление из избранного(кеша).
    public void controllerRemoveFromCache(String ticker) {
        modelView.removeFromFavorites(ticker);
        viewsFavorites.refresh();
    }

    //Запуск активити showDetailsFromCache.java.
    public void controllerShowDetailsFromCache(String ticker) {
        viewsFavorites.showDetailsFromCache(ticker);
    }

    //Вывод данных из кеша.
    public void loadDetailsFromCache(Intent intent) {
        viewsShowDetails.DetailsFromCache(intent);
    }

    //Функция, вызываемая моделью(model.java), для демонстрации прогресса загрузки.
    public void setProgressBar(Integer i) {
        trendView.setProgressBar(i);
    }



    //Инициализация адаптеров.
    public void setList(String type) {
        if(type.equals("Trends")) {
            run = Single.fromCallable(() -> modelView.getDataFromYahoo())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(db -> trendView.setAdapter(db));
            //Установка временного пустого списка в процессе обработки сетевого запроса.
            trendView.setAdapter(new String[0][0]);
        }else if(type.equals("Favorites")) {
            viewsFavorites.setAdapter();
        }
    }

    //Запрос лейбла из кеша.
    public Integer setImageForFav(Integer i) {
        return modelView.setImageForFav(i);
    }

    //Запрос данных из кеша.
    public String setListForFav(Integer i, String id) {
        return modelView.setListFromCache(i, id);
    }

    //Вывод тоаста с сообщением об отсутствии интернета.
    public void showToastFromSearch() {
        viewsSearch.showToast();
    }

    //Вывод тоаста с сообщением об отсутствии интернета.
    public void showToastFromTrend() {
        trendView.showToast();
    }

    //Вывод тоаста с сообщением об отсутствии интернета.
    public void showToastFromHistory() {
        viewsHistory.showToast();
    }
}

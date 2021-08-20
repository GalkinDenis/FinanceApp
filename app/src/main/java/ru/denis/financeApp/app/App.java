package ru.denis.financeApp.app;

import android.app.Application;
import ru.denis.financeApp.app.components.AppComponent;
import ru.denis.financeApp.app.components.AppFavoritesComponent;
import ru.denis.financeApp.app.components.AppSearchComponent;
import ru.denis.financeApp.app.components.DaggerAppComponent;
import ru.denis.financeApp.app.components.findComponent;
import ru.denis.financeApp.app.components.historyComponent;
import ru.denis.financeApp.app.components.showDetailsComponent;
import ru.denis.financeApp.app.components.trendingComponent;

public class App extends Application  {

    public static trendingComponent trend;
    public static AppSearchComponent search;
    public static findComponent find;
    public static AppFavoritesComponent favorites;
    public static showDetailsComponent showDetails;
    public static historyComponent history;


    @Override
    public void onCreate() {
        super.onCreate();
        AppComponent mainComponent = DaggerAppComponent.create();
        trend = mainComponent.getTrendingComponent();
        search = mainComponent.getSearchComponent();
        find = mainComponent.getFindComponent();
        favorites = mainComponent.getFavoritesComponent();
        showDetails = mainComponent.getShowDetailsComponent();
        history = mainComponent.getHistoryComponent();
    }
}


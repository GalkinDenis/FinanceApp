package ru.denis.financeApp.app.components;

import dagger.Component;

@Component
public interface AppComponent {
     trendingComponent getTrendingComponent();
     AppSearchComponent getSearchComponent();
     AppFavoritesComponent getFavoritesComponent();
     findComponent getFindComponent();
     historyComponent getHistoryComponent();
     showDetailsComponent getShowDetailsComponent();
}

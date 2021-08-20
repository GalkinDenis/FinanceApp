package ru.denis.financeApp.app.components;

import dagger.Subcomponent;
import ru.denis.financeApp.app.annotations.ActivityScope;
import ru.denis.financeApp.app.modules.searchModuleController;
import ru.denis.financeApp.app.modules.searchModuleModel;
import ru.denis.financeApp.search_fragment.SearchStock;

@ActivityScope
@Subcomponent(modules = {searchModuleController.class, searchModuleModel.class})
public interface AppSearchComponent {
    void injectSearchStocks(SearchStock view);
}


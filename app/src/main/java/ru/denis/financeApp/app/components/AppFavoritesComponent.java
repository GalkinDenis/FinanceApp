package ru.denis.financeApp.app.components;

import dagger.Subcomponent;
import ru.denis.financeApp.app.annotations.favoritesScope;
import ru.denis.financeApp.app.modules.favoritesModuleController;
import ru.denis.financeApp.app.modules.favoritesModuleModel;
import ru.denis.financeApp.favorites_fragment.Favorites;


@favoritesScope
@Subcomponent(modules = {favoritesModuleController.class, favoritesModuleModel.class})
public interface AppFavoritesComponent {
    void injectFavorites(Favorites view);
}



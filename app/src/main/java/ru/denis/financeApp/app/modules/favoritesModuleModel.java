package ru.denis.financeApp.app.modules;


import dagger.Module;
import dagger.Provides;
import ru.denis.financeApp.app.annotations.favoritesScope;
import ru.denis.financeApp.model.model;

@Module
public class favoritesModuleModel {
    @favoritesScope
    @Provides
    model provideModel() {
        return new model();
    }
}

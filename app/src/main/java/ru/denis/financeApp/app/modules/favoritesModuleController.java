package ru.denis.financeApp.app.modules;

import dagger.Module;
import dagger.Provides;
import ru.denis.financeApp.app.annotations.favoritesScope;
import ru.denis.financeApp.model.model;
import ru.denis.financeApp.presenter.controller;

@Module
public class favoritesModuleController {
    @favoritesScope
    @Provides
    controller provideController(model model) {
        return new controller(model);
    }
}

package ru.denis.financeApp.app.modules;

import dagger.Module;
import dagger.Provides;
import ru.denis.financeApp.app.annotations.ActivityScope;
import ru.denis.financeApp.model.model;
import ru.denis.financeApp.presenter.controller;

@Module
public class searchModuleController {

    @ActivityScope
    @Provides
    controller provideController(model model) {
        return new controller(model);
    }
}


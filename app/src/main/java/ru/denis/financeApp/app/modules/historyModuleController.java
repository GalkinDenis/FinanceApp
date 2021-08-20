package ru.denis.financeApp.app.modules;

import dagger.Module;
import dagger.Provides;
import ru.denis.financeApp.app.annotations.historyScope;
import ru.denis.financeApp.model.model;
import ru.denis.financeApp.presenter.controller;

@Module
public class historyModuleController {
    @historyScope
    @Provides
    controller provideController(model model) {
        return new controller(model);
    }
}

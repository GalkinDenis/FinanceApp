package ru.denis.financeApp.app.modules;

import dagger.Module;
import dagger.Provides;
import ru.denis.financeApp.app.annotations.showDetailsScope;
import ru.denis.financeApp.model.model;

@Module
public class showDetailsModuleModel {
    @showDetailsScope
    @Provides
    model provideModel() {
        return new model();
    }
}

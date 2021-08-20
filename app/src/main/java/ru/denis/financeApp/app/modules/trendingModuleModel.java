package ru.denis.financeApp.app.modules;

import dagger.Module;
import dagger.Provides;
import ru.denis.financeApp.app.annotations.trendScope;
import ru.denis.financeApp.model.model;

@Module
public class trendingModuleModel {

    @trendScope
    @Provides
    model provideModel() {
        return new model();
    }
}

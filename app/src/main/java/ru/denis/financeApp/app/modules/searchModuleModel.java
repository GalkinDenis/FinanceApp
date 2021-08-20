package ru.denis.financeApp.app.modules;

import dagger.Module;
import dagger.Provides;
import ru.denis.financeApp.app.annotations.ActivityScope;
import ru.denis.financeApp.model.model;

@Module
public class searchModuleModel {

    @ActivityScope
    @Provides
    model provideModel() {
        return new model();
    }
}

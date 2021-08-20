package ru.denis.financeApp.app.modules;

import dagger.Module;
import dagger.Provides;
import ru.denis.financeApp.app.annotations.findScope;
import ru.denis.financeApp.model.model;

@Module
public class findModuleModel {
    @findScope
    @Provides
    model provideModel() {
        return new model();
    }
}

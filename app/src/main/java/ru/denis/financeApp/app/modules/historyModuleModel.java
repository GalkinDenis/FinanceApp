package ru.denis.financeApp.app.modules;

import dagger.Module;
import dagger.Provides;
import ru.denis.financeApp.app.annotations.historyScope;
import ru.denis.financeApp.model.model;

@Module
public class historyModuleModel {
    @historyScope
    @Provides
    model provideModel() {
        return new model();
    }
}

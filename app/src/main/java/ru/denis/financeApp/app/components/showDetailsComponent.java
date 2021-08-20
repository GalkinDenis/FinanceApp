package ru.denis.financeApp.app.components;

import dagger.Subcomponent;
import ru.denis.financeApp.app.annotations.showDetailsScope;
import ru.denis.financeApp.app.modules.showDetailsModuleController;
import ru.denis.financeApp.app.modules.showDetailsModuleModel;
import ru.denis.financeApp.detail_activities.showDetailsFromCache;

@showDetailsScope
@Subcomponent(modules = {showDetailsModuleController.class, showDetailsModuleModel.class})
public interface showDetailsComponent {
    void injectShowDetailsComponent(showDetailsFromCache view);

}

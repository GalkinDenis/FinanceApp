package ru.denis.financeApp.app.components;

import dagger.Subcomponent;

import ru.denis.financeApp.app.annotations.findScope;
import ru.denis.financeApp.app.modules.findModuleController;
import ru.denis.financeApp.app.modules.findModuleModel;
import ru.denis.financeApp.detail_activities.FindStock;

@findScope
@Subcomponent(modules = {findModuleController.class, findModuleModel.class})
public interface findComponent {
    void injectFindStocks(FindStock view);
}

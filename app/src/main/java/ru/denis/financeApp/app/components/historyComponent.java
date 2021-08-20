package ru.denis.financeApp.app.components;

import dagger.Subcomponent;
import ru.denis.financeApp.app.annotations.historyScope;
import ru.denis.financeApp.app.modules.historyModuleController;
import ru.denis.financeApp.app.modules.historyModuleModel;
import ru.denis.financeApp.history_activity.historyList;

@historyScope
@Subcomponent(modules = {historyModuleController.class, historyModuleModel.class})
public interface historyComponent {
    void injectHistory(historyList view);
}

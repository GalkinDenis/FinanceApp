package ru.denis.financeApp.app.components;

import dagger.Subcomponent;
import ru.denis.financeApp.app.annotations.trendScope;
import ru.denis.financeApp.app.modules.trendingModuleController;
import ru.denis.financeApp.app.modules.trendingModuleModel;
import ru.denis.financeApp.trend_fragment.TrendingListFragment;

@trendScope
@Subcomponent(modules = {trendingModuleController.class, trendingModuleModel.class})
public interface trendingComponent {
    void injectTrendingList(TrendingListFragment view);
}

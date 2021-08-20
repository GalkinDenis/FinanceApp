package ru.denis.financeApp.detail_activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import javax.inject.Inject;
import ru.denis.financeApp.R;
import ru.denis.financeApp.app.App;
import ru.denis.financeApp.interfaces.interfaceViews;
import ru.denis.financeApp.model.tickerPlusLabel;
import ru.denis.financeApp.presenter.controller;

//Активити найденной акции.
public class FindStock extends Activity implements interfaceViews {

    @Inject controller controller;

    ImageView labelOfTicker;
    TextView showTicker;
    TextView showIndex;
    TextView showPrice;
    TextView showDivident;
    TextView showChanging;
    ImageView upOrDown;
    Button addToFavorites;
    Button showHistory;
    Animation animAlpha;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_stock_activity);

        //Создание связки объектов - "контроллер(модель)", посредствам компонентов.
        App.find.injectFindStocks(this);

        //Передача ссылки на View контроллеру.
        controller.attachView(this);

        //Объект анимации нажатия кнопок.
        animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);

        //Обращение к котроллеру, за олучением строки из намерения(TrendingLisFragment.kt/SearchStock.kt),
        //содержащую имя тикера.
        controller.loadST(
                getIntent().getStringExtra("enter").toUpperCase(),
                0,
                2
        );
    }

    //Заполнения списка вручную даными из намерения.
    @Override
    public void loadStartList(String ticker, String[] str, int count) {
        double converter = 0;
        labelOfTicker = findViewById(R.id.imageTesla);
        //Вставить заглушку в виде стандартной картинки, если подходящая не найдена.
        if (tickerPlusLabel.stockStack.get(ticker) != null) {
            labelOfTicker.setImageResource(tickerPlusLabel.stockStack.get(ticker));
        } else {
            labelOfTicker.setImageResource(R.drawable.clear);
        }
        showTicker = findViewById(R.id.ticker);
        showTicker.setText(ticker);
        showPrice = findViewById(R.id.showPrice);
        showPrice.setText("$ " + str[1]);
        showDivident = findViewById(R.id.showDivident);
        if (str[3] != null) {
            showDivident.setText(str[3] + "%");
        } else {
            showDivident.setText("-");
        }
        showIndex = findViewById(R.id.indexFind);
        showIndex.setText(str[2]);
        showChanging = findViewById(R.id.showChange);
        showChanging.setText("(" + str[0]  + "%)");
        //Вывод зеленой\красной стрелки, в зависимости от знака перед значением.
        upOrDown = findViewById(R.id.upDown);
        if(str[0]  != null) {
            converter = Double.parseDouble(str[0] );
        }
        if (converter > 0) {
            upOrDown.setScaleY(1);
            upOrDown.setScaleX(1);
            upOrDown.setImageResource(R.drawable.green_arrow);
        } else {
            upOrDown.setScaleY(1);
            upOrDown.setScaleX(1);
            upOrDown.setImageResource(R.drawable.red_arrow);
        }
        //Кнопка добавления в избранное.
        addToFavorites = findViewById(R.id.getSearch);
        addToFavorites.setText(R.string.addToFavorites);
        addToFavorites.setTag(ticker);
        //Выовод исторических данных.
        showHistory = findViewById(R.id.showHistory);
        showHistory.setText(R.string.showHistory);
        showHistory.setTag(ticker);
    }

    //Обработчик нажатий на кнопку "Show history".
    public void onClickShowHistory(View v) {
        v.startAnimation(animAlpha);
        controller.controllerClickShowDetails(getIntent()
                .getStringExtra("enter").toUpperCase(), v, 4);
    }

    //Метод, вызываемый контроллером(controller.java), для вызова активити
    //с исторической сводкой.
    @Override
    public void showHistory(String v) {
        Intent intentFindStock = new Intent("historyList");
        intentFindStock.putExtra("getHistory", v);
        startActivity(intentFindStock);
    }

    //Обработчик нажатий на кнопку "Go favorites".
    public void onClickAddOrRemoveFromFavorites(View v){
        v.startAnimation(animAlpha);
        controller.controllerClickAddToFavorites((String) v.getTag());
    }
}
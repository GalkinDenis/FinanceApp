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
import ru.denis.financeApp.interfaces.interfaceShowDetails;
import ru.denis.financeApp.model.tickerPlusLabel;
import ru.denis.financeApp.presenter.controller;

//Активити индивидуальной детализации акции из избранного.
public class showDetailsFromCache extends Activity implements interfaceShowDetails {

    @Inject controller controller;

    TextView ticker;
    Intent getIntent;
    TextView dividend;
    TextView changing;
    TextView changingIndex;
    ImageView upOrDown;
    TextView priceText;
    String getExtraPrice;
    String getExtraTicker;
    String getExtraChange;
    String getExtraChangeIndex;
    String getExtraDividend;
    ImageView labelOfTicker;
    Animation animAlpha;
    Button showHistory;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_details_activity);

        //Создание связки объектов - "контроллер(модель)", посредствам компонентов.
        App.showDetails.injectShowDetailsComponent(this);

        //Передача ссылки на View контроллеру.
        controller.attachView(this);

        //Объект анимации нажатия кнопок.
        animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);

        //Получение параметров из намерения.
        getIntent = getIntent();

        //Обращение к контроллеру за данными по акции.
        controller.loadDetailsFromCache(getIntent);
    }

    @Override
    public void DetailsFromCache(Intent intent) {
        //Получение данных из намерения, которые содержат имя тикера, цену, изменение за день,
        //дивиденды в год из Favorites.kt.
        getExtraTicker = intent.getStringExtra("ticker");
        getExtraPrice = intent.getStringExtra("price");
        getExtraChange = intent.getStringExtra("change");
        getExtraChangeIndex = intent.getStringExtra("changeIndex");
        getExtraDividend = intent.getStringExtra("divident");
        labelOfTicker = findViewById(R.id.imageTesla);
        //Вставить заглушку в виде стандартной картинки, если подходящая не найдена.
        if (tickerPlusLabel.stockStack.get(getExtraTicker) != null) {
            labelOfTicker.setImageResource(tickerPlusLabel.stockStack.get(getExtraTicker));
        } else {
            labelOfTicker.setImageResource(R.drawable.clear);
        }
        ticker = findViewById(R.id.ticker);
        ticker.setText(getExtraTicker);
        priceText = findViewById(R.id.showPrice);
        priceText.setText("$ " + String.valueOf(getExtraPrice));
        dividend = findViewById(R.id.showDivident);
        dividend.setText(getExtraDividend + "%");
        changingIndex = findViewById(R.id.showIndexFind);
        changingIndex.setText(getExtraChangeIndex);
        changing = findViewById(R.id.showChange);
        changing.setText("(" + String.valueOf(getExtraChange) + "%)");
        //Вывод зеленой\красной стрелки, в зависимости от знака(-\+) перед параметром.
        upOrDown = findViewById(R.id.upDown);
        double converter = Double.parseDouble(String.valueOf(getExtraChange));
        if (converter > 0) {
            upOrDown.setScaleY(1);
            upOrDown.setScaleX(1);
            upOrDown.setImageResource(R.drawable.green_arrow);
        } else {
            upOrDown.setScaleY(1);
            upOrDown.setScaleX(1);
            upOrDown.setImageResource(R.drawable.red_arrow);
        }
        //Выовод исторических данных за год.
        showHistory = findViewById(R.id.getSearch);
        showHistory.setText(R.string.showHistory);
        showHistory.setTag(getExtraTicker);
    }

    //Обработчик нажатий на кнопку "Show history".
    public void onClickShowHistory(View v) {
        v.startAnimation(animAlpha);
        controller.controllerClickShowDetails((String) v.getTag(), v, 3);
    }

    //Метод, вызываемый контроллером(controller.java), для запуска активити historyList.java.
    @Override
    public void showHistory(String ticker) {
        Intent intentFindStock = new Intent("historyList");
        intentFindStock.putExtra("getHistory", ticker);
        startActivity(intentFindStock);
    }
}

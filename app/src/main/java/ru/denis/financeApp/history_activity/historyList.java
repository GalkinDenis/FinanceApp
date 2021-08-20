package ru.denis.financeApp.history_activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import javax.inject.Inject;
import ru.denis.financeApp.R;
import ru.denis.financeApp.app.App;
import ru.denis.financeApp.interfaces.interfaceHistory;
import ru.denis.financeApp.presenter.controller;

public class historyList extends Activity implements interfaceHistory {

    @Inject controller controller;

    View item;
    LinearLayout linLayout;
    LayoutInflater ltInflater;
    TextView symbolPrice;
    TextView showSymbolPrice;
    TextView datePrice;
    TextView showDatePrice;
    TextView highPrice;
    TextView showHighPrice;
    TextView lowPrice;
    TextView showLowPrice;
    TextView closePrice;
    TextView showClosePrice;

    private ProgressBar progressBar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_list);

        //Установка индикатора загрузки.
        progressBar = findViewById(R.id.progress_Bar);

        //Создание связки объектов - "контроллер(модель)", посредствам компонентов.
        App.history.injectHistory(this);

        //Передача ссылки на View контроллеру.
        controller.attachView(this);

        //Обращение к котроллеру, за олучением строки из намерения(FindStock.kt/showDetailsFromCache.java),
        //содержащую имя тикера.
        controller.loadST(getIntent()
                .getStringExtra("getHistory").toUpperCase(), 0, 5);
    }

    //Метод, вызываемый контроллером(controller.java), для загрузки исторической сводки.
    @Override
    public void loadHistory(String[][] str2) {
        //Скрытие индикатора загрузки.
        progressBar.setVisibility(View.GONE);
        //Заполнения списка вручную даными из намерения.
        for (String[] strings : str2) {
            linLayout = findViewById(R.id.linLayout);
            ltInflater = getLayoutInflater();
            item = ltInflater.inflate(R.layout.history_item, linLayout, false);

            symbolPrice = item.findViewById(R.id.symbolPrice);
            showSymbolPrice = item.findViewById(R.id.showSymbolPrice);
            showSymbolPrice.setText(strings[0]);

            datePrice = item.findViewById(R.id.datePrice);
            showDatePrice = item.findViewById(R.id.showDatePrice);
            showDatePrice.setText(strings[1]);

            highPrice = item.findViewById(R.id.highPrice);
            showHighPrice = item.findViewById(R.id.showHighPrice);
            showHighPrice.setText(strings[2]);

            lowPrice = item.findViewById(R.id.lowPrice);
            showLowPrice = item.findViewById(R.id.showLowPrice);
            showLowPrice.setText(strings[3]);

            closePrice = item.findViewById(R.id.changing);
            showClosePrice = item.findViewById(R.id.showClosePrice);
            showClosePrice.setText(strings[4]);

            //Добавление элемента в список.
            linLayout.addView(item);
        }
    }
}

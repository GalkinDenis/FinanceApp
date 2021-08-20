package ru.denis.financeApp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import ru.denis.financeApp.model.tickerPlusLabel;

public class startActivity extends Activity {
    TextView[] tv;
    int[] rId;
    int[] resRu;
    int[] resEng;

    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;
    TextView textView8;
    TextView textView9;
    TextView textView10;
    TextView textView11;
    TextView textView12;
    TextView textView13;
    TextView textView14;
    TextView textView15;
    TextView textView16;
    TextView textView17;
    TextView textView18;
    TextView textView19;

    TextView web;
    Button setEng;
    Button setRus;
    Button startApp;
    Animation animAlpha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        //Инициализация массивов с лейблами компаний и их именами\тикерами.
        tickerPlusLabel.initializationOfArrays();

        //Определение элементов макета.
        tv = new TextView[] {
                textView1, textView2, textView3, textView4, textView5, textView6,
                textView7, textView8, textView9, textView10, textView11, textView12,
                textView13, textView14, textView15, textView16, textView17, textView18, textView19
        };

        //Оперделение id элементов макета.
        rId = new int[] {
                R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4,
                R.id.textView5, R.id.textView6, R.id.textView7, R.id.textView8,
                R.id.textView9, R.id.textView10, R.id.textView11, R.id.textView12,
                R.id.textView13, R.id.textView14, R.id.textView15, R.id.textView16,
                R.id.textView17, R.id.textView18, R.id.textView19
        };

        //Оперделение  строковых ресурсов на Русском языке.
        resRu = new int[] {
                R.string.R_string1, R.string.R_string2, R.string.R_string3, R.string.R_string4, R.string.R_string5,
                R.string.R_string6, R.string.R_string7, R.string.R_string8, R.string.R_string9, R.string.R_string10,
                R.string.R_string11, R.string.R_string12, R.string.R_string13, R.string.R_string14, R.string.R_string15,
                R.string.R_string16, R.string.R_string17, R.string.R_string18, R.string.R_string19
        };

        //Оперделение  строковых ресурсов на Английском языке.
        resEng = new int[] {
                R.string.E_string1, R.string.E_string2, R.string.E_string3,
                R.string.E_string4, R.string.E_string5, R.string.E_string6, R.string.E_string7,
                R.string.E_string8, R.string.E_string9, R.string.E_string10, R.string.E_string11,
                R.string.E_string12, R.string.E_string13, R.string.E_string14, R.string.E_string15,
                R.string.E_string16, R.string.E_string17, R.string.E_string18, R.string.E_string19
        };

        //Инициализация элементов макета.
        for(int i = 0; i < 19; i++) {
            tv[i] = findViewById(rId[i]);
        }
        web = findViewById(R.id.web);
        setEng = findViewById(R.id.setEng);
        setRus = findViewById(R.id.setRus);
        startApp = findViewById(R.id.startApp);
        animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);

        //Изъятие параметров элементов макета из сохраненных в(Bundle outState)
        //в момент смены конфигурации экрана.
        if(savedInstanceState != null) {
            for(int i = 0; i < 19; i++) {
                tv[i].setText(savedInstanceState.getString(String.valueOf(i)));
            }
        }
    }

    //Смена текста на Русский язык.
    public void onClickSetRus(View v){
        v.startAnimation(animAlpha);
        for(int i = 0; i < 19; i++) {
            tv[i].setText(resRu[i]);
        }
    }

    //Смена текста на Английский язык.
    public void onClickSetEng(View v){
        v.startAnimation(animAlpha);
        for(int i = 0; i < 19; i++) {
            tv[i].setText(resEng[i]);
        }
    }

    //Старт приложения.
    public void onClickStartApp(View v){
        v.startAnimation(animAlpha);
        Intent startApp = new Intent("M");
        startActivity(startApp);
    }

    //Переход к учетной записи на GitHub с исходным кодом приложения.
    public void onClickGoGit(View v){
        Intent browserIntent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/GalkinDenis/FinanceApp"));
        startActivity(browserIntent);
    }

    //Переход на financequotes-api.com, где размещено используемое в приложении API.
    public void onClickGoWeb(View v){
        Intent browserIntent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://financequotes-api.com"));
        startActivity(browserIntent);
    }

    //Сохранение параметров элементов макета в момент смены конфигурации экрана.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        for(int i = 0; i < 19; i++) {
            outState.putString(String.valueOf(i), (String) tv[i].getText());
        }
    }
}

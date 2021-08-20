package ru.denis.financeApp.search_fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.denis.financeApp.R
import ru.denis.financeApp.app.App
import ru.denis.financeApp.databinding.GetStockBinding
import ru.denis.financeApp.interfaces.interfaceSearch
import ru.denis.financeApp.presenter.controller
import javax.inject.Inject

class SearchStock : Fragment(), interfaceSearch {

    @Inject lateinit var controller: controller
    private var _binding: GetStockBinding? = null
    private val binding get() = _binding!!
    private lateinit var enterTicker: EditText

    //Сохранение введенного текста в поле ввода для поиска акции по тикеру
    //в момент смены конфигурации экрана.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("editText", enterTicker.text.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)

        //Создание связки объектов - "контроллер(модель)", посредствам компонентов.
        App.search.injectSearchStocks(this)

        //Передача ссылки на View контроллеру.
        controller.attachView(this)
    }

    //Раздувание макета.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = GetStockBinding.inflate(layoutInflater)
        return _binding?.root
    }

    //Инициализация элементов.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        enterTicker = view.findViewById(R.id.enterStock)

        //Проверка на содержание текста в поле ввода перед сменой конфигурации экрана.
        if (savedInstanceState != null) {
            binding.enterStock.setText(savedInstanceState.getString("editText", "-"))
        }

        //Обработчик нажатий на кнопку - "Search".
        binding.getSearch.setOnClickListener{
            controller.controllerClickShowDetails(enterTicker.text.toString(), it, 2);
        }
    }

    //Метод, вызываемый контроллером(controller.java), для проверки существования запрашиваемого тикера
    // и его последующего вывода на экран или вывода соответсвущего сообщения в случаем ошибки.
    override fun findStock(ticker: String, i: Int) {
        //Проверка на пустую строку
        if (ticker.isNotEmpty()) {
            //Проверка запроса на null.
            if (i == 0) {
                Toast.makeText(context, "Wrong request", Toast.LENGTH_LONG).show()
                return
            }
        } else {
            Toast.makeText(context, "Field is empty", Toast.LENGTH_LONG).show()
            return
        }
        //Вывод активити найденной акции.
        val intentFindStock = Intent("intentFindStock")
        intentFindStock.putExtra("enter", ticker)
        startActivity(intentFindStock)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
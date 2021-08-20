package ru.denis.financeApp.trend_fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.denis.financeApp.app.App
import ru.denis.financeApp.databinding.FragmentTrendListBinding
import ru.denis.financeApp.detail_activities.FindStock
import ru.denis.financeApp.interfaces.TrendInterface
import ru.denis.financeApp.presenter.controller
import javax.inject.Inject

//Фрагмент популярных акций.
class TrendingListFragment : Fragment(), TrendInterface {
    @Inject lateinit var controller: controller
    private var _binding: FragmentTrendListBinding? = null
    private val binding get() = _binding!!

    //Функция, вызываемая контроллером, для демонстрации прогресса загрузки.
    override fun setProgressBar(i: Int){
            when (i) {
                in 0..99 -> binding.progressBar.progress = i
                100 -> {
                    binding.progressBar.progress = i
                }
            }
    }

    //Функция, вызываемая контроллером, для заполнения списка.
    override fun setAdapter(data: Array<Array<String>>) {
        when {
            data.isNotEmpty() -> {
                 binding.progressBar.visibility = View.GONE
             }
        }
        binding.recyclerView.adapter = TrendAdapter(controller, data)
    }

    //Метод, вызываемый контроллером(controller.java), для вызова активити
    //с детализацией.
    override fun find(v: String?) {
        val context = view?.context
        val intent  = Intent(context, FindStock::class.java)
        intent.putExtra("enter", v)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    //Раздувание макета.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrendListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        //Создание связки объектов - "контроллер(модель)", посредствам компонентов.
        App.trend.injectTrendingList(this)

        //Передача ссылки на View контроллеру.
        controller.attachView(this)

        //Установка списка популярных акций.
        controller.setList("Trends")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

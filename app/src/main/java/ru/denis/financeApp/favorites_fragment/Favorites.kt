package ru.denis.financeApp.favorites_fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.denis.financeApp.app.App
import ru.denis.financeApp.databinding.FavsRecyclerViewBinding
import ru.denis.financeApp.interfaces.interfaceFavorites
import ru.denis.financeApp.model.tickerPlusLabel
import ru.denis.financeApp.presenter.controller
import ru.denis.financeApp.detail_activities.showDetailsFromCache
import javax.inject.Inject

//Фрагмент списка фаворитов.
class Favorites : Fragment(), interfaceFavorites {
    @Inject lateinit var controller: controller
    private var _binding: FavsRecyclerViewBinding? = null
    private val binding get() = _binding!!

    //Функция, вызываемая контроллером, для бновление списка.
    override fun refresh() {
        binding.favoritesRecyclerView.adapter?.notifyDataSetChanged()
    }

    //Функция, вызываемая контроллером, для заполнения списка.
    override fun setAdapter() {
        binding.favoritesRecyclerView.adapter = FavoritesAdapter(controller)
    }

    //Функция, вызываемая контроллером, для запуска активити(showDetailsFromCache.java),
    //с деталями по акции.
    override fun showDetailsFromCache(ticker: String?) {
        val index = tickerPlusLabel.tickerList.indexOf(ticker)
        //Вывод активити с деталями найденной акции.
        val context = view?.context
        val intentFindStock  = Intent(context, showDetailsFromCache::class.java)
        intentFindStock.putExtra("ticker", tickerPlusLabel.tickerList[index])
        intentFindStock.putExtra("price", tickerPlusLabel.priceList[index])
        intentFindStock.putExtra("change", tickerPlusLabel.changeList[index])
        intentFindStock.putExtra("changeIndex", tickerPlusLabel.changeIndexList[index])
        intentFindStock.putExtra("divident", tickerPlusLabel.dividentList[index])
        startActivity(intentFindStock);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)

        //Создание связки объектов - "контроллер(модель)", посредствам компонентов.
        App.favorites.injectFavorites(this)

        //Передача ссылки на View контроллеру.
        controller.attachView(this)
    }

    //Раздувание макета.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FavsRecyclerViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.favoritesRecyclerView.layoutManager = LinearLayoutManager(context)

        //Установка списка фаворитов.
        controller.setList("Favorites")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

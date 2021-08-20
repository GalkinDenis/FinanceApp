package ru.denis.financeApp;

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.denis.financeApp.databinding.MainActivityBinding
import ru.denis.financeApp.model.tickerPlusLabel

class MainActivity : AppCompatActivity() {

    private var savingFavorites: SharedPreferences? = null

    private lateinit var binding: MainActivityBinding

    //Функция загрузки кеша после старта приложения.
    private fun loadCache(){
        var i = 0
        while (true) {
            savingFavorites = getPreferences(MODE_PRIVATE)
            val loadKey: String? = savingFavorites?.getString(i.toString() + "a", "")
            val loadKey2: String? = savingFavorites?.getString(i.toString() + "b", "")
            val loadKey3: String? = savingFavorites?.getString(i.toString() + "c", "")
            val loadKey4: String? = savingFavorites?.getString(i.toString() + "d", "")
            val loadKey5: String? = savingFavorites?.getString(i.toString() + "e", "")
            if (loadKey != "") {
                tickerPlusLabel.tickerList.add(loadKey)
                tickerPlusLabel.priceList.add(loadKey2)
                tickerPlusLabel.changeList.add(loadKey3)
                tickerPlusLabel.changeIndexList.add(loadKey4)
                tickerPlusLabel.dividentList.add(loadKey5)
                i++
            } else {
                break
            }
        }
    }

    //Функция сохранения кеша в момент закрытия приложения.
    private fun saveCache() {
        for (i in tickerPlusLabel.tickerList.indices) {
            val ed = savingFavorites?.edit()
            ed?.putString(i.toString() + "a", tickerPlusLabel.tickerList[i].toString())
            ed?.putString(i.toString() + "b", tickerPlusLabel.priceList[i].toString())
            ed?.putString(i.toString() + "c", tickerPlusLabel.changeList[i].toString())
            ed?.putString(i.toString() + "d", tickerPlusLabel.changeIndexList[i].toString())
            ed?.putString(i.toString() + "e", tickerPlusLabel.dividentList[i].toString())
            ed?.apply()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        tickerPlusLabel.initializationOfArrays()
        loadCache()

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment)

        //Инициализация панели нижней навигации.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.trend_list, R.id.search_page, R.id.favorites_list
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    //Промежуточное сохранение кеша в момент работы приложения.
    override fun onPause() {
        super.onPause()
        savingFavorites?.edit()?.clear()?.apply()
        saveCache()
    }
}


package ru.denis.financeApp.favorites_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.denis.financeApp.R
import ru.denis.financeApp.databinding.ItemBinding
import ru.denis.financeApp.model.tickerPlusLabel
import ru.denis.financeApp.presenter.controller

//Адаптер фаворитов.
class FavoritesAdapter(private val controller: controller) :
    RecyclerView.Adapter<FavoritesAdapter.FavoritesAdapterViewHolder>() {

    //Определение элементов списка.
    class FavoritesAdapterViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)

    //Определение длины списка.
    override fun getItemCount(): Int {
        return tickerPlusLabel.tickerList.size
    }

    //Раздувание макета.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesAdapterViewHolder {
        return FavoritesAdapterViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    //Инициализация элементов списка.
    override fun onBindViewHolder(holder: FavoritesAdapterViewHolder, position: Int) {
        //Обращение к котроллеру за данными из кеша.
        //holder.binding.imageTesla.setImageResource(controller.setImageForFav(position))
        holder.binding.imageTesla.setImageResource(controller.setImageForFav(position))
        holder.binding.ticker.text = controller.setListForFav(position, "ticker")
        holder.binding.name.text = controller.setListForFav(position, "name")
        holder.binding.price.text = controller.setListForFav(position, "price")
        holder.binding.index.text = controller.setListForFav(position, "index")
        holder.binding.changing.text = "(" + controller.setListForFav(position, "change") + "%)"
        when {
            controller.setListForFav(position, "change").toDouble() > 0.0 -> holder.binding.upOrDown.setImageResource(R.drawable.green_arrow)
            controller.setListForFav(position, "change").toDouble() < 0.0 -> holder.binding.upOrDown.setImageResource(R.drawable.red_arrow)
        }
        holder.binding.details.tag = controller.setListForFav(position, "ticker")
        holder.binding.favorits.tag = controller.setListForFav(position, "ticker")
        //

        holder.binding.favorits.text = "Remove"

        //Обработчик нажатия на копку - "Show details".
        holder.binding.details.setOnClickListener {
            controller.controllerShowDetailsFromCache(holder.binding.details.tag as String?)
        }

        //Обработчик нажатия на копку - "Remove".
        holder.binding.favorits.setOnClickListener {
            controller.controllerRemoveFromCache(holder.binding.favorits.tag as String?)
        }
    }
}
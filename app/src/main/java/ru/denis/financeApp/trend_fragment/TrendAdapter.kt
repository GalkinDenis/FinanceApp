package ru.denis.financeApp.trend_fragment;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.denis.financeApp.R
import ru.denis.financeApp.databinding.ItemBinding
import ru.denis.financeApp.model.tickerPlusLabel
import ru.denis.financeApp.presenter.controller

//Адаптер популярных акций.
class TrendAdapter(private val controller: controller, private var dataSet: Array<Array<String>>) :
    RecyclerView.Adapter<TrendAdapter.TrendViewHolder>() {

    //Определение элементов списка.
    class TrendViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)

    //Определение длины списка.
    override fun getItemCount(): Int {
        return dataSet.size
    }

    //Раздувание макета.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendViewHolder {
        return TrendViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    //Инициализация элементов списка.
    override fun onBindViewHolder(holder: TrendViewHolder, position: Int) {
        holder.binding.imageTesla.setImageResource(tickerPlusLabel.drawable[position])
        holder.binding.ticker.text = dataSet[position][3]
        holder.binding.name.text = dataSet[position][4]
        holder.binding.price.text = dataSet[position][1]
        holder.binding.index.text = dataSet[position][2]
        holder.binding.changing.text = dataSet[position][0]

        when {
            dataSet[position][2].toDouble() > 0.0 -> holder.binding.upOrDown.setImageResource(R.drawable.green_arrow)
            dataSet[position][2].toDouble() < 0.0 -> holder.binding.upOrDown.setImageResource(R.drawable.red_arrow)
        }

        holder.binding.details.tag = dataSet[position][3]
        holder.binding.favorits.tag = dataSet[position][3]

        //Обработчик нажатия на копку - "Show details".
        holder.binding.details.setOnClickListener {
            controller.controllerClickShowDetails(
                holder.binding.details.tag as String?,
                holder.binding.details.rootView,
                1
            )
        }

        //Обработчик нажатия на копку - "Go favorites".
        holder.binding.favorits.setOnClickListener {
            controller.controllerClickAddToFavorites(holder.binding.favorits.tag as String?)
        }
    }
    }

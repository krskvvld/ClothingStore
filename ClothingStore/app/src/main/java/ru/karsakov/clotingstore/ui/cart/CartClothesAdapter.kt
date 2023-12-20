package ru.rakhimova.gamestore.ui.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.rakhimova.gamestore.R
import ru.rakhimova.gamestore.databinding.CartListItemBinding
import ru.rakhimova.gamestore.domain.models.GameModel

@SuppressLint("NotifyDataSetChanged")
class CartClothesAdapter(
    private val listener: Listener,
) : RecyclerView.Adapter<CartGamesAdapter.ViewHolder>() {

    private var games: List<GameModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CartListItemBinding.inflate(inflater, parent, false)
        val viewHolder = ViewHolder(binding)

        with(binding) {
            root.setOnClickListener {
                listener.onItemClick(games[viewHolder.bindingAdapterPosition])
            }
            cartListItemDeleteIcon.setOnClickListener {
                listener.onDeleteClick(games[viewHolder.bindingAdapterPosition])
            }
        }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(games[position])
    }

    fun setGames(value: List<GameModel>) {
        games = value
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: CartListItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(game: GameModel) = with(binding) {
            Glide.with(cartListItemLogoImage)
                .load(game.logoUrl)
                .into(cartListItemLogoImage)
            cartListItemNameText.text = game.name
            cartListItemPriceText.text = root.context.getString(R.string.price, game.price)
        }
    }

    interface Listener {
        fun onItemClick(item: GameModel)
        fun onDeleteClick(item: GameModel)
    }
}
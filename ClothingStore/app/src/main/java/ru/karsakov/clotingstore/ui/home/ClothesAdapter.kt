package ru.karsakov.clothingstore.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.karsakov.clothingstore.R
import ru.karsakov.clothingstore.databinding.ClothGridItemBinding
import ru.karsakov.clothingstore.domain.models.ClothModel

@SuppressLint("NotifyDataSetChanged")
class ClothesAdapter(
    private val listener: Listener,
) : RecyclerView.Adapter<ClothesAdapter.ViewHolder>() {

    private var clothes: List<ClotheModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ClothGridItemBinding.inflate(inflater, parent, false)
        val viewHolder = ViewHolder(binding)

        with(binding) {
            root.setOnClickListener {
                listener.onItemClick(clothes[viewHolder.bindingAdapterPosition])
            }
        }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return clothes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clothes[position])
    }

    fun setClothes(value: List<ClotheModel>) {
        clothes = value
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ClothGridItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cloth: ClothModel) = with(binding) {
            Glide.with(clothGripItemImage)
                .load(cloth.logoUrl)
                .into(cloth)
            clothGripItemNameText.text = cloth.name
            clothGripItemTagsText.text = cloth.tags.joinToString(", ")
            clothGripItemPriceText.text = root.context.getString(R.string.price, cloth.price)
        }
    }

    interface Listener {
        fun onItemClick(item: ClothModel)
    }
}
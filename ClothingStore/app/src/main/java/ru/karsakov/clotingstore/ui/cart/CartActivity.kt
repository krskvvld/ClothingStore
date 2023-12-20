package ru.karsakov.clothingstore.ui.cart

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.karsakov.clothingstore.R
import ru.karsakov.clothingstore.core.navigateUp
import ru.karsakov.clothingstore.core.navigateWithArgs
import ru.karsakov.clothingstore.databinding.CartActivityBinding
import ru.karsakov.clothingstore.domain.models.ClothModel
import ru.karsakov.clothingstore.ui.cloth.ClothActivity
import ru.karsakov.clothingstore.ui.cloth.ClothActivityArguments

@AndroidEntryPoint
class CartActivity : AppCompatActivity(), CartClothesAdapter.Listener {

    private val viewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = CartActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cartBackIcon.setOnClickListener {
            navigateUp()
        }

        binding.cartBuyButton.setOnClickListener {
            viewModel.buyClothes()
        }

        val clothesAdapter = CartClothesAdapter(this)
        with(binding.cartClothRecycler) {
            layoutManager = GridLayoutManager(
                context, RecyclerView.VERTICAL,
            )
            adapter = clothesAdapter
            setHasFixedSize(true)
        }
        viewModel.clothes.observe(this) { clothes ->
            clothesAdapter.setClothes(clothes)

            if (clothes.isEmpty()) {
                binding.cartBuyButton.visibility = View.GONE
            } else {
                binding.cartBuyButton.visibility = View.VISIBLE
            }
        }

        viewModel.buySuccessEffect.observe(this) { sideEffect ->
            sideEffect.handle {
                Toast.makeText(this, R.string.cart_buy_success, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadClothes()
    }

    override fun onItemClick(item: ClothModel) {
        navigateWithArgs<ClothActivity, ClothActivityArguments>(
            args = ClothActivityArguments(
                clothId = item.id
            )
        )
    }

    override fun onDeleteClick(item: ClothModel) {
        viewModel.removeFromCart(item)
    }
}
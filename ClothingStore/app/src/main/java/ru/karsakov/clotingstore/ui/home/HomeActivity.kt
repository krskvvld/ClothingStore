package ru.karsakov.clothingstore.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import ru.karsakov.clothingstore.core.navigate
import ru.karsakov.clothingstore.core.navigateWithArgs
import ru.karsakov.clothingstore.databinding.HomeActivityBinding
import ru.karsakov.clothingstore.domain.models.ClothModel
import ru.karsakov.clothingstore.ui.cart.CartActivity
import ru.karsakov.clothingstore.ui.cloth.ClothActivity
import ru.karsakov.clothingstore.ui.cloth.ClothActivityArguments
import ru.karsakov.clothingstore.ui.profile.ProfileActivity

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), ClothesAdapter.Listener {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.homeProfileLayout.setOnClickListener {
            navigate<ProfileActivity>()
        }

        binding.homeCartIcon.setOnClickListener {
            navigate<CartActivity>()
        }

        val clothesAdapter = ClothesAdapter(this)
        with(binding.homeClothRecycler) {
            layoutManager = GridLayoutManager(
                this@HomeActivity, 1,
            )
            adapter = clothesAdapter
            setHasFixedSize(true)
        }
        viewModel.clothes.observe(this, clothesAdapter::setClothes)

        viewModel.user.observe(this) { user ->
            binding.homeTitleText.text = user.firstName

            if (user.photoUrl != null) {
                Glide.with(binding.homeProfileIcon)
                    .load(user.photoUrl)

                    .into(binding.homeProfileIcon)
            }
        }
    }

    override fun onItemClick(item: ClothModel) {
        navigateWithArgs<ClothActivity, ClothActivityArguments>(
            args = ClothActivityArguments(
                clotheId = item.id
            )
        )
    }
}
package ru.karsakov.clothingstore.ui.cloth

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import ru.karsakov.clothingstore.R
import ru.karsakov.clothingstore.core.getArgs
import ru.karsakov.clothingstore.core.navigateUp
import ru.karsakov.clothingstore.databinding.ClothActivityBinding
import java.time.format.DateTimeFormatter

@Parcelize
class ClothActivityArguments(
    val clothId: Long = -1L,
) : Parcelable

@AndroidEntryPoint
class ClothActivity : AppCompatActivity() {

    private val viewModel: ClothViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ClothActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val args: ClothActivityArguments? = getArgs()
        if (args != null) {
            viewModel.initWithArgs(args)
        }

        binding.clothBackIcon.setOnClickListener {
            navigateUp()
        }

        binding.clothCartFab.setOnClickListener {
            viewModel.onCartClick()
        }

        viewModel.cloth.observe(this) { cloth ->
            with(binding) {
                Glide.with(clothImage)
                    .load(cloth.imageUrl)
                    .into(clothImage)

                clothTitleText.text = cloth.name
                clothTagsText.text = cloth.tags.joinToString(", ")
                clothPriceText.text = getString(R.string.price, cloth.price)
                clothDateText.text = cloth.releaseDate.format(
                    DateTimeFormatter.ISO_LOCAL_DATE,
                )
                clothDeveloperText.text = cloth.developer
                clothPublisherText.text = cloth.publisher
                clothDescriptionText.text = cloth.description

                if (cloth.inCart) {
                    clothCartFab.setImageResource(R.drawable.ic_cart_remove)
                    clothCartFab.background.setTint(getColor(R.color.red))
                } else {
                    clothCartFab.setImageResource(R.drawable.ic_cart_add)
                    clothCartFab.background.setTint(getColor(R.color.secondary_background))
                }
            }
        }
    }
}
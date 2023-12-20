package ru.karsakov.clothingstore.ui.profile

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import ru.karsakov.clothingstore.R
import ru.karsakov.clothingstore.core.navigateUp
import ru.karsakov.clothingstore.databinding.ProfileActivityBinding

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ProfileActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.profileBackIcon.setOnClickListener {
            navigateUp()
        }

        viewModel.user.observe(this) {user ->
            binding.profileNameText.text = user.fullName
            binding.profileGroupText.text = getString(R.string.profile_group, user.group)

            if (user.photoUrl != null) {
                Glide.with(binding.profilePhotoImage)
                    .load(user.photoUrl)
                    .into(binding.profilePhotoImage)
            }
        }
    }
}
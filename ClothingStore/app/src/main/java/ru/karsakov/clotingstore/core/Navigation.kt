package ru.karsakov.clothingstore.core

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import ru.karsakov.clothingstore.ui.cloth.ClothActivityArguments

inline fun <reified S> Activity.navigate() {
    val intent = Intent(this, S::class.java)
    startActivity(intent)
}

inline fun <reified S, reified A : Parcelable> Activity.navigateWithArgs(args: A) {
    val intent = Intent(this, S::class.java)
    val bundle = Bundle()
    bundle.putParcelable(
        A::class.qualifiedName,
        args,
    )
    intent.putExtras(bundle)
    startActivity(intent)
}

@Suppress("DEPRECATION")
inline fun <reified A : Parcelable> Activity.getArgs(): A? {
    return intent.extras
        ?.getParcelable(A::class.qualifiedName)
}

fun Activity.navigateUp() {
    finish()
}
package com.cabify.library.utils.recyclerview

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class DefaultItemCallback<T : Any>(private val idSelector: ((T) -> Any?)? = null) : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return (idSelector?.let { return it(oldItem) == it(newItem) } ?: oldItem) == newItem
    }

    /**
     * Note that in kotlin, == checking on data classes compares all contents, but in Java,
     * typically you'll implement Object#equals, and use it to compare object contents.
     */
    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
}
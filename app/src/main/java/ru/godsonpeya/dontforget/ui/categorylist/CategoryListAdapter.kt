package ru.godsonpeya.dontforget.ui.categorylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.godsonpeya.dontforget.databinding.RecyclerviewItemBinding
import ru.godsonpeya.dontforget.entity.Category
import ru.godsonpeya.dontforget.tools.formatDate


class CategoryListAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Category, CategoryListAdapter.WordViewHolder>(CategoriesComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.from(parent)
    }

    public override fun getItem(position: Int): Category {
        return super.getItem(position)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, listener)
    }

    class WordViewHolder private constructor(private val binding: RecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category, listener: OnItemClickListener) {

            binding.time.text = formatDate(category.nextTime)
            binding.category = category
            binding.listener = listener
        }

        companion object {
            fun from(parent: ViewGroup): WordViewHolder {
                val inflater: LayoutInflater = LayoutInflater.from(parent.context)
                val view = RecyclerviewItemBinding.inflate(inflater, parent, false)
                return WordViewHolder(view)
            }
        }
    }

    class CategoriesComparator : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.name == newItem.name
        }
    }
}


class OnItemClickListener(val listener: (item: Category) -> Unit) {
    fun onClick(item: Category) = listener(item)
}
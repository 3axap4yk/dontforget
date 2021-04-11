package ru.godsonpeya.dontforget.ui.categorylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.godsonpeya.dontforget.R
import ru.godsonpeya.dontforget.WordsApplication
import ru.godsonpeya.dontforget.databinding.FragmentCategoryListBinding
import ru.godsonpeya.dontforget.entity.Category


class CategoryListFragment : Fragment() {
    private lateinit var binding: FragmentCategoryListBinding
    private lateinit var adapter: CategoryListAdapter
    private lateinit var viewModel: CategoryViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryListBinding.inflate(inflater, container, false)
        val wordApp = requireContext().applicationContext as WordsApplication

        adapter = CategoryListAdapter(OnItemClickListener {
            it.let {
                viewModel.onClickCategory(it)
            }
        })
        binding.recyclerview.adapter = adapter

        viewModel =
            ViewModelProvider(viewModelStore, CategoryViewModelFactory(wordApp.repository)).get(
                CategoryViewModel::class.java
            )
        binding.viewModel = viewModel

        viewModel.navigateToShowDetail.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController()
                    .navigate(CategoryListFragmentDirections.actionCategoryListFragmentToDetailFragment(it))
                viewModel.navigationDone()
            }
        })

        viewModel.navigateToDetail.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController()
                    .navigate(CategoryListFragmentDirections.actionCategoryListFragmentToDetailFragment(null))
                viewModel.navigationDone()
            }
        })

        viewModel.categories.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })
        inTouchMove()

        return binding.root
    }

    private fun inTouchMove() {
        val helper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(
                    viewHolder: RecyclerView.ViewHolder,
                    direction: Int
                ) {
                    val position = viewHolder.adapterPosition
                    val myCategory: Category = adapter.getItem(position)
                    Toast.makeText(
                        requireContext(), "Deleting " +
                                myCategory.name, Toast.LENGTH_LONG
                    ).show()

                    // Delete the word
                    viewModel.deleteWord(myCategory)
                }
            })

        helper.attachToRecyclerView(binding.recyclerview)
    }

}
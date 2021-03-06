package ru.godsonpeya.dontforget.ui.wordlist

import android.os.Bundle
import android.util.Log
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
import ru.godsonpeya.dontforget.databinding.FragmentWordListBinding
import ru.godsonpeya.dontforget.entity.Category


class WordListFragment : Fragment() {
    private lateinit var binding: FragmentWordListBinding
    private lateinit var adapter: CategoryListAdapter
    private lateinit var viewModel: CategoryViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWordListBinding.inflate(inflater, container, false)
        val wordApp = requireContext().applicationContext as WordsApplication

        adapter = CategoryListAdapter()
        binding.recyclerview.adapter = adapter

        viewModel = ViewModelProvider(viewModelStore, WordViewModelFactory(wordApp.repository)).get(
            CategoryViewModel::class.java
        )
        binding.viewModel = viewModel

        viewModel.navigateToDetail.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController().navigate(R.id.action_wordListFragment_to_detailFragment)
                viewModel.navigationDone()
            }
        })
        viewModel.categories.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.allCategoryWithParams.observe(viewLifecycleOwner,{
            it?.let {
                it.forEach { list ->
                    Log.d(WordListFragment::class.simpleName, "****************************")
                    Log.d(WordListFragment::class.simpleName, list.category?.name.toString())
                    list.parameters.forEach{parameter ->
                        Log.d(WordListFragment::class.simpleName, parameter.name.toString() +" : " + parameter.value.toString())
                    }
                    Log.d(WordListFragment::class.simpleName, "****************************")
                }
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
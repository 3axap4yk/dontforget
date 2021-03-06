package ru.godsonpeya.dontforget.ui.detail

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.godsonpeya.dontforget.R
import ru.godsonpeya.dontforget.WordsApplication
import ru.godsonpeya.dontforget.databinding.FragmentDetailBinding
import ru.godsonpeya.dontforget.entity.Category
import ru.godsonpeya.dontforget.ui.wordlist.CategoryViewModel
import ru.godsonpeya.dontforget.ui.wordlist.WordViewModelFactory


class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: CategoryViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        val wordApp = requireContext().applicationContext as WordsApplication
        viewModel =
            ViewModelProvider(viewModelStore, WordViewModelFactory(wordApp.repository)).get(
                CategoryViewModel::class.java
            )
        viewModel.navigateBackToList.observe(viewLifecycleOwner,{
            it?.let {
                this.findNavController().navigate(R.id.action_detailFragment_to_wordListFragment)
                viewModel.navigationDone()
            }
        })

        binding.buttonSave.setOnClickListener {
            if (TextUtils.isEmpty(binding.editWord.text)) {
                //TODO error
            } else {
                val word = binding.editWord.text.toString()
                viewModel.insert(Category(name = word))
            }
        }

        return binding.root
    }

}
package ru.godsonpeya.dontforget.ui.detail

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import ru.godsonpeya.dontforget.R
import ru.godsonpeya.dontforget.WordsApplication
import ru.godsonpeya.dontforget.databinding.FragmentDetailBinding
import ru.godsonpeya.dontforget.entity.Category
import ru.godsonpeya.dontforget.schedule.NotificationUtils
import ru.godsonpeya.dontforget.tools.formatDate
import ru.godsonpeya.dontforget.tools.toEditable
import ru.godsonpeya.dontforget.ui.categorylist.CategoryViewModel
import ru.godsonpeya.dontforget.ui.categorylist.CategoryViewModelFactory
import java.util.*


class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: CategoryViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        val wordApp = requireContext().applicationContext as WordsApplication
        viewModel =
            ViewModelProvider(viewModelStore, CategoryViewModelFactory(wordApp.repository)).get(
                CategoryViewModel::class.java
            )
        viewModel.navigateBackToList.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController()
                    .navigate(R.id.action_detailFragment_to_categoryListFragment)
                viewModel.navigationDone()
            }
        })

        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        updateTextViewDate(calendar, null)
        binding.dataPicker.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Выбирете дату")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
            datePicker.addOnPositiveButtonClickListener {
                updateTextViewDate(calendar, it)
            }
            datePicker.show(childFragmentManager, "pickedDate")
        }

        val category: Category? = DetailFragmentArgs.fromBundle(requireArguments()).category
        if (category != null) {
            binding.editCategory.text = category.name.toEditable()
            binding.dataPicker.text = formatDate(category.nextTime)
        }

        binding.buttonSave.setOnClickListener {
            if (TextUtils.isEmpty(binding.editCategory.text)) {
                //TODO error
            } else {
                val word = binding.editCategory.text.toString()
                viewModel.insert(
                    Category(
                        id = category?.id,
                        name = word,
                        nextTime = calendar.timeInMillis
                    )
                )

                NotificationUtils().setNotification(calendar.timeInMillis, word, requireActivity())
                requireActivity().hideSoftKeyboard(binding.editCategory)
            }
        }

        return binding.root
    }

    private fun updateTextViewDate(calendar: Calendar, it: Long?) {
        if (it != null) {
            calendar.time = Date(it)
        } else {
            calendar.time = Date()
        }
        binding.dataPicker.text = formatDate(calendar.timeInMillis)
    }

    fun Activity.hideSoftKeyboard(editText: EditText) {
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(editText.windowToken, 0)
        }
    }
}
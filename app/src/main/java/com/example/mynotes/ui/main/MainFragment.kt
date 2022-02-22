package com.example.mynotes.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotes.R
import com.example.mynotes.databinding.MainFragmentBinding
import com.example.mynotes.models.TaskList

class MainFragment(val clickListener: MainFragmentInteractiionListener) : Fragment(), ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListener {
    private lateinit var binding: MainFragmentBinding

    companion object {
        fun newInstance(clickListener: MainFragmentInteractiionListener) = MainFragment(clickListener)
    }

    interface MainFragmentInteractiionListener {
        fun listItemTapped(list:TaskList)
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)

        binding.listRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(),
            MainViewModelFactory(PreferenceManager.getDefaultSharedPreferences(requireActivity()))).get(MainViewModel::class.java)

        val recyclerViewAdapter = ListSelectionRecyclerViewAdapter(viewModel.lists, this)
        binding.listRecyclerview.adapter = recyclerViewAdapter
        viewModel.onListAdded = {
            recyclerViewAdapter.listUpdated()
        }
    }

    override fun listItemClicked(list: TaskList) {
        clickListener.listItemTapped(list)
    }

}
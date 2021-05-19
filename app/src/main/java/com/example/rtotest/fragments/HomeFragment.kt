    package com.example.rtotest.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.rtotest.R
import com.example.rtotest.adapter.HomeListAdapter
import com.example.rtotest.adapter.HomeRvClickListener
import com.example.rtotest.databinding.FragmentHomeBinding
import com.example.rtotest.model.Question
import com.example.rtotest.viewmodels.HomeViewModel


    class HomeFragment : Fragment(), HomeRvClickListener {

    private val mHomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = HomeListAdapter(this)

        mHomeViewModel.listOfQuestion.observe(viewLifecycleOwner){ listQA ->
            mHomeViewModel.listOfTrafficSigns.observe(viewLifecycleOwner){ listTS ->
                adapter.setHomeLists(listQA , listTS)
            }
        }

        binding.homeRecyclerView.adapter = adapter
    }

    override fun onClickRvVertical(data: Question, QNo: Int) {

        val action = HomeFragmentDirections.actionHomeFragmentToQuestionFragment(data)
        findNavController().navigate(action)
    }

    override fun onClickRvHorizontal(position: Int) {

        val action = HomeFragmentDirections.actionHomeFragmentToTrafficSigns(position)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_primary, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.settings_item) {
            findNavController().navigate(R.id.action_global_settingsFragment)
            true
        } else super.onOptionsItemSelected(item)
    }
}
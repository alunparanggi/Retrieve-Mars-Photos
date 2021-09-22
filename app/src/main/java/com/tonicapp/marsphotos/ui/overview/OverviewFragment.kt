package com.tonicapp.marsphotos.ui.overview

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tonicapp.marsphotos.R
import com.tonicapp.marsphotos.databinding.FragmentOverviewBinding
import com.tonicapp.marsphotos.network.MarsApiFilter

class OverviewFragment : Fragment() {

    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentOverviewBinding.inflate(inflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.recyclerView.adapter = MarsPhotoAdapter(
            MarsPhotoAdapter.OnClickListener{
                viewModel.displayPropertyDetails(it)
            }
        )

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, {
            if ( null != it ) {
                this.findNavController()
                    .navigate(OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overview_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
            when(item.itemId){
            R.id.show_buy_menu -> MarsApiFilter.SHOW_BUY
            R.id.show_rent_menu -> MarsApiFilter.SHOW_RENT
            else -> MarsApiFilter.SHOW_ALL
        } )
        return true
    }

}
package com.chugay.cartech.ui.admins_ui.fragments.editmaster

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chugay.cartech.R
import com.chugay.cartech.adapter.RvMasterAdapter
import com.chugay.cartech.databinding.FragmentEditMasterBinding
import com.chugay.cartech.model.Master
import com.chugay.cartech.ui.admins_ui.fragments.editmaster.viewmodel.MasterViewModel
import com.chugay.cartech.ui.admins_ui.fragments.home.viewmodel.ServiceViewModel

class EditMasterFragment : Fragment() {
    private var _binding : FragmentEditMasterBinding?=null
    private val binding get() = _binding!!
    lateinit var masterVm : MasterViewModel
    lateinit var serviceVm : ServiceViewModel
    private var spinnerList = mutableListOf<String>()
    private var rvAdapter : RvMasterAdapter?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditMasterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        masterVm = ViewModelProvider(requireActivity())[MasterViewModel::class.java]
        serviceVm = ViewModelProvider(requireActivity())[ServiceViewModel::class.java]
        masterVm.readAllMasters.observe(requireActivity()){
            setSpinner(it)
        }
        setMenu()
        binding.btnShowMaster.setOnClickListener {
            masterVm.readAllMasters.observe(requireActivity()){
                for (i in it){
                    if (binding.spMyMasters.selectedItem == i.name){
                        loadMasterDetails(i)
                    }
                }
            }
        }
    }

    private fun setMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId){
                    R.id.itemAdd ->{
                        findNavController().navigate(R.id.nav_edit_add_master)
                    }
                }
                return true
                // Handle option Menu Here
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun loadMasterDetails(i: Master) {
        binding.tvCurMasterName.text = i.name
        binding.tvMasterId.text = "id: " + i.id.toString()
        rvAdapter = RvMasterAdapter(i.busy_times)
        binding.rvBusyTimes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBusyTimes.setHasFixedSize(true)
        binding.rvBusyTimes.adapter = rvAdapter
    }

    private fun setSpinner(list: List<Master>) {
        spinnerList.clear()
        for ((j, i) in list.withIndex()){
            spinnerList.add(j, i.name)
        }
        val myAdapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, spinnerList)
        binding.spMyMasters.adapter = myAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
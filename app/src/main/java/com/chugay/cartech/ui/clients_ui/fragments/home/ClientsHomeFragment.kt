package com.chugay.cartech.ui.clients_ui.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chugay.cartech.helper.Constants
import com.chugay.cartech.R
import com.chugay.cartech.adapter.RvServicesAdapter
import com.chugay.cartech.databinding.FragmentClientsHomeBinding
import com.chugay.cartech.sharedpref.MySharedPreferences
import com.chugay.cartech.ui.clients_ui.fragments.home.viewmodel.ClientServiceViewModel
import com.chugay.cartech.ui.shared.login.LoginActivity


class ClientsHomeFragment : Fragment() {
    private var _binding : FragmentClientsHomeBinding?=null
    private val binding get() = _binding!!
    lateinit var serviceVm : ClientServiceViewModel
    private var adapter : RvServicesAdapter ?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentClientsHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        serviceVm = ViewModelProvider(requireActivity())[ClientServiceViewModel::class.java]
        setLogoutMenu()
        loadServices()
    }

    private fun setLogoutMenu() {
        val sp = MySharedPreferences(requireContext())
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.logout_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId){
                    R.id.item_logout ->{
                        sp.removeUserEmail()
                        val intent = Intent(requireContext(), LoginActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                    R.id.clientItemLang ->{
                        findNavController().navigate(R.id.nav_cl_home_lang)
                    }
                }
                return true
                // Handle option Menu Here
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun loadServices() {
        serviceVm.readAllProducts.observe(requireActivity()){
            adapter = RvServicesAdapter(it) { service ->
                findNavController().navigate(R.id.nav_details_cl, bundleOf(Pair(Constants.SERVICE_ID, service.id)))
            }
            binding.rvServices.setHasFixedSize(true)
            binding.rvServices.layoutManager = LinearLayoutManager(requireContext())
            binding.rvServices.adapter = adapter
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
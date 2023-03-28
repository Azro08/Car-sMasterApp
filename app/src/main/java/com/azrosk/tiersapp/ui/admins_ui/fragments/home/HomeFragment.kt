package com.azrosk.tiersapp.ui.admins_ui.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.azrosk.tiersapp.helper.Constants
import com.azrosk.tiersapp.R
import com.azrosk.tiersapp.adapter.RvServicesAdapter
import com.azrosk.tiersapp.databinding.FragmentHomeBinding
import com.azrosk.tiersapp.ui.admins_ui.fragments.editmaster.viewmodel.MasterViewModel
import com.azrosk.tiersapp.ui.admins_ui.fragments.home.viewmodel.ServiceViewModel
import com.azrosk.tiersapp.sharedpref.MySharedPreferences
import com.azrosk.tiersapp.ui.shared.login.LoginActivity

class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding?=null
    private val binding get() = _binding!!
    lateinit var productsViewModel : ServiceViewModel
    private var adapter : RvServicesAdapter ?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        productsViewModel = ViewModelProvider(requireActivity())[ServiceViewModel::class.java]
        loadProducts()
        setMenu()
    }

    private fun setMenu() {
        val sp = MySharedPreferences(requireContext())
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.admin_main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId){
                    R.id.item_logout_admin ->{
                        sp.removeUserEmail()
                        val intent = Intent(requireContext(), LoginActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                    R.id.itemAddService ->{
                        findNavController().navigate(R.id.nav_add_service)
                    }
                    R.id.item_edit_masters ->{
                        findNavController().navigate(R.id.nav_home_edit_masters)
                    }
                    R.id.itemLanguage -> {
                        findNavController().navigate(R.id.nav_home_lang)
                    }
                }
                return true
                // Handle option Menu Here
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun loadProducts() {
        productsViewModel.readAllProducts.observe(requireActivity()){
            adapter = RvServicesAdapter(it){ service ->
                findNavController().navigate(R.id.nav_details_admin, bundleOf(Pair(Constants.SERVICE_ID, service.id)))
            }
            binding.rvAddedItemsAdmin.layoutManager = LinearLayoutManager(requireContext())
            binding.rvAddedItemsAdmin.setHasFixedSize(true)
            binding.rvAddedItemsAdmin.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
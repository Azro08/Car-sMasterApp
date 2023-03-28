package com.azrosk.tiersapp.ui.clients_ui.fragments.myOrders

import android.app.AlertDialog
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
import com.azrosk.tiersapp.R
import com.azrosk.tiersapp.adapter.RvCartAdapter
import com.azrosk.tiersapp.databinding.FragmentClientsMyOrdersBinding
import com.azrosk.tiersapp.helper.Constants
import com.azrosk.tiersapp.model.Cart
import com.azrosk.tiersapp.ui.clients_ui.fragments.myOrders.viewmodel.CartViewModel
import com.azrosk.tiersapp.ui.clients_ui.fragments.ordersHistory.viewmodel.OrderViewModel
import com.azrosk.tiersapp.sharedpref.MySharedPreferences
import com.azrosk.tiersapp.ui.clients_ui.actitvity.ClientsActivity

class ClientsMyOrdersFragment : Fragment() {
    private var _binding : FragmentClientsMyOrdersBinding?=null
    private val binding get() = _binding!!
    private var adapter : RvCartAdapter ?= null
    private lateinit var orderVm : OrderViewModel
    private lateinit var cartVm : CartViewModel
    private val ownerList : MutableList<Cart> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentClientsMyOrdersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        orderVm = ViewModelProvider(requireActivity())[OrderViewModel::class.java]
        cartVm = ViewModelProvider(requireActivity())[CartViewModel::class.java]
        val sp = MySharedPreferences(requireContext())
        val cur_user = sp.getEmail()
        setMenu()
        loadMyOrders(cur_user!!)
    }

    private fun loadMyOrders(curUser: String) {
        ownerList.clear()
        cartVm.readCartItems.observe(requireActivity()){ cartItems ->
            for (i in cartItems){
                if (i.user == curUser){
                    ownerList.add(0, i)
                    adapter = RvCartAdapter(ownerList) { myCart ->
                        goToOrder(myCart)
                    }
                    binding.rvMyOrder.setHasFixedSize(true)
                    binding.rvMyOrder.layoutManager = LinearLayoutManager(requireContext())
                    binding.rvMyOrder.adapter = adapter
                }
            }
        }

    }

    private fun setMenu() {
        val sp = MySharedPreferences(requireContext())
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.orders_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId){
                    R.id.itemShowHistory ->{
                        findNavController().navigate(R.id.nav_cart_history)
                    }
                    R.id.clearCart -> {
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setTitle("clear all")
                        builder.setMessage("are you sure?")
                        builder.setPositiveButton("YES") { dialog, which ->
                            if (ownerList.size != 0){
                                for (i in ownerList){
                                    cartVm.deleteFromCart(i)
                                }
                            }
                            val intent = Intent(requireContext(), ClientsActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                        }
                        builder.setNegativeButton("NO") { dialog, which ->
                            dialog.dismiss()
                        }
                        builder.show()
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun goToOrder(myCart: Cart) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("")
        builder.setMessage("order service?")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            findNavController().navigate(R.id.nav_cart_order, bundleOf(Pair(
                Constants.SERVICE_ID, myCart.service_id))
            )
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
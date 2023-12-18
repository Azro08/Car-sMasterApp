package com.chugay.cartech.ui.clients_ui.fragments.order

import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.chugay.cartech.databinding.FragmentClOrderServiceBinding
import com.chugay.cartech.helper.Constants
import com.chugay.cartech.model.Master
import com.chugay.cartech.model.Order
import com.chugay.cartech.sharedpref.MySharedPreferences
import com.chugay.cartech.ui.clients_ui.actitvity.ClientsActivity
import com.chugay.cartech.ui.clients_ui.fragments.order.viewmodel.ClMastersViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

class ClOrderServiceFragment : Fragment() {
    private var _binding: FragmentClOrderServiceBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: ClMastersViewModel
    private val timesList = listOf("10:00 - 12:00", "14:00 - 16:00", "18:00 - 19:00")
    private var title = ""
    private var busyTimes = ArrayList<String>()
    private var spinnerList = mutableListOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentClOrderServiceBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity())[ClMastersViewModel::class.java]
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        viewModel.readAllMasters.observe(viewLifecycleOwner) {
            setSpinner(it)
        }

        val myAdapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            timesList
        )
        binding.spPickedTime.adapter = myAdapter

        // Date Picker Dialog
        val dialog = datePickerDialog()
        // Button for Showing Date Picker Dialog
        binding.btnOrderDate.setOnClickListener {
            // Show Date Picker
            dialog.show()
            // Hide Year Selector
            val year = dialog.findViewById<View>(
                Resources.getSystem().getIdentifier("android:id/year", null, null)
            )
            if (year != null) {
                year.visibility = View.GONE
            }
        }
        val service_id = arguments?.getInt(Constants.SERVICE_ID)
        val sp = MySharedPreferences(requireContext())
        val buyer = sp.getEmail()
        Handler(Looper.getMainLooper()).postDelayed(
            {
                binding.ivGif.visibility = View.GONE
                binding.llDetails.visibility = View.VISIBLE
                if (service_id != null && buyer != null && spinnerList.isNotEmpty()) {
                    val masterName = binding.spinnerMasters.selectedItem
                    lifecycleScope.launch {
                        viewModel.readAllProducts.observe(requireActivity()) {
                            for (i in it) {
                                if (i.id == service_id) {
                                    title = i.title
                                }
                            }
                        }
                    }
                } else {
                    binding.llDetails.visibility = View.GONE
                    binding.tvErrOrder.visibility = View.VISIBLE
                    binding.tvErrOrder.text =
                        "Something went wrong! \n service no longer availabele or no available masters"
                }
            }, 3000L
        )

        binding.btnFinishOrder.setOnClickListener {
            Log.d("FinishFragment", "Finish")
            finishOrder(service_id!!, buyer!!, title)
        }
    }

    private fun getBusyTimesList(): List<String> {
        val busyTime = mutableListOf<String>()
        viewModel.readAllMasters.observe(requireActivity()) {
            busyTime.clear()
            for (i in it) {
                if (binding.spinnerMasters.selectedItem == i.name) {
                    for (j in i.busy_times) {
                        busyTime.add(j)
                    }
                }
            }
        }
        return busyTime
    }

    private fun isMasterBusy(): Int {
        val busyTimesList = getBusyTimesList()
        var isBusy = 0
        val date = binding.tvOrderPickedDate.text.toString()
        val hrs = binding.spPickedTime.selectedItem.toString()
        val timePicked = "$date $hrs"
        for (i in busyTimesList) {
            if (i == timePicked) {
                isBusy = 1
                break
            } else isBusy = 0
        }
        return isBusy
    }


    private fun finishOrder(ser_id: Int, buyer: String, title: String) {
        val masterName = binding.spinnerMasters.selectedItem.toString()
        val cl_comment = binding.etClientComment.text.toString()
        val date = binding.tvOrderPickedDate.text.toString()
        val hrs = binding.spPickedTime.selectedItem.toString()
        val carType = binding.etOrderCarType.text.toString()
        val carModel = binding.etOrderCarModel.text.toString()
        val timePicked = "$date $hrs"

        val master = viewModel.readAllMasters.value?.find { it.name == masterName }

        if (TextUtils.isEmpty(date.trim()) || TextUtils.isEmpty(cl_comment.trim()) ||
            TextUtils.isEmpty(carType.trim()) || TextUtils.isEmpty(carModel.trim())) {
            // Handle empty fields error
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (master != null) {
            val busyTimesList = master.busy_times

            if (busyTimesList.contains(timePicked)) {
                // Master is busy at the selected time
                Toast.makeText(requireContext(), "Мастер занят в это время", Toast.LENGTH_SHORT).show()
            } else {
                // Master is available, proceed with order submission
                val order = Order(
                    service_id = ser_id,
                    buyer = buyer,
                    phone_num = binding.etBuyerPhoneNum.text.toString(),
                    order_time = timePicked,
                    service_title = title,
                    client_comment = cl_comment,
                    car_model = carModel,
                    car_type = carType,
                    picked_master = masterName
                )

                busyTimesList.add(timePicked)

                lifecycleScope.launch {
                    viewModel.updateMaster(
                        Master(
                            id = master.id,
                            name = master.name,
                            busy_times = busyTimesList
                        )
                    )
                    viewModel.addOrder(order)
                    Toast.makeText(requireContext(), "Order Finished", Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireContext(), ClientsActivity::class.java)
                    startActivity(intent)

                    requireActivity().finish()
                }
            }
        }
    }

    private fun datePickerDialog(): DatePickerDialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        // Date Picker Dialog
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            android.R.style.Theme_Material_Light_Dialog,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in textbox
                binding.tvOrderPickedDate.text = "$dayOfMonth/$monthOfYear/$year"
            },
            year,
            month,
            day
        )
        // Show Date Picker
        datePickerDialog.datePicker.minDate = (System.currentTimeMillis() - 1000)
        return datePickerDialog
    }

    private fun setSpinner(list: List<Master>) {
        spinnerList.clear()
        for ((j, i) in list.withIndex()) {
            spinnerList.add(j, i.name)
        }
        val myAdapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            spinnerList
        )
        binding.spinnerMasters.adapter = myAdapter
    }

}
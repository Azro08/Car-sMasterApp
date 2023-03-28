package com.azrosk.tiersapp.ui.clients_ui.fragments.location

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.style.UnderlineSpan
import android.view.*
import androidx.fragment.app.Fragment
import com.azrosk.tiersapp.databinding.FragmentClientsLocationBinding


class ClientsLocationFragment : Fragment() {
    private var _binding : FragmentClientsLocationBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentClientsLocationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val stringNum = android.text.SpannableString("+375 (25) 535-73-46")
        stringNum.setSpan(UnderlineSpan(), 0, stringNum.length, 0)
        binding.tvPhoneNum.text = stringNum
        binding.tvPhoneNum.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$stringNum")
            startActivity(intent)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
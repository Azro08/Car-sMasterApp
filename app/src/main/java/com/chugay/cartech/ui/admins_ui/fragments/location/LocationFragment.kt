package com.chugay.cartech.ui.admins_ui.fragments.location

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.style.UnderlineSpan
import android.view.*
import androidx.fragment.app.Fragment
import com.chugay.cartech.databinding.FragmentAdminLocationBinding


class LocationFragment : Fragment() {
    private var _binding : FragmentAdminLocationBinding ?= null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAdminLocationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val stringNum = android.text.SpannableString("+375 (25) 535-73-46")
        stringNum.setSpan(UnderlineSpan(), 0, stringNum.length, 0)
        binding.tvClPhoneNum.text = stringNum
        binding.tvClPhoneNum.setOnClickListener{
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
package com.example.aviator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class MenuFragment : Fragment() {
    private lateinit var buttonPlay: Button
    private lateinit var buttonSetting: Button
    private lateinit var buttonInfo: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        buttonSetting = view.findViewById<Button>(R.id.button_setting)
        buttonInfo = view.findViewById<Button>(R.id.button_info)
        buttonPlay = view.findViewById<Button>(R.id.button_menu_play)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonPlay.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.container, PlayFragment(), null)
                .commit()
        }
    }
}
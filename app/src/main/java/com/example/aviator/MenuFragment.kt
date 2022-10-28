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
        buttonInfo.isClickable = true
        buttonSetting.isClickable = true
        buttonPlay.isClickable = true
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonPlay.setOnClickListener {
            (requireActivity() as MainActivity).pushBackStack(PlayFragment())
            buttonInfo.isClickable = false
            buttonSetting.isClickable = false
            buttonPlay.isClickable = false
        }
        buttonSetting.setOnClickListener {
            (requireActivity() as MainActivity).pushBackStack(SettingFragment())
            buttonInfo.isClickable = false
            buttonSetting.isClickable = false
            buttonPlay.isClickable = false
        }
        buttonInfo.setOnClickListener {
            (requireActivity() as MainActivity).pushBackStack(InfoFragment())
            buttonInfo.isClickable = false
            buttonSetting.isClickable = false
            buttonPlay.isClickable = false
        }
    }
}
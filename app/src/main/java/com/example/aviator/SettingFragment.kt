package com.example.aviator

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.fragment.app.Fragment

private const val SHARED_QUERY = "shared_prefs"
private const val MUTE = "mute"

class SettingFragment : Fragment() {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var volumeIsChecked: Switch
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        volumeIsChecked = view.findViewById(R.id.volume)
        val sharedPrefs = requireContext().getSharedPreferences(SHARED_QUERY, Context.MODE_PRIVATE)
        volumeIsChecked.isChecked=sharedPrefs.getBoolean(MUTE,true)
        volumeIsChecked.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sharedPrefs.edit().putBoolean(MUTE, isChecked).apply()
            } else {
                sharedPrefs.edit().putBoolean(MUTE, isChecked).apply()
            }
        }
        view.findViewById<Button>(R.id.button_menu).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.container, MenuFragment(), null)
                .commit()
        }
    }
}
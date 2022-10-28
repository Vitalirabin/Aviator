package com.example.aviator

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment

class SettingFragment : Fragment() {
    private var volumeIsChecked: Boolean = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        return view
    }

  /*  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (volumeIsChecked) {
            val audioManager = AudioManager.getSystemService(Context.AUDIO_SERVICE);
            audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
        } else {
            val audioManager = AudioManager.getSystemService(Context.AUDIO_SERVICE);
            audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
        }
    }*/
}
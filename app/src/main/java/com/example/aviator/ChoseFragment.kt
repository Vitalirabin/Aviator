package com.example.aviator

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class ChoseFragment : DialogFragment() {

    private var retry = false
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("")
                .setCancelable(true)
                .setPositiveButton("Начать заново") { _, _ ->
                    retry = false
                }
                .setNegativeButton(
                    "Перезапустить через 3 секунды"
                ) { _, _ ->
                    retry = true
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

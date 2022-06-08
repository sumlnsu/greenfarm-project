package com.greenfarm.ui

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.greenfarm.R

class PhoneNumberDialog(context: Context) : Dialog(context){

    private lateinit var cancelButton : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_phonenumber)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        cancelButton = findViewById(R.id.delete_ic)

        cancelButton.setOnClickListener{
            dismiss()
            Log.d("click","click")
        }

    }
}
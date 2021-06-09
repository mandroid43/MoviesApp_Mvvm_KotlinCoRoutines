package com.manapps.mandroid.moviesapp_mvc_ret.utils

import android.content.Context
import android.widget.Toast

object Utils {


    fun showMessage(context: Context,message:String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}
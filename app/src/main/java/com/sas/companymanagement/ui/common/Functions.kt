package com.sas.companymanagement.ui.common

import android.app.Activity
import android.widget.Toast

fun dateToString(date: String): String {
    return date.replace("[^[0-9] ]".toRegex(), "").substring(2).replace(" ", ".")
}

fun toastMessage(message: String, owner: Activity){
    Toast.makeText(owner, message, Toast.LENGTH_SHORT).show()
}
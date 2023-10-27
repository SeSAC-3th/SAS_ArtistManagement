package com.sas.companymanagement.ui.common

import android.app.Activity
import android.widget.Toast
import java.util.Random

const val SCORE_SIZE = 5 - 1
const val SCORE_START = 0


fun dateToString(date: String): String {
    return date.replace("[^[0-9] ]".toRegex(), "").substring(2).replace(" ", ".")
}

/**
 * Toast message 처리
 *
 * @param message 띄울 메시지
 * @param owner 해당 액티비티
 */
fun toastMessage(message: String, owner: Activity){
    Toast.makeText(owner, message, Toast.LENGTH_SHORT).show()
}

fun getRandomListToString(): String {
    var s = ""
    for (item in SCORE_START..SCORE_SIZE) {
        val random:Int = (Random().nextInt(89) + 10) / 10
        s += random.toString()
    }
    return s
}


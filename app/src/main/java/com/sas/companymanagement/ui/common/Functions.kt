package com.sas.companymanagement.ui.common

import android.app.Activity
import android.widget.Toast
import java.util.Random

const val SCORE_SIZE = 5 - 1
const val SCORE_START = 0


/**
 * Date to string
 *
 * @param date  ex) 2023년 10월 10일
 * @return 23.10.10
 */
fun dateToString(date: String): String {
    return date.replace("[^[0-9] ]".toRegex(), "").substring(2).replace(" ", ".")
}

fun toastMessage(message: String, owner: Activity){
    Toast.makeText(owner, message, Toast.LENGTH_SHORT).show()
}

/**
 * Get random list to string
 * 1의 자리 랜덤 값 5개를 string 으로 이어 return
 * @return
 */
fun getRandomListToString(): String {
    var s = ""
    for (item in SCORE_START..SCORE_SIZE) {
        // 산포도 높이기 위한 코드
        val random:Int = (Random().nextInt(89) + 10) / 10
        s += random.toString()
    }
    return s
}


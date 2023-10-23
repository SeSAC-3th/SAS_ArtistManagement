package com.sas.companymanagement.ui.common

fun dateToString(date: String): String {
    return date.replace("[^[0-9] ]".toRegex(), "").substring(2).replace(" ", ".")
}
package com.sas.companymanagement.ui.artist


const val JOB_ACTOR = "배우"
const val JOB_SINGER = "가수"
const val JOB_TALENT = "탤런트"
const val GENDER_MALE = "남자"
const val GENDER_FEMALE = "여자"


enum class ArtistCategory(val job: String) {
    ACTOR(JOB_ACTOR),
    TALENT(JOB_TALENT),
    SINGER(JOB_SINGER)
}

enum class ArtistGender(val gender: String) {
    MALE(GENDER_MALE), FEMALE(GENDER_FEMALE)
}
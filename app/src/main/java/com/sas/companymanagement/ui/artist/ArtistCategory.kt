package com.sas.companymanagement.ui.artist

import com.sas.companymanagement.ui.common.*

enum class ArtistCategory(val job: String) {
    ACTOR(JOB_ACTOR),
    TALENT(JOB_TALENT),
    SINGER(JOB_SINGER)
}

enum class ArtistGender(val gender: String) {
    MALE(GENDER_MALE), FEMALE(GENDER_FEMALE)
}
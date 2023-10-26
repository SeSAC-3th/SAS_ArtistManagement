package com.sas.companymanagement.ui.artist

import com.sas.companymanagement.ui.common.GENDER_FEMALE
import com.sas.companymanagement.ui.common.GENDER_MALE
import com.sas.companymanagement.ui.common.JOB_ACTOR
import com.sas.companymanagement.ui.common.JOB_SINGER
import com.sas.companymanagement.ui.common.JOB_TALENT

enum class ArtistCategory(val job: String) {
    ACTOR(JOB_ACTOR),
    TALENT(JOB_TALENT),
    SINGER(JOB_SINGER)
}

enum class ArtistGender(val gender: String) {
    MALE(GENDER_MALE), FEMALE(GENDER_FEMALE)
}
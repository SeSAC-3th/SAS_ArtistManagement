package com.sas.companymanagement.ui.artist

enum class ArtistCategory(val job: String) {
    ACTOR("배우"),
    TALENT("탤랜트"),
    SINGER("가수")
}

enum class ArtistGender(val gender: String) {
    MALE("남자"), FEMALE("여자")
}
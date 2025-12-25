package com.example.praktikum12.modeldata

import kotlinx.serialization.Serializable

@Serializable

data class DataSiswa(
    val id: Int,
    val nama: String,
    val alamat: String,
    val telpon: String
)

data class UIStateSiswa(
    val detailSiswa: DetailSiswa = DetailSiswa(),
    val isEntryValid: Boolean = false
)

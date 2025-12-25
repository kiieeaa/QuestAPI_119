package com.example.praktikum12.uicontroller.route

import com.example.praktikum12.R

object DestinasiDetail : DestinasiNavigasi {
    override val route = "item_details"
    override val titleRes = R.string.detail_siswa
    const val siswaIdArg = "itemId" // Kunci argumen navigasi
    val routeWithArgs = "$route/{$siswaIdArg}"
}
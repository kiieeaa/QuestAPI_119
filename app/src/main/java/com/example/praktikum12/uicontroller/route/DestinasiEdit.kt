package com.example.praktikum12.uicontroller.route

import com.example.praktikum12.R

object DestinasiEdit : DestinasiNavigasi {
    override val route = "item_edit"
    override val titleRes = R.string.edit_siswa
    // Pastikan nama variabel ini siswaIdArg
    const val siswaIdArg = "itemId"
    val routeWithArgs = "$route/{$siswaIdArg}"
}
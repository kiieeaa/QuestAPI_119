package com.example.praktikum12.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.praktikum12.modeldata.DetailSiswa
import com.example.praktikum12.modeldata.UIStateSiswa
import com.example.praktikum12.modeldata.toDataSiswa
import com.example.praktikum12.repositori.RepositoryDataSiswa
import retrofit2.Response
import com.example.praktikum12.modeldata.toDataSiswa
class EntryViewModel(private val repositoryDataSiswa: RepositoryDataSiswa) : ViewModel() {
    // Contoh di EntryViewModel
    var uiStateSiswa: UIStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    /* Fungsi untuk memvalidasi input */
    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa): Boolean {
        return with(uiState) {
            // Tambahkan nim.isNotBlank()
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank() && nim.isNotBlank()
        }
    }

    // Fungsi untuk menangani saat ada perubahan pada text input
    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa = UIStateSiswa(
            detailSiswa = detailSiswa,
            isEntryValid = validasiInput(detailSiswa)
        )
    }

    /* Fungsi untuk menyimpan data yang di-entry */
    suspend fun addSiswa() {
        if (validasiInput()) {
            val sip: Response<Void> = repositoryDataSiswa.postDataSiswa(
                uiStateSiswa.detailSiswa.toDataSiswa()
            )
            if (sip.isSuccessful) {
                println("Sukses Tambah Data : ${sip.message()}")
            } else {
                println("Gagal tambah data : ${sip.errorBody()}")
            }
        }
    }
}
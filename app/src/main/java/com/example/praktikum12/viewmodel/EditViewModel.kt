package com.example.praktikum12.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikum12.modeldata.DetailSiswa
import com.example.praktikum12.modeldata.UIStateSiswa
import com.example.praktikum12.modeldata.toDataSiswa
import com.example.praktikum12.repositori.RepositoryDataSiswa
import com.example.praktikum12.uicontroller.route.DestinasiEdit
import kotlinx.coroutines.launch

class EditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryDataSiswa: RepositoryDataSiswa
) : ViewModel() {
    // PASTIKAN NAMA VARIABEL ADALAH siswaUiState
    var siswaUiState by mutableStateOf(UIStateSiswa())
        private set

    private val siswaId: Int = checkNotNull(savedStateHandle[DestinasiEdit.siswaIdArg])

    init {
        viewModelScope.launch {
            val siswa = repositoryDataSiswa.getSiswaById(siswaId)
            siswaUiState = UIStateSiswa(
                detailSiswa = DetailSiswa(
                    id = siswa.id,
                    nama = siswa.nama,
                    alamat = siswa.alamat,
                    telpon = siswa.telpon,
                    nim = siswa.nim
                ),
                isEntryValid = true
            )
        }
    }

    fun updateUiState(detailSiswa: DetailSiswa) {
        siswaUiState = UIStateSiswa(
            detailSiswa = detailSiswa,
            isEntryValid = validasiInput(detailSiswa)
        )
    }

    private fun validasiInput(uiState: DetailSiswa): Boolean {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank() && nim.isNotBlank()
        }
    }

    suspend fun updateSiswa() {
        if (validasiInput(siswaUiState.detailSiswa)) {
            repositoryDataSiswa.updateSiswa(siswaId, siswaUiState.detailSiswa.toDataSiswa())
        }
    }
}
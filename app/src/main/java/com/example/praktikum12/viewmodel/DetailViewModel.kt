package com.example.praktikum12.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikum12.modeldata.DataSiswa
import com.example.praktikum12.repositori.RepositoryDataSiswa
import com.example.praktikum12.uicontroller.route.DestinasiDetail
import kotlinx.coroutines.launch

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryDataSiswa: RepositoryDataSiswa
) : ViewModel() {
    private val siswaId: Int = checkNotNull(savedStateHandle[DestinasiDetail.siswaIdArg])

    var uiState by mutableStateOf(DetailUiState())
        private set

    init {
        getSiswaById()
    }

    private fun getSiswaById() {
        viewModelScope.launch {
            val siswa = repositoryDataSiswa.getSiswaById(siswaId)
            uiState = DetailUiState(detailSiswa = siswa)
        }
    }

    suspend fun deleteSiswa() {
        repositoryDataSiswa.deleteSiswa(siswaId)
    }
}

data class DetailUiState(
    val detailSiswa: DataSiswa = DataSiswa(nama = "", alamat = "", telpon = "", nim = "")
)
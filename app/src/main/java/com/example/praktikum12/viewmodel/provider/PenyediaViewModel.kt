package com.example.praktikum12.viewmodel.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.praktikum12.repositori.AplikasiDataSiswa // Pastikan import ini sesuai lokasi file AplikasiDataSiswa kamu
import com.example.praktikum12.viewmodel.DetailViewModel
import com.example.praktikum12.viewmodel.EditViewModel
import com.example.praktikum12.viewmodel.EntryViewModel
import com.example.praktikum12.viewmodel.HomeViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        // 1. HomeViewModel
        initializer {
            HomeViewModel(aplikasiDataSiswa().container.repositoryDataSiswa)
        }

        // 2. EntryViewModel
        initializer {
            EntryViewModel(aplikasiDataSiswa().container.repositoryDataSiswa)
        }

        // 3. DetailViewModel
        initializer {
            DetailViewModel(
                createSavedStateHandle(), // Memerlukan SavedStateHandle untuk navigasi
                aplikasiDataSiswa().container.repositoryDataSiswa
            )
        }

        // 4. EditViewModel
        initializer {
            EditViewModel(
                createSavedStateHandle(), // Memerlukan SavedStateHandle untuk navigasi
                aplikasiDataSiswa().container.repositoryDataSiswa
            )
        }
    }
}

/**
 * Fungsi ekstensi untuk mengambil instance aplikasi dan container-nya
 */
fun CreationExtras.aplikasiDataSiswa(): AplikasiDataSiswa =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AplikasiDataSiswa)
package com.example.praktikum12.repositori

import com.example.praktikum12.apiservice.ServiceApiSiswa
import com.example.praktikum12.modeldata.DataSiswa
import retrofit2.Response

interface RepositoryDataSiswa {
    suspend fun getDataSiswa(): List<DataSiswa>
    suspend fun postDataSiswa(dataSiswa: DataSiswa): Response<Void>
    suspend fun getSiswaById(id: Int): DataSiswa
    suspend fun updateSiswa(id: Int, dataSiswa: DataSiswa): Response<Void>
    suspend fun deleteSiswa(id: Int): Response<Void>
}

// Pastikan class ini ada di bawah interface
class JaringanRepositoryDataSiswa(
    private val apiSiswa: ServiceApiSiswa
) : RepositoryDataSiswa {
    override suspend fun getDataSiswa(): List<DataSiswa> = apiSiswa.getDataSiswa()
    override suspend fun postDataSiswa(dataSiswa: DataSiswa): Response<Void> = apiSiswa.postDataSiswa(dataSiswa)
    override suspend fun getSiswaById(id: Int): DataSiswa = apiSiswa.getSiswaById(id)
    override suspend fun updateSiswa(id: Int, dataSiswa: DataSiswa): Response<Void> = apiSiswa.updateSiswa(id, dataSiswa)
    override suspend fun deleteSiswa(id: Int): Response<Void> = apiSiswa.deleteSiswa(id)
}
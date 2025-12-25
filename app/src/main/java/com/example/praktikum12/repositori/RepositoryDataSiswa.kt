package com.example.praktikum12.repositori

import com.example.praktikum12.apiservice.ServiceApiSiswa
import com.example.praktikum12.modeldata.DataSiswa
import retrofit2.Response

interface RepositoryDataSiswa {
    suspend fun getDataSiswa(): List<DataSiswa>
    suspend fun postDataSiswa(dataSiswa: DataSiswa): Response<Void>
    suspend fun getSatuSiswa(id: Int): DataSiswa
    suspend fun editSatuSiswa(id: Int, dataSiswa: DataSiswa): Response<Void>
    suspend fun hapusSatuSiswa(id: Int): Response<Void>
}

class JaringanRepositoryDataSiswa(
    private val serviceApiSiswa: ServiceApiSiswa
) : RepositoryDataSiswa {
    override suspend fun getDataSiswa(): List<DataSiswa> = serviceApiSiswa.getSiswa()

    override suspend fun postDataSiswa(dataSiswa: DataSiswa): Response<Void> =
        serviceApiSiswa.postSiswa(dataSiswa)

    override suspend fun getSatuSiswa(id: Int): DataSiswa = serviceApiSiswa.getSatuSiswa(id)

    override suspend fun editSatuSiswa(id: Int, dataSiswa: DataSiswa): Response<Void> =
        serviceApiSiswa.editSatuSiswa(id, dataSiswa)

    override suspend fun hapusSatuSiswa(id: Int): Response<Void> =
        serviceApiSiswa.hapusSatuSiswa(id)
}
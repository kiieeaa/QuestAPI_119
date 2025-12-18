package com.example.praktikum12.apiservice

import com.example.praktikum12.modeldata.DataSiswa
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ServiceApiSiswa {
    @GET("bacasiswa.php")
    suspend fun getDataSiswa(): List<DataSiswa>

    @POST("tambahsiswa.php")
    suspend fun postDataSiswa(@Body dataSiswa: DataSiswa): Response<Void>

    // Tambahkan di dalam interface ServiceApiSiswa
    @GET("bacasatu.php") // sesuaikan nama file php di server
    suspend fun getSiswaById(@Query("id") id: Int): DataSiswa

    @PUT("updatesiswa.php")
    suspend fun updateSiswa(@Query("id") id: Int, @Body dataSiswa: DataSiswa): Response<Void>

    @DELETE("hapussiswa.php")
    suspend fun deleteSiswa(@Query("id") id: Int): Response<Void>
}
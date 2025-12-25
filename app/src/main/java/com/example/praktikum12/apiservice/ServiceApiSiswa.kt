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
    suspend fun getSiswa(): List<DataSiswa>
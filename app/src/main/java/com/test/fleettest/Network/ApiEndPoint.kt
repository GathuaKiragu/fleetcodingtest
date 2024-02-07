package com.test.fleettest.Network

import com.test.fleettest.data.model.GithubRepositoryModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiEndPoint {
    @GET("repositories")
    suspend fun getAllRepo(): Response<List<GithubRepositoryModel>>


}
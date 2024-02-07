package com.test.fleettest.data

import com.test.fleettest.Network.ApiEndPoint
import com.test.fleettest.Network.RetrofitClient

class GithubRepository {
    private val retrofit = RetrofitClient.getRetrofitInstance().create(ApiEndPoint::class.java)

    suspend fun getAllRepository(i: Int) = retrofit.getAllRepo()
}
package gq.kirmanak.mealient.datasource.v0

import gq.kirmanak.mealient.datasource.v0.models.*
import retrofit2.http.*

interface MealieServiceV0 {

    @FormUrlEncoded
    @POST
    suspend fun getToken(
        @Url url: String,
        @Field("username") username: String,
        @Field("password") password: String,
    ): GetTokenResponseV0

    @POST
    suspend fun addRecipe(
        @Url url: String,
        @Body addRecipeRequestV0: AddRecipeRequestV0,
    ): String

    @GET
    suspend fun getVersion(
        @Url url: String,
    ): VersionResponseV0

    @GET
    suspend fun getRecipeSummary(
        @Url url: String,
        @Query("start") start: Int,
        @Query("limit") limit: Int,
    ): List<GetRecipeSummaryResponseV0>

    @GET
    suspend fun getRecipe(
        @Url url: String,
    ): GetRecipeResponseV0

    @POST
    suspend fun createRecipeFromURL(
        @Url url: String,
        @Body request: ParseRecipeURLRequestV0,
    ): String

    @POST
    suspend fun createApiToken(
        @Url url: String,
        @Body request: CreateApiTokenRequestV0,
    ): String
}
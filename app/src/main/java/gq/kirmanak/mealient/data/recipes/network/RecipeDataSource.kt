package gq.kirmanak.mealient.data.recipes.network

interface RecipeDataSource {
    suspend fun requestRecipes(start: Int, limit: Int): List<RecipeSummaryInfo>

    suspend fun requestRecipeInfo(slug: String): FullRecipeInfo

    suspend fun getFavoriteRecipes(): List<String>

    suspend fun removeFavoriteRecipe(recipeSlug: String)

    suspend fun addFavoriteRecipe(recipeSlug: String)
}
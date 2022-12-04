package gq.kirmanak.mealient.data.recipes.network

data class FullRecipeInfo(
    val remoteId: String,
    val name: String,
    val recipeYield: String,
    val recipeIngredients: List<RecipeIngredientInfo>,
    val recipeInstructions: List<RecipeInstructionInfo>,
    val settings: RecipeSettingsInfo,
)

data class RecipeSettingsInfo(
    val disableAmounts: Boolean,
)

data class RecipeIngredientInfo(
    val note: String,
    val quantity: Double?,
    val unit: String?,
    val food: String?,
)

data class RecipeInstructionInfo(
    val text: String,
)

package gq.kirmanak.mealie.ui.recipes

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gq.kirmanak.mealie.data.recipes.RecipeImageLoader
import gq.kirmanak.mealie.data.recipes.RecipeRepo
import gq.kirmanak.mealie.data.recipes.db.entity.RecipeSummaryEntity
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    recipeRepo: RecipeRepo,
    private val recipeImageLoader: RecipeImageLoader
) : ViewModel() {
    val recipeFlow = recipeRepo.createPager().flow

    fun loadRecipeImage(view: ImageView, recipeSummary: RecipeSummaryEntity?) {
        viewModelScope.launch {
            recipeImageLoader.loadRecipeImage(view, recipeSummary?.slug)
        }
    }
}
package gq.kirmanak.mealient.ui.recipes

import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import gq.kirmanak.mealient.R
import gq.kirmanak.mealient.database.recipe.entity.RecipeSummaryEntity
import gq.kirmanak.mealient.databinding.ViewHolderRecipeBinding
import gq.kirmanak.mealient.extensions.resources
import gq.kirmanak.mealient.logging.Logger
import gq.kirmanak.mealient.ui.recipes.images.RecipeImageLoader
import javax.inject.Inject
import javax.inject.Singleton

class RecipeViewHolder private constructor(
    private val logger: Logger,
    private val binding: ViewHolderRecipeBinding,
    private val recipeImageLoader: RecipeImageLoader,
    private val clickListener: (RecipeSummaryEntity) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    @Singleton
    class Factory @Inject constructor(
        private val logger: Logger,
    ) {

        fun build(
            recipeImageLoader: RecipeImageLoader,
            binding: ViewHolderRecipeBinding,
            clickListener: (RecipeSummaryEntity) -> Unit,
        ) = RecipeViewHolder(logger, binding, recipeImageLoader, clickListener)

    }

    private val loadingPlaceholder by lazy {
        binding.resources.getString(R.string.view_holder_recipe_text_placeholder)
    }

    fun bind(item: RecipeSummaryEntity?) {
        logger.v { "bind() called with: item = $item" }
        binding.name.text = item?.name ?: loadingPlaceholder
        recipeImageLoader.loadRecipeImage(binding.image, item)
        item?.let { entity ->
            binding.root.setOnClickListener {
                logger.d { "bind: item clicked $entity" }
                clickListener(entity)
            }
            binding.favoriteIcon.setImageResource(
                if (item.isFavorite) {
                    R.drawable.ic_favorite_filled
                } else {
                    R.drawable.ic_favorite_unfilled
                }
            )
            binding.favoriteIcon.setContentDescription(
                if (item.isFavorite) {
                    R.string.view_holder_recipe_favorite_content_description
                } else {
                    R.string.view_holder_recipe_non_favorite_content_description
                }
            )
        }
    }
}

private fun View.setContentDescription(@StringRes resId: Int) {
    contentDescription = context.getString(resId)
}
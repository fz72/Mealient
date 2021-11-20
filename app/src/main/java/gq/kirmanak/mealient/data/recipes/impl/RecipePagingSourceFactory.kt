package gq.kirmanak.mealient.data.recipes.impl

import androidx.paging.PagingSource
import gq.kirmanak.mealient.data.recipes.db.RecipeStorage
import gq.kirmanak.mealient.data.recipes.db.entity.RecipeSummaryEntity
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipePagingSourceFactory @Inject constructor(
    private val recipeStorage: RecipeStorage
) : () -> PagingSource<Int, RecipeSummaryEntity> {
    private val sources: MutableList<PagingSource<Int, RecipeSummaryEntity>> = mutableListOf()

    @Synchronized
    override fun invoke(): PagingSource<Int, RecipeSummaryEntity> {
        Timber.v("invoke() called")
        val newSource = recipeStorage.queryRecipes()
        sources.add(newSource)
        return newSource
    }

    @Synchronized
    fun invalidate() {
        Timber.v("invalidate() called")
        for (source in sources) {
            if (!source.invalid) {
                source.invalidate()
            }
        }
        sources.removeAll { it.invalid }
    }
}
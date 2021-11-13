package gq.kirmanak.mealie.data.recipes.impl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import gq.kirmanak.mealie.data.recipes.RecipeRepo
import gq.kirmanak.mealie.data.recipes.db.RecipeEntity
import gq.kirmanak.mealie.data.recipes.db.RecipeStorage
import javax.inject.Inject

@ExperimentalPagingApi
class RecipeRepoImpl @Inject constructor(
    private val mediator: RecipesRemoteMediator,
    private val storage: RecipeStorage
) : RecipeRepo {
    override fun createPager(): Pager<Int, RecipeEntity> {
        val pagingConfig = PagingConfig(pageSize = 30, enablePlaceholders = false, prefetchDistance = 10)
        return Pager(pagingConfig, 0, mediator) {
            storage.queryRecipes()
        }
    }
}
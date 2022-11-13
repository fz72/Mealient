package gq.kirmanak.mealient.data.recipes.impl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.InvalidatingPagingSourceFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import gq.kirmanak.mealient.data.recipes.RecipeRepo
import gq.kirmanak.mealient.data.recipes.db.RecipeStorage
import gq.kirmanak.mealient.data.recipes.network.RecipeDataSource
import gq.kirmanak.mealient.database.recipe.entity.FullRecipeEntity
import gq.kirmanak.mealient.database.recipe.entity.RecipeSummaryEntity
import gq.kirmanak.mealient.datasource.runCatchingExceptCancel
import gq.kirmanak.mealient.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Singleton
class RecipeRepoImpl @Inject constructor(
    private val mediator: RecipesRemoteMediator,
    private val storage: RecipeStorage,
    private val pagingSourceFactory: RecipePagingSourceFactory,
    private val invalidatingPagingSourceFactory: InvalidatingPagingSourceFactory<Int, RecipeSummaryEntity>,
    private val dataSource: RecipeDataSource,
    private val logger: Logger,
) : RecipeRepo {

    override fun createPager(): Pager<Int, RecipeSummaryEntity> {
        logger.v { "createPager() called" }
        val pagingConfig = PagingConfig(pageSize = 5, enablePlaceholders = true)
        return Pager(
            config = pagingConfig,
            remoteMediator = mediator,
            pagingSourceFactory = invalidatingPagingSourceFactory,
        )
    }

    override suspend fun clearLocalData() {
        logger.v { "clearLocalData() called" }
        storage.clearAllLocalData()
    }

    override suspend fun refreshRecipeInfo(recipeSlug: String): Result<Unit> {
        logger.v { "refreshRecipeInfo() called with: recipeSlug = $recipeSlug" }
        return runCatchingExceptCancel {
            storage.saveRecipeInfo(dataSource.requestRecipeInfo(recipeSlug))
        }.onFailure {
            logger.e(it) { "loadRecipeInfo: can't update full recipe info" }
        }
    }

    override suspend fun loadRecipeInfo(recipeId: String): FullRecipeEntity? {
        logger.v { "loadRecipeInfo() called with: recipeId = $recipeId" }
        val recipeInfo = storage.queryRecipeInfo(recipeId)
        logger.v { "loadRecipeInfo() returned: $recipeInfo" }
        return recipeInfo
    }

    override fun setSearchName(name: String?) {
        logger.v { "setSearchName() called with: name = $name" }
        pagingSourceFactory.setQuery(name)
        invalidatingPagingSourceFactory.invalidate()
    }
}
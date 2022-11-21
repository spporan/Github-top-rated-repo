package poran.cse.github_top_rated_repo.data.util

import androidx.datastore.preferences.core.stringPreferencesKey

val SORTING_CACHE_PARAM = stringPreferencesKey("current_sorting")

enum class Sorting {
    DSC,
    ASC
}

fun String.toSortedOrder(): Sorting {
    return when(this) {
       Sorting.ASC.name -> Sorting.ASC
        else ->  Sorting.DSC
    }
}
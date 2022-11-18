package poran.cse.github_top_rated_repo.data.model

import androidx.room.TypeConverter
import com.google.gson.Gson

/**
 * TypeConverter is responsible for serialize and  deserialize object  and help to store in room
 */
class TypeConverter {

    @TypeConverter
    fun fromRepoOwner(repoOwner: RepoOwner): String {
        return Gson().toJson(repoOwner)
    }

    @TypeConverter
    fun toRepoOwner(jsonString: String) : RepoOwner {
        return Gson().fromJson(jsonString, RepoOwner::class.java)
    }
}
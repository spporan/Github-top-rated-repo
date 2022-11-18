package poran.cse.github_top_rated_repo.data.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * AndroidRepo data class which represent android repo room table and also serialize from api data.
 */

@Entity(tableName = "android_repository")
@Keep
data class AndroidRepo(
    @PrimaryKey
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("html_url")
    val url: String,
    @SerializedName("html_url")
    val owner: RepoOwner,
    @SerializedName("stargazers_count")
    val stars: Int,
    @SerializedName("forks_count")
    val forks: Int,
    @SerializedName("language")
    val language: String?
)

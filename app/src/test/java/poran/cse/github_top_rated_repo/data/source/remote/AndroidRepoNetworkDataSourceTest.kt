package poran.cse.github_top_rated_repo.data.source.remote


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import poran.cse.github_top_rated_repo.data.source.remote.api.GithubApiService
import poran.cse.github_top_rated_repo.data.util.MockResponseFileReader
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import poran.cse.github_top_rated_repo.data.util.enqueueResponse
import java.util.concurrent.TimeUnit


class AndroidRepoNetworkDataSourceTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(4, TimeUnit.SECONDS)
        .readTimeout(4, TimeUnit.SECONDS)
        .writeTimeout(4, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GithubApiService::class.java)

    private val dataSource = AndroidRepoNetworkDataSource(api)

    @Test
    fun `read sample success json file`(){
        val reader = MockResponseFileReader("api-response/repo-search-response-success-200.json")
        assertNotNull(reader.content)
    }

    @Test
    fun `should fetch android repo correct number of items and item per page should 10`() {
        mockWebServer.enqueueResponse("repo-search-response-success-200.json", 200)
        runBlocking {
            val actual = dataSource.fetchRepos(1,10)

            assert(actual.items.size == 10)
        }
    }

    @Test
    fun `should fetch android repo correctly and not empty items`() {
        mockWebServer.enqueueResponse("repo-search-response-success-200.json", 200)

        runBlocking {
            val actual = dataSource.fetchRepos(1,10)

           /* val expected = listOf(
                AndroidRepo(
                    id = 82128465,
                    name = "Android",
                    fullName = "hmkcode/Android",
                    description = "Android related examples",
                    owner = RepoOwner(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,null,
                        null
                    ),
                    stars = 100,
                    forks = 10,
                    language = null,
                    htmlUrl = ""
                )
            )*/
            assert(actual.items.isNotEmpty())
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

}
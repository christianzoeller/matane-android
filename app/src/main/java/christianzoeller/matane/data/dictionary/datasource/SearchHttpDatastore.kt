package christianzoeller.matane.data.dictionary.datasource

import christianzoeller.matane.data.dictionary.model.VocabularyOverview
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Inject

class SearchHttpDatastore @Inject constructor() {
    // TODO how to properly close the client in this setup?
    // TODO there should be a module for the client so that it can be injected
    private val client = HttpClient(CIO) {
        expectSuccess = true

        install(ContentNegotiation) {
            json()
        }

        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = "matane.app"
                path("api/")
            }
        }
    }

    suspend fun search(query: String) = client
        .get("vocabulary/search") {
            url {
                parameters.append("query", query)
            }
        }.body<List<VocabularyOverview>>()
}
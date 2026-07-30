package com.uncenx

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*
import org.jsoup.nodes.Document

class UncenXProvider : MainUrlPlugin() {
    override var mainUrl = "https://www.uncenx.com"
    override var name = "UncenX"
    override var lang = "en"
    override val hasMainPage = true
    override val supportedTypes = setOf(TvType.NSFW)

    override suspend fun getMainPage(page: Int, request: ProviderData): HomePageResponse {
        val doc = app.get("$mainUrl/en/").document
        val home = doc.select("article, div.post-item, div.video-card").mapNotNull {
            it.toSearchResult()
        }
        return newHomePageResponse(request.name, home)
    }

    private fun Document.toSearchResult(): SearchResponse? {
        val href = this.selectFirst("a")?.attr("href") ?: return null
        val title = this.selectFirst("h2, h3, .title")?.text() ?: "Unknown"
        val poster = this.selectFirst("img")?.attr("src") ?: ""

        return newMovieSearchResponse(title, href, TvType.NSFW) {
            this.posterUrl = poster
        }
    }

    override suspend fun load(url: String): LoadResponse {
        val doc = app.get(url).document
        val title = doc.selectFirst("h1.entry-title, h1")?.text() ?: "UncenX Video"
        val poster = doc.selectFirst("meta[property=og:image]")?.attr("content")
        val description = doc.selectFirst("meta[name=description]")?.attr("content")

        return newMovieLoadResponse(title, url, TvType.NSFW, url) {
            this.posterUrl = poster
            this.plot = description
        }
    }

    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        val doc = app.get(data).document
        val iframes = doc.select("iframe[src]").map { it.attr("src") }
        for (iframeUrl in iframes) {
            loadExtractor(iframeUrl, data, subtitleCallback, callback)
        }
        return true
    }
}

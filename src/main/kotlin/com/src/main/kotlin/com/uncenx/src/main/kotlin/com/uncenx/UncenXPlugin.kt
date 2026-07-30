package com.uncenx

import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.Plugin
import android.content.Context

@CloudstreamPlugin
class UncenXPlugin: Plugin() {
    override fun load(context: Context) {
        registerMainAPI(UncenXProvider())
    }
}

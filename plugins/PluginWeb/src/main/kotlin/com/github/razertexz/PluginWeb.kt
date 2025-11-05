package com.github.razertexz

import android.content.Context

import com.aliucord.annotations.AliucordPlugin
import com.aliucord.entities.Plugin
import com.aliucord.Utils

@AliucordPlugin(requiresRestart = false)
class PluginWeb : Plugin() {
    override fun start(ctx: Context) {
        Utils.openPageWithProxy(Utils.appActivity, PluginWebPage())
    }

    override fun stop(ctx: Context) {}
}

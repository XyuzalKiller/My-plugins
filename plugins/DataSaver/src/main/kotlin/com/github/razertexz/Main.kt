package com.github.razertexz

import android.content.Context

import com.aliucord.annotations.AliucordPlugin
import com.aliucord.entities.Plugin

import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.facebook.imagepipeline.request.ImageRequest
import com.discord.utilities.images.MGImages

import de.robv.android.xposed.XC_MethodHook

@AliucordPlugin(requiresRestart = false)
class Main : Plugin() {
    init {
        settingsTab = SettingsTab(PluginSettings::class.java).withArgs(settings)
    }

    override fun start(ctx: Context) {
        patcher.patch(MGImages::class.java, "getImageRequest", arrayOf(String::class.java, Int::class.java, Int::class.java, Boolean::class.java), object : XC_MethodHook() {
            override fun afterHookedMethod(param: XC_MethodHook.MethodHookParam) {
                val url = param.args[0] as String? ?: return
                if (url.contains("/avatars/") && !settings.getBool("userAvatars", false)
                    || url.contains("/banners/") && !settings.getBool("banners", false)
                    || url.contains("/icons/") && !settings.getBool("serverIcons", false)
                    || url.contains("/role-icons/") && !settings.getBool("roleIcons", false)
                ) {
                    (param.result as ImageRequestBuilder).b = ImageRequest.c.k
                }
            }
        })
    }

    override fun stop(ctx: Context) = patcher.unpatchAll()
}

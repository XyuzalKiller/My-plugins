package com.github.razertexz

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

import com.aliucord.annotations.AliucordPlugin
import com.aliucord.entities.Plugin
import com.aliucord.Utils

import com.discord.api.user.User
import com.discord.api.guildmember.GuildMember
import com.discord.api.message.embed.EmbedAuthor
import com.discord.api.guild.Guild
import com.discord.api.role.GuildRole

import de.robv.android.xposed.XC_MethodHook

@AliucordPlugin(requiresRestart = false)
internal class Main : Plugin() {
    private val cm = Utils.appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    init {
        settingsTab = SettingsTab(PluginSettings::class.java).withArgs(settings)
    }

    override fun start(ctx: Context) {
        setNull(User::class.java, "a", "userAvatars")
        setNull(GuildMember::class.java, "b", "userAvatars")
        setNull(EmbedAuthor::class.java, "b", "userAvatars")

        setNull(User::class.java, "b", "userBanners")
        setNull(GuildMember::class.java, "c", "userBanners")

        setNull(Guild::class.java, "q", "serverIcons")
        setNull(Guild::class.java, "e", "serverBanners")
        setNull(GuildRole::class.java, "d", "roleIcons")
    }

    private fun setNull(clazz: Class<*>, methodName: String, key: String) {
        patcher.patch(clazz, methodName, emptyArray(), object : XC_MethodHook() {
            override fun beforeHookedMethod(param: XC_MethodHook.MethodHookParam) {
                if (cm.getNetworkCapabilities(cm.activeNetwork)?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true && !settings.getBool(key, false)) {
                    param.result = null
                }
            }
        })
    }

    override fun stop(ctx: Context) = patcher.unpatchAll()
}
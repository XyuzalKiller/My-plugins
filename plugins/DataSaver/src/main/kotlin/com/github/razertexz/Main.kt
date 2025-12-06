package com.github.razertexz

import android.content.Context

import com.aliucord.annotations.AliucordPlugin
import com.aliucord.entities.Plugin

import com.discord.api.user.User
import com.discord.api.guildmember.GuildMember
import com.discord.api.guild.Guild
import com.discord.api.role.GuildRole

import de.robv.android.xposed.XC_MethodHook

@AliucordPlugin(requiresRestart = false)
class Main : Plugin() {
    init {
        settingsTab = SettingsTab(PluginSettings::class.java).withArgs(settings)
    }

    override fun start(ctx: Context) {
        setNull(User::class.java, "a", "userAvatars")
        setNull(GuildMember::class.java, "b", "userAvatars")
        setNull(User::class.java, "b", "userBanners")
        setNull(GuildMember::class.java, "c", "userBanners")

        setNull(Guild::class.java, "q", "serverIcons")
        setNull(Guild::class.java, "e", "serverBanners")
        setNull(GuildRole::class.java, "d", "roleIcons")
    }

    private fun setNull(clazz: Class<*>, methodName: String, key: String) {
        patcher.patch(clazz, methodName, emptyArray(), object : XC_MethodHook() {
            override fun beforeHookedMethod(param: XC_MethodHook.MethodHookParam) {
                if (!settings.getBool(key, false)) {
                    param.result = null
                }
            }
        })
    }

    override fun stop(ctx: Context) = patcher.unpatchAll()
}
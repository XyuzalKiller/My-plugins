package com.github.razertexz

import android.content.Context

import com.aliucord.annotations.AliucordPlugin
import com.aliucord.entities.Plugin
import com.aliucord.api.PatcherAPI

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
        patcher.setNull(User::class.java, "a", "userAvatars")
        patcher.setNull(User::class.java, "b", "userBanners")

        patcher.setNull(GuildMember::class.java, "b", "userAvatars")
        patcher.setNull(GuildMember::class.java, "c", "userBanners")

        patcher.setNull(Guild::class.java, "q", "serverIcons")
        patcher.setNull(Guild::class.java, "e", "serverBanners")

        patcher.setNull(GuildRole::class.java, "d", "roleIcons")
    }

    override fun stop(ctx: Context) = patcher.unpatchAll()

    private fun PatcherAPI.setNull(clazz: Class<*>, methodName: String, key: String) {
        patch(clazz, methodName, emptyArray(), object : XC_MethodHook() {
            override fun beforeHookedMethod(param: XC_MethodHook.MethodHookParam) {
                if (!settings.getBool(key, false)) {
                    param.result = null
                }
            }
        })
    }
}
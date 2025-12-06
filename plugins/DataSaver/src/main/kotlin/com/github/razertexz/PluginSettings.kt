package com.github.razertexz

import android.view.View

import com.aliucord.fragments.SettingsPage
import com.aliucord.api.SettingsAPI
import com.aliucord.Utils

import com.discord.views.CheckedSetting

internal class PluginSettings(private val settings: SettingsAPI) : SettingsPage() {
    override fun onViewBound(view: View) {
        super.onViewBound(view)

        setActionBarTitle("Data Saver")
        setActionBarSubtitle("Only applies when using mobile data")

        addCheckedSetting("User Avatars", "userAvatars")
        addCheckedSetting("User Banners", "userBanners")

        addCheckedSetting("Server Icons", "serverIcons")
        addCheckedSetting("Server Banners", "serverBanners")

        addCheckedSetting("Role Icons", "roleIcons")
    }

    private fun addCheckedSetting(hint: CharSequence, key: String) {
        addView(Utils.createCheckedSetting(getContext()!!, CheckedSetting.ViewType.SWITCH, hint, null).apply {
            isChecked = settings.getBool(key, false)
            setOnCheckedListener { settings.setBool(key, it) }
        })
    }
}
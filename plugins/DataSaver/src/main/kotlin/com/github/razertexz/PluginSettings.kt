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

        addCheckedSetting("User Avatars", null, "userAvatars", false)
        addCheckedSetting("User and Server Banners", null, "banners", false)
        addCheckedSetting("Server Icons", null, "serverIcons", false)
        addCheckedSetting("Role Icons", null, "roleIcons", false)
    }

    private fun addCheckedSetting(hint: CharSequence, description: String?, key: String, defValue: Boolean) {
        addView(Utils.createCheckedSetting(getContext()!!, CheckedSetting.ViewType.SWITCH, hint, description).apply {
            isChecked = settings.getBool(key, defValue)
            setOnCheckedListener { settings.setBool(key, it) }
        })
    }
}
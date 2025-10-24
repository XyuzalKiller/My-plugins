package com.github.razertexz

import android.content.Context

import com.aliucord.annotations.AliucordPlugin
import com.aliucord.entities.Plugin
import com.aliucord.patcher.*

import com.discord.databinding.WidgetChatListAdapterItemTextBinding

@AliucordPlugin(requiresRestart = false)
class Main : Plugin() {
    override fun start(ctx: Context) {
        patcher.patch(WidgetChatListAdapterItemTextBinding::class.java.declaredConstructors[0], Hook {
            logger.infoToast("HI!")
        })
    }

    override fun stop(ctx: Context) = patcher.unpatchAll()
}

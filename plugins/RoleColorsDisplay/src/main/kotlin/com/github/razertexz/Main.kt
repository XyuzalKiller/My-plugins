package com.github.razertexz

import android.content.Context
import android.widget.TextView
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape

import com.aliucord.annotations.AliucordPlugin
import com.aliucord.entities.Plugin
import com.aliucord.patcher.*
import com.aliucord.Utils
import com.aliucord.utils.DimenUtils

import com.discord.widgets.chat.list.adapter.WidgetChatListAdapterItemMessage
import com.discord.utilities.color.ColorCompat
import com.discord.models.member.GuildMember
import com.discord.views.UsernameView

import com.lytefast.flexinput.R

@AliucordPlugin(requiresRestart = false)
class Main : Plugin() {
    private val roleColorIconSize = DimenUtils.dpToPx(8.0f)
    private val roleColorIconPadding = DimenUtils.dpToPx(4.0f)

    override fun start(ctx: Context) {
        val itemName = WidgetChatListAdapterItemMessage::class.java.getDeclaredField("itemName").apply { isAccessible = true }

        patcher.instead<WidgetChatListAdapterItemMessage>("getAuthorTextColor", GuildMember::class.java) {
            val textView = itemName[this] as TextView?
            val guildMember = it.args[0] as GuildMember?
 
            if (textView != null && guildMember != null)
                addRoleColorIcon(textView, guildMember.color)

            ColorCompat.getThemedColor(itemView.context, R.b.colorHeaderPrimary)
        }

        patcher.instead<UsernameView>("setUsernameColor", Int::class.java) {
            if (id == Utils.getResId("channel_members_list_item_name", "id"))
                addRoleColorIcon(j.c, it.args[0] as Int)
        }
    }

    override fun stop(ctx: Context) = patcher.unpatchAll()

    private fun addRoleColorIcon(textView: TextView, roleColor: Int) {
        if (roleColor != Color.BLACK) {
            val icon = (textView.compoundDrawablesRelative[0] as ShapeDrawable?) ?: ShapeDrawable(OvalShape()).apply {
                setBounds(0, 0, roleColorIconSize, roleColorIconSize)
            }
            icon.paint.color = roleColor

            textView.setCompoundDrawablesRelative(icon, null, null, null)
            textView.compoundDrawablePadding = roleColorIconPadding
        } else if (textView.compoundDrawablesRelative[0] != null) {
            textView.setCompoundDrawablesRelative(null, null, null, null)
        }
    }
}

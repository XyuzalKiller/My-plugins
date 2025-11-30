package com.github.razertexz

import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.graphics.Color

import com.aliucord.annotations.AliucordPlugin
import com.aliucord.entities.Plugin

import com.discord.app.AppFragment

import de.robv.android.xposed.XC_MethodHook

@AliucordPlugin(requiresRestart = false)
class BetterThemer : Plugin() {
    override fun start(ctx: Context) {

        patcher.patch(AppFragment::class.java, "onViewBound", arrayOf(View::class.java), object : XC_MethodHook(10000) {
            override fun afterHookedMethod(param: XC_MethodHook.MethodHookParam) {
                applyTheme(param.args[0] as ViewGroup)
            }
        })

        patcher.patch(RecyclerView.Adapter::class.java, "onBindViewHolder", arrayOf(RecyclerView.ViewHolder::class.java, Int::class.java), object : XC_MethodHook(10000) {
            override fun afterHookedMethod(param: XC_MethodHook.MethodHookParam) {
                applyTheme((param.args[0] as RecyclerView.ViewHolder).itemView)
            }
        })
    }

    override fun stop(ctx: Context) = patcher.unpatchAll()

    private fun applyTheme(viewGroup: ViewGroup) {
        for (i in 0 until viewGroup.childCount) {
            val child = viewGroup.getChildAt(i)
            if (child is ViewGroup) {
                applyTheme(child)
            } else if (child is TextView && child.currentTextColor != Color.RED) {
                child.setTextColor(Color.RED)
            }
        }
    }
}
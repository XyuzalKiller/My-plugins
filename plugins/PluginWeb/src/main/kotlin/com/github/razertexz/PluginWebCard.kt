package com.github.razertexz

import android.content.Context
import android.widget.LinearLayout
import android.widget.GridLayout
import android.widget.TextView

import com.aliucord.Constants
import com.aliucord.views.Divider
import com.aliucord.views.Button
import com.aliucord.views.DangerButton
import com.aliucord.views.ToolbarButton
import com.aliucord.utils.DimenUtils

import com.discord.utilities.color.ColorCompat

import com.google.android.material.card.MaterialCardView
import com.lytefast.flexinput.R

class PluginWebCard(ctx: Context) : MaterialCardView(ctx) {
    private val p = DimenUtils.defaultPadding

    val titleView = TextView(ctx, null, 0, R.i.UiKit_TextView_Semibold).apply {
        textSize = 16.0f
        setBackgroundColor(ColorCompat.getThemedColor(ctx, R.b.colorBackgroundSecondaryAlt))
        setPadding(p, p, p, p)
    }

    val descriptionView = TextView(ctx, null, 0, R.i.UiKit_Settings_Item_Addition).apply {
        setPadding(p, p, p, p)
    }

    val changelogButton = ToolbarButton(ctx).apply {
        setImageDrawable(ctx.getDrawable(R.e.ic_history_white_24dp))
    }

    val installButton = Button(ctx).apply {
        text = "Install"
    }

    val uninstallButton = DangerButton(ctx).apply {
        text = "Uninstall"
    }

    init {
        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
            topMargin = p
        }

        radius = DimenUtils.defaultCardRadius.toFloat()
        setCardBackgroundColor(ColorCompat.getThemedColor(ctx, R.b.colorBackgroundSecondary))

        addView(LinearLayout(ctx, null, 0, R.i.UiKit_ViewGroup_LinearLayout).apply {
            addView(titleView)
            addView(Divider(ctx))
            addView(descriptionView)
            addView(GridLayout(ctx).apply {
                rowCount = 1
                columnCount = 5
                useDefaultMargins = true
                setPadding(p, 0, p, 0)

                addView(changelogButton, GridLayout.LayoutParams(GridLayout.spec(0, GridLayout.CENTER), GridLayout.spec(1)))
                addView(installButton, GridLayout.LayoutParams(GridLayout.spec(0, GridLayout.CENTER), GridLayout.spec(4)))
                addView(uninstallButton, GridLayout.LayoutParams(GridLayout.spec(0, GridLayout.CENTER), GridLayout.spec(4)))
            })
        })
    }
}

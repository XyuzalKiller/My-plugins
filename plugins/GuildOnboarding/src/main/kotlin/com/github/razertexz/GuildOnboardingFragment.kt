package com.github.razertexz

import android.content.Context
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.view.Gravity
import android.os.Bundle

import com.aliucord.utils.DimenUtils
import com.aliucord.views.Button

import com.discord.utilities.color.ColorCompat
import com.discord.app.AppFragment

import com.lytefast.flexinput.R

class GuildOnboardingFragment() : AppFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val ctx = getContext()!!

        return FrameLayout(ctx).apply {
            layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
            setBackgroundColor(ColorCompat.getThemedColor(ctx, R.b.colorBackgroundTertiary))

            addView(LinearLayout(ctx).apply {
                val p = DimenUtils.defaultPadding / 2

                orientation = LinearLayout.VERTICAL
                setPadding(p, p, p, p)

                addView(TextView(ctx, null, 0, R.i.UiKit_TextView_Subtext).apply {
                    text = "Question 2 of 2"
                }, LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                    topMargin = DimenUtils.dpToPx(256.0f)
                })

                addView(TextView(ctx, null, 0, R.i.UiKit_TextView_Semibold).apply {
                    text = "Self roles!"
                    textSize = 28.0f
                })

                addView(View(ctx), LinearLayout.LayoutParams(0, 0, 1.0f))

                addView(Button(ctx).apply {
                    text = "Next"
                })
            }, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))
        }
    }
}

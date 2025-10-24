package com.github.razertexz

import androidx.core.content.res.ResourcesCompat
import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button
import android.widget.ToggleButton
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.view.Gravity
import android.os.Bundle

import com.discord.app.AppFragment
import com.discord.utilities.color.ColorCompat

class GuildOnboardingFragment() : AppFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LinearLayout(getContext()!!).apply {
            val colorBackgroundTertiary = ColorCompat.getThemedColor(context, 0x7f040152)
            val colorHeaderPrimary = ColorCompat.getThemedColor(context, 0x7f04019d)
            val colorHeaderSecondary = ColorCompat.getThemedColor(context, 0x7f04019e)

            val whitneyMedium = ResourcesCompat.getFont(context, 0x7f090006)
            val whitneySemibold = ResourcesCompat.getFont(context, 0x7f090007)

            val defParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                leftMargin = context.dpToPx(4.0f)
                rightMargin = context.dpToPx(4.0f)
            }

            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(colorBackgroundTertiary)

            addView(View(context), LinearLayout.LayoutParams(0, 0, 0.8f))

            addView(TextView(context).apply {
                text = "Question 2 of 2"
                setTextColor(colorHeaderSecondary)
                typeface = whitneyMedium
            }, defParams)

            addView(TextView(context).apply {
                text = "Self roles!"
                textSize = 28.0f
                setTextColor(colorHeaderPrimary)
                typeface = whitneySemibold
            }, defParams)

            addView(ToggleButton(context).apply {
                setTextColor(colorHeaderPrimary)
                typeface = whitneySemibold
                gravity = Gravity.LEFT
            }, defParams)

            addView(ToggleButton(context).apply {
                setTextColor(colorHeaderPrimary)
                typeface = whitneySemibold
                gravity = Gravity.LEFT
            }, defParams)

            addView(View(context), LinearLayout.LayoutParams(0, 0, 1.0f))

            addView(Button(context).apply {
                text = "Next"
                setTextColor(colorHeaderPrimary)
                typeface = whitneySemibold
            }, defParams)
        }
    }

    private inline fun Context.dpToPx(dp: Float): Int = (dp * resources.displayMetrics.density + 0.5f).toInt()
}

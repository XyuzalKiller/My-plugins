package com.github.razertexz

import android.content.Context
import android.widget.FrameLayout
import android.widget.ImageView
import android.view.Gravity
import android.view.ViewGroup
import android.view.View

import com.aliucord.annotations.AliucordPlugin
import com.aliucord.entities.Plugin
import com.aliucord.patcher.*
import com.aliucord.utils.DimenUtils
import com.aliucord.Utils

import com.discord.models.message.Message
import com.discord.models.domain.NonceGenerator
import com.discord.api.utcdatetime.UtcDateTime
import com.discord.api.message.MessageTypes
import com.discord.api.message.MessageFlags
import com.discord.utilities.time.ClockFactory
import com.discord.utilities.color.ColorCompat
import com.discord.stores.StoreMessages
import com.discord.stores.StoreStream

import com.lytefast.flexinput.R
import com.lytefast.flexinput.viewmodel.FlexInputState
import com.lytefast.flexinput.fragment.FlexInputFragment
import com.lytefast.flexinput.fragment.`FlexInputFragment$d`

@AliucordPlugin(requiresRestart = false)
class Main : Plugin() {
    override fun start(ctx: Context) {
        val viewId = View.generateViewId()

        patcher.after<`FlexInputFragment$d`>("invoke", Object::class.java) {
            val fragment = receiver as FlexInputFragment

            val binding = fragment.j()
            val layout = binding.n
            val previewButton = layout.getChildById(viewId) ?: FrameLayout(layout.context).apply {
                id = viewId
                setOnClickListener {
                    val nonce = NonceGenerator.computeNonce(ClockFactory.get())
                    val message = Message(
                        nonce,
                        StoreStream.getChannelsSelected().id,
                        null,
                        Utils.buildClyde(null, null),
                        fragment.m.text.toString(),
                        UtcDateTime(ClockFactory.get().currentTimeMillis()),
                        null,
                        false,
                        false,
                        null,
                        null,
                        null,
                        null,
                        null,
                        nonce.toString(),
                        false,
                        null,
                        MessageTypes.LOCAL,
                        null,
                        null,
                        null,
                        null,
                        MessageFlags.EPHEMERAL,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        false,
                        null,
                        false,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                    )

                    StoreMessages.`access$handleLocalMessageCreate`(StoreStream.getMessages(), message)
                }

                addView(ImageView(context).apply {
                    setImageDrawable(context.getDrawable(R.e.ic_visibility_white_24dp)!!.mutate().apply {
                        setTint(ColorCompat.getThemedColor(context, R.b.colorInteractiveNormal))
                    })

                    val p = DimenUtils.defaultPadding / 2
                    setPadding(p, p, p, p)
                })

                layout.addView(this, 1, FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT).apply { gravity = Gravity.CENTER_VERTICAL })
            }

            previewButton.visibility = binding.o.visibility
        }
    }

    private fun ViewGroup.getChildById(id: Int): View? {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.id == id)
                return child
        }

        return null
    }

    override fun stop(ctx: Context) = patcher.unpatchAll()
}

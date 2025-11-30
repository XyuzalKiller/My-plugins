package com.github.razertexz

import android.content.Context

import com.aliucord.annotations.AliucordPlugin
import com.aliucord.entities.Plugin
import com.aliucord.patcher.*
import com.aliucord.Utils

import com.discord.stores.StoreStream
import com.discord.stores.StoreGatewayConnection
import com.discord.api.presence.ClientStatus
import com.discord.api.activity.Activity
import com.discord.api.activity.ActivityAssets
import com.discord.api.activity.ActivityTimestamps
import com.discord.api.activity.ActivityType

@AliucordPlugin(requiresRestart = false)
class Main : Plugin() {
    private val runnable = object : Runnable {
        override fun run() {
            val presence = StoreStream.getPresences().`getLocalPresence$app_productionGoogleRelease`()
            StoreStream.getGatewaySocket().presenceUpdate(presence.status, null, presence.activities, false)

            Utils.mainThread.postDelayed(this, 60000L)
        }
    }

    override fun start(ctx: Context) {
        val name: String = "HALF-ASS-LIFE 3"
        val details: String = "Very fun, 10/10"
        val state: String = "Dying"

        val applicationId: Long? = 1444584818028576909L
        val largeImage: String? = "pexels-vo-van-ti-n-2037497312-34949976"
        val largeImageText: String? = "Big Image"
        val smallImage: String? = null
        val smallImageText: String? = null

        patcher.before<StoreGatewayConnection>(
            "presenceUpdate",
            ClientStatus::class.java,
            Long::class.javaObjectType,
            List::class.java,
            Boolean::class.javaObjectType
        ) {
            val newActivities = ArrayList(it.args[2] as List<Activity>)
            newActivities.removeIf { it.a() == applicationId }
            newActivities += Activity(
                name,
                ActivityType.PLAYING,
                null,
                System.currentTimeMillis(),
                ActivityTimestamps(System.currentTimeMillis().toString(), null),
                applicationId,
                details,
                state,
                null,
                null,
                ActivityAssets(largeImage, largeImageText, smallImage, smallImageText),
                0,
                null,
                null,
                null,
                null,
                null,
                null
            )

            it.args[2] = newActivities
        }
        
        Utils.mainThread.postDelayed(runnable, 60000L)
    }

    override fun stop(ctx: Context) {
        Utils.mainThread.removeCallbacks(runnable)
        patcher.unpatchAll()
    }
}

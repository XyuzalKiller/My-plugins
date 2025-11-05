package com.github.razertexz

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.EditText
import android.view.ViewGroup
import android.view.View
import android.view.inputmethod.EditorInfo

import com.aliucord.PluginManager
import com.aliucord.Http
import com.aliucord.Utils
import com.aliucord.utils.ChangelogUtils
import com.aliucord.utils.MDUtils
import com.aliucord.fragments.SettingsPage

import com.discord.utilities.color.ColorCompat

import com.lytefast.flexinput.R
import com.google.gson.reflect.TypeToken
import java.io.IOException

class PluginWebPage() : SettingsPage() {
    private val pluginInfoList = object : TypeToken<List<PluginInfo>>() {}.type

    private class PluginInfo(
        val name: String,
        val description: String,
        val version: String,
        val authors: List<String>,
        val url: String,
        val repoUrl: String,
        val changelog: String?
    )

    private class Adapter(private val data: List<PluginInfo>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
        private class ViewHolder(val card: PluginWebCard) : RecyclerView.ViewHolder(card)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(PluginWebCard(parent.context)).apply {
                card.changelogButton.setOnClickListener {
                    if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                        val item = data[bindingAdapterPosition]
                        ChangelogUtils.show(it.context, "${item.name} v${item.version}", null, item.changelog!!, ChangelogUtils.FooterAction(R.e.ic_account_github_white_24dp, item.repoUrl))
                    }
                }
            }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.card.apply {
                val item = data[position]

                titleView.text = "${item.name} v${item.version} by ${item.authors[0]}"
                descriptionView.text = MDUtils.render(item.description)
                changelogButton.visibility = if (item.changelog != null) View.VISIBLE else View.GONE

                if (PluginManager.plugins.containsKey(item.name)) {
                    installButton.visibility = View.GONE
                    uninstallButton.visibility = View.VISIBLE
                } else {
                    installButton.visibility = View.VISIBLE
                    uninstallButton.visibility = View.GONE
                }
            }
        }

        override fun getItemCount(): Int = data.size
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)

        val ctx = view.context
    
        setActionBarTitle("Plugin Web")
        setActionBarSubtitle("Search for Aliucord plugins!")
        removeScrollView()

        addView(EditText(ctx, null, 0, R.i.UiKit_TextInputLayout_EditText_SingleLine).apply {
            setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_NEXT) {}
                false
            }
        })

        addView(RecyclerView(ctx).apply {
            layoutManager = LinearLayoutManager(ctx)

            Utils.threadPool.execute {
                try {
                    val plugins = Http.simpleJsonGet<List<PluginInfo>>("https://raw.githubusercontent.com/Aliucord/plugins-repo/builds/manifest.json", pluginInfoList)

                    Utils.mainThread.post {
                        adapter = Adapter(plugins)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        })
    }
}

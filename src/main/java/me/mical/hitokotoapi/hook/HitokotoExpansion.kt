package me.mical.hitokotoapi.hook

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import me.mical.hitokotoapi.HitokotoAPI
import me.mical.hitokotoapi.util.HitokotoUtil
import org.bukkit.entity.Player

@Suppress("unused")
class HitokotoExpansion : PlaceholderExpansion() {

    override fun getIdentifier(): String = "Hitokoto"

    override fun onPlaceholderRequest(p: Player, params: String): String {
        val args = params.split("_")
        if (args[0].toLowerCase() == "format") {
            val tempArgs = args.drop(1)
            val format = tempArgs[0]
            if (HitokotoAPI.formats.containsKey(format)) {
                val section = HitokotoAPI.formats[format]
                val minLength = if (section?.getConfigurationSection("Min") != null) section.getInt("Min") else 0
                val maxLength = if (section?.getConfigurationSection("Max") != null) section.getInt("Max") else 30
                return HitokotoUtil.getHitokoto(section?.getStringList("Types"), minLength, maxLength)
                    .format(section?.getString("Format") ?: "『{hitokoto}』—— {from}「{fromWho}」")
            }
        }
        return ""
    }

    override fun getAuthor(): String = "Mical"

    override fun getVersion(): String = HitokotoAPI.plugin.description.version
}
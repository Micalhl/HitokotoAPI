package me.mical.hitokotoapi.hook

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import me.mical.hitokotoapi.HitokotoAPI
import me.mical.hitokotoapi.util.HitokotoUtil
import org.bukkit.entity.Player

@Suppress("unused")
class HitokotoExpansion : PlaceholderExpansion() {

    override fun getIdentifier(): String = "Hitokoto"

    override fun onPlaceholderRequest(p: Player, params: String): String? {
        val args = params.split("_")
        if (args[0].toLowerCase() == "format") {
            val tempArgs = args.drop(1)
            val format = tempArgs[0]
            if (HitokotoAPI.formats.containsKey(format)) {
                val section = HitokotoAPI.formats[format]
                val types = if (section?.getConfigurationSection("Types") != null) {
                    if (section.getStringList("Types").contains("random")) null
                    else section.getStringList("Types")
                } else null
                val minLength = if (section?.getConfigurationSection("Min") != null) section.getInt("Min") else 0
                val maxLength = if (section?.getConfigurationSection("Max") != null) section.getInt("Max") else 30
                return HitokotoUtil.getHitokoto(types, minLength, maxLength)
                    .format(section?.getString("Format") ?: "『{hitokoto}』—— {from}「{fromWho}」")
            }
        }
        if (args[0].toLowerCase() == "onedayhitokoto") {
            val format = if (HitokotoAPI.CONFIG.getStringColored("OneDayHitokoto.Format") != "") HitokotoAPI.CONFIG.getStringColored("OneDayHitokoto.Format") else "『{hitokoto}』—— {from}「{fromWho}」"
            return HitokotoAPI.CONFIG.getString("OneDayHitokoto.Content")?.let { HitokotoUtil.getHitokoto(it).format(format) }
        }
        return ""
    }

    override fun getAuthor(): String = "Mical"

    override fun getVersion(): String = HitokotoAPI.plugin.description.version
}
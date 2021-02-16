package me.mical.hitokotoapi.feature

import me.mical.hitokotoapi.enums.HitokotoType
import org.bukkit.ChatColor
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Mical
 * @date 2021/1/16 17:59
 */
data class Hitokoto(
    val id: Int,
    val uuid: UUID,
    val hitokoto: String,
    val type: String,
    val from: String,
    val fromWho: String?,
    val creator: String,
    val creatorUID: Int,
    val reviewer: Int,
    val commitFrom: String?,
    val createdAt: Long,
    val length: Int
) {
    fun format(format: String): String {
        val type0 = HitokotoType.valueOf(type.toUpperCase())
        return ChatColor.translateAlternateColorCodes('&', format)
            .replace("{id}", id.toString())
            .replace("{uuid}", uuid.toString())
            .replace("{hitokoto}", hitokoto)
            .replace("{typeFinalLowerCase}", type0.name)
            .replace("{typeFinalUpperCase}", type0.name.toUpperCase())
            .replace("{typeName}", type0.toChinese())
            .replace("{from}", from)
            .replace("{fromWho}", if (fromWho == null) "" else " $fromWho")
            .replace("{creator}", creator)
            .replace("{creatorUID}", creatorUID.toString())
            .replace("{reviewer}", reviewer.toString())
            .replace("{createdAtFinal}", createdAt.toString())
            .replace("{createdAtDate}", SimpleDateFormat().format(Date(createdAt)))
            .replace("{length}", length.toString())
    }
}
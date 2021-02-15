package me.mical.hitokotoapi.task

import me.mical.hitokotoapi.HitokotoAPI
import me.mical.hitokotoapi.util.HitokotoUtil
import org.bukkit.scheduler.BukkitRunnable

class AutoRefresher {
    init {
        object : BukkitRunnable() {
            override fun run() {
                if ((HitokotoAPI.CONFIG.getLong("OneDayHitokoto.LastTime") + (60 * 20 * 2)) <= System.currentTimeMillis()) {
                    HitokotoAPI.CONFIG.set("OneDayHitokoto.LastTime", System.currentTimeMillis())
                    val format = if (HitokotoAPI.formats.containsKey(HitokotoAPI.CONFIG.getString("OneDayHitokoto.Format", null)))
                        HitokotoAPI.CONFIG.getString("OneDayHitokoto.Format") else null
                    if (format != null) {
                        val type = HitokotoAPI.formats[format]!!.getStringList("Types")
                        val min = HitokotoAPI.formats[format]!!.getInt("Min")
                        val max = HitokotoAPI.formats[format]!!.getInt("Max")
                        val content = HitokotoUtil.get(type, min, max)
                        HitokotoAPI.CONFIG.set("OneDayHitokoto.Content", content)
                        HitokotoAPI.CONFIG.saveToFile()
                    } else {
                        val content = HitokotoUtil.get(null, 5, 30)
                        HitokotoAPI.CONFIG.set("OneDayHitokoto.Content", content)
                        HitokotoAPI.CONFIG.saveToFile()
                    }
                }
            }

        }.runTaskTimerAsynchronously(
                HitokotoAPI.plugin,
                0L,
                86400 * 20
        )
    }
}
package me.mical.hitokotoapi.listener

import io.izzel.taboolib.module.inject.TListener
import me.mical.hitokotoapi.HitokotoAPI
import me.mical.hitokotoapi.util.HitokotoUtil
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.scheduler.BukkitRunnable

@TListener
class AsyncPlayerChatListener : Listener {
    @EventHandler
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        if (event.message == "一言")
            object : BukkitRunnable() {
                override fun run() {
                    Bukkit.broadcastMessage("&7[&9&l一言&7] &a>> &r" + HitokotoUtil.getHitokoto(null, 5, 30).format("『{hitokoto}』—— {from}「{fromWho}」"))
                }
            }.runTaskLater(HitokotoAPI.plugin, 1)
    }
}
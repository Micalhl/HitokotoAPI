package me.mical.hitokotoapi

import io.izzel.taboolib.loader.Plugin
import io.izzel.taboolib.module.config.TConfig
import io.izzel.taboolib.module.inject.TInject
import io.izzel.taboolib.module.locale.TLocale
import me.mical.hitokotoapi.hook.HitokotoExpansion
import org.bukkit.configuration.ConfigurationSection

object HitokotoAPI : Plugin() {

    @TInject("config.yml", locale = "Options.Language")
    lateinit var CONFIG: TConfig
    val formats = hashMapOf<String, ConfigurationSection>()

    override fun onEnable() {
        TLocale.sendToConsole("Load.Loading")
        init()
        TLocale.sendToConsole("Load.Loaded")
        TLocale.sendToConsole("Load.RegisterExpansion")
        HitokotoExpansion().register()
        TLocale.sendToConsole("Load.RegisteredExpansion")
    }

    override fun onDisable() {
        HitokotoExpansion().unregister()
        TLocale.sendToConsole("Disable.UnregisteredExpansion")
        TLocale.sendToConsole("Disable.Disabled")
    }

    fun init() {
        formats.clear()
        TLocale.sendToConsole("Load.Config")
        for (key in CONFIG.getConfigurationSection("Formats")!!.getKeys(false)) {
            val section = CONFIG.getConfigurationSection("Formats.$key")!!
            formats[key] = section
            TLocale.sendToConsole("Load.LoadFormat", key)
        }
    }
}
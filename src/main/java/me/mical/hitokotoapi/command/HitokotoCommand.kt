package me.mical.hitokotoapi.command

import io.izzel.taboolib.module.command.base.BaseCommand
import io.izzel.taboolib.module.command.base.BaseSubCommand
import io.izzel.taboolib.module.command.base.SubCommand
import io.izzel.taboolib.module.locale.TLocale
import me.mical.hitokotoapi.HitokotoAPI
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

@Suppress("unused")
@BaseCommand(name = "Hitokoto", description = "一言 插件命令")
class HitokotoCommand {

    @SubCommand(permission = "Hitokoto.reload", description = "重新加载插件配置文件")
    val reload = object : BaseSubCommand() {
        override fun onCommand(cs: CommandSender, command: Command, cmd: String, ct: Array<out String>) {
            HitokotoAPI.init()
            TLocale.sendTo(cs, "Reload")
        }
    }
}
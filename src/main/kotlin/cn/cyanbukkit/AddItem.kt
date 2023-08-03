package cn.cyanbukkit

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AddItem : Command(
    "addItem",
) {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        // 获取玩家背包然后添加物品 到FindClue.instance.config的"itemList"中并且保存
        if (sender is Player) {
            val allItem = sender.inventory.contents
            FindClue.instance.config.set("itemList", allItem)
            FindClue.instance.saveConfig()
        }

        return true
    }


}
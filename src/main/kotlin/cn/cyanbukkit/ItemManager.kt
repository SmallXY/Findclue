package cn.cyanbukkit

import org.bukkit.inventory.ItemStack

object ItemManager {
    fun getClue(): List<ItemStack> {
        val list = mutableListOf<ItemStack>()
        val conList = FindClue.instance.config.getList("itemList")
        if (conList == null) {
            FindClue.instance.logger.warning("config.yml中itemList为空")
            return list
        }
        conList.forEach {
            val item = it as ItemStack
            list.add(item)
        }
        return list
    }

}

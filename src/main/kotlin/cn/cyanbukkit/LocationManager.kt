package cn.cyanbukkit

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Chest
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import java.util.Timer

object LocationManager : Listener{

    val locationMap = mutableMapOf<Player, Location>()
    val particleMap = mutableMapOf<Location, Timer>()

    @EventHandler
    fun onMove(e : PlayerMoveEvent) {
        if (!locationMap.contains(e.player)) return
        // 监测是否在locationMap的location附近3格
        val player = e.player
        val location = e.to?.block?.location ?: return
        val map = locationMap[player]!!
        if (map.world == location.world) {
            if (map.x - 3 < location.x && map.x + 3 > location.x) {
                if (map.y - 3 < location.y && map.y + 3 > location.y) {
                    if (map.z - 3 < location.z && map.z + 3 > location.z) {
                        // 停止粒子
                        val timer = particleMap[map]
                        if (timer != null) {
                            timer.cancel()
                            particleMap.remove(map)
                        }
                        // 移除locationMap
                        // 删除名为"§b§l 线索指南针"的指南针物品
                        player.inventory.remove(player.inventory.first {
                            it.type == Material.COMPASS &&
                            it.itemMeta?.displayName == "§b§l 线索指南针"
                        })
                        // 在location区域放个箱子
                        location.block.type = Material.CHEST
                        val chest = location.block.state as Chest
                        ItemManager.getClue().forEach {
                            chest.inventory.addItem(it)
                        }
                        // 生成线索
                        locationMap.remove(player)
                    }
                }
            }
        }
    }

}
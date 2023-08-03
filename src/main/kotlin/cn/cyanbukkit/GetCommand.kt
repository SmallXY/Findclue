package cn.cyanbukkit

import cn.cyanbukkit.LocationManager.particleMap
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

import org.bukkit.Material
import org.bukkit.Particle

import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.CompassMeta
import java.util.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


class GetCommand : Command(
    "getClue",
) {

    fun setCompassLocation(player: Player, location: Location) {
        val compass = ItemStack(Material.COMPASS)
        val meta  = compass.itemMeta
        meta?.setDisplayName("§b§l 线索指南针")
        compass.setItemMeta(meta)
        val compassMeta = compass.itemMeta as CompassMeta
        compassMeta.isLodestoneTracked = false
        compassMeta.lodestone = location
        compass.setItemMeta(compassMeta)
        player.getInventory().addItem(compass)
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            // 根据sender的location方圆50格以内 随机的 一个location 并判定除地面以外的位置没有方块则停止随机
            val location = sender.location
            val x = location.x
            val y = location.y
            val z = location.z
            val world = location.world
            var randomX = x + (Math.random() * 50 - 25)
            var randomZ = z + (Math.random() * 50 - 25)
            var randomY = y + (Math.random() * 50 - 25)
            var randomLocation = Location(world, randomX, randomY, randomZ)
            while (randomLocation.block.type != Material.AIR) {
                randomX = x + (Math.random() * 50 - 25)
                randomZ = z + (Math.random() * 50 - 25)
                randomY = y + (Math.random() * 50 - 25)
                randomLocation = Location(world, randomX, randomY, randomZ)
            }
            // 给物品
            setCompassLocation(sender, randomLocation)
            LocationManager.locationMap[sender] = randomLocation
            createParticleCircle(randomLocation)
        }
        return true
    }


    private fun createParticleCircle(location: Location) {
        val center = location.toVector()
        val xxx = Timer()
        particleMap[location] = xxx
        xxx.schedule(
            object : TimerTask() {
                override fun run() {

                    for (i in 0..360 step 10) {
                        val x = center.x + 3 * cos(i * PI / 180)
                        val y = center.y
                        val z = center.z + 3 * sin(i * PI / 180)
                        location.world?.spawnParticle(Particle.VILLAGER_HAPPY, x, y, z, 2)
                    }
                }
            }, 0, 1000
        )
    }




}
package io.github.jaemin0299.plugin

import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.plugin.java.JavaPlugin

class Main: JavaPlugin(), Listener {
    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
    }
    @EventHandler
    private fun entityExplodeEvent(event: EntityExplodeEvent) {
        if (event.entityType == EntityType.CREEPER) {
            event.isCancelled = true
            event.location.createExplosion(0.toFloat())
        }
    }
}
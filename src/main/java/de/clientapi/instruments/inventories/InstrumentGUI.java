package de.clientapi.instruments.inventories;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class InstrumentGUI implements Listener {
    protected JavaPlugin plugin;
    protected String title;
    protected Sound[] notes;

    public InstrumentGUI(JavaPlugin plugin, String title, Sound[] notes) {
        this.plugin = plugin;
        this.title = title;
        this.notes = notes;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void open(Player player) {
        Inventory gui = Bukkit.createInventory(null, 9, title);
        for (int i = 0; i < notes.length; i++) {
            ItemStack note = new ItemStack(Material.GLASS_PANE);
            ItemMeta meta = note.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(ChatColor.YELLOW + "Note " + (i + 1));
                note.setItemMeta(meta);
            }
            gui.setItem(i, note);
        }
        player.openInventory(gui);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(title)) {
            event.setCancelled(true);
            if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.GLASS_PANE) {
                Player player = (Player) event.getWhoClicked();
                int slot = event.getRawSlot();
                if (slot < notes.length) {
                    player.playSound(player.getLocation(), notes[slot], 1.0f, 1.0f);
                }
            }
        }
    }
}
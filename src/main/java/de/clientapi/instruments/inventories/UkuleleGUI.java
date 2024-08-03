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

import java.util.Arrays;

public class UkuleleGUI extends InstrumentGUI implements Listener {
    private static final int OCTAVE_SIZE = 12;
    private static final int TOTAL_NOTES = 24; // Two octaves
    private static final float[] PITCHES = {
            0.5f, 0.53f, 0.56f, 0.6f, 0.63f, 0.67f, 0.7f, 0.75f, 0.8f, 0.85f, 0.9f, 1.0f,
            1.05f, 1.1f, 1.15f, 1.2f, 1.25f, 1.3f, 1.35f, 1.4f, 1.45f, 1.5f, 1.55f, 1.6f
    };
    private static final Sound[] SOUNDS = new Sound[TOTAL_NOTES];
    private static final String[] NOTE_NAMES = {
            "F#", "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F",
            "F#", "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F"
    };

    private static final Material[] GLASS_PANES = {
            Material.ORANGE_STAINED_GLASS_PANE,  // F#
            Material.YELLOW_STAINED_GLASS_PANE,  // G
            Material.ORANGE_STAINED_GLASS_PANE,  // G#
            Material.YELLOW_STAINED_GLASS_PANE,  // A
            Material.ORANGE_STAINED_GLASS_PANE,  // A#
            Material.YELLOW_STAINED_GLASS_PANE,  // B
            Material.YELLOW_STAINED_GLASS_PANE,  // C
            Material.ORANGE_STAINED_GLASS_PANE,  // C#
            Material.YELLOW_STAINED_GLASS_PANE,  // D
            Material.ORANGE_STAINED_GLASS_PANE,  // D#
            Material.YELLOW_STAINED_GLASS_PANE,  // E
            Material.YELLOW_STAINED_GLASS_PANE   // F
    };

    private static final Material[] GLASS_BLOCKS = Arrays.copyOf(GLASS_PANES, GLASS_PANES.length);

    static {
        Arrays.fill(SOUNDS, Sound.BLOCK_NOTE_BLOCK_GUITAR);
    }

    public UkuleleGUI(JavaPlugin plugin) {
        super(plugin, ChatColor.YELLOW + "Ukulele", SOUNDS);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void open(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, title);
        for (int i = 0; i < TOTAL_NOTES; i++) {
            ItemStack note;
            Material mat = i < OCTAVE_SIZE ? GLASS_PANES[i] : GLASS_BLOCKS[i - OCTAVE_SIZE];
            note = new ItemStack(mat);
            ItemMeta meta = note.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(ChatColor.YELLOW + NOTE_NAMES[i]);
                note.setItemMeta(meta);
            }
            gui.setItem(i, note);
        }
        player.openInventory(gui);
    }

    @Override
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(title)) {
            event.setCancelled(true);
            ItemStack currentItem = event.getCurrentItem();
            if (currentItem != null && currentItem.getType() != Material.AIR) {
                Material type = currentItem.getType();
                Player player = (Player) event.getWhoClicked();
                int slot = event.getRawSlot();
                if ((Arrays.asList(GLASS_PANES).contains(type) || Arrays.asList(GLASS_BLOCKS).contains(type)) && slot >= 0 && slot < TOTAL_NOTES) {
                    float pitch = PITCHES[slot];
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, 1.0f, pitch);
                }
            }
        }
    }
}

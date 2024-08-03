package de.clientapi.instruments.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class InstrumentCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /instrument <name>");
            return true;
        }

        Player player = (Player) sender;
        String instrumentName = args[0].toLowerCase();

        ItemStack instrument = createInstrument(instrumentName);
        if (instrument == null) {
            player.sendMessage(ChatColor.RED + "Invalid instrument name.");
            return true;
        }

        player.getInventory().addItem(instrument);
        player.sendMessage(ChatColor.GREEN + "You have been given a " + instrumentName + ".");
        return true;
    }

    private ItemStack createInstrument(String name) {
        Material material = Material.STICK;
        String displayName;
        switch (name) {
            case "benjo":
                displayName = ChatColor.YELLOW + "Benjo";
                break;
            case "trumpet":
                displayName = ChatColor.GOLD + "Trumpet";
                break;
            case "flute":
                displayName = ChatColor.YELLOW + "Flute";
                break;
            case "frenchhorn":
                displayName = ChatColor.YELLOW + "French Horn";
                break;
            case "ukulele":
                displayName = ChatColor.YELLOW + "Ukulele";
                break;
            default:
                return null;
        }

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(displayName);
            meta.setLore(Arrays.asList(ChatColor.DARK_GRAY + "Instrument"));
            item.setItemMeta(meta);
        }
        return item;
    }
}
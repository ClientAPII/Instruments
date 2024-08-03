package de.clientapi.instruments;

import de.clientapi.instruments.commands.InstrumentCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Instruments extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("instrument").setExecutor(new InstrumentCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
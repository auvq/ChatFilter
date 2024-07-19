package me.auvq.chatfilter;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.annotations.LiteCommandsAnnotations;
import dev.rollczi.litecommands.bukkit.LiteCommandsBukkit;
import lombok.Getter;
import me.auvq.chatfilter.command.Command;
import me.auvq.chatfilter.listeners.ChatListener;
import me.auvq.chatfilter.utils.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    private LiteCommands<CommandSender> liteCommands;


    @Override
    public void onEnable() {
        instance = this;

        liteCommands = LiteCommandsBukkit.builder()
                .commands(new Command())
                .build();

        getServer().getPluginManager().registerEvents(new ChatListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        getServer().getConsoleSender().sendMessage(CC.color("&aThe config setup correctly!"));
    }
}

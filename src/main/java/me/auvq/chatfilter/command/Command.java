package me.auvq.chatfilter.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import me.auvq.chatfilter.Main;
import me.auvq.chatfilter.utils.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@dev.rollczi.litecommands.annotations.command.Command(name = "chatfilter")
public class Command {

    private Main plugin = Main.getInstance();

    @Execute(name = "help")
    public void help(@Context CommandSender sender){
        sender.sendMessage(CC.color("&7&m-----------------------------"));
        sender.sendMessage("§6ChatFilter Help");
        sender.sendMessage(CC.color("&7/chatfilter help &8- &fDisplays this message"));
        sender.sendMessage(CC.color("&7/chatfilter add %word% &8- &fAdds a word to the filter"));
        sender.sendMessage(CC.color("&7/chatfilter remove %word% &8- &fRemoves a word from the filter"));
        sender.sendMessage(CC.color("&7/chatfilter reload &8- &fReloads the config"));
        sender.sendMessage(CC.color("&7&m-----------------------------"));
    }

    @Execute(name = "add")
    @Permission("chatfilter.admin")
    public void add(@Context CommandSender sender, @Arg String word){
        if(!(sender instanceof Player)){
            sender.sendMessage(CC.color("&cYou must be a player to execute this command!"));
            return;
        }

        List<String> filteredWords = new ArrayList<>(plugin.getConfig().getStringList("filtered-words"));
        if(filteredWords.contains(word)){
            sender.sendMessage(CC.color("&cThis word is already in the filter!"));
            return;
        }

        filteredWords.add(word);
        plugin.getConfig().set("filtered-words", filteredWords);
        plugin.saveConfig();
        sender.sendMessage(CC.color("&aSuccessfully added the word to the filter!"));
    }

    @Execute(name = "remove")
    @Permission("chatfilter.admin")
    public void remove(@Context CommandSender sender, @Arg String word){
        if(!(sender instanceof Player)){
            sender.sendMessage(CC.color("&cYou must be a player to execute this command!"));
            return;
        }

        List<String> filteredWords = new ArrayList<>(plugin.getConfig().getStringList("filtered-words"));
        if(!filteredWords.contains(word)){
            sender.sendMessage(CC.color("&cThis word is not in the filter!"));
            return;
        }

        filteredWords.remove(word);
        plugin.getConfig().set("filtered-words", filteredWords);
        plugin.saveConfig();
        sender.sendMessage(CC.color("&aSuccessfully removed the word from the filter!"));
    }

    @Execute(name = "reload")
    @Permission("chatfilter.admin")
    public void reload(@Context CommandSender sender){
        if(!(sender instanceof Player)){
            sender.sendMessage(CC.color("&cYou must be a player to execute this command!"));
            return;
        }

        plugin.reloadConfig();
        sender.sendMessage(CC.color("&aSuccessfully reloaded the config!"));
    }
}

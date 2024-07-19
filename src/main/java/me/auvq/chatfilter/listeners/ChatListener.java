package me.auvq.chatfilter.listeners;

import me.auvq.chatfilter.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.concurrent.atomic.AtomicReference;

public class ChatListener implements Listener {

    private Main plugin = Main.getInstance();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();

        event.setMessage(filterMessage(message));
    }

    public String filterMessage(String message){
        AtomicReference<String> finalMessage = new AtomicReference<>(message);
        plugin.getConfig().getStringList("filtered-words")
                .stream()
                .filter(word -> finalMessage.get().contains(word) || areStringsSimilar(finalMessage.get(), word, 3))
                .forEach(word ->
                        finalMessage.set(finalMessage.get().replace(word, "*".repeat(word.length())))
                );

        return finalMessage.get();
    }

    private boolean areStringsSimilar(String str1, String str2, int threshold) {
        int currentIndex = 0;
        int matchCount = 0;

        for (int i = 0; i < str1.length(); i++) {
            int foundIndex = str2.indexOf(str1.charAt(i), currentIndex);
            if (foundIndex != -1) {
                matchCount++;
                currentIndex = foundIndex + 1;
            }
        }

        return matchCount >= threshold;
    }
}
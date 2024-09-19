package tt.Guayando.utils;

import tt.Guayando.ToolTracker;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import tt.Guayando.listeners.LoreUpdater;

import java.util.List;

public class MessageUtils {

    public static String getColoredMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String applyPAPIAndColorMessage(Player player, String message) {
        return ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, message));
    }

    public static String applyPlaceholdersAndColor(Player player, String message, ToolTracker plugin, boolean PlaceholderAPIWorks) {
        if (player != null) {
            String playerName = player.getName();
        }

        String currentDurability = String.valueOf(LoreUpdater.getCurrentDurability());
        String maxDurability = String.valueOf(LoreUpdater.getMaxDurability());

        // Reemplazar los placeholders
        message = message
                .replaceAll("%plugin%", ToolTracker.prefix)
                .replaceAll("%version%", plugin.getVersion())
                .replaceAll("%latestversion%", plugin.getLatestVersion())
                .replaceAll("%link%", "https://www.spigotmc.org/resources/119680/")
                .replaceAll("%author%", plugin.getDescription().getAuthors().toString())

                .replaceAll("%currentDurability%", currentDurability)
                .replaceAll("%maxDurability%", maxDurability)
        ;

        if (PlaceholderAPIWorks) {
            // Aplicar color y placeholders con PlaceholderAPI
            return getColoredMessage(PlaceholderAPI.setPlaceholders(player, message));
        } else {
            // Aplicar color y placeholders
            return getColoredMessage(message);
        }
    }

    // String messagePath = "bank.bal.playerBalance";
    // MessageUtils.sendMessageWithPlaceholdersAndColor(player, messagePath, plugin);
    public static void sendMessageWithPlaceholdersAndColor(Player player, String messagePath, ToolTracker plugin, boolean PlaceholderAPIWorks) {
        String message = plugin.getLanguageManager().getMessage(messagePath);
        if (player != null) {
            message = applyPlaceholdersAndColor(player, message, plugin, PlaceholderAPIWorks);
            player.sendMessage(message);
        }
    }

    // String messagePath = "bank.bal.playerBalance";
    // MessageUtils.sendMessageListWithPlaceholdersAndColor(player, messagePath, plugin);
    public static void sendMessageListWithPlaceholdersAndColor(Player player, String messagePath, ToolTracker plugin, boolean PlaceholderAPIWorks) {
        List<String> messages = plugin.getLanguageManager().getStringList(messagePath);
        if (player != null) {
            for (String msg : messages) {
                String processedMessage = applyPlaceholdersAndColor(player, msg, plugin, PlaceholderAPIWorks);
                player.sendMessage(processedMessage);
            }
        }
    }
}
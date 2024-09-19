package tt.Guayando.utils;

import tt.Guayando.ToolTracker;
import tt.Guayando.managers.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UpdateChecker implements Listener{
    private final ToolTracker plugin;
    private final LanguageManager languageManager;
    public UpdateChecker(ToolTracker plugin){
        this.plugin = plugin;
        this.languageManager = plugin.getLanguageManager();
    }
    @EventHandler
    public void CheckUpdate(PlayerJoinEvent event) {
        try{
            Player player = event.getPlayer();
            boolean updateChecker = plugin.getConfig().getBoolean("config.update-checker");
            boolean isOutdatedVersion = !(plugin.getVersion().equals(plugin.getLatestVersion()));
            boolean updateCheckerWork = plugin.getUpdateCheckerWork();

            // Verificar si el mensaje de actualización debe enviarse
            if (updateChecker && isOutdatedVersion) {
                if (player.isOp() || player.hasPermission("tooltracker.updatechecker") || player.hasPermission("tooltracker.admin")) {
                    languageManager.reloadLanguage(); // Recargar el archivo de idioma
                    if(!updateCheckerWork){
                        plugin.comprobarActualizaciones();
                    }
                    String messagePath = "config.update-checker";
                    MessageUtils.sendMessageWithPlaceholdersAndColor(player, messagePath , plugin, ToolTracker.getPlaceholderAPI());
                }
            }
        }catch (NullPointerException e){
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
            e.printStackTrace();
        }
    }
}
/*package tt.Guayando.api;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

import tt.Guayando.ToolTracker;

public class PlaceholderAPIToolTracker extends PlaceholderExpansion {

    // We get an instance of the plugin later.
    private final ToolTracker plugin;

    public PlaceholderAPIToolTracker(ToolTracker plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean persist(){
        return true;
    }
    @Override
    public boolean canRegister(){
        return true;
    }
    @Override
    public String getAuthor(){
        return "Guayando";
    }
    @Override
    public String getIdentifier() {
        return "tooltracker"; // %tooltracker_XXXXX%
    }
    @Override
    public String getVersion(){
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        plugin.getLanguageManager().reloadLanguage();
        plugin.reloadConfig();

        if (player == null) {
            return "";
        }

        String playerName = player.getName();

// ----------------------------------------------------- USER ----------------------------------------------------- //

        // %tooltracker_player_name%
        if (identifier.equals("player_name")) {
            return playerName; // Retorna el nombre del jugador actual
        }

// --------------------------------------------------- -------- --------------------------------------------------- //
        return null;
    }
}*/
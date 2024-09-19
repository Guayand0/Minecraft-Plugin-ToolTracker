package tt.Guayando.commands;

import tt.Guayando.ToolTracker;
import tt.Guayando.managers.*;
import tt.Guayando.utils.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ComandoPrincipal implements CommandExecutor {

    private final ToolTracker plugin;
    private final LanguageManager languageManager;

    public ComandoPrincipal(ToolTracker plugin) {
        this.plugin = plugin;
        this.languageManager = plugin.getLanguageManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        languageManager.reloadLanguage();
        plugin.reloadConfig();

        Player player = (Player) sender;

        if (!(player instanceof Player)) {
            // Consola
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    subCommandReload(player);
                    return true;
                }
                consoleError(player);
                return true;
            }
            consoleError(player);
            return true;
        }

        if (!player.hasPermission("tooltracker.admin")) {
            noPerm(player);
            return true;
        }

        // /tooltracker args[0] args[1] args[2]
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                // tooltracker reload
                subCommandReload(player);
            } else if (args[0].equalsIgnoreCase("help")) {
                // tooltracker help
                help(player);
            } else if (args[0].equalsIgnoreCase("version")) {
                // tooltracker version
                subCommandVersion(player);
            } else if (args[0].equalsIgnoreCase("author")) {
                // tooltracker author
                subCommandAutor(player);
            }else if (args[0].equalsIgnoreCase("plugin")) {
                // tooltracker plugin
                subCommandPlugin(player);
            } else if (args[0].equalsIgnoreCase("permissions")) {
                // tooltracker permissions
                subCommandPermissions(player);
            } else {
                noArg(player); // tooltracker qwewe
            }
        } else {
            noArg(player); // tooltracker
        }
        return true;
    }

    public void help(Player player) {
        String messagePath = "messages.help";
        MessageUtils.sendMessageListWithPlaceholdersAndColor(player, messagePath , plugin, ToolTracker.getPlaceholderAPI());
    }

    public void subCommandReload(Player player) {
        languageManager.reloadLanguage();
        plugin.reloadConfig();

        String messagePath = "messages.reload";
        MessageUtils.sendMessageWithPlaceholdersAndColor(player, messagePath , plugin, ToolTracker.getPlaceholderAPI());
    }

    public void subCommandVersion(Player player) {
        String messagePath = "messages.version";
        MessageUtils.sendMessageWithPlaceholdersAndColor(player, messagePath , plugin, ToolTracker.getPlaceholderAPI());
    }

    public void subCommandAutor(Player player) {
        String messagePath = "messages.author";
        MessageUtils.sendMessageWithPlaceholdersAndColor(player, messagePath, plugin, ToolTracker.getPlaceholderAPI());
    }

    public void subCommandPlugin(Player player){
        player.sendMessage(MessageUtils.applyPlaceholdersAndColor(player, "%plugin% &7%link%", plugin, ToolTracker.getPlaceholderAPI()));
    }

    public void subCommandPermissions(Player player){
        String messagePath = "messages.permissions";
        MessageUtils.sendMessageListWithPlaceholdersAndColor(player, messagePath , plugin, ToolTracker.getPlaceholderAPI());
    }

    public void noPerm(Player player){
        String messagePath = "messages.no-perm";
        MessageUtils.sendMessageWithPlaceholdersAndColor(player, messagePath , plugin, ToolTracker.getPlaceholderAPI());
    }

    public void noArg(Player player){
        String messagePath = "messages.command-no-argument";
        MessageUtils.sendMessageWithPlaceholdersAndColor(player, messagePath , plugin, ToolTracker.getPlaceholderAPI());
    }

    public void consoleError(Player player){
        String messagePath = "messages.console-error";
        MessageUtils.sendMessageWithPlaceholdersAndColor(player, messagePath , plugin, ToolTracker.getPlaceholderAPI());
    }
}
package tt.Guayando.listeners;

import org.bukkit.ChatColor;
import tt.Guayando.ToolTracker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import tt.Guayando.utils.MessageUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoreUpdater implements Listener {

    private final ToolTracker plugin;
    private Set<String> trackableItems;
    static int maxDurability;
    static int currentDurability;

    public LoreUpdater(ToolTracker plugin) {
        this.plugin = plugin;
        loadConfigItems();
        startDurabilityUpdater(); // Iniciar la tarea que actualiza la durabilidad cada segundo
    }

    // Cargar los ítems rastreables desde la configuración
    private void loadConfigItems() {
        trackableItems = new HashSet<>(plugin.getConfig().getStringList("tool-track"));
    }

    // Método que inicia una tarea repetitiva para actualizar la durabilidad
    private void startDurabilityUpdater() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Bukkit.getOnlinePlayers().isEmpty()) {
                    return; // No hacer nada si no hay jugadores conectados
                }

                // Recorremos todos los jugadores conectados
                for (Player player : Bukkit.getOnlinePlayers()) {
                    // Recorremos el inventario del jugador
                    for (ItemStack item : player.getInventory().getContents()) {
                        if (item != null && isTrackable(item)) {
                            updateItemLore(player, item); // Actualizar el lore del ítem
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 20L); // Se ejecuta cada 20 ticks (1 segundo)
    }

    // Verifica si el ítem es rastreable
    private boolean isTrackable(ItemStack item) {
        // Verificar si el ítem está en la lista de seguimiento y tiene durabilidad
        return trackableItems.contains(item.getType().name().toLowerCase()) && item.getType().getMaxDurability() > 0;
    }

    // Actualiza el lore del ítem con la durabilidad actual y máxima
    private void updateItemLore(Player player, ItemStack item) {
        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();

        maxDurability = item.getType().getMaxDurability();
        currentDurability = maxDurability - item.getDurability();

        // Obtener el mensaje de durabilidad desde el archivo de configuración
        String message = plugin.getLanguageManager().getMessage("tool-tracker.durability");
        String durabilityText = MessageUtils.applyPlaceholdersAndColor(player, message, plugin, ToolTracker.getPlaceholderAPI());

        // Extraer la primera palabra del mensaje para la comparación
        String firstWordOfMessage = durabilityText.split(" ")[0];

        // Buscar y actualizar la línea de durabilidad si ya existe
        boolean foundDurabilityLine = false;
        for (int i = 0; i < lore.size(); i++) {
            if (lore.get(i).startsWith(firstWordOfMessage)) {
                lore.set(i, durabilityText); // Reemplazar la línea existente
                foundDurabilityLine = true;
                break;
            }
        }

        // Si no se encontró una línea de durabilidad, añadirla
        if (!foundDurabilityLine) {
            lore.add(durabilityText);
        }

        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public static int getMaxDurability() {
        return maxDurability;
    }
    public static int getCurrentDurability() {
        return currentDurability;
    }
}
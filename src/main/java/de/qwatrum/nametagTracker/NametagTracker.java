package de.qwatrum.nametagTracker;

import io.papermc.paper.event.player.PlayerNameEntityEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public final class NametagTracker extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            if (label.equalsIgnoreCase("overview") || label.equalsIgnoreCase("ov") || label.equalsIgnoreCase("nametagtracker:overview") || label.equalsIgnoreCase("nametagtracker:ov")) {
                if (args.length == 0) {
                    sender.sendMessage(buildOverviewMessage((Player) sender));

                } else if (args.length == 2) {
                    if (Objects.equals(args[0], "remove")) {
                        String id = args[1];

                        FileConfiguration config = getConfig();
                        String playerKey = "players." + Objects.requireNonNull(((Player) sender).getPlayer()).getUniqueId() + ".entities";

                        if (config.contains(playerKey + "." + id)) {
                            config.set(playerKey + "." + id, null);
                            saveConfig();
                            Component start = buildNttPart();
                            Component message = Component.text("Entity removed");
                            sender.sendMessage(start.append(message));
                        }
                    }
                }

            } else if (label.equalsIgnoreCase("about") || label.equalsIgnoreCase("nametagtracker:about")) {

                Component start = buildNttPart();
                Component message = Component.text("NametagTracker -  © 2025 Qwatrum").color(NamedTextColor.WHITE);
                Component part1 = Component.text("\nHow does it work:\n- Name an entity with a nametag.\n- Use /overview to open an overview\n- Hover over the names and click to remove them\n\nContact: ");
                Component part2 = Component.text("GitHub repository").color(NamedTextColor.AQUA).clickEvent(ClickEvent.openUrl("https://github.com/qwatrum/nametagtracker"));

                message = message.append(part1);
                message = message.append(part2);
                sender.sendMessage(start.append(message));

            }
        }

        return true;
    }


    @EventHandler
    public void onPlayerNameEntity(PlayerNameEntityEvent event) {
        Entity entity = event.getEntity();
        Player player = event.getPlayer();

        UUID entityUUID = entity.getUniqueId();
        String title = event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName();

        FileConfiguration config = getConfig();
        String playerKey = "players." + player.getUniqueId() + ".entities";

        config.set(playerKey + "." + entityUUID, title);
        saveConfig();
    }


    public Component buildOverviewMessage(Player player) {

        Component start = buildNttPart();

        Component message = Component.text("NametagTracker - Overview:\n")
                .color(NamedTextColor.WHITE);

        FileConfiguration config = getConfig();
        String playerKey = "players." + player.getUniqueId() + ".entities";

        Map<String, Object> entities = config.getConfigurationSection(playerKey) != null ? config.getConfigurationSection(playerKey).getValues(false) : new HashMap<>();
        if (entities.isEmpty()) {

            message = Component.text("NametagTracker - Overview:\nNo named entities.");
            return start.append(message);
        } else {

            for (Map.Entry<String, Object> entry : entities.entrySet()) {
                String id = entry.getKey();
                String name = (String) entry.getValue();

                Component part = Component.text("\n- "+name)
                        .color(NamedTextColor.YELLOW)
                        .clickEvent(ClickEvent.suggestCommand("/ov remove "+id))
                        .hoverEvent(HoverEvent.showText(Component.text(getInfo(UUID.fromString(id)))));

                message = message.append(part);

            }
        }

        return start.append(message);
    }

    public String getInfo(UUID id) {
        Entity entity = getEntityByUniqueId(id);
        if (entity == null) {
            return "Entity not found. Entity may be dead or in unloaded chunks.\n(click to remove)";
        }

        double x = Math.round(entity.getX());
        double y = Math.round(entity.getY());
        double z = Math.round(entity.getZ());

        String position = x + " " + y + " " + z;
        LivingEntity livingEntity = (LivingEntity) entity;
        double health = livingEntity.getHealth();
        String dimension = entity.getWorld().getEnvironment().toString();

        if (dimension.equalsIgnoreCase("THE_END")) {
            dimension = "The End";
        } else if (dimension.equalsIgnoreCase("NETHER")) {
            dimension = "Nether";
        } else if (dimension.equalsIgnoreCase("NORMAL")) {
            dimension = "Overworld";
        }

        String type = livingEntity.getType().toString();

        type = WordUtils.capitalizeFully(type);

        return "Type: " + type + "\n" + "Position: " + position + "\n" + "Dimension: " + dimension + "\n"+"Health: " + health+"\nUUID: "+id + "\n(click to remove)";
    }

    public Entity getEntityByUniqueId(UUID uniqueId){
        for (World world : Bukkit.getWorlds()) {
            for (Chunk chunk : world.getLoadedChunks()) {
                for (Entity entity : chunk.getEntities()) {
                    if (entity.getUniqueId().equals(uniqueId)) {
                        return entity;
                    }
                }
            }
        }

        return null;
    }

    public Component buildNttPart() {
        Component start = Component.text("[");
        Component part1 = Component.text("NTT").color(NamedTextColor.AQUA);
        Component end = Component.text("] ").color(NamedTextColor.WHITE);

        Component ntt;
        ntt = start.append(part1);
        ntt = ntt.append(end);

        return ntt;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

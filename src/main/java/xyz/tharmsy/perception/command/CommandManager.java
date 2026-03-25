package xyz.tharmsy.perception.command;

import xyz.tharmsy.perception.event.EventTarget;
import xyz.tharmsy.perception.event.impl.EventChat;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    public static final String PREFIX = ".";
    private final List<Command> commands = new ArrayList<>();

    public CommandManager() {
        // typeshit commands here
    }

    @EventTarget
    public void onChat(EventChat event) {
        String message = event.getMessage();
        if (!message.startsWith(PREFIX))
            return;

        event.setCancelled(true);

        message = message.substring(PREFIX.length());
        String[] parts = message.split(" ");
        String commandName = parts[0];

        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, parts.length - 1);

        for (Command cmd : commands) {
            if (cmd.getName().equalsIgnoreCase(commandName)) {
                cmd.execute(args);
                return;
            }
            for (String alias : cmd.getAliases()) {
                if (alias.equalsIgnoreCase(commandName)) {
                    cmd.execute(args);
                    return;
                }
            }
        }

        printMessage("Unknown command: " + commandName + ".");
    }

    public void printMessage(String msg) {
        if (Minecraft.getMinecraft().thePlayer != null) {
            Minecraft.getMinecraft().thePlayer
                    .addChatMessage(new ChatComponentText("\u00A78[\u00A7bPerception\u00A78] \u00A77" + msg));
        }
    }
}

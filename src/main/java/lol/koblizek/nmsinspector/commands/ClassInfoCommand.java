package lol.koblizek.nmsinspector.commands;

import lol.koblizek.nmsinspector.util.ClassInfoBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ClassInfoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) return false;

        try {
            Class<?> type = Class.forName(args[0]);
            
            Component classInfo = Component.empty()
                    .append(ClassInfoBuilder.getSummary(type))
                    .appendNewline();
            sender.sendMessage(classInfo);
        } catch (ClassNotFoundException e) {
            sender.sendMessage(Component.text("Could not locate class " + args[0])
                    .color(NamedTextColor.RED));
        }

        return true;
    }
}

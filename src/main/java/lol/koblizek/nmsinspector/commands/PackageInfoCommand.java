package lol.koblizek.nmsinspector.commands;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import lol.koblizek.nmsinspector.util.ClassInfoBuilder;
import lol.koblizek.nmsinspector.util.NamedColors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;

public class PackageInfoCommand implements CommandExecutor {
    
    private Package findPackage(String name) {
        return Arrays.stream(Package.getPackages()).filter(p -> p.getName().equals(name)).findFirst().orElse(null);
    }
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 1) return false;
        String pckg = args[0];
        Package p = findPackage(pckg);

        if (p == null) {
            sender.sendMessage(Component.text("No such package " + pckg).color(NamedTextColor.RED));
            return true;
        }

        var component = Component.empty()
                .append(ClassInfoBuilder.getAnnotations(p))
                .appendNewline()
                .append(Component.text("package ").color(NamedColors.KEYWORDS))
                .append(Component.text(p.getName()).color(NamedColors.SYMBOLS))
                .append(Component.text(";").color(NamedColors.UNUSED))
                .appendNewline();

        try {
            ImmutableSet<ClassPath.ClassInfo> types = ClassPath.from(getClass().getClassLoader()).getTopLevelClasses(p.getName());
            component = component.append(Component.join(JoinConfiguration.separator(Component.newline()), types.stream().map(t -> {
                return Component.empty()
                        .append(Component.text("class").color(NamedColors.KEYWORDS))
                        .appendSpace()
                        .append(Component.text(t.getName())
                        .color(NamedColors.SYMBOLS)
                        .decorate(TextDecoration.UNDERLINED)
                        .hoverEvent(HoverEvent.showText(Component.text("Click to view class info").color(NamedTextColor.GRAY)))
                        .clickEvent(ClickEvent.runCommand("/classinfo " + t.getName())))
                        .append(Component.text(";").color(NamedColors.UNUSED));
            }).toList()));
        } catch (IOException e) {
            sender.sendMessage(Component.text("Failed to read package data: " + e.getMessage()).color(NamedTextColor.RED));
            return true;
        }

        sender.sendMessage(component);

        return true;
    }
}

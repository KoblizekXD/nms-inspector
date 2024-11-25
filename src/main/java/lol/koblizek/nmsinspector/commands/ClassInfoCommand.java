package lol.koblizek.nmsinspector.commands;

import lol.koblizek.nmsinspector.util.ClassInfoBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ClassInfoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) return false;
        
        Filter filter = Filter.NONE;
        if (args.length == 2)
            filter = Filter.getValue(args[1]).orElse(Filter.NONE);
        
        try {
            Class<?> type = Class.forName(args[0]);
            
            Component classInfo = Component.empty()
                    .append(ClassInfoBuilder.getSummary(type, filter))
                    .appendNewline();
            sender.sendMessage(classInfo);
        } catch (ClassNotFoundException e) {
            sender.sendMessage(Component.text("Could not locate class " + args[0])
                    .color(NamedTextColor.RED));
        }

        return true;
    }
    
    public enum Filter {
        ALL("*"),
        FIELDS("fields"),
        METHODS("methods"),
        CONSTRUCTORS("constructors"),
        NONE("none");
        
        private final String str;

        Filter(String filter) {
            this.str = filter;
        }

        @Override
        public String toString() {
            return str;
        }
        
        public static Optional<Filter> getValue(String filter) {
            for (Filter f : values()) {
                if (f.str.equals(filter))
                    return Optional.of(f);
            }
            return Optional.empty();
        }
    }
}

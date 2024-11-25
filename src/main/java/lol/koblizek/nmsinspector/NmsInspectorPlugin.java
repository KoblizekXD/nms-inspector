package lol.koblizek.nmsinspector;

import lol.koblizek.nmsinspector.commands.ClassInfoCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class NmsInspectorPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getCommand("classinfo").setExecutor(new ClassInfoCommand());
    }
}

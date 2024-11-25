package lol.koblizek.nmsinspector;

import lol.koblizek.nmsinspector.commands.ClassInfoCommand;
import lol.koblizek.nmsinspector.commands.PackageInfoCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class NmsInspectorPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getCommand("classinfo").setExecutor(new ClassInfoCommand());
        getCommand("packageinfo").setExecutor(new PackageInfoCommand());
    }
}

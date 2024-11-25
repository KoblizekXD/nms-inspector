package lol.koblizek.nmsinspector;

import lol.koblizek.nmsinspector.commands.ClassDataCommand;
import lol.koblizek.nmsinspector.commands.ClassInfoCommand;
import lol.koblizek.nmsinspector.commands.PackageInfoCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class NmsInspectorPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getCommand("classinfo").setExecutor(new ClassInfoCommand());
        getCommand("classdata").setExecutor(new ClassDataCommand(this));
        getCommand("packageinfo").setExecutor(new PackageInfoCommand());
    }
}

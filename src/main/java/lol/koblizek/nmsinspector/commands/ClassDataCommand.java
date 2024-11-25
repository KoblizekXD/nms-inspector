package lol.koblizek.nmsinspector.commands;

import lol.koblizek.nmsinspector.NmsInspectorPlugin;
import lol.koblizek.nmsinspector.util.ComponentHighlighter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.java.decompiler.main.Fernflower;
import org.jetbrains.java.decompiler.main.extern.IContextSource;
import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger;
import org.jetbrains.java.decompiler.main.extern.IFernflowerPreferences;
import org.jetbrains.java.decompiler.main.extern.IResultSaver;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.jar.Manifest;

public class ClassDataCommand implements CommandExecutor {

    private final NmsInspectorPlugin plugin;

    public ClassDataCommand(NmsInspectorPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) return false;
        
        Bukkit.getAsyncScheduler().runNow(plugin, task -> {
            var saver = new ComponentResultSaver();
            Fernflower fernflower = new Fernflower(saver, IFernflowerPreferences.getDefaults(), IFernflowerLogger.NO_OP);
            fernflower.addSource(new InlineContextSource(args[0]));
            fernflower.decompileContext();
            sender.sendMessage(ComponentHighlighter.highlight(saver.getComponent()));
        });
        
        return true;
    }
    
    static class ComponentWrapper {
        
        private Component component = Component.empty();

        public Component getComponent() {
            return component;
        }

        public void setComponent(Component component) {
            this.component = component;
        }
    }
    
    static class ComponentResultSaver implements IResultSaver {
        
        private ComponentWrapper component;
        
        public ComponentResultSaver() {
            this.component = new ComponentWrapper();
        }
        
        @Override
        public void saveFolder(String path) {
            
        }

        @Override
        public void copyFile(String source, String path, String entryName) {

        }

        @Override
        public void saveClassFile(String path, String qualifiedName, String entryName, String content, int[] mapping) {
            component.setComponent(component.getComponent().append(Component.text(content)));
        }

        @Override
        public void createArchive(String path, String archiveName, Manifest manifest) {

        }

        @Override
        public void saveDirEntry(String path, String archiveName, String entryName) {

        }

        @Override
        public void copyEntry(String source, String path, String archiveName, String entry) {

        }

        @Override
        public void saveClassEntry(String path, String archiveName, String qualifiedName, String entryName, String content) {
            
        }

        @Override
        public void closeArchive(String path, String archiveName) {

        }

        public Component getComponent() {
            return component.getComponent();
        }
    }
    
    static class InlineContextSource implements IContextSource {

        private final String type;

        public InlineContextSource(String type) {
            this.type = type;
        }
        
        @Override
        public String getName() {
            return type;
        }

        @Override
        public Entries getEntries() {
            return new Entries(List.of(new Entry(type, -1)), List.of(), List.of());
        }

        @Override
        public InputStream getInputStream(String resource) {
            return getClass().getClassLoader().getResourceAsStream(type.replace('.', '/') + ".class");
        }

        @Override
        public IOutputSink createOutputSink(IResultSaver saver) {
            return new IOutputSink() {
                @Override
                public void begin() {
                    
                }

                @Override
                public void acceptClass(String qualifiedName, String fileName, String content, int[] mapping) {
                    saver.saveClassFile(fileName, qualifiedName, fileName, content, mapping);
                }

                @Override
                public void acceptDirectory(String directory) {

                }

                @Override
                public void acceptOther(String path) {

                }

                @Override
                public void close() throws IOException {

                }
            };
        }
    }
}

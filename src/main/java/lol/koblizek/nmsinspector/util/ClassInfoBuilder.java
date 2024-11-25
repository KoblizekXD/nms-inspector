package lol.koblizek.nmsinspector.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextDecoration;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public final class ClassInfoBuilder {
    
    private ClassInfoBuilder() {}
    
    public static Component getSummary(Class<?> type) {
        return Component.empty()
                .append(modsAsComponent(type.getModifiers()))
                .appendSpace()
                .append(Component.text("class").color(NamedColors.KEYWORDS))
                .appendSpace()
                .append(Component.text(type.getSimpleName()).color(NamedColors.SYMBOLS))
                .append(getSupers(type))
                .append(Component.text(";").color(NamedColors.UNUSED))
                .appendNewline()
                .append(getMemberSummary(type)); 
    }
    
    private static Component getSupers(Class<?> type) {
        Component com = Component.empty();
        
        if (type.getSuperclass() != null) {
            com = com.append(Component.text(" extends ").color(NamedColors.KEYWORDS))
                    .append(Component.text(type.getSuperclass().getSimpleName()).color(NamedColors.SYMBOLS)
                            .clickEvent(ClickEvent.runCommand("/classinfo " + type.getSuperclass().getName()))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to view " + type.getSuperclass().getName()).color(NamedColors.SYMBOLS)))
                            .decorate(TextDecoration.UNDERLINED));
        }

        if (type.getInterfaces().length != 0) {
            com = com.append(Component.text(" implements ").color(NamedColors.KEYWORDS))
                    .append(Component.join(JoinConfiguration.separator(Component.text(", ").color(NamedColors.UNUSED)), Arrays.stream(type.getInterfaces())
                            .map(c -> Component.text(c.getSimpleName()).color(NamedColors.SYMBOLS)
                                    .clickEvent(ClickEvent.runCommand("/classinfo " + c.getName()))
                                    .hoverEvent(HoverEvent.showText(Component.text("Click to view " + c.getSimpleName()).color(NamedColors.SYMBOLS)))
                                    .decorate(TextDecoration.UNDERLINED)).toList()));
        }
        
        return com;
    }
    
    private static Component getMemberSummary(Class<?> type) {
        return Component.empty().append(Component.text("Field count: " + type.getDeclaredFields().length))
                .appendNewline()
                .append(Component.text("Constructor count: " + type.getDeclaredConstructors().length))
                .appendNewline()
                .append(Component.text("Method count: " + type.getDeclaredMethods().length))
                .color(NamedColors.UNUSED);
    }
    
    public static Component modsAsComponent(int modifiers) {
        return Component.text(Modifier.toString(modifiers))
                .color(NamedColors.KEYWORDS);
    }
}

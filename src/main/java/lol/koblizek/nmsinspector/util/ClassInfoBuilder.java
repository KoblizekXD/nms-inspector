package lol.koblizek.nmsinspector.util;

import lol.koblizek.nmsinspector.commands.ClassInfoCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextDecoration;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public final class ClassInfoBuilder {
    
    private ClassInfoBuilder() {}
    
    public static Component getSummary(Class<?> type, ClassInfoCommand.Filter filter) {
        var component = Component.empty()
                .append(getAnnotations(type))
                .appendNewline()
                .append(modsAsComponent(type.getModifiers()))
                .appendSpace()
                .append(Component.text("class").color(NamedColors.KEYWORDS))
                .appendSpace()
                .append(Component.text(type.getSimpleName()).color(NamedColors.SYMBOLS)
                        .hoverEvent(HoverEvent.showText(Component.text(type.getName()).color(NamedColors.SYMBOLS))))
                .append(getSupers(type))
                .append(Component.text(";").color(NamedColors.UNUSED))
                .appendNewline()
                .append(getMemberSummary(type));
        
        if (filter == ClassInfoCommand.Filter.FIELDS || filter == ClassInfoCommand.Filter.ALL)
            component = component.appendNewline().append(getFields(type));
        
        if (filter == ClassInfoCommand.Filter.CONSTRUCTORS || filter == ClassInfoCommand.Filter.ALL)
            component = component.appendNewline().append(getConstructors(type));
        
        if (filter == ClassInfoCommand.Filter.METHODS || filter == ClassInfoCommand.Filter.ALL)
            component = component.appendNewline().append(getMethods(type));
        
        return component;
    }
    
    public static Component getAnnotations(AnnotatedElement element) {
        return Component.join(JoinConfiguration.separator(Component.newline()), Arrays.stream(element.getAnnotations())
                .map(a -> Component.text("@" + a.annotationType().getSimpleName()).color(NamedColors.SYMBOLS))
                .toList());
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
    
    public static Component getFields(Class<?> type) {
        return Component.join(JoinConfiguration.separator(Component.newline()), Arrays.stream(type.getDeclaredFields()).map(field -> Component.empty()
                .append(modsAsComponent(field.getModifiers()))
                .appendSpace().append(Component.text(field.getType().getSimpleName()).color(NamedColors.SYMBOLS).clickEvent(ClickEvent.runCommand("/classinfo " + field.getType().getName()))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to view " + field.getType().getName()).color(NamedColors.SYMBOLS)))
                        .decorate(TextDecoration.UNDERLINED))
                .appendSpace().append(Component.text(field.getName()).color(NamedColors.SYMBOLS))
                .append(Component.text(";").color(NamedColors.UNUSED))).toList());
    }

    public static Component getMethods(Class<?> type) {
        return Component.join(JoinConfiguration.separator(Component.newline()), Arrays.stream(type.getDeclaredMethods()).map(method -> Component.empty()
                .append(modsAsComponent(method.getModifiers()))
                .appendSpace().append(Component.text(method.getReturnType().getSimpleName()).color(NamedColors.SYMBOLS).clickEvent(ClickEvent.runCommand("/classinfo " + method.getReturnType().getName()))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to view " + method.getReturnType().getName()).color(NamedColors.SYMBOLS)))
                        .decorate(TextDecoration.UNDERLINED))
                .appendSpace().append(Component.text(method.getName()).color(NamedColors.SYMBOLS))
                .append(Component.text("(").color(NamedColors.UNUSED))
                .append(Component.join(JoinConfiguration.separator(Component.text(", ").color(NamedColors.UNUSED)), Arrays.stream(method.getParameterTypes())
                        .map(c -> Component.text(c.getSimpleName()).color(NamedColors.SYMBOLS).clickEvent(ClickEvent.runCommand("/classinfo " + c.getName()))
                                .hoverEvent(HoverEvent.showText(Component.text("Click to view " + c.getName()).color(NamedColors.SYMBOLS)))
                                .decorate(TextDecoration.UNDERLINED)).toList()))
                .append(Component.text(")").color(NamedColors.UNUSED))
                .append(Component.text(";").color(NamedColors.UNUSED))).toList());
    }

    public static Component getConstructors(Class<?> type) {
        return Component.join(JoinConfiguration.separator(Component.newline()), Arrays.stream(type.getDeclaredConstructors()).map(method -> Component.empty()
                .append(modsAsComponent(method.getModifiers()))
                .appendSpace().append(Component.text(type.getSimpleName()).color(NamedColors.SYMBOLS))
                .append(Component.text("(").color(NamedColors.UNUSED))
                .append(Component.join(JoinConfiguration.separator(Component.text(", ").color(NamedColors.UNUSED)), Arrays.stream(method.getParameterTypes())
                        .map(c -> Component.text(c.getSimpleName()).color(NamedColors.SYMBOLS).clickEvent(ClickEvent.runCommand("/classinfo " + c.getName()))
                                .hoverEvent(HoverEvent.showText(Component.text("Click to view " + c.getName()).color(NamedColors.SYMBOLS)))
                                .decorate(TextDecoration.UNDERLINED)).toList()))
                .append(Component.text(")").color(NamedColors.UNUSED))
                .append(Component.text(";").color(NamedColors.UNUSED))).toList());
    }
}

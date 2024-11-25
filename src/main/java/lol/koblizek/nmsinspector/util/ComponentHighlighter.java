package lol.koblizek.nmsinspector.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class ComponentHighlighter {
    private ComponentHighlighter() {}
    
    private static final List<String> KEYWORDS = Arrays.asList("abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue",
            "default", "do", "double", "else", "enum", "extends", "final", "finally", "float", "for", "goto", "if",
            "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "package", "private",
            "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this",
            "throw", "throws", "transient", "try", "void", "volatile", "while");
    
    private static final List<String> BRACES = Arrays.asList("{", "}", "(", ")", "[", "]", ";");
    
    public static Component highlight(Component component) {
        String res = MiniMessage.miniMessage().serialize(component);
        
        return MiniMessage.miniMessage().deserialize(Arrays.stream(res.split(" ")).map(word -> {
            if (KEYWORDS.contains(word)) {
                return "<" + NamedColors.KEYWORDS.asHexString() + ">" + word + "</" + NamedColors.KEYWORDS.asHexString() + ">";
            } else if (BRACES.contains(word)) {
                return "<" + NamedColors.SYMBOLS.asHexString() + ">" + word + "</" + NamedColors.SYMBOLS.asHexString() + ">";
            } else {
                return word;
            }
        }).collect(Collectors.joining(" ")));
    }
}

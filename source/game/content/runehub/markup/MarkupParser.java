package game.content.runehub.markup;

import java.util.Arrays;

public class MarkupParser {

    public static RSString parseMarkup(String text) {
        final String[] words = text.split(" ");
        final RSString.RSStringBuilder builder = new RSString.RSStringBuilder();
        Arrays.stream(words).forEach(word -> {
            try {
                if (word.startsWith("@")) {
                    builder
                            .insertItem(Integer.parseInt(word.substring(1)))
                            .addText(" ");
                } else if (word.startsWith("#")) {
                    builder
                            .insertFormattedQuantity(Integer.parseInt(word.substring(1)))
                            .addText(" ");
                } else if (word.startsWith("$")) {
                    builder
                            .highlight(word.substring(1))
                            .addText(" ");
                } else if(word.startsWith("^")) {
                    builder
                            .insertHeader(word.substring(1))
                            .addText(" ");
                } else {
                    builder.addText(word)
                            .addText(" ");
                }
            } catch (NumberFormatException e) {
                builder.addText(word)
                        .addText(" ");
            }
        });
        return builder.build();
    }
}

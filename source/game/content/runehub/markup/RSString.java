package game.content.runehub.markup;

import org.runehub.api.io.load.impl.ItemIdContextLoader;

import java.text.NumberFormat;

public class RSString {

    public static final String BLUE = "255";
    public static final String RED = "ff0000";
    public static final String PURPLE = "A10081";

    public String getText() {
        return text;
    }

    private RSString(RSStringBuilder builder) {
        this.text = builder.sb.toString();
    }

    private final String text;

    public static class RSStringBuilder {

        public RSStringBuilder addText(String text) {
            sb.append(text);
            return this;
        }

        public RSStringBuilder insertHeader(String text) {
            sb.append("[<col=")
                    .append(PURPLE)
                    .append(">")
                    .append(text)
                    .append("</col>]");
            return this;
        }

        public RSStringBuilder wrapInColor(String color, String text) {
            sb.append("<col=")
                    .append(color)
                    .append(">")
                    .append(text)
                    .append("</col>");
            return this;
        }

        public RSStringBuilder highlight(String text) {
            return this.wrapInColor(BLUE,text);
        }

        public RSStringBuilder insertItem(int itemId) {
            return this.wrapInColor(RED,ItemIdContextLoader.getInstance().read(itemId).getName());
        }

        public RSStringBuilder insertQuantity(int amount) {
            return this.wrapInColor(RED,String.valueOf(amount));
        }

        public RSStringBuilder insertFormattedQuantity(int amount) {
            sb.append("<col=")
                    .append(RED)
                    .append(">")
                    .append(NumberFormat.getInstance().format(amount))
                    .append("</>");
            return this;
        }

        public RSStringBuilder insertFormattedNumber(int amount) {
            sb.append(NumberFormat.getInstance().format(amount));
            return this;
        }

        public RSString build() {
            return new RSString(this);
        }

        public RSStringBuilder() {
            sb = new StringBuilder();
        }

        private final StringBuilder sb;

    }

}

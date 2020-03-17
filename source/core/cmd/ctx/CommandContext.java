package core.cmd.ctx;

import org.menaphos.entity.impl.impl.PlayableCharacter;

public class CommandContext {

    private final String content;
    private final PlayableCharacter author;

    public CommandContext(String content, PlayableCharacter author) {
        this.content = content;
        this.author = author;
    }

    public PlayableCharacter getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}

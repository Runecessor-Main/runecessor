package game.content.phantasye.dialogue;

import game.content.dialogue.DialogueChain;
import game.content.dialogueold.DialogueHandler;
import game.npc.data.NpcDefinition;
import game.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class DialogueOptionPaginator {

    private final String[] options;
    private final String title;
    private final Player player;
    private int index;

    private DialogueOptionPaginator(DialogueOptionPaginatorBuilder bldr) {
        this.options = createOptionChain(bldr.options);
        this.title = bldr.title;
        this.player = bldr.player;
    }

    public String[] sendPage(int index) {
        this.setIndex(index);
        return getPage(index);
    }

    public String[] nextPage() {
        if (!isLastPage()) {
            this.setIndex(index += 1);
            return sendPage(index);
        }
        return sendPage(index);
    }

    public DialogueChain getPageAsDialogOptions(int page,DialoguePaginatorClickListener listener) {
        return new DialogueChain().option(listener,title,sendPage(page));
    }

    public DialogueChain getPageAsNpcDialog(int npc, DialogueHandler.FacialAnimation animation,int page) {
        return new DialogueChain().npc(NpcDefinition.getDefinitions()[npc],animation,sendPage(page));
    }

    public DialogueChain getPageAsPlayerDialog(DialogueHandler.FacialAnimation animation,int page) {
        return new DialogueChain().player(animation,sendPage(page));
    }

    public boolean isLastPage() {
        return index == getPages() - 1;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public String[] getOptions() {
        return options;
    }

    public int getPages() {
        return createPages(Arrays.asList(options)).size();
    }

    public String[] getPage(int index) {
        if (index <= getPages()) {
            final List<String[]> pages = createPages(Arrays.asList(options));
            final List<String> page = new ArrayList<>(Arrays.asList(pages.get(index)));
            return createOptionChain(page);
        }
        return null;
    }


    public int getTotalOptions() {
        return options.length;
    }

    private List<String[]> createPages(List<String> options) {
        final List<String[]> optionChainList = new ArrayList<>();
        final List<String> optionList = new ArrayList<>();
        for (int i = 0; i < options.size(); i++) {
            if (i % 3 == 0 && i != 0 && i != options.size() - 1) {
                optionList.add("Next");
                optionChainList.add(optionList.toArray(new String[0]));
                optionList.clear();
                optionList.add(options.get(i));
            } else if (i != options.size() - 1) {
                optionList.add(options.get(i));
            } else if (i % 3 == 0 && i != 0 && i == options.size() - 1) {
                optionList.add("Next");
                optionChainList.add(optionList.toArray(new String[0]));
                optionList.clear();
                optionList.add(options.get(i));
                optionList.add("Nevermind");
                optionChainList.add(optionList.toArray(new String[0]));
            } else {
                optionList.add(options.get(i));
                optionList.add("Nevermind");
                optionChainList.add(optionList.toArray(new String[0]));
            }
        }
        return optionChainList;
    }

    private String[] createOptionChain(Collection<String> options) {
        final List<String> optionChainList = new ArrayList<>(options);
        final String[] array = optionChainList.toArray(new String[0]);
        return array;
    }

    public static class DialogueOptionPaginatorBuilder {

        private final List<String> options;
        private final Player player;
        private String title;

        public DialogueOptionPaginatorBuilder(Player player) {
            this.player = player;
            this.options = new ArrayList<>();
        }

        public DialogueOptionPaginatorBuilder addOption(String option) {
            options.add(option);
            return this;
        }

        public DialogueOptionPaginatorBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public DialogueOptionPaginator build() {
            return new DialogueOptionPaginator(this);
        }
    }


}

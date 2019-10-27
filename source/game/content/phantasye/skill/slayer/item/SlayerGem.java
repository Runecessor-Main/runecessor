package game.content.phantasye.skill.slayer.item;

import game.content.phantasye.skill.slayer.SlayerAssignment;
import game.content.phantasye.skill.slayer.SlayerUnlocks;
import game.content.phantasye.skill.slayer.master.SlayerMaster;
import game.content.phantasye.skill.slayer.master.SlayerMasterFactory;
import game.player.Player;

public final class SlayerGem {

    public static final int ID = 4155;

    private final Player player;

    public SlayerGem(Player player) {
        this.player = player;
    }

    public void checkTask() {
        if (player.getPlayerDetails().getSlayerTask() != null) {
            player.receiveMessage("You must slay "
                    + player.getPlayerDetails().getSlayerTask().getAmount().value()
                    + " more "
                    + SlayerAssignment.values()[player.getPlayerDetails().getSlayerTask().getAssignment()].toString());
        } else {
            player.receiveMessage("You currently have no Slayer Task.");
        }
    }

    public void activate() {
        if (player.getPlayerDetails().getUnlocksList().contains(SlayerUnlocks.REMOTE_TASKS.ordinal())) {
            if (player.getPlayerDetails().getSlayerMaster() > 0) {
                SlayerMasterFactory.getSlayerMaster(player.getPlayerDetails().getSlayerMaster()).ifPresent(slayerMaster -> slayerMaster.talkTo(player));
            } else {
                player.receiveMessage("You do not have a Slayer Master. Please get a task first.");
            }
        } else {
            player.receiveMessage("You must unlock " + SlayerUnlocks.REMOTE_TASKS.toString() + " to do this.");
        }
    }

    public void social() {
        if (player.getSlayerPartner() == null) {
            player.receiveMessage("Use this gem on another player to invite them to Slay with you.");
        } else {
            SlayerMaster.leaveGroupDialog(player);
        }
    }

}

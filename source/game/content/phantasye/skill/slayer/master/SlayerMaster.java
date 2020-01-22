package game.content.phantasye.skill.slayer.master;

import core.ServerConstants;
import game.content.dialogue.DialogueChain;
import game.content.dialogueold.DialogueHandler;
import game.content.phantasye.dialogue.DialogueOptionPaginator;
import game.content.phantasye.dialogue.impl.ResetTaskPaginatorListener;
import game.content.phantasye.dialogue.impl.SlayerMasterTalkToListener;
import game.content.phantasye.dialogue.impl.SlayerMasterUnlockListener;
import game.content.phantasye.skill.slayer.SlayerAssignment;
import game.content.phantasye.skill.slayer.SlayerSkill;
import game.content.phantasye.skill.slayer.SlayerUnlocks;
import game.content.phantasye.skill.slayer.master.assignment.DuoPlayerAssignmentChain;
import game.content.phantasye.skill.slayer.master.assignment.SoloSlayerAssignmentChain;
import game.content.phantasye.skill.slayer.task.SlayerTask;
import game.content.skilling.Skilling;
import game.npc.data.NpcDefinition;
import game.player.Player;
import org.menaphos.action.ActionInvoker;
import org.menaphos.entity.impl.impl.NonPlayableCharacter;
import org.menaphos.entity.impl.impl.PlayableCharacter;
import org.menaphos.model.math.Range;
import org.menaphos.model.world.location.Location;
import org.menaphos.util.StopWatch;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SlayerMaster implements NonPlayableCharacter {

    private static final int RESET_COST = 30;
    private static final int BLOCK_COST = 100;
    private static final int PREFER_COST = 150;
    private static final int EXTEND_COST = 30;
    private final int GP_COST = 500000;
    private final List<SlayerTask> taskList;
    private final int levelRequirement;
    private final int id;
    private final int basePointValue;
    private final List<String> options;

    public SlayerMaster(int id, int levelRequirement, int basePointValue) {
        this.taskList = new ArrayList<>();
        this.id = id;
        this.basePointValue = basePointValue;
        this.options = new LinkedList<>();
        this.levelRequirement = levelRequirement;
        options.add("Assign Task");
        options.add("Open Shop");
        options.add("Cancel Task (" + RESET_COST + " Points)");
        options.add("Extend Task (" + EXTEND_COST + " Points)");
        options.add("Prefer Task (" + PREFER_COST + " Points)");
        options.add("Block Task (" + BLOCK_COST + " Points)");
        options.add("Social Slayer");
        options.add("Unlockables");
    }

    private List<String[]> createOptionChain(List<String> options) {
        final StringBuilder sb = new StringBuilder();
        final List<String[]> optionChainList = new ArrayList<>();
        for (int i = 0; i < options.size(); i++) {
            if (i % 5 == 0 && options.size() > 5) {
                sb.append("More Options");
                optionChainList.add(sb.toString().split("|"));
                sb.setLength(0);
                sb.append(options.get(i))
                        .append("|");
            } else {
                sb.append(options.get(i))
                        .append("|");
            }
        }
        return optionChainList;
    }

    public void completeTask(Player player) {
        int points = basePointValue;
        player.getPlayerDetails().getTaskStreak().increment();
        player.getPlayerDetails().getTasksCompleted().increment();
        if (player.getPlayerDetails().getTaskStreak().value() % 10 == 0) {
            points *= 5;
        } else if (player.getPlayerDetails().getTaskStreak().value() % 50 == 0) {
            points *= 15;
        } else if (player.getPlayerDetails().getTaskStreak().value() % 100 == 0) {
            points *= 25;
        } else if (player.getPlayerDetails().getTaskStreak().value() % 250 == 0) {
            points *= 35;
        } else if (player.getPlayerDetails().getTaskStreak().value() % 1000 == 0) {
            points *= 50;
        }

        player.getPlayerDetails().getSlayerPoints().add(points);
        player.receiveMessage("You've completed " + player.getPlayerDetails().getTaskStreak().value()
                + " Tasks in a row and received "
                + points
                + " Slayer Points! You now have " + player.getPlayerDetails().getSlayerPoints().value()
                + " Slayer Points.");

        if (SlayerSkill.isBossTask(player.getPlayerDetails().getSlayerTask().getAssignment())) {
            Skilling.addSkillExperience(player, 5000, 18, false);
            player.receiveMessage("You receive a bonus "
                    + " 5000 XP for completeing a boss task.");
        }

        player.getPlayerDetails().setSlayerTask(null);
    }

    public void talkTo(Player player) {
        player.getPA().closeInterfaces(true);
        DialogueOptionPaginator.DialogueOptionPaginatorBuilder builder = new DialogueOptionPaginator.DialogueOptionPaginatorBuilder(player);
        options.forEach(builder::addOption);
        builder.withTitle("How Can I Help?");
        DialogueOptionPaginator paginator = builder.build();
        player.setDialogueChain(paginator.getPageAsDialogOptions(0, new SlayerMasterTalkToListener(paginator, this)))
                .start(player);
    }

    public void assignTaskTo(Player player) {
        player.getPlayerDetails().setSlayerMaster(this.id);
        if (SlayerSkill.isDoingDuoSlayer(player)) {
            new DuoPlayerAssignmentChain(player, this).execute();
        } else {
            new SoloSlayerAssignmentChain(player, this).execute();
        }
    }

    public void openShopFor(Player player) {
        player.getShops().openShop(46);
        player.receiveMessage("You have " + ServerConstants.RED_COL + player.getPlayerDetails().getSlayerPoints().value() + "</col> Slayer Points.");
    }

    public void sendUnlockDialog(Player player) {
        player.getPA().closeInterfaces(true);
        final DialogueOptionPaginator.DialogueOptionPaginatorBuilder builder = new DialogueOptionPaginator.DialogueOptionPaginatorBuilder(player);
        builder.withTitle("What would you like to Unlock?");
        Arrays.stream(SlayerUnlocks.values()).forEach(unlock -> builder.addOption(unlock.toString()));
        DialogueOptionPaginator paginator = builder.build();
        player.setDialogueChain(paginator.getPageAsDialogOptions(0, new SlayerMasterUnlockListener(paginator)))
                .start(player);
    }

    public void sendSocialSlayerInfoDialog(Player player) {
        player.setDialogueChain(new DialogueChain().npc(NpcDefinition.getDefinitions()[id], DialogueHandler.FacialAnimation.DEFAULT,
                "Simply use your Slayer Gem on another player to invite them",
                "both players must have the same task or no task and to work.",
                "Both players will get kill credit if they're within 30 tiles of eachother.")).start(player);
    }

    public void sendBlockDialog(Player player) {
        player.getPA().closeInterfaces(true);
        player.setDialogueChain(new DialogueChain().option((p, option) -> {
                    switch (option) {
                        case 1:
                            blockTaskFor(player);
                            player.getPA().closeInterfaces(true);
                            break;
                        case 2:
                            if (!player.getPlayerDetails().getBlockedTasks().isEmpty())
                                sendBlockedTaskList(player);
                            else {
                                player.receiveMessage("You do not have any blocked tasks.");
                                player.getPA().closeInterfaces(true);
                            }

                            break;
                        case 3:
                            player.getPA().closeInterfaces(true);
                            break;
                    }
                }, "How Can I Help?",
                "Block Current Task", "View Blocked Tasks", "Nevermind"))
                .start(player);
    }

    private void sendBlockedTaskList(Player player) {
        player.getPA().closeInterfaces(true);
        final StringBuilder taskBuilder = new StringBuilder();
        player.getPlayerDetails().getBlockedTasks()
                .forEach(task -> taskBuilder.append(SlayerAssignment.values()[task].name()).append("#"));
        taskBuilder.append("Back");
        final String[] tasks = taskBuilder.toString().split("#");
        final int lastOption = tasks.length;
        player.setDialogueChain(new DialogueChain().option((p, option) -> {
                    if (option == lastOption) {
                        sendBlockDialog(player);
                    } else {
                        removeBlockedDialog(player, option - 1);
                    }
                }, "Your Blocked Tasks",
                tasks))
                .start(player);
    }

    private void removeBlockedDialog(Player player, int task) {
        player.getPA().closeInterfaces(true);
        player.setDialogueChain(new DialogueChain().option((p, option) -> {
                    switch (option) {
                        case 1:
                            removeBlock(player, task);
                            player.getPA().closeInterfaces(true);
                            break;
                        case 2:
                            sendBlockedTaskList(player);
                            break;
                    }
                }, "Would you Like to Remove this Blocked Task?",
                "Yes", "No"))
                .start(player);
    }

    private void removeBlock(Player player, int task) {
        player.receiveMessage("You have removed "
                + SlayerAssignment.values()[player.getPlayerDetails().getBlockedTasks().get(task)].name()
                + " from your blocked task list.");
        player.getPlayerDetails().getBlockedTasks().remove(task);
        player.saveDetails();
    }

    public void sendPreferDialog(Player player) {
        player.getPA().closeInterfaces(true);
        player.setDialogueChain(new DialogueChain().option((p, option) -> {
                    switch (option) {
                        case 1:
                            preferTaskForPlayer(player);
                            player.getPA().closeInterfaces(true);
                            break;
                        case 2:
                            if (!player.getPlayerDetails().getPreferredTasks().isEmpty())
                                sendPreferredTaskList(player);
                            else {
                                player.receiveMessage("You do not have any preferred tasks.");
                                player.getPA().closeInterfaces(true);
                            }

                            break;
                        case 3:
                            player.getPA().closeInterfaces(true);
                            break;
                    }
                }, "How Can I Help?",
                "Prefer Current Task", "View Preferred Tasks", "Nevermind"))
                .start(player);
    }

    private void sendPreferredTaskList(Player player) {
        player.getPA().closeInterfaces(true);
        final StringBuilder taskBuilder = new StringBuilder();
        player.getPlayerDetails().getPreferredTasks()
                .forEach(task -> taskBuilder.append(SlayerAssignment.values()[task].name()).append("#"));
        taskBuilder.append("Back");
        final String[] tasks = taskBuilder.toString().split("#");
        final int lastOption = tasks.length;
        player.setDialogueChain(new DialogueChain().option((p, option) -> {
                    if (option == lastOption) {
                        sendPreferDialog(player);
                    } else {
                        removePreferenceDialog(player, option - 1);
                    }
                }, "Your Preferred Tasks",
                tasks))
                .start(player);
    }

    private void removePreferenceDialog(Player player, int task) {
        player.getPA().closeInterfaces(true);
        player.setDialogueChain(new DialogueChain().option((p, option) -> {
                    switch (option) {
                        case 1:
                            removePreference(player, task);
                            player.getPA().closeInterfaces(true);
                            break;
                        case 2:
                            sendPreferredTaskList(player);
                            break;
                    }
                }, "Would you Like to Remove this Preference?",
                "Yes", "No"))
                .start(player);
    }

    private void removePreference(Player player, int task) {
        player.receiveMessage("You have removed "
                + SlayerAssignment.values()[player.getPlayerDetails().getPreferredTasks().get(task)].name()
                + " from your preferred task list.");
        player.getPlayerDetails().getPreferredTasks().remove(task);
        player.saveDetails();
    }

    private void blockTaskFor(Player player) {
        if (player.getPlayerDetails().getSlayerTask() != null) {
            if (!SlayerSkill.isBossTask(player.getPlayerDetails().getSlayerTask().getAssignment())) {
                if (player.getPlayerDetails().getSlayerPoints().value() >= BLOCK_COST) {
                    if (player.getPlayerDetails().getBlockedTasks().size() < player.getPlayerDetails().getBlockCap().value()) {
                        player.getPlayerDetails().getBlockedTasks().add(player.getPlayerDetails().getSlayerTask().getAssignment());
                        player.receiveMessage("You've blocked the task of "
                                + SlayerAssignment.values()[player.getPlayerDetails().getSlayerTask().getAssignment()].name()
                                + " for "
                                + BLOCK_COST
                                + " Slayer Points.");
                        player.getPlayerDetails().setSlayerTask(null);
                        player.saveDetails();
                    } else {
                        player.receiveMessage("You need at least "
                                + BLOCK_COST
                                + " Slayer Points to block a task.");
                    }
                } else {
                    player.receiveMessage("You may only block up to "
                            + player.getPlayerDetails().getBlockCap().value()
                            + " tasks at a time. Please remove one.");
                }
            } else {
                player.receiveMessage("You may not block a boss task.");
            }
        } else {
            player.receiveMessage("You do not have an active Slayer Task.");
        }
    }

    private void preferTaskForPlayer(Player player) {
        if (player.getPlayerDetails().getSlayerTask() != null) {
            if (!SlayerSkill.isBossTask(player.getPlayerDetails().getSlayerTask().getAssignment())) {
                if (player.getPlayerDetails().getPreferredTasks().size() < player.getPlayerDetails().getPreferCap().value()) {
                    if (player.getPlayerDetails().getSlayerPoints().value() >= PREFER_COST) {
                        player.getPlayerDetails().getSlayerPoints().subtract(PREFER_COST);
                        player.getPlayerDetails().getPreferredTasks().add(player.getPlayerDetails().getSlayerTask().getAssignment());
                        player.saveDetails();
                        player.receiveMessage("You've preferred your current task for " + PREFER_COST + " Slayer Points.");
                    } else {
                        player.receiveMessage("You need at least " + PREFER_COST + " Slayer Points to prefer a task.");
                    }
                } else {
                    player.receiveMessage("You may only prefer up to " + player.getPlayerDetails().getPreferCap().value() + " tasks at a time.");
                }
            } else {
                player.receiveMessage("You may not prefer a boss task.");
            }
        } else {
            player.receiveMessage("You do not have a task to prefer.");
        }
    }

    public void extendTaskFor(Player player) {
        if (player.getPlayerDetails().getSlayerTask() != null) {
            if (!SlayerSkill.isBossTask(player.getPlayerDetails().getSlayerTask().getAssignment())) {
                if (player.getPlayerDetails().getSlayerPoints().value() >= EXTEND_COST) {
                    final Range extension = taskList.stream()
                            .filter(task -> player.getPlayerDetails().getSlayerTask().getAssignment() == task.getAssignment().ordinal())
                            .findAny().orElse(new SlayerTask(SlayerAssignment.BLOODVELD, null, null, 0))
                            .getExtendedAmount();
                    if (extension != null) {
                        if (extension.getMin() > 0) {
                            player.getPlayerDetails().getSlayerTask().getAmount().add(Range.getIntFromRange(extension));
                            player.getPlayerDetails().getSlayerPoints().subtract(EXTEND_COST);
                            player.receiveMessage("You've extended your task to "
                                    + player.getPlayerDetails().getSlayerTask().getAmount().value()
                                    + " "
                                    + SlayerAssignment.values()[player.getPlayerDetails().getSlayerTask().getAssignment()].name());
                            player.saveDetails();
                        } else {
                            player.receiveMessage("An extension is not available for your task.");
                        }
                    } else {
                        player.receiveMessage("An extension is not available for your task.");
                    }
                } else {
                    player.receiveMessage("You need at least "
                            + EXTEND_COST
                            + " Slayer Points to extend your task.");
                }
            } else {
                player.receiveMessage("You can not extend a boss task.");
            }
        } else {
            player.receiveMessage("You do not have a Slayer Task to extend.");
        }
    }

    public void cancelTaskForCoins(Player player) {
        if (player.getPlayerDetails().getSlayerTask() != null) {
            if (player.hasItem(995,GP_COST)) {
                player.removeItemFromInventory(995,GP_COST);
                player.getPlayerDetails().setSlayerTask(null);
                player.getPlayerDetails().getTaskStreak().setValue(0);
                player.saveDetails();
                player.receiveMessage("You've cancelled your current task for " + NumberFormat.getInstance().format(GP_COST) + " Coins.");
            } else {
                player.receiveMessage("You need at least " + NumberFormat.getInstance().format(GP_COST) + " Coins to cancel a task.");
            }
        } else {
            player.receiveMessage("You do not have a Slayer Task to cancel.");
        }
    }

    public void cancelTaskFor(Player player) {
        player.getPA().closeInterfaces(true);
        DialogueOptionPaginator.DialogueOptionPaginatorBuilder builder = new DialogueOptionPaginator.DialogueOptionPaginatorBuilder(player);
        builder.withTitle("Reset your Task?")
                .addOption("Reset with Coins " + "( " + NumberFormat.getInstance().format(GP_COST) + " Coins )" )
                .addOption("Reset with Points " + "( " + RESET_COST + " Points )");
        DialogueOptionPaginator resetPaginator = builder.build();
        player.setDialogueChain(resetPaginator.getPageAsDialogOptions(0, new ResetTaskPaginatorListener(resetPaginator, this)))
                .start(player);
    }

    public void cancelTaskForPoints(Player player) {
        if (player.getPlayerDetails().getSlayerTask() != null) {
            if (player.getPlayerDetails().getSlayerPoints().value() >= RESET_COST) {
                player.getPlayerDetails().getSlayerPoints().subtract(RESET_COST);
                player.getPlayerDetails().setSlayerTask(null);
                player.getPlayerDetails().getTaskStreak().setValue(0);
                player.saveDetails();
                player.receiveMessage("You've cancelled your current task for " + RESET_COST + " Slayer Points.");
            } else {
                player.receiveMessage("You need at least " + RESET_COST + " Slayer Points to cancel a task.");
            }
        } else {
            player.receiveMessage("You do not have a Slayer Task to cancel.");
        }
    }

//    private PlayerSlayerTask getTaskFor(Player player) {
//        if (player.baseSkillLevel[18] >= levelRequirement) {
//            if (player.getSlayerPartner() != null && player.getSlayerPartner().baseSkillLevel[18] >= levelRequirement) {
//                final List<SlayerTask> playerTaskList = new ArrayList<>();
//                taskList.stream()
//                        .filter(task -> task.getAssignment().getLevel() <= player.baseSkillLevel[18])
//                        .filter(task -> !player.getPlayerDetails().getBlockedTasks().contains(task.getAssignment().ordinal()))
//                        .forEach(playerTaskList::add);
//                if (player.getPlayerDetails().getUnlocksList().contains(SlayerUnlocks.BOSS_SLAYER.ordinal())) {
//                    Arrays.stream(BossTask.values())
//                            .filter(bossTask -> bossTask.getCombatLevel() <= player.getCombatLevel())
//                            .forEach(bossTask -> playerTaskList.add(new SlayerTask(
//                                    SlayerAssignment.valueOf(bossTask.name()),
//                                    new Range(3, 35),
//                                    new Range(0, 0),
//                                    4
//                            )));
//                }
//                return roll(playerTaskList, player.getPlayerDetails().getPreferredTasks());
//            } else {
//                player.receiveMessage("Both members need " + levelRequirement + " Slayer to get a task.");
//            }
//        } else {
//            player.receiveMessage("You need " + levelRequirement + " Slayer to get a task.");
//        }
//        return null;
//    }
//
//    private PlayerSlayerTask roll(List<SlayerTask> playerTaskList, List<Integer> preferredList) {
//        final Random r = new Random();
//        final int sum = playerTaskList.stream()
//                .filter(task -> !SlayerSkill.isBossTask(task.getAssignment().ordinal()))
//                .mapToInt(SlayerTask::getWeight).sum();
//        float chance = r.nextFloat();
//        final int index = r.nextInt(playerTaskList.size());
//        float taskRoll;
//        if (index >= taskList.size()) {
//            taskRoll = (((float) playerTaskList.get(index).getWeight() / (sum + 4)));
//        } else if (!preferredList.contains(taskList.get(index).getAssignment().ordinal())) {
//            taskRoll = (((float) playerTaskList.get(index).getWeight() / sum));
//        } else {
//            taskRoll = (((float) (playerTaskList.get(index).getWeight() * 2) / sum));
//        }
//        if (chance <= (taskRoll)) {
//            return new PlayerSlayerTask(playerTaskList.get(index), this.getId());
//        }
//        return roll(playerTaskList, preferredList);
//    }

    public static void sendSocialSlayer(Player player, Player target) {
        if ((player.getPlayerDetails().getSlayerTask() == null && target.getPlayerDetails().getSlayerTask() == null)
                || (player.getPlayerDetails().getSlayerTask() != null
                && target.getPlayerDetails().getSlayerTask() != null)
                && player.getPlayerDetails().getSlayerTask().getAssignment()
                == target.getPlayerDetails().getSlayerTask().getAssignment()) {
            if (player.getSlayerPartner() == null || player.getSlayerPartner() == target) {
                target.getPA().closeInterfaces(true);
                target.setDialogueChain(new DialogueChain().option((p, option) -> {
                            switch (option) {
                                case 1:
                                    target.setSlayerPartner(player);
                                    player.setSlayerPartner(target);
                                    target.getPA().closeInterfaces(true);
                                    player.receiveMessage(target.getPlayerName() + " has joined the group.");
                                    target.receiveMessage("You joined the group.");
                                    break;
                                case 2:
                                    target.getPA().closeInterfaces(true);
                                    player.receiveMessage("Invite declined.");
                                    break;
                            }
                        }, "Slay With " + player.getPlayerName() + "?",
                        "Yes", "No"))
                        .start(target);
            } else {
                player.receiveMessage("Please leave your current group first.");
            }
        } else {
            player.receiveMessage("Both players must not have a task, or have the same task.");
        }
    }

    public static void leaveGroupDialog(Player player) {
        player.getPA().closeInterfaces(true);
        player.setDialogueChain(new DialogueChain().option((p, option) -> {
                    switch (option) {
                        case 1:
                            leaveGroup(player);
                            break;
                        case 2:
                            player.getPA().closeInterfaces(true);
                            break;
                    }
                }, "Leave Slayer Group?",
                "Yes", "No"))
                .start(player);
    }

    public static void leaveGroup(Player player) {
        final Player other = player.getSlayerPartner();
        other.setSlayerPartner(null);
        player.setSlayerPartner(null);
        other.receiveMessage(player.getPlayerName() + " has left the group.");
        player.receiveMessage("You've left the group.");
        player.getPA().closeInterfaces(true);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public boolean addItemToInventory(int i, int i1) {
        return false;
    }

    @Override
    public boolean removeItemFromInventory(int i, int i1) {
        return false;
    }

    @Override
    public boolean pickupItem(int i, int i1) {
        return false;
    }

    @Override
    public void sendMessage(String s) {

    }

    @Override
    public void receiveMessage(String s) {

    }

    @Override
    public boolean hasItem(int i, int i1) {
        return false;
    }

    @Override
    public void performAnimation(int i) {

    }

    @Override
    public StopWatch getStopWatch() {
        return null;
    }

    @Override
    public ActionInvoker getActionInvoker() {
        return null;
    }

    @Override
    public boolean moveTo(Location location) {
        return false;
    }

    public int getLevelRequirement() {
        return levelRequirement;
    }

    public List<SlayerTask> getTaskList() {
        return taskList;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getBasePointValue() {
        return basePointValue;
    }

    @Override
    public void dropLootFor(PlayableCharacter playableCharacter) {

    }
}

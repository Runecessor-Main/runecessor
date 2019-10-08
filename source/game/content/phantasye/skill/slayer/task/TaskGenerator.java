package game.content.phantasye.skill.slayer.task;

import game.content.phantasye.skill.slayer.SlayerAssignment;
import game.content.phantasye.skill.slayer.SlayerSkill;
import game.content.phantasye.skill.slayer.SlayerUnlocks;
import game.content.phantasye.skill.slayer.master.SlayerMaster;
import game.menaphos.looting.model.Range;
import game.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class TaskGenerator {

    public static PlayerSlayerTask generateSlayerTaskFor(Player player,SlayerMaster master) {
        final List<SlayerTask> playerTaskList = new ArrayList<>();
        master.getTaskList().stream()
                .filter(task -> task.getAssignment().getLevel() <= player.baseSkillLevel[18])
                .filter(task -> !player.getPlayerDetails().getBlockedTasks().contains(task.getAssignment().ordinal()))
                .forEach(playerTaskList::add);
        if (player.getPlayerDetails().getUnlocksList().contains(SlayerUnlocks.BOSS_SLAYER.ordinal())) {
            Arrays.stream(BossTask.values())
                    .filter(bossTask -> bossTask.getCombatLevel() <= player.getCombatLevel())
                    .forEach(bossTask -> playerTaskList.add(new SlayerTask(
                            SlayerAssignment.valueOf(bossTask.name()),
                            new Range(3, 35),
                            new Range(0, 0),
                            4
                    )));
        }
        final Random r = new Random();
        final int sum = playerTaskList.stream()
                .filter(task -> !SlayerSkill.isBossTask(task.getAssignment().ordinal()))
                .mapToInt(SlayerTask::getWeight).sum();
        float chance = r.nextFloat();
        final int index = r.nextInt(playerTaskList.size());
        float taskRoll;
        if (index >= master.getTaskList().size()) {
            taskRoll = (((float) playerTaskList.get(index).getWeight() / (sum + 4)));
        } else if (!player.getPlayerDetails().getPreferredTasks().contains(master.getTaskList().get(index).getAssignment().ordinal())) {
            taskRoll = (((float) playerTaskList.get(index).getWeight() / sum));
        } else {
            taskRoll = (((float) (playerTaskList.get(index).getWeight() * 2) / sum));
        }
        if (chance <= (taskRoll)) {
            return new PlayerSlayerTask(playerTaskList.get(index), master.getId());
        }
        return generateSlayerTaskFor(player,master);
    }

}

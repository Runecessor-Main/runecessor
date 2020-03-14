package game.content.phantasye.skill.support.slayer.task;

import game.content.phantasye.skill.support.slayer.SlayerAssignment;
import game.content.phantasye.skill.support.slayer.SlayerSkill;
import game.content.phantasye.skill.support.slayer.SlayerUnlocks;
import game.content.phantasye.skill.support.slayer.master.SlayerMaster;
import game.player.Player;
import org.menaphos.model.math.Range;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class TaskGenerator {

//    package game.content.phantasye.skill.slayer.task;
//
//import game.content.phantasye.skill.slayer.SlayerSkill;
//import game.player.Player;
//import org.menaphos.io.fs.repository.BossTaskRepositoryManager;
//import org.menaphos.model.math.Range;
//import org.menaphos.model.skill.slayer.io.SlayerMonsterFactory;
//import org.menaphos.model.skill.slayer.npc.SlayerMaster;
//import org.menaphos.model.skill.slayer.task.BossTask;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Random;
//
//    public final class TaskGenerator {
//
//        public static PlayerSlayerTask generateSlayerTaskFor(Player player, org.menaphos.model.skill.slayer.npc.SlayerMaster master) {
//            final List<org.menaphos.model.skill.slayer.task.SlayerTask> playerTaskList = new ArrayList<>();
//            master.getTaskList().stream()
//                    .filter(task -> SlayerMonsterFactory.getSlayerMonsterForTask(task.getTaskId()).getLevel() <= player.baseSkillLevel[18])
//                    .filter(task -> !player.getPlayerDetails().getBlockedTasks().contains(task.getTaskId()))
//                    .forEach(playerTaskList::add);
//            if (player.getPlayerDetails().getUnlocksList().contains(1)) {
//                BossTaskRepositoryManager.getInstance().getRepository().readAll()
//                        .stream()
//                        .filter(bossTask -> bossTask.getCombatLevel() <= player.getCombatLevel())
//                        .forEach(bossTask -> playerTaskList.add(new org.menaphos.model.skill.slayer.task.BossTask(
//                                bossTask.getTaskId(),
//                                bossTask.getCombatLevel()
//                        )));
//            }
//
//
//            final Random r = new Random();
//            final int sum = playerTaskList.stream()
//                    .filter(task -> !SlayerSkill.isBossTask(task.getTaskId()))
//                    .mapToInt(org.menaphos.model.skill.slayer.task.SlayerTask::getWeight).sum();
//            float chance = r.nextFloat();
//            final int index = r.nextInt(playerTaskList.size());
//            float taskRoll;
//            if (index >= master.getTaskList().size()) {
//                taskRoll = (((float) playerTaskList.get(index).getWeight() / (sum + 4)));
//            } else if (!player.getPlayerDetails().getPreferredTasks().contains(master.getTaskList().get(index).getTaskId())) {
//                taskRoll = (((float) playerTaskList.get(index).getWeight() / sum));
//            } else {
//                taskRoll = (((float) (playerTaskList.get(index).getWeight() * 2) / sum));
//            }
//            if (chance <= (taskRoll)) {
//                return new PlayerSlayerTask(playerTaskList.get(index), master.getId());
//            }
//            return generateSlayerTaskFor(player, master);
//        }
//
//    }


    public static PlayerSlayerTask generateSlayerTaskFor(Player player, SlayerMaster master) {
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

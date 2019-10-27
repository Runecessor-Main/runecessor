package game.content.combat.effect;

import game.npc.Npc;
import game.npc.data.NpcDefinition;

public class TwistedBow {

    public static double getAccuracyOnNpc(Npc npc) {
        final NpcDefinition definition = NpcDefinition.getDefinitions()[npc.npcType];
        final double npcMagicLevel = ((double)definition.attack / 3) - 100;
        final double value1 = ((npcMagicLevel * 3) - 10) / 100;
        final double value2 = Math.pow((npcMagicLevel * 3) / 10 - 100,2) / 100;
        final double finalValue = 140 + value1 - value2;
        if(finalValue > 140) {
            return 140;
        }
        return finalValue;
    }

    public static double getDamageOnNpc(Npc npc) {
        final NpcDefinition definition = NpcDefinition.getDefinitions()[npc.npcType];
        int enemyMagicLevel = definition.attack /3;
        double enemyMagicLevelAdjusted = enemyMagicLevel - (enemyMagicLevel * .9);
        double leftTopValue = enemyMagicLevelAdjusted * 3 - 14;
        double leftValue = leftTopValue / 100;
        double rightTopValue = Math.pow((enemyMagicLevelAdjusted * 3) / 10 - 140,2);
        double rightValue = (rightTopValue / 100); //2116

        double finalValue = (200 + leftValue - rightValue);
        if(finalValue > 200) {
            return 100;
        }
        return finalValue;
    }
}

package game.content.phantasye.gambling;

import core.GameType;
import game.content.dialogue.DialogueChain;
import game.content.dialogueold.DialogueHandler;
import game.item.GameItem;
import game.item.ItemDefinition;
import game.npc.CustomNpcComponent;
import game.npc.Npc;
import game.npc.NpcHandler;
import game.player.Player;
import game.type.GameTypeIdentity;
import org.menaphos.action.ActionInvoker;
import org.menaphos.entity.impl.impl.NonPlayableCharacter;
import org.menaphos.model.math.AdjustableNumber;
import org.menaphos.model.math.impl.AdjustableInteger;
import org.menaphos.model.world.location.Location;
import utility.Misc;

import java.text.NumberFormat;
import java.util.*;

@CustomNpcComponent(identities = @GameTypeIdentity(type = GameType.OSRS, identity = 2713))
public class DiceNPCBase extends Npc implements NonPlayableCharacter {

    private static DiceNPCBase instance = null;

    private final AdjustableNumber<Integer> coins;
    private final ActionInvoker actionInvoker;
    private Player player;
    private final List<GameItem> playersWager;
//    private final AdjustableNumber<Integer> coinsWager;

    private Timer wageTimer;

    private DiceNPCBase(int id, int type) {
        super(-1, type);
        this.actionInvoker = new ActionInvoker();
        this.coins = new AdjustableInteger(500000000);
        this.playersWager = new ArrayList<>();
    }

    public static DiceNPCBase getInstance(int index) {
        if (instance == null)
            instance = new DiceNPCBase(index, 2713);
        return instance;
    }

    private void startWagerTimer() {
        final Timer timer = new Timer();
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                cancelWager();
            }
        };
        timer.schedule(task, 60000);
        this.setWageTimer(timer);
    }

    private boolean stopWagerTimer() {
        if (wageTimer != null) {
            wageTimer.cancel();
            return true;
        }
        return false;
    }

    private void cancelWager() {
        if (wageTimer != null) {
            wageTimer.cancel();
            if (!playersWager.isEmpty()) {
                playersWager.stream().forEach(item -> player.addItemToInventory(item.getId(), item.getAmount()));
                sendMessage(getPlayer().getPlayerName() + " has forfeit and I am now accepting new wagers!");
                this.reset();
            }
        }
    }

    private void reset() {
        playersWager.clear();
        this.setWageTimer(null);
        this.setPlayer(null);
    }

    private void sendPlayDialog(Player player) {
        player.setDialogueChain(new DialogueChain().npc(
                this.getDefinition(),
                DialogueHandler.FacialAnimation.DEFAULT,
                "Would you like to play some 55x2?",
                "I'm currently taking wagers up to " + this.formatValue(this.getMaxBid()),
                "and I do accept Items!")
                .option((p, option) -> {
                    if (option == 1) {
                        this.setPlayer(player);
                        player.getPA().closeInterfaces(true);
                        player.setDialogueChain(new DialogueChain().npc(
                                this.getDefinition(),
                                DialogueHandler.FacialAnimation.DEFAULT,
                                "I'll give you 1 minute",
                                "to give me your wager."));
                        this.startWagerTimer();
                    } else {
                        player.getPA().closeInterfaces(true);
                    }
                }, "Play 55x2", "Yes", "No")).start(player);
    }

    private int convertWinningsToTokens(int winnings) {
        if (winnings >= 1000000)
            return winnings / 1000000;
        return 0;
    }

    private void roll() {
        int roll = Misc.random(1, 100);
        int maxValue = roll - 55;
        int finalRoll = roll;
        if(maxValue > 0) {
            finalRoll -= Misc.random(1,20);
        }
        NpcHandler.getNpcByNpcId(this.getId()).requestAnimation(11000);
        NpcHandler.getNpcByNpcId(this.getId()).gfx0(2000);
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

            }
        }, 1000);
        announceFinalRoll(finalRoll);
    }

    private void announceFinalRoll(int finalRoll) {
        if (finalRoll > 55) {
            NpcHandler.getNpcByNpcId(getId()).forceChat("It's a " + finalRoll + "! We have a winner!");
            payout();
        } else {
            NpcHandler.getNpcByNpcId(getId()).forceChat("It's a " + finalRoll + "! Better luck next time!");
            collect();
        }
    }

    private void collect() {
        playersWager.forEach(item -> this.addItemToInventory(item.getId(), item.getAmount()));
        this.reset();
    }

    private void payout() {
        playersWager.stream().filter(item->item.getId() != 995).forEach(item -> player.addItemToInventory(item.getId(), item.getAmount()));
        final int winnings = playersWager.stream().mapToInt(item->ItemDefinition.getDefinitions()[item.getId()].price * item.getAmount()).sum();

        if (this.convertWinningsToTokens(winnings) > 0) {
            player.addItemToInventory(13204, this.convertWinningsToTokens(winnings * 2));
            this.removeItemFromInventory(995, winnings);
        } else {
            player.addItemToInventory(995, winnings);
            this.removeItemFromInventory(995, winnings);
        }
        this.reset();
    }

    private void sendPlayEarlyDialog() {
        player.setDialogueChain(new DialogueChain().npc(
                this.getDefinition(),
                DialogueHandler.FacialAnimation.DEFAULT,
                "Are you ready to play? Your current wager is ",
                this.formatValue(this.getCurrentWager()))
                .option((p, option) -> {
                    if (option == 1) {
                        if (!playersWager.isEmpty()) {
                            this.stopWagerTimer();
                            player.getPA().closeInterfaces(true);
                            this.roll();
                        } else {
                            this.cancelWager();
                            player.setDialogueChain(new DialogueChain().npc(
                                    this.getDefinition(),
                                    DialogueHandler.FacialAnimation.DEFAULT,
                                    "You've not placed a wager."));
                            player.getPA().closeInterfaces(true);
                        }
                    } else {
                        player.getPA().closeInterfaces(true);
                    }
                }, "Wager of: " + this.formatValue(this.getCurrentWager()), "Yes", "No")).start(player);
    }

    private void sendBusyDialog(Player player) {
        player.setDialogueChain(new DialogueChain().statement("He appears to be already playing with someone else.")).start(player);
    }

    public void talkTo(Player player) {
        if (this.getPlayer() == null) {
            this.sendPlayDialog(player);
        } else if (this.getPlayer() == player) {
            this.sendPlayEarlyDialog();
        } else {
            this.sendBusyDialog(player);
        }
    }

    public void announce() {
        if (player == null) {
            this.sendMessage("I am now accepting trades! Use the item(s) you wish to wager on me!");
        }
    }

    public boolean acceptTrade(int itemId, int amount, Player player) {
        if (this.player != null) {
            if (this.player == player) {
                if (!ItemDefinition.getDefinitions()[itemId].untradeableOsrsEco) {
                    if (this.convertToCoins(itemId, amount) && player.removeItemFromInventory(itemId, amount)) {
                        this.playersWager.add(new GameItem(itemId, amount));
                        return true;
                    }
                } else {
                    this.sendMessage("I can not accept untradable items.");
                }
            } else {
                this.sendMessage("I'm currently dicing with " + this.player.getPlayerName() + " please come back in a bit.");
            }
        } else {
            this.talkTo(player);
        }
        return false;
    }

    private boolean convertToCoins(int itemId, int amount) {
        final int value = ItemDefinition.getDefinitions()[itemId].price * amount;
            if (value <= this.getMaxBid()) {
                return true;
            } else {
                this.sendMessage("I can't accept an offer that high! My current max trade is: " + this.formatValue(this.getMaxBid()) + " GP.");
            }
        return false;
    }

    public int getCurrentWager() {
        if (!playersWager.isEmpty())
            return playersWager.stream().mapToInt(item -> ItemDefinition.getDefinitions()[item.getId()].price * item.getAmount()).sum();
        return 0;
    }

    public String formatValue(int value) {
        return NumberFormat.getInstance().format(value) + " GP";
    }

    public int getMaxBid() {
        return coins.value() * 2;
    }

//    public int getMaxTrade() {
//        return Integer.MAX_VALUE - coins.value();
//    }

    @Override
    public int getId() {
        return 2713;
    }

    @Override
    public boolean addItemToInventory(int id, int amt) {
            final int value = ItemDefinition.getDefinitions()[id].price * amt;
            coins.add(value);
            return true;
    }

    @Override
    public boolean removeItemFromInventory(int id, int amt) {
        if(coins.value() > amt) {
            coins.subtract(amt);
            return true;
        }
        return false;
    }

    @Override
    public boolean pickupItem(int i, int i1) {
        return false;
    }

    @Override
    public void sendMessage(String s) {
        NpcHandler.getNpcByNpcId(this.getId()).forceChat(s);
    }

    @Override
    public void receiveMessage(String s) {

    }

    @Override
    public ActionInvoker getActionInvoker() {
        return actionInvoker;
    }

    @Override
    public boolean moveTo(Location location) {
        return false;
    }

    @Override
    public Npc copy(int index) {
        return getInstance(index);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    public void setWageTimer(Timer wageTimer) {
        this.wageTimer = wageTimer;
    }
}

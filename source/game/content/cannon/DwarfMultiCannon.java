package game.content.cannon;

import com.google.common.collect.ImmutableSet;
import core.Server;
import core.ServerConstants;
import game.content.combat.Combat;
import game.content.combat.vsnpc.CombatNpc;
import game.content.minigame.Minigame;
import game.content.minigame.MinigameArea;
import game.content.minigame.MinigameAreaKey;
import game.item.ItemAssistant;
import game.npc.Npc;
import game.npc.pet.PetData;
import game.object.custom.Object;
import game.player.Area;
import game.player.Player;
import game.player.PlayerHandler;
import game.player.event.CycleEvent;
import game.player.event.CycleEventContainer;
import game.player.event.CycleEventHandler;
import game.position.Position;
import game.position.PositionUtils;
import utility.Misc;

import java.util.HashSet;
import java.util.Set;

/**
 * @author relex lawl
 * @author jonny
 */
public final class DwarfMultiCannon extends Object {
	
	private static final Set<DwarfMultiCannon> cannons = new HashSet<>();

	private String ownerName;

	private final Set<MinigameAreaKey> PROHIBITED_MINIGAME_AREAS = ImmutableSet.of(
			MinigameAreaKey.ZULRAH);

	public DwarfMultiCannon(Player player, String ownerName, Position position, int objectId) {
		super(objectId, position.getX(), position.getY(), position.getZ(), 0, 10, -1, -1, false);
		this.player = player;
		setOwnerName(ownerName);
	}
	
	private final Player player;
	
	private int cannonballs;
	
	private boolean cancelSetup;
	
	private Part part = Part.BASE;
	
	private Direction direction = Direction.NORTH;

	public Player getPlayerOwner() {
		return this.player;
	}

	public void setup() {
		if (getPlayerOwner() == null)
			return;
		
		if (restrictedArea(getPlayerOwner())) {
			return;
		}
		if(Area.inRevs(player)) {
			player.getPA().sendMessage("You cannot setup the cannon here!");
			return;
		}
		
		if (player.getDwarfMultiCannon() != null) {
			player.getPA().sendMessage("You already have a cannon set up in coordinates " + getPlayerOwner().getDwarfMultiCannon().getPosition());
			return;
		}
		if(Area.inUber(player)) {
			player.getPA().sendMessage("You cannot setup the cannon here!");
			return;
		}
		if (Area.inGodWarsDungeon(player)) {

			player.getPA().sendMessage("You cannot setup the cannon here!");
			return;
		}
		if (player.isMoving() || cancelSetup) {
			return;
		}
		if(Area.inVorkath(player)){
			player.getPA().sendMessage("You cannot setup the cannon here!");
			return;
		}
		if (Area.inGodWarsDungeon(player)) {

			player.getPA().sendMessage("You cannot setup the cannon here!");
			return;
		}
		Minigame minigame = player.getMinigame();
        
		if (minigame != null) {
			MinigameArea area = minigame.getAreaOrNull(player);

			if (area != null) {
				if (PROHIBITED_MINIGAME_AREAS.contains(area.getKey())) {
					player.getPA().sendMessage("The minigame you're in does not permit cannon setup here.");
					return;
				}
			}
		}
		player.setDwarfMultiCannon(this);

		player.doingActionEvent(9);

		setupPart();

		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			int timer = 2;

			@Override
			public void execute(CycleEventContainer container) {
				if (timer == 0) {
					Part nextPart = part != Part.FURNACE ? Part.values()[part.ordinal() + 1] : Part.FURNACE;;
					if (player.isMoving()
							|| cancelSetup
							|| !ItemAssistant.hasItemInInventory(player, nextPart.item)) {
						container.stop();
						return;
					}
					part = nextPart;
					if (!setupPart()) {
						if (part == Part.FURNACE) {
							if (ItemAssistant.hasItemInInventory(player, CANNONBALL)) {
								final int inventoryAmount = ItemAssistant.getItemAmount(player, CANNONBALL);
								cannonballs = inventoryAmount > 30 ? 30 : inventoryAmount;
								player.startAnimation(827);
								ItemAssistant.deleteItemFromInventory(player, CANNONBALL, cannonballs);
								startRotation();
							}
						}
						container.stop();
						return;
					}
					timer = 3;
				}
				timer--;
			}

			@Override
			public void stop() {

			}
		}, 1);
	}
	
	public void drop(Player player) {
		
		if (!cannons.contains(this)) {
			return;
		}
		
		for (Part p : Part.values()) {
			if (p.ordinal() <= part.ordinal()) {
				Server.itemHandler.createGroundItem(player, p.item, objectX, objectY, height, 1, false, 0, true, player.getPlayerName(),
						player.getPlayerName(), player.addressIp, player.addressUid, "Cannon on ground");
			}
		}
		
		if (cannonballs > 0) {
			Server.itemHandler.createGroundItem(player, CANNONBALL, objectX, objectY, height, cannonballs, false, 0, true, player.getPlayerName(),
					player.getPlayerName(), player.addressIp, player.addressUid, "Cannonballs on ground");
		}
		
		cannons.remove(this);

		Server.objectManager.removeObject(this);

		getPlayerOwner().setDwarfMultiCannon(null);
		if (player != null) {
			player.setDwarfMultiCannon(null);
		}
	}
	
	public void pickup() {
		cancelSetup = true;
		int slotsNeeded = part.ordinal() + 1 + (cannonballs > 0 && !ItemAssistant.hasItemInInventory(getPlayerOwner(), CANNONBALL) ? 1 : 0);
		if (ItemAssistant.getFreeInventorySlots(getPlayerOwner()) >= slotsNeeded) {
			getPlayerOwner().startAnimation(827);
			Server.objectManager.removeObject(this);
			for (Part part : Part.values()) {
				if (part.ordinal() <= this.part.ordinal())
					ItemAssistant.addItemToInventoryOrDrop(player, part.item, 1);
			}
			ItemAssistant.addItemToInventoryOrDrop(player, CANNONBALL, cannonballs);

			cannons.remove(this);
			getPlayerOwner().setDwarfMultiCannon(null);
		} else {
			getPlayerOwner().getPA().sendMessage("Not enough space in your inventory.");
		}
	}
	
	private boolean setupPart() {
		ItemAssistant.deleteItemFromInventory(player, part.item, 1);

		getPlayerOwner().startAnimation(827);
		if (part == Part.BASE) {
			Server.objectManager.addObject(this);
			if (!cannons.contains(this))
				cannons.add(this);
		}
		if (part != null) {
			transform(part.objectId);
		}
		return part != Part.FURNACE;
	}
	
	private void startRotation() {
		canRotate();
		if (!canRotate())
			return;
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (!canRotate())
					container.stop();
			}

			@Override
			public void stop() {
			}
		}, 1);
	}
	
	private boolean canRotate() {
		if(!cannons.contains(this)) {
			return false;
		}
		if (cannonballs <= 0) {
			getPlayerOwner().getPA().sendMessage("Your cannon has run out of ammo!");
			return false;
		}
		direction = direction.ordinal() == (Direction.values().length - 1) ?
				Direction.values()[0] : Direction.values()[direction.ordinal() + 1];

		for(Player player : PlayerHandler.getPlayers()) {
			if (player != null && PositionUtils.isWithinDistance(player.getPosition(), new Position(objectX, objectY))) {
				player.getPA().objectAnimation(objectX, objectY, 10, 0, direction.animation);
			}
		}
		shoot(direction);
		return true;
	}
	
	private void shoot(Direction direction) {
		if (getPlayerOwner() == null || getPlayerOwner().getDwarfMultiCannon() != this)
			return;
		Npc toShoot = null;
		int myX = objectX, myY = objectY;
		
		for (Npc mob : getPlayerOwner().getLocalNpcs()) {
			if ( getPlayerOwner() == null || getPlayerOwner().getDwarfMultiCannon() != this)
				return;
			if (mob == null)
				continue;
			if(!PositionUtils.isWithinDistance(mob.getPosition(), getPosition(), 12))
				continue;
			if (getPlayerOwner() != null && !Area.inMulti(mob.getX(), mob.getY())
					&& getPlayerOwner().getNpcIndexAttackingPlayer() != mob.npcIndex && getPlayerOwner().getNpcIndexAttackingPlayer() > 0)
				continue;
			boolean isPet = false;
			for (int i = 0; i < PetData.petData.length; i++) {
				if (PetData.petData[i][0] == mob.npcType) {
					isPet = true;
					break;
				}
			}
			
			if(isPet) {
				continue;
			}
			
			int theirX = mob.getX(), theirY = mob.getY();

			switch (direction) {
			case NORTH:
				if(theirY > myY && theirX >= myX - 1 && theirX <= myX + 1)
					toShoot = mob;
				break;
			case NORTH_EAST:
				if(theirX >= myX + 1 && theirY >= myY + 1)
					toShoot = mob;
				break;
			case EAST:
				if(theirX > myX && theirY >= myY - 1 && theirY <= myY + 1)
					toShoot = mob;
				break;
			case SOUTH_EAST:
				if(theirY <= myY - 1 && theirX >= myX + 1)
					toShoot = mob;
				break;
			case SOUTH:
				if(theirY < myY && theirX >= myX - 1 && theirX <= myX + 1)
					toShoot = mob;
				break;
			case SOUTH_WEST:
				if(theirX <= myX - 1 && theirY <= myY - 1)
					toShoot = mob;
				break;
			case WEST:
				if(theirX < myX && theirY >= myY - 1 && theirY <= myY + 1)
					toShoot = mob;
				break;
			case NORTH_WEST:
				if(theirX <= myX - 1 && theirY >= myY + 1)
					toShoot = mob;
				break;
			}
		}
		if (toShoot != null) {
			cannonballs--;

			player.getPA().createPlayersProjectile(new Position(getPosition().getX() + 1, getPosition().getY() + 1, getPosition().getZ()), toShoot.getPosition(), 50, 80, CANNON_BALL_GFX, 35, 15, toShoot.npcIndex + 1, 0, 0);
			toShoot.setKillerId(player.getPlayerId());
			toShoot.underAttackBy = player.getPlayerId();
			toShoot.underAttack = true;
			int damage = Misc.random(30);
			Combat.addCombatExperience(player, ServerConstants.RANGED_ICON, damage, true);
			CombatNpc.applyHitSplatOnNpc(player, toShoot, damage, ServerConstants.NORMAL_HITSPLAT_COLOUR, 4, 1);

		}
	}
	
	@Override
	public boolean hasMenuAction(Player player, int option) {
		if (player.getPlayerName().equals(getOwnerName())) {
			if (part != Part.FURNACE) {
				pickup();
				return true;
			}
			switch (option) {
			case 1:
				if (cannonballs == 30) {
					player.getPA().sendMessage("Your cannon is already loaded");
				} else if (cannonballs > 0) {
					final int inventoryAmount = ItemAssistant.getItemAmount(player, CANNONBALL);
					int amount = 30 - cannonballs;
					if (amount > inventoryAmount)
						amount = inventoryAmount;
					cannonballs += amount;
					player.getPA().sendMessage("You have added "+amount+" cannonballs to your cannon.");
					getPlayerOwner().startAnimation(827);
					ItemAssistant.deleteItemFromInventory(player, CANNONBALL, amount);
				} else {
					if (ItemAssistant.hasItemInInventory(player, CANNONBALL)) {
						final int inventoryAmount = ItemAssistant.getItemAmount(player, CANNONBALL);
						cannonballs = inventoryAmount > 30 ? 30 : inventoryAmount;
						player.getPA().sendMessage("You have added 30 cannonballs to your cannon.");
						getPlayerOwner().startAnimation(827);
						ItemAssistant.deleteItemFromInventory(player, CANNONBALL, cannonballs);
						startRotation();
					} else {
						player.getPA().sendMessage("You need cannonballs to shoot your cannon!");
					}
				}
				break;
			case 2:
				pickup();
				break;
			case 3:
				if(cannonballs == 0) {
					player.getPA().sendMessage("Your cannon is already empty.");
					return true;
				}
				int slotsNeeded = cannonballs > 0 && !ItemAssistant.hasItemInInventory(getPlayerOwner(), CANNONBALL) ? 1 : 0;
				if (ItemAssistant.getFreeInventorySlots(getPlayerOwner()) >= slotsNeeded) {
					getPlayerOwner().startAnimation(827);
					ItemAssistant.addItem(player, CANNONBALL, cannonballs);
					cannonballs = 0;
					player.getPA().sendMessage("You have emptied your cannon.");
				} else {
					getPlayerOwner().getPA().sendMessage("Not enough space in your inventory.");
				}
				break;
			}
		} else {
			player.getPA().sendMessage("This is not your cannon! This cannon belongs to <col=FF0000>" + getOwnerName());
		}
		return true;
	}
	
	public static boolean restrictedArea(Player player) {

		if (Area.inBank(player) || Area.isWithinVarrock(player.getPosition()) || Area.isWithinEdgeville(player.getPosition())) {
			player.getPA().sendMessage("You cannot setup a cannon here!");
			return true;
		}
		
		for (DwarfMultiCannon cannon : cannons) {
			if (cannon.objectX == player.getPosition().getX() && cannon.objectY == player.getPosition().getY()) {
				player.getPA().sendMessage("You cannot place your cannon on top of another cannon!");
				return true;
			}
		}
		
		return false;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	private enum Part {
		BASE(7, 6),
		
		STAND(8, 8),
		
		BARRELS(9, 10),
		
		FURNACE(6, 12);
		
		private Part(int objectId, int item) {
			this.objectId = objectId;
			this.item = item;
		}
		
		private final int objectId;
		
		private final int item;
	}

	private enum Direction {
		NORTH(515),
		
		NORTH_EAST(516),
		
		EAST(517),
		
		SOUTH_EAST(518),
		
		SOUTH(519),
		
		SOUTH_WEST(520),
		
		WEST(521),
		
		NORTH_WEST(514);
		
		private Direction(int animation) {
			this.animation = animation;
		}
		
		private int animation;
	}

 	
	@Override
	public int hashCode() {
		return -1;
	}
	
	private static final int CANNON_BALL_GFX = 53;
	
	private static final int CANNONBALL = 2;
}

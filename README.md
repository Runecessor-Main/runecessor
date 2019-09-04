# ZinfernOS
###### RuneScape PrivateServer Emulator
### Credits

* ZinfernOS
* Owain
* Jason
* Dexter Morgan

# How to setup SQL
There are 3 __schemas__ (databases) that are associated with ZinfernOS. Each of the
schemas have __properties__ associated to them that are loaded on server initialization. 
The properties are the user, password, etcetera. The following are schemas with their
associated properties file locations. These files are not committed and should never be.

* ZinfernOS_osrs_eco -> data/osrs/eco/sql/hikari.properties
* ZinfernOS_osrs_pvp -> data/osrs/pvp/sql/hikari.properties
* ZinfernOS_pre-eoc -> data/pre-eoc/sql/hikari.properties

The following is an example for the schema 'ZinfernOS_osrs_eco'
```properties
dataSource.user=root 
dataSource.password=MY_PASSWORD_TO_USER_ROOT
dataSource.databaseName=ZinfernOS_osrs_eco
dataSourceClassName=com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource
dataSource.url=jdbc:mysql://localhost:3306/ZinfernOS_osrs_eco
```

# Best Practices

## Interacting with SQL Server
It is possible to interact with various SQL schemas and individual tables using the __SQLNetwork__ class.
A __SQLNetworkTransaction__ is the class you must implement to interact with the SQL server. These transactions
must be submitted to the network using __SQLNetwork#submit__. __SQLNetwork__ is not __thread-safe__ so it would be 
advised that any objects modified on the game thread not be modified in the SQL thread during the processing of
the transaction.

The following is an example of how to retrieve a String value from a table called 'test'. We wait for the result by submitting
a __SQLNetworkTransactionFutureCycleEvent__ to the games __CycleEventHandler__ so that the result be returned on the game-thread
for future modifications/retrieval. 

```java
class Example {
	
    // create a future transaction that returns the value String by selecting from the table
    // test where the id of that row is 1337. 
    SQLNetworkTransactionFuture<String> future = new SQLNetworkTransactionFuture<String>() {
    	
        @Override
        public String request(Connection connection) throws SQLException {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM test WHERE id=?;")) {
                statement.setInt(1, 1337);

                try (ResultSet results = statement.executeQuery()) {
                    if (results.next()) {
                        return results.getString("value");
                    }
                }
            }
            return null;
        }
    };
    
    // create event that will quit if it doesnt complete in 10 cycles or 6 seconds
    SQLNetworkTransactionFutureCycleEvent<String> event = new SQLNetworkTransactionFutureCycleEvent<>(future, 10);

    // submit the future transaction to the sql network
    Server.getSqlNetwork().submit(future);

    // retrieve an instance of the container added to the event handler
    CycleEventContainer<Object> container = CycleEventHandler.getSingleton().addEvent(new Object(), event, 1);

    // tell the container that once the event stops print out the result
    container.addStopListener(() -> {
        String result = event.getResult();

        System.out.println("Result: " + result + ", " + event.isCompletedWithErrors());
    });
	
}
```


## Attributes
Every entity contains a mapping of attributes to their respective __AttributeKey__. This allows us to easily create temporary session related attributes 
without adding an additional attribute to the respective entity class. It is however also possible to create __PermanentAttributeKey__ variables
that are saved to the players account file.

```
// creates a key that will not be saved to the player file, only used per session
public static final AttributeKey<String> TRANSIENT_KEY_NAME = new TransientAttributeKey("this is only temporary");

// creates a key that will be saved to the player file, the annotation is required and it must be a PermanentAttributeKey
@PermanentAttributeKeyComponent
public static final AttributeKey<String> PERMANENT_KEY_NAME = new PermanentAttributeKey("permanent", "key-name-for-file");

//example entity instance
Entity entity = ...;

// inserts the new value for the key, will overwrite if existing.
entity.getAttributeMap().put(TRANSIENT_KEY_NAME, "new name");

// retrieves the value for the key, or the default value if key doesnt exist.
String valueForKey = entity.getAttributeMap().getOrDefault(TRANSIENT_KEY_NAME, "jason");
```

## Packet Structure
Packets are made up of two attributes, a __header__ and a __payload__. The header is comprised of two pieces of information,
the __opcode__ and the __length__ of the packet. The payload is just a 2 dimensional __byte__ array. 
#### Opcode
* The opcode is the identification value of the packet that makes it unique from every other packet. Their cannot be two packets
with the same id. The size of the opcode is read as 1 unsigned byte, with a value range of 0 to 255.
#### Length
* The length is arguably the most confusing aspect of the packet, it can either be -2, -1, 0, or 1+. The length is not sent
if the packet has a size of 0+. This is because the receiver of the packet is aware of the predefined fixed size. 
    * When the length is __-2__, the packet can send a payload of size 0 to 65535 (read as unsigned short).
    * When the length is __-1__, the packet can send a payload of size 0 to 255 (read as unsigned byte).
    * When the length is __0__, the packet payload has no bytes to be read. 
    * When the length is __1+__, the packet has a fixed payload size that must match.
    
## Creating a 'variable' Packet
Lets say for example we want to create a packet on the server end that sends a single String to the Client that can have
a size of upwards of 512 characters. Because the Client expects a payload size that is unpredictable, we need to indicate
to the Client that the size is either -1 or -2. Since the size can be upwards of 512 bytes, we need to
use -2, also known as __variable short__. -1 only supports upwards of 255 bytes, which is known as a __variable byte__. 

1. We need to find a unused packet, lets take packet 40 for example. At the time of writing this, packet 40 doesn't exist
so we can use this.
2. We need to create a Packet in PlayerAssistant.
3. We need to change the value of packet 40 from 0 to -2 to indicate that the client should expect a short
   for the length of the packet.
```java
class PlayerAssistant {
	
    public void sendPacket40(String message) {
    	if (player == null || player.disconnected || player.getEntityType() == EntityType.PLAYER_BOT) {
    		return;
    	}
    	Stream output = player.getOutputStream();
    	
    	if (output == null) {
    		return;
    	}
    	// The following indicates that we want packet 40 to have a maximum size of 65535 or unsigned short
    	// This is the header, and the length will be sent to the client.
    	// If we only wanted to send a maximum size of 255, we would say output.createFrameVarSize(40);
    	output.createFrameVarSizeWord(40); 
    	
    	// Writes the String to the output stream
    	// This is a part of the payload that hasnt been constructed
    	output.writeString(message);
    	
    	// The following indicates that we are finished with the structure of packet 40
    	// This constructs the packet payload
    	// If we only sent a byte, it would be output.endFrameVarSize();
    	output.endFrameVarSizeWord(); 
    }
    
}
```

## Creating a fix sized Packet
Lets say for example we want Packet 40 to now send a __byte__, __short__, __integer__ and __long__ from the Server to the Client. 
We now have to indicate the predefiend, or fixed, length of the packet in the Client. The size of the packet is going to be;
* 1 for a Byte because bytes can only hold 8 bits of data, which is 2 ^ 7 - 1.
* 2 for a Short because shorts are comprised of 16 bits, which is 2 ^ 15 - 1.
* 4 for a Integer because integers are comprised of 32 bits, which is 2 ^ 31 - 1.
* 8 for a Long because longs are comprised of 64 bits, which is 2 ^ 63 - 1.
* A total of __15__ bytes is what the __payload__ is comprised of. This is what you indicate as the size of the packet.

It is possible to read data as unsigned, but it is not possible to write it as unsigned. Unsigned data types typically
have a maximum size of 2 times that of their unsigned counter parts. For example; An unsigned byte can have a value of 0 to 255 where as
a signed byte has a value range of -128 to 127. It should be noted that its not possible to receive a negative number when reading
as unsigned as the minimum value of an unsigned is always 0.

```java
class PlayerAssistant { 
	
    public void sendPacket40(String message) {
        if (player == null || player.disconnected || player.getEntityType() == EntityType.PLAYER_BOT) {
            return;
        }
        Stream output = player.getOutputStream();
        
        if (output == null) {
            return;
        }
        // The following indicates that we want packet 40 to have a fixed size.
        output.createFrame(40); 
        
        output.writeByte(127); // 127 is sent as a byte, maximum value of signed.
        output.writeWord(32767); // 32767 is sent as a short, maximum value of signed short.
        output.writeDWord(2147483648); // 2147483648 is sent as a integer, maximum value of signed integer.
        output.writeQWord(Long.MAX_VALUE); // Long.MAX_VALUE is sent as a long, which is the max value.
        
        // The following indicates that we are finished with the structure of packet 40
        // This constructs the packet payload
        // If we only sent a byte, it would be output.endFrameVarSize();
        output.endFrame();
        
        // This indicates that we want to write the packet over the network, this is required for fixed length packets.
        player.flushOutStream(); 
    }
    
}
```

## Changing Packet Size
Changing the size of a packet to -2, -1, 0, or 1+ is the easiest part. If sending a packet to the Client from the Server, find the size
array in __SizeConstants__. If sending a packet to the Server from the Client, find the size array in the __ServerConstants__. Now that
you have the array, lets assume the Packet opcode is 40 and we want to change it to 15 to indicate a fixed length packet.
```java
class SizeConsants {
	
	int[] packetSizes = {
            0, 0, 0, 0, 6, 0, 0, 0, 4, 1, 
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
            0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 
            0, 0, 0, 0, -2, 4, 3, 0, 0, 0, 
            0, 0, 0, 0, 4, 0, 0, 6, 0, 0, 
          //^-- This is the index 40, for packet 40.
	};
	
}
```

To indicate the size is 15 we simply need to change __0__ to __15__ which is the amount of bytes in the packet 40.

## Dialogue
It is now very easy to create dialogue using something called a __DialogueChain__. A DialogueChain
keeps an ordered list of all __DialogueLink__ objects. Each DialogueLink can have a __Dialogue__ and any
number of __DialogueListener__ objects. This allows for any number of scenarios for dialogue.
```
DialogueChain chain = new DialogueChain().player(FacialAnimation.DEFAULT, "Hello jason")
                                         .player(FacialAnimation.DEFAULT, "Need some more coffee?")
                                         .option((p, option) -> {
                                             // give coffee if option == 1
                                         }, "title", "Yes I need more coffee", "Nope!");
                                         
player.setDialogueChain(chain).start(player);                            
```

## Extending Npc Class Contract
When extending the Npc class, there is a contract you must follow if you want the Npc to be automatically loaded
to the CustomNpcMap on initialization. 
* The class that extends Npc is __required__ to implemented the constructor with the parameters (int, int) for (index, type).
* The class must be annotated with the annotation __CustomNpcComponent__.
* The class must override the Npc#copy(int) function and provide a new instance of this class.
```java
@CustomNpcComponent(id=1) // id of npc is 1
public class Man extends Npc {
	
	public Man(int index, int type) { // contains 2 parameters of type int.class
		super(index, type);
	}
	
	@Override
	public Npc copy(int index) { // returns a new instance with the given index and this type
		return new Man(index, super.npcType);
	}
	
}
```

## Npc Combat
All Entities have a function called __getCombatStrategy()__ that by default returns __null__ but when overridden
can change how an Entity performs in combat. At this time, as of April 26, 2018, this only affects Npcs but it is 
designed so that in the future we can make the switch when needed. Any NPC that has an EntityCombatStrategy will
 not use the default PI combat methods like loadSpell(int) to perform attacks.
```
class MyNpc extends Npc {

    @Override
    public EntityCombatStrategy getCombatStrategyOrNull() {
        return new MyEntityCombatStrategy();
    }
    
    class MyEntityCombatStrategy implements EntityCombatStrategy {
    	
    	@Override
    	public EntityType getAttackerType() {
    	    return EntityType.NPC;
    	}
    
        @Override
    	public EntityType getDefenderType() {
    	    return EntityType.PLAYER;
    	}
    	
    	@Override
    	public boolean isCustomAttack() {
    	    return true; // must be overridden to use combat strategy customAttack
    	}
    	
    	@Override
    	public void onCustomAttack(Entity attacker, Entity defender) {
    	    // perform some custom attack
    	}
    }
}
```

## Minigames
Minigames are essential in providing time-consuming content for players. There are global minigames and there
are single player minigames. Minigames keep track of the players and npcs in the minigame as well as the 
__MinigameArea__ that the players are in. 
```
class MyMinigame extends Minigame {
    
    public MyMinigame() {
        super(MinigameKey.MY_MINIGAME);
        addArea(MinigameAreaKey.MY_MINIGAME_LOBBY_AREA, new MyMinigameArea());
    }
    
    @Override
    public void start() {
        // start the minigame
        // move players to another area
    }
    
    @Override
    public void end() {
        // end it
    }
    
    class MyMinigameLobbyArea extends MinigameLobbyArea {
    
        MyMinigameArea() {
            super(MinigameAreaKey.MY_MINIGAME_LOBBY_AREA);
        }
    }
}
```

## Item Containers
__ItemContainer__ objects are used to store and retrieve GameItem objects in a centralized manner. This allows 
for a write-once use-many time relationship. ItemContainer provide essential information and functionality such 
as the amount of items in the container, the next free slot if any, and the ability to add or delete of course.
```
// create a container that has a capacity of 10, always stacks items, and always denotes items
// before inserting.
ItemContainer myContainer = new ItemContainer(10, 
                                    ItemContainerStackPolicy.STACKABLE,
                                    ItemContainerNotePolicy.DENOTE);

int added = myContainer.add(new GameItem(4151, 1));

myContainer.forEach((index, item) -> ...);

int removed = myContainer.remove(new GameItem(4151, 1));
```

## Item Interaction
__ItemInteraction__ class handles all functionality of an item. __ItemInteractionComponent__ allows us to define an Integer array of item ids and also specify __GameType__. 
In the below example, 4151 and 4153 are in the _int[]_ array representing the items. _type_ represents the game type the interaction is available on. 
```
@ItemInteractionComponent(
		identities = {@GameTypeIdentity(type = GameType.PRE_EOC, identity = {4151, 4153}),})
public class MyItemInteraction extends ItemInteraction {

	@Override
	public boolean canEquip(Player player, int id, int slot) {
		return true;
	}
	@Override
	public void operate(Player player, int id) {

	}
}
```
__ItemInteraction#sendItemAction__ handles the right clicking option. _type_ being the index of the option.
```
@Override
public boolean sendItemAction(Player player, int id, int type) {
    return false;
}
```
__ItemInteraction#useItem__ handles using item on item.
```
@Override
public void useItem(Player player, int id, int useWith) {

}
```

## Use FailSafeCycleEvent instead of CycleEvent
The class __FailSafeCycleEvent__ should be used when you can expect a certain event to only exist for only an absolute
maximum amount of cycles. This is just a defensive measure taken so that if some logic in an event is improper, the event
wont last forever. 

For example, say we want to message a player after 3 game ticks that they received some reward, we can set the fail safe to 
2 because this event should never execute more than once if we set the period length to 3. 

```java
class MyCycleEvent extends FailSafeCycleEvent<Entity> {
    
    public MyCycleEvent() {
        super(1); // this 1 means that after its executed once, it cannot be executed again.
    }
    
    @Override
    public void onSafe(CycleEventContainer<Entity> container) { 
        // perform some action
    }
    
    @Override
    public void onFail(CycleEventContainer<Entity> container) {
        // the event didnt finish as expected and failed, do something about it.
    }
    
}
```
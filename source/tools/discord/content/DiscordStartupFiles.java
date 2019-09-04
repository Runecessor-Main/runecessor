package tools.discord.content;

import tools.discord.DiscordConstants;
import tools.discord.api.DiscordBot;

public class DiscordStartupFiles {

	public static void discordStartupFiles() {
		if (DiscordBot.isDiscordBot(DiscordConstants.Runecessor_BOT)) {
			DiscordMessageSent.readFlaggedText();
		}
	}

}

package tools.multitool;

import core.ServerConstants;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class DonatorTokensScan {


	public static int tokensReceivedInEconomy;

	/**
	 * Say i received 500 tokens but i only spent 200 in shop, it means i sold 300. So add 300 to this variable.
	 */
	public static int playerTokensNotSpentInShopTotal;

	public static void scanDonatorSpendOrSoldAmount(int alphabetIndex) {
		RunecessorMultiTool.scanThreadOnline = true;
		final File folder = new File(ServerConstants.getCharacterLocationWithoutLastSlash());

		int size = RunecessorMultiTool.getTotalCharacters();
		for (final File fileEntry : folder.listFiles()) {
			if (alphabetIndex == -1) {
				if (!Character.isDigit(fileEntry.getName().charAt(0))) {
					break;
				}
			} else {
				if (!fileEntry.getName().startsWith(RunecessorMultiTool.ALPHABET[alphabetIndex])) {
					continue;
				}
			}
			try {
				BufferedReader file = new BufferedReader(new FileReader(ServerConstants.getCharacterLocationWithoutLastSlash() + "/" + fileEntry.getName()));
				String originalLine;
				int playerTokensUsedInShop = 0;
				while ((originalLine = file.readLine()) != null) {
					/*
					if (originalLine.contains("Username ="))
					{
						playerName = originalLine.substring(originalLine.indexOf("=") + 2);
					}
					*/
					if (originalLine.contains("donatorTokensRankUsed")) {
						int tokens = Integer.parseInt(originalLine.substring(originalLine.lastIndexOf("=") + 2));
						playerTokensUsedInShop += tokens;

					} else if (originalLine.contains("donatorTokensReceived")) {
						int tokensReceived = Integer.parseInt(originalLine.substring(originalLine.lastIndexOf("=") + 2));
						tokensReceivedInEconomy += tokensReceived;
						if (tokensReceived > 0 && playerTokensUsedInShop < tokensReceived) {
							playerTokensNotSpentInShopTotal += tokensReceived - playerTokensUsedInShop;
						}
						RunecessorMultiTool.totalScans++;
						if (RunecessorMultiTool.totalScans % 500 == 0) {
							RunecessorMultiTool.setStatusText("Accounts scanned: " + RunecessorMultiTool.totalScans + "/" + size);
						}

					}
				}
				file.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		RunecessorMultiTool.scanThreadsActive--;
	}

	public static void scanDonatorSpendOrSoldAmountEnd() {
		RunecessorMultiTool.updateOutputText("tokensReceivedInEconomy: " + tokensReceivedInEconomy, Color.BLACK, true);
		RunecessorMultiTool.updateOutputText("playerTokensNotSpentInShopTotal: " + playerTokensNotSpentInShopTotal, Color.BLACK, true);
		RunecessorMultiTool
				.updateOutputText(((double) playerTokensNotSpentInShopTotal / (double) tokensReceivedInEconomy) * 100 + "% of Donator tokens are sold.", Color.BLACK, true);
		RunecessorMultiTool.setStatusText("Completed.");
	}

}

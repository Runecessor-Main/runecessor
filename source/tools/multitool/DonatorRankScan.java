package tools.multitool;

import core.ServerConstants;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class DonatorRankScan {


	private static int donator = 0;

	private static int superDonator = 0;

	private static int extremeDonator = 0;

	private static int legendaryDonator = 0;

	private static int uber = 0;

	private static int immortal = 0;

	private static int ultimate = 0;

	private static int supreme = 0;

	private static int lucifer = 0;

	private static int omega = 0;

	public static void scanDonatorsAmounts(int alphabetIndex) {
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
				while ((originalLine = file.readLine()) != null) {
					if (originalLine.contains("donatorTokensRankUsed")) {
						int tokens = Integer.parseInt(originalLine.substring(originalLine.lastIndexOf("=") + 2));
						if (tokens >= 150_000) {
							omega++;
						} else if (tokens >= 100_000) {
							lucifer++;
						} else if (tokens >= 50_000) {
							supreme++;
						} else if (tokens >= 25000) {
							immortal++;
						} else if (tokens >= 12000) {
							uber++;
						} else if (tokens >= 6000) {
							ultimate++;
						} else if (tokens >= 3000) {
							legendaryDonator++;
						} else if (tokens >= 1000) {
							extremeDonator++;
						} else if (tokens >= 400) {
							superDonator++;
						} else if (tokens >= 150) {
							donator++;
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

	public static void scanDonatorAmountsEnd() {
		RunecessorMultiTool.updateOutputText("Omega: " + omega, Color.BLACK, true);
		RunecessorMultiTool.updateOutputText("Lucifer: " + lucifer, Color.BLACK, true);
		RunecessorMultiTool.updateOutputText("Supreme: " + supreme, Color.BLACK, true);
		RunecessorMultiTool.updateOutputText("Immortal: " + immortal, Color.BLACK, true);
		RunecessorMultiTool.updateOutputText("Uber: " + uber, Color.BLACK, true);
		RunecessorMultiTool.updateOutputText("Ultimate: " + ultimate, Color.BLACK, true);
		RunecessorMultiTool.updateOutputText("Legendary: " + legendaryDonator, Color.BLACK, true);
		RunecessorMultiTool.updateOutputText("Extreme: " + extremeDonator, Color.BLACK, true);
		RunecessorMultiTool.updateOutputText("Super: " + superDonator, Color.BLACK, true);
		RunecessorMultiTool.updateOutputText("Normal: " + donator, Color.BLACK, true);
		RunecessorMultiTool.setStatusText("Completed.");
	}
}

package game;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private String userName;
	private String emailAddress;
	public static List<HangManGame> games = new ArrayList<HangManGame>();

	public Player(String theName, String theEmail)
	{
		userName =theName;
		emailAddress = theEmail;
	}

	public void setGamesForPlayer(HangManGame game) {
		games.add(game);
	}

	public HangManGame GamesForPlayer() { //work on it...
		return gameID;
	}
	public String getEmail() {
		return emailAddress;
	}
	public String getUser() {
		return userName;
	}
}


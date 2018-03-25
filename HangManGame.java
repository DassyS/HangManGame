package game;

import java.util.*;
import java.io.BufferedReader;
import java.nio.charset.Charset;
// Paths, Files
import java.nio.file.Files;
import java.nio.file.Paths;

public class HangManGame {
	private int gameID;
	private String player;
	private int score;
	private String randomWord;
	private int numTries;
	static int tries;
	public static List<Player> players =new ArrayList <Player>();
	static HashSet<Character> lettersIN;
	static Scanner stdIn = new Scanner(System.in);
	static int ID=0;

	public void setID(int ID) {
		this.gameID = ID;
	}

	public int getID() {
		return gameID;
	}

	public void setWord(String word) {
		this.randomWord = word;
	}

	public String getWord() {
		return randomWord;
	}
	public void setPlayer(String name) {
		this.player = name;
	}

	public String getPlayer() {
		return player;
	}
	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}
	public void setTries(int tries) {
		this.numTries = tries;
	}

	public int getTries() {
		return numTries;
	}


	// this method read dictionary file, copy only letter words to list and return a random word from the list.
	public static String getRandomWord(String file){
		Random rand = new Random();
		List<String> data = new ArrayList<String>();
		try (BufferedReader fileIn = Files.newBufferedReader(
				Paths.get(file),
				Charset.defaultCharset()))
		{   
			while (fileIn.ready())
			{
				String s = fileIn.readLine();
				if (s!=null && s.chars().allMatch(Character::isLetter)&&(s.length()>4)){
					//System.out.print(s+"\n");
					data.add(s.toLowerCase());
				}

			}
		} // end try
		catch (Exception e)
		{
			System.out.println(e.getClass());
			System.out.println(e.getMessage());
		}
		int index = rand.nextInt(data.size());
		return data.get(index);
	}
	//this method prints a char array

	public static void printArray(char[]word)
	{
		for(char c: word){
			System.out.print(c);
		}
		System.out.print("\n");
	}
	//this method checks how many times a letter appears in a string and enters it in to the * array for the view of the player

	public static int findLetter(String word, char letter, char[] enteredLetters)
	{
		boolean flag=false;
		int index = word.indexOf(letter);
		if (lettersIN.contains(letter))
		{
			System.out.print("The letter "+letter+" was already entered, please try again\n");
		}
		else
		{
			lettersIN.add(letter);
			tries++;
			//System.out.print(tries);
			while (index >= 0) {
				flag=true;
				enteredLetters[index]=letter;
				//System.out.println(index);
				index = word.indexOf(letter, index + 1);
			}
			if (flag==false){
				System.out.println("The letter "+letter+" is not in the word");
			}
		}
		return tries;
	}
	//this method checks if the user guessed the word
	public static boolean checkWord(char[]enteredLetters)
	{
		for(char c: enteredLetters){
			if (c=='*'){
				return false;	
			}
		}
		return true;
	}
	private static int calculateScore(int tries, String word) {
		return (((word.length())/tries)*10);
	}

	private static int calculateMissed(String word,char[] enteredLetters) {
		int index=0;
		int missed=0;
		while (index >= 0) {
			enteredLetters[index]='*';
			index = word.indexOf('*', index + 1);
			missed--;
		}
		return missed;
	}

	public static HangManGame playGame(String user)
	{
		//set up new game

		HangManGame g = new HangManGame();
		ID++; //set game number
		g.setID(ID);
		g.setPlayer(user);
		String word=getRandomWord("words.txt"); //get random word from dict.
		//System.out.print(word+"\n");
		g.setWord(word);
		char[] enteredLetters = new char[word.length()]; //create array for hidden word.
		//start game
		System.out.print("Hang man Game, number "+ g.getID()+ "\n");
		for (int i=0; i<word.length();i++){
			enteredLetters[i]='*';
		}
		printArray(enteredLetters); //prints string with * and guessed letters.
		boolean wordIsGuessed=false;
		int i=0;
		while (i<(word.length())&& (wordIsGuessed==false))
		{
			i++;
			char letter= (stdIn.next()).charAt(0); //get entered letter
			if (Character.isLetter(letter)) //check if letter char.
			{
				//System.out.print(letter);
				tries=findLetter(word,letter,enteredLetters); //look for index of letter in word. if exist then added to array. counts the tries.
				printArray(enteredLetters); //prints the array again after each guess.
				wordIsGuessed=checkWord(enteredLetters); //check if letter in the word. flag will turn true if all letters in words will get guessed.
				if (wordIsGuessed==true){
					break;
				}
			}
		}
	if (wordIsGuessed=true){
		g.setScore(calculateScore(tries, word));
		System.out.print("Congragulations! You have guessed the word: "+word+" in "+tries+" tries.\n Your score is: "+g.getScore()+"\n");
	}
	else{
		int missed=calculateMissed(word, enteredLetters);
		g.setScore(tries+missed);
		System.out.print("Sorry! not this time. The word was "+word+"and you had "+missed+ "misses. Your score is "+g.getScore()+"\n");
	}
	g.setTries(tries);
	return g;
}
public static void checkPlayer(String user, String email){
	//iterate through list of players, if player exist pull if not create player
	for (Player player:players){
		if (player.getUser()==user && player.getEmail()==email){
			System.out.print("Hello, "+user+" \n");
		}
		if (player.getUser()==user && player.getEmail()!=email){
			System.out.print("Email address is not correct.\n ");
		}
		if (player.getUser()!=user && player.getEmail()==email){
			System.out.print("User name is not correct\n ");
		}
		else{
			Player p = new Player(user, email);
			System.out.print("Hello, "+p.getUser());
		}
	}
}

public static void main(String[] args) {
	// TODO Auto-generated method stub
	int choice=0;
	while (choice!=1){
		System.out.print("Please enter a user name: ");
		String user= stdIn.next();
		System.out.print("Please enter an Email Address: ");
		String email= stdIn.next();
		checkPlayer(user,email);
		System.out.print("Welcome to hang-man-game, Please enter your chioce:\n"
				+ "1-exit\n"
				+ "2-list all my games\n"
				+ "3-start a new game\n");
		choice=stdIn.nextInt();
		if (choice==2){
			for (HangManGame game:Player.games){
				System.out.print(game.gameID+"\t"+game.score+"\n");
			}
		}
		if (choice==3){
			tries=0;
			lettersIN = new HashSet<Character>();
			HangManGame game=playGame(user);
			Player.games.add(game);
		}
	}

	stdIn.close();
}

}

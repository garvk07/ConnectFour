import java.util.Scanner;

/**
 * CS312 Assignment 10.
 *
 * On my honor, <Garv Kinariwala>, this programming assignment is my own work and I have
 * not shared my solution with any other student in the class.
 *
 *  email address:garvkinariwala@gmail.com
 *  UTEID:gnk289
 *  TA name:Charlotte White
 *  Number of slip days used on this assignment: 2
 *
 * Program that allows two people to play Connect Four.
 */


public class ConnectFour {

    // CS312 Students, add you constants here
	private static final int ROWS = 6;
	private static final int BOTTOM_ROW = 5;
	private static final int COLUMNS = 7;
	private static final int WINS = 4;
	private static final int TOTAL_TURNS = 42;

    public static void main(String[] args) {
        intro();
        Scanner key = new Scanner(System.in);
        String player1 = getPlayer1(key);
        String player2 = getPlayer2(key);
        char[][] board = new char[ROWS][COLUMNS];
        emptyBoard(board);
        displayBoard(key, board);
        playGame(player1, key, board, player2);
        key.close();
        // complete this method
        // Recall make and use one Scanner coonected to System.in
    }
    
    // CS312 Students, add your methods
    // show the intro
    public static void intro() {
        System.out.println("This program allows two people to play the");
        System.out.println("game of Connect four. Each player takes turns");
        System.out.println("dropping a checker in one of the open columns");
        System.out.println("on the board. The columns are numbered 1 to 7.");
        System.out.println("The first player to get four checkers in a row");
        System.out.println("horizontally, vertically, or diagonally wins");
        System.out.println("the game. If no player gets fours in a row and");
        System.out.println("and all spots are taken the game is a draw.");
        System.out.println("Player one's checkers will appear as r's and");
        System.out.println("player two's checkers will appear as b's.");
        System.out.println("Open spaces on the board will appear as .'s.\n");
    }

    //gets player one
    private static String getPlayer1(Scanner key) {
    	String player1 = "";
    	System.out.print("Player 1 enter your name: ");
    	player1 = key.nextLine();
    	System.out.println();
		return player1;
    }
    
    //gets player two
    private static String getPlayer2(Scanner key) {
    	System.out.print("Player 2 enter your name: ");
    	String player2 = "";
    	player2 = key.nextLine();
    	System.out.println();
		return player2;
    }

    //creates the board and displays it
    private static void displayBoard(Scanner key, char[][] board) {
    	System.out.print("Current Board");
    	System.out.println();
    	System.out.print("1 2 3 4 5 6 7  column numbers");
    	System.out.println();
    	for (int i = 0; i < board.length; i++) {
      		for (int j = 0; j < board[i].length; j++) {
      			System.out.print(board[i][j] + " ");
      		}
      	System.out.println();
    	}
    }
    
    //creates the final board after a winner
    private static void finalBoard(Scanner key, char[][] board) {
    	System.out.print("Final Board");
    	System.out.println();
    	System.out.print("1 2 3 4 5 6 7  column numbers");
    	System.out.println();
    	for (int i = 0; i < board.length; i++) {
      		for (int j = 0; j < board[i].length; j++) {
      			System.out.print(board[i][j] + " ");
      		}
      	System.out.println();
    	}
    }
    
    //creates an empty board with periods representing blank spaces
    private static void emptyBoard(char[][] board){
    	for (int i = 0; i < ROWS; i++) {
      		for (int j = 0; j < COLUMNS; j++) {
      			board[i][j] = '.';
      		}
    	}
    }
    
    //plays a complete game of connect 4, alternating turns, and will check for winner
    private static void playGame(String player1, Scanner key, char[][]board, String player2) {
    	int turn = 0;
    	while(turn < TOTAL_TURNS) {
        System.out.println('\n' + player1 + " it is your turn." + '\n' + "Your pieces are the r's.");
    	int checker = getValidInput(key, player1, board);
    	turn++;
    	int row = dropChecker(checker, board, turn);
    	if (checkWin1(board, row, checker - 1) || checkWin2(board, row, checker -  1)){
    		System.out.println(player1 + " wins!!" + '\n');
    		finalBoard(key, board);
    		return;
    	} if (!(turn == 42)){
    		displayBoard(key, board);
    	}
    	System.out.println('\n'+ player2 + " it is your turn." + '\n' + "Your pieces are the b's.");
    	checker = getValidInput(key, player2, board);
    	turn++; 
    	row = dropChecker(checker, board, turn);
    	if (checkWin1(board, row, checker - 1) || checkWin2(board, row, checker - 1)){
    		System.out.println(player2 + " wins!!" + '\n');
    		finalBoard(key, board);
    		return;
    	}if (!(turn == 42)){
    		displayBoard(key, board);
    	}
    	}
    	System.out.println("The game is a draw." + '\n');
    	finalBoard(key, board);
    }
     
    //drops the checker based on the current players input
    private static int dropChecker (int checker, char [][] board, int turn) {
    	int counter = 0;
    	for (int i = 0; i < ROWS; i++) {
    		if (board[i][checker - 1] != '.') {
    			counter++;
    		}
    	}if (turn % 2 != 0){
    		board[BOTTOM_ROW - counter][checker - 1] = 'r';
    	}else if(turn % 2 == 0) {
    		board[BOTTOM_ROW - counter][checker - 1] = 'b';
    	}
    	return BOTTOM_ROW - counter;
    }
    
    //gets a valid column input
    private static int getValidInput(Scanner key, String player1, char [][]board) {
    	String prompt = player1 + ", enter the column to drop your checker: ";
    	System.out.print(prompt);
    	int checker = getInt(key, prompt);
    	System.out.println();
    	while (rangeChecker(board, checker, key, prompt)) {
    		System.out.println(checker + " is not a valid column.");
    		checker = getValidInput(key, player1, board);
    	}
    	while (columnChecker(board, checker, key, prompt)) {
    		System.out.println(checker + " is not a legal column. That column is full");
    		checker = getValidInput(key, player1, board);
    	}
		return checker;
    }
    
    //makes sure that the column is not full or out of range
    private static boolean rangeChecker(char [][] board, int checker, Scanner key, String prompt) {
    	return (checker < 1 || checker > COLUMNS);
    }
    
    private static boolean columnChecker(char [][] board, int checker, Scanner key, String prompt) {
    	return (board[0][checker - 1] != '.');
    }
    
    //checks the vertical and horizontal win
    private static boolean checkWin1(char[][] board, int row, int column) {
    	int countTotal = 0;
    	char lastToken = board[row][column];
    	for (int i = 0; i < WINS && row + i <= BOTTOM_ROW; i++) {
    		if (board[row + i][column] == lastToken) {
    			countTotal++;
    		}
    	}if (countTotal == WINS) {
    		return true;
    	}
    	countTotal = 0;
    	for (int i = 0; i < WINS && column - i >= 0 && board[row][column - i] == lastToken; i++) {
    		countTotal++;
    	}
    	for (int i = 1; i < WINS && column + i <= ROWS && board[row][column + i] == lastToken; i++) {
    		countTotal++;
    	}if (countTotal >= WINS) {
    		return true;
    	}
    	return false;
    }
    
    //checks the diagonal win
    private static boolean checkWin2(char[][] board, int row, int column) {
    	int counts = 0;
    	char lastPlacement = board[row][column];
    	for (int i = 0; i < WINS && column - i >= 0 && row + i <= BOTTOM_ROW && 
    			board[row + i][column - i] == lastPlacement; i++) {
    		counts++;
    	}
    	for (int i = 1; i < WINS && column + i <= ROWS  && row - i >= 0 && 
    			board[row - i][column + i] == lastPlacement; i++) {
    		counts++;
    	}if (counts >= WINS) {
    		return true;
    	}
    	counts = 0;
    	for (int i = 0; i < WINS && column - i >= 0 && row - i >= 0 && 
    			board[row - i][column - i] == lastPlacement; i++) {
    		counts++;
    	}
    	for (int i = 1; i < WINS && column + i <= ROWS  && row + i <= BOTTOM_ROW && 
    			board[row + i][column + i] == lastPlacement; i++) {
    		counts++;
    	}if (counts >= WINS) {
    		return true;
    	}
    	return false;
    }

    // Prompt the user for an int. The String prompt will
    // be printed out. I expect key is connected to System.in.
    public static int getInt(Scanner key, String prompt) {
        while(!key.hasNextInt()) {
            String notAnInt = key.nextLine();
            System.out.println();
            System.out.println(notAnInt + " is not an integer.");
            System.out.print(prompt);
        }
        int result = key.nextInt();
        key.nextLine();
        return result;
    }
}
package tictactoe;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Game {
    enum HardnessLevel {
        EASY,
        MEDIUM,
        HARD
    }
    enum GameState {
        STARTED,
        ENDED,
        DRAW,
        X_WIN,
        O_WIN
    }
    private enum Opponents {
        AI_AI,
        AI_USER,
        USER_USER
    }
    protected HardnessLevel level;
    private Opponents opponent;
    protected GameState state;
    protected final char[][] board = new char[3][3];
    protected char currentPlayer = 'X';
    protected char otherPlayer = 'O';
    public void start() {
        showMenu();
        while (state != GameState.ENDED) {
            switch (opponent) {
                case AI_AI -> playAI_AI();
                case AI_USER -> playAI_user();
                case USER_USER -> playUser_User();
            }
            state = checkState();
            showMenu();
        }
    }
    private void showMenu() {
        Scanner scan = new Scanner(System.in);
        String command, opponents;
        while (state != GameState.STARTED) {
            System.out.print("Input command: ");
            command = scan.next();
            opponents = scan.nextLine().trim();
            if (command.equals("start")) {
                switch (opponents) {
                    case "easy easy" -> {
                        opponent = Opponents.AI_AI;
                        level = HardnessLevel.EASY;
                    }
                    case "user easy", "easy user" -> {
                        opponent = Opponents.AI_USER;
                        level = HardnessLevel.EASY;
                    }
                    case "user user" -> opponent = Opponents.USER_USER;
                    case "user medium", "medium user" -> {
                        opponent = Opponents.AI_USER;
                        level = HardnessLevel.MEDIUM;
                    }
                    case "medium medium" -> {
                        opponent = Opponents.AI_AI;
                        level = HardnessLevel.MEDIUM;
                    }
                    case "user hard", "hard user" -> {
                        opponent = Opponents.AI_USER;
                        level = HardnessLevel.HARD;
                    }
                    case "hard hard" -> {
                        opponent = Opponents.AI_AI;
                        level = HardnessLevel.HARD;
                    }
                    default -> {
                        System.out.println("Bad parameters!");
                        continue;
                    }
                }
                state = GameState.STARTED;
            } else if (command.equals("exit")) {
                state = GameState.ENDED;
                break;
            } else {
                System.out.println("Bad parameters!");
            }
        }
    }
    private void playAI_AI() {
        switch (level) {
            case EASY -> {
                Easy easyGame = new Easy();
                easyGame.playAI_AI();
            }
            case MEDIUM -> {
                Medium mediumGame = new Medium();
                mediumGame.playAI_AI();
            }
            case HARD -> {
                Hard hardGame = new Hard();
                hardGame.playAI_AI();
            }
            default -> System.out.println("Incorrect hardness level");
        }
    }
    private void playAI_user() {
        switch (level) {
            case EASY -> {
                Easy easyGame = new Easy();
                easyGame.playAI_User();
            }
            case MEDIUM -> {
                Medium mediumGame = new Medium();
                mediumGame.playAI_User();
            }
            case HARD -> {
                Hard hardGame = new Hard();
                hardGame.playAI_user();
            }
            default -> System.out.println("Incorrect hardness level");
        }
    }
    private void playUser_User() {
        prepareGame();
        while (state == GameState.STARTED) {
            displayBoard();
            makeUserMove();
            state = checkState();
        }
        printState();
    }
    protected void makeUserMove() {
        Scanner scan = new Scanner(System.in);
        int row, column;
        boolean done = false;
        while (!done) {
            try {
                System.out.print("Enter the coordinates: ");
                row = scan.nextInt();
                column = scan.nextInt();
                if (row < 1 || row > 3 || column < 1 || column > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue;
                }
                if (!isEmpty(row - 1, column - 1)) {
                    System.out.println("This cell is occupied! Choose another one!");
                    continue;
                }
                makeMove(row-1, column-1);
                done = true;
            } catch (InputMismatchException e) {
                System.out.println("You should enter numbers!");
                scan.nextLine();
            }
        }
    }
    protected void prepareGame() {
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++)
                board[i][j] = ' ';
    }
    protected void displayBoard() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.printf("%c ", board[i][j]);
            }
            System.out.printf("|%n");
        }
        System.out.println("---------");
    }
    protected boolean areEqual(char a, char b, char c) {
        return a == b && b == c && a != ' ';
    }
    protected boolean isEmpty(int row, int col) {
        return board[row][col] == ' ';
    }
    protected GameState checkState() {
        // horizontal
        for (int i = 0; i < 3; i++) {
            if (areEqual(board[i][0], board[i][1], board[i][2])) {
                return board[i][0] == 'X'? GameState.X_WIN : GameState.O_WIN;
            }
        }
        // vertical
        for (int i = 0; i < 3; i++) {
            if (areEqual(board[0][i], board[1][i], board[2][i])) {
                return board[0][i] == 'X'? GameState.X_WIN : GameState.O_WIN;
            }
        }
        // diagonals
        if (areEqual(board[0][0], board[1][1], board[2][2])) {
            return board[0][0] == 'X'? GameState.X_WIN : GameState.O_WIN;
        }
        if (areEqual(board[2][0], board[1][1], board[0][2])) {
            return board[2][0] == 'X'? GameState.X_WIN : GameState.O_WIN;
        }
        // check empty cells
        int empty = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (isEmpty(i,j)) empty++;
            }
        }
        if (empty == 0) return GameState.DRAW;
        else return GameState.STARTED;
    }
    protected void makeMove(int i, int j) {
        board[i][j] = currentPlayer;
        switchPlayers();
    }
    protected void switchPlayers() {
        if (currentPlayer == 'X') {
            currentPlayer = 'O';
            otherPlayer = 'X';
        }
        else {
            currentPlayer = 'X';
            otherPlayer = 'O';
        }
    }
    protected void printState() {
        switch (checkState()) {
            case O_WIN -> System.out.println("O wins");
            case X_WIN -> System.out.println("X wins");
            case DRAW -> System.out.println("Draw");
            default -> System.out.println("Unreachable state");
        }
    }
}

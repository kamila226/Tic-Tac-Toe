package tictactoe;
public class Hard extends Game {

    protected void playAI_user() {
        prepareGame();
        while(checkState() == GameState.STARTED) {
            makeHardMove();
            displayBoard();
            if (checkState() != GameState.STARTED) break;
            makeUserMove();
            displayBoard();
        }
        printState();
    }
    protected void playAI_AI() {
        prepareGame();
        while(checkState() == GameState.STARTED) {
            makeHardMove();
            displayBoard();
        }
        printState();
    }

    private void makeHardMove() {
        System.out.println("Making move level \"hard\"");
        int row = findBestMove().getRow();
        int col = findBestMove().getColumn();
        board[row][col] = currentPlayer;
        switchPlayers();
    }
    private Move findBestMove() {
        int bestVal = -1000;
        Move bestMove = new Move(-1, -1);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (isEmpty(i, j)) {
                    board[i][j] = currentPlayer;
                    int moveVal = minimax(0, false);
                    board[i][j] = ' ';
                    if (moveVal > bestVal) {
                        bestMove.setRow(i);
                        bestMove.setColumn(j);
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }
    private int evaluate() {
        if (currentPlayer == 'X') {
            if (checkState() == GameState.X_WIN) return 10;
            if (checkState() == GameState.O_WIN) return -10;
            else return 0;
        } else {
            if (checkState() == GameState.X_WIN) return -10;
            if (checkState() == GameState.O_WIN) return 10;
            else return 0;
        }
    }
    private int minimax(int moves, boolean isMax) {
        GameState state = checkState();
        if (state != GameState.STARTED) {
            return evaluate();
        }
        int score, bestScore;
        if (isMax) {
            bestScore = -1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (isEmpty(i,j)) {
                        board[i][j] = currentPlayer;
                        score = minimax(moves + 1, false);
                        board[i][j] = ' ';
                        bestScore = Math.max(bestScore, score);
                    }
                }
            }
            return bestScore;
        } else {
            bestScore = 1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (isEmpty(i, j)) {
                        board[i][j] = otherPlayer;
                        score = minimax(moves + 1, true);
                        board[i][j] = ' ';
                        bestScore = Math.min(bestScore, score);
                    }
                }
            }
            return bestScore;
        }
    }

}

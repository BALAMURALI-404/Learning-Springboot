import java.util.*;
public class TicTacToe {
    public static void printBoard(char[][] board){
        System.out.println("Current Board:");
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                System.out.print(board[i][j]);
                if(j<2) System.out.print("|");
            }
            System.out.println();
            if(i<2) System.out.println("-----");
        }
    }
    public static char checkWinner(char[][] board){
        for(int i=0;i<3;i++){
            if(board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) return board[i][0];
            if(board[0][i] != ' ' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) return board[0][i];
        }
        if(board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) return board[0][0];
        if(board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) return board[0][2];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board[i][j] == 'X' || board[i][j] == 'O') return ' '; 
            }
        }
        return 'T';
    }
    public static void main(String[] args){
        char[][] board = {
            {'1','2','3'},
            {'4','5','6'},
            {'7','8','9'}
        };
        Scanner s = new Scanner(System.in);
        System.out.println("Welcome to Tic Tac Toe!");
        System.out.println("Player 1 is 'X' and Player 2 is 'O'");
        char currentPlayer = 'X';
        while(true){
            printBoard(board);
            System.out.println(currentPlayer + "'s turn. Enter your postion");
            int pos = s.nextInt();
            int row = (pos-1)/3;
            int col = (pos-1)%3;
            if(pos < 1 || pos > 9 || board[row][col] == 'X' || board[row][col] == 'O'){
                System.out.println("Invalid move. Try again.");
                continue;
            }
            board[row][col] = currentPlayer;
            char winner = checkWinner(board);
            if(winner == 'X' || winner == 'O'){
                printBoard(board);
                System.out.println("Player " + winner + " wins!");
                break;
            } else if(winner == 'T'){
                printBoard(board);
                System.out.println("It's a tie!");
                break;
            }
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
    }
}
package ndttt;

import java.util.ArrayList;
import java.util.List;

public class GameState {
	
	static class Move {
		private int x;
		private int y;
		private GamePiece piece;

		public Move(int x, int y, GamePiece piece) {
			super();
			this.x = x;
			this.y = y;
			this.piece = piece;
		}
		public Player getPlayer() {
			return piece.getPlayer();
		}
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		public GamePiece getPiece() {
			return piece;
		}
		public void setPiece(GamePiece piece) {
			this.piece = piece;
		}
		
		@Override
		public String toString() {
			return getPlayer().getName() + " plays " + getPiece() + " at [" + getX() + "][" + getY() + "]";
		}
	}
	
	static class GamePiece {
		private Player player;
		private int rank;
		private GamePiece nestedPiece;
		public GamePiece(Player player, int rank) {
			this.player = player;
			this.rank = rank;
		}
		public Player getPlayer() {
			return player;
		}
		public void setPlayer(Player player) {
			this.player = player;
		}
		public int getRank() {
			return rank;
		}
		public void setRank(int rank) {
			this.rank = rank;
		}
		public GamePiece getNestedPiece() {
			return nestedPiece;
		}
		public void setNestedPiece(GamePiece nestedPiece) {
			this.nestedPiece = nestedPiece;
		}
		public boolean canPlayOver(GamePiece piece) {
			return piece == null || getRank() > piece.getRank();
		}
		
		@Override
		public String toString() {
			return player.getIdentifier() + Integer.toString(rank);
		}
	}
	
	private GamePiece[][] board;
	private int moveCount;
	
	public GameState(int boardSize) {
		board = new GamePiece[boardSize][boardSize];
		moveCount = 0;
	}
	
	public boolean isWin(Move move) {
		Player player = move.getPlayer();
		int x = move.getX();
		int y = move.getY();
		int n = board.length;
		
		
		//check col
        for(int i = 0; i < n; i++){
            if(i != y && (board[x][i] == null || !board[x][i].getPlayer().equals(player)))
                break;
            if(i == n-1){
            	return true;
            }
        }

        //check row
        for(int i = 0; i < n; i++){
            if(i != x && (board[i][y] == null || !board[i][y].getPlayer().equals(player)))
                break;
            if(i == n-1){
                return true;
            }
        }

        //check diag
        if(x == y){
            //we're on a diagonal
            for(int i = 0; i < n; i++){
                if(x != i && (board[i][i] == null || !board[i][i].getPlayer().equals(player)))
                    break;
                if(i == n-1){
                    return true;
                }
            }
        }

        //check anti diag (thanks rampion)
        if(x + y == n - 1){
            for(int i = 0; i < n; i++){
            	int j = (n-1)-i;
            	if (i != x || j != y) {
            		if(board[i][j] == null || !board[i][j].getPlayer().equals(player)) {
            			break;
            		}
            	}
                if(i == n-1){
                    return true;
                }
            }
        }
		return false;
	}
	
	public List<Move> getPossibleMoves(Player player) {
		List<Move> moves = new ArrayList<>();
		for (GamePiece piece : player.getRemainingPieces()) {
			for (int x = 0; x < board.length; x++) {
				for (int y = 0; y < board[x].length; y++) {
					Move move = new Move(x, y, piece);
					if (isAllowedMove(move)) {
						moves.add(move);
					}
				}
			}
		}
		return moves;
	}
	
	public boolean isAllowedMove(Move move) {
		boolean validPosition = move.piece.canPlayOver(board[move.x][move.y]);
		boolean hasPiece = move.getPlayer().getRemainingPieces().contains(move.piece);
		return validPosition && hasPiece;
	}
	
	public void playMove(Move move) {
		moveCount++;
		GamePiece position = board[move.x][move.y];
		move.getPiece().setNestedPiece(position);
		board[move.x][move.y] = move.getPiece();
		move.getPlayer().getRemainingPieces().remove(move.getPiece());
	}
	
	public GamePiece[][] getBoard() {
		return board;
	}

	@Override
	public String toString() {
		StringBuffer buf =  new StringBuffer();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				GamePiece piece = board[i][j];
				if (piece != null) {
					buf.append(piece.toString()).append(' ');
				} else {
					buf.append("   ");
				}
			}
			buf.append("\n");
		}
		return buf.toString();
	}

	public int getMoveCount() {
		return moveCount;
	}
}

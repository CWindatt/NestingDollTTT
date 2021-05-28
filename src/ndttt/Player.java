package ndttt;

import java.util.ArrayList;
import java.util.List;

import ndttt.GameState.GamePiece;
import ndttt.GameState.Move;

public abstract class Player {
	
	private String name;
	private char identifier;
	private List<GamePiece> remainingPieces;
	private Player nextPlayer;
	public Player(String name, char identifier) {
		this.setName(name);
		this.setIdentifier(identifier);
	}
	
	public abstract Move selectMove(List<Move> possibleMoves, GameState state);
	
	public void resetPieces(int pieceCount) {
		setRemainingPieces(new ArrayList<>(pieceCount));
		for (int i=0; i<pieceCount; i++) {
			GamePiece piece = new GamePiece(this, i);
			getRemainingPieces().add(piece);
		}
	}
	
	public GamePiece getHighestRankPieceRemaining() {
		if (remainingPieces == null || remainingPieces.isEmpty()) {
			return null;
		}
		GamePiece bestPiece = remainingPieces.get(0);
		for (GamePiece gamePiece : remainingPieces) {
			if (gamePiece.getRank() > bestPiece.getRank()) {
				bestPiece = gamePiece;
			}
		}
		return bestPiece;
	}
	
	public GamePiece getLowestRankRemaining(int aboveRank) {
		if (remainingPieces == null || remainingPieces.isEmpty()) {
			return null;
		}
		GamePiece bestPiece = null;
		for (GamePiece gamePiece : remainingPieces) {
			if (gamePiece.getRank() > aboveRank) {
				if (bestPiece == null || gamePiece.getRank() < bestPiece.getRank()) {
					bestPiece = gamePiece;
				}
			}
		}
		return bestPiece;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<GamePiece> getRemainingPieces() {
		return remainingPieces;
	}

	public void setRemainingPieces(List<GamePiece> remainingPieces) {
		this.remainingPieces = remainingPieces;
	}

	public Player getNextPlayer() {
		return nextPlayer;
	}

	public void setNextPlayer(Player nextPlayer) {
		this.nextPlayer = nextPlayer;
	}

	public char getIdentifier() {
		return identifier;
	}

	public void setIdentifier(char identifier) {
		this.identifier = identifier;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
}

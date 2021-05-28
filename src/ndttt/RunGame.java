package ndttt;

import java.util.ArrayList;
import java.util.List;

import ndttt.GameState.Move;

public class RunGame {

	public static final int PIECE_COUNT = 5;
	public static final int BOARD_SIZE = 3;
	
	public static void main(String[] args) {
		List<Player> players = new ArrayList<>();
		players.add(new BasicBot("Basic Bot X", 'X'));
		players.add(new BasicBot("Basic Bot O", 'O'));
//		add new RandoBot("Rando Bot X", 'X');
//		players.add(player1);
//		RandoBot player2 = new RandoBot("Rando Bot O", 'O');
//		players.add(player2);
		
		for (int i=0; i<1; i++) {
			System.out.println();
			System.out.println("--------------------");
			System.out.println("STARTING GAME " + i);
			playGame(players);
		}
	}

	private static void playGame(List<Player> players) {
		GameState state = new GameState(BOARD_SIZE);
		for (Player player : players) {
			player.resetPieces(PIECE_COUNT);
		}
		
		Player startingPlayer = orderPlayers(players);
		
		Player currentPlayer = startingPlayer;
		Player winner = null;
		List<Move> possibleMoves = state.getPossibleMoves(currentPlayer);
		while (!possibleMoves.isEmpty() && winner == null && state.getMoveCount() < 100) {
			Move move = currentPlayer.selectMove(possibleMoves, state);
			if (move.getPlayer() != currentPlayer || !state.isAllowedMove(move)) {
				throw new RuntimeException(move.getPlayer() + " is a cheater");
			}
			if (state.isWin(move)) {
				winner = currentPlayer;
			}
			state.playMove(move);
			System.out.println(state.getMoveCount() + ": " + move);
			System.out.println(state);
			currentPlayer = currentPlayer.getNextPlayer();
			possibleMoves = state.getPossibleMoves(currentPlayer);
		}
		
		System.out.println("Game completed in " + state.getMoveCount() + " moves.");
		if (winner == null) {
			System.out.println("Game was a draw.");
		} else {
			System.out.println("Winner was " + winner.getName());
		}
	}

	private static Player orderPlayers(List<Player> players) {
		players.get(players.size()-1).setNextPlayer(players.get(0));
		for (int i=(players.size()-2); i >= 0; i--) {
			players.get(i).setNextPlayer(players.get(i+1));
		}
		return players.get(0);
	}

}

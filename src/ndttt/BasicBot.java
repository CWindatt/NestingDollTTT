package ndttt;

import java.util.List;

import ndttt.GameState.GamePiece;
import ndttt.GameState.Move;

public class BasicBot extends RandoBot {

	public BasicBot(String name, char identifier) {
		super(name, identifier);
	}

	@Override
	public Move selectMove(List<Move> possibleMoves, GameState state) {
		int checks = 0;
		for (Move move : possibleMoves) {
			checks++;
			if (state.isWin(move)) {
				printAnalysis(checks);
				System.out.println("Chose a winning move.");
				return move;
			}
		}
		
		Player nextPlayer = getNextPlayer();
		List<Move> opponentMoves = state.getPossibleMoves(nextPlayer);
		for (Move oppMove : opponentMoves) {
			checks++;
			if (state.isWin(oppMove)) {
				GamePiece oppHighest = nextPlayer.getHighestRankPieceRemaining();
				if (oppHighest != null) {
					GamePiece lowestStongerPiece = getLowestRankRemaining(oppHighest.getRank());
					if (lowestStongerPiece != null) {
						Move preventLossMove = new Move(oppMove.getX(), oppMove.getY(), lowestStongerPiece);
						if (state.isAllowedMove(preventLossMove)) {
							printAnalysis(checks);
							System.out.println("Chose a move to prevent opponent from winning.");
							return preventLossMove;
						}
					}
				}
			}
		}
		
		printAnalysis(checks);
		return super.selectMove(possibleMoves, state);
	}
	
	private void printAnalysis(int checks) {
		System.out.println(getName() + " analyzed " + checks + " moves.");
	}
}

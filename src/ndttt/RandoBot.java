package ndttt;

import java.util.List;
import java.util.Random;

import ndttt.GameState.Move;

public class RandoBot extends Player {

	private Random rnd;
	
	public RandoBot(String name, char identifier) {
		super(name, identifier);
		rnd = new Random();
	}
	
	@Override
	public Move selectMove(List<Move> possibleMoves, GameState state) {
		int randomMoveIndex = rnd.nextInt(possibleMoves.size());
		Move randomMove = possibleMoves.get(randomMoveIndex);
		System.out.println("Chose a move at random.");
		return randomMove;
	}
}

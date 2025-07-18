package chess;

import boardgame.PECA;
import boardgame.Position;
import boardgame.Tabuleiro;

public abstract class PecaXadrez extends PECA{

		private Color color;
		private int moveCount;

		public PecaXadrez(Tabuleiro tabuleiro, Color color) {
			super(tabuleiro);
			this.color = color;
		}

		public Color getColor() {
			return color;
		}
		
		public PosicaoXadrez getPosicaoXadrez() {
			return PosicaoXadrez.fromPosition(position);
		}
		 
		protected boolean verSePecaAdversaria(Position position) {
			PecaXadrez p = (PecaXadrez)getTabuleiro().peca(position);
			return p != null && p.getColor() != color;
		}
		
		public int getMoveCount() {
			return moveCount;
		}
		
		protected void increaseMoveCount() {
			moveCount++;
		}

		protected void decreaseMoveCount() {
			moveCount--;
		}

}

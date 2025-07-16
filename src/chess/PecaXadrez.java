package chess;

import boardgame.PECA;
import boardgame.Tabuleiro;

public abstract class PecaXadrez extends PECA{

		private Color color;

		public PecaXadrez(Tabuleiro tabuleiro, Color color) {
			super(tabuleiro);
			this.color = color;
		}

		public Color getColor() {
			return color;
		}
		
		
}

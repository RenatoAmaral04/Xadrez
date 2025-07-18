package chess.peca;

import boardgame.Position;
import boardgame.Tabuleiro;
import chess.Color;
import chess.PecaXadrez;

public class Peao extends PecaXadrez {

	public Peao(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro, color);
	}

	@Override
	public String toString() {
		return "p";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Position p = new Position(0, 0);

		if (getColor() == Color.WHITE) {
			p.setCoordenada(position.getLinha() - 1, position.getColuna());
			if (getTabuleiro().positionExists(p) && !getTabuleiro().temPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setCoordenada(position.getLinha() - 2, position.getColuna());
			Position p2 = new Position(position.getLinha() - 1, position.getColuna());
			if (getTabuleiro().positionExists(p) && !getTabuleiro().temPeca(p) && getTabuleiro().positionExists(p2) && !getTabuleiro().temPeca(p2) && getMoveCount() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setCoordenada(position.getLinha() - 1, position.getColuna() - 1);
			if (getTabuleiro().positionExists(p) && verSePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}			
			p.setCoordenada(position.getLinha() - 1, position.getColuna() + 1);
			if (getTabuleiro().positionExists(p) && verSePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}			
		}
		else {
			p.setCoordenada(position.getLinha() + 1, position.getColuna());
			if (getTabuleiro().positionExists(p) && !getTabuleiro().temPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setCoordenada(position.getLinha() + 2, position.getColuna());
			Position p2 = new Position(position.getLinha() - 1, position.getColuna());
			if (getTabuleiro().positionExists(p) && !getTabuleiro().temPeca(p) && getTabuleiro().positionExists(p2) && !getTabuleiro().temPeca(p2) && getMoveCount() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setCoordenada(position.getLinha() + 1, position.getColuna() - 1);
			if (getTabuleiro().positionExists(p) && verSePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}			
			p.setCoordenada(position.getLinha() + 1, position.getColuna() + 1);
			if (getTabuleiro().positionExists(p) && verSePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}	
		}
		return mat;
	}
}

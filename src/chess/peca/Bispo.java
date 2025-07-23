package chess.peca;

import boardgame.Position;
import boardgame.Tabuleiro;
import chess.Color;
import chess.PecaXadrez;

public class Bispo extends PecaXadrez {

	public Bispo(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro, color);
	}

	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Position p = new Position(0, 0);
		
		//esquerda superior
		p.setCoordenada(position.getLinha() + 1, position.getColuna() - 1);
		while (getTabuleiro().positionExists(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setCoordenada(p.getLinha() + 1, p.getColuna() - 1);
		}
		if (getTabuleiro().positionExists(p) && verSePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//direita superior
		p.setCoordenada(position.getLinha() + 1, position.getColuna() + 1);
		while (getTabuleiro().positionExists(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setCoordenada(p.getLinha() + 1, p.getColuna() + 1);
		}
		if (getTabuleiro().positionExists(p) && verSePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//esquerda inferior
		p.setCoordenada(position.getLinha() - 1, position.getColuna() - 1);
		while (getTabuleiro().positionExists(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setCoordenada(p.getLinha() - 1, p.getColuna() - 1);
		}
		if (getTabuleiro().positionExists(p) && verSePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//esquerda superior
		p.setCoordenada(position.getLinha() - 1, position.getColuna() + 1);
		while (getTabuleiro().positionExists(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setCoordenada(p.getLinha() - 1, p.getColuna() + 1);
		}
		if (getTabuleiro().positionExists(p) && verSePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		return mat;
	}
}

package chess.peca;

import boardgame.Position;
import boardgame.Tabuleiro;
import chess.Color;
import chess.PecaXadrez;

public class Rei extends PecaXadrez {

	public Rei(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro, color);
	}

	@Override
	public String toString() {
		return "R";
	}

	private boolean podeMover(Position position) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(position);
		return p == null || p.getColor() != getColor();
	}
	
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean [][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Position p = new Position(0,0);
		
		//acima
		p.setCoordenada(position.getLinha() -1 , position.getColuna());
		if(getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//abaixo
		p.setCoordenada(position.getLinha() + 1 , position.getColuna());
		if(getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//direita
		p.setCoordenada(position.getLinha(), position.getColuna() + 1);
		if(getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//esquerda
		p.setCoordenada(position.getLinha(), position.getColuna() - 1);
		if(getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//diagonal esquerda superior
		p.setCoordenada(position.getLinha() - 1 , position.getColuna() - 1);
		if(getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//diagonal esquerda inferior
		p.setCoordenada(position.getLinha() - 1 , position.getColuna() + 1);
		if(getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//diagonal direita superior
		p.setCoordenada(position.getLinha() + 1 , position.getColuna() - 1);
		if(getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//diagonal direita inferior
		p.setCoordenada(position.getLinha() + 1 , position.getColuna() + 1);
		if(getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		return mat;
	}
}

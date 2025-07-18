package chess.peca;

import boardgame.Position;
import boardgame.Tabuleiro;
import chess.Color;
import chess.PecaXadrez;

public class Torre extends PecaXadrez {

	public Torre(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro, color);
	}
	
	@Override
	public String toString() {
		return "T";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		
		boolean [][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Position p = new Position(0,0);
		
		//pra cima
		p.setCoordenada(position.getLinha() - 1, position.getColuna());
		while(getTabuleiro().positionExists(p) && !getTabuleiro().temPeca(p));{
			
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
		}
		if(getTabuleiro().positionExists(p) && verSePecaAdversaria(p)) {
			
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//esquerda
		p.setCoordenada(position.getLinha(), position.getColuna() - 1);
		while(getTabuleiro().positionExists(p) && !getTabuleiro().temPeca(p));{
			
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1);
		}
		if(getTabuleiro().positionExists(p) && verSePecaAdversaria(p)) {
			
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//direita
		p.setCoordenada(position.getLinha(), position.getColuna() + 1);
		while(getTabuleiro().positionExists(p) && !getTabuleiro().temPeca(p));{
			
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() + 1);
		}
		if(getTabuleiro().positionExists(p) && verSePecaAdversaria(p)) {
			
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//baixo
		p.setCoordenada(position.getLinha() + 1, position.getColuna());
		while(getTabuleiro().positionExists(p) && !getTabuleiro().temPeca(p));{
			
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
		}
		if(getTabuleiro().positionExists(p) && verSePecaAdversaria(p)) {
			
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		return mat;
	}

}

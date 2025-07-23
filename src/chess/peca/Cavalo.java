package chess.peca;

import boardgame.Position;
import boardgame.Tabuleiro;
import chess.Color;
import chess.PecaXadrez;

public class Cavalo extends PecaXadrez {

	public Cavalo(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro, color);
	}

	@Override
	public String toString() {
		return "C";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Position p = new Position(0, 0);
		
		 // cima esquerda
	    p.setCoordenada(position.getLinha() + 2, position.getColuna() - 1);
	    if (getTabuleiro().positionExists(p) && !getTabuleiro().temPeca(p)) {
	        mat[p.getLinha()][p.getColuna()] = true;
	    }
	    if (getTabuleiro().positionExists(p) && verSePecaAdversaria(p)) {
	        mat[p.getLinha()][p.getColuna()] = true;
	    }
	    
	    // cima direita
	    p.setCoordenada(position.getLinha() + 2, position.getColuna() + 1);
	    if (getTabuleiro().positionExists(p) && !getTabuleiro().temPeca(p)) {
	        mat[p.getLinha()][p.getColuna()] = true;
	    }
	    if (getTabuleiro().positionExists(p) && verSePecaAdversaria(p)) {
	        mat[p.getLinha()][p.getColuna()] = true;
	    }
	    
	    // baixo esquerda
	    p.setCoordenada(position.getLinha() - 2, position.getColuna() - 1);
	    if (getTabuleiro().positionExists(p) && !getTabuleiro().temPeca(p)) {
	        mat[p.getLinha()][p.getColuna()] = true;
	    }
	    if (getTabuleiro().positionExists(p) && verSePecaAdversaria(p)) {
	        mat[p.getLinha()][p.getColuna()] = true;
	    }
	    
	    // baixo direita
	    p.setCoordenada(position.getLinha() - 2, position.getColuna() + 1);
	    if (getTabuleiro().positionExists(p) && !getTabuleiro().temPeca(p)) {
	        mat[p.getLinha()][p.getColuna()] = true;
	    }
	    if (getTabuleiro().positionExists(p) && verSePecaAdversaria(p)) {
	        mat[p.getLinha()][p.getColuna()] = true;
	    }
	    
	    // full esquerda cima
	    p.setCoordenada(position.getLinha() + 1, position.getColuna() - 2);
	    if (getTabuleiro().positionExists(p) && !getTabuleiro().temPeca(p)) {
	        mat[p.getLinha()][p.getColuna()] = true;
	    }
	    if (getTabuleiro().positionExists(p) && verSePecaAdversaria(p)) {
	        mat[p.getLinha()][p.getColuna()] = true;
	    }
	    
	    // full direita cima
	    p.setCoordenada(position.getLinha() + 1, position.getColuna() + 2);
	    if (getTabuleiro().positionExists(p) && !getTabuleiro().temPeca(p)) {
	        mat[p.getLinha()][p.getColuna()] = true;
	    }
	    if (getTabuleiro().positionExists(p) && verSePecaAdversaria(p)) {
	        mat[p.getLinha()][p.getColuna()] = true;
	    }
	            
	    // full esquerda baixo
	    p.setCoordenada(position.getLinha() - 1, position.getColuna() - 2);
	    if (getTabuleiro().positionExists(p) && !getTabuleiro().temPeca(p)) {
	        mat[p.getLinha()][p.getColuna()] = true;
	    }
	    if (getTabuleiro().positionExists(p) && verSePecaAdversaria(p)) {
	        mat[p.getLinha()][p.getColuna()] = true;
	    }
	    
	    // full direita baixo 
	    p.setCoordenada(position.getLinha() - 1, position.getColuna() + 2); 
	    if (getTabuleiro().positionExists(p) && !getTabuleiro().temPeca(p)) {
	        mat[p.getLinha()][p.getColuna()] = true;
	    }
	    if (getTabuleiro().positionExists(p) && verSePecaAdversaria(p)) {
	        mat[p.getLinha()][p.getColuna()] = true;
	    }	
	    
	    return mat;
	}
}

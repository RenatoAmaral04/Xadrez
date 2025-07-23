package chess.peca;

import boardgame.Position;
import boardgame.Tabuleiro;
import chess.Color;
import chess.PartidaXadrez;
import chess.PecaXadrez;

public class Peao extends PecaXadrez {
	
	private PartidaXadrez partidaXadrez;

	public Peao(Tabuleiro tabuleiro, Color color, PartidaXadrez partidaXadrez) {
		super(tabuleiro, color);
		this.partidaXadrez = partidaXadrez;
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
			
			  // Movimento 1 casa para frente
	        p.setCoordenada(position.getLinha() - 1, position.getColuna());
	        if (getTabuleiro().positionExists(p) && !getTabuleiro().temPeca(p)) {
	            mat[p.getLinha()][p.getColuna()] = true;
	        }

	        // Movimento 2 casas para frente (
	        if (getTabuleiro().positionExists(p) && !getTabuleiro().temPeca(p) && getMoveCount() == 0) {
	            Position p2 = new Position(position.getLinha() - 2, position.getColuna());
	            if (getTabuleiro().positionExists(p2) && !getTabuleiro().temPeca(p2)) {
	                mat[p2.getLinha()][p2.getColuna()] = true;
	            }
	        }
			
			//Captura na diagonal esquerda
			p.setCoordenada(position.getLinha() - 1, position.getColuna() - 1);
			if (getTabuleiro().positionExists(p) && verSePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			// Captura na diagonal direita
			p.setCoordenada(position.getLinha() - 1, position.getColuna() + 1);
			if (getTabuleiro().positionExists(p) && verSePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			//enPassant brancas
			if(position.getLinha() == 3) {
				Position esquerda = new Position(position.getLinha(), position.getColuna() - 1);
				if(getTabuleiro().positionExists(esquerda) && verSePecaAdversaria(esquerda) && getTabuleiro().peca(esquerda) == partidaXadrez.getEnPassant()) {
					mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
				}
				Position direita = new Position(position.getLinha(), position.getColuna() + 1);
				if(getTabuleiro().positionExists(direita) && verSePecaAdversaria(direita) && getTabuleiro().peca(direita) == partidaXadrez.getEnPassant()) {
					mat[direita.getLinha() - 1][direita.getColuna()] = true;
				}
			}
			
			
		}
		else { 
		
			//para frente
			p.setCoordenada(position.getLinha() + 1, position.getColuna());
			if (getTabuleiro().positionExists(p) && !getTabuleiro().temPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
				
				// 2 para frente 
				Position p2 = new Position(position.getLinha() + 2, position.getColuna());
				if (getTabuleiro().positionExists(p2) && !getTabuleiro().temPeca(p2) && getMoveCount() == 0) {
					mat[p2.getLinha()][p2.getColuna()] = true;
				}
			}
			
			//Captura na diagonal esquerda
			p.setCoordenada(position.getLinha() + 1, position.getColuna() + 1);
			if (getTabuleiro().positionExists(p) && verSePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			// Captura na diagonal direita
			p.setCoordenada(position.getLinha() + 1, position.getColuna() - 1);
			if (getTabuleiro().positionExists(p) && verSePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			//enPassant pretas
			if(position.getLinha() == 4) {
				Position esquerda = new Position(position.getLinha() , position.getColuna() + 1);
				if(getTabuleiro().positionExists(esquerda) && verSePecaAdversaria(esquerda) && getTabuleiro().peca(esquerda) == partidaXadrez.getEnPassant()) {
					mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
				}
				Position direita = new Position(position.getLinha() , position.getColuna() - 1);
				if(getTabuleiro().positionExists(direita) && verSePecaAdversaria(direita) && getTabuleiro().peca(direita) == partidaXadrez.getEnPassant()) {
					mat[direita.getLinha() + 1][direita.getColuna()] = true;
				}
			}
		}
		return mat;
	}
}

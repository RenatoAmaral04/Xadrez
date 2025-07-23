package chess.peca;

import boardgame.Position;
import boardgame.Tabuleiro;
import chess.Color;
import chess.PartidaXadrez;
import chess.PecaXadrez;

public class Rei extends PecaXadrez {

	private PartidaXadrez partidaXadrez;
	
	

	public Rei(Tabuleiro tabuleiro, Color color, PartidaXadrez partidaXadrez) {
		super(tabuleiro, color);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public String toString() {
		return "R";
	}

	private boolean podeMover(Position position) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(position);
		return p == null || p.getColor() != getColor();
	}
	
	private boolean testTorreRock(Position position) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(position);
		return p != null && p instanceof Torre && p.getColor() == getColor() && p.getMoveCount() == 0;
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
		
		//verificar o rock Pequeno
		if(getMoveCount() == 0 && !partidaXadrez.getCheck()) {
			Position torre1 = new Position(position.getLinha(), position.getColuna() + 3);
			if(testTorreRock(torre1)) {
				Position p1 = new Position(position.getLinha(), position.getColuna() + 1);
				Position p2 = new Position(position.getLinha(), position.getColuna() + 2);
				if(getTabuleiro().peca(p1) ==  null && getTabuleiro().peca(p2) == null) {
					mat[position.getLinha()][position.getColuna() + 2] = true;
				}
				
			}
		}
		
		//verificar o rock grande
		if(getMoveCount() == 0 && !partidaXadrez.getCheck()) {
			Position torre2 = new Position(position.getLinha(), position.getColuna() - 4);
			if(testTorreRock(torre2)) {
				Position p1 = new Position(position.getLinha(), position.getColuna() - 1);
				Position p2 = new Position(position.getLinha(), position.getColuna() - 2);
				Position p3 = new Position(position.getLinha(), position.getColuna() - 3);
				if(getTabuleiro().peca(p1) ==  null && getTabuleiro().peca(p2) == null  && getTabuleiro().peca(p3) == null) {
					mat[position.getLinha()][position.getColuna() - 2] = true;
				}
			}
		}
		return mat;
	}
}

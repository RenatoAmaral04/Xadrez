package boardgame;

public class Tabuleiro {

	private int linhas;
	private int colunas;
	private PECA[][] pecas;
	
	
	public Tabuleiro(int linhas, int colunas) {
		if(linhas < 1 || colunas < 1) {
			throw new BoardException("ERRO!! é necessário que tenha pelo menos 1 linha e uma coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new PECA[linhas][colunas];
	}


	public int getLinhas() {
		return linhas;
	}


	public void setLinhas(int linhas) {
		this.linhas = linhas;
	}


	public int getColunas() {
		return colunas;
	}


	public void setColunas(int colunas) {
		this.colunas = colunas;
	}
	
	public PECA pecas(int linhas, int colunas) {
		if(!positionExists(linhas, colunas)) {
			throw new BoardException("Posição não existe no tabuleiro");
		}
		return pecas[linhas][colunas];
	}
	
	public PECA peca(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Posição não existe no tabuleiro");
		}
		return pecas[position.getLinha()][position.getColuna()];
	}
	public void placePiece(PECA peca, Position position) {
		if(temPeca(position)) {
				throw new BoardException("Já tem uma peça nessa posição " + position);
		}
		pecas[position.getLinha()][position.getColuna()] = peca;
		peca.position = position;
	}
	public PECA removePeca(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Posição não existe no tabuleiro");
		}
		if(peca(position) == null) {
			return null;	
		}
		
		PECA aux = peca(position);
		aux.position = null;
		pecas[position.getLinha()][position.getColuna()] = null;
		return aux;
	}
	
	public boolean positionExists(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >=0 && coluna < colunas;
	}
	public boolean positionExists(Position position) {
		return positionExists(position.getLinha(), position.getColuna());
	}
	public boolean temPeca(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Posição não existe no tabuleiro");
		}
		return peca(position) != null;
	}
}

package boardgame;

public class Position {

	
	private int linha;
	private int Coluna;
	
	public Position(int linha, int coluna) {
		this.linha = linha;
		Coluna = coluna;
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public int getColuna() {
		return Coluna;
	}

	public void setColuna(int coluna) {
		Coluna = coluna;
	}
	
	@Override
	public String toString() {
		
		return linha + ", " + Coluna;
	}
}

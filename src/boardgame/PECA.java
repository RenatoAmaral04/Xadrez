package boardgame;

public abstract class PECA {

	protected Position position;
	private Tabuleiro tabuleiro;
	
	
	public PECA(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		position = null;
	}

	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	
	public abstract boolean [][] movimentosPossiveis();
	
	public boolean movimentoPossivel(Position position) {
		return movimentosPossiveis()[position.getLinha()][position.getColuna()];
	}
	
	public boolean verPossibilidadeMovimento() {
		boolean [][] mat = movimentosPossiveis();
		for(int i =0; i<mat.length; i++) {
			for (int j =0; j<mat.length; j++) {
				if(mat[i][j]) {
					return true;
				}
			}
		}
		return false; 
	}
}

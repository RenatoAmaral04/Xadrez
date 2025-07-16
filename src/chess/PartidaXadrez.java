package chess;

import boardgame.PECA;
import boardgame.Position;
import boardgame.Tabuleiro;
import chess.peca.Bispo;
import chess.peca.Cavalo;
import chess.peca.Peao;
import chess.peca.Rainha;
import chess.peca.Rei;
import chess.peca.Torre;

public class PartidaXadrez {

	
	private Tabuleiro tabuleiro;
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8,8);
		initialSetup();
	}
	
	public PecaXadrez[][] getPecas(){
		
		PecaXadrez [][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i< tabuleiro.getLinhas(); i++) {
			for (int j = 0; j<tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.pecas(i, j);
			}
		}
		return mat;
	}
	
	public PecaXadrez movimentoPeca(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		
		Position origem = posicaoOrigem.toPosition();
		Position destino = posicaoDestino.toPosition();
		validarPosicao(origem);
		PECA capturar = realizarMovimento(origem, destino);
		return (PecaXadrez)capturar;
	}
	
	private PECA realizarMovimento(Position origem, Position destino) {
		PECA p = tabuleiro.removePeca(origem);
		PECA pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.placePiece(p, destino);
		return pecaCapturada;
	}
	
	private void validarPosicao(Position position) {
		if(!tabuleiro.temPeca(position)) {
			throw new CheesException("Não existe peça na posição de origem");
		}
		if(tabuleiro.peca(position).verPossibilidadeMovimento()) {
			throw new CheesException("Não existe movimentos possíveis para a peça escolhida");
		}
	}
	private void receberCoordenada(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.placePiece(peca, new PosicaoXadrez(coluna, linha).toPosition());
	}
	
	private void initialSetup() {
		receberCoordenada('a', 8, new Torre(tabuleiro, Color.WHITE));
		receberCoordenada('h', 8, new Torre(tabuleiro, Color.WHITE));
		receberCoordenada('d', 8, new Rainha(tabuleiro, Color.WHITE));
		receberCoordenada('c', 8, new Bispo(tabuleiro, Color.WHITE));
		receberCoordenada('f', 8, new Bispo(tabuleiro, Color.WHITE));
		receberCoordenada('b', 8, new Cavalo(tabuleiro, Color.WHITE));
		receberCoordenada('g', 8, new Cavalo(tabuleiro, Color.WHITE));
		receberCoordenada('e', 8, new Rei( tabuleiro, Color.WHITE));
		receberCoordenada('a', 7, new Peao(tabuleiro, Color.WHITE));
		receberCoordenada('b', 7, new Peao(tabuleiro, Color.WHITE));
		receberCoordenada('c', 7, new Peao(tabuleiro, Color.WHITE));
		receberCoordenada('d', 7, new Peao(tabuleiro, Color.WHITE));
		receberCoordenada('e', 7, new Peao(tabuleiro, Color.WHITE));
		receberCoordenada('f', 7, new Peao(tabuleiro, Color.WHITE));
		receberCoordenada('g', 7, new Peao(tabuleiro, Color.WHITE));
		receberCoordenada('h', 7, new Peao(tabuleiro, Color.WHITE));
		
		
		receberCoordenada('e', 1, new Rei( tabuleiro, Color.BLACK));
		receberCoordenada('a', 1, new Torre(tabuleiro, Color.BLACK));
		receberCoordenada('h', 1, new Torre(tabuleiro, Color.BLACK));
		receberCoordenada('d', 1, new Rainha(tabuleiro, Color.BLACK));
		receberCoordenada('c', 1, new Bispo(tabuleiro, Color.BLACK));
		receberCoordenada('f', 1, new Bispo(tabuleiro, Color.BLACK));
		receberCoordenada('b', 1, new Cavalo(tabuleiro, Color.BLACK));
		receberCoordenada('g', 1, new Cavalo(tabuleiro, Color.BLACK));
		receberCoordenada('a', 2, new Peao(tabuleiro, Color.BLACK));
		receberCoordenada('b', 2, new Peao(tabuleiro, Color.BLACK));
		receberCoordenada('c', 2, new Peao(tabuleiro, Color.BLACK));
		receberCoordenada('d', 2, new Peao(tabuleiro, Color.BLACK));
		receberCoordenada('e', 2, new Peao(tabuleiro, Color.BLACK));
		receberCoordenada('f', 2, new Peao(tabuleiro, Color.BLACK));
		receberCoordenada('g', 2, new Peao(tabuleiro, Color.BLACK));
		receberCoordenada('h', 2, new Peao(tabuleiro, Color.BLACK));
	}
	
}

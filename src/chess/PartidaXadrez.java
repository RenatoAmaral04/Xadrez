package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

	private int vezJogar;
	private Tabuleiro tabuleiro;
	private Color jogador;
	private boolean check;
	private boolean checkMate;
	
	private List<PECA> pecaNoTabuleiro = new ArrayList<>();
	private List<PECA> pecaCapturadas = new ArrayList<>();
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8,8);
		vezJogar = 1;
		jogador = Color.WHITE;
		check = false;
		initialSetup();
	}
	
	public int getVezJogar() {
		return vezJogar;
	}

	public Color getJogador() {
		return jogador;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
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
	
	public boolean[][] possiveisMov(PosicaoXadrez posicaoOrigem){
		Position position = posicaoOrigem.toPosition();
		validarPosicaoOrigem(position);
		return tabuleiro.peca(position).movimentosPossiveis();
		
	}
	
	public PecaXadrez movimentoPeca(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		
		Position origem = posicaoOrigem.toPosition();
		Position destino = posicaoDestino.toPosition();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		PECA capturar = realizarMovimento(origem, destino);
		
		if(Check(jogador)) {
			desfazerMovimento(origem, destino, capturar);
			throw new CheesException("Você não pode se por em check");
		}
		
		check = (Check(adversario(jogador))) ? true : false;
		
		if (testCheckMate(adversario(jogador))) {
			checkMate = true;
		}
		else {
			getVezJogar();
		}
		return (PecaXadrez)capturar;
	}
	
	private PECA realizarMovimento(Position origem, Position destino) {
		
		PecaXadrez p = (PecaXadrez)tabuleiro.removePeca(origem);
		p.increaseMoveCount();
		PECA pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.placePiece(p, destino);
		
		if(pecaCapturada != null) {
			pecaNoTabuleiro.remove(pecaCapturada);
			pecaCapturadas.add(pecaCapturada);
		}
		return pecaCapturada;
	}
	
	private void desfazerMovimento(Position origem, Position destino, PECA pecaCapturada) {
		
		PecaXadrez p = (PecaXadrez)tabuleiro.removePeca(destino);
		p.decreaseMoveCount();
		tabuleiro.placePiece(p, origem);
		
		if(pecaCapturada != null) {
			tabuleiro.placePiece(pecaCapturada, destino);
			pecaCapturadas.remove(pecaCapturada);
			pecaNoTabuleiro.add(pecaCapturada);
		}
	}
	
	private void validarPosicaoOrigem(Position position) {
		if(!tabuleiro.temPeca(position)) {
			throw new CheesException("Não existe peça na posição de origem");
		}
		
		if(jogador != ((PecaXadrez)tabuleiro.peca(position)).getColor()) {
			throw new CheesException("A peça escolhida não é sua");
		}
		if(tabuleiro.peca(position).verPossibilidadeMovimento()) {
			throw new CheesException("Não existe movimentos possíveis para a peça escolhida");
		}
	}
	
	public void validarPosicaoDestino(Position origem, Position destino) {
		if(!tabuleiro.peca(origem).movimentoPossivel(destino)) {
			throw new CheesException("Não é possível mover a peça para o destino selecionado");
		}
		
	}
	
	private void proximaJogada() {
		vezJogar++;
		jogador = (jogador == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private Color adversario(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private PecaXadrez rei(Color color) {
		List<PECA> list = pecaNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getColor() == color).collect(Collectors.toList());
		for(PECA p : list) {
			if ( p instanceof Rei) {
				return (PecaXadrez) p;
			}
		}
		throw new IllegalStateException("Não existe o rei da cor: " + color + " no tabuleiro");
	}
	
	private boolean Check(Color color) {
		Position posicaoRei = rei(color).getPosicaoXadrez().toPosition();
		List<PECA> pecasOponentes = pecaNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getColor() == adversario(color)).collect(Collectors.toList());
		for(PECA p : pecasOponentes) {
			boolean [][] mat = p.movimentosPossiveis();
			if(mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testCheckMate(Color color) {
		if (!Check(color)) {
			return false;
		}
		List<PECA> list = pecaNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getColor() == color).collect(Collectors.toList());
		for (PECA p : list) {
			boolean[][] mat = p.movimentosPossiveis();
			for (int i=0; i<tabuleiro.getLinhas(); i++) {
				for (int j=0; j<tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Position origem = ((PecaXadrez)p).getPosicaoXadrez().toPosition();
						Position destino = new Position(i, j);
						PECA pecaCapturada = realizarMovimento(origem, destino);
						boolean testCheck = Check(color);
						desfazerMovimento(origem, destino, pecaCapturada);
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}	
	
	
	private void receberCoordenada(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.placePiece(peca, new PosicaoXadrez(coluna, linha).toPosition());
		pecaNoTabuleiro.add(peca);
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

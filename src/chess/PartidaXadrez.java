package chess;

import java.security.InvalidParameterException;
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
	private PecaXadrez enPassant;
	private PecaXadrez promocao;
	
	public PecaXadrez getPromocao() {
		return promocao;
	}

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

	public PecaXadrez getEnPassant() {
		return enPassant;
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
		
		PecaXadrez pecaMovida = (PecaXadrez)tabuleiro.peca(destino);
		
		//promoção
		promocao = null;
		if(pecaMovida instanceof Peao) {
			if(pecaMovida.getColor() == Color.WHITE && destino.getLinha() == 0 || pecaMovida.getColor() == Color.BLACK && destino.getLinha() == 7) {
				promocao = (PecaXadrez)tabuleiro.peca(destino);
				promocao = pecaPromovida("D");
			}
		}
		
		check = (Check(adversario(jogador))) ? true : false;
		
		if (testCheckMate(adversario(jogador))) {
			checkMate = true;
		}
		else {
			proximaJogada();
		}
		
		//Enpassant
		
		if(pecaMovida instanceof Peao && (destino.getLinha() == origem.getLinha()-2 || destino.getLinha() == origem.getLinha() + 2)) {
			enPassant = pecaMovida;
		}else {
			enPassant = null;
		}
		return (PecaXadrez)capturar;
	}
	
	public PecaXadrez pecaPromovida(String type) {
		if(promocao == null)	{
			throw new IllegalStateException("Não da pra promover a peça");
		}
		if(!type.equals("B") && !type.equals("C") && !type.equals("D") && !type.equals("T")) {
			throw new InvalidParameterException("Peça selecionada, não é válida");
		}
		
		Position pos = promocao.getPosicaoXadrez().toPosition();
		PECA p = tabuleiro.removePeca(pos);
		pecaNoTabuleiro.remove(p);
		
		PecaXadrez novaPeca = novaPeca(type, promocao.getColor());
		tabuleiro.placePiece(novaPeca, pos);
		pecaNoTabuleiro.add(novaPeca);
		
		return novaPeca;
	}
	
	private PecaXadrez novaPeca(String type, Color color) {
		if(type.equals("B")) return new Bispo(tabuleiro, color);
		if(type.equals("C")) return new Cavalo(tabuleiro, color);
		if(type.equals("D")) return new Rainha(tabuleiro, color);
		return new Torre(tabuleiro, color);
	}
	
	
	private PECA realizarMovimento(Position origem, Position destino) {
	    PecaXadrez p = (PecaXadrez)tabuleiro.removePeca(origem);
	    p.increaseMoveCount();
	    PECA pecaCapturada = tabuleiro.removePeca(destino);
	    tabuleiro.placePiece(p, destino);

	    if (pecaCapturada != null) {
	        pecaNoTabuleiro.remove(pecaCapturada);
	        pecaCapturadas.add(pecaCapturada);
	    }

	    //Roque Pequeno
	    if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
	        Position origemTorre = new Position(origem.getLinha(), origem.getColuna() + 3);
	        Position destinoTorre = new Position(origem.getLinha(), origem.getColuna() + 1);
	        PecaXadrez torre = (PecaXadrez)tabuleiro.removePeca(origemTorre);
	        tabuleiro.placePiece(torre, destinoTorre);
	        torre.increaseMoveCount();
	    }

	    // Roque Grande
	    if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
	        Position origemTorre = new Position(origem.getLinha(), origem.getColuna() - 4);
	        Position destinoTorre = new Position(origem.getLinha(), origem.getColuna() - 1);
	        PecaXadrez torre = (PecaXadrez)tabuleiro.removePeca(origemTorre);
	        tabuleiro.placePiece(torre, destinoTorre);
	        torre.increaseMoveCount();
	    }
	    
	    // En Passant
	    if (p instanceof Peao) {
	        if (origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
	            Position posicaoPeaoCapturado;
	            if (p.getColor() == Color.WHITE) {
	                posicaoPeaoCapturado = new Position(destino.getLinha() + 1, destino.getColuna());
	            } else {
	                posicaoPeaoCapturado = new Position(destino.getLinha() - 1, destino.getColuna());
	            }
	            pecaCapturada = tabuleiro.removePeca(posicaoPeaoCapturado);
	            pecaCapturadas.add(pecaCapturada);
	            pecaNoTabuleiro.remove(pecaCapturada);
	        }
	    }

	    return pecaCapturada;
	}
	private void desfazerMovimento(Position origem, Position destino, PECA pecaCapturada) {
	    PecaXadrez p = (PecaXadrez)tabuleiro.removePeca(destino);
	    p.decreaseMoveCount();
	    tabuleiro.placePiece(p, origem);

	    if (pecaCapturada != null) {
	        tabuleiro.placePiece(pecaCapturada, destino);
	        pecaCapturadas.remove(pecaCapturada);
	        pecaNoTabuleiro.add(pecaCapturada);
	    }

	    // Roque Pequeno
	    if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
	        Position origemTorre = new Position(origem.getLinha(), origem.getColuna() + 3);
	        Position destinoTorre = new Position(origem.getLinha(), origem.getColuna() + 1);
	        PecaXadrez torre = (PecaXadrez)tabuleiro.removePeca(destinoTorre);
	        tabuleiro.placePiece(torre, origemTorre);
	        torre.decreaseMoveCount();
	    }

	    // Roque Grande
	    if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
	        Position origemTorre = new Position(origem.getLinha(), origem.getColuna() - 4);
	        Position destinoTorre = new Position(origem.getLinha(), origem.getColuna() - 1);
	        PecaXadrez torre = (PecaXadrez)tabuleiro.removePeca(destinoTorre);
	        tabuleiro.placePiece(torre, origemTorre);
	        torre.decreaseMoveCount();
	    }
	    
	    // Desfazendo En Passant
	    if (p instanceof Peao) {
	     
	        if (origem.getColuna() != destino.getColuna() && pecaCapturada == enPassant) {
	       
	            PecaXadrez peaoCapturado = (PecaXadrez)tabuleiro.removePeca(destino);
	            Position posicaoOriginalPeao;
	            if (p.getColor() == Color.WHITE) {
	                posicaoOriginalPeao = new Position(3, destino.getColuna());
	            } else {
	                posicaoOriginalPeao = new Position(4, destino.getColuna());
	            }
	            tabuleiro.placePiece(peaoCapturado, posicaoOriginalPeao);
	        }
	    }
	}
				
    //enPassant
//	if(p instanceof Peao && origem.getColuna() != destino.getColuna() && pecaCapturada == enPassant) {
//					
//		PecaXadrez peaoCapturado = (PecaXadrez)pecaCapturada;
//		       Position peaoCapturadoPosition;
//		       if(p.getColor() == Color.WHITE){
//		    	   peaoCapturadoPosition = new Position(destino.getLinha() + 1, destino.getColuna());
//		            } else {
//		                peaoCapturadoPosition = new Position(destino.getLinha() - 1, destino.getColuna());
//		            }
//		            tabuleiro.placePiece(peaoCapturado, peaoCapturadoPosition);
//		        }
//		
//		        else {
//		            tabuleiro.placePiece(pecaCapturada, destino);
//		        }
//		   
//		        pecaCapturadas.remove(pecaCapturada);
//		        pecaNoTabuleiro.add(pecaCapturada);
//		    }
//		    
//	}
	
	private void validarPosicaoOrigem(Position position) {
		if(!tabuleiro.temPeca(position)) {
			throw new CheesException("Não existe peça na posição de origem");
		}
		
		if(jogador != ((PecaXadrez)tabuleiro.peca(position)).getColor()) {
			throw new CheesException("A peça escolhida não é sua");
		}
		if(!tabuleiro.peca(position).verPossibilidadeMovimento()) {
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
		receberCoordenada('a', 1, new Torre(tabuleiro, Color.WHITE));
		receberCoordenada('h', 1, new Torre(tabuleiro, Color.WHITE));
		receberCoordenada('d', 1, new Rainha(tabuleiro, Color.WHITE));
		receberCoordenada('c', 1, new Bispo(tabuleiro, Color.WHITE));
		receberCoordenada('f', 1, new Bispo(tabuleiro, Color.WHITE));
		receberCoordenada('b', 1, new Cavalo(tabuleiro, Color.WHITE));
		receberCoordenada('g', 1, new Cavalo(tabuleiro, Color.WHITE));
		receberCoordenada('e', 1, new Rei( tabuleiro, Color.WHITE, this));
		receberCoordenada('a', 2, new Peao(tabuleiro, Color.WHITE, this));
		receberCoordenada('b', 2, new Peao(tabuleiro, Color.WHITE, this));
		receberCoordenada('c', 2, new Peao(tabuleiro, Color.WHITE, this));
		receberCoordenada('d', 2, new Peao(tabuleiro, Color.WHITE, this));
		receberCoordenada('e', 2, new Peao(tabuleiro, Color.WHITE, this));
		receberCoordenada('f', 2, new Peao(tabuleiro, Color.WHITE, this));
		receberCoordenada('g', 2, new Peao(tabuleiro, Color.WHITE, this));
		receberCoordenada('h', 2, new Peao(tabuleiro, Color.WHITE, this));
		
		
		receberCoordenada('e', 8, new Rei( tabuleiro, Color.BLACK, this));
		receberCoordenada('a', 8, new Torre(tabuleiro, Color.BLACK));
		receberCoordenada('h', 8, new Torre(tabuleiro, Color.BLACK));
		receberCoordenada('d', 8, new Rainha(tabuleiro, Color.BLACK));
		receberCoordenada('c', 8, new Bispo(tabuleiro, Color.BLACK));
		receberCoordenada('f', 8, new Bispo(tabuleiro, Color.BLACK));
		receberCoordenada('b', 8, new Cavalo(tabuleiro, Color.BLACK));
		receberCoordenada('g', 8, new Cavalo(tabuleiro, Color.BLACK));
		receberCoordenada('a', 7, new Peao(tabuleiro, Color.BLACK, this));
		receberCoordenada('b', 7, new Peao(tabuleiro, Color.BLACK, this));
		receberCoordenada('c', 7, new Peao(tabuleiro, Color.BLACK, this));
		receberCoordenada('d', 7, new Peao(tabuleiro, Color.BLACK, this));
		receberCoordenada('e', 7, new Peao(tabuleiro, Color.BLACK, this));
		receberCoordenada('f', 7, new Peao(tabuleiro, Color.BLACK, this));
		receberCoordenada('g', 7, new Peao(tabuleiro, Color.BLACK, this));
		receberCoordenada('h', 7, new Peao(tabuleiro, Color.BLACK, this));
	}
	
}

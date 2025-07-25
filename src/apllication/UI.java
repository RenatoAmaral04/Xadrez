package apllication;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.Color;
import chess.PartidaXadrez;
import chess.PecaXadrez;
import chess.PosicaoXadrez;

public class UI {
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	// https://stackoverflow.com/questions/2979383/java-clear-the-console 
	public static void clearScreen() {   
	System.out.print("\033[H\033[2J");   
	System.out.flush();   
	}   
	
	public static PosicaoXadrez receberPosicao(Scanner ff) {
		
		try {
			String s = ff.nextLine();
			char coluna = s.charAt(0);
			int linha = Integer.parseInt(s.substring(1));
			return new PosicaoXadrez(coluna, linha);
		}catch (RuntimeException e){
			throw new InputMismatchException("ERRO!! Valores válidos são de a1 até h8.");
		}
	
	}

	public static void printPartida(PartidaXadrez partidaXadrez, List<PecaXadrez> capturada) {
		
		printTabuleiro(partidaXadrez.getPecas());
		System.out.println();
		printPecaCapturada(capturada);
		System.out.println();
		System.out.println("Jogada " + partidaXadrez.getVezJogar());
		System.out.println("Vez das: " + partidaXadrez.getJogador());
		
		if(partidaXadrez.getCheckMate()) {
			System.out.println("CHECKMATE!! ");
			System.out.println("Vencedor: " + partidaXadrez.getJogador());
			
			
			
			}else {
				if(partidaXadrez.getCheck()) {
					System.out.println("CHECK");
			}
		}
		
	}
	
	public static void printTabuleiro(PecaXadrez[][] pecas) {
		
		for (int i =0; i< pecas.length; i++) {
			
			System.out.print((8-i) + " ");
			for(int j = 0; j<pecas.length; j++) {
				printPeca(pecas[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	public static void printTabuleiro(PecaXadrez[][] pecas, boolean[][] possivelMov) {
		
		for (int i =0; i< pecas.length; i++) {
			
			System.out.print((8-i) + " ");
			for(int j = 0; j<pecas.length; j++) {
				printPeca(pecas[i][j], possivelMov[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	
	private static void printPeca(PecaXadrez peca, boolean background) {
		
		if(background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		
		if (peca == null) {
			System.out.print("-");
		}else {
			
			if(peca.getColor() == Color.WHITE) {
				System.out.print(ANSI_WHITE + peca);
			} else {
				System.out.print(ANSI_YELLOW + peca);
			}
		}
		 System.out.print(ANSI_RESET);
		System.out.print(" ");
	}
	
	private static void printPecaCapturada(List<PecaXadrez> capturada) {
		
		List<PecaXadrez> white = capturada.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
		List<PecaXadrez> black = capturada.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
		System.out.println("Peças capturadas: ");
		System.out.println("Brancas: ");
		System.out.println(ANSI_WHITE);
		System.out.println(Arrays.toString(white.toArray()));
		System.out.println(ANSI_RESET);
		System.out.println("Peças capturadas: ");
		System.out.println("Pretas: ");
		System.out.println(ANSI_YELLOW);
		System.out.println(Arrays.toString(black.toArray()));
		System.out.println(ANSI_RESET);
	}
}

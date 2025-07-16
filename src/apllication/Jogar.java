package apllication;

import java.util.InputMismatchException;
import java.util.Scanner;

import boardgame.Position;
import boardgame.Tabuleiro;
import chess.CheesException;
import chess.PartidaXadrez;
import chess.PecaXadrez;
import chess.PosicaoXadrez;

public class Jogar {

	public static void main(String[] args) {

		Scanner ff = new Scanner(System.in);
		PartidaXadrez partida = new PartidaXadrez();
		
		while(true) {
			try {
				
				UI.clearScreen();
				UI.printTabuleiro(partida.getPecas());
				System.out.println();
				System.out.println("Origem: ");
				PosicaoXadrez origem = UI.receberPosicao(ff);
				
				System.out.println();
				System.out.println("Destino: ");
				PosicaoXadrez destino = UI.receberPosicao(ff);
				
				PecaXadrez pecaCapturada = partida.movimentoPeca(origem, destino);
				
			}catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				ff.nextLine();
			}
			catch(CheesException e){
				System.out.println(e.getMessage());
				ff.nextLine();
			}
			
		}
		
		
	}

}

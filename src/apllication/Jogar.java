package apllication;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
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
		List<PecaXadrez> capturada = new ArrayList<>();
		
		while(!partida.getCheckMate()) {
			try {
				
				UI.clearScreen();
				UI.printPartida(partida, capturada);
				System.out.println();
				System.out.println("Origem: ");
				PosicaoXadrez origem = UI.receberPosicao(ff);
				
				boolean[][] possiveisMov = partida.possiveisMov(origem);
				UI.clearScreen();
				UI.printTabuleiro(partida.getPecas(), possiveisMov);
				System.out.println();
				
				System.out.println();
				System.out.println("Destino: ");
				PosicaoXadrez destino = UI.receberPosicao(ff);
				
				PecaXadrez pecaCapturada = partida.movimentoPeca(origem, destino);
				
				if(pecaCapturada != null) {
					capturada.add(pecaCapturada);
				}
				
				if(partida.getPromocao() != null) {
					System.out.println("Digite apara qual peca você quer promover (B/C/D/T): ");
					String type = ff.nextLine();
					partida.pecaPromovida(type);
				}
				
			}catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				ff.nextLine();
			}
			catch(CheesException e){
				System.out.println(e.getMessage());
				ff.nextLine();
			}
			
		}
		UI.clearScreen();
		UI.printPartida(partida, capturada);
		
		
	}

}

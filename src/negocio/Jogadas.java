/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import javax.swing.JLabel;
import model.Casa;
import telatrilha.JogoTrilha;

/**
 *
 * @author Joamila
 * Created: 21/02/2016
 * Last Modified: 29/02/2016
 */
public class Jogadas {
    
    java.util.List<String> nomeCasa =  Arrays.asList("a1Label", "a2Label", "a3Label", 
                "b1Label", "b2Label", "b3Label",
                "c1Label", "c2Label", "c3Label",
                "d1Label", "d2Label", "d3Label",
                "d4Label", "d5Label", "d6Label",
                "e1Label", "e2Label", "e3Label",
                "f1Label", "f2Label", "f3Label",
                "g1Label", "g2Label", "g3Label");
    java.util.List<String> nomePeca = Arrays.asList("p1Label", "p2Label", "p3Label", "p4Label", "p5Label",
            "p6Label", "p7Label", "p8Label", "p9Label");
    
    /** Informa ao jogador que é a vez do adversário jogar.
     * 
     * @param tabuleiro JogoTrilha 
     */
    public void passarVez(JogoTrilha tabuleiro){
        tabuleiro.getStatusJogoLabel().setText("Seu adversário está jogando. Aguarde sua vez!");
        
        tabuleiro.getReiniciarJogoButton().setEnabled(false);
        tabuleiro.getVoltarJogoButton().setEnabled(false);
        tabuleiro.getDesistirJogoButton().setEnabled(false);       
    }
    
    /** Informa ao jogador que é a sua vez de jogar.
     * 
     * @param tabuleiro JogoTrilha
     */
    public void retomarVez(JogoTrilha tabuleiro){
        tabuleiro.getStatusJogoLabel().setText("Agora é a sua vez. Capriche na jogada!");
        
        tabuleiro.getReiniciarJogoButton().setEnabled(true);
        tabuleiro.getVoltarJogoButton().setEnabled(true);
        tabuleiro.getDesistirJogoButton().setEnabled(true);
    }
    
    /** Faz a verificação da validade da jogada.
     * Avalia se a peça pertence ao jogador que tenta movê-la.
     * Avalia se a casa para onde a peça será movida está vazia.
     * Avalia se a peça pode ser excluída, quando a formação de um moinho é 
     * detectada.
     * 
     * @param tabuleiro JogoTrilha
     * @param peca JLabel
     * @param minhaCor String
     * @param pecas Map
     * @param pos int
     * @return String
     */
    public String verificarValidadePeca(JogoTrilha tabuleiro, JLabel peca, String minhaCor, Map<String, Casa> pecas, int pos){
        String casa ="";
        
        if(pos == 1){ // Jogada inicial
            tabuleiro.getSelecaoPecaLabel().setText("Peça selecionada!");
            
            if(minhaCor.equals(pecas.get(peca.getName()).getCor())){
                casa = peca.getName();
            }
            else if(pecas.get(peca.getName()).getCor().equals("vazia")){    
                tabuleiro.getStatusJogoLabel().setText("Atenção, não há nenhuma peça nesta casa!");
            }
            else{
                tabuleiro.getStatusJogoLabel().setText("Atenção, use apenas suas próprias peças!");
            }
        }
        else if (pos == 2){ //Jogada final
            tabuleiro.getSelecaoPecaLabel().setText("Casa selecionada!");
            
            if(pecas.get(peca.getName()).getCor().equals("vazia")){
                casa = peca.getName();
            }
            else{
                tabuleiro.getStatusJogoLabel().setText("Atenção, mova a peça para uma casa vazia");
            }
        }
        else{ //pos=3 Exclusão de peça
            tabuleiro.getSelecaoPecaLabel().setText("Peça selecionada!");
            
            if(minhaCor.equals(pecas.get(peca.getName()).getCor())){
                tabuleiro.getStatusJogoLabel().setText("Atenção, selecione uma peça adversária!");
            }
            else if(pecas.get(peca.getName()).getCor().equals("vazia")){    
                tabuleiro.getStatusJogoLabel().setText("Atenção, não há nenhuma peça nesta casa!");
            }
            else{
                casa = peca.getName();
            }
        }
        
        return casa;
    }
    
    /** Checa se as casas vizinhas (as duas próximas) têm peças com a mesma
     * cor da última peça jogada. Caso sim, um moinho é identificado.
     * 
     * @param casa String
     * @param pecas Map
     * @return boolean
     */
    public boolean checarVizinhos(String casa, Map<String, Casa> pecas){
        String vizinho1 = pecas.get(casa).getVizinhos().get(0);
        String vizinho2 = pecas.get(casa).getVizinhos().get(1);
        String vizinho3 = pecas.get(casa).getVizinhos().get(2);
        String vizinho4 = pecas.get(casa).getVizinhos().get(3);
        
        if ((pecas.get(casa).getCor().equals(pecas.get(vizinho1).getCor()) 
                && pecas.get(casa).getCor().equals(pecas.get(vizinho2).getCor())) 
                || (pecas.get(casa).getCor().equals(pecas.get(vizinho3).getCor())  
                && pecas.get(casa).getCor().equals(pecas.get(vizinho4).getCor()))){
            
            return true;
        }
        else
            return false;
    }
    
    /** Informa o fim de jogo.
     * Se o código for 100, informa a derrota do jogador.
     * Se o código for 200, informa a vitória do jogador.
     * 
     * @param tabuleiro JogoTrilha
     * @param cod int
     */
    public void identificarFimJogo(JogoTrilha tabuleiro, int cod){
        if(cod == 100){ // derrota
            tabuleiro.getStatusJogoLabel().setText("Que pena, você perdeu!");
        }
        else{ // cod=200: vitória
           tabuleiro.getStatusJogoLabel().setText("Parabéns, você VENCEU!"); 
        }
        
        tabuleiro.getIniciarJogoButton().setEnabled(false);
        tabuleiro.getReiniciarJogoButton().setEnabled(false);
        tabuleiro.getVoltarJogoButton().setEnabled(false);
        tabuleiro.getDesistirJogoButton().setEnabled(false);
    }
    
    /** Informa que o jogo terminou empatado.
     * 
     * @param tabuleiro JogoTrilha
     */
    public void informarEmpate(JogoTrilha tabuleiro){
        tabuleiro.getStatusJogoLabel().setText("Ops... o jogo empatou.");
        
        tabuleiro.getIniciarJogoButton().setEnabled(false);
        tabuleiro.getReiniciarJogoButton().setEnabled(false);
        tabuleiro.getVoltarJogoButton().setEnabled(false);
        tabuleiro.getDesistirJogoButton().setEnabled(false);
    }
    
    /** Checa se o jogador tem todas as suas peças trancadas, ou seja, impedidas
     * de fazer uma jogada válida.
     * Consulta todos os vizinhos imediatos de cada casa para verificar se há 
     * casas vazias. Caso não, o trancamento é identificado.
     * 
     * @param tabuleiro JogoTrilha
     * @param casas Map
     * @param cor String
     * @return boolean
     */
    public boolean identificarTrancamento(JogoTrilha tabuleiro, Map<String, Casa> casas, String cor){
        int casaVazia = 0;
        ArrayList<String> minhasCasas = new ArrayList();
        
        for (String peca : nomeCasa){
            if(casas.get(peca).getCor().equals(cor)){
                minhasCasas.addAll(casas.get(peca).getVizinhosImediatos());
            }
        }
        
        for(String mCasa : minhasCasas){
            if(casas.get(mCasa).getCor().equals("vazia")){
                casaVazia += 1;
            }
        }
       
        return casaVazia <= 0;
    }
}

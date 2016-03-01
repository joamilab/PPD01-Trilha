/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import model.Casa;
import telatrilha.JogoTrilha;

/**
 *
 * @author Joamila
 * Created: 19/02/2016
 * Last Modified: 26/02/2016
 */
public class Tabuleiro {
    private Map<String,Casa> casas;
    private int qtdPecas;
    private int qtdPecasJogadas;
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
    
    public Map<String, Casa> getCasas(){
        return casas;
    }
    public int getQtdPecas(){ return this.qtdPecas; }
    public void setQtdPecas(int qtdPecas){ this.qtdPecas = qtdPecas; }
    public int getQtdPecasJogadas(){ return this.qtdPecasJogadas; }
    public void setQtdPecasJogadas(int qtdPecasJogadas){ this.qtdPecasJogadas = qtdPecasJogadas; }
    
    public Tabuleiro(){
        this.casas = new HashMap();
        this.qtdPecas = 9;
        this.qtdPecasJogadas = 0;
    }
    
    /** Retira uma peça do tabuleiro que esteja na casa informada.
     * Altera a cor da casa no HashMap para "vazia".
     * Se o código enviado for o 100, indica que as peças iniciais podem ser 
     * retiradas da caixa de peças fora do tabuleiro.
     * Caso contrário, a opção de retirar peças inicais não é considerada.
     * 
     * @param tabuleiro JogoTrilha
     * @param casa String
     * @param cod int
     */
    public void retirarPeca(JogoTrilha tabuleiro, String casa, int cod){ 
        java.util.List<JLabel> pecas = Arrays.asList(tabuleiro.getA1Label(), tabuleiro.getA2Label(), tabuleiro.getA3Label(), 
                tabuleiro.getB1Label(), tabuleiro.getB2Label(), tabuleiro.getB3Label(),
                tabuleiro.getC1Label(), tabuleiro.getC2Label(), tabuleiro.getC3Label(),
                tabuleiro.getD1Label(), tabuleiro.getD2Label(), tabuleiro.getD3Label(),
                tabuleiro.getD4Label(), tabuleiro.getD5Label(), tabuleiro.getD6Label(),
                tabuleiro.getE1Label(), tabuleiro.getE2Label(), tabuleiro.getE3Label(),
                tabuleiro.getF1Label(), tabuleiro.getF2Label(), tabuleiro.getF3Label(),
                tabuleiro.getG1Label(), tabuleiro.getG2Label(), tabuleiro.getG3Label());
        
        java.util.List<JLabel> pecasIniciais = Arrays.asList(tabuleiro.getP1Label(), tabuleiro.getP2Label(), tabuleiro.getP3Label(),
                tabuleiro.getP4Label(), tabuleiro.getP5Label(), tabuleiro.getP6Label(),
                tabuleiro.getP7Label(), tabuleiro.getP8Label(), tabuleiro.getP9Label());
        
        if(cod == 100){ //minha jogada
            casas.get(casa).setCor("vazia");
            char pLetra = casa.charAt(0);
            
            if(pLetra == 'p'){
                for(JLabel peca : pecasIniciais){
                    if(peca.getName().equals(casa)){
                        peca.setIcon(null);
                    } 
                }
            }
            else{
               for(JLabel peca : pecas){
                    if(peca.getName().equals(casa)){
                        peca.setIcon(null);
                    } 
                }
            }
        }
        else{ // jogada adversário
            for(JLabel peca : pecas){
                if(peca.getName().equals(casa)){
                    peca.setIcon(null);
                    casas.get(casa).setCor("vazia");
                }
            }
        }
    }
    
    /** Adiciona uma peça com a cor designada no tabuleiro que esteja na casa
     * informada.
     * Altera a cor da casa no HashMap para a cor designada. 
     * 
     * @param tabuleiro JogoTrilha
     * @param peca String
     * @param cor String
     */
    public void adicionarPeca(JogoTrilha tabuleiro, String peca, String cor){
        java.util.List<JLabel> pecas = Arrays.asList(tabuleiro.getA1Label(), tabuleiro.getA2Label(), tabuleiro.getA3Label(), 
                tabuleiro.getB1Label(), tabuleiro.getB2Label(), tabuleiro.getB3Label(),
                tabuleiro.getC1Label(), tabuleiro.getC2Label(), tabuleiro.getC3Label(),
                tabuleiro.getD1Label(), tabuleiro.getD2Label(), tabuleiro.getD3Label(),
                tabuleiro.getD4Label(), tabuleiro.getD5Label(), tabuleiro.getD6Label(),
                tabuleiro.getE1Label(), tabuleiro.getE2Label(), tabuleiro.getE3Label(),
                tabuleiro.getF1Label(), tabuleiro.getF2Label(), tabuleiro.getF3Label(),
                tabuleiro.getG1Label(), tabuleiro.getG2Label(), tabuleiro.getG3Label(),
                tabuleiro.getP1Label(), tabuleiro.getP2Label(), tabuleiro.getP3Label(),
                tabuleiro.getP4Label(), tabuleiro.getP5Label(), tabuleiro.getP6Label(),
                tabuleiro.getP7Label(), tabuleiro.getP8Label(), tabuleiro.getP9Label());
        
        casas.get(peca).setCor(cor);
        
        if(cor.equals("azul")){
            for(JLabel casa : pecas){
                if(casa.getName().equals(peca)){
                    casa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/telatrilha/peca_azul.png")));
                }
            }
        }
        else{
            for(JLabel casa : pecas){
                if(casa.getName().equals(peca)){
                    casa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/telatrilha/peca_vermelha.png")));
                }
            }
        }
    
    }
    
    /** Atualiza o tabuleiro, retirando e adicionando uma peça nas casas informadas
     * depois de uma jogada.
     * 
     * @param tabuleiro JogoTrilha
     * @param cor String
     * @param partida String
     * @param chegada String
     * @param cod int
     */
    public void atualizarTabuleiro(JogoTrilha tabuleiro, String cor, String partida, String chegada, int cod){
        retirarPeca(tabuleiro, partida, cod);
        adicionarPeca(tabuleiro, chegada, cor);
    }
    
    /** Inicia as 9 peças com a cor designada.
     * As peças são atribuídas à caixa de peças.
     * As peças são adicionadas no HashMap.
     * 
     * @param tabuleiro JogoTrilha
     * @param cor String
     */
    public void iniciarPecas(JogoTrilha tabuleiro, String cor){
        java.util.List<JLabel> pecasIniciais = Arrays.asList(tabuleiro.getP1Label(), tabuleiro.getP2Label(), tabuleiro.getP3Label(),
                tabuleiro.getP4Label(), tabuleiro.getP5Label(), tabuleiro.getP6Label(),
                tabuleiro.getP7Label(), tabuleiro.getP8Label(), tabuleiro.getP9Label());
        
        if(cor.equals("azul")){
            for(JLabel peca : pecasIniciais){
                casas.put(peca.getName(), new Casa(peca.getName()));
                casas.get(peca.getName()).setCor(cor);
                peca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/telatrilha/peca_azul.png")));
            }
        }
        else{
           for(JLabel peca : pecasIniciais){
                casas.put(peca.getName(), new Casa(peca.getName()));
                casas.get(peca.getName()).setCor(cor);
                peca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/telatrilha/peca_vermelha.png")));
            } 
        }
    }
    
    /** Reinicia as peças depois de um pedido de reinício de partida.
     * 
     * @param tabuleiro JogoTrilha
     * @param cor String
     */
    public void reiniciarPecas(JogoTrilha tabuleiro, String cor){
        java.util.List<JLabel> pecasIniciais = Arrays.asList(tabuleiro.getP1Label(), tabuleiro.getP2Label(), tabuleiro.getP3Label(),
                tabuleiro.getP4Label(), tabuleiro.getP5Label(), tabuleiro.getP6Label(),
                tabuleiro.getP7Label(), tabuleiro.getP8Label(), tabuleiro.getP9Label());
        
        if(cor.equals("azul")){
            for(JLabel peca : pecasIniciais){
                casas.get(peca.getName()).setCor(cor);
                peca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/telatrilha/peca_azul.png")));
            }
        }
        else{
           for(JLabel peca : pecasIniciais){
                casas.get(peca.getName()).setCor(cor);
                peca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/telatrilha/peca_vermelha.png")));
            } 
        }
    }
    
    /** Inicia as casas do tabuleiro com a cor "vazia".
     * Insere as casas no HashMap.
     * 
     * @param tabuleiro JogoTrilha
     */
    public void iniciarTabuleiro(JogoTrilha tabuleiro){
        String corInicial = "vazia";
        
        for(int i = 0; i < nomeCasa.size(); i++){
            casas.put(nomeCasa.get(i), new Casa(nomeCasa.get(i)));
            casas.get(nomeCasa.get(i)).setCor(corInicial);
        }        
    }
        
    /** Reinicia o tabuleiro depois de um pedido de reinício de partida.
     * 
     * @param tabuleiro JogoTrilha
     */
    public void reiniciarTabuleiro(JogoTrilha tabuleiro){
       String corInicial = "vazia";
       
        for(int i = 0; i < nomeCasa.size(); i++){
            //casas.get(nomeCasa.get(i)).setCor(corInicial);
            retirarPeca(tabuleiro, nomeCasa.get(i), 200);
        }   
    }
     
    /** Desabilita os botões: Reiniciar, Voltar e Desistir.
     * 
     * @param tabuleiro JogoTrilha
     */
    public void desabilitaBotoes(JogoTrilha tabuleiro){
        java.util.List<JButton> botoes = Arrays.asList(tabuleiro.getReiniciarJogoButton(), tabuleiro.getVoltarJogoButton(),
                tabuleiro.getDesistirJogoButton());
         
        for (JButton botao : botoes) {
           botao.setEnabled(false);
        }
    }
     
    /** Habilita os botões: Reiniciar, Voltar e Desistir.
     * 
     * @param tabuleiro JogoTrilha
     */
    public void habilitaBotoes(JogoTrilha tabuleiro){
        java.util.List<JButton> botoes = Arrays.asList(tabuleiro.getReiniciarJogoButton(), tabuleiro.getVoltarJogoButton(),
                tabuleiro.getDesistirJogoButton());
         
        for (JButton botao : botoes) {
            botao.setEnabled(true);
        } 
    }
}

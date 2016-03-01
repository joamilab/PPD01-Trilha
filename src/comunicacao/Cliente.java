/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comunicacao;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import model.Mensagem;
import negocio.Jogadas;
import negocio.Tabuleiro;
import telatrilha.JogoTrilha;

/**
 *
 * @author Joamila
 * based in: http://www.devmedia.com.br/como-criar-um-chat-multithread-com-socket-em-java/33639
 * Created: 15/02/2016
 * Last Modified: 01/03/2016
 */
public class Cliente extends JFrame implements KeyListener, ActionListener, MouseListener, WindowListener{
    
    //Comunicação
    private Socket socketCl;
    private OutputStream out;
    private Writer outWr; 
    private BufferedWriter bufWr;
    String host = "localhost";
    
    //Tela inicial
    private JTextField txtNome;
    public JogoTrilha tabuleiro;
    
    //Variáveis auxiliares
    int flagJogadaEncerrada = 0;
    int flagTrilha = 0;
    String jogadaIni = "";
    String jogadaFin = "";
    String iniUltimaJog = "";
    String finUltimaJog = "";
    Tabuleiro tab = new Tabuleiro();
    String corPecas = "";
    String corPecasOutro = "";
    Jogadas jogadas = new Jogadas();
    int pecasAdversario = 9;
    int qtdJogadasRestantes = 10;
    
    /** Construtor da classe Cliente.
     * Constrói uma tela auxiliar para adquirir o nome do usuário.
     * Constrói a janela com tabuleiro e chat.
     * Inicia o tabuleiro e as suas casas.
     * 
     * @throws IOException 
     */
    public Cliente() throws IOException{
        JLabel lblMessage = new JLabel("Digite seu nome");
        txtNome = new JTextField("User");
        Object[] texts = {lblMessage, txtNome};
        JOptionPane.showMessageDialog(null, texts);
        
        this.tabuleiro = new JogoTrilha();
        this.tabuleiro.setVisible(true);

        iniciaListenerButtons();
        iniciaCasas();
        tab.iniciarTabuleiro(tabuleiro);
        tab.desabilitaBotoes(tabuleiro);
        }
    
    /**
     * Estabelece a conexão do cliente com a porta 12346, permitindo assim a 
     * comunicação com o servidor.
     */
    void estabeleceConexao(){
        try {
            socketCl = new Socket(host, 12346);
            
            out = socketCl.getOutputStream();
            outWr = new OutputStreamWriter(out);
            bufWr = new BufferedWriter(outWr);
            bufWr.write(txtNome.getText() + "\r\n");
            bufWr.flush();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Habilita os listeners dos botões, campo de texto onde a conversa do chat 
     * é digitada e da janela.
     */
    void iniciaListenerButtons(){
        java.util.List<JButton> botoes = Arrays.asList(tabuleiro.getEnviarButton(), tabuleiro.getIniciarJogoButton(),
                tabuleiro.getReiniciarJogoButton(), tabuleiro.getVoltarJogoButton(), tabuleiro.getDesistirJogoButton());
        
        for (JButton botao : botoes) {
            botao.addActionListener(this);
            botao.addKeyListener(this);
        }
  
        tabuleiro.getMensagemTextField().addKeyListener(this);
        tabuleiro.addWindowListener(this);
    }
    
    /**
     * Inicia as casas, atribuindo um nome e habilitando o listener de cada label
     * correspondentes às casas.
     */
    void iniciaCasas(){
        java.util.List<JLabel> casas = Arrays.asList(tabuleiro.getA1Label(), tabuleiro.getA2Label(), tabuleiro.getA3Label(), 
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
        
        java.util.List<String> nomeLabel =  Arrays.asList("a1Label", "a2Label", "a3Label", "b1Label", "b2Label", "b3Label",
                "c1Label", "c2Label", "c3Label", "d1Label", "d2Label", "d3Label", "d4Label", "d5Label", "d6Label",
                "e1Label", "e2Label", "e3Label", "f1Label", "f2Label", "f3Label", "g1Label", "g2Label", "g3Label",
                "p1Label", "p2Label", "p3Label", "p4Label", "p5Label", "p6Label", "p7Label", "p8Label", "p9Label");
        
        for(int i = 0; i < casas.size(); i++){
            casas.get(i).addMouseListener(this);
            casas.get(i).setName(nomeLabel.get(i));
        }
    }
    
    /**
     * Desabilita os listeners das casas, impedindo assim que elas sejam clicadas.
     */
    void desabilitaListeners(){
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
         
         for(JLabel casa : pecas){
             casa.removeMouseListener(this);
         }
    }
    
    /**
     * Habilita os listeners das casas, permitindo que elas sejam clicadas.
     */
    void habilitaListenersLabel(){
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
         
         for(JLabel casa : pecas){
             casa.addMouseListener(this);
         }
    }
    
    /** Define a cor inicial como azul para o primeiro jogador a clicar no botão
     * iniciar.
     * Informa ao outro jogador que a sua cor será a vermelha.
     * 
     * @param cor String
     */
    void definirCorInicial(String cor){
        String corSec =  Mensagem.DEFINE_COR_VERMELHA;
        tab.iniciarPecas(tabuleiro, cor);
        tabuleiro.getStatusJogoLabel().setForeground(Color.BLUE);
        try {
            enviarMensagem(corSec + "\r\n");
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    	
    /** Envia mensagens que serão direcionadas ao servidor.
     * 
     * @param msg String
     * @param cod int
     * @throws IOException 
     */
    public void enviarMensagem(String msg) throws IOException{
        
        if((msg.contains(Mensagem.JOGADA_INICIO)&& msg.contains(Mensagem.JOGADA_FINAL)) || 
                        msg.contains(Mensagem.DEFINE_COR_VERMELHA) || 
                        (msg.contains(Mensagem.EXCLUI_PECA_INICIO)) && msg.contains(Mensagem.EXCLUI_PECA_FINAL) ||
                        msg.contains(Mensagem.INFORMA_VITORIA) || msg.contains(Mensagem.VOLTA_JOGO) ||
                        msg.contains(Mensagem.SOLICITA_REINICIO) ||
                        (msg.contains(Mensagem.RESPOSTA_REINICIO_INICIO) && msg.contains(Mensagem.RESPOSTA_REINICIO_FINAL)) ||
                        msg.contains(Mensagem.DESISTE_JOGO) || msg.contains(Mensagem.DESCONECTA)){
            bufWr.write(msg + "\r\n");
        }
        else{
           bufWr.write(msg + "\r\n");
            tabuleiro.conversaTextArea.append(txtNome.getText() + ": " + tabuleiro.getMensagemTextField().getText() + "\r\n"); 
        }
        
        bufWr.flush(); 
        tabuleiro.getMensagemTextField().setText("");
    }

    /** Escuta as mensagens enviadas pelo servidor.
     * 
     * @throws IOException 
     */
    void escutarPorta() throws IOException{
        InputStream in = socketCl.getInputStream();
        InputStreamReader inReader = new InputStreamReader(in);
        BufferedReader bufRead = new BufferedReader(inReader);
        String mensagem = "";
             
        while(!Mensagem.DESCONECTA.equalsIgnoreCase(mensagem)){
            if(bufRead.ready()){
                mensagem = bufRead.readLine();
                
                //O adversário fechou a janela.
                if(mensagem.contains(Mensagem.SAIR_JANELA)){
                    tabuleiro.getStatusJogoLabel().setText("Seu adversário saiu da sala.\r\n");
                    desabilitaListeners();
                    tab.desabilitaBotoes(tabuleiro);
                    tabuleiro.getIniciarJogoButton().setEnabled(false);
                }
                //O adversário fez uma jogada.
                else if(mensagem.contains(Mensagem.JOGADA_INICIO) && mensagem.contains(Mensagem.JOGADA_FINAL)){
                    iniUltimaJog = mensagem.substring(Mensagem.JOGADA_INICIO.length(), Mensagem.JOGADA_INICIO.length()+7);
                    finUltimaJog = mensagem.substring(Mensagem.JOGADA_FINAL.length()+7, Mensagem.JOGADA_FINAL.length()+14);
                    
                    tab.atualizarTabuleiro(tabuleiro, corPecasOutro, iniUltimaJog, finUltimaJog, 200);
                    //O adversário informou que possui apenas 3 peças.
                    if(mensagem.contains(Mensagem.TRES_PECAS)){
                        pecasAdversario = 3;
                    }
                    //O adversário detectou um empate.
                    if(mensagem.contains(Mensagem.INFORMA_EMPATE)){
                        jogadas.informarEmpate(tabuleiro);
                        desabilitaListeners();
                    }
                    //O adversário informou que fez um moinho.
                    else if(mensagem.contains(Mensagem.INFORMA_TRILHA)){
                        tabuleiro.getStatusJogoLabel().setText("Seu adversário fez um moinho. Aguarde enquanto uma peça"
                                + " é excluida!");
                    }
                    //O adversário fez uma jogada normal.
                    else{
                        //O jogador tem 3 peças e já jogou todas as peças iniciais.
                        if(tab.getQtdPecas()>3 && tab.getQtdPecasJogadas()>=9){
                            //O jogador identificou um trancamento.
                            if(jogadas.identificarTrancamento(tabuleiro, tab.getCasas(), corPecas)){
                                String msgFinal = Mensagem.INFORMA_VITORIA + "\r\n";
                                jogadas.identificarFimJogo(tabuleiro, 100);
                                enviarMensagem(msgFinal);  
                            }
                            //Não houve trancamento.
                            else{
                                jogadas.retomarVez(tabuleiro);
                                habilitaListenersLabel();
                            }
                        }
                        //O jogador tem mais de 3 peças ou ainda não jogou todas as peças iniciais.
                        else{
                            jogadas.retomarVez(tabuleiro);
                            habilitaListenersLabel();
                        }         
                    }
                }
                //O adversário informou que a cor dele é a azul e a sua é a vermelha.
                else if(mensagem.contains(Mensagem.DEFINE_COR_VERMELHA)){
                    corPecas = "vermelha";
                    corPecasOutro = "azul";
                    
                    tab.iniciarPecas(tabuleiro, corPecas);
                    tab.desabilitaBotoes(tabuleiro);
                    desabilitaListeners();
                    tabuleiro.getStatusJogoLabel().setForeground(Color.red);
                    tabuleiro.getIniciarJogoButton().setEnabled(false);
                    tabuleiro.getStatusJogoLabel().setText("Seu adversário iniciará o jogo.");
                }
                //O adversário excluiu uma peça sua.
                else if(mensagem.contains(Mensagem.EXCLUI_PECA_INICIO) && mensagem.contains(Mensagem.EXCLUI_PECA_FINAL)){
                    String casa = mensagem.substring(Mensagem.EXCLUI_PECA_INICIO.length(), Mensagem.EXCLUI_PECA_INICIO.length()+7);
                    
                    tab.retirarPeca(tabuleiro, casa, 200);
                    tabuleiro.getStatusJogoLabel().setText("Que pena, você perdeu uma peça!");
                    jogadas.retomarVez(tabuleiro);
                    tab.setQtdPecas(tab.getQtdPecas()-1);
                    habilitaListenersLabel();
                    
                    //O jogador tem apenas duas peças.
                    if(tab.getQtdPecas() <= 2){
                        String msgFinal = Mensagem.INFORMA_VITORIA + "\r\n";
                        jogadas.identificarFimJogo(tabuleiro, 100);
                        desabilitaListeners();
                        enviarMensagem(msgFinal);  
                    }
                }
                //O adversário informou sua derrota.
                else if(mensagem.contains(Mensagem.INFORMA_VITORIA)){
                    jogadas.identificarFimJogo(tabuleiro, 200);
                    desabilitaListeners();
                }
                //O adversário voltou o jogo.
                else if(mensagem.contains(Mensagem.VOLTA_JOGO)){
                    jogadas.retomarVez(tabuleiro);
                    tab.atualizarTabuleiro(tabuleiro, corPecas, jogadaFin, jogadaIni, 100);
                    habilitaListenersLabel();
                    tab.habilitaBotoes(tabuleiro);
                }
                //O adversário solicitou o reínicio do jogo.
                else if (mensagem.contains(Mensagem.SOLICITA_REINICIO)){
                    int resposta;
                    String msgRespostaReiniciar = "";
                    
                    tabuleiro.getStatusJogoLabel().setText("O seu adversário deseja reiniciar o jogo.");
                    resposta = JOptionPane.showConfirmDialog(null, "Você aceita reiniciar o jogo?");
                    msgRespostaReiniciar = Mensagem.RESPOSTA_REINICIO_INICIO + String.valueOf(resposta) + 
                            Mensagem.RESPOSTA_REINICIO_FINAL + "\r\n";

                    //O jogador concordou com o reinício do jogo.
                    if(resposta == JOptionPane.OK_OPTION){
                        tab.reiniciarTabuleiro(tabuleiro);
                        tab.reiniciarPecas(tabuleiro, corPecas);
                        if(corPecas.equals("azul")){
                            tabuleiro.getStatusJogoLabel().setText("Você iniciará o jogo. Faça sua jogada!");
                            habilitaListenersLabel();
                            tab.habilitaBotoes(tabuleiro);
                        }    
                        else{
                            tabuleiro.getStatusJogoLabel().setText("Seu adversário iniciará o jogo.");
                            desabilitaListeners();
                            tab.desabilitaBotoes(tabuleiro);
                        }       
                    }
                    //O jogador não concordou com o reinício do jogo.
                    else{
                        tabuleiro.getStatusJogoLabel().setText("O jogo não será reiniciado. Aguarde a sua vez!");
                        desabilitaListeners();
                        tab.desabilitaBotoes(tabuleiro);
                    }

                    enviarMensagem(msgRespostaReiniciar);
                }
                //O adversário enviou a resposta da solicitação de reinício do jogo.
                else if(mensagem.contains(Mensagem.RESPOSTA_REINICIO_INICIO) && mensagem.contains(Mensagem.RESPOSTA_REINICIO_FINAL)){
                    String msgResposta = mensagem.substring(Mensagem.RESPOSTA_REINICIO_INICIO.length(), 
                            Mensagem.RESPOSTA_REINICIO_INICIO.length()+1);
                    
                    //O adversário concordou com o reinício do jogo.
                    if(msgResposta.equals(String.valueOf(JOptionPane.OK_OPTION))){
                        tab.reiniciarTabuleiro(tabuleiro);
                        tab.reiniciarPecas(tabuleiro, corPecas);
                        if(corPecas.equals("azul")){
                            tabuleiro.getStatusJogoLabel().setText("Você iniciará o jogo. Faça sua jogada!");
                            habilitaListenersLabel();
                            tab.habilitaBotoes(tabuleiro);
                        }   
                        else{
                            tabuleiro.getStatusJogoLabel().setText("Seu adversário iniciará o jogo."); 
                            desabilitaListeners();
                            tab.desabilitaBotoes(tabuleiro);
                        }     
                    }
                    //O adversário não concordou com o reinício do jogo.
                    else{
                        tabuleiro.getStatusJogoLabel().setText("O jogo não será reiniciado. Faça a sua jogada!");
                        habilitaListenersLabel();
                        tab.habilitaBotoes(tabuleiro);
                    }
                }
                //O adversário desistiu do jogo.
                else if(mensagem.contains(Mensagem.DESISTE_JOGO)){
                    tabuleiro.getStatusJogoLabel().setText("O seu adversário desistiu. Parabéns, você venceu!");
                    desabilitaListeners();
                }
                //O adversário enviou uma mensagem no chat.
                else 
                    tabuleiro.conversaTextArea.append(mensagem + "\r\n");
            }
        }
    }
        
    /** Fecha a janela quando o botão de fechar é clicado.
     * 
     * @throws IOException 
     */
    public void desconectarChat() throws IOException{
            enviarMensagem(Mensagem.SAIR_JANELA);
            bufWr.close();
            outWr.close();
            out.close();
            socketCl.close();
    }

    /** Método principal da classe Cliente.
     * 
     * @param args String[]
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
            // TODO Auto-generated method stub
            Cliente app1 = new Cliente();
            
            app1.estabeleceConexao();
            app1.escutarPorta();
    }


    /** Define ações para o clique em uma tecla.
     * 
     * @param arg0 KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent arg0) {
        //Tecla enter: envia mensagem pelo chat.
        if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
                try {
                    if(!tabuleiro.getMensagemTextField().getText().equals("")){
                        enviarMensagem(tabuleiro.getMensagemTextField().getText());
                    }  
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }

    /**
     * 
     * @param arg0 ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        
        try {
            //Botão enviar: envia mensagem pelo chat.
            if(arg0.getActionCommand().equals(tabuleiro.getEnviarButton().getActionCommand())){
                if(!tabuleiro.getMensagemTextField().getText().equals("")){
                        enviarMensagem(tabuleiro.getMensagemTextField().getText());
                    }
            }
            //Botão iniciar: inicia o jogo no tabuleiro, define a cor das peças.
            else if(arg0.getActionCommand().equals(tabuleiro.getIniciarJogoButton().getActionCommand())){
                corPecas = "azul";
                corPecasOutro = "vermelha";
                
                definirCorInicial(corPecas);
                tabuleiro.getIniciarJogoButton().setEnabled(false);
                tab.habilitaBotoes(tabuleiro);
                tabuleiro.getStatusJogoLabel().setText("Você iniciará o jogo. Faça sua jogada!");
                
            }
            //Botão voltar: volta a jogada no tabuleiro.
            else if(arg0.getActionCommand().equals(tabuleiro.getVoltarJogoButton().getActionCommand())){
                String msgVoltarJogo = Mensagem.VOLTA_JOGO + "\r\n";
                
                if(iniUltimaJog.equals("p1Label") || iniUltimaJog.equals("p2Label") || iniUltimaJog.equals("p3Label")
                        || iniUltimaJog.equals("p4Label") || iniUltimaJog.equals("p5Label") || iniUltimaJog.equals("p6Label")
                        || iniUltimaJog.equals("p7Label") || iniUltimaJog.equals("p8Label") || iniUltimaJog.equals("p9Label")){
                    
                    tab.setQtdPecasJogadas(tab.getQtdPecasJogadas() - 1);
                    tab.retirarPeca(tabuleiro, finUltimaJog, 200);
                }
                else{
                    tab.atualizarTabuleiro(tabuleiro, corPecasOutro, finUltimaJog, iniUltimaJog, 200);
                }
                jogadas.passarVez(tabuleiro);
                desabilitaListeners();
                tab.desabilitaBotoes(tabuleiro);
                enviarMensagem(msgVoltarJogo);
            }
            //Botão reiniciar: solicita o reinício do jogo.
            else if(arg0.getActionCommand().equals(tabuleiro.getReiniciarJogoButton().getActionCommand())){
                String msgReiniciarJogo = Mensagem.SOLICITA_REINICIO + "\r\n";
                
                enviarMensagem(msgReiniciarJogo);
                tabuleiro.getStatusJogoLabel().setText("A solicitação foi enviada. Aguarde a resposta!");
                desabilitaListeners();
                tab.desabilitaBotoes(tabuleiro);
            }
            //Botão desistir: desiste do jogo.
            else if(arg0.getActionCommand().equals(tabuleiro.getDesistirJogoButton().getActionCommand())){
                int resposta;
                String msgDesistirJogo = Mensagem.DESISTE_JOGO + "\r\n";
                resposta = JOptionPane.showConfirmDialog(null, "Você tem certeza que quer desistir?");
                
                if(resposta == JOptionPane.OK_OPTION){
                    tabuleiro.getStatusJogoLabel().setText("Que pena, você perdeu o jogo!");
                    tab.desabilitaBotoes(tabuleiro);
                    desabilitaListeners();
                    enviarMensagem(msgDesistirJogo);
                }       
                else{
                    tabuleiro.getStatusJogoLabel().setText("Que bom, que você não desistiu! Agora faça sua jogada!");
                }           
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /** Define as ações para o clique com o mouse.
     * 
     * @param e MouseEvent 
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        //Seleção da peça a ser movida.
        if(flagJogadaEncerrada == 0 && flagTrilha == 0){
            jogadaIni = jogadas.verificarValidadePeca(tabuleiro, (JLabel) e.getComponent(), corPecas, tab.getCasas(), 1);
            if(!jogadaIni.equals(""))
               flagJogadaEncerrada = 1; 
            }  
        //Seleção da casa para onde a peça será movida.
        else if (flagJogadaEncerrada == 1 && flagTrilha == 0){
            jogadaFin = jogadas.verificarValidadePeca(tabuleiro, (JLabel) e.getComponent(), corPecas, tab.getCasas(), 2);
            if(!jogadaFin.equals("")){
               flagJogadaEncerrada = 0;
               
               //O jogador e o seu adversário têm apenas 3 peças cada.
               if(tab.getQtdPecas() == 3 && pecasAdversario == 3)
                   qtdJogadasRestantes -= 1;
               
               try {
                   String jogada = Mensagem.JOGADA_INICIO + jogadaIni + jogadaFin + Mensagem.JOGADA_FINAL + "\r\n";
                   tab.atualizarTabuleiro(tabuleiro, this.corPecas, jogadaIni, jogadaFin, 100);
                   tab.setQtdPecasJogadas(tab.getQtdPecasJogadas() + 1);
                   
                   //O jogador e o seu adversário tem 3 peças cada e já aconteceram 10 jogadas.
                   if(qtdJogadasRestantes == 0){
                       jogada = Mensagem.JOGADA_INICIO + jogadaIni + jogadaFin + Mensagem.JOGADA_FINAL + 
                                Mensagem.INFORMA_EMPATE + "\r\n";
                       jogadas.informarEmpate(tabuleiro);
                       desabilitaListeners();
                   }
                   //O jogador e o seu adversário tem 3 peças cada, mas ainda não aconteceram 10 jogadas.
                   else{
                       //O jogador tem apenas 3 peças.
                       if(tab.getQtdPecas()==3){
                           jogada = Mensagem.JOGADA_INICIO + jogadaIni + jogadaFin + Mensagem.JOGADA_FINAL + 
                                    Mensagem.TRES_PECAS + "\r\n";
                       }
                       
                       //O jogador formou um moinho.
                       if(jogadas.checarVizinhos(jogadaFin, tab.getCasas())){
                           jogada = Mensagem.JOGADA_INICIO + jogadaIni + jogadaFin + Mensagem.JOGADA_FINAL + 
                                    Mensagem.INFORMA_TRILHA + "\r\n";
                           tabuleiro.getStatusJogoLabel().setText("MOINHO! Você pode excluir uma peça adversária.");
                           flagTrilha = 1;
                       }
                       //O jogador não formou um moinho.
                       else{
                           jogadas.passarVez(tabuleiro);
                           desabilitaListeners();
                       }
                   }
                        
                   enviarMensagem(jogada);
                   
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }  
            }
        }
        //Seleção da peça a ser excluida, caso tenha sido detectada a formação de um moinho.
        else if (flagTrilha == 1){
            String pecaExcluida = "";
            String jogada = "";
            
            pecaExcluida = jogadas.verificarValidadePeca(tabuleiro, (JLabel) e.getComponent(), corPecas, tab.getCasas(), 3);
            if(!pecaExcluida.equals("")){
                flagTrilha = 0;
                jogada = Mensagem.EXCLUI_PECA_INICIO + pecaExcluida + Mensagem.EXCLUI_PECA_FINAL + "\r\n";
                try {
                    tab.retirarPeca(tabuleiro, pecaExcluida, 100);
                    jogadas.passarVez(tabuleiro);
                    desabilitaListeners();
                    enviarMensagem(jogada);
                } catch (IOException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    /** Ação a ser executada quando a janela é fechada.
     * 
     * @param e WindowEvent
     */
    @Override
    public void windowClosing(WindowEvent e) {
        try {
            desconectarChat();
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) { }
    
    @Override
    public void keyReleased(KeyEvent arg0) { }

    @Override
    public void keyTyped(KeyEvent arg0) { }

    @Override
    public void mousePressed(MouseEvent e) { }
  
    @Override
    public void mouseReleased(MouseEvent e) { }
    
    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void windowOpened(WindowEvent e) { }

    @Override
    public void windowIconified(WindowEvent e) {  }

    @Override
    public void windowDeiconified(WindowEvent e) { }

    @Override
    public void windowActivated(WindowEvent e) { }

    @Override
    public void windowDeactivated(WindowEvent e) { }
}

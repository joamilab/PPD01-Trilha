/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comunicacao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import model.Mensagem;

/**
 *
 * @author Joamila
 * based in: http://www.devmedia.com.br/como-criar-um-chat-multithread-com-socket-em-java/33639
 * Created: 15/02/2016
 * Last Modified: 26/02/2016
 */
public class Servidor extends Thread{
    //Comunicação
    private static ArrayList<BufferedWriter>clientes; 
    private static ServerSocket server; 
    private String nome; 
    private Socket con; 
    private InputStream in; 
    private InputStreamReader inr; 
    private BufferedReader bfr;

    /**Construtor da classe Servidor 
     * Inicia uma conexão de tipo server por socket
     * @param conexao Socket 
     */
    public Servidor(Socket conexao){
        this.con = conexao;

        try {
            in = con.getInputStream();
            inr = new InputStreamReader(in);
            bfr = new BufferedReader(inr);
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
   
    }

    /** Método invocado por cada thread criada
     * Uma thread corresponde a um cliente iniciado
     */
    @Override
    public void run(){
        try {
            String mensagem = null;
            OutputStream out = this.con.getOutputStream();
            Writer outWriter = new OutputStreamWriter(out);
            BufferedWriter bufWriter = new BufferedWriter(outWriter);
            clientes.add(bufWriter);
            nome = mensagem = bfr.readLine();

            while(!Mensagem.DESCONECTA.equalsIgnoreCase(mensagem) && mensagem!=null){
                mensagem = bfr.readLine();
                enviaTodos(bufWriter, mensagem);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /** Distribui uma mensagem entre todos os clientes que estão escutando
     * em determinada porta.
     * 
     * @param bufWrOut BufferedWriter
     * @param msg String
     */
    private void enviaTodos(BufferedWriter bufWrOut, String msg) {
        BufferedWriter bufferWrOut;
       
        for(BufferedWriter bufW : clientes){
            bufferWrOut = (BufferedWriter) bufW;
            if(!(bufWrOut == bufferWrOut)){
                if((msg.contains(Mensagem.JOGADA_INICIO)&& msg.contains(Mensagem.JOGADA_FINAL)) || 
                        msg.contains(Mensagem.DEFINE_COR_VERMELHA) || 
                        (msg.contains(Mensagem.EXCLUI_PECA_INICIO)) && msg.contains(Mensagem.EXCLUI_PECA_FINAL) ||
                        msg.contains(Mensagem.INFORMA_VITORIA) || msg.contains(Mensagem.VOLTA_JOGO) ||
                        msg.contains(Mensagem.SOLICITA_REINICIO) ||
                        (msg.contains(Mensagem.RESPOSTA_REINICIO_INICIO) && msg.contains(Mensagem.RESPOSTA_REINICIO_FINAL)) ||
                        msg.contains(Mensagem.DESISTE_JOGO) || msg.contains(Mensagem.DESCONECTA)){
                    try {
                        bufW.write(msg);
                        bufW.flush();
                        } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                else{
                    try {
                        bufW.write(nome + ": " + msg + "\r\n"); 
                        bufW.flush();
                    } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    }
                }   
            }
        }
    }

    /** Método principal da classe.
     * Inicia o servidor na porta 12346 e aguarda a conexão de até 
     * dois clientes.
     * 
     * @param args String[]
     */
    public static void main(String[] args) {
            // TODO Auto-generated method stub
        int qtdClientes = 0;
        try {
            server = new ServerSocket(12346);
            clientes = new ArrayList<BufferedWriter>();
            System.out.println("Servidor ativo!");

            while(qtdClientes<2){
                System.out.println("Aguardando conexão...");
                Socket con = server.accept();
                qtdClientes += 1;
                System.out.println("Cliente conectado...");
                Thread t = new Servidor(con);
                t.start();
            }
        } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
    }
}

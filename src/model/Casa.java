/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;


/**
 *
 * @author Joamila
 * Created: 23/02/2016
 * Last Modified: 26/02/2016
 */
public class Casa {
    private String nome = "";
    private String cor = "";
    private ArrayList<String> vizinhos = new ArrayList();
    private ArrayList<String> vizinhosImediatos = new ArrayList();
    
    public String getNome() { return this.nome; }
    public String getCor() { return this.cor; }
    public void setCor(String cor) { this.cor = cor; }
    public ArrayList<String> getVizinhos() { return this.vizinhos; }
    public ArrayList<String> getVizinhosImediatos() { return this.vizinhosImediatos; }

    public Casa(String nome) {
        this.nome = nome;
        this.iniciarVizinhos(nome);
    }
    
    /** Inicia os vizinhos e vizinhos imediatos de uma casa.
     * 
     * @param casa String
     */
    public void iniciarVizinhos(String casa){
        switch(casa){
            case "a1Label":
                this.vizinhos.add("a2Label"); this.vizinhos.add("a3Label");
                this.vizinhos.add("d1Label"); this.vizinhos.add("g1Label");
                this.vizinhosImediatos.add("a2Label"); this.vizinhosImediatos.add("d1Label");
                break;
            case "a2Label":
                this.vizinhos.add("a1Label"); this.vizinhos.add("a3Label");
                this.vizinhos.add("b2Label"); this.vizinhos.add("c2Label");
                this.vizinhosImediatos.add("a1Label"); this.vizinhosImediatos.add("a3Label");
                this.vizinhosImediatos.add("b2Label"); 
                break;
            case "a3Label":
                this.vizinhos.add("a2Label"); this.vizinhos.add("a1Label");
                this.vizinhos.add("d6Label"); this.vizinhos.add("g3Label");
                this.vizinhosImediatos.add("a2Label"); this.vizinhosImediatos.add("d6Label");
                break;
            case "b1Label":
                this.vizinhos.add("b2Label"); this.vizinhos.add("b3Label");
                this.vizinhos.add("d2Label"); this.vizinhos.add("f1Label");
                this.vizinhosImediatos.add("b2Label"); this.vizinhosImediatos.add("d2Label");
                break;
            case "b2Label":
                this.vizinhos.add("b1Label"); this.vizinhos.add("b3Label");
                this.vizinhos.add("a2Label"); this.vizinhos.add("c2Label");
                this.vizinhosImediatos.add("b1Label"); this.vizinhosImediatos.add("b3Label");
                this.vizinhosImediatos.add("a2Label"); this.vizinhosImediatos.add("c2Label");
                break;
            case "b3Label":
                this.vizinhos.add("b2Label"); this.vizinhos.add("b1Label");
                this.vizinhos.add("d5Label"); this.vizinhos.add("f3Label");
                this.vizinhosImediatos.add("b2Label"); this.vizinhosImediatos.add("d5Label");
                break;
            case "c1Label":
                this.vizinhos.add("c2Label"); this.vizinhos.add("c3Label");
                this.vizinhos.add("d3Label"); this.vizinhos.add("e1Label");
                this.vizinhosImediatos.add("c2Label"); this.vizinhosImediatos.add("d3Label");
                break;
            case "c2Label":
                this.vizinhos.add("c1Label"); this.vizinhos.add("c3Label");
                this.vizinhos.add("b2Label"); this.vizinhos.add("a2Label");
                this.vizinhosImediatos.add("c1Label"); this.vizinhosImediatos.add("c3Label");
                this.vizinhosImediatos.add("b2Label"); 
                break;
            case "c3Label":
                this.vizinhos.add("c2Label"); this.vizinhos.add("c1Label");
                this.vizinhos.add("d4Label"); this.vizinhos.add("e3Label");
                this.vizinhosImediatos.add("c2Label"); this.vizinhosImediatos.add("d4Label");
                break;
            case "d1Label":
                this.vizinhos.add("d2Label"); this.vizinhos.add("d3Label");
                this.vizinhos.add("a1Label"); this.vizinhos.add("g1Label");
                this.vizinhosImediatos.add("a1Label"); this.vizinhosImediatos.add("g1Label");
                this.vizinhosImediatos.add("d2Label"); 
                break;
            case "d2Label":
                this.vizinhos.add("d1Label"); this.vizinhos.add("d3Label");
                this.vizinhos.add("b1Label"); this.vizinhos.add("f1Label");
                this.vizinhosImediatos.add("d1Label"); this.vizinhosImediatos.add("d3Label");
                this.vizinhosImediatos.add("b1Label"); this.vizinhosImediatos.add("f1Label");
                break;
            case "d3Label":
                this.vizinhos.add("d2Label"); this.vizinhos.add("d1Label");
                this.vizinhos.add("c1Label"); this.vizinhos.add("e1Label");
                this.vizinhosImediatos.add("d2Label"); this.vizinhosImediatos.add("c1Label");
                this.vizinhosImediatos.add("e1Label"); 
                break;
            case "d4Label":
                this.vizinhos.add("d5Label"); this.vizinhos.add("d6Label");
                this.vizinhos.add("c3Label"); this.vizinhos.add("e3Label");
                this.vizinhosImediatos.add("c3Label"); this.vizinhosImediatos.add("d5Label");
                this.vizinhosImediatos.add("e3Label"); 
                break;
            case "d5Label":
                this.vizinhos.add("d4Label"); this.vizinhos.add("d6Label");
                this.vizinhos.add("b3Label"); this.vizinhos.add("f3Label");
                this.vizinhosImediatos.add("d4Label"); this.vizinhosImediatos.add("d6Label");
                this.vizinhosImediatos.add("b3Label"); this.vizinhosImediatos.add("f3Label");
                break;
            case "d6Label":
                this.vizinhos.add("d5Label"); this.vizinhos.add("d4Label");
                this.vizinhos.add("a3Label"); this.vizinhos.add("g3Label");
                this.vizinhosImediatos.add("d5Label"); this.vizinhosImediatos.add("g3Label");
                this.vizinhosImediatos.add("a3Label"); 
                break;
            case "e1Label":
                this.vizinhos.add("e2Label"); this.vizinhos.add("e3Label");
                this.vizinhos.add("d3Label"); this.vizinhos.add("c1Label");
                this.vizinhosImediatos.add("e2Label"); this.vizinhosImediatos.add("d3Label");
                break;
            case "e2Label":
                this.vizinhos.add("e1Label"); this.vizinhos.add("e3Label");
                this.vizinhos.add("f2Label"); this.vizinhos.add("g2Label");
                this.vizinhosImediatos.add("e1Label"); this.vizinhosImediatos.add("e3Label");
                this.vizinhosImediatos.add("f2Label"); 
                break;
            case "e3Label":
                this.vizinhos.add("e2Label"); this.vizinhos.add("e1Label");
                this.vizinhos.add("d4Label"); this.vizinhos.add("c3Label");
                this.vizinhosImediatos.add("e2Label"); this.vizinhosImediatos.add("d4Label");
                break;
            case "f1Label":
                this.vizinhos.add("f2Label"); this.vizinhos.add("f3Label");
                this.vizinhos.add("d2Label"); this.vizinhos.add("b1Label");
                this.vizinhosImediatos.add("d2Label"); this.vizinhosImediatos.add("f2Label");
                break;
            case "f2Label":
                this.vizinhos.add("f1Label"); this.vizinhos.add("f3Label");
                this.vizinhos.add("e2Label"); this.vizinhos.add("g2Label");
                this.vizinhosImediatos.add("f1Label"); this.vizinhosImediatos.add("f3Label");
                this.vizinhosImediatos.add("e2Label"); this.vizinhosImediatos.add("g2Label");
                break;
            case "f3Label":
                this.vizinhos.add("f2Label"); this.vizinhos.add("f1Label");
                this.vizinhos.add("d5Label"); this.vizinhos.add("b3Label");
                this.vizinhosImediatos.add("f2Label"); this.vizinhosImediatos.add("d5Label");
                break;
            case "g1Label":
                this.vizinhos.add("g2Label"); this.vizinhos.add("g3Label");
                this.vizinhos.add("d1Label"); this.vizinhos.add("a1Label");
                this.vizinhosImediatos.add("d1Label"); this.vizinhosImediatos.add("g2Label");
                break;
            case "g2Label":
                this.vizinhos.add("g1Label"); this.vizinhos.add("g3Label");
                this.vizinhos.add("f2Label"); this.vizinhos.add("e2Label");
                this.vizinhosImediatos.add("g1Label"); this.vizinhosImediatos.add("g3Label");
                this.vizinhosImediatos.add("f2Label");
                break;
            case "g3Label":
                this.vizinhos.add("g2Label"); this.vizinhos.add("g1Label");
                this.vizinhos.add("d6Label"); this.vizinhos.add("a3Label");
                this.vizinhosImediatos.add("g2Label"); this.vizinhosImediatos.add("d6Label");
                break;
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Joamila
 * Created: 25/02/2016
 * Last Modified: 29/02/2016
 */
public interface Mensagem {
    
    //Mensagens para comunicação de ações
    static public String DESCONECTA = "**CoNvErSaEnCeRrAr**:encerrarJANELA:**EnCeRrArCoNvErSa**"; 
    static public String SAIR_JANELA = "**SaIrJaNeLa**:sair:**JaNeLaSaIr";
    static public String JOGADA_INICIO = "**TaBuLeIrOJoGaDa**:"; 
    static public String JOGADA_FINAL = ":**JoGaDaTaBuLeIrO**"; 
    static public String DEFINE_COR_VERMELHA = "**TaBuLeIrOCoR**:vermelha:**CoRTaBuLeIrO**"; 
    static public String INFORMA_TRILHA = "**TaBuLeIrOTrIlHa**:trilha:**TrIlHaTaBuLeIrO**";
    static public String EXCLUI_PECA_INICIO = "**TaBuLeIrOExClUiR**:"; 
    static public String EXCLUI_PECA_FINAL = ":**ExClUiRTaBuLeIrO**"; 
    static public String INFORMA_VITORIA = "**TaBuLeIrOViToRiA**:vitoria:**ViToRiATaBuLeIrO**"; 
    static public String VOLTA_JOGO = "**TaBuLeIrOVoLtAr**:voltarJOGO:**VoLtArTaBuLeIrO**"; 
    static public String SOLICITA_REINICIO = "**TaBuLeIrOReInIcIaR**:reiniciarJOGO:**ReInIcIaRTaBuLeIrO**"; 
    static public String RESPOSTA_REINICIO_INICIO = "**TaBuLeIrOReSpOsTa**:"; 
    static public String RESPOSTA_REINICIO_FINAL = ":**ReSpOsTaTaBuLeIrO**"; 
    static public String DESISTE_JOGO = "**TaBuLeIrODeSiStIr**:desistirJOGO:DeSiStIrTaBuLeIrO**"; 
    static public String TRES_PECAS = "**TaBuLeIrOPeCaS**:restam3PECAS:**TaBuLeIrOPeCaS**";
    static public String INFORMA_EMPATE = "**TaBuLeIrOEmPaTaDo**:jogoEMPATADO:**EmPaTaDoTaBuLeIrO**";
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.object;

/**
 *
 * @author Ervin
 */
public class Message {
    private String msg;
    private String auteur;
    
    public Message(String msg,String auteur) {
        this.msg = msg;
        this.auteur= auteur;
    }
    
    public String getAuteur(){
        return auteur;
    }
    
    public String getMsg(){
        return msg;
    }
}

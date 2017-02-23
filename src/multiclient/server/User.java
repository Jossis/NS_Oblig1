/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiclient.server;

/**
 *
 * @author Joakim
 */
public class User {
    private String pw;
    private String username;
    
    public void User (String u, String p){
        pw=p;
        username=u;
    }
    
    public String getPassword(){
        return pw;
    }
    public String getUsername(){
        return username;
    }
    public void setPw(String p){
        pw=p;
    }
    public void setUsername(String u){
        username=u;
    }
}

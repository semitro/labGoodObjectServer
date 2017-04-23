package vt.smt.Commands;

import vt.smt.Commands.ServerAnswer;

/**
 * Уведомления от сервера
 */
public class Message implements ServerAnswer {
    private String msg;
    public Message(String message){
        this.msg = new String(message);
    }
    public String getMessage(){
        return this.msg;
    }
}

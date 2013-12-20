package ru.develop.anketamanager.utils;
import java.io.Serializable;



public class util_login_pass implements Serializable {
    
    protected String login;
    protected String pass;
    protected String dir;
    protected String file;

    public String getLogin() {
        return login;
    }

    public void setLogin(String value) {
        this.login = value;
    }
    public String getPass() {
        return pass;
    }

    public void setPass(String value) {
        this.pass = value;
    }

    
    public String getDir() {
        return this.dir;
    }

    public void setDir(String value) {
        this.dir = value;
    }

    public String getFile() {
        return this.file;
    }

    public void setFile(String value) {
        this.file = value;
    }

}
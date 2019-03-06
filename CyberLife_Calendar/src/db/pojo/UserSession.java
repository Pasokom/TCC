package db.pojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;

/**
 * Classe responsável por guardar a sessão anterior do usuário para futuros logins
 */
public class UserSession implements Serializable {

    private String user_email;
    private int user_id;

    public UserSession() {

    }

    public UserSession(String email) {

        this.user_email = email;
    }

    public UserSession(String email, int id) {
        
        this.user_email = email;
        this.user_id = id;
    }

    public void serialize() {

        try{

            FileOutputStream fStream = new FileOutputStream(getPath());
            ObjectOutputStream oStream = new ObjectOutputStream(fStream);
            
            oStream.writeObject(this);

            oStream.close();
            fStream.close();
        }
        catch (IOException e){

            e.printStackTrace();
        }
    }

    public static UserSession fromSerialization(){

        UserSession session = null;

        if(fileExists()){

            try{

                FileInputStream fStream = new FileInputStream(getPath());
                ObjectInputStream oStream = new ObjectInputStream(fStream);
    
                session = (UserSession)oStream.readObject();
    
                oStream.close();
                fStream.close();
            }
            catch (IOException | ClassNotFoundException e){
    
                e.printStackTrace();
            }
        }
        else {

            session = new UserSession();
        }

        return session;
    }

    public static void close(){

        UserSession session = UserSession.fromSerialization();
		session.setUser_id(0);
		session.serialize();
    }

    public static boolean fileExists(){

        return new File(getPath()).exists();
    }

    private static String getPath(){

        String home = FileSystems.getDefault().getPath(System.getProperty("user.home")).toString();
        String path = "/CyberLifeConfig/serialization/session.ser";

        return home + path;
    }

    /**
     * @return the user_email
     */
    public String getUser_email() {
        return user_email;
    }

    /**
     * @return the user_id
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * @param user_email the user_email to set
     */
    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
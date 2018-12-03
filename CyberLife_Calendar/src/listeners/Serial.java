package listeners;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * <h2>Do a serial file (.ser) on the user temporary files directory</h2>
 * 
 * <h3>Features until now</h3>
 * <p>
 * <h4>Save the user id from the database, with this, the user will not need to
 * inform his password and login evertime that he want to use the program</h4>
 * </p>
 * 
 */
public class Serial {

    private final String PATH = System.getProperty("java.io.tmpdir") + "/";

    /**
     * <h1>Create a new serialization file</h1>
     * 
     * @param Object
     *               <p>
     *               The Object that will be written on the file
     *               </p>
     * @param String
     *               <p>
     *               The file name
     *               </p>
     * @return
     *         <p>
     *         The return going to be false if the file name passed by parameter
     *         already exists in the folder or if some exception happens
     *         </p>
     */
    public boolean doSerialization(Object obj, String fileName) {
        if (fileExists(fileName))
            return false;
        try {
            FileOutputStream file = new FileOutputStream(PATH + fileName + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(obj);
            out.close();
            file.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * <h2>Return the object in the file with the name that was passed by parameter
     * </h2>
     * 
     * @param String fileName
     * @return
     *         <p>
     *         If the return ara null, there was no object finded in the file,
     *         otherwise, the return will be a object, do the convertion for the
     *         object that you have stored in the file
     *         </p>
     */
    public Object undoSerialization(String fileName) {
        try {
            FileInputStream fis = new FileInputStream(PATH + fileName + ".ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object out = (Object) ois.readObject();
            ois.close();
            fis.close();
            return out;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <h2>If the return are true, the file already exists</h2>
     */
    public  boolean fileExists(String fileName) {
        return new File(PATH + fileName + ".ser").exists();
    }

}
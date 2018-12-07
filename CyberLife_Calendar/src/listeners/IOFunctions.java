package listeners;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;

import javax.imageio.ImageIO;

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
public class IOFunctions {
    /**
     * pasta de configuração do software TODO deixar mais facil de manipular
     */
    private final String CONFIG_FOLDER = "CyberLifeConfig";
    /** pode adicionar mais ai */
    private final String[] extensions = new String[] { "png", "jpg", "gif" };

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
            FileOutputStream file = new FileOutputStream(CONFIG_FOLDER + fileName + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(obj);
            out.close();
            file.close();
            return true;
        } catch (IOException e) {
            // e.printStackTrace();
            System.out.println("[ERROR] function: serialization() - Classe IOFunctions()");
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
     *         If the return are null, there was no object finded in the file,
     *         otherwise, the return will be a object, do the convertion for the
     *         object that you have stored in the file
     *         </p>
     */
    public Object undoSerialization(String fileName) {
        try {
            FileInputStream fis = new FileInputStream(getConfigFolder() + fileName + ".ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object out = (Object) ois.readObject();
            ois.close();
            fis.close();
            return out;
        } catch (ClassNotFoundException | IOException e) {
            // e.printStackTrace();
            System.out.println("[ERROR] função : undoSerialization() - classe IOFunctions ");
        }
        return null;
    }

    /**
     * <h2>If the return are true, the file already exists</h2>
     */
    public boolean fileExists(String fileName) {
        return new File(getSerializationFolder() + fileName + ".ser").exists();
    }

    /**
     * <h2>arquivos serializados na pasta temporaria</h2>
     */
    public void deleteSerialization(String filename) {
        new File(getSerializationFolder() + filename + ".ser").delete();
    }

    /**
     * <h2>Com essa função voce pode passar um diretorio especifico , não só o
     * temporarios</h2>
     */
    public boolean fileExists(String fileName, String path) {
        System.out.println(path + fileName);
        return new File(path + fileName).exists();
    }

    /**
     * Passar o nome do arquivo por parametro Retorna a extensão do arquivo
     */
    public String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        return fileName.substring(index + 1);
    }

    /**
     * Cria um diretorio no caminho passado
     */
    public boolean mkDir(String path, String dirName) {
        return new File(path + "/" + dirName).mkdir();
    }

    /**
     * Define uma image para o usuario na pasta images
     */
    public void updateImage(String fileName, BufferedImage buffer) {
        String folder = getImagesFolder();
        File f = new File(folder);
        if (!f.exists())
            f.mkdir();
        final String imageFolder = f.getPath() + "/" + fileName;
        File image = new File(imageFolder);
        deleteFileIfExists(image);
        try {
            ImageIO.write(buffer, getFileExtension(fileName), image);
            return;
        } catch (IOException e) {
            System.out.println("IOException on function updateImage() in the IOFunctions class");
        }
    }

    public File getFile(String path, String fileName) {
        for (int i = 0; i < extensions.length; i++) {
            String strFile = path + fileName + '.' + extensions[i];
            File f = new File(strFile);
            if (f.exists())
                return f;
        }
        return null;
    }

    /*
     * Vai no diretorio do arquivo e apaga, caso exista
     */
    public void deleteFileIfExists(File file) {
        if (file.exists()) {
            file.delete();
            return;
        }
        final String filePath = file.getPath();
        final String fileName = file.getName();
        for (int i = 0; i < extensions.length; i++) {
            String newFile = filePath + "/" + fileName + extensions[i];
            File f = new File(newFile);
            if (f.exists()) {
                f.delete();
                return;
            }
        }
    }

    /**
     * Retorna a pasta de configuração do programa (até agora só ta sendo o usada
     * para funções de salvar imagem e serialização)
     */
    public String getConfigFolder() {
        final String path = FileSystems.getDefault().getPath(System.getProperty("user.home")).toString();
        String folder = path + "/" + this.CONFIG_FOLDER;
        File f = new File(folder);
        if (!f.exists())
            f.mkdir();
        return f.getPath() + "/";
    }

    public String getImagesFolder() {
        return getConfigFolder() + "images/";
    }

    public String getSerializationFolder() {
        return getConfigFolder() + "serialization/";
    }

}
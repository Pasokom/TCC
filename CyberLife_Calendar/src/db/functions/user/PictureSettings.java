package db.functions.user;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import listeners.IOFunctions;

public class PictureSettings {

    public PictureSettings() {
    }

    public void chooseImage(Stage owner, int userID) {
        FileChooser fc = new FileChooser();
        File f = fc.showOpenDialog(owner);
        IOFunctions io = new IOFunctions();
        byte[] bytes = new byte[(int) f.length()];

        if (f != null && bytes != null)
            try {
                String fileName = String.valueOf(1) + '.' + io.getFileExtension(f.getName());
                BufferedImage bfi = ImageIO.read(f);
                io.updateImage(fileName, bfi);
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        System.out.println("not a image choosed");
    }

    public Image getImageInFolder(int userID) {
        IOFunctions serial = new IOFunctions();
        // Image img = (Image)
        // serial.undoSerializationByChoosedDir(String.valueOf(userID), PATH);
        // return img != null ? img : null;
        return null;
    }
}
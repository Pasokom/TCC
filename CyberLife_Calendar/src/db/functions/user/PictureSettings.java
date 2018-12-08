package db.functions.user;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
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

    public Image getUserImage(int userID) {
        IOFunctions io = new IOFunctions();
        try {
            final String path = io.getImagesFolder();
            final String fileName = String.valueOf(userID);
            final File image = io.getFile(path, fileName);
            if (image == null)
                return null;
            return SwingFXUtils.toFXImage(ImageIO.read(image), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
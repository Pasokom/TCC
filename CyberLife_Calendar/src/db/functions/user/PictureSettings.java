package db.functions.user;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import db.Database;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PictureSettings {
    private Connection connection;

    public PictureSettings() {
        try {
            this.connection = Database.get_connection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setImage(Stage owner, int userID) {
        FileChooser fc = new FileChooser();
        File f = fc.showOpenDialog(owner);
        FileInputStream fis = null;
        if (f != null) {
            byte[] img = new byte[(int) f.length()];
            try {
                fis = new FileInputStream(f);
                fis.read(img);
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (img.length < 52428800) {// size of the file
                try {
                    final String sql = "UPDATE USUARIO  SET UFOTO = ? WHERE UCODIGO = ?";
                    PreparedStatement stmt = this.connection.prepareStatement(sql);

                    stmt.setBytes(1, img);
                    stmt.setInt(2, userID);
                    stmt.execute();
                    this.connection.close();
                    return;
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * @return The image of the user
     * @param int userID
     */
    public Image getImage(int userID) {
        final String sql = "SELECT UFOTO FROM USUARIO WHERE UCODIGO = " + userID + ";";
        ResultSet result = null;
        Image img = null;
        try {
            result = this.connection.createStatement().executeQuery(sql);

            if (result.isBeforeFirst())
                result.next();
            if (result.getBlob(1) == null) {
                return null;
            }
            Blob blob = result.getBlob(1);

            byte[] bytes = blob.getBytes(1, (int) blob.length());

            BufferedImage bfi = ImageIO.read(new ByteArrayInputStream(bytes));
            img = SwingFXUtils.toFXImage(bfi, null);
            this.connection.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return img;
    }

}
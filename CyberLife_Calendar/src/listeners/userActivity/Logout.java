
package listeners.userActivity;

import display.scenes.Login;
import listeners.IOFunctions;
import main.Main;
import statics.SESSION;

public class Logout {

    public Logout() { }

    public void leave() {
        SESSION.END_SESSION();

        IOFunctions serial = new IOFunctions();
        if (serial.fileExists("stay_connected"))
            serial.deleteSerialization("stay_connected");
        Main.main_stage.setScene(new Login());
    }

    public void close() {
        Main.main_stage.close();
    }

}
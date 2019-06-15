package statics;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import db.functions.appointment.LoadAppointment;
import javafx.application.Platform;
import main.Main;

public class NotifyUser {

	public static SystemTray tray;
	private static TrayIcon trayIcon;
	
	public static void init() {
		
		Toolkit.getDefaultToolkit();
		tray = SystemTray.getSystemTray();
		
		/* Icone */
		Image image = Toolkit.getDefaultToolkit().createImage(NotifyUser.class.getResource("").getPath() + "../../resources/images/Logo.png");
		
		/* Menu de opcoes */
		PopupMenu popupMenu = new PopupMenu();

		MenuItem mSair = new MenuItem("Sair");
		mSair.addActionListener(e -> {
		
			System.exit(0);
		});
		
		popupMenu.add(mSair);
		
		trayIcon = new TrayIcon(image, "logo", popupMenu);
		trayIcon.setImageAutoSize(true);
		trayIcon.setToolTip("CyberLife Calendar");

		trayIcon.addActionListener(e -> {

			Platform.runLater(() -> {

				Main.main_stage.show();
			});   
		});
		
		try {
			tray.add(trayIcon);
		} catch (AWTException e) {

			e.printStackTrace();
		}
		
		verify();
	}
	
	private static void verify() {
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				int minute = -1;
				
				while(true) {
					
					if(minute != Calendar.getInstance().get(Calendar.MINUTE)) {

						LoadAppointment load = new LoadAppointment();
						ArrayList<String> lista = load.loadNotifications();

						Format formatter = new SimpleDateFormat("HH:mm");
						
						for (String appointment : lista) {

							//String hora = formatter.format(Calendar.getInstance());
							sendNotification("Compromisso", appointment, MessageType.INFO);
						}
						
						minute = Calendar.getInstance().get(Calendar.MINUTE);
						try {
							Thread.sleep(1000 * (60 - Calendar.getInstance().get(Calendar.SECOND)));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} 
				}
			}
		});
		
		thread.start();
	}
	
	public static void sendNotification(String caption, String text, MessageType messageType) {
		
		trayIcon.displayMessage(caption, text, messageType);
	}
}

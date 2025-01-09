/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package launcher;

import controllers.HomeController;
import java.util.prefs.Preferences;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import mdlaf.MaterialLookAndFeel;
import controllers.LoginController;
import models.User;
import views.HomeView;
import views.LoginView;

/**
 *
 * @author Jaqui Sanchez
 */
public class MonkeyGarage {
    
    
    public MonkeyGarage(){
        initMaterial();
        launchScreen();
        //createDefaultUser();
    }
    
    
    public static void main(String[] args) {
        new MonkeyGarage();
    }
    
    
       public void initMaterial() {
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public void launchScreen() {
        if (getSessionID()) {

            new HomeController(new HomeView(), new User().getData(getEmail()));

        } else {

            new LoginController(new LoginView());
        }
    }
    
    
       public boolean getSessionID() {
        Preferences prefs = Preferences.userNodeForPackage(controllers.LoginController.class);
        final String PREF_NAME = "email";
        String defaultValue = "default";

        String id = prefs.get(PREF_NAME, defaultValue);

        boolean val = false;
        if (!id.equals("default")) {
            val = true;
        }

        return val;

    }

    private void createDefaultUser(){
        User user = new User();
        user.registerUser(new User(
                "Admin",
                "Admin",
                "admin@mail.com",
                "admin",
                "12345678"
        ));
    }

    public String getEmail() {
        Preferences prefs = Preferences.userNodeForPackage(controllers.LoginController.class);
        final String PREF_NAME = "email";
        String defaultValue = "default";

        return prefs.get(PREF_NAME, defaultValue);
    }
    
}

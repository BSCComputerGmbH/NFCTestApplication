package de.bscgmbh.nfc;


import com.gluonhq.attach.util.Constants;
import com.gluonhq.attach.util.Util;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.gluonhq.charm.glisten.license.License;


public class GluonApplication extends MobileApplication {

    @Override
    public void init() {
        addViewFactory(HOME_VIEW, BasicView::new);
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);

        ((Stage) scene.getWindow()).getIcons().add(new Image(GluonApplication.class.getResourceAsStream("/icon.png")));
    }

    public static void main(String args[]) {

        System.setProperty(Constants.ATTACH_DEBUG, "true");
      	boolean wtf = Util.DEBUG;
      	System.out.println("wtf " + wtf);
        
        launch(args);
    }
}

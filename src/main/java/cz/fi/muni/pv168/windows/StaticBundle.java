package cz.fi.muni.pv168.windows;

import java.util.ResourceBundle;

/**
 * Created by Milan on 12.05.2016.
 */
public class StaticBundle {
    private static ResourceBundle rs = ResourceBundle.getBundle("languages");

    public static ResourceBundle getInstance() {
        return rs;
    }
}

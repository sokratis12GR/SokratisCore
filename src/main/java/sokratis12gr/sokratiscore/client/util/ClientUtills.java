package sokratis12gr.sokratiscore.client.util;

import sokratis12gr.sokratiscore.util.LogHelper;

import java.net.URI;

/**
 * sokratis12gr.sokratiscore.client.util
 * SokratisCore created by sokratis12GR on 7/26/2016 2:38 PM.
 */
public class ClientUtills {

    public static void openLink(String url) {
        try {
            URI uri = new URI(url);
            Class oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
            oclass.getMethod("browse", new Class[]{URI.class}).invoke(object, new Object[]{uri});
        } catch (Throwable throwable) {
            LogHelper.error("Couldn\'t open link");
            throwable.printStackTrace();
        }
    }
}
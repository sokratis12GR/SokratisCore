package sokratis12gr.sokratiscore.handlers;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

/**
 * sokratis12gr.sokratiscore.handlers
 * SokratisCore created by sokratis12GR on 7/26/2016 1:13 PM.
 */
public class FileHandler {
    public static File configFolder;
    public static File mcDirectory;

    public static void init(FMLPreInitializationEvent event) {
        configFolder = event.getModConfigurationDirectory();
        mcDirectory = configFolder.getParentFile();
    }
}
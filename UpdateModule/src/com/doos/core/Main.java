package com.doos.core;

import com.doos.SettingsManager.ApplicationConstants;
import com.doos.SettingsManager.core.SettingsManager;
import com.doos.SettingsManager.registry.RegistryCanNotReadInfoException;
import com.doos.SettingsManager.registry.RegistryException;
import com.doos.SettingsManager.registry.RegistryManager;
import com.doos.gui.UpdateDialog;
import com.doos.nongui.NonGuiUpdater;
import com.doos.utils.Internal;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import static com.doos.SettingsManager.ApplicationConstants.UPDATE_PATH_FILE;
import static com.doos.SettingsManager.core.SettingsManager.showErrorMessage;
import static com.doos.nongui.NonGuiUpdater.tray;
import static com.doos.nongui.NonGuiUpdater.trayIcon;

/**
 * Created by Eugene Zrazhevsky on 02.11.2016.
 */
public class Main {
    public static Properties properties = new Properties();
    public static Mode mode = Mode.NORMAL;

    public static void main(String[] args) {
        enableLookAndFeel();

        try {
            loadProperties();
        } catch (RegistryException e) {
            e.printStackTrace();
            try {
                fixProperties();
            } catch (RegistryException | FileNotFoundException e1) {
                e1.printStackTrace();
                showErrorMessage("Can not fix com.doos.com.doos.SettingsManager.core.SettingsManager.registry",
                                 "Registry application data is corrupt. " +
                        "Please re-install the " + "application.");
                System.exit(-1);
            }

        }
        System.out.println("Updater args: " + Arrays.toString(args));
        if (args.length > 0) {

            switch (args[0]) {
                case "-s":
                    mode = Mode.SILENT;
                    final String autoUpdateString = properties.getProperty(RegistryManager.KEY_AUTO_UPDATE);
                    System.out.println(RegistryManager.KEY_AUTO_UPDATE + " : " + autoUpdateString);
                    if (autoUpdateString != null) {
                        if (Boolean.parseBoolean(autoUpdateString)) {
                            new NonGuiUpdater();
                        }
                    } else {
                        properties.setProperty(RegistryManager.KEY_AUTO_UPDATE, Boolean.toString(true));
                        new NonGuiUpdater();
                    }
                    break;
                case "-afterUpdate":
                    mode = Mode.AFTER_UPDATE;
                    new File(UPDATE_PATH_FILE + "Updater_.jar").delete();
                    //createUpdateDialog();
                    break;
                default:
                    mode = Mode.ERROR;
                    System.out.println("No such argument: " + args[0]);
                    break;
            }
        } else {
            mode = Mode.NORMAL;
            /*-----*/
            try {
                System.out.println("Installation folder: " + RegistryManager.getInstallLocationValue());
            } catch (RegistryCanNotReadInfoException ignore) {/*NOP*/}
            /*-----*/


            initUpdateJar();
        }
    }

    public static void fixProperties() throws RegistryException, FileNotFoundException {
        properties = SettingsManager.fixRegistry();

    }

    public static void initUpdateJar() {
        final File JAR_FILE = new File(UpdateDialog.class.getProtectionDomain()
                                               .getCodeSource().getLocation().getPath());
        final String property = properties.getProperty(RegistryManager.KEY_INSTALL_LOCATION);
        File JAR_FILE_DEFAULT_LOCATION = null;
        if (property != null) {
            JAR_FILE_DEFAULT_LOCATION = new File(property
                                                         + File.separator + "Updater.jar");
        } else {
            File file = JAR_FILE.getParentFile();
            System.out.println("Parent file is: " + file.getAbsolutePath());
            if (file.getName().equals(ApplicationConstants.APP_NAME)) {
                JAR_FILE_DEFAULT_LOCATION = new File(file.getAbsolutePath() + File.separator + "Updater.jar");
            } else {
                JAR_FILE_DEFAULT_LOCATION = new File(
                        "C:\\Program Files (x86)\\WeblocOpener\\Updater.jar"); //TODO find better solution
            }
            ;
        }


        System.out
                .println("Jar: " + JAR_FILE.getAbsolutePath() + " def: " + JAR_FILE_DEFAULT_LOCATION.getAbsolutePath());

        if (JAR_FILE.getAbsolutePath().replace("%20", " ").equals(
                JAR_FILE_DEFAULT_LOCATION.getAbsolutePath().replace("%20", " "))) {
            try {
                System.out.println(
                        "Creating itself at: " + new File(UPDATE_PATH_FILE + "Updater_.jar").getAbsolutePath());
                FileUtils.copyFile(new File(JAR_FILE.getAbsolutePath().replace("%20", " ")),
                                   new File(UPDATE_PATH_FILE + "Updater_.jar"));
                Runtime.getRuntime().exec("java -jar " + UPDATE_PATH_FILE + "Updater_.jar");
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                loadProperties();
            } catch (RegistryException e) {
                String message
                        = "Can not read data from com.doos.com.doos.SettingsManager.core.SettingsManager.registry.";
                System.out.println(message);
                JOptionPane.showMessageDialog(null, message, message, JOptionPane.ERROR_MESSAGE);

            }
            Main.createUpdateDialog();
        }
    }

    public static void createUpdateDialog() {
        final UpdateDialog updateDialog = new UpdateDialog();

        updateDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    Main.loadProperties();
                } catch (RegistryException e1) {
                    e1.printStackTrace();
                }
                String str = properties.getProperty(RegistryManager.KEY_CURRENT_VERSION);
                if (str == null) {
                    //str = serverAppVersion.getVersion(); //why????
                    str = ApplicationConstants.APP_VERSION;
                } else if (str.isEmpty()) {
                    str = ApplicationConstants.APP_VERSION;
                }
                if (Internal.versionCompare(str, updateDialog.getAppVersion().getVersion()) == 0) {
                    tray.remove(trayIcon);
                    System.exit(0);
                }
                super.windowClosed(e);

            }

        });
        updateDialog.setVisible(true);
        updateDialog.checkForUpdates();
    }

    public static void updateProperties() {
        SettingsManager.updateInfo(properties);
    }

    public static void loadProperties() throws RegistryException {
        properties = SettingsManager.loadInfo();
    }

    private static void enableLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignore) {/*NOP*/}
    }

    public enum Mode {NORMAL, SILENT, AFTER_UPDATE, ERROR}
}

package com.doos.update_module.gui;

import com.doos.commons.core.ApplicationConstants;
import com.doos.commons.core.Translation;
import com.doos.commons.registry.RegistryCanNotReadInfoException;
import com.doos.commons.registry.RegistryException;
import com.doos.commons.registry.RegistryManager;
import com.doos.commons.registry.fixer.RegistryFixer;
import com.doos.commons.utils.FrameUtils;
import com.doos.commons.utils.Internal;
import com.doos.commons.utils.MessagePushable;
import com.doos.update_module.core.Main;
import com.doos.update_module.update.AppVersion;
import com.doos.update_module.update.Updater;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import static com.doos.commons.utils.UserUtils.showErrorMessageToUser;
import static com.doos.commons.utils.UserUtils.showSuccessMessageToUser;

@SuppressWarnings({"ALL", "ResultOfMethodCallIgnored"})
public class UpdateDialog extends JFrame implements MessagePushable {
    public static UpdateDialog updateDialog = null;
    public JProgressBar progressBar1;
    public JButton buttonOK;
    public JButton buttonCancel;
    Timer messageTimer;
    private Translation translation;
    private AppVersion serverAppVersion;
    private JPanel contentPane;
    private JLabel currentVersionLabel;
    private JLabel availableVersionLabel;
    private JLabel newVersionSizeLabel;
    private JLabel unitLabel;
    private JLabel currentVersionStringLabel;
    private JLabel avaliableVersionStringLabel;
    private JPanel errorPanel;
    private JTextPane errorTextPane;
    private Thread updateThread;

    private String successUpdatedMessage = "WeblocOpener successfully updated to version: ";
    private String successTitle = "Success";

    private String installationCancelledTitle = "Installation cancelled";
    private String installationCancelledMessage = "Installation cancelled by User during installation";

    private String noPermissionsMessage = "Installation can not be run, because it has no permissions.";

    private String installationCancelledByErrorMessage1 = "Installation cancelled by Error (unhandled error),";
    private String installationCancelledByErrorMessage2 = "code: ";
    private String installationCancelledByErrorMessage3 = "visit " + ApplicationConstants.GITHUB_WEB_URL + " for more info.";

    public UpdateDialog() {
        serverAppVersion = new AppVersion();

        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);

        setIconImage(Toolkit.getDefaultToolkit().getImage(UpdateDialog.class.getResource("/icon.png")));

        buttonOK.addActionListener(e -> {
            updateThread = new Thread(() -> onOK());
            updateThread.start();
        });

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        pack();
        setLocation(FrameUtils.getFrameOnCenterLocationPoint(this));
        setSize(new Dimension(400, 170));
        setResizable(false);
        loadProperties();
        translateDialog();
    }

    private void translateDialog() {
        translation = new Translation("translations/UpdateDialogBundle") {
            @Override
            public void initTranslations() {
                setTitle(messages.getString("windowTitle"));
                buttonOK.setText(messages.getString("buttonOk"));
                buttonCancel.setText(messages.getString("buttonCancel"));

                currentVersionStringLabel.setText(messages.getString("currentVersionStringLabel"));
                avaliableVersionStringLabel.setText(messages.getString("avaliableVersionStringLabel"));
                availableVersionLabel.setText(messages.getString("availableVersionLabel"));

                successTitle = messages.getString("successTitle");
                successUpdatedMessage = messages.getString("successUpdatedMessage");
                installationCancelledTitle = messages.getString("installationCancelledTitle");
                installationCancelledMessage = messages.getString("installationCancelledMessage");
                noPermissionsMessage = messages.getString("noPermissionsMessage");
                installationCancelledByErrorMessage1 = messages.getString("installationCancelledByErrorMessage1");
                installationCancelledByErrorMessage2 = messages.getString("installationCancelledByErrorMessage2");
                installationCancelledByErrorMessage3 = messages.getString("installationCancelledByErrorMessage3");
            }
        };
        translation.initTranslations();
    }

    public void checkForUpdates() {
        progressBar1.setIndeterminate(true);
        Updater updater = new Updater();
        serverAppVersion = updater.getAppVersion();
        progressBar1.setIndeterminate(false);
        availableVersionLabel.setText(serverAppVersion.getVersion());

        setNewVersionSizeInfo();

        String str;
        try {
            str = RegistryManager.getAppVersionValue();
        } catch (RegistryCanNotReadInfoException e) {
            RegistryManager.setDefaultSettings();
            str = ApplicationConstants.APP_VERSION;
        }
        compareVersions(str);
    }

    private void compareVersions(String str) {
        if (Internal.versionCompare(str, serverAppVersion.getVersion()) < 0) {
            //Need to update
            buttonOK.setEnabled(true);
            buttonOK.setText(translation.messages.getString("buttonOk"));
        } else if (Internal.versionCompare(str, serverAppVersion.getVersion()) > 0) {
            //App version is bigger then on server
            buttonOK.setText(translation.messages.getString("buttonOkDev"));
//            buttonOK.setEnabled(true);
            buttonOK.setEnabled(false); //TODO TURN BACK BEFORE RELEASE
        } else if (Internal.versionCompare(str, serverAppVersion.getVersion()) == 0) {
            //No reason to update;
            buttonOK.setText(translation.messages.getString("buttonOkUp2Date"));
        }
    }

    private void setNewVersionSizeInfo() {
        if (serverAppVersion.getSize() > 1024 * 1024) {
            double size = serverAppVersion.getSize() / 1024 / (double) 1024;
            size = size * 100;
            int i = (int) Math.round(size);
            size = (double) i / 100;
            newVersionSizeLabel.setText(Double.toString(size));
            unitLabel.setText("MB");
        } else {
            newVersionSizeLabel.setText(serverAppVersion.getSize() / 1024 + "");
            unitLabel.setText("KB");
        }
    }

    private void loadProperties() {
        try {
            currentVersionLabel.setText(RegistryManager.getAppVersionValue());
        } catch (RegistryException e) {
            currentVersionLabel.setText(ApplicationConstants.APP_VERSION);
        }
        availableVersionLabel.setText("Unknown");
    }


    private void processUpdateResult(int installationCode) {
        System.out.println("Installation code: " + installationCode);
        switch (installationCode) {
            case ApplicationConstants.UPDATE_CODE_SUCCESS:
                updateSuccessfullyInstalled();
                break;
            case ApplicationConstants.UPDATE_CODE_CANCEL:
                showErrorMessageToUser(this, installationCancelledTitle,
                        installationCancelledMessage);
                Updater.installerFile.delete();
                break;
            case ApplicationConstants.UPDATE_CODE_NO_FILE:
                showErrorMessageToUser(this, installationCancelledTitle, noPermissionsMessage);
                break;

            case ApplicationConstants.UPDATE_CODE_CORRUPT:
                showErrorMessageToUser(this, installationCancelledTitle, noPermissionsMessage);
                break;
            case ApplicationConstants.UPDATE_CODE_INTERRUPT:/*NOP*/
                break;
            default:
                String message = installationCancelledByErrorMessage1
                        + "\n" + installationCancelledByErrorMessage2 +
                        installationCode
                        + "\n" + installationCancelledByErrorMessage3;
                showErrorMessageToUser(this, installationCancelledTitle, message);
                break;
        }
    }

    private void updateSuccessfullyInstalled() {
        try {
            RegistryFixer.fixRegistry();
        } catch (Exception ignore) {
           /*NOP*/
        }


        showSuccessMessageToUser(this, successTitle,
                successUpdatedMessage + serverAppVersion.getVersion());

        if (Main.mode != Main.Mode.AFTER_UPDATE) {
            //dispose(); //TODO test it, if ok, delete
            runCleanTempUpdaterFile();
        }

    }

    public AppVersion getAppVersion() {
        return serverAppVersion;
    }

    private void onOK() {
        buttonOK.setEnabled(false);
        if (!Thread.currentThread().isInterrupted()) {
            //TODO make this beautifull: call downloader, return file, then call installation
            int result = Updater.startUpdate(serverAppVersion);
            if (Thread.currentThread().isInterrupted()) {
                result = -999;
            } else {
                processUpdateResult(result);
            }
            buttonOK.setEnabled(true);
            buttonCancel.setEnabled(true);
        } else {
            buttonOK.setEnabled(true);
            buttonCancel.setEnabled(true);
        }

    }

    private void onCancel() {
        if (updateThread != null) {
            if (!updateThread.isInterrupted()) {
                updateThread.interrupt();
                System.out.println("Installation was interrupted: " + updateThread.isInterrupted());
                if (!updateThread.isInterrupted()) {
                    System.out.println("dispose>>");
                    dispose();
                    System.exit(0);//TODO FIXME
                }
            }
            runCleanInstallerFile();
        } else {
            dispose();
        }
        File updateJar = new File(ApplicationConstants.UPDATE_PATH_FILE + "Updater_.jar");
        if (updateJar.exists()) {
            runCleanTempUpdaterFile();
        }
    }

    private void runCleanTempUpdaterFile() {
        try {
            String value = RegistryManager.getInstallLocationValue();
            final String command
                    = "java -jar \"" + value + "Updater.jar\" " + ApplicationConstants.UPDATE_DELETE_TEMP_FILE_ARGUMENT;
            System.out.println("running " + ApplicationConstants.UPDATE_DELETE_TEMP_FILE_ARGUMENT + " " +
                    "argument: " + command);
            Runtime.getRuntime().exec(command);
        } catch (RegistryCanNotReadInfoException | IOException e) {
            e.printStackTrace();
        }
    }

    private void runCleanInstallerFile() {
        System.out.println("Deleting file: " + ApplicationConstants.UPDATE_PATH_FILE + "WeblocOpenerSetupV"
                + serverAppVersion.getVersion() + "" + ".exe");
        new File(ApplicationConstants.UPDATE_PATH_FILE + "WeblocOpenerSetupV"
                + serverAppVersion.getVersion() + ".exe").delete();
    }

    public void showMessage(String message, int messageValue) {
        errorPanel.setBackground(MessagePushable.getMessageColor(messageValue));

        boolean wasVisible = errorPanel.isVisible();
        errorPanel.setVisible(true);
        errorTextPane.setText(message);

        if (!wasVisible) {
            updateSize(UpdateSizeMode.BEFORE_HIDE);
        }

        if (messageTimer != null) {
            messageTimer.stop();
        }

        messageTimer = new Timer(DEFAULT_TIMER_DELAY, e -> {
            errorTextPane.setText("");
            errorPanel.setVisible(false);
            updateSize(UpdateSizeMode.AFTER_HIDE);
        });
        messageTimer.setRepeats(false);
        messageTimer.start();
    }

    private void updateSize(UpdateSizeMode mode) {

        setResizable(true);
        revalidate();
        final int DEFAULT_APPLICATION_WIDTH = 400;
        if (mode == UpdateSizeMode.BEFORE_HIDE) {
            pack();
            setSize(new Dimension(DEFAULT_APPLICATION_WIDTH, getHeight()));
        } else if (mode == UpdateSizeMode.AFTER_HIDE) {
            setSize(new Dimension(DEFAULT_APPLICATION_WIDTH, 170));
        }
        setResizable(false);

    }

    enum UpdateSizeMode {BEFORE_HIDE, AFTER_HIDE}
}

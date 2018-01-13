package com.github.benchdoos.weblocopener.updater.gui;

import com.github.benchdoos.weblocopener.commons.core.ApplicationConstants;
import com.github.benchdoos.weblocopener.commons.core.Translation;
import com.github.benchdoos.weblocopener.commons.registry.RegistryCanNotReadInfoException;
import com.github.benchdoos.weblocopener.commons.registry.RegistryException;
import com.github.benchdoos.weblocopener.commons.registry.RegistryManager;
import com.github.benchdoos.weblocopener.commons.registry.fixer.RegistryFixer;
import com.github.benchdoos.weblocopener.commons.utils.*;
import com.github.benchdoos.weblocopener.updater.core.Main;
import com.github.benchdoos.weblocopener.updater.update.AppVersion;
import com.github.benchdoos.weblocopener.updater.update.Updater;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import static com.github.benchdoos.weblocopener.commons.utils.system.SystemUtils.IS_WINDOWS_XP;

@SuppressWarnings({"ALL", "ResultOfMethodCallIgnored"})
public class UpdateDialog extends JFrame implements MessagePushable {
    private static final Logger log = Logger.getLogger(Logging.getCurrentClassName());
    public static UpdateDialog updateDialog = null;
    public JProgressBar progressBar1;
    public JButton buttonOK;
    public JButton buttonCancel;
    Timer messageTimer;
    String retryButton = "Retry";
    private Translation translation;
    private AppVersion serverAppVersion;
    private JPanel contentPane;
    private JPanel errorPanel;
    private JLabel currentVersionLabel;
    private JLabel availableVersionLabel;
    private JLabel newVersionSizeLabel;
    private JLabel unitLabel;
    private JLabel currentVersionStringLabel;
    private JLabel availableVersionStringLabel;
    private JTextPane errorTextPane;
    private JButton updateInfoButton;
    private Thread updateThread;
    private String successUpdatedMessage = "WeblocOpener successfully updated to version: ";
    private String successTitle = "Success";
    private String installationCancelledTitle = "Installation cancelled";
    private String installationCancelledMessage = "Installation cancelled by User during installation";
    private String noPermissionsMessage = "Installation can not be run, because it has no permissions.";
    private String installationCancelledByErrorMessage1 = "Installation cancelled by Error (unhandled error),";
    private String installationCancelledByErrorMessage2 = "code: ";
    private String installationCancelledByErrorMessage3 = "visit " + ApplicationConstants.GITHUB_WEB_URL + " for more info.";
    private String lostConnectionTitle = "Unable to update.";
    private String lostConnectionMessage = "Can not download update \nLost connection, retry.";

    public UpdateDialog() {
        serverAppVersion = new AppVersion();

        createGUI();
        loadProperties();
        translateDialog();
    }

    public void checkForUpdates() {
        progressBar1.setIndeterminate(true);
        Updater updater = null;
        try {
            updater = new Updater();
            createDefaultActionListeners();

            serverAppVersion = updater.getAppVersion();
            progressBar1.setIndeterminate(false);
            availableVersionLabel.setText(serverAppVersion.getVersion());
            setNewVersionSizeInfo();

            updateInfoButton.setEnabled(true);


            String str;
            try {
                str = RegistryManager.getAppVersionValue();
            } catch (RegistryCanNotReadInfoException e) {
                RegistryManager.setDefaultSettings();
                str = ApplicationConstants.APP_VERSION;
            }
            compareVersions(str);
        } catch (IOException e) {
            removeAllListeners(buttonOK);

            Updater.canNotConnectManage(e);
            progressBar1.setIndeterminate(false);
            buttonOK.setEnabled(true);
            buttonOK.setText(retryButton);
            buttonOK.addActionListener(e1 -> {
                progressBar1.setIndeterminate(true);
                checkForUpdates();
            });
        }
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
            buttonOK.setEnabled(false); //TODO TURN TO FALSE BACK BEFORE RELEASE
        } else if (Internal.versionCompare(str, serverAppVersion.getVersion()) == 0) {
            //No reason to update;
            buttonOK.setText(translation.messages.getString("buttonOkUp2Date"));
        }
    }

    private void createDefaultActionListeners() {
        for (ActionListener actionListener :
                buttonOK.getActionListeners()) {
            buttonOK.removeActionListener(actionListener);
        }

        for (ActionListener actionListener :
                buttonCancel.getActionListeners()) {
            buttonCancel.removeActionListener(actionListener);
        }

        buttonOK.addActionListener(e -> {
            updateThread = new Thread(() -> onOK());
            updateThread.start();
        });

        buttonCancel.addActionListener(e -> onCancel());
    }

    private void createGUI() {
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);
        if (IS_WINDOWS_XP) {
            //for windows xp&server 2003
            setIconImage(Toolkit.getDefaultToolkit().getImage(UpdateDialog.class.getResource("/updaterIcon16_white.png")));
        } else {
            setIconImage(Toolkit.getDefaultToolkit().getImage(UpdateDialog.class.getResource("/updaterIcon16.png")));

        }

        createDefaultActionListeners();

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

        updateInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onUpdateInfoButton();
            }
        });

        updateInfoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pack();
        setLocation(FrameUtils.getFrameOnCenterLocationPoint(this));
        setSize(new Dimension(400, 170));
        setResizable(false);
    }

    public AppVersion getAppVersion() {
        return serverAppVersion;
    }

    private void loadProperties() {
        try {
            currentVersionLabel.setText(RegistryManager.getAppVersionValue());
        } catch (RegistryException e) {
            currentVersionLabel.setText(ApplicationConstants.APP_VERSION);
        }
        availableVersionLabel.setText("Unknown");
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
        }
        File updateJar = new File(ApplicationConstants.UPDATE_PATH_FILE + "Updater_.jar");
        if (updateJar.exists()) {
            runCleanTempUpdaterFile();
        }
        dispose();
    }

    private void onOK() {
        buttonOK.setEnabled(false);
        if (!Thread.currentThread().isInterrupted()) {
            //TODO make this beautifull: call downloader, return file, then call installation
            int result = 0;
            try {
                result = Updater.startUpdate(serverAppVersion);
            } catch (IOException e) {
                log.warn(e);
                result = ApplicationConstants.UPDATE_CODE_INTERRUPT;
                UserUtils.showErrorMessageToUser(this, lostConnectionTitle, lostConnectionMessage); //TODO translate this
            }
            if (Thread.currentThread().isInterrupted()) {
                result = ApplicationConstants.UPDATE_CODE_INTERRUPT;
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

    private void onUpdateInfoButton() {
        if (serverAppVersion != null) {
            if (!serverAppVersion.getUpdateInfo().isEmpty()) {
                UpdateInfoDialog dialog = new UpdateInfoDialog(serverAppVersion);
            }
        }
    }

    private void processUpdateResult(int installationCode) {
        System.out.println("Installation code: " + installationCode);
        switch (installationCode) {
            case ApplicationConstants.UPDATE_CODE_SUCCESS:
                updateSuccessfullyInstalled();
                break;
            case ApplicationConstants.UPDATE_CODE_CANCEL:
                UserUtils.showErrorMessageToUser(this, installationCancelledTitle,
                        installationCancelledMessage);
                Updater.installerFile.delete();
                break;
            case ApplicationConstants.UPDATE_CODE_NO_FILE:
                UserUtils.showErrorMessageToUser(this, installationCancelledTitle, noPermissionsMessage);
                break;

            case ApplicationConstants.UPDATE_CODE_CORRUPT:
                UserUtils.showErrorMessageToUser(this, installationCancelledTitle, noPermissionsMessage);
                break;
            case ApplicationConstants.UPDATE_CODE_INTERRUPT:/*NOP*/
                break;
            default:
                String message = installationCancelledByErrorMessage1
                        + "\n" + installationCancelledByErrorMessage2 +
                        installationCode
                        + "\n" + installationCancelledByErrorMessage3;
                UserUtils.showErrorMessageToUser(this, installationCancelledTitle, message);
                break;
        }
    }

    private void removeAllListeners(JButton button) {
        for (ActionListener al :
                button.getActionListeners()) {
            button.removeActionListener(al);
        }
    }

    private void runCleanInstallerFile() {
        log.info("Deleting file: " + ApplicationConstants.UPDATE_PATH_FILE + ApplicationConstants.WINDOWS_WEBLOCOPENER_SETUP_NAME + "V"
                + serverAppVersion.getVersion() + "" + ".exe");
        File installer = new File(ApplicationConstants.UPDATE_PATH_FILE + ApplicationConstants.WINDOWS_WEBLOCOPENER_SETUP_NAME + "V"
                + serverAppVersion.getVersion() + ".exe");
        installer.deleteOnExit();
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

        messageTimer = new Timer(MessagePushable.DEFAULT_TIMER_DELAY, e -> {
            errorTextPane.setText("");
            errorPanel.setVisible(false);
            updateSize(UpdateSizeMode.AFTER_HIDE);
        });
        messageTimer.setRepeats(false);
        messageTimer.start();
    }

    private void translateDialog() {
        translation = new Translation("translations/UpdateDialogBundle") {
            @Override
            public void initTranslations() {
                setTitle(messages.getString("windowTitle"));
                buttonOK.setText(messages.getString("buttonOk"));
                buttonCancel.setText(messages.getString("buttonCancel"));

                currentVersionStringLabel.setText(messages.getString("currentVersionStringLabel"));
                availableVersionStringLabel.setText(messages.getString("availableVersionStringLabel"));
                availableVersionLabel.setText(messages.getString("availableVersionLabel"));

                successTitle = messages.getString("successTitle");
                successUpdatedMessage = messages.getString("successUpdatedMessage");
                installationCancelledTitle = messages.getString("installationCancelledTitle");
                installationCancelledMessage = messages.getString("installationCancelledMessage");
                noPermissionsMessage = messages.getString("noPermissionsMessage");
                installationCancelledByErrorMessage1 = messages.getString("installationCancelledByErrorMessage1");
                installationCancelledByErrorMessage2 = messages.getString("installationCancelledByErrorMessage2");
                installationCancelledByErrorMessage3 = messages.getString("installationCancelledByErrorMessage3");

                lostConnectionTitle = messages.getString("lostConnectionTitle");
                lostConnectionMessage = messages.getString("lostConnectionMessage");

                retryButton = messages.getString("retryButton");
            }
        };
        translation.initTranslations();
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

    private void updateSuccessfullyInstalled() {
        try {
            RegistryFixer.fixRegistry();
        } catch (Exception ignore) {
            /*NOP*/
        }


        UserUtils.showSuccessMessageToUser(this, successTitle,
                successUpdatedMessage + serverAppVersion.getVersion());

        if (Main.mode != Main.Mode.AFTER_UPDATE) {
            //dispose(); //TODO test it, if ok, delete
            runCleanTempUpdaterFile();
        }

    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 10, 10, 10), -1, -1));
        contentPane.add(panel1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        buttonOK.setActionCommand(ResourceBundle.getBundle("translations/UpdateDialogBundle").getString("buttonOk"));
        buttonOK.setEnabled(false);
        Font buttonOKFont = this.$$$getFont$$$(null, Font.BOLD, -1, buttonOK.getFont());
        if (buttonOKFont != null) buttonOK.setFont(buttonOKFont);
        this.$$$loadButtonText$$$(buttonOK, ResourceBundle.getBundle("translations/UpdateDialogBundle").getString("buttonOk"));
        panel2.add(buttonOK, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setActionCommand(ResourceBundle.getBundle("translations/UpdateDialogBundle").getString("buttonCancel"));
        this.$$$loadButtonText$$$(buttonCancel, ResourceBundle.getBundle("translations/UpdateDialogBundle").getString("buttonCancel"));
        panel2.add(buttonCancel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(4, 8, new Insets(10, 10, 0, 10), -1, -1));
        contentPane.add(panel3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel3.add(spacer1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        progressBar1 = new JProgressBar();
        progressBar1.setStringPainted(false);
        panel3.add(progressBar1, new GridConstraints(2, 0, 1, 8, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        currentVersionStringLabel = new JLabel();
        this.$$$loadLabelText$$$(currentVersionStringLabel, ResourceBundle.getBundle("translations/UpdateDialogBundle").getString("currentVersionStringLabel"));
        panel3.add(currentVersionStringLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        currentVersionLabel = new JLabel();
        Font currentVersionLabelFont = this.$$$getFont$$$(null, Font.BOLD, -1, currentVersionLabel.getFont());
        if (currentVersionLabelFont != null) currentVersionLabel.setFont(currentVersionLabelFont);
        currentVersionLabel.setText("");
        panel3.add(currentVersionLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        availableVersionStringLabel = new JLabel();
        this.$$$loadLabelText$$$(availableVersionStringLabel, ResourceBundle.getBundle("translations/UpdateDialogBundle").getString("availableVersionStringLabel"));
        panel3.add(availableVersionStringLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        availableVersionLabel = new JLabel();
        Font availableVersionLabelFont = this.$$$getFont$$$(null, Font.BOLD, -1, availableVersionLabel.getFont());
        if (availableVersionLabelFont != null) availableVersionLabel.setFont(availableVersionLabelFont);
        availableVersionLabel.setText("");
        panel3.add(availableVersionLabel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel3.add(spacer2, new GridConstraints(1, 4, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        newVersionSizeLabel = new JLabel();
        newVersionSizeLabel.setText("");
        panel3.add(newVersionSizeLabel, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        unitLabel = new JLabel();
        unitLabel.setText("");
        panel3.add(unitLabel, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        updateInfoButton = new JButton();
        updateInfoButton.setBorderPainted(false);
        updateInfoButton.setContentAreaFilled(true);
        updateInfoButton.setDefaultCapable(true);
        updateInfoButton.setDoubleBuffered(false);
        updateInfoButton.setEnabled(false);
        updateInfoButton.setIcon(new ImageIcon(getClass().getResource("/infoIcon16.png")));
        updateInfoButton.setMargin(new Insets(2, 2, 2, 2));
        updateInfoButton.setRequestFocusEnabled(false);
        updateInfoButton.setText("");
        panel3.add(updateInfoButton, new GridConstraints(1, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        errorPanel = new JPanel();
        errorPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        errorPanel.setBackground(new Color(-65536));
        errorPanel.setVisible(false);
        contentPane.add(errorPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 65), new Dimension(-1, 65), 0, false));
        errorTextPane = new JTextPane();
        errorTextPane.setBackground(new Color(-16777216));
        errorTextPane.setEditable(false);
        errorTextPane.setEnabled(true);
        errorTextPane.setFocusCycleRoot(false);
        errorTextPane.setFocusable(false);
        Font errorTextPaneFont = this.$$$getFont$$$("Segoe UI", Font.BOLD, 14, errorTextPane.getFont());
        if (errorTextPaneFont != null) errorTextPane.setFont(errorTextPaneFont);
        errorTextPane.setForeground(new Color(-1));
        errorTextPane.setOpaque(false);
        errorTextPane.setRequestFocusEnabled(false);
        errorTextPane.setVerifyInputWhenFocusTarget(false);
        errorPanel.add(errorTextPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    private void $$$loadLabelText$$$(JLabel component, String text) {
        StringBuffer result = new StringBuffer();
        boolean haveMnemonic = false;
        char mnemonic = '\0';
        int mnemonicIndex = -1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '&') {
                i++;
                if (i == text.length()) break;
                if (!haveMnemonic && text.charAt(i) != '&') {
                    haveMnemonic = true;
                    mnemonic = text.charAt(i);
                    mnemonicIndex = result.length();
                }
            }
            result.append(text.charAt(i));
        }
        component.setText(result.toString());
        if (haveMnemonic) {
            component.setDisplayedMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }

    /**
     * @noinspection ALL
     */
    private void $$$loadButtonText$$$(AbstractButton component, String text) {
        StringBuffer result = new StringBuffer();
        boolean haveMnemonic = false;
        char mnemonic = '\0';
        int mnemonicIndex = -1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '&') {
                i++;
                if (i == text.length()) break;
                if (!haveMnemonic && text.charAt(i) != '&') {
                    haveMnemonic = true;
                    mnemonic = text.charAt(i);
                    mnemonicIndex = result.length();
                }
            }
            result.append(text.charAt(i));
        }
        component.setText(result.toString());
        if (haveMnemonic) {
            component.setMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

    enum UpdateSizeMode {BEFORE_HIDE, AFTER_HIDE}
}

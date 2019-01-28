/*
 * (C) Copyright 2019.  Eugene Zrazhevsky and others.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Contributors:
 * Eugene Zrazhevsky <eugene.zrazhevsky@gmail.com>
 */

package com.github.benchdoos.weblocopener.gui;

import com.github.benchdoos.weblocopener.core.Application;
import com.github.benchdoos.weblocopener.core.Translation;
import com.github.benchdoos.weblocopener.core.constants.ApplicationConstants;
import com.github.benchdoos.weblocopener.core.constants.SettingsConstants;
import com.github.benchdoos.weblocopener.preferences.PreferencesManager;
import com.github.benchdoos.weblocopener.service.Analyzer;
import com.github.benchdoos.weblocopener.utils.*;
import com.github.benchdoos.weblocopener.utils.browser.Browser;
import com.github.benchdoos.weblocopener.utils.browser.BrowserManager;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.MinimalBalloonStyle;
import net.java.balloontip.utils.TimingUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TooManyListenersException;

public class SettingsDialog extends JFrame {
    private static final Logger log = LogManager.getLogger(Logging.getCurrentClassName());

    private boolean onInit = true;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JCheckBox autoUpdateEnabledCheckBox;
    private JButton checkUpdatesButton;
    private JLabel versionLabel;
    private JLabel versionStringLabel;
    private JButton aboutButton;
    private JComboBox<Object> comboBox;
    private JTextField callTextField;
    private JLabel callLabel;
    private JLabel syntaxInfoLabel;
    private JCheckBox incognitoCheckBox;
    private JCheckBox openFolderForQRCheckBox;
    private JCheckBox showNotificationsToUserCheckBox;
    private JScrollPane scrollpane;
    private String toolTipText = "" +
            "<html>" +
            "  <body style=\"font-size:10px;\">Syntax: <b><u>file path</u></b> <b style=\"color:red;\">%site</b>, don't forget to add <b>%site</b>" +
            "  <br>Example for Google Chrome: <b style=\"color:green;\">start chrome \"%site\"</b>" +
            "  </body>" +
            "</html>";

    private String customBrowserName = "Custom...";

    public SettingsDialog() {
        log.debug("Creating settings dialog.");
        translateDialog();
        initGui();
        log.debug("Settings dialog created.");
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
        contentPane.setLayout(new GridLayoutManager(3, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel1.add(panel2, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        this.$$$loadButtonText$$$(buttonOK, ResourceBundle.getBundle("translations/SettingsDialogBundle").getString("buttonOk"));
        panel2.add(buttonOK, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        this.$$$loadButtonText$$$(buttonCancel, ResourceBundle.getBundle("translations/SettingsDialogBundle").getString("buttonCancel"));
        panel2.add(buttonCancel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        aboutButton = new JButton();
        this.$$$loadButtonText$$$(aboutButton, ResourceBundle.getBundle("translations/SettingsDialogBundle").getString("buttonAbout"));
        panel1.add(aboutButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setForeground(new Color(-6316129));
        this.$$$loadLabelText$$$(label1, ResourceBundle.getBundle("translations/SettingsDialogBundle").getString("dragAndDropNotice"));
        panel1.add(label1, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        contentPane.add(spacer2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollpane = new JScrollPane();
        contentPane.add(scrollpane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(300, 150), null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        scrollpane.setViewportView(panel3);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), null));
        comboBox = new JComboBox();
        comboBox.setMaximumRowCount(9);
        panel4.add(comboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        this.$$$loadLabelText$$$(label2, ResourceBundle.getBundle("translations/SettingsDialogBundle").getString("openInBrowser"));
        panel4.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        callLabel = new JLabel();
        this.$$$loadLabelText$$$(callLabel, ResourceBundle.getBundle("translations/SettingsDialogBundle").getString("customCallLabel"));
        callLabel.setVisible(true);
        panel4.add(callLabel, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        incognitoCheckBox = new JCheckBox();
        incognitoCheckBox.setEnabled(false);
        incognitoCheckBox.setText("");
        incognitoCheckBox.setToolTipText(ResourceBundle.getBundle("translations/SettingsDialogBundle").getString("incognitoModeTooltip"));
        panel4.add(incognitoCheckBox, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel4.add(spacer3, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        callTextField = new JTextField();
        callTextField.setVisible(true);
        panel4.add(callTextField, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(80, -1), null, 0, false));
        syntaxInfoLabel = new JLabel();
        syntaxInfoLabel.setIcon(new ImageIcon(getClass().getResource("/images/infoIcon16.png")));
        syntaxInfoLabel.setText("");
        panel4.add(syntaxInfoLabel, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        versionLabel = new JLabel();
        this.$$$loadLabelText$$$(versionLabel, ResourceBundle.getBundle("translations/SettingsDialogBundle").getString("versionLabel"));
        panel5.add(versionLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        versionStringLabel = new JLabel();
        this.$$$loadLabelText$$$(versionStringLabel, ResourceBundle.getBundle("translations/SettingsDialogBundle").getString("versionString"));
        panel5.add(versionStringLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel5.add(spacer4, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel6, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        autoUpdateEnabledCheckBox = new JCheckBox();
        autoUpdateEnabledCheckBox.setContentAreaFilled(true);
        autoUpdateEnabledCheckBox.setSelected(true);
        this.$$$loadButtonText$$$(autoUpdateEnabledCheckBox, ResourceBundle.getBundle("translations/SettingsDialogBundle").getString("autoUpdateEnabledCheckBox"));
        autoUpdateEnabledCheckBox.setVerifyInputWhenFocusTarget(false);
        autoUpdateEnabledCheckBox.setVerticalAlignment(0);
        panel6.add(autoUpdateEnabledCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JSeparator separator1 = new JSeparator();
        panel6.add(separator1, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkUpdatesButton = new JButton();
        this.$$$loadButtonText$$$(checkUpdatesButton, ResourceBundle.getBundle("translations/SettingsDialogBundle").getString("checkUpdatesButton"));
        panel6.add(checkUpdatesButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel6.add(spacer5, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        openFolderForQRCheckBox = new JCheckBox();
        openFolderForQRCheckBox.setSelected(true);
        this.$$$loadButtonText$$$(openFolderForQRCheckBox, ResourceBundle.getBundle("translations/SettingsDialogBundle").getString("openFolderForQRCheckBox"));
        panel6.add(openFolderForQRCheckBox, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        showNotificationsToUserCheckBox = new JCheckBox();
        showNotificationsToUserCheckBox.setSelected(true);
        this.$$$loadButtonText$$$(showNotificationsToUserCheckBox, ResourceBundle.getBundle("translations/SettingsDialogBundle").getString("showNotifications"));
        panel6.add(showNotificationsToUserCheckBox, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        panel3.add(spacer6, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
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

    private int findBrowser(String browserValue) {
        int result;
        for (int i = 0; i < BrowserManager.getBrowserList().size(); i++) {
            Browser browser = BrowserManager.getBrowserList().get(i);
            log.debug("Checking if incoming browser {} is in list, now: {}", browserValue, browser);

            if (browser.getCall() != null) {
                if (browser.getCall().equals(browserValue)) {
                    result = i;
                    return result;
                } else if (browser.getIncognitoCall() != null) {
                    if (browser.getIncognitoCall().equals(browserValue)) {
                        result = i;
                        return result;
                    }
                }
            }
        }

        if (browserValue.equalsIgnoreCase(SettingsConstants.BROWSER_DEFAULT_VALUE) || browserValue.isEmpty()) {
            return 0;
        } else return BrowserManager.getBrowserList().size() - 1;
    }

    private BalloonTip generateBalloonTip(String toolTipText) {
        BalloonTip balloonTip = new BalloonTip(syntaxInfoLabel, toolTipText);
        balloonTip.setStyle(new MinimalBalloonStyle(Color.white, 5));
        balloonTip.setCloseButton(null);
        balloonTip.setVisible(false);
        balloonTip.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                balloonTip.setVisible(false);
            }
        });
        return balloonTip;
    }

    private void initComboBox() {
        ArrayList<Browser> browsers = BrowserManager.getBrowserList();

        Browser others = new Browser(
                Translation.getTranslatedString("SettingsDialogBundle", "customBrowserName"), null);
        browsers.add(others);

        comboBox.setModel(new DefaultComboBoxModel<>(browsers.toArray()));
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Browser) {
                    return super.getListCellRendererComponent(list, ((Browser) value).getName(), index, isSelected, cellHasFocus);
                } else {
                    return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                }
            }
        });

        comboBox.addActionListener(e -> {
            if (comboBox.getSelectedIndex() == comboBox.getItemCount() - 1) {
                if (!onInit) {
                    log.info("Opening file browser for custom browser search");
                    String path = openFileBrowser();
                    if (path != null) {
                        callLabel.setVisible(true);
                        callTextField.setVisible(true);
                        callTextField.setText(path);
                        incognitoCheckBox.setEnabled(false);
                    } else {
                        comboBox.setSelectedIndex(findBrowser(PreferencesManager.getBrowserValue()));
                    }
                } else {
                    callLabel.setVisible(true);
                    callTextField.setText(PreferencesManager.getBrowserValue());
                    callTextField.setVisible(true);
                    syntaxInfoLabel.setVisible(true);
                }
            } else {
                if (comboBox.getSelectedIndex() == 0) {
                    incognitoCheckBox.setEnabled(false);
                    incognitoCheckBox.setSelected(false);
                } else {
                    if (browsers.get(comboBox.getSelectedIndex()).getIncognitoCall() != null) {
                        incognitoCheckBox.setEnabled(true);
                    } else {
                        incognitoCheckBox.setSelected(false);
                        incognitoCheckBox.setEnabled(false);
                    }
                }
                callLabel.setVisible(false);
                callTextField.setVisible(false);
            }
        });
    }

    private void initDropTarget() {
        Component component = this;
        final ImageIcon rickAndMortyIcon = new ImageIcon(Toolkit.getDefaultToolkit()
                .getImage(getClass().getResource("/images/easter/rickAndMorty.gif")));
        final DropTarget dropTarget = new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                onDrop(evt);
            }

            private void onDrop(DropTargetDropEvent evt) {
                try {
                    contentPane.setBorder(null);
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    final Object transferData = evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    List<?> list = (List<?>) transferData;
                    ArrayList<File> files = new ArrayList<>(list.size());

                    int easterCounter = 0;

                    for (Object o : list) {
                        if (o instanceof File) {
                            File file = (File) o;
                            try {
                                if (FilenameUtils.removeExtension(file.getName()).toLowerCase().contains("rick and morty")) {
                                    if (easterCounter == 0) {
                                        showRickAndMortyEaster();
                                        easterCounter++;
                                    }
                                }
                            } catch (Exception e) {
                                log.warn("Rick and Morty easter egg is broken", e);
                            }

                            if (Analyzer.getFileExtension(file).equalsIgnoreCase(ApplicationConstants.URL_FILE_EXTENSION)) {
                                try {
                                    files.add(Converter.convertUrlToWebloc(file));
                                    try {
                                        FileUtils.forceDelete(file);
                                    } catch (IOException e) {
                                        log.warn("Could not delete file: {}", file, e);
                                    }
                                } catch (IOException e) {
                                    log.warn("Could not convert *.url to *.webloc file: {}", file, e);
                                }

                            } else if (Analyzer.getFileExtension(file).equalsIgnoreCase(ApplicationConstants.WEBLOC_FILE_EXTENSION)) {
                                try {
                                    files.add(Converter.convertWeblocToUrl(file));
                                    try {
                                        FileUtils.forceDelete(file);
                                    } catch (IOException e) {
                                        log.warn("Could not delete file: {}", file, e);
                                    }
                                } catch (IOException e) {
                                    log.warn("Could not convert *.url to *.webloc file: {}", file, e);
                                }
                            }
                        }
                    }

                    if (files.size() == list.size()) {
                        UserUtils.showTrayMessage(ApplicationConstants.WEBLOCOPENER_APPLICATION_NAME,
                                Translation.getTranslatedString(
                                        "SettingsDialogBundle", "allFilesSuccessfullyConverted")
                                        + files.size() + "/" + list.size(), TrayIcon.MessageType.INFO);
                    } else {
                        UserUtils.showTrayMessage(ApplicationConstants.WEBLOCOPENER_APPLICATION_NAME,
                                Translation.getTranslatedString(
                                        "SettingsDialogBundle", "someFilesFailedToConvert")
                                        + files.size() + "/" + list.size(), TrayIcon.MessageType.WARNING);
                    }

                } catch (Exception ex) {
                    log.warn("Can not open files from drop", ex);
                    UserUtils.showTrayMessage(ApplicationConstants.WEBLOCOPENER_APPLICATION_NAME,
                            Translation.getTranslatedString(
                                    "SettingsDialogBundle", "couldNotConvertFiles"),
                            TrayIcon.MessageType.ERROR);
                }
            }

            private void showRickAndMortyEaster() {
                log.info("Look! This user has found an easter egg (Rick and Morty). Good job!");

                JFrame frame = new JFrame(ApplicationConstants.WEBLOCOPENER_APPLICATION_NAME);
                frame.setLayout(new GridLayout());
                JLabel label = new JLabel();
                label.setIcon(rickAndMortyIcon);
                frame.add(label);
                frame.setUndecorated(true);
                frame.setSize(500, 281);
                frame.setResizable(false);
                frame.setLocation(FrameUtils.getFrameOnCenterLocationPoint(frame));
                Timer timer = new Timer(5000, e -> frame.dispose());
                timer.setRepeats(false);
                timer.start();
                frame.setVisible(true);
            }

        };
        contentPane.setDropTarget(dropTarget);

        try {
            dropTarget.addDropTargetListener(new DropTargetAdapter() {
                @Override
                public void dragEnter(DropTargetDragEvent dtde) {
                    contentPane.setBorder(BorderFactory.createLineBorder(Color.RED));
                    super.dragEnter(dtde);
                }

                @Override
                public void dragExit(DropTargetEvent dte) {
                    contentPane.setBorder(null);
                    super.dragExit(dte);
                }

                @Override
                public void drop(DropTargetDropEvent dtde) {
                    contentPane.setBorder(null);
                }
            });
        } catch (TooManyListenersException e) {
            log.warn("Can not init drag and drop dropTarget", e);
        }
    }

    private void initGui() {
        setContentPane(contentPane);
        setTitle(Translation.getTranslatedString("SettingsDialogBundle", "windowTitle"));


        getRootPane().setDefaultButton(buttonOK);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/balloonIcon256.png")));

        syntaxInfoLabel.setVisible(false);
        callTextField.setVisible(false);
        callLabel.setVisible(false);

        initComboBox();

        loadSettings();

        buttonOK.addActionListener(e -> onSave());

        buttonCancel.addActionListener(e -> onCancel());

        checkUpdatesButton.addActionListener(e -> onUpdateNow());

        aboutButton.addActionListener(e -> onAbout());

        callTextField.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                syntaxInfoLabel.setVisible(false);
            }

            @Override
            public void componentShown(ComponentEvent e) {
                syntaxInfoLabel.setVisible(true);
            }
        });

        setSyntaxInfoButtonToolTip();

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        versionLabel.setText(CoreUtils.getApplicationVersionString());
        scrollpane.setBorder(null);

        onInit = false;

        initDropTarget();

        pack();
        setMinimumSize(new Dimension(400, 260));
        setSize(new Dimension(400, 260));
        setLocation(FrameUtils.getFrameOnCenterLocationPoint(this));
    }

    private void loadBrowserSettings() {
        final Browser browser = (Browser) comboBox.getSelectedItem();

        if (browser != null) {
            if (browser.getIncognitoCall() != null) {
                incognitoCheckBox.setSelected(PreferencesManager.getBrowserValue().equals(browser.getIncognitoCall()));
            } else {
                incognitoCheckBox.setSelected(false);
                incognitoCheckBox.setEnabled(false);
            }
        } else {
            incognitoCheckBox.setSelected(false);
            incognitoCheckBox.setEnabled(false);
        }
    }

    private void loadSettings() {
        autoUpdateEnabledCheckBox.setSelected(PreferencesManager.isAutoUpdateActive());
        comboBox.setSelectedIndex(findBrowser(PreferencesManager.getBrowserValue()));
        openFolderForQRCheckBox.setSelected(PreferencesManager.openFolderForQrCode());
        showNotificationsToUserCheckBox.setSelected(PreferencesManager.isNotificationsShown());
        loadBrowserSettings();
    }

    private void onAbout() {
        AboutApplicationDialog dialog = new AboutApplicationDialog();
        dialog.setVisible(true);
    }

    private void onCancel() {
        dispose();
    }

    private void onSave() {
        updateRegistry();
        dispose();
    }

    private void onUpdateNow() {
        dispose();
        Application.runUpdateDialog();
    }

    private String openFileBrowser() {
        log.debug("Opening File Browser");

        try {
            FileDialog fd = new FileDialog(this,
                    Translation.getTranslatedString("SettingsDialogBundle", "chooseAFile"),
                    FileDialog.LOAD);
            fd.setIconImage(Toolkit.getDefaultToolkit()
                    .getImage(getClass().getResource("/images/balloonIcon256.png")));
            fd.setDirectory(System.getProperty("user.dir"));
            fd.setFile("*.exe");
            fd.setMultipleMode(false);
            fd.setVisible(true);
            File[] f = fd.getFiles();
            if (f.length > 0) {
                log.debug("Choice: " + fd.getFiles()[0].getAbsolutePath());
                return fd.getFiles()[0].getAbsolutePath();
            } else {
                log.debug("Choice canceled");
                return null;
            }
        } catch (Exception e) {
            log.warn("Could not launch File Browser", e);
            return null;
        }
    }

    private void saveBrowser() {
        Browser browser = (Browser) comboBox.getSelectedItem();
        if (browser != null) {
            log.info("Browser call: " + browser.getCall());
            if (comboBox.getSelectedIndex() != comboBox.getItemCount() - 1) {
                if (browser.getCall() != null) {
                    if (!PreferencesManager.getBrowserValue().equals(browser.getCall())) {
                        if (!incognitoCheckBox.isSelected()) {
                            PreferencesManager.setBrowserValue(browser.getCall());
                        }
                    }
                }
                if (browser.getIncognitoCall() != null) {
                    if (!PreferencesManager.getBrowserValue().equals(browser.getIncognitoCall())) {
                        if (incognitoCheckBox.isSelected()) {
                            PreferencesManager.setBrowserValue(browser.getIncognitoCall());
                        }
                    }
                }
            } else {
                if (!callTextField.getText().equals(browser.getIncognitoCall())) {
                    PreferencesManager.setBrowserValue(callTextField.getText());
                }
            }
        }
    }

    private void setSyntaxInfoButtonToolTip() {

        syntaxInfoLabel.addMouseListener(new MouseAdapter() {
            final int DEFAULT_TIME = 10_000;
            final int SHORT_TIME = 6_000;

            BalloonTip balloonTip = generateBalloonTip(toolTipText);

            @Override
            public void mouseClicked(MouseEvent e) {
                balloonTip.setVisible(true);
                TimingUtils.showTimedBalloon(balloonTip, DEFAULT_TIME);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                balloonTip.setVisible(true);
                TimingUtils.showTimedBalloon(balloonTip, SHORT_TIME);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                balloonTip = generateBalloonTip(toolTipText);
            }
        });
    }

    private void translateDialog() {
        Translation translation = new Translation("translations/SettingsDialogBundle") {
            @Override
            public void initTranslations() {
                toolTipText = messages.getString("toolTipText");
            }
        };
        translation.initTranslations();
    }

    private void updateRegistry() {
        log.info("Saving settings: Auto-update enabled: {}; Open folder for QR-Code: {}; Notifications active: {}; Selected browser: {}",
                autoUpdateEnabledCheckBox.isSelected(), openFolderForQRCheckBox.isSelected(), showNotificationsToUserCheckBox.isSelected(),
                comboBox.getSelectedItem());
        PreferencesManager.setAutoUpdateActive(autoUpdateEnabledCheckBox.isSelected());
        PreferencesManager.setOpenFolderForQrCode(openFolderForQRCheckBox.isSelected());
        PreferencesManager.setNotificationsShown(showNotificationsToUserCheckBox.isSelected());
        saveBrowser();
    }
}

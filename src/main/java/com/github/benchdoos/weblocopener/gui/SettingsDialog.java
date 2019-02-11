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

import com.github.benchdoos.weblocopener.core.Translation;
import com.github.benchdoos.weblocopener.core.constants.ApplicationConstants;
import com.github.benchdoos.weblocopener.gui.panels.AppearanceSetterPanel;
import com.github.benchdoos.weblocopener.gui.panels.BrowserSetterPanel;
import com.github.benchdoos.weblocopener.gui.panels.MainSetterPanel;
import com.github.benchdoos.weblocopener.gui.panels.SettingsPanel;
import com.github.benchdoos.weblocopener.service.Analyzer;
import com.github.benchdoos.weblocopener.utils.Converter;
import com.github.benchdoos.weblocopener.utils.FrameUtils;
import com.github.benchdoos.weblocopener.utils.Logging;
import com.github.benchdoos.weblocopener.utils.UserUtils;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TooManyListenersException;

public class SettingsDialog extends JFrame {
    private static final Logger log = LogManager.getLogger(Logging.getCurrentClassName());

    private Timer settingsSavedTimer = null;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton aboutButton;
    private JScrollPane scrollpane;
    private JList<SettingsPanel> settingsList;
    private JPanel scrollPaneContent;
    private JButton buttonApply;
    private JLabel settingsSavedLabel;
    private BrowserSetterPanel browserSetterPanel;
    private MainSetterPanel mainSetterPanel;
    private AppearanceSetterPanel appearanceSetterPanel;


    public SettingsDialog() {
        $$$setupUI$$$();
        log.debug("Creating settings dialog.");
        initGui();
        log.debug("Settings dialog created.");
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(3, 2, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        this.$$$loadButtonText$$$(buttonOK, ResourceBundle.getBundle("translations/SettingsDialogBundle").getString("buttonOk"));
        panel2.add(buttonOK, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        this.$$$loadButtonText$$$(buttonCancel, ResourceBundle.getBundle("translations/SettingsDialogBundle").getString("buttonCancel"));
        panel2.add(buttonCancel, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonApply = new JButton();
        this.$$$loadButtonText$$$(buttonApply, ResourceBundle.getBundle("translations/SettingsDialogBundle").getString("buttonApply"));
        panel2.add(buttonApply, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        aboutButton = new JButton();
        this.$$$loadButtonText$$$(aboutButton, ResourceBundle.getBundle("translations/SettingsDialogBundle").getString("buttonAbout"));
        panel1.add(aboutButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setForeground(new Color(-6316129));
        this.$$$loadLabelText$$$(label1, ResourceBundle.getBundle("translations/SettingsDialogBundle").getString("dragAndDropNotice"));
        panel1.add(label1, new GridConstraints(0, 0, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        settingsSavedLabel = new JLabel();
        settingsSavedLabel.setForeground(new Color(-16732650));
        this.$$$loadLabelText$$$(settingsSavedLabel, ResourceBundle.getBundle("translations/SettingsDialogBundle").getString("settingsSaved"));
        panel1.add(settingsSavedLabel, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        contentPane.add(spacer2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JSplitPane splitPane1 = new JSplitPane();
        contentPane.add(splitPane1, new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setMinimumSize(new Dimension(128, 15));
        splitPane1.setLeftComponent(scrollPane1);
        settingsList = new JList();
        settingsList.setSelectionMode(0);
        scrollPane1.setViewportView(settingsList);
        scrollpane = new JScrollPane();
        splitPane1.setRightComponent(scrollpane);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        scrollpane.setViewportView(panel3);
        panel3.add(scrollPaneContent, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel3.add(spacer3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
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

    private void createUIComponents() {
        scrollPaneContent = new JPanel();
        scrollPaneContent.setLayout(new GridLayout());
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
                Translation translation = new Translation("SettingsDialogBundle");
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
                                    files.add(Converter.convert(file, ApplicationConstants.WEBLOC_FILE_EXTENSION));
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
                                    files.add(Converter.convert(file, ApplicationConstants.URL_FILE_EXTENSION));
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
                                translation.getTranslatedString("allFilesSuccessfullyConverted")
                                        + files.size() + "/" + list.size(), TrayIcon.MessageType.INFO);
                    } else {
                        UserUtils.showTrayMessage(ApplicationConstants.WEBLOCOPENER_APPLICATION_NAME,
                                translation.getTranslatedString("someFilesFailedToConvert")
                                        + files.size() + "/" + list.size(), TrayIcon.MessageType.WARNING);
                    }
                } catch (Exception ex) {
                    log.warn("Can not open files from drop", ex);
                    UserUtils.showTrayMessage(ApplicationConstants.WEBLOCOPENER_APPLICATION_NAME,
                            translation.getTranslatedString("couldNotConvertFiles"),
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

        initSettingsList();

        loadSettingsList();

        loadSettings();

        buttonApply.addActionListener(e -> onApply());

        buttonOK.addActionListener(e -> onSave());

        buttonCancel.addActionListener(e -> onCancel());


        aboutButton.addActionListener(e -> onAbout());


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


        scrollpane.setBorder(null);

        settingsSavedLabel.setVisible(false);

        initDropTarget();

        browserSetterPanel.init(); //don't forget it or it will crash fileBrowser


        pack();
        setMinimumSize(new Dimension(640, 300));
//        setSize(new Dimension(400, 260));
        setLocation(FrameUtils.getFrameOnCenterLocationPoint(this));
    }

    private void initSettingsList() {
        settingsList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof SettingsPanel) {
                    String name = "";
                    if (value instanceof MainSetterPanel) {
                        name = Translation.getTranslatedString("SettingsDialogBundle", "settingsMainPanelName");
                    } else if (value instanceof BrowserSetterPanel) {
                        name = Translation.getTranslatedString("SettingsDialogBundle", "settingsBrowserPanelName");
                    } else if (value instanceof AppearanceSetterPanel) {
                        name = Translation.getTranslatedString("SettingsDialogBundle", "settingsAppearancePanelName");
                    }
                    return super.getListCellRendererComponent(list, name, index, isSelected, cellHasFocus);
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        settingsList.addListSelectionListener(e -> {
            scrollPaneContent.removeAll();
            scrollPaneContent.add((Component) settingsList.getSelectedValue());
            scrollpane.updateUI();
        });
    }

    private void loadSettings() {
        final ListModel<SettingsPanel> model = settingsList.getModel();
        for (int i = 0; i < model.getSize(); i++) {
            final SettingsPanel settingsPanel = model.getElementAt(i);
            settingsPanel.loadSettings();
        }
        settingsList.setSelectedIndex(0);
    }

    private void loadSettingsList() {
        DefaultListModel<SettingsPanel> model = new DefaultListModel<>();

        mainSetterPanel = new MainSetterPanel();
        browserSetterPanel = new BrowserSetterPanel();
        appearanceSetterPanel = new AppearanceSetterPanel();

        model.addElement(mainSetterPanel);
        model.addElement(browserSetterPanel);
        model.addElement(appearanceSetterPanel);
        settingsList.setModel(model);
    }

    private void onAbout() {
        AboutApplicationDialog dialog = new AboutApplicationDialog();
        dialog.setVisible(true);
    }

    private void onApply() {
        updateRegistry();
        settingsSavedLabel.setVisible(true);
        if (settingsSavedTimer == null) {
            settingsSavedTimer = new Timer(5000, e -> {
                settingsSavedLabel.setVisible(false);
            });
            settingsSavedTimer.setRepeats(false);
        }
        settingsSavedTimer.restart();
    }

    private void onCancel() {
        dispose();
    }

    private void onSave() {
        updateRegistry();
        dispose();
    }


    private void updateRegistry() {
        mainSetterPanel.saveSettings();
        browserSetterPanel.saveSettings();
        appearanceSetterPanel.saveSettings();
    }
}

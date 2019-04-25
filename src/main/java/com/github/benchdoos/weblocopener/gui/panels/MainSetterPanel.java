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

package com.github.benchdoos.weblocopener.gui.panels;

import com.github.benchdoos.weblocopener.core.Application;
import com.github.benchdoos.weblocopener.core.Translation;
import com.github.benchdoos.weblocopener.core.constants.ApplicationConstants;
import com.github.benchdoos.weblocopener.core.constants.ArgumentConstants;
import com.github.benchdoos.weblocopener.core.constants.SettingsConstants;
import com.github.benchdoos.weblocopener.gui.Translatable;
import com.github.benchdoos.weblocopener.preferences.PreferencesManager;
import com.github.benchdoos.weblocopener.utils.CoreUtils;
import com.github.benchdoos.weblocopener.utils.FrameUtils;
import com.github.benchdoos.weblocopener.utils.Logging;
import com.github.benchdoos.weblocopener.utils.system.OperatingSystem;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;
import java.util.StringJoiner;

public class MainSetterPanel extends JPanel implements SettingsPanel, Translatable {
    private static final Logger log = LogManager.getLogger(Logging.getCurrentClassName());

    private JPanel contentPane;
    private JCheckBox autoUpdateEnabledCheckBox;
    private JButton checkUpdatesButton;
    private JCheckBox openFolderForQRCheckBox;
    private JCheckBox showNotificationsToUserCheckBox;
    private JLabel versionLabel;
    private JLabel versionStringLabel;
    private JComboBox<String> converterComboBox;
    private JLabel convertToLabel;
    private JPanel unixOpenModePanel;
    private JComboBox<UnixOpenMode> unixOpenModeComboBox;
    private JCheckBox betaInstallCheckBox;
    private String mode;

    public MainSetterPanel() {
        initGui();
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
        panel1.setLayout(new GridLayoutManager(7, 3, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        openFolderForQRCheckBox = new JCheckBox();
        openFolderForQRCheckBox.setSelected(true);
        this.$$$loadButtonText$$$(openFolderForQRCheckBox, ResourceBundle.getBundle("translations/MainSetterPanelBundle").getString("openFolderForQRCheckBox"));
        panel1.add(openFolderForQRCheckBox, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        showNotificationsToUserCheckBox = new JCheckBox();
        showNotificationsToUserCheckBox.setSelected(true);
        this.$$$loadButtonText$$$(showNotificationsToUserCheckBox, ResourceBundle.getBundle("translations/MainSetterPanelBundle").getString("showNotificationsCheckBox"));
        panel1.add(showNotificationsToUserCheckBox, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JSeparator separator1 = new JSeparator();
        panel1.add(separator1, new GridConstraints(5, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(6, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        convertToLabel = new JLabel();
        this.$$$loadLabelText$$$(convertToLabel, ResourceBundle.getBundle("translations/MainSetterPanelBundle").getString("convertTo"));
        panel2.add(convertToLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        converterComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        converterComboBox.setModel(defaultComboBoxModel1);
        panel2.add(converterComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel2.add(spacer2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        unixOpenModePanel = new JPanel();
        unixOpenModePanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(unixOpenModePanel, new GridConstraints(4, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        unixOpenModePanel.setBorder(BorderFactory.createTitledBorder(ResourceBundle.getBundle("translations/MainSetterPanelBundle").getString("onlyForUnix")));
        final JLabel label1 = new JLabel();
        this.$$$loadLabelText$$$(label1, ResourceBundle.getBundle("translations/MainSetterPanelBundle").getString("unixOpenModeLabel"));
        unixOpenModePanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        unixOpenModeComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("Ask every time");
        defaultComboBoxModel2.addElement("Open");
        defaultComboBoxModel2.addElement("Edit");
        defaultComboBoxModel2.addElement("Copy to clipboard");
        defaultComboBoxModel2.addElement("Generate QR-Code");
        defaultComboBoxModel2.addElement("Copy QR-Code");
        unixOpenModeComboBox.setModel(defaultComboBoxModel2);
        unixOpenModePanel.add(unixOpenModeComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, new GridConstraints(0, 0, 2, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        autoUpdateEnabledCheckBox = new JCheckBox();
        autoUpdateEnabledCheckBox.setContentAreaFilled(true);
        autoUpdateEnabledCheckBox.setSelected(true);
        this.$$$loadButtonText$$$(autoUpdateEnabledCheckBox, ResourceBundle.getBundle("translations/MainSetterPanelBundle").getString("autoUpdateEnabledCheckBox"));
        autoUpdateEnabledCheckBox.setVerifyInputWhenFocusTarget(false);
        autoUpdateEnabledCheckBox.setVerticalAlignment(0);
        panel3.add(autoUpdateEnabledCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkUpdatesButton = new JButton();
        this.$$$loadButtonText$$$(checkUpdatesButton, ResourceBundle.getBundle("translations/MainSetterPanelBundle").getString("checkUpdatesButton"));
        panel3.add(checkUpdatesButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        betaInstallCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(betaInstallCheckBox, ResourceBundle.getBundle("translations/MainSetterPanelBundle").getString("betaUpdateInstallCheckBox"));
        panel3.add(betaInstallCheckBox, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        versionLabel = new JLabel();
        this.$$$loadLabelText$$$(versionLabel, ResourceBundle.getBundle("translations/MainSetterPanelBundle").getString("versionLabel"));
        panel4.add(versionLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        versionStringLabel = new JLabel();
        this.$$$loadLabelText$$$(versionStringLabel, ResourceBundle.getBundle("translations/MainSetterPanelBundle").getString("versionString"));
        panel4.add(versionStringLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel4.add(spacer3, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JSeparator separator2 = new JSeparator();
        contentPane.add(separator2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        convertToLabel.setLabelFor(converterComboBox);
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

    private void fillConvertComboBox() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("*." + ApplicationConstants.URL_FILE_EXTENSION);
        model.addElement("*." + ApplicationConstants.DESKTOP_FILE_EXTENSION);
        converterComboBox.setModel(model);
    }

    private void initGui() {
        setLayout(new GridLayout());
        add(contentPane);

        checkUpdatesButton.addActionListener(e -> onUpdateNow());
        versionLabel.setText(CoreUtils.getApplicationVersionString());

        initUnixOpenModePanel();

        initUnixOpenModeComboBox();

        fillUnixOpenModeComboBox();

        loadUnixOpenModeComboBox();

        fillConvertComboBox();
        translate();
    }

    private void loadUnixOpenModeComboBox() {
        this.mode = PreferencesManager.getUnixOpeningMode();

        final ComboBoxModel<UnixOpenMode> model = unixOpenModeComboBox.getModel();
        for (int i = 0; i < model.getSize(); i++) {
            final UnixOpenMode mode = model.getElementAt(i);
            if (mode.getMode().equalsIgnoreCase(this.mode)) {
                unixOpenModeComboBox.setSelectedItem(mode);
            }
        }
    }

    private void initUnixOpenModeComboBox() {
        unixOpenModeComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof UnixOpenMode) {
                    final UnixOpenMode mode = (UnixOpenMode) value;
                    return super.getListCellRendererComponent(list, mode.getModeName(), index, isSelected, cellHasFocus);
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        unixOpenModeComboBox.addActionListener(e -> {
            final UnixOpenMode selectedItem = ((UnixOpenMode) unixOpenModeComboBox.getSelectedItem());
            mode = selectedItem.getMode();
        });
    }

    private void fillUnixOpenModeComboBox() {
        UnixOpenMode defaultMode = new UnixOpenMode(SettingsConstants.OPENER_UNIX_DEFAULT_SELECTOR_MODE,
                Translation.getTranslatedString("MainSetterPanelBundle", "unixModeAsk"));
        UnixOpenMode openMode = new UnixOpenMode(ArgumentConstants.OPENER_OPEN_ARGUMENT,
                Translation.getTranslatedString("MainSetterPanelBundle", "unixModeOpen"));
        UnixOpenMode editMode = new UnixOpenMode(ArgumentConstants.OPENER_EDIT_ARGUMENT,
                Translation.getTranslatedString("MainSetterPanelBundle", "unixModeEdit"));
        UnixOpenMode copyMode = new UnixOpenMode(ArgumentConstants.OPENER_COPY_LINK_ARGUMENT,
                Translation.getTranslatedString("MainSetterPanelBundle", "unixModeCopy"));
        UnixOpenMode generateQrMode = new UnixOpenMode(ArgumentConstants.OPENER_EDIT_ARGUMENT,
                Translation.getTranslatedString("MainSetterPanelBundle", "unixModeGenerateQR"));
        UnixOpenMode copyQr = new UnixOpenMode(ArgumentConstants.OPENER_EDIT_ARGUMENT,
                Translation.getTranslatedString("MainSetterPanelBundle", "unixModeCopyQR"));

        DefaultComboBoxModel<UnixOpenMode> model = new DefaultComboBoxModel<>();
        model.addElement(defaultMode);
        model.addElement(openMode);
        model.addElement(editMode);
        model.addElement(copyMode);
        model.addElement(generateQrMode);
        model.addElement(copyQr);

        unixOpenModeComboBox.setModel(model);
    }

    private void initUnixOpenModePanel() {
        unixOpenModePanel.setVisible(OperatingSystem.isUnix());
    }

    private void loadConverterValue() {
        DefaultComboBoxModel<String> model = ((DefaultComboBoxModel<String>) converterComboBox.getModel());
        if (model != null) {
            for (int i = 0; i < model.getSize(); i++) {
                final String current = model.getElementAt(i);
                final String converterExportExtension = PreferencesManager.getConverterExportExtension();
                if (current.contains(converterExportExtension)) {
                    converterComboBox.setSelectedItem(current);
                }
            }
        }
    }

    @Override
    public void loadSettings() {
        loadConverterValue();
        autoUpdateEnabledCheckBox.setSelected(PreferencesManager.isAutoUpdateActive());
        betaInstallCheckBox.setSelected(PreferencesManager.isBetaUpdateInstalling());
        openFolderForQRCheckBox.setSelected(PreferencesManager.openFolderForQrCode());
        showNotificationsToUserCheckBox.setSelected(PreferencesManager.isNotificationsShown());
    }

    private void onUpdateNow() {
        FrameUtils.findWindow(this).dispose();
        Application.runUpdateDialog();
    }

    private void saveConverterValue() {
        final Object selectedItem = converterComboBox.getSelectedItem();
        if (selectedItem != null) {
            if (selectedItem.toString().toLowerCase().contains(ApplicationConstants.URL_FILE_EXTENSION)) {
                PreferencesManager.setConverterExportExtension(ApplicationConstants.URL_FILE_EXTENSION);
            } else if (selectedItem.toString().toLowerCase().contains(ApplicationConstants.DESKTOP_FILE_EXTENSION)) {
                PreferencesManager.setConverterExportExtension(ApplicationConstants.DESKTOP_FILE_EXTENSION);
            }
        }
    }

    @Override
    public void saveSettings() {
        final boolean update = autoUpdateEnabledCheckBox.isSelected();
        final boolean beta = betaInstallCheckBox.isSelected();
        final boolean folder = openFolderForQRCheckBox.isSelected();
        final boolean notification = showNotificationsToUserCheckBox.isSelected();
        final Object converterMode = converterComboBox.getSelectedItem();

        final UnixOpenMode mode = ((UnixOpenMode) unixOpenModeComboBox.getSelectedItem());
        if (mode != null) {
            this.mode = mode.getMode();
        }

        saveConverterValue();
        log.info("Saving settings: " +
                        "auto-update: {}, " +
                        "beta installing: {}, " +
                        "open folder for qr-code: {}, " +
                        "notifications available: {}, " +
                        "converter will save: {}, " +
                        "unix mode: {}",
                update, beta, folder, notification, converterMode, this.mode);

        PreferencesManager.setAutoUpdateActive(update);
        PreferencesManager.setBetaUpdateInstalling(beta);
        PreferencesManager.setOpenFolderForQrCode(folder);
        PreferencesManager.setNotificationsShown(notification);

        if (OperatingSystem.isUnix()) {
            PreferencesManager.setUnixOpeningMode(this.mode);
        }
    }

    @Override
    public void translate() {
        Translation translation = new Translation("MainSetterPanelBundle");
        versionStringLabel.setText(translation.getTranslatedString("versionString"));
        versionLabel.setText(CoreUtils.getApplicationVersionString());
        autoUpdateEnabledCheckBox.setText(translation.getTranslatedString("autoUpdateEnabledCheckBox"));
        checkUpdatesButton.setText(translation.getTranslatedString("checkUpdatesButton"));
        openFolderForQRCheckBox.setText(translation.getTranslatedString("openFolderForQRCheckBox"));
        showNotificationsToUserCheckBox.setText(translation.getTranslatedString("showNotificationsCheckBox"));
        convertToLabel.setText(translation.getTranslatedString("convertTo"));
    }

    class UnixOpenMode {
        private final String mode;
        private final String modeName;

        UnixOpenMode(String mode, String modeName) {
            this.mode = mode;

            this.modeName = modeName;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", UnixOpenMode.class.getSimpleName() + "[", "]")
                    .add("mode='" + mode + "'")
                    .add("modeName='" + modeName + "'")
                    .toString();
        }

        public String getMode() {
            return mode;
        }

        public String getModeName() {
            return modeName;
        }
    }
}

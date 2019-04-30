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
import com.github.benchdoos.weblocopener.preferences.PreferencesManager;
import com.github.benchdoos.weblocopener.service.gui.ClickListener;
import com.github.benchdoos.weblocopener.service.links.WeblocLink;
import com.github.benchdoos.weblocopener.utils.CoreUtils;
import com.github.benchdoos.weblocopener.utils.FrameUtils;
import com.github.benchdoos.weblocopener.utils.Logging;
import com.github.benchdoos.weblocopener.utils.notification.NotificationManager;
import com.github.benchdoos.weblocopener.utils.system.OperatingSystem;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.apache.commons.io.FileExistsException;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import static com.github.benchdoos.weblocopener.core.constants.ApplicationConstants.WEBLOC_FILE_EXTENSION;
import static com.github.benchdoos.weblocopener.core.constants.StringConstants.FAVICON_GETTER_URL;


public class EditDialog extends JFrame implements Translatable {
    private static final Logger log = LogManager.getLogger(Logging.getCurrentClassName());
    private final String pathToEditingFile;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField;
    private JLabel editLinkLabel;
    private JLabel urlPageTitle;
    private JButton clearTextButton;
    private JCheckBox autoRenameFileCheckBox;

    public EditDialog(String pathToEditingFile) {
        this.pathToEditingFile = pathToEditingFile;
        $$$setupUI$$$();

        initGui();

        log.debug("Got path: [" + pathToEditingFile + "]");
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
        contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 10, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 10, 0, 10), -1, -1));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel1.add(panel2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        this.$$$loadButtonText$$$(buttonOK, ResourceBundle.getBundle("translations/EditDialogBundle").getString("buttonOk"));
        buttonOK.putClientProperty("hideActionText", Boolean.FALSE);
        panel2.add(buttonOK, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        this.$$$loadButtonText$$$(buttonCancel, ResourceBundle.getBundle("translations/EditDialogBundle").getString("buttonCancel"));
        panel2.add(buttonCancel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        autoRenameFileCheckBox = new JCheckBox();
        autoRenameFileCheckBox.setEnabled(false);
        this.$$$loadButtonText$$$(autoRenameFileCheckBox, ResourceBundle.getBundle("translations/EditDialogBundle").getString("autoRenameFile"));
        autoRenameFileCheckBox.setToolTipText(ResourceBundle.getBundle("translations/EditDialogBundle").getString("canNotRenameToolTip"));
        panel1.add(autoRenameFileCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(3, 1, new Insets(5, 10, 0, 10), -1, -1));
        contentPane.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridBagLayout());
        panel3.add(panel4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(400, -1), new Dimension(400, -1), new Dimension(400, -1), 0, false));
        textField.setText("");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel4.add(textField, gbc);
        clearTextButton = new JButton();
        clearTextButton.setBorderPainted(false);
        clearTextButton.setContentAreaFilled(false);
        clearTextButton.setIcon(new ImageIcon(getClass().getResource("/images/closeButtons/circleGray12.png")));
        clearTextButton.setMargin(new Insets(6, 6, 6, 6));
        clearTextButton.setOpaque(false);
        clearTextButton.setPressedIcon(new ImageIcon(getClass().getResource("/images/closeButtons/circleRedDarker12.png")));
        clearTextButton.setRolloverIcon(new ImageIcon(getClass().getResource("/images/closeButtons/circleRed12.png")));
        clearTextButton.setText("");
        clearTextButton.setToolTipText(ResourceBundle.getBundle("translations/EditDialogBundle").getString("clearTextToolTip"));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel4.add(clearTextButton, gbc);
        editLinkLabel = new JLabel();
        editLinkLabel.setAutoscrolls(true);
        editLinkLabel.setEnabled(true);
        editLinkLabel.setFocusable(false);
        Font editLinkLabelFont = this.$$$getFont$$$(null, -1, 14, editLinkLabel.getFont());
        if (editLinkLabelFont != null) editLinkLabel.setFont(editLinkLabelFont);
        editLinkLabel.setOpaque(false);
        editLinkLabel.setRequestFocusEnabled(true);
        this.$$$loadLabelText$$$(editLinkLabel, ResourceBundle.getBundle("translations/EditDialogBundle").getString("EditWeblocLink"));
        editLinkLabel.setVerifyInputWhenFocusTarget(false);
        editLinkLabel.setVisible(true);
        panel3.add(editLinkLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        urlPageTitle = new JLabel();
        urlPageTitle.setForeground(new Color(-6316129));
        urlPageTitle.setHorizontalTextPosition(11);
        urlPageTitle.setText("");
        panel3.add(urlPageTitle, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, 1, GridConstraints.SIZEPOLICY_FIXED, new Dimension(16, 16), null, null, 0, false));
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

    private void createTitle() {
        String path = "";
        try {
            path = new File(pathToEditingFile).getName();
        } catch (Exception e) {
            log.warn("Could not get file name for: " + pathToEditingFile, e);
        }
        setTitle(path + " — WeblocOpener");
    }

    private void createUIComponents() {
        textField = new PlaceholderTextField();
        ((PlaceholderTextField) textField).setPlaceholder("URL");
    }

    private void fillTextField(String pathToEditingFile) {
        try {
            log.debug("Filling textfield by file content: " + pathToEditingFile);
            URL url = new WeblocLink().getUrl(new File(pathToEditingFile));
            textField.setText(url.toString());
            textField.setCaretPosition(textField.getText().length());
            textField.selectAll();
            log.debug("Got URL [" + url + "] from [" + pathToEditingFile + "]");
        } catch (Exception e) {
            log.warn("Can not read url from: [" + pathToEditingFile + "]: ", e);
            FrameUtils.fillTextFieldWithClipboard(textField);
        }
    }


    private void initGui() {
        createTitle();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/webloc256.png")));

        setContentPane(contentPane);


        getRootPane().setDefaultButton(buttonOK);

        clearTextButton.addActionListener(e -> onClearText());

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        ((PlaceholderTextField) textField).setPlaceholder("URL");

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                if (textField.getText().isEmpty()) {
                    FrameUtils.fillTextFieldWithClipboard(textField);
                }
                super.windowActivated(e);
            }

            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        editLinkLabel.setBackground(new Color(232, 232, 232));


        initTextField(pathToEditingFile);

        pack();

        setResizable(false);

        setLocation(FrameUtils.getFrameOnCenterLocationPoint(this));
        translate();
    }

    private void initTextField(String pathToEditingFile) {
        textField.addMouseListener(new ClickListener() {
            @Override
            public void doubleClick(MouseEvent e) {
                textField.selectAll();
            }
        });

        textField.getDocument().addDocumentListener(new DocumentListener() {
            Runnable runnable = () -> {
                updatePageTitle();
                updateRenameFileCheckBoxState();
                updateRenameFileCheckBoxToolTip();

                updatePageFaviconIcon();
            };

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

            private File getCreatingFileName(String pathToEditingFile) {
                final String fileName = CoreUtils.fixFileName(urlPageTitle.getToolTipText()) + "." + WEBLOC_FILE_EXTENSION;
                File folder = new File(pathToEditingFile).getParentFile();
                return new File(folder + File.separator + fileName);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTextFont();
                new Thread(runnable).start();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTextFont();
                new Thread(runnable).start();
            }

            private void updatePageFaviconIcon() {
                final String pageUrl = textField.getText();
                log.debug("Getting favicons from url: {}", pageUrl);
                if (pageUrl != null && !pageUrl.isEmpty()) {
                    try {
                        URL url = new URL(FAVICON_GETTER_URL + pageUrl);
                        log.debug("Creating connection");

                        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                        final int TIMEOUT = 7000;
                        connection.setConnectTimeout(TIMEOUT);

                        log.debug("Connection created successfully");

                        BufferedReader bufferedReader = new BufferedReader(
                                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

                        String input = bufferedReader.readLine();

                        JsonParser parser = new JsonParser();
                        JsonObject root = parser.parse(input).getAsJsonObject();
                        final JsonArray icons = root.getAsJsonArray("icons");
                        if (icons.size() > 0) {
                            final JsonElement jsonElement = icons.get(0);
                            final String faviconUrl = jsonElement.getAsJsonObject().getAsJsonPrimitive("url").getAsString();
                            final BufferedImage read = ImageIO.read(new URL(faviconUrl));
                            urlPageTitle.setIcon(new ImageIcon(CoreUtils.resize(read, 16, 16)));
                        }
                    } catch (Exception e) {
                        urlPageTitle.setIcon(null);
                        log.warn("Could not load favicon for page: {}", pageUrl, e);
                    }
                } else {
                    urlPageTitle.setIcon(null);
                    log.warn("Could not load favicon for empty page: {}", pageUrl);
                }
                updatePageTitle();
            }

            private void updatePageTitle() {
                String url = textField.getText();
                if (url != null && !url.isEmpty()) {
                    try {
                        String fullTitle = CoreUtils.getPageTitle(url);
                        urlPageTitle.setToolTipText(fullTitle);

                        urlPageTitle.setText(fullTitle);
                    } catch (Exception e) {
                        log.warn("Could not get page title", e);
                        urlPageTitle.setText(null);
                        urlPageTitle.setToolTipText(null);

                    }
                } else {
                    log.debug("Page is null, so title is clear");
                    urlPageTitle.setText(null);
                    urlPageTitle.setToolTipText(null);
                }
            }

            private void updateRenameFileCheckBoxState() {
                if (urlPageTitle.getText() != null) {
                    if (urlPageTitle.getText().isEmpty()) {
                        autoRenameFileCheckBox.setSelected(false);
                        autoRenameFileCheckBox.setEnabled(false);
                    } else {
                        File file = getCreatingFileName(pathToEditingFile);
                        log.debug("Checking for existing of file: [{}] and it exists: {}", file, file.exists());
                        if (!file.exists()) {
                            autoRenameFileCheckBox.setEnabled(true);
                        } else {
                            autoRenameFileCheckBox.setSelected(false);
                            autoRenameFileCheckBox.setEnabled(false);
                        }
                    }
                } else {
                    autoRenameFileCheckBox.setSelected(false);
                    autoRenameFileCheckBox.setEnabled(false);
                }
            }

            private void updateRenameFileCheckBoxToolTip() {
                Translation translation = new Translation("EditDialogBundle");

                if (urlPageTitle.getText() != null) {
                    if (!urlPageTitle.getText().isEmpty()) {
                        File file = getCreatingFileName(pathToEditingFile);
                        if (!file.exists()) {
                            autoRenameFileCheckBox.setToolTipText("<html>" + translation.getTranslatedString("autoRenameToolTip")
                                    + "<br>" + urlPageTitle.getToolTipText() + "</html>");
                        } else {
                            autoRenameFileCheckBox.setToolTipText(translation.getTranslatedString("canNotRenameExistsToolTip"));

                        }
                    } else {
                        autoRenameFileCheckBox.setToolTipText(translation.getTranslatedString("canNotRenameToolTip"));
                    }
                } else {
                    autoRenameFileCheckBox.setToolTipText(translation.getTranslatedString("canNotRenameToolTip"));
                }
            }

        });

        UndoManager undoManager = new UndoManager();
        textField.getDocument().addUndoableEditListener(evt -> undoManager.addEdit(evt.getEdit()));

        textField.getActionMap().put("Undo",
                new AbstractAction("Undo") {
                    public void actionPerformed(ActionEvent evt) {
                        try {
                            if (undoManager.canUndo()) {
                                undoManager.undo();
                            }
                        } catch (CannotUndoException ignore) {
                            /*NOP*/
                        }
                    }
                });

        textField.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "Undo");

        textField.getActionMap().put("Redo",
                new AbstractAction("Redo") {
                    public void actionPerformed(ActionEvent evt) {
                        try {
                            if (undoManager.canRedo()) {
                                undoManager.redo();
                            }
                        } catch (CannotRedoException ignore) {
                            /*NOP*/
                        }
                    }
                });

        textField.getInputMap().put(KeyStroke.getKeyStroke("control shift Z"), "Redo");

        fillTextField(pathToEditingFile);
    }

    @Override
    public void translate() {
        Translation translation = new Translation("EditDialogBundle");
        editLinkLabel.setText(translation.getTranslatedString("EditWeblocLink"));
        clearTextButton.setToolTipText(translation.getTranslatedString("clearTextToolTip"));
        autoRenameFileCheckBox.setText(translation.getTranslatedString("autoRenameFile"));
        autoRenameFileCheckBox.setToolTipText(translation.getTranslatedString("canNotRenameToolTip"));
        buttonOK.setText(translation.getTranslatedString("buttonOk"));
        buttonCancel.setText(translation.getTranslatedString("buttonCancel"));

    }

    public void updateTextFont() {
        UrlValidator urlValidator = new UrlValidator();
        if (urlValidator.isValid(textField.getText())) {
            if (textField != null) {
                FrameUtils.setTextFieldFont(textField, textField.getFont(), TextAttribute.UNDERLINE_ON);
                if (!PreferencesManager.isDarkModeEnabledNow()) {
                    prepareTextFieldColor();
                } else {
                    textField.setForeground(Color.decode("#1BA7FF"));
                }
            }
        } else {
            if (textField != null) {
                FrameUtils.setTextFieldFont(textField, textField.getFont(), TextAttribute.SUPERSCRIPT_SUB);
                if (!PreferencesManager.isDarkModeEnabledNow()) {
                    textField.setForeground(Color.BLACK);
                } else {
                    textField.setForeground(ApplicationConstants.DARK_MODE_THEME.getTextComponentElement().getForegroundColor());
                }
            }
        }
    }

    private void prepareTextFieldColor() {
        if (OperatingSystem.isUnix()) {
            final Color background = textField.getBackground();
            if (background.equals(Color.WHITE)) {
                textField.setForeground(Color.BLUE);
            } else {
                textField.setForeground(Color.decode("#1BA7FF"));
            }
        } else {
            textField.setForeground(Color.BLUE);
        }
    }


    private void manageFileName() {
        try {
            renameFileIfAsked(pathToEditingFile);
        } catch (Exception e) {
            final String fileName = urlPageTitle.getToolTipText() + "." + WEBLOC_FILE_EXTENSION;

            log.warn("Could not rename file {} to {}", pathToEditingFile, fileName, e);
            NotificationManager.getNotificationForCurrentOS().showWarningNotification(
                    ApplicationConstants.WEBLOCOPENER_APPLICATION_NAME,
                    Translation.getTranslatedString(
                            "EditDialogBundle", "canNotRenameFileMessage") + " " + fileName);
        }
    }

    private void onCancel() {
        dispose();
    }

    private void onClearText() {
        log.debug("Clearing textfield");
        textField.setText(null);
        urlPageTitle.setIcon(null);
        urlPageTitle.setText(null);
    }

    private void onOK() {
        final String urlText = textField.getText();
        try {
            URL url = new URL(urlText);
            UrlValidator urlValidator = new UrlValidator();
            if (urlValidator.isValid(urlText)) {
                PreferencesManager.getLink().createLink(new File(pathToEditingFile), url);

                manageFileName();

                dispose();
            } else {
                throw new MalformedURLException();
            }
        } catch (MalformedURLException e) {
            log.warn("Can not parse URL: [" + urlText + "]", e);


            String message = Translation.getTranslatedString(
                    "EditDialogBundle", "incorrectUrlMessage") + ": [";

            String incorrectUrl = urlText
                    .substring(0, Math.min(urlText.length(), 50));
            //Fixes EditDialog long url message showing issue
            message += urlText.length() > incorrectUrl.length() ? incorrectUrl + "...]" : incorrectUrl + "]";


            NotificationManager.getForcedNotification(this).showErrorNotification(
                    Translation.getTranslatedString("EditDialogBundle", "errorTitle"),
                    message);
        } catch (IOException e) {
            log.warn("Can not create file at: " + pathToEditingFile + " with url: " + urlText, e);

            String message = Translation.getTranslatedString(
                    "EditDialogBundle", "canNotSaveFile") + "<br>" + e.getMessage();
            NotificationManager.getForcedNotification(this).showWarningNotification(
                    Translation.getTranslatedString("EditDialogBundle", "errorTitle"),
                    "<html>" + message + "</html>");
        }

    }

    private void renameFileIfAsked(String pathToEditingFile) throws FileNotFoundException, FileExistsException {
        final String toolTip = urlPageTitle.getToolTipText();
        final String fileName = toolTip + "." + WEBLOC_FILE_EXTENSION;
        if (autoRenameFileCheckBox.isSelected()) {

            final File file = CoreUtils.renameFile(new File(pathToEditingFile), fileName);
            if (file.exists()) {
                final String successMessage = Translation.getTranslatedString("EditDialogBundle",
                        "fileSuccessfullyRenamedMessage");

                NotificationManager.getNotificationForCurrentOS().showInfoNotification(
                        ApplicationConstants.WEBLOCOPENER_APPLICATION_NAME,
                        successMessage + " " + CoreUtils.fixFileName(fileName));
            } else {
                throw new FileNotFoundException("File can not be found: " + file);
            }
        }
    }


    @Override
    public void setVisible(boolean b) {
        FrameUtils.showOnTop(this);
        super.setVisible(b);
    }
}

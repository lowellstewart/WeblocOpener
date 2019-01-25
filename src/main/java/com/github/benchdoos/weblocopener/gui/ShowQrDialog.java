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
import com.github.benchdoos.weblocopener.service.Analyzer;
import com.github.benchdoos.weblocopener.service.UrlsProceed;
import com.github.benchdoos.weblocopener.service.gui.MousePickListener;
import com.github.benchdoos.weblocopener.utils.CoreUtils;
import com.github.benchdoos.weblocopener.utils.FrameUtils;
import com.github.benchdoos.weblocopener.utils.Logging;
import com.github.benchdoos.weblocopener.utils.UserUtils;
import com.google.zxing.WriterException;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

public class ShowQrDialog extends JFrame {
    private static final Logger log = LogManager.getLogger(Logging.getCurrentClassName());

    private final String url;
    private final BufferedImage qrCodeImage;
    private final File weblocFile;
    private String title = "QR-Code";
    private JPanel contentPane;
    private ImagePanel imagePanel;
    private JButton openButton;
    private JButton saveImageButton;
    private JButton copyImageButton;


    public ShowQrDialog(File weblocFile) throws Exception {
        this.weblocFile = weblocFile;

        url = new Analyzer(weblocFile.getAbsolutePath()).getUrl();

        this.qrCodeImage = UrlsProceed.generateQrCode(url);
        $$$setupUI$$$();
        initGui();

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
        contentPane.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.setBackground(new Color(-1));
        contentPane.add(imagePanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        contentPane.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 4, new Insets(0, 10, 5, 10), -1, -1));
        panel1.setBackground(new Color(-1));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        saveImageButton = new JButton();
        saveImageButton.setIcon(new ImageIcon(getClass().getResource("/images/downloadsIcon16.png")));
        saveImageButton.setText("");
        saveImageButton.setToolTipText(ResourceBundle.getBundle("translations/ShowQrDialogBundle").getString("saveImageButton"));
        panel1.add(saveImageButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        openButton = new JButton();
        this.$$$loadButtonText$$$(openButton, ResourceBundle.getBundle("translations/ShowQrDialogBundle").getString("openButton"));
        panel1.add(openButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        copyImageButton = new JButton();
        copyImageButton.setIcon(new ImageIcon(getClass().getResource("/images/copyIcon16.png")));
        copyImageButton.setText("");
        copyImageButton.setToolTipText(ResourceBundle.getBundle("translations/ShowQrDialogBundle").getString("copyButton"));
        panel1.add(copyImageButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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

    private void createImageForFile(File qrFile) {
        try {
            final BufferedImage image = UrlsProceed.generateQrCode(url);
            ImageIO.write(image, "jpg", qrFile);
        } catch (Exception ex) {
            log.warn("Could not save image to {}", qrFile, ex);
        }
    }

    private void createImagePanel() {
        imagePanel = new ImagePanel(qrCodeImage);
        MousePickListener mousePickListener = new MousePickListener(this);

        imagePanel.addMouseListener(mousePickListener.getMouseAdapter);
        imagePanel.addMouseMotionListener(mousePickListener.getMouseMotionAdapter);
    }

    private void createUIComponents() {
        createImagePanel();
    }

    private void initActionListeners() {
        copyImageButton.addActionListener(e -> {
            try {
                final BufferedImage image = UrlsProceed.generateQrCode(url);
                CoreUtils.copyImageToClipBoard(image);

                UserUtils.showTrayMessage(ApplicationConstants.WEBLOCOPENER_APPLICATION_NAME,
                        Translation.getTranslatedString("ShowQrDialogBundle", "successCopyImage"),
                        TrayIcon.MessageType.INFO);
            } catch (IOException | WriterException ex) {
                log.warn("Could not create qr-code image for url: {}", url, ex);
                UserUtils.showTrayMessage(ApplicationConstants.WEBLOCOPENER_APPLICATION_NAME,
                        Translation.getTranslatedString("ShowQrDialogBundle", "errorCopyImage")
                        , TrayIcon.MessageType.ERROR);

            }
        });

        openButton.addActionListener(e -> {
            UrlsProceed.openUrl(url);
            dispose();
        });

        saveImageButton.addActionListener(e -> {
            try {
                final String s = CoreUtils.getFileName(weblocFile);
                File qrFile = new File(weblocFile.getParentFile()
                        + File.separator + s + "-qr.jpg");
                createImageForFile(qrFile);

                openFileInExplorer(qrFile);
            } catch (Exception e1) {
                log.warn("Could not save qr-code image", e1);
            }
            dispose();
        });
    }

    private void initGui() {

        setTitle(weblocFile.getName() + " — " + Translation.getTranslatedString("ShowQrDialogBundle", "windowTitle"));
        setIconImage(Toolkit.getDefaultToolkit().getImage(ShowQrDialog.class.getResource("/images/qrCode256.png")));


        setContentPane(contentPane);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        imagePanel.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(
                KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        initActionListeners();

        pack();
        setResizable(false);

        setLocation(FrameUtils.getFrameOnCenterLocationPoint(this));
    }

    private void onCancel() {
        dispose();
    }

    private void openFileInExplorer(File qrFile) {
        if (PreferencesManager.openFolderForQrCode()) {
            log.info("Opening image file: {}", qrFile);
            try {
                Runtime.getRuntime().exec("explorer.exe /select,\"" + qrFile + "\"");
            } catch (IOException ex) {
                log.warn("Could not open file {} in explorer", qrFile, ex);
                try {
                    log.debug("Opening parent: {}", qrFile.getParentFile());
                    Desktop.getDesktop().open(qrFile.getParentFile());
                } catch (Exception ex1) {
                    log.warn("Could not open parent for file: {}, skipping.", qrFile, ex1);
                }
            }
        } else {
            log.debug("Opening folder for qr-code is blocked by settings");
        }
    }
}

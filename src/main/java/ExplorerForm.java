import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.List;


public class ExplorerForm extends JFrame {

    private JPanel panel;
    private JLabel techDocLabel;
    private JTextField pathToTechDoc;
    private JLabel awbLabel;
    private JTextField pathToAwb;
    private JLabel partsLabel;
    private JTextArea partNumbers;
    private JButton button1;
    private JProgressBar progressBar1;

    public ExplorerForm() {
        setContentPane(panel);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        setBounds(100, 100, 400, 500);
        progressBar1.setMinimum(0);
        progressBar1.setMaximum(100);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (pathToTechDoc.getText().isEmpty() || pathToAwb.getText().isEmpty() || partNumbers.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Одно из полей пустое, выполнение невозможно");
                } else if (!pathToTechDoc.getText().trim().contains(":\\") || !pathToAwb.getText().trim().contains(":\\")) {
                    JOptionPane.showMessageDialog(panel, "Выполнение невозможно, проверьте корректность путей к папкам!");
                } else {
                    File techDocDirectory = new File(
                            pathToTechDoc.getText().trim());
                    File awbDirectory = new File(
                            pathToAwb.getText().trim());
                    int count = 1;

                    //Читаем список партийных номеров
                    String str = partNumbers.getText().trim();
                    List<String> parts = new ArrayList<>(Arrays.asList(str.split("\n+")));
                    List<Integer> fails = new ArrayList<>();

                    for (String partNum : parts) {
                        List<File> listOfFilesWithPartNumber = FileExplorer.getListOfFilesByStream(partNum.trim(), techDocDirectory);
                        if (listOfFilesWithPartNumber.size() > 0) {
                            File necessaryFileForCopy = FileExplorer.chooseFileForCopy(listOfFilesWithPartNumber);
                            FileExplorer.fileCopy(necessaryFileForCopy, awbDirectory, count);
                        } else {
                            fails.add(count);
                        }
                        count++;
                        progressBar1.setValue((100 / parts.size()) * count);
                        progressBar1.update(progressBar1.getGraphics());
                    }

                    progressBar1.setValue(100);

                    String message = "";
                    message += "Процесс для " + parts.size() + " позиций завершен!\n";
                    if (fails.size() != 0) {
                        message += "Нет документов для позиций: \n";
                        message += fails.toString();
                    }
                    JOptionPane.showMessageDialog(panel, message);
                }
            }
        });
    }

    public static void main(String[] args) {
        new ExplorerForm();
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
        panel = new JPanel();
        panel.setLayout(new GridLayoutManager(8, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel.setBackground(new Color(-12566248));
        panel.setMaximumSize(new Dimension(400, 500));
        panel.setMinimumSize(new Dimension(400, 500));
        panel.setName("FileExplorer");
        panel.setPreferredSize(new Dimension(400, 500));
        panel.setRequestFocusEnabled(true);
        panel.setToolTipText("");
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-4473925)), "FileExplorer", TitledBorder.CENTER, TitledBorder.TOP, this.$$$getFont$$$("Impact", Font.BOLD | Font.ITALIC, 16, panel.getFont()), new Color(-1579552)));
        pathToTechDoc = new JTextField();
        pathToTechDoc.setColumns(0);
        pathToTechDoc.setHorizontalAlignment(10);
        panel.add(pathToTechDoc, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        awbLabel = new JLabel();
        awbLabel.setBackground(new Color(-1579033));
        Font awbLabelFont = this.$$$getFont$$$("Arial", Font.BOLD, 14, awbLabel.getFont());
        if (awbLabelFont != null) awbLabel.setFont(awbLabelFont);
        awbLabel.setForeground(new Color(-1579552));
        awbLabel.setText("Путь до папки накладой:");
        panel.add(awbLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(350, 50), null, 0, false));
        techDocLabel = new JLabel();
        techDocLabel.setBackground(new Color(-2234129));
        Font techDocLabelFont = this.$$$getFont$$$("Arial", Font.BOLD, 14, techDocLabel.getFont());
        if (techDocLabelFont != null) techDocLabel.setFont(techDocLabelFont);
        techDocLabel.setForeground(new Color(-1579552));
        techDocLabel.setHorizontalAlignment(2);
        techDocLabel.setHorizontalTextPosition(2);
        techDocLabel.setText("Путь до папки с технической документацией:");
        techDocLabel.setVerticalAlignment(0);
        panel.add(techDocLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(350, 50), new Dimension(350, 50), new Dimension(350, 50), 0, false));
        pathToAwb = new JTextField();
        panel.add(pathToAwb, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        partsLabel = new JLabel();
        partsLabel.setAutoscrolls(true);
        partsLabel.setBackground(new Color(-1579552));
        Font partsLabelFont = this.$$$getFont$$$("Arial", Font.BOLD, 14, partsLabel.getFont());
        if (partsLabelFont != null) partsLabel.setFont(partsLabelFont);
        partsLabel.setForeground(new Color(-1579552));
        partsLabel.setHorizontalAlignment(10);
        partsLabel.setHorizontalTextPosition(2);
        partsLabel.setText("Список партийных номеров:");
        partsLabel.setVerifyInputWhenFocusTarget(false);
        partsLabel.setVerticalAlignment(0);
        partsLabel.setVerticalTextPosition(0);
        partsLabel.putClientProperty("html.disable", Boolean.FALSE);
        panel.add(partsLabel, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(350, 50), new Dimension(350, 50), new Dimension(350, 50), 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel.add(scrollPane1, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        partNumbers = new JTextArea();
        scrollPane1.setViewportView(partNumbers);
        button1 = new JButton();
        button1.setText("Закинуть документы");
        panel.add(button1, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        progressBar1 = new JProgressBar();
        progressBar1.setStringPainted(true);
        panel.add(progressBar1, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }

}

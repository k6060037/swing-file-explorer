import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
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
                    List<String> parts = new LinkedList<String>(Arrays.asList(str.split("\n+")));
                    List<Integer> fails = new LinkedList<>();


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
                    if (fails.size() != 0 ) {
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
}

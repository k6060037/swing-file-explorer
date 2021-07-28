import org.apache.commons.io.FileUtils;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileExplorer {
    //Получаем список файлов, подходящих для определенного партийного номера
    //с использованием stream кратное преимущество по времени
    public static  List<File> getListOfFilesByStream(String part, File techDoc){
        List<File> listFiles = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(techDoc.getPath()))) {
            paths
                    .filter(path -> path.getFileName().toString().contains(part))
                    .forEach(path -> listFiles.add(path.toFile()));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listFiles;
    }

    //Получаем список файлов, подходящих для определенного партийного номера
//    public static List<File> getListOfFilesWithPartNumber(String part, File techDocDirectory) {
//        IOFileFilter fileFilter = new IOFileFilter() {
//            @Override
//            public boolean accept(File file) {
//                if (file.getName().contains(part)){
//                    return true;
//                }
//                return false;
//            }
//            @Override
//            public boolean accept(File file, String s) {
//                return false;
//            }
//        };
//
//        List<File> listFiles = (List<File>) FileUtils.listFiles(techDocDirectory, fileFilter, TrueFileFilter.INSTANCE);
//        return listFiles;
//    }

    //Из листа подходящих файлов выбираем нужный (самый новый по дате изменения)
    // и возвращаем наиболее подходящий
    public static File chooseFileForCopy(List<File> files) {
        File necessaryFileToCopy = files.get(0);

        for(File file : files) {
            if (file.lastModified() > necessaryFileToCopy.lastModified()) {
                necessaryFileToCopy = file;
            }
        }
        return necessaryFileToCopy;
    }

    //Копируем файл в папку с накладной и прибавляем к имени порядковый номер
    public static void fileCopy(File necessaryFileToCopy, File awbDirectory, int count){
        File finalFile;
        if (count < 10) {
            finalFile = new File(awbDirectory.getPath() + "\\0" + count + ". " + necessaryFileToCopy.getName());
        } else {
            finalFile = new File(awbDirectory.getPath() + "\\" + count + ". " + necessaryFileToCopy.getName());

        }
        try {
            FileUtils.copyFile(necessaryFileToCopy, finalFile, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

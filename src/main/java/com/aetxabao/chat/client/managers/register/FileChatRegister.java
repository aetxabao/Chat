package com.aetxabao.chat.client.managers.register;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static com.aetxabao.chat.client.consts.AppConsts.*;

public class FileChatRegister implements IRegister<String> {
    public void save(String txt) throws IOException {
        Files.createDirectories(Path.of(IO_FOLDER));
        String filePath = getFilePath();
        File file = new File(filePath);
        FileWriter fr = new FileWriter(file, true);
        String timestamp = LocalDateTime.now().toString().replace('T',' ');
        fr.write(timestamp + '\t' + txt + '\n');
        fr.close();
    }

    private String getFilePath() {
        File file = new File(IO_FOLDER);
        String filePath = file.getAbsolutePath();
        filePath += File.separatorChar + IO_FILE_PREFIX + IO_FILE_SEPARATOR;
        LocalDateTime now = LocalDateTime.now();
        filePath += now.toString().substring(0,10).replaceAll("-",IO_FILE_SEPARATOR);
        filePath += IO_FILE_EXTENSION;
        return filePath;
    }

    public static void main(String[] args) throws IOException {
        FileChatRegister fileChatRegister = new FileChatRegister();
        String fileName = fileChatRegister.getFilePath();
        System.out.println(fileName);
        fileChatRegister.save("Bye");
    }

}

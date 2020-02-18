package cn.tegongdete.easyclass.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class StorageService {
    static String dirPath = "/Users/wangyijie/Desktop/easyclass/files/";

    public void store(MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        String filePath = dirPath + fileName;
        File localFile = new File(filePath);
        File imagePath = new File(dirPath);
        if (!imagePath.exists()) {
            imagePath.mkdirs();
        }
        multipartFile.transferTo(localFile);
    }

    public FileInputStream load(String filename) throws Exception{
        File file = new File(dirPath+filename);
        // 穿件输入对象
        FileInputStream fis = new FileInputStream(file);
        return fis;
    }
}
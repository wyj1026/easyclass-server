package cn.tegongdete.easyclass.controller;

import cn.tegongdete.easyclass.model.ResponseMessage;
import cn.tegongdete.easyclass.service.StorageService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;

@Api(tags = "File Management")
@RestController
@RequestMapping("file")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final StorageService storageService;

    @Autowired
    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }


    @GetMapping("/download/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(HttpServletResponse response, @PathVariable String filename) {
        try {
            FileInputStream fis = storageService.load(filename);
//            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                    "attachment; filename=\"" + file.getFilename() + "\"").body(file);

            // 设置相关格式
            response.setContentType("application/force-download");
            // 设置下载后的文件名以及header
            response.addHeader("Content-disposition", "attachment;fileName=" + filename);
            // 创建输出对象
            OutputStream os = response.getOutputStream();
            // 常规操作
            byte[] buf = new byte[1024];
            int len = 0;
            while((len = fis.read(buf)) != -1) {
                os.write(buf, 0, len);
            }
            fis.close();
            return ResponseEntity.ok().build();

        }
        catch (Exception e) {
            logger.error("Save Error", e);
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/upload")
    public ResponseMessage uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            storageService.store(file);
            return ResponseMessage.success();
        }
        catch (Exception e) {
            logger.error("Save Error", e);
            return ResponseMessage.fail();
        }
    }
}
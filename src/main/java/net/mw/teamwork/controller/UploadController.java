/**
 * @Description UploadController
 * @Author W_Messi
 * @CrateTime 2021-03-08 18:31:50
 * 
 */
package net.mw.teamwork.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


/**
 * @Description UploadController接口实现
 * @Author W_Messi
 * @CrateTime 2021-03-08 18:32:32
 */
@Controller
public class UploadController {
    private static final Logger LOGGER = LogManager.getLogger(UploadController.class);

    @Value("${files.path}")
    private String path ;
    
    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("id")String id,@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }

        String fileName = file.getOriginalFilename();
        String filePath = path +"/"+ id;
        File dir = new File(filePath);
        File dest = new File(filePath  + "/" + fileName);
        try {
        	if(!dir.exists()) {
        		dir.mkdirs();
        	}
        	 file.transferTo(dest);
             LOGGER.info("上传成功");
             return "上传成功";
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
        }
        return "上传失败！";
    }
    
    @GetMapping("/multiUpload")
    public String multiUpload() {
        return "multiUpload";
    }

    @PostMapping("/multiUpload")
    @ResponseBody
    public String multiUpload(@RequestParam("id") String id , HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        String filePath = path +"/"+ id ;
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            if (file.isEmpty()) {
                return "上传第" + (i++) + "个文件失败";
            }
            String fileName = file.getOriginalFilename();

            File dest = new File(filePath + fileName);
            try {
                file.transferTo(dest);
                LOGGER.info("第" + (i + 1) + "个文件上传成功");
            } catch (IOException e) {
                LOGGER.error(e.toString(), e);
                return "上传第" + (i++) + "个文件失败";
            }
        }

        return "上传成功";

    }
    
}



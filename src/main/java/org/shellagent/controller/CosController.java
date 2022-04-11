package org.shellagent.controller;

import com.qcloud.cos.model.COSObjectSummary;
import com.qcloud.cos.model.PutObjectResult;
import org.shellagent.utils.Constant;
import org.shellagent.utils.CosFTP;
import org.shellagent.utils.ResponseBuilder;
import org.shellagent.utils.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@RequestMapping("/cos")
@RestController
public class CosController {

    @Autowired
    CosFTP cosFTP;
    @Value("${cos.localpath}")
    String COS_PATH;

    @GetMapping("/getlist")
    public ResponseEntity getCosObjs (String url) {
//        cosFTP.init();
        List<COSObjectSummary> summaries =  cosFTP.getObjectsByUrl(url);
        return new ResponseBuilder().setData(summaries).getResponseEntity();
    }

    @PostMapping("/uploadObj")
    public ResponseEntity uploadObject (@RequestParam("file") MultipartFile file) {
        File tmpfile = new File(COS_PATH + File.separator + file.getOriginalFilename());
        OutputStream outputStream = null;
        try {
            InputStream inputStream = file.getInputStream();
            outputStream = new FileOutputStream(tmpfile);
            byte[] bytes = file.getBytes();
            for (int i = 0; i < bytes.length; i++) {
                outputStream.write(bytes[i]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String fileid = tmpfile.getName();
        return new ResponseBuilder().putItem("fileid", fileid).getResponseEntity();
    }

    @GetMapping("/uploadObjInfo")
    public ResponseEntity uploadObjectInfo (String filepath, String fileid) {
        File file = new File(COS_PATH + fileid);
        PutObjectResult putObjectResult = null;
        if (file.exists() && file.canRead()) {
            putObjectResult = cosFTP.uploadObject(filepath, file);
        }
        return new ResponseBuilder().setData(putObjectResult).getResponseEntity();
    }

}

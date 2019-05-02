package com.zx.bs.controller;


import com.zx.bs.utils.FileUtil;
import com.zx.bs.utils.ImageResult;
import com.zx.bs.utils.ImageResultUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


//@Controller
@RestController
public class FileController {


    @RequestMapping(value="/uploadimg", method = RequestMethod.POST)
    @ResponseBody
    public ImageResult uploadImg(MultipartFile myFileName,
                                 HttpServletRequest request) {
        String contentType = myFileName.getContentType();
        String fileName =System.currentTimeMillis()+ myFileName.getOriginalFilename();

        String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
        //
        //filePath="C:/Download/";
        System.out.println(filePath);
        System.out.println(fileName);
        System.out.println(contentType);



        try {
            FileUtil.uploadFile(myFileName.getBytes(), filePath, fileName);
        } catch (Exception e) {
            // TODO: handle exception
        }
        //返回json
        String [] str = {"/imgupload/" +  fileName};
        return ImageResultUtil.success(str);
    }
//    @RequestMapping(value="/upload", method = RequestMethod.POST)
//    @ResponseBody
//    public String upload(@RequestParam("name") String name,@RequestParam("filename") String fileName,
//                            HttpServletRequest request) {
//
//        String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
//
//        //filePath="C:/Download/";
//        System.out.println(filePath);
//        System.out.println(fileName);
//        System.out.println();
//
//
//        //返回json
//        return "{" +
//
//                "\"errno\": 0," +
//
//
//                "\"data\": [" +
//                "\"" +
//                filePath +
//                "\"," +
//
//                "]" +
//                "}";
//    }
}

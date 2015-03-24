package com.comdosoft.financial.user.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.comdosoft.financial.user.domain.query.CommentReq;
import com.comdosoft.financial.user.mapper.zhangfu.CommentMapper;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    
    @Value("${uploadPictureTempsPath}")
    private String uploadPictureTempsPath;

    public Map<String, Object> getList(CommentReq req) {
        Map<String, Object> map=new HashMap<String, Object>();
        int total=commentMapper.getCommentCount(req.getGoodId());
        List<Map<String, Object>> list=commentMapper.getCommentList(req);
        map.put("total", total);
        map.put("list", list);
        return map;
    }
    
    /**
     * 图片上传
     * 
     * @param img
     * @param request
     * @return
     * @throws IOException
     */
    public String saveTmpImage(MultipartFile img, HttpServletRequest request) throws IOException {
        String fileName = Calendar.getInstance().getTimeInMillis() + ".jpg";
        // String realPath = dirRoot + imgTempPath;
        String realPath = request.getServletContext().getRealPath(uploadPictureTempsPath);
        File dir = new File(realPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        InputStream stream = img.getInputStream();
        // Thumbnails.of(stream).size(480, 480).toFile(realPath + File.separator + fileName);
        stream.close();
        return uploadPictureTempsPath + fileName;
    }


}

package com.comdosoft.financial.user.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
     * 获得所有省份
     * 
     * @return
     * @throws IOException
     */
    public List<Map<String, Object>> findAllCity() {
        List<Map<String, Object>> parent_list = commentMapper.getParentCitiesList();
        List<Map<String, Object>> newlist = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = null;
        for(Map<String,Object> m: parent_list){
            map = new HashMap<String,Object>();
            String id =  m.get("id")==null?"":m.get("id").toString();
            map.put("id", id);
            map.put("name", m.get("name")==null?"":m.get("name"));
            List<Map<String, Object>> children_list = commentMapper.getChildrenCitiesList(id);
            List<Map<String, Object>> new_children_list = new ArrayList<Map<String,Object>>();
            Map<String,Object> cmap = null;
            for(Map<String,Object> c:children_list){
                cmap = new HashMap<String,Object>();
                String cid =  c.get("id")==null?"":c.get("id").toString();
                cmap.put("id", cid);
                cmap.put("name", c.get("name")==null?"":c.get("name"));
                new_children_list.add(cmap);
            }
            map.put("childrens",new_children_list );
            newlist.add(map);
        }
        return newlist;
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

package com.comdosoft.financial.user.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.multipart.MultipartFile;

import com.comdosoft.financial.user.domain.Response;




public class HttpFile {

    private static String localpath = "C:/test/local/";
    
   // private static String urlpath="http://127.0.0.1:8080/file/index/upload";
    
    private static String urlpath="http://121.40.84.2:8888/File/index/upload";

    private static String successurl="http://121.40.84.2:8888/";
    
    public static Response upload(MultipartFile file, String path) {
         String upload_path =localpath+path; 
         String name= file.getOriginalFilename();
         int a=-1;
         try {
             File f=new File(upload_path, name);
             FileUtils.copyInputStreamToFile(file.getInputStream(), f);
             a=postHttp(urlpath, path,f);
         } catch (Exception e) {
             e.printStackTrace();
             return Response.getError("上传失败");
         }
         if(a==-1){
             return Response.getError("同步上传失败");
         }else{
             return Response.getSuccess(successurl+path+name);
         }
         
     }
    
    
    public static int postHttp(String url, String path, File file) throws HttpException, IOException {

        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
        mEntityBuilder.addBinaryBody("file", file);
        mEntityBuilder.addTextBody("path", path);
        httppost.setEntity(mEntityBuilder.build());
        HttpResponse resp=httpClient.execute(httppost);
        int code=resp.getStatusLine().getStatusCode();
        if(200==code){
            return 0;
        }else{
            return -1;
        }
    }
    
    

}

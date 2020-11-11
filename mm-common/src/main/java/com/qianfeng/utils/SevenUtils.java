package com.qianfeng.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.io.UnsupportedEncodingException;

/**
 * 七牛云上传工具类
 */
public class SevenUtils {

    public static String upload(String data,String maId){
        //构造一个带指定 Region 对象的配置类
        //指定上传文件服务器地址
        Configuration cfg = new Configuration(Region.region2()); //空间存储的服务器（华南2，华东0）
        //...其他参数参考类注释
//        上传管理器
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "5sk8c6a1fIM5X0AzlCDOCd48FrYptmVWmfH-v4TN"; //秘钥管理的AK
        String secretKey = "wHEr6PEiTsYvZkdo19wiEfpj9pr_kTdB2iENCtYN"; //秘钥管理的SK
        String bucket = "mmbao"; //存储空间名称

        //我要用文章的id当做Key
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = maId;

        try {
            //讲需要上传的字符串转为二进制上传
            byte[] uploadBytes = data.getBytes("utf-8");
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(uploadBytes, key, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                if(maId != null){
                    //返回url地址,前面需要添加域名（文件管理->外链域名）
                    return "http://"+"qjeuq8ug1.hn-bkt.clouddn.com"+"/"+key;
                }
                else{
                    return "http://"+"qjeuq8ug1.hn-bkt.clouddn.com"+"/"+putRet.hash;
                }
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (UnsupportedEncodingException ex) {
            //ignore
        }
        return "";
    }

    public static String uploadImage(byte[] bytes){
        //构造一个带指定 Region 对象的配置类
        //指定上传文件服务器地址
        Configuration cfg = new Configuration(Region.region2()); //空间存储的服务器（华南2，华东0）
        //...其他参数参考类注释
//        上传管理器
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "5sk8c6a1fIM5X0AzlCDOCd48FrYptmVWmfH-v4TN"; //秘钥管理的AK
        String secretKey = "wHEr6PEiTsYvZkdo19wiEfpj9pr_kTdB2iENCtYN"; //秘钥管理的SK
        String bucket = "mmbao"; //存储空间名称

        //我要用文章的id当做Key
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;

        //讲需要上传的字符串转为二进制上传
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(bytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return "http://"+"qjeuq8ug1.hn-bkt.clouddn.com"+"/"+putRet.hash;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return "";
    }
}

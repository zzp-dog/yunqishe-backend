package com.zx.yunqishe.controller;

import com.google.gson.Gson;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.entity.User;
import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.service.UserService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/bucket")
public class BucketController {
    /** 密钥对 */
    private static String accessKey = "bKoXFKcscx-xuUCVS8iOVssDKyUVNPxk9j_zLkRt";
    private static String secretKey = "9iTQkmkr7dxnkSOk6Ri_EhihR7HMoWXPhzjrCOxb";

    /** 系统相关图像存储桶 */
    private static String systemBucket = "yqs-gift-imgs";
    /** 站内内容相关图像存储桶 */
    private static String contentBucket = "yqs-user-imgs";
    /** 对应systemBucket的cname的域名 */
    private static String domain1 = "http://qny.sharesource.top/";
    /** 对应contentBucket的cname的域名 */
    private static String domain2 = "http://qny1.sharesource.top/";

    @Autowired
    private UserService userService;

    /**
     * 后台非覆盖上传图片，配置图片
     * @param file
     * @return
     */
    @RequestMapping("/b/upload/img")
    @RequiresRoles(value = {"superAdmin"})
    public ResponseData uploadImg(@RequestParam("file") MultipartFile file) {
        String name = file.getOriginalFilename();
        return uploadOneFile(file, name, systemBucket, domain1);
    }

    /**
     * 前台覆盖上传头像
     * @param file
     * @return
     */
    @RequestMapping("/f/upload/avator")
    public ResponseData uploadUserAvator(@RequestParam("file") MultipartFile file, @SessionAttribute("me") User me) {
        String name = "user-avator" + me.getId();
        return uploadOneFile(file,name, contentBucket, domain2);
    }

    /**
     * 前台覆盖上传用户中心背景音乐
     * @param file
     * @return
     */
    @RequestMapping("/f/upload/bgm")
    public ResponseData uploadUserBGM(@RequestParam("file") MultipartFile file, @SessionAttribute("me") User me) {
        String name = "user-avator" + me.getId();
        return uploadOneFile(file,name, contentBucket, domain2);
    }

    /**
     * 前台覆盖上传用户中心背景图像
     * @param file
     * @return
     */
    @RequestMapping("/f/upload/bgp")
    public ResponseData uploadUserBGP(@RequestParam("file") MultipartFile file, @SessionAttribute("me") User me) {
        String name = "user-avator" + me.getId();
        return uploadOneFile(file,name, contentBucket, domain2);
    }

    /**
     * 上传文件
     * @param file
     * @param fileName
     * @return
     */
    private ResponseData uploadOneFile(MultipartFile file, String fileName, String bucket, String domain) {
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize),\"url\":\""+domain+"$(key)\"}");
        MyPutRet myPutRet;
        try {
            myPutRet = qynUpload(file, bucket, fileName, putPolicy);
        } catch (Exception e) {
            return ResponseData.error(ErrorMsg.UPLOAD_ERROR);
        }
        return ResponseData.success().add("url", myPutRet.url);
    }

    /**
     * 七牛上传文件 - 上传至华东区的桶
     * @param file - 要上传的文件
     * @param bucket - 存储桶名称
     * @param fileName - 文件名称
     * @param putPolicy - 自定义返回串
     * @return myPutRet {key, hash}
     */
    private MyPutRet qynUpload(MultipartFile file, String bucket, String fileName, StringMap putPolicy)throws Exception {
        //构造一个带指定 Region 对象的配置类
        // Region.region0() 华东区
        Configuration cfg = new Configuration(Region.region0());
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = this.accessKey;
        String secretKey = this.secretKey;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket, null, 3600, putPolicy);
        //文件名
        String key = fileName;
        InputStream inputStream = file.getInputStream();
        Response response = uploadManager.put(inputStream,key,upToken,null, null);
        //解析上传成功的结果
        MyPutRet putRet = new Gson().fromJson(response.bodyString(), MyPutRet.class);
        return putRet;
    }

    class MyPutRet {
        public String key;
        public String hash;
        public String bucket;
        public long fsize;
        public String url;
    }
}

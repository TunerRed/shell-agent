package org.shellagent.utils;

import com.qcloud.cos.*;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.List;

@Component
public class CosFTP {

    @Value("${cos.secretid}")
    private String SECRETID;
    @Value("${cos.secretkey}")
    private String SECRETKEY;
    @Value("${cos.origin}")
    private String COS_REGION;
    @Value("${cos.bucket.name}")
    private String BUCKET;

    private static COSClient cosClient;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public void init() {
        logger.debug("Cos FTP init");
        if (cosClient == null) {
            logger.info(SECRETID);
            logger.info(SECRETKEY);
            // 1 初始化用户身份信息（secretId, secretKey）。
            // SECRETID和SECRETKEY请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
            COSCredentials cred = new BasicCOSCredentials(SECRETID, SECRETKEY);
            // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
            // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
            Region region = new Region(COS_REGION);
            ClientConfig clientConfig = new ClientConfig(region);
            // 这里建议设置使用 https 协议
            // 从 5.6.54 版本开始，默认使用了 https
            clientConfig.setHttpProtocol(HttpProtocol.https);
            // 3 生成 cos 客户端。
            cosClient = new COSClient(cred, clientConfig);
        }
    }

    public PutObjectResult uploadObject(String url, @NotNull File localFile) {
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        String key;
        if (url !=null) {
            key = (url.endsWith("/")?url:(url+"/")) + localFile.getName();
        } else {
            key = "/" + localFile.getName();
        }
        logger.debug("upload key: " + key);

        PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET, key, localFile);
        return cosClient.putObject(putObjectRequest);
    }

    public List<COSObjectSummary> getObjectsByUrl (String url) {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        // 设置bucket名称
        listObjectsRequest.setBucketName(BUCKET);
        // prefix表示列出的object的key以prefix开始
        listObjectsRequest.setPrefix(url);
        // deliter表示分隔符, 设置为/表示列出当前目录下的object, 设置为空表示列出所有的object
        listObjectsRequest.setDelimiter("/");
        // 设置最大遍历出多少个对象, 一次listobject最大支持1000
        listObjectsRequest.setMaxKeys(1000);
        ObjectListing objectListing = null;
        List<COSObjectSummary> cosObjectSummaries = null;
        do {
            try {
                objectListing = cosClient.listObjects(listObjectsRequest);
            } catch (CosServiceException e) {
                e.printStackTrace();
                return null;
            } catch (CosClientException e) {
                e.printStackTrace();
                return null;
            }
            // common prefix表示表示被delimiter截断的路径, 如delimter设置为/, common prefix则表示所有子目录的路径
            List<String> commonPrefixs = objectListing.getCommonPrefixes();

            // object summary表示所有列出的object列表
            cosObjectSummaries = objectListing.getObjectSummaries();
            for (COSObjectSummary cosObjectSummary : cosObjectSummaries) {
                // 文件的路径key
                String key = cosObjectSummary.getKey();
                // 文件的etag
                String etag = cosObjectSummary.getETag();
                // 文件的长度
                long fileSize = cosObjectSummary.getSize();
                // 文件的存储类型
                String storageClasses = cosObjectSummary.getStorageClass();
            }

            String nextMarker = objectListing.getNextMarker();
            listObjectsRequest.setMarker(nextMarker);
        } while (objectListing.isTruncated());
        return cosObjectSummaries;
    }
}

package com.zsxfa.cloud.core.config.fileConf.operation.download.product;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.DownloadUrl;
import com.qiniu.util.Auth;
import com.qiwenshare.common.util.HttpsUtils;
import com.zsxfa.cloud.core.config.fileConf.config.QiniuyunConfig;
import com.zsxfa.cloud.core.config.fileConf.operation.download.Downloader;
import com.zsxfa.cloud.core.config.fileConf.operation.download.domain.DownloadFile;
import com.zsxfa.cloud.core.config.fileConf.util.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

public class QiniuyunKodoDownloader extends Downloader {

    private QiniuyunConfig qiniuyunConfig;

    public QiniuyunKodoDownloader(){

    }

    public QiniuyunKodoDownloader(QiniuyunConfig qiniuyunConfig) {
        this.qiniuyunConfig = qiniuyunConfig;
    }
    @Override
    public void download(HttpServletResponse httpServletResponse, DownloadFile downloadFile) {


            Auth auth = Auth.create(qiniuyunConfig.getKodo().getAccessKey(), qiniuyunConfig.getKodo().getSecretKey());

            String urlString = auth.privateDownloadUrl(qiniuyunConfig.getKodo().getDomain() + "/" + downloadFile.getFileUrl());

            InputStream inputStream = HttpsUtils.doGet(urlString);
            IOUtils.writeInputStreamToResponse(inputStream, httpServletResponse);



    }

    @Override
    public InputStream getInputStream(DownloadFile downloadFile) {
        InputStream inputStream = null;
        // domain   下载 domain, eg: qiniu.com【必须】
// useHttps 是否使用 https【必须】
// key      下载资源在七牛云存储的 key【必须】
        DownloadUrl url = new DownloadUrl("qiniu.com", true, downloadFile.getFileUrl());
//            url.setAttname(attname) // 配置 attname
//                    .setFop(fop) // 配置 fop
//                    .setStyle(style, styleSeparator, styleParam) // 配置 style
        // 带有效期

        Auth auth = Auth.create(qiniuyunConfig.getKodo().getAccessKey(), qiniuyunConfig.getKodo().getSecretKey());
        long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
        try {
            String urlString = url.buildURL(auth, expireInSeconds);
           inputStream = HttpsUtils.doGet(urlString);
        } catch (QiniuException e) {
            e.printStackTrace();
        }

        return inputStream;
    }

}

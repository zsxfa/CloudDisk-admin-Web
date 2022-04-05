package com.zsxfa.cloud.core.config.fileConf.operation.read.product;

import com.qiniu.util.Auth;
import com.qiwenshare.common.util.HttpsUtils;
import com.zsxfa.cloud.core.config.fileConf.config.QiniuyunConfig;
import com.zsxfa.cloud.core.config.fileConf.exception.ReadException;
import com.zsxfa.cloud.core.config.fileConf.operation.read.Reader;
import com.zsxfa.cloud.core.config.fileConf.operation.read.domain.ReadFile;
import com.zsxfa.cloud.core.config.fileConf.util.ReadFileUtils;
import com.zsxfa.cloud.core.config.fileConf.util.UFOPUtils;

import java.io.IOException;
import java.io.InputStream;

public class QiniuyunKodoReader extends Reader {

    private QiniuyunConfig qiniuyunConfig;

    public QiniuyunKodoReader(){

    }

    public QiniuyunKodoReader(QiniuyunConfig qiniuyunConfig) {
        this.qiniuyunConfig = qiniuyunConfig;
    }

    @Override
    public String read(ReadFile readFile) {
        String fileUrl = readFile.getFileUrl();
        String fileType = UFOPUtils.getFileExtendName(fileUrl);
        try {
            return ReadFileUtils.getContentByInputStream(fileType, getInputStream(readFile.getFileUrl()));
        } catch (IOException e) {
            throw new ReadException("读取文件失败", e);
        }
    }

    public InputStream getInputStream(String fileUrl) {
        Auth auth = Auth.create(qiniuyunConfig.getKodo().getAccessKey(), qiniuyunConfig.getKodo().getSecretKey());

        String urlString = auth.privateDownloadUrl(qiniuyunConfig.getKodo().getDomain() + "/" + fileUrl);



        return HttpsUtils.doGet(urlString);
    }


}

package com.zsxfa.cloud.core.config.fileConf.operation.read.product;

import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.zsxfa.cloud.core.config.fileConf.exception.ReadException;
import com.zsxfa.cloud.core.config.fileConf.operation.read.Reader;
import com.zsxfa.cloud.core.config.fileConf.operation.read.domain.ReadFile;
import com.zsxfa.cloud.core.config.fileConf.util.ReadFileUtils;
import com.zsxfa.cloud.core.config.fileConf.util.UFOPUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
//@Component
public class FastDFSReader extends Reader {
    @Autowired
    private FastFileStorageClient fastFileStorageClient;
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
        String group = fileUrl.substring(0, fileUrl.indexOf("/"));
        group = "group1";
        String path = fileUrl.substring(fileUrl.indexOf("/") + 1);
        DownloadByteArray downloadByteArray = new DownloadByteArray();
        byte[] bytes = fastFileStorageClient.downloadFile(group, path, downloadByteArray);
        InputStream inputStream = new ByteArrayInputStream(bytes);
        return inputStream;
    }
}

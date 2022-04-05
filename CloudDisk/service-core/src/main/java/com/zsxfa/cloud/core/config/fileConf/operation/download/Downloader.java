package com.zsxfa.cloud.core.config.fileConf.operation.download;

import com.zsxfa.cloud.core.config.fileConf.operation.download.domain.DownloadFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

public abstract class Downloader {
    public abstract void download(HttpServletResponse httpServletResponse, DownloadFile uploadFile);
    public abstract InputStream getInputStream(DownloadFile downloadFile);
}

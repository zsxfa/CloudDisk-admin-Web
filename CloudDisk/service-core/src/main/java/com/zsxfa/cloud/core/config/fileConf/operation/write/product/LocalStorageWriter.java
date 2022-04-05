package com.zsxfa.cloud.core.config.fileConf.operation.write.product;

import com.zsxfa.cloud.core.config.fileConf.exception.WriteException;
import com.zsxfa.cloud.core.config.fileConf.operation.write.Writer;
import com.zsxfa.cloud.core.config.fileConf.operation.write.domain.WriteFile;
import com.zsxfa.cloud.core.config.fileConf.util.UFOPUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class LocalStorageWriter extends Writer {
    @Override
    public void write(InputStream inputStream, WriteFile writeFile) {
        try (FileOutputStream out = new FileOutputStream(UFOPUtils.getStaticPath() + writeFile.getFileUrl())){
            int read;
            final byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
        } catch (FileNotFoundException e) {
            throw new WriteException("待写入的文件不存在:{}", e);
        } catch (IOException e) {
            throw new WriteException("IO异常:{}", e);
        }
    }
}

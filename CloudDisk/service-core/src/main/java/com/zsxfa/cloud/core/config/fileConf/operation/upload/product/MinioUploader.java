package com.zsxfa.cloud.core.config.fileConf.operation.upload.product;

import com.zsxfa.cloud.core.config.fileConf.config.MinioConfig;
import com.zsxfa.cloud.core.config.fileConf.constant.StorageTypeEnum;
import com.zsxfa.cloud.core.config.fileConf.constant.UploadFileStatusEnum;
import com.zsxfa.cloud.core.config.fileConf.exception.UploadException;
import com.zsxfa.cloud.core.config.fileConf.operation.upload.Uploader;
import com.zsxfa.cloud.core.config.fileConf.operation.upload.domain.UploadFile;
import com.zsxfa.cloud.core.config.fileConf.operation.upload.domain.UploadFileResult;
import com.zsxfa.cloud.core.config.fileConf.operation.upload.request.QiwenMultipartFile;
import com.zsxfa.cloud.core.config.fileConf.util.RedisUtil;
import com.zsxfa.cloud.core.config.fileConf.util.UFOPUtils;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class MinioUploader extends Uploader {

    private MinioConfig minioConfig;

    @Resource
    RedisUtil redisUtil;

    public MinioUploader(){

    }

    public MinioUploader(MinioConfig minioConfig){
        this.minioConfig = minioConfig;
    }

    @Override
    public void cancelUpload(UploadFile uploadFile) {

    }

    @Override
    protected void doUploadFileChunk(QiwenMultipartFile qiwenMultipartFile, UploadFile uploadFile) throws IOException {

    }

    @Override
    protected UploadFileResult organizationalResults(QiwenMultipartFile qiwenMultipartFile, UploadFile uploadFile) {
        return null;
    }

    @Override
    protected UploadFileResult doUploadFlow(QiwenMultipartFile qiwenMultipartFile, UploadFile uploadFile) {
        UploadFileResult uploadFileResult = new UploadFileResult();
        try {
            qiwenMultipartFile.getFileUrl(uploadFile.getIdentifier());
            String fileUrl = UFOPUtils.getUploadFileUrl(uploadFile.getIdentifier(), qiwenMultipartFile.getExtendName());

            File tempFile =  UFOPUtils.getTempFile(fileUrl);
            File processFile = UFOPUtils.getProcessFile(fileUrl);

            byte[] fileData = qiwenMultipartFile.getUploadBytes();

            writeByteDataToFile(fileData, tempFile, uploadFile);

            //判断是否完成文件的传输并进行校验与重命名
            boolean isComplete = checkUploadStatus(uploadFile, processFile);
            uploadFileResult.setFileUrl(fileUrl);
            uploadFileResult.setFileName(qiwenMultipartFile.getFileName());
            uploadFileResult.setExtendName(qiwenMultipartFile.getExtendName());
            uploadFileResult.setFileSize(uploadFile.getTotalSize());
            uploadFileResult.setStorageType(StorageTypeEnum.MINIO);

            if (uploadFile.getTotalChunks() == 1) {
                uploadFileResult.setFileSize(qiwenMultipartFile.getSize());
            }

            if (isComplete) {

                minioUpload(fileUrl, tempFile, uploadFile);
                uploadFileResult.setFileUrl(fileUrl);
                tempFile.delete();
                uploadFileResult.setStatus(UploadFileStatusEnum.SUCCESS);
            } else {
                uploadFileResult.setStatus(UploadFileStatusEnum.UNCOMPLATE);
            }
        } catch (IOException e) {
            throw new UploadException(e);
        }


        return uploadFileResult;
    }


    private void minioUpload(String fileUrl, File file,  UploadFile uploadFile) {
        // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
        MinioClient minioClient = null;
        try {
            minioClient = new MinioClient(minioConfig.getEndpoint(), minioConfig.getAccessKey(), minioConfig.getSecretKey());
            // 检查存储桶是否已经存在
            boolean isExist = minioClient.bucketExists(minioConfig.getBucketName());
            if(!isExist) {
                minioClient.makeBucket(minioConfig.getBucketName());
            }
            PutObjectOptions putObjectOptions = new PutObjectOptions(uploadFile.getTotalSize(), 1024 * 1024 * 5);
            InputStream inputStream = new FileInputStream(file);
            // 使用putObject上传一个文件到存储桶中。
            minioClient.putObject(minioConfig.getBucketName(), fileUrl, inputStream, putObjectOptions);

        } catch (InvalidEndpointException e) {
            e.printStackTrace();
        } catch (InvalidPortException e) {
            e.printStackTrace();
        } catch (RegionConflictException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        }

    }


}

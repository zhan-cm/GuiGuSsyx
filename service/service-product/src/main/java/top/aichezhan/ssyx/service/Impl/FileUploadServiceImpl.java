package top.aichezhan.ssyx.service.Impl;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.aichezhan.ssyx.service.FileUploadService;

import java.io.InputStream;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${minio.endpoint}")
    private String endpoint;
    
    @Value("${minio.accessKey}")
    private String accessKey;
    
    @Value("${minio.secretKey}")
    private String secretKey;
    
    @Value("${minio.bucketName}")
    private String bucketName;

    @Override
    public String fileUpload(MultipartFile file) throws Exception {
        try {
            // 1. 创建 MinioClient
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(accessKey, secretKey)
                    .build();

            // 2. 判断桶是否存在（理论上你刚才建了就肯定存在，这里加一层保险）
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            // 3. 准备文件流和文件名
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            
            // 4. UUID防重名 + 日期文件夹分类
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String timeUrl = new DateTime().toString("yyyy/MM/dd");
            String fileName = timeUrl + "/" + uuid + originalFilename;

            // 5. 执行上传
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType()) 
                            .build()
            );

            // 6. 返回图片访问 URL 
            // 格式类似：http://8.138.246.216:9000/ssyx-bucket/2026/04/07/xxx.jpg
            return endpoint + "/" + bucketName + "/" + fileName;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
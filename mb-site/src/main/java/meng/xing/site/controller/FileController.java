package meng.xing.site.controller;


import meng.xing.site.util.MyResourcePath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/file")
public class FileController {
    private final static Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    @PostMapping(value = "/upload/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<String> requestBodyFlux(@RequestPart("file") FilePart filePart, ServerHttpResponse response) throws IOException {
        LOGGER.info("上传文件开始：{}", filePart.filename());
        Path tempFile = Files.createTempFile("temp", filePart.filename());
        LOGGER.info("上传文件临时路径：{}", tempFile);

        return filePart.transferTo(tempFile.toFile())
                .doOnSuccess(aVoid -> LOGGER.info("上传文件完成：{}", filePart.filename()))
                .doOnError(e -> {
                    LOGGER.warn("上传文件失败：{}", e);
                    response.setStatusCode(HttpStatus.BAD_GATEWAY);
                }).then(Mono.just(filePart.filename()));
    }


    @GetMapping("/download/{filename}/")
    public Mono<Void> downloadByFile(@PathVariable String filename, ServerHttpResponse response) {

        ClassPathResource resource = new ClassPathResource(MyResourcePath.imgPath(filename));
        LOGGER.info("下载文件：{}", resource.getPath());
        File file;
        try {
            file = resource.getFile();
        } catch (IOException e) {
            LOGGER.warn("文件不存在：{}", resource.getPath());
            response.setStatusCode(HttpStatus.NOT_FOUND);
            return Mono.empty();
        }
        ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) response;

        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
        response.getHeaders().setContentType(MediaType.IMAGE_JPEG);

        return zeroCopyResponse.writeWith(file, 0, file.length());
    }
}

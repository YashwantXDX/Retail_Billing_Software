package in.yashwant.billingsoftware.service.impl;

import in.yashwant.billingsoftware.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    @Override
    public String uploadFile(MultipartFile file) {

        String filenameExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        String key = UUID.randomUUID().toString()+"."+filenameExtension;

        // This is AWS part - IF THIS CLASS IS NOT USED THEN DELETE IT
        return "";

    }

    @Override
    public boolean deleteFile(String imgUrl) {
        return false;
    }
}

package rootstudio.development.tms.features.file.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rootstudio.development.tms.features.file.domain.response.FileResponse;
import rootstudio.development.tms.features.file.service.FileService;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    @Override
    public FileResponse uploadFile(MultipartFile file) {
        return FileResponse.builder()
                .url("https://t4.ftcdn.net/jpg/01/43/23/83/360_F_143238306_lh0ap42wgot36y44WybfQpvsJB5A1CHc.jpg")
                .build();
    }

}
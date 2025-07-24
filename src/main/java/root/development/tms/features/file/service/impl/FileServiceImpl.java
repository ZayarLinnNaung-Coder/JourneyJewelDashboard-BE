package root.development.tms.features.file.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import root.development.tms.features.file.domain.response.FileResponse;
import root.development.tms.features.file.service.FileService;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    @Override
    public FileResponse uploadFile(MultipartFile file) {
        return FileResponse.builder()
                .url("https://media.cntraveler.com/photos/6539d1998ab4257d24ee47e4/16:9/w_2580,c_limit/Lenc%CC%A7o%CC%81is-Maranhenses-National-Park-marcreation-M0wxmEHpBtE-unsplash.jpg?mbid=social_retweet")
                .build();
    }

}
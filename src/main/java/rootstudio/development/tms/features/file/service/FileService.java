package rootstudio.development.tms.features.file.service;

import org.springframework.web.multipart.MultipartFile;
import rootstudio.development.tms.features.file.domain.response.FileResponse;

public interface FileService {

    FileResponse uploadFile(MultipartFile file);

}

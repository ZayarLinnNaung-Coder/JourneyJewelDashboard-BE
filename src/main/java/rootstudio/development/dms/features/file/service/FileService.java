package rootstudio.development.dms.features.file.service;

import org.springframework.web.multipart.MultipartFile;
import rootstudio.development.dms.features.file.domain.response.FileResponse;

public interface FileService {

    FileResponse uploadFile(MultipartFile file);

}

package root.development.tms.features.file.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import root.development.tms.features.file.domain.response.FileResponse;
import root.development.tms.features.file.service.FileService;
import root.development.tms.global.BaseController;
import root.development.tms.global.constants.SuccessCodeConstants;
import static root.development.tms.global.constants.SuccessCodeConstants.*;
import root.development.tms.global.domain.CustomResponse;
import root.development.tms.global.utils.MessageBundle;

@RestController
@AllArgsConstructor
@RequestMapping("/api/files")
public class FileController extends BaseController {

    private final FileService fileService;
    private final MessageBundle messageBundle;

    @PostMapping("/upload")
    private ResponseEntity<CustomResponse<FileResponse>> uploadFile(
            @RequestParam("file") MultipartFile file
    ){
        return createResponse(HttpStatus.OK, fileService.uploadFile(file), messageBundle.getMessage(SuccessCodeConstants.SUC_JWT003), SUC_JWT003);
    }

}

package rootstudio.development.dms.features.menu.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rootstudio.development.dms.features.menu.service.MenuService;
import rootstudio.development.dms.global.BaseController;
import rootstudio.development.dms.global.constants.SuccessCodeConstants;
import static rootstudio.development.dms.global.constants.SuccessCodeConstants.*;
import rootstudio.development.dms.global.document.Menu;
import rootstudio.development.dms.global.domain.CustomResponse;
import rootstudio.development.dms.global.utils.MessageBundle;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/menu")
public class MenuController extends BaseController {

    private final MenuService menuService;
    private final MessageBundle messageBundle;

    @GetMapping
    private ResponseEntity<CustomResponse<List<Menu>>> getAllMenu(){
        return createResponse(HttpStatus.OK, menuService.getAllMenu(), messageBundle.getMessage(SuccessCodeConstants.SUC_COM001), SUC_COM001);
    }

}

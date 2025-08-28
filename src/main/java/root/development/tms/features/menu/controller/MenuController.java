package root.development.tms.features.menu.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import root.development.tms.features.menu.service.MenuService;
import root.development.tms.global.BaseController;
import root.development.tms.global.constants.SuccessCodeConstants;
import root.development.tms.global.document.Menu;
import root.development.tms.global.domain.CustomResponse;
import root.development.tms.global.utils.MessageBundle;

import java.util.List;

import static root.development.tms.global.constants.SuccessCodeConstants.*;

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

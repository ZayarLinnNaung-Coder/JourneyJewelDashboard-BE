package rootstudio.development.tms.features.menu.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rootstudio.development.tms.features.menu.service.MenuService;
import rootstudio.development.tms.global.document.Menu;
import rootstudio.development.tms.global.repo.MenuRepo;

import java.util.List;

@Service
@AllArgsConstructor
public class MenuServiceImpl implements MenuService {

    private MenuRepo menuRepo;

    @Override
    public List<Menu> getAllMenu() {
        return menuRepo.findByParentMenuIdIsNull();
    }
}

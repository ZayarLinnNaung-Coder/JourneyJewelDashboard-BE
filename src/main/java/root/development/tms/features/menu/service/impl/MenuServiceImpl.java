package root.development.tms.features.menu.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import root.development.tms.features.menu.service.MenuService;
import root.development.tms.global.document.Menu;
import root.development.tms.global.repo.MenuRepo;

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

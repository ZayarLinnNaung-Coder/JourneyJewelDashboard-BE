package rootstudio.development.dms.global.utils;

import rootstudio.development.dms.global.document.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuUtils {

    public static List<Menu> collectAllSubMenus(Menu menu) {
        List<Menu> allSubMenus = new ArrayList<>();
        collectSubMenusRecursive(menu, allSubMenus);
        return allSubMenus;
    }

    private static void collectSubMenusRecursive(Menu menu, List<Menu> allSubMenus) {
        if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
            for (Menu child : menu.getChildren()) {
                allSubMenus.add(child); // Add the child menu to the list
                collectSubMenusRecursive(child, allSubMenus); // Recursively collect submenus
            }
        }
    }

}

package com.example.android_library_app;

import android.content.Context;
import android.view.Menu;

public class UserRoleBasedNavigation {

    private static Menu nav_menu;

    // set navigation
    public void setNavigation(Context context, String role, String userId) {

        nav_menu = AdminScreen.nav_menu;

        if (role.equalsIgnoreCase("admin")) {

            setNavigationForAdmin(context, userId);

        } else if (role.equalsIgnoreCase("departmentAdmin")) {

            setNavigationForDepartmentAdmin(context, userId);

        } else if (role.equalsIgnoreCase("user")) {

            setNavigationForUser(context, userId);

        } else {

            System.out.println("User found with different role");
            nav_menu.findItem(R.id.nav_login).setVisible(true);
            nav_menu.findItem(R.id.nav_register).setVisible(true);
            nav_menu.findItem(R.id.nav_logout).setVisible(false);
            nav_menu.findItem(R.id.nav_add_dept_admin).setVisible(false);
            nav_menu.findItem(R.id.nav_list_all_dept_admin).setVisible(false);
            nav_menu.findItem(R.id.nav_add_book).setVisible(false);
            nav_menu.findItem(R.id.nav_setting).setVisible(false);
        }
    }

    // navigation bar for setting the admin user
    public void setNavigationForAdmin(Context context, String userId) {
        nav_menu.findItem(R.id.nav_add_dept_admin).setVisible(true);
        nav_menu.findItem(R.id.nav_list_all_dept_admin).setVisible(true);
        nav_menu.findItem(R.id.nav_add_book).setVisible(true);
        nav_menu.findItem(R.id.nav_login).setVisible(false);
        nav_menu.findItem(R.id.nav_register).setVisible(false);
        nav_menu.findItem(R.id.nav_logout).setVisible(true);
        nav_menu.findItem(R.id.nav_setting).setVisible(true);
    }

    // navigation bar for setting the departmentAdmin user
    public void setNavigationForDepartmentAdmin(Context context, String userId) {
        nav_menu.findItem(R.id.nav_add_dept_admin).setVisible(false);
        nav_menu.findItem(R.id.nav_list_all_dept_admin).setVisible(false);
        nav_menu.findItem(R.id.nav_add_book).setVisible(true);
        nav_menu.findItem(R.id.nav_login).setVisible(false);
        nav_menu.findItem(R.id.nav_register).setVisible(false);
        nav_menu.findItem(R.id.nav_logout).setVisible(true);
        nav_menu.findItem(R.id.nav_setting).setVisible(true);
    }

    // navigation bar for setting the user
    public void setNavigationForUser(Context context, String userId) {
        nav_menu.findItem(R.id.nav_add_dept_admin).setVisible(false);
        nav_menu.findItem(R.id.nav_list_all_dept_admin).setVisible(false);
        nav_menu.findItem(R.id.nav_add_book).setVisible(false);
        nav_menu.findItem(R.id.nav_login).setVisible(false);
        nav_menu.findItem(R.id.nav_register).setVisible(false);
        nav_menu.findItem(R.id.nav_logout).setVisible(true);
        nav_menu.findItem(R.id.nav_setting).setVisible(true);
    }
}

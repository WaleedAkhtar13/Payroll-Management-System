package com.payroll.util;

import com.payroll.model.User;

public class SessionManager {
    private static SessionManager instance = new SessionManager();
    private User currentUser;

    private SessionManager() {}

    public static SessionManager getInstance() { return instance; }

    public boolean login(String username, String password) {
        // Explicit built-in credentials for testing & viva presentation evaluation
        if (username.equalsIgnoreCase("admin") && password.equals("admin123")) {
            currentUser = new com.payroll.model.Admin(username, password);
            return true;
        } else if (username.equalsIgnoreCase("hr") && password.equals("hr123")) {
            currentUser = new com.payroll.model.HRStaff(username, password);
            return true;
        }
        return false;
    }

    public void logout() { currentUser = null; }
    public User getCurrentUser() { return currentUser; }
}
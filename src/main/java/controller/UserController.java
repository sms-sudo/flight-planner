package controller;

import usecases.LoginHandler;

import java.util.Date;

public class UserController {
    private final LoginHandler loginHandler;

    public UserController(LoginHandler loginHandler) {
        this.loginHandler = loginHandler;
    }

    public void createAccount(String username, String password, String email, String phoneNumber) {
        loginHandler.createAccount(username, password, email, phoneNumber);
    }

    public boolean login(String username, String password) {
        try {
            return loginHandler.loginAttempt(username, password);
        }
        catch (NullPointerException e){
            return false;
        }
    }

    public void logout() { loginHandler.logout(); }

    public void deleteAccount(String username) { loginHandler.deleteAccount(username); }

    /**
     * Serializes and deserializes userList. Call this at the end of changing ViewProfile.
     */
    public void saveSettings() { loginHandler.saveSettings(); }

    // methods below are getters and setters for various user information
    public String getUsername() {
        return loginHandler.currentUser.getUsername();
    }

    public String getPassword() {
        return loginHandler.currentUser.getPassword();
    }

    public void setPassword(String pw) {
        loginHandler.currentUser.setPassword(pw);
    }

    public String getEmail() { return loginHandler.currentUser.getEmail(); }

    public void setEmail(String e) { loginHandler.currentUser.setEmail(e); }

    public String getPhoneNumber() { return loginHandler.currentUser.getPhoneNumber(); }

    public void setPhoneNumber(String pn) { loginHandler.currentUser.setPhoneNumber(pn); }

    public int getAppRating() { return loginHandler.currentUser.getAppRating(); }

    public void setAppRating(int appRating) { loginHandler.currentUser.setAppRating(appRating); }

    public StringBuilder getRouteHistory() { return loginHandler.currentUser.getRouteHistory(); }

    public String upgradeUserType() { return loginHandler.currentUser.upgradeUserType(); }

    public String downgradeUserType() { return loginHandler.currentUser.downgradeUserType(); }

    public String getUserType() { return loginHandler.currentUser.getUserType(); }

    public String getClassType() { return loginHandler.currentUser.getClassType(); }

    public String setClassType(String classType) { return loginHandler.currentUser.setClassType(classType); }

    public Date getRenewalDate() { return loginHandler.currentUser.getRenewalDate(); }

    public String setRenewalDate(Date date) { return loginHandler.currentUser.setRenewalDate(date); }

    public String getColorScheme() { return loginHandler.currentUser.getColorScheme(); }

    public String setColorScheme(String colorScheme) { return loginHandler.currentUser.setColorScheme(colorScheme); }

    public StringBuilder getFavouriteAirports() { return loginHandler.currentUser.getFavouriteAirports(); }

    // TODO: add fav airport, remove fav airport

    public int getAutoLogoutTimer() { return loginHandler.currentUser.getAutoLogoutTimer(); }

    public String setAutoLogoutTimer(int autoLogoutTimer) { return loginHandler.currentUser.setAutoLogoutTimer(autoLogoutTimer); }

    public StringBuilder getHomeAirport() { return loginHandler.currentUser.getHomeAirport(); }

    // TODO: set home airport
}
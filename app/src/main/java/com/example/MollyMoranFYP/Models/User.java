package com.example.MollyMoranFYP.Models;

public class User {

        private String userID;
        private String name;
        private String userType;
        private String profilePic;



        public User() {
        }

    public User(String name, String userType, String userID) {
        this.name = name;
        this.userType = userType;
        this.userID = userID;
    }

    public User(String name, String userType, String userID, String profilePic) {
        this.name = name;
        this.userType = userType;
        this.userID = userID;
        this.profilePic = profilePic;
    }


        public User(String name, String userType) {
            this.name = name;
            this.userType = userType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }


}

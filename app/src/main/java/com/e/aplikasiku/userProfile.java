package com.e.aplikasiku;

public class userProfile {
    public String username;
    public String useremail;
    public String userhp;

    public userProfile() {

    }

    public userProfile(String username, String useremail, String userhp) {
        this.username = username;
        this.useremail = useremail;
        this.userhp = userhp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUserhp() {
        return userhp;
    }

    public void setUserhp(String userhp) {
        this.userhp = userhp;
    }
}

package lk.icbt.fyp.helpYouNeed.models;

 public  class User {

     public String uid;
     public String firstName;
     public String lastName;
     public String university;
     public String dob;
     public String gender;
     private String bfNum;
     public String image;
     public String status;
     public String thumb_image;
     public String device_token;

     public User(){};



     public  User(String firstName, String lastName, String university, String dob, String gender, String bfNum, String image, String status, String thumb_image, String device_token){
        this.firstName = firstName;
        this.lastName = lastName;
        this.university = university;
        this.dob = dob;
        this.gender = gender;
        this.bfNum = bfNum;
        this.image = image;
        this.status = status;
        this.thumb_image = thumb_image;
        this.device_token = device_token;
    }


    public  String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {return device_token;}

    public String getBfNum() {
         return bfNum;
     }

    public String getDob() {
        return dob;
    }

    public String getFirstName() {
        return firstName;
    }


    public String getGender() {
        return gender;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUniversity() {
        return university;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getDisplayName(){
        return firstName+" "+lastName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image)
    {
        this.thumb_image = thumb_image;
    }
}

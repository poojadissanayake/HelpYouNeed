package lk.icbt.fyp.helpYouNeed.Chat;

public class Users {
    public String displayName;
    public String image;
    public String status;
    public String thumb_image;

    public Users()
    {

    }

    public Users(String displayName, String image, String status) {
        this.displayName = displayName;
        this.image = image;
        this.status = status;
        this.thumb_image = thumb_image;
    }

    public String getName() {
        return displayName;
    }

    public void setName(String displayName) {
        this.displayName = displayName;
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

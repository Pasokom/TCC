package db.pojo;

public class NotificationDB {

    private String title;
    private int user_cod;
    private int project_cod;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUser_cod() {
        return user_cod;
    }

    public void setUser_cod(int user_cod) {
        this.user_cod = user_cod;
    }

    public int getProject_cod() {
        return project_cod;
    }

    public void setProject_cod(int project_cod) {
        this.project_cod = project_cod;
    }
}
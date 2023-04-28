package me.reidj.takiwadai.scene.log;

public class Log {

    private final int id;
    private final String creator;
    private final String dateCreation;
    private final String category;
    private final String description;
    private final String status;

    public Log(int id, String creator, String dateCreation, String category, String description, String status) {
        this.id = id;
        this.creator = creator;
        this.dateCreation = dateCreation;
        this.category = category;
        this.description = description;
        this.status = status;
    }

    public Log(String dateCreation, String category, String description, String status) {
        this(0, null, dateCreation, category, description, status);
    }

    public int getId() {
        return id;
    }

    public String getCreator() {
        return creator;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }
}

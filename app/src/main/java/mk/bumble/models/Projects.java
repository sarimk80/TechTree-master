package mk.bumble.models;

public class Projects {
    private String code;
    private String functionality;
    private String howToBuild;
    private String imageUrl;
    private String introduction;
    private String projectName;
    private String thingsYouNeed;
    private int id;
    private String projectImageUrl;
    private String youtubeUrl;

    public Projects() {
    }

    public Projects(String code, String functionality, String howToBuild, String imageUrl, String introduction, String projectName, String thingsYouNeed
    ,String projectImageUrl,int id,String youtubeUrl) {
        this.code = code;
        this.functionality = functionality;
        this.howToBuild = howToBuild;
        this.imageUrl = imageUrl;
        this.introduction = introduction;
        this.projectName = projectName;
        this.thingsYouNeed = thingsYouNeed;
        this.id=id;
        this.projectImageUrl=projectImageUrl;
        this.youtubeUrl=youtubeUrl;
    }

    public int getId() {
        return id;
    }

    public String getProjectImageUrl() {
        return projectImageUrl;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public String getCode() {
        return code;
    }

    public String getFunctionality() {
        return functionality;
    }

    public String getHowToBuild() {
        return howToBuild;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getThingsYouNeed() {
        return thingsYouNeed;
    }
}

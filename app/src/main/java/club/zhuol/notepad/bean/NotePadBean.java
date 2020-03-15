package club.zhuol.notepad.bean;

public class NotePadBean {
    private String id;  //记录的id
    private String notpadContent;   //记录的内容
    private String notpadTime;    //保存记录的时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotpadContent() {
        return notpadContent;
    }

    public void setNotpadContent(String notpadContent) {
        this.notpadContent = notpadContent;
    }

    public String getNotpadTime() {
        return notpadTime;
    }

    public void setNotpadTime(String notpadTime) {
        this.notpadTime = notpadTime;
    }
}

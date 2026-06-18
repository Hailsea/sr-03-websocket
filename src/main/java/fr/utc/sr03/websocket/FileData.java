package fr.utc.sr03.websocket;

public class FileData {
    private String name;
    private String type;
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return String.format("{\"name\":\"%s\", \"type\":\"%s\",\"data\":\"%s\"}",name, type,data);
    }
}
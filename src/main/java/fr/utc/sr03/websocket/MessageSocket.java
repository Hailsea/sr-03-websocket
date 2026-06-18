package fr.utc.sr03.websocket;

import fr.utc.sr03.model.User;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class MessageSocket {

    private Integer channel_id;
    private Integer user_id;
    private String message;
    private ArrayList<FileData> files;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(Integer channel_id) {
        this.channel_id = channel_id;
    }

    public ArrayList<FileData> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<FileData> files) {
        this.files = files;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    //un toString pour les files
    public String getFilesString(){
        System.out.println("getfilesstring");
        String acc="[";
        for (FileData file:files){
            System.out.println(acc);
            acc+=file.toString()+",";
        }
        if (acc.charAt(acc.length()-1)==','){
            acc=acc.substring(0,acc.length()-1);
        }
        acc+="]";
        System.out.println(acc);
        System.out.println("getfilesstring");
        return acc;
    }
}

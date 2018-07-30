package com.pawn.pawnstudydemo;

public class ChatContent {

    public final static int TYPE_TEXT = 0;
    public final static int TYPE_IMG = 1;
    public final static int TYPE_KNOW = 2;
    public final static int TYPE_PLUGIN = 3;

    /**
     * content : {"chatContent":"32456743543"}
     * messageId :
     * pk : 8a7de68e648d979101648d9c42a20003
     * role : visitor
     * roomId :
     * sender : 访客1625
     * time : 1531355386000
     * type : word
     */

    private String content;
    private String messageId;
    private String pk;
    private String role;
    private String roomId;
    private String sender;
    private String time;
    private String type;

    private int typeKey;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        if ("image".equals(type)) {
            typeKey = TYPE_IMG;
        } else if ("word".equals(type)) {
            typeKey = TYPE_TEXT;
        } else if ("plugin".equals(type)) {
            typeKey = TYPE_KNOW;
        } else if ("imageText".equals(type)) {
            typeKey = TYPE_PLUGIN;
        }
    }

    public int getTypeKey() {
        return typeKey;
    }
}
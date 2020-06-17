package com.spellingtrip.example.bean;

/**
 * date:2020/5/11
 * author:王思敏
 * function
 */
public class LabelsEvent {
    private String typeId;
    private String typeName;

    public LabelsEvent(String id, String name) {
        this.typeId = id;
        this.typeName = name;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}

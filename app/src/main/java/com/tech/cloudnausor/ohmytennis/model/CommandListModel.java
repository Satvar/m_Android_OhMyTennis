package com.tech.cloudnausor.ohmytennis.model;

public class CommandListModel {
    String name;
    String qualification;
    String rating;
    String command;

    public CommandListModel() {
    }

    public CommandListModel(String name, String qualification, String rating, String command) {
        this.name = name;
        this.qualification = qualification;
        this.rating = rating;
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}

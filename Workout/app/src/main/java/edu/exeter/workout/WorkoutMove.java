package edu.exeter.workout;

import java.io.Serializable;

public class WorkoutMove implements Serializable {

    private String name;
    private String description;
    private String image;
    private String diagram;

    public WorkoutMove( String moveName, String des, String img, String muscleDiagram){
        name = moveName;
        description = des;
        image = img;
        diagram = muscleDiagram;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDiagram() {
        return diagram;
    }

    public void setDiagram(String diagram) {
        this.diagram = diagram;
    }
}

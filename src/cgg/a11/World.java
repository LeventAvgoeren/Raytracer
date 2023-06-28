package cgg.a11;

import java.util.ArrayList;

public class World {
    private ArrayList<Light> lightSources;
    private Group sceneSource;

    public World(Group scene) {
        sceneSource = scene;
        lightSources = new ArrayList<Light>();
    }
    
    public World(Group scene, ArrayList<Light> lights) {
        sceneSource = scene;
        lightSources = lights;
    }

    public void addLightSource(Light light) {
        lightSources.add(light);
    }

    public Group getScene() {
        return sceneSource;
    }

    public ArrayList<Light> getLightSources() {
        return lightSources;
    }
}

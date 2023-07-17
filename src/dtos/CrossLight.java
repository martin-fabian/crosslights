package dtos;

import enums.CrossLightEnum;
import enums.LightColourEnum;

public class CrossLight {
   CrossLightEnum name;
    LightColourEnum lightColour;

    public CrossLight(CrossLightEnum name, LightColourEnum lightColour) {
        this.name = name;
        this.lightColour = lightColour;
    }

    public CrossLightEnum getName() {
        return name;
    }

    public void setName(CrossLightEnum name) {
        this.name = name;
    }

    public LightColourEnum getLightColour() {
        return lightColour;
    }

    public void setLightColour(LightColourEnum lightColour) {
        this.lightColour = lightColour;
    }
}

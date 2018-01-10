package com.zgodnji.fifastatstracker;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("rest-config")
public class ShowLeaderboardProperties {

    @ConfigValue(watch = true)
    private String stringProperty;
    private Boolean booleanProperty;
    private Integer integerProperty;

    public String getStringProperty() {
        return stringProperty;
    }

    public Boolean getBooleanProperty() {
        return booleanProperty;
    }

    public Integer getIntegerProperty() {
        return integerProperty;
    }

    public void setStringProperty(String stringProperty) {
        this.stringProperty = stringProperty;
    }

    public void setBooleanProperty(boolean booleanProperty) {
        this.booleanProperty = booleanProperty;
    }

    public void setIntegerProperty(int integerProperty) {
        this.integerProperty = integerProperty;
    }
}

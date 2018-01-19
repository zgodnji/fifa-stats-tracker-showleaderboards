package com.zgodnji.fifastatstracker;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("monthly")
public class ShowLeaderboardProperties {

    @ConfigValue(watch = true)
    private String gameOfTheMonth;

    public String getGameOfTheMonth() {
        return gameOfTheMonth;
    }

    public void setGameOfTheMonth(String gameOfTheMonth) {
        this.gameOfTheMonth = gameOfTheMonth;
    }

}

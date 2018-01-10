package com.zgodnji.fifastatstracker;

import com.kumuluz.ee.discovery.annotations.RegisterService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@RegisterService
@ApplicationPath("v1")
public class ShowLeaderboardApplication extends Application {
}

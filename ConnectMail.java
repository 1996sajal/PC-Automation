package com.sajal;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;

public class ConnectMail {

    private Robot robot;
    private Process process;

    public void Gmail() {
        try {
            robot = new Robot();
            process = Runtime.getRuntime().exec("C:\\Users\\Manoj Tyagi\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe");
        } catch (AWTException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}

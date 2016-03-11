/*
 Created by SAJAL TYAGI
 22/12/2015
 21:12 IST
 */
package com.sajal;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import java.io.IOException;

public class speech {
        
    public static void main(String[] args) throws IOException {
        ConnectMail connectMail = new ConnectMail();
                
        ConfigurationManager cm;
        Process p = null;
        int track = 0;
        if (args.length > 0) {
            cm = new ConfigurationManager(args[0]);
        } else {
            cm = new ConfigurationManager(speech.class.getResource("helloworld.config.xml"));
        }

        Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
        recognizer.allocate();

        Microphone microphone = (Microphone) cm.lookup("microphone");
        if (!microphone.startRecording()) {
            System.out.println("Cannot start microphone.");
            recognizer.deallocate();
            System.exit(1);
        }

        System.out.println("Say: (Open) ( Paint | Notepad | CMD)");
        Runtime runtime = Runtime.getRuntime();
        while (true) {
            System.out.println("Start speaking. Press Ctrl-C to quit.\n");

            Result result = recognizer.recognize();

            if (result != null) {
                String resultText = result.getBestFinalResultNoFiller();
                System.out.println("You said: " + resultText + '\n');
                if (resultText.equals("paint") || resultText.equals("open paint")) {
                    if (track == 0) {
                        p = runtime.exec("mspaint");
                        track++;
                    }
                }
                if (resultText.equals("note") || resultText.equals("open note")) {
                    if (track == 0) {
                        p = runtime.exec("notepad");
                        track++;
                    }
                }
                if (resultText.equals("system")) {
                    if (track == 0) {
                        p = runtime.exec("cmd /c start cmd.exe");
                        track++;
                    }
                }
                if(resultText.equals("connect")){
                    if(track == 0){
                        connectMail.Gmail();
//                        track++;
                    }
                }
                if (resultText.equals("close") || resultText.equals("exit")) {
                    try {
                        if (track > 0) {
                            p.destroy();
                            track--;
                        }
                    } catch (NullPointerException n) {
                        System.err.println("ERROR :" + n);
                    }
                }
            } else {
                System.out.println("I can't hear what you said.\n");
            }

        }
    }

}

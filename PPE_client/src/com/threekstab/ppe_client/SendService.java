package com.threekstab.ppe_client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;

public class SendService extends AsyncTask<String, Void, Void>{

	protected Void doInBackground(String... data) {
        String ip = "192.168.0.10";
        int port = 1234;
        // Do work here, based on the contents of dataString
        try {
            Socket s = new Socket(ip, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            //send output msg
            for(String obj : data){
            	out.write(obj);
                out.flush();
            } 
            
            //close connection
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onProgressUpdate(Void none) {
    }

    protected void onPostExecute(Void none) {
    }
}

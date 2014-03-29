package com.threekstab.ppe_client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import android.os.AsyncTask;

public class ReceiveService extends 
AsyncTask<String/* work parameter*/, Void/*progress parameter*/, String/*return parameter*/>{

	protected String doInBackground(String... data) {
        String ip = "127.0.0.1";
        int port = 1234;
        String res = null;
        // Do work here, based on the contents of dataString
        try {
            Socket s = new Socket(ip, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            //send output msg
            res = in.readLine();
            	/*out.write(obj);
                out.flush();*/
            
            //close connection
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    protected void onProgressUpdate(Void none) {
    }

    protected void onPostExecute(String none) {
    }
}

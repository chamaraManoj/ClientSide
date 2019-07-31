package com.example.clientside;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class MainActivity extends AppCompatActivity {

    //private UiHandler mUiHandler;
    String message = "";
    private static String ip = "10.130.1.229";

    Button sendButton;
    private byte[] receiveBuffer1;
    private byte[] receiveBuffer2;
    private byte[] receiveBuffer3;
    private byte[] receiveBuffer4;

    private static int frameID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        message = "test";
        sendButton = findViewById(R.id.button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //frameID++;
                sendMessage();
            }
        });
    }

    public void sendMessage() {
        Client myClient = new Client();
        myClient.execute();
    }

    class Client extends AsyncTask<Void, Void, Void> {

        /*String dstAddress;
        int dstPort;
        String response = "";
        TextView textResponse;*/

        byte[] receiverBuffer;
        int frameID;

        public Client() {
            //this.receiverBuffer = receiverBuffer;
            //this.frameID = frameID;
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Log.d("Debug", "2");
            int BUFFER_LENGTH = 9;
            Log.d("Debug", "3");
            Socket socket1 = null;
            Socket socket2 = null;
            Socket socket3 = null;
            Socket socket4 = null;
            Socket socket5 = null;

            try {
                Log.d("Debug", "4");
                Log.d("Debug", "5");
                byte[] sendBuffer = new byte[BUFFER_LENGTH];
                //Log.d("Debug", "6");
                for (int frame = 0; frame < 1; frame++) {
                    byte tile1 = 1;
                    byte tile2 = 3;
                    byte tile3 = 5;
                    byte tile4 = 6;
                    frameID = frame;
                    Log.d("Debug", String.valueOf(frameID));
                    byte quality = 1;
                    socket1 = new Socket(ip, 5550);
                    socket2 = new Socket(ip, 5551);
                    socket3 = new Socket(ip, 5552);
                    socket4 = new Socket(ip, 5553);
                    socket5 = new Socket(ip, 5554);
                    //Log.d("Debug", "7");
                    byte[] bytes = ByteBuffer.allocate(4).putInt(frameID).array();
                    sendBuffer[0] = bytes[0];
                    sendBuffer[1] = bytes[1];
                    sendBuffer[2] = bytes[2];
                    sendBuffer[3] = bytes[3];
                /*Log.d("Debug","0 "+String.valueOf(sendBuffer[0]));
                Log.d("Debug","1 "+String.valueOf(sendBuffer[1]));
                Log.d("Debug","2 "+String.valueOf(sendBuffer[2]));
                Log.d("Debug","3 "+String.valueOf(sendBuffer[3]));*/
                    sendBuffer[4] = tile1;
                    sendBuffer[5] = tile2;
                    sendBuffer[6] = tile3;
                    sendBuffer[7] = tile4;
                    sendBuffer[8] = quality;

                /*for(int i =0;i<9;i++){
                    Log.d("Debug","ok"+String.valueOf(sendBuffer[i]));
                }*/

                    try {
                        //Log.d("Debug", "Created Socket");
                        OutputStream os = socket1.getOutputStream();
                        //Log.d("Debug", "Sending msg");
                        //String sendMessage = "This";
                        os.write(sendBuffer, 0, sendBuffer.length);
                        os.flush();
                        socket1.shutdownOutput();

                        ReceivedBufferUpdateTask receivedBufferUpdateTask1 = new ReceivedBufferUpdateTask(receiveBuffer1);
                        /*ReceivedBufferUpdateTask receivedBufferUpdateTask2 = new ReceivedBufferUpdateTask(receiveBuffer2);
                        ReceivedBufferUpdateTask receivedBufferUpdateTask3 = new ReceivedBufferUpdateTask(receiveBuffer3);
                        ReceivedBufferUpdateTask receivedBufferUpdateTask4 = new ReceivedBufferUpdateTask(receiveBuffer4);*/


                        SocektReceiveFile socketReceiveFile1 = new SocektReceiveFile(socket2, receivedBufferUpdateTask1,1);
                       /*SocektReceiveFile socektReceiveFile2 = new SocektReceiveFile(socket3, receivedBufferUpdateTask2,2);
                        SocektReceiveFile socektReceiveFile3 = new SocektReceiveFile(socket4, receivedBufferUpdateTask3,3);
                        SocektReceiveFile socektReceiveFile4 = new SocektReceiveFile(socket5, receivedBufferUpdateTask4,4);*/

                        ModuleManager.getDownloadManager().runDownloadFile(socketReceiveFile1);
                        /*ModuleManager.getDownloadManager().runDownloadFile(socektReceiveFile2);
                        ModuleManager.getDownloadManager().runDownloadFile(socektReceiveFile3);
                        ModuleManager.getDownloadManager().runDownloadFile(socektReceiveFile4);*/


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            /*ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
                    1024);
            byte[] buffer = new byte[1024];

            int bytesRead;
            InputStream inputStream = socket.getInputStream();

            /*
             * notice: inputStream.read() will block if no data return
             */
            /*while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                response += byteArrayOutputStream.toString("UTF-8");
            }*/
                }
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //response = "IOException: " + e.toString();
            } catch (Exception e) {
                e.printStackTrace();
                //Log.d("Degbig", e.toString());
            } finally {
                //Log.d("Degbig", "7");
                if (socket1 != null) {
                    try {
                        socket1.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }
}

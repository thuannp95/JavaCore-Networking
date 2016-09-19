package demosocket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PhuongThuan
 */
public class Client {

    static BufferedWriter bw = null;
    static BufferedReader br = null;
    static Socket socket = null;

    public static void main(String[] args) {
        try {
            System.out.println("Đang kết nối đến Server...");
            socket = new Socket("localhost", 6969);
            System.out.println("Kết nối đến server thành công !!");
            System.out.println("================================");
            //Step 1: Receive message from Server: 
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        while (true) {
                            String readline = br.readLine();
                            System.out.println("Server: " + readline);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } finally {
                        try {
                            br.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }).start();//end step 1.

            //Step 2: Send message to Server:
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (true) {
                String ms = new Scanner(System.in).nextLine();
                bw.write(ms);
                bw.newLine();
                bw.flush();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                bw.close();
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

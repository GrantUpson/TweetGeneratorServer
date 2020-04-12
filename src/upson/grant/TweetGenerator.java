package upson.grant;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TweetGenerator
{
    public static void main(String[] args)
    {
        if(args.length != 2)
        {
            System.out.println("Error: Invalid parameters, usage: Java -jar TweetGeneratorServer.jar 6666 Tweets.txt");
        }
        else
        {
            try(ServerSocket serverConnection = new ServerSocket(Integer.parseInt(args[0])))
            {
                Socket incomingConnection = serverConnection.accept();

                System.out.println("Data Server connected from: " + serverConnection.getInetAddress().getHostAddress());

                String textFile = "src/upson/grant/" + args[1];
                String line = "";
                int counter = 0;

                try(BufferedReader reader = new BufferedReader(new FileReader(textFile));
                    BufferedWriter tweetOutput = new BufferedWriter(new OutputStreamWriter(incomingConnection.getOutputStream())))
                {
                    while ((line = reader.readLine()) != null)
                    {
                        if (counter > 0)
                        {
                            tweetOutput.write(line + "\r\n");
                            tweetOutput.flush();
                        }

                        counter++;
                        Thread.sleep(1000);
                    }

                    incomingConnection.close();
                }
            }
            catch(IOException ioException)
            {
                System.out.println("Error: Connection lost or host is not online.");
            }
            catch(InterruptedException iException)
            {
                System.out.println("Error: Thread interrupted before sleep could be successful.");
            }
        }
    }
}
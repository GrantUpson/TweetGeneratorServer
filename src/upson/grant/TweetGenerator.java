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
            System.out.println("Error: Invalid parameters, usage: Java -jar TweetGeneratorServer.jar <Listening Port> <File Name>");
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
            catch(IOException | InterruptedException exception)
            {
                System.out.println("Error: " + exception.getMessage());
            }
        }
    }
}
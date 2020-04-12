package upson.grant;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TweetGenerator
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        if(args.length != 2)
        {
            System.out.println("Error: Invalid parameters, usage: Java -jar TweetGeneratorServer.jar 6666 Tweets.txt");
        }
        else
        {
            ServerSocket serverConnection = new ServerSocket(Integer.parseInt(args[0]));
            Socket incomingConnection = serverConnection.accept();
            BufferedWriter tweetOutput = new BufferedWriter(new OutputStreamWriter(incomingConnection.getOutputStream()));

            System.out.println("Connected");

            String textFile = args[1];
            String line = "";
            int counter = 0;

            try(BufferedReader reader = new BufferedReader(new FileReader(textFile)))
            {
                while((line = reader.readLine()) != null)
                {
                    if(counter > 0)
                    {
                        System.out.println(line);
                        tweetOutput.write(line + "\r\n");
                        tweetOutput.flush();
                    }

                    counter++;
                    Thread.sleep(1000);
                }

                incomingConnection.close();
                serverConnection.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
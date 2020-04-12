package upson.grant;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
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
            DataOutputStream tweetOutput = new DataOutputStream(incomingConnection.getOutputStream());

            String textFile = args[1];
            String line = "";
            //String cvsSplitBy = "\t";
            int counter = 0;

            try(BufferedReader reader = new BufferedReader(new FileReader(textFile)))
            {
                while((line = reader.readLine()) != null)
                {
                    if(counter > 0)
                    {
                        tweetOutput.writeUTF(line);
                    }

                    counter++;
                    Thread.sleep(1000);
                    tweetOutput.flush();
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
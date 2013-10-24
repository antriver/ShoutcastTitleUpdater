
/*
 * Main class for Shoutcast title updater
 * 
 * @author  Anthony Kuske - www.anthonykuske.com
 * @version 2013-10-13 16:58
 */

public class main
{
   public static void main(String[] args) throws Exception { 
        String shoutcastServer = args[0];
        String shoutcastPassword = args[1];
        String inputFilename = args[2];
        ShoutcastUpdater updater = new ShoutcastUpdater(shoutcastServer,shoutcastPassword,inputFilename);
   }
}

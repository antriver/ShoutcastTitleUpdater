import java.io.*;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;
import java.text.SimpleDateFormat;

class ShoutcastUpdater 
{

  private String shoutcastPassword;
  private String shoutcastServer;
  private String inputFilename;
  private int checkInterval = 10000; //10 second
  private String lastFileContents;

  public ShoutcastUpdater(String shoutcastServer, String shoutcastPassword, String inputFilename)
  {
    this.shoutcastServer = shoutcastServer;
    this.shoutcastPassword = shoutcastPassword;
    this.inputFilename = inputFilename;
    
    //Check every checkInterval milliseconds
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask()
    {
        public void run()
        {
            updateTitleMaybe();
        }
    }, 0, checkInterval);   
  }
  
  public void updateTitleMaybe()
  {
    String fileContents = getFileContents( this.inputFilename );
    
    //We convert it even if we don't need it so we can log the nice version
    String title = this.convertFileContents(fileContents);
    
    if ( !fileContents.equals(this.lastFileContents) )
    {
        this.lastFileContents = fileContents;
        this.log( "Title changed to: "+title);
        setShoutcastTitle( title ); 
    }
    else
    { 
       this.log( "Title hasn't changed. ("+title+")" );
    }
      
  }
  
  
  private String getFileContents( String filename )
  {
    String line = "";
    try
    {
      FileReader fr = new FileReader(filename);
      BufferedReader br = new BufferedReader(fr);
      line = br.readLine(); //Read the first line of the tile
      br.close();
    }
    catch (Exception e)
    { 
        this.log("Error reading file: " + e.getMessage());
    }    
    line = line.trim();
    return line;
  }
  
  private String convertFileContents(String title)
  {    
    try
    {
        //Replace the weird BCX seperator. The URL encoding is a gross but working way to find the seperator character
        title = URLEncoder.encode(title,"UTF-8");
        title = title.replace("%EF%BF%BD"," - ");
        title = URLDecoder.decode(title,"UTF-8");
        
        String[] parts = title.split(" - ");

        title = "";
        //Check for the title duplication from BCX (also rebuild the string)
        for (int i = 0; i < parts.length; i++)
        {
            //Not the first part, and equal to the previous part
            if ( i > 0 && parts[i].equals(parts[i-1]) )
            {
                continue;
            }
            
            //Seperator
            if ( i > 0 )
            {
                title = title + " - ";
            }
            
            title = title + parts[i];
        }
    }
    catch(Exception e)
    { 
        this.log(e.getMessage());
    }
    
    return title;    
  }
  
  
  /*
   * Send title to Shoutcast server
   */
  public void setShoutcastTitle(String title)
  {
    try
    {
        //Change title to url encoded string
        title = URLEncoder.encode(title,"UTF-8");
        title = title.replace("+","%20");
        
        this.log("Sending to "+this.shoutcastServer);
        
        URL url = new URL( "http://"+this.shoutcastServer+"/admin.cgi?mode=updinfo&pass="+this.shoutcastPassword+"&song="+title );
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod( "GET" );
        con.setDoInput( true );
        con.setRequestProperty( "User-agent","Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.A.B.C Safari/525.13 " );

        InputStream is = con.getInputStream();
        con.disconnect();
    }
    catch(Exception e)
    {
        //this.log("Error sending to Shoutcast server: "+e.getMessage());
    }
      
  }
  
  private void log(String text)
  {
      String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
      
      System.out.println("["+timeStamp+"] "+text);
    }
}
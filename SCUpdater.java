import java.io.*;
import java.net.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;
import java.net.URLEncoder;

class SCUpdater 
{

  private String scpass;
  private String scserver;
  private String SLOFloc;
  private int checkInterval = 10000; //milliseconds
  
  private String nowplaying;

  public SCUpdater(String scserver, String scpass, String SLOFloc)
  {
    this.scserver = scserver;
    this.scpass = scpass;
    this.SLOFloc = SLOFloc;
    
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
    String newcontents = getSLOFContents();
    if ( !newcontents.equals(nowplaying) )
    {
        System.out.println( "Title changed:"+newcontents );
        nowplaying = newcontents;
        setShoutcastTitle( convertSLOFText(nowplaying) ); 
    }
    else { System.out.println( "Title not changed." ); }
      
  }
  
  
  private String getSLOFContents()
  {
    String line = "";
    try
    {
      FileInputStream stream = new FileInputStream( this.SLOFloc );
      DataInputStream in = new DataInputStream(stream);
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      line = br.readLine();
      in.close();
    }
    catch (Exception e){ System.err.println("Error: " + e.getMessage()); }    
      
    return line;
  }
  
  private String convertSLOFText(String title)
  {
    //title = title.replace(" ","%20");
    title = title.replace("¦"," - ");
    
    try
    {
        title = URLEncoder.encode(title,"UTF-8");
        title = title.replace("+","%20");
    }
    catch(Exception e) { System.out.println(e.getMessage()); }
    
    return title;    
  }
  
  
  public void setShoutcastTitle(String title)
  {
      try
      {
          URL url = new URL( "http://"+this.scserver+"/admin.cgi?mode=updinfo&pass="+this.scpass+"&song="+title );
          HttpURLConnection con = (HttpURLConnection) url.openConnection();

          con.setRequestMethod( "GET" );
          con.setDoInput( true );
          con.setRequestProperty( "User-agent","Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.A.B.C Safari/525.13 " );

          InputStream is = con.getInputStream();
          con.disconnect();
      }
      catch(Exception e) {  }
      
  }
}
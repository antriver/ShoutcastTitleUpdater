
public class main
{
   public static void main(String[] args) throws Exception { 
        String scserver = args[0];
        String scpass = args[1];
        String SLOFloc = args[2];
        SCUpdater scupdater = new SCUpdater(scserver,scpass,SLOFloc);
   }
}

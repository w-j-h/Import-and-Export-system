package gui;
import java.util.*;
import java.io.*;

public class Person implements Serializable
{
  protected boolean PersonIdentity;
  protected String UserName;  
  protected String Password;
  protected String gender;
  protected String Birthday;
  protected String SSN;
  List <Person> AllMembers;
  static String FileName ="Person.txt"; 
 
  protected Person(boolean PersonIdentity,String UserName,String Password,String gender,String Birthday,String SSN)
  {
      this.PersonIdentity=PersonIdentity;
      this.UserName=UserName;
      this.Password=Password;
      this.gender=gender;
      this.Birthday=Birthday;
      this.SSN=SSN;
      this.AllMembers=new ArrayList<Person>();
     
      Encryption(this.Password);
  }
  
  protected void Encryption (String Password)
  {
      String ExtraEncryption = "FACE";
      this.Password=this.Password+ExtraEncryption ;
      
  } 
  protected  void GetAllDataFromFile () throws Exception
   {
      if(new File(FileName).length() == 0)
           return;
      
       BufferedReader reader = new BufferedReader(new FileReader(FileName));
       String temp ;
       while((temp = reader.readLine()) != null)
       {
           String[] p = temp.split(",");
           Person user =new Person(false,"", "", "", "", "");
           user.PersonIdentity = Boolean.parseBoolean(p[0]);
           user.UserName = p[1];
           user.Password = p[2];
           user.Birthday = p[3];
           user.SSN = p[4];
           user.gender = p[5];
           AllMembers.add(user);
       }
   }
   
  protected boolean Login (String username, String Password) throws Exception
  {
     // Encryption(Password);
      GetAllDataFromFile();
      boolean IsMangerOruser = false;
      for(int i=0;i<AllMembers.size();i++)
      {
          if(AllMembers.get(i).UserName.equals(username) && AllMembers.get(i).Password.equals(Password) && (AllMembers.get(i).PersonIdentity) == true)
          {
              IsMangerOruser=true;
               this.PersonIdentity=IsMangerOruser;
               this.UserName = AllMembers.get(i).UserName;
               this.Password = AllMembers.get(i).Password;
               this.gender=AllMembers.get(i).gender;
               this.Birthday = AllMembers.get(i).Birthday;
               this.SSN = AllMembers.get(i).SSN;
          }
          if ((AllMembers.get(i).UserName.equals(username) && AllMembers.get(i).Password.equals(Password) && (AllMembers.get(i).PersonIdentity) == false))
          {
              IsMangerOruser =false;
              this.PersonIdentity=IsMangerOruser;
               this.UserName = AllMembers.get(i).UserName;
               this.Password = AllMembers.get(i).Password;
               this.gender=AllMembers.get(i).gender;
               this.Birthday = AllMembers.get(i).Birthday;
               this.SSN = AllMembers.get(i).SSN;
          }
         
      }
      return IsMangerOruser; 
  }
}

 class DataEntriyUser extends Person
{
   protected List <DataEntriyUser> users = new  ArrayList<DataEntriyUser>();
   protected List <DataEntriyUser> AllMembers = new  ArrayList<DataEntriyUser>() ;
    
   DataEntriyUser(String UserName,String Password,String gender,String Birthday,String SSN)
    {
    super(false, UserName, Password, gender, Birthday, SSN);
    }
    
    protected void Users() throws Exception 
    {
       if(new File(FileName).length() == 0)
           return;
      
       BufferedReader reader = new BufferedReader(new FileReader(FileName));
       String temp ;
       while((temp = reader.readLine()) != null)
       {
           String[] p = temp.split(",");
           DataEntriyUser user =new DataEntriyUser("", "", "", "", "");
           user.PersonIdentity = Boolean.parseBoolean(p[0]);
           user.UserName = p[1];
           user.Password = p[2];
           user.Birthday = p[3];
           user.SSN = p[4];
           user.gender = p[5];
           AllMembers.add(user);
       }
       
      for(int i=0;i<AllMembers.size();i++)
     {
        if(AllMembers.get(i).PersonIdentity == false)
        {
            DataEntriyUser Data = (DataEntriyUser)AllMembers.get(i);
            users.add(Data);
        }
     }
    }
    
    protected void UserSignUp (DataEntriyUser p) throws FileNotFoundException, IOException, Exception 
    { 
      Users();
      AllMembers.add((DataEntriyUser)p);
      users.add((DataEntriyUser)p);
      
      FileOutputStream  fos = new FileOutputStream(FileName,true);
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
      writer.write(p.PersonIdentity+",");
      writer.write(p.UserName+",");
      writer.write(p.Password+",");
      writer.write(p.Birthday+",");
      writer.write(p.SSN+",");
      writer.write(p.gender);
      writer.newLine();
      writer.close();
      fos.close();      
    }
}

class Manger extends Person
{
   protected List<Manger> Mangers    = new ArrayList<Manger>() ;
   protected List<Manger> AllMembers = new ArrayList<Manger>();    
    Manger(String UserName,String Password,String gender,String Birthday,String SSN)
    {
    super(true, UserName, Password, gender, Birthday, SSN);
    }
    
    protected void Mangers() throws Exception 
    {
        
      if(new File(FileName).length() == 0)
           return;
      
       BufferedReader reader = new BufferedReader(new FileReader(FileName));
       String temp ;
       while((temp = reader.readLine()) != null)
       {
           String[] p = temp.split(",");
           Manger user =new Manger("", "", "", "", "");
           user.PersonIdentity = Boolean.parseBoolean(p[0]);
           user.UserName = p[1];
           user.Password = p[2];
           user.Birthday = p[3];
           user.SSN = p[4];
           user.gender = p[5];
           AllMembers.add(user);
       }
      
      for(int i=0;i<AllMembers.size();i++)
     {
        if(AllMembers.get(i).PersonIdentity == true)
        {
            Manger Data = (Manger)AllMembers.get(i);
            Mangers.add(Data);
        }
     }
    }
    
    protected void DeletManger(Manger M1) throws Exception
    {
        
        for (int i=0;i<Mangers.size();i++)
        {
          if(Mangers.get(i).SSN == M1.SSN)
          {
              Mangers.remove(Mangers.get(i));
          }
        }
        for (int i=0;i<AllMembers.size();i++)
        {
          if(AllMembers.get(i).SSN==M1.SSN)
          {
              AllMembers.remove(AllMembers.get(i));
          }
        }
    FileOutputStream  fos = new FileOutputStream(FileName);
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
    
    for(int i=0;i<AllMembers.size();i++)
     {
      writer.write(AllMembers.get(i).PersonIdentity+",");
      writer.write(AllMembers.get(i).UserName+",");
      writer.write(AllMembers.get(i).Password+",");
      writer.write(AllMembers.get(i).Birthday+",");
      writer.write(AllMembers.get(i).SSN+",");
      writer.write(AllMembers.get(i).gender);
      writer.newLine();
     }
        writer.close();
        fos.close(); 
    }
    
    protected void MangerSignUp (Manger p) throws Exception
    {
      Mangers();
      AllMembers.add(p);
      Mangers.add(p);
      
      FileOutputStream  fos = new FileOutputStream(FileName,true);
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
      writer.write(p.PersonIdentity+",");
      writer.write(p.UserName+",");
      writer.write(p.Password+",");
      writer.write(p.Birthday+",");
      writer.write(p.SSN+",");
      writer.write(p.gender);
      writer.newLine();
      writer.close();
      fos.close(); 
    }
}

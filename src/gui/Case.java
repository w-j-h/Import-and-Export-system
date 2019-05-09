package gui;
import java.io.*;
import java.util.*;

public class Case implements Serializable
{
 protected static int ID = 0;
 protected int TempID;
 protected int Indecator;
 protected String Name;
 protected String Description;
 protected String Source;
 protected String Date;
 protected String Time;
 protected String User;
 
 protected String Action;
 protected String ActionID;
 protected String ActionDate;
 protected String ActionTime;
 
 protected String TransactionFrom ;
 protected String TransactionTo ; 
 protected String TransactionDate ; 
 protected String TransactionTime ; 
 protected String CaseOwner;
 
 protected ArrayList<Case> Cases ;
 static String FileName  = "Cases.txt";
 
 protected Case(String Name , String Description , String Date , String User,String Time,String Source)
 {
    this.Name = Name;
    this.Description = Description;
    this.Date = Date;
    this.User = User;
    this.Time=Time;
    this.Source=Source;
    
    this.Action = " ";
    this.ActionID =" ";
    this.ActionDate =" ";
    this.ActionTime = " ";
    
    this.TransactionFrom = " ";
    this.TransactionTo = " ";
    this.TransactionTime = " ";
    this.TransactionDate = " ";
    this.CaseOwner=" ";
    this.Cases = new ArrayList<Case>();
    
    if(Name != "")
    {
        ID ++; 
        this.TempID=ID;
    }
 }
 
 protected void LoadFromFile () throws Exception 
 { 
        if(new File(FileName).length() == 0)
           return;
      
       BufferedReader reader = new BufferedReader(new FileReader(FileName));
       String temp ;
       while((temp = reader.readLine()) != null)
       {
           String[] p = temp.split(",");
           Case c = new Case("", "", "", "", "", "");
           c.Indecator = Integer.parseInt(p[0]);
           c.TempID  = Integer.parseInt(p[1]);
           c.Name = p[2];
           c.Description = p[3];
           c.Source=p[4];
           c.Date = p[5];
           c.Time = p[6];
           c.User = p[7];
           c.TransactionFrom = p[8];
           c.TransactionTo = p[9];
           c.TransactionTime = p[10];
           c.TransactionDate = p[11];
           c.CaseOwner = p[12];
           c.ActionID= p[13];
           c.ActionDate = p[14];
           c.ActionTime = p[15];
           c.Action = p[16];
           Cases.add(c);
       }
 }
 
  protected void SaveToFile () throws Exception 
 {   
      FileOutputStream  fos = new FileOutputStream(FileName);
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
      for(int i=0;i<Cases.size();i++)
      {
        writer.write(Cases.get(i).Indecator + ",");
        writer.write(Cases.get(i).TempID + ",");
        writer.write(Cases.get(i).Name+",");
        writer.write(Cases.get(i).Description+",");
        writer.write(Cases.get(i).Source+",");
        writer.write(Cases.get(i).Date+",");
        writer.write(Cases.get(i).Time+",");
        writer.write(Cases.get(i).User+",");
        writer.write(Cases.get(i).TransactionFrom + ",");
        writer.write(Cases.get(i).TransactionTo + ",");
        writer.write(Cases.get(i).TransactionTime + ",");
        writer.write(Cases.get(i).TransactionDate + ",");
        writer.write(Cases.get(i).CaseOwner + ",");
        writer.write(Cases.get(i).ActionID + ",");
        writer.write(Cases.get(i).ActionDate + ",");
        writer.write(Cases.get(i).ActionTime + ",");
        writer.write(Cases.get(i).Action + "" );
        writer.newLine();
      }
      writer.close();
      fos.close();
  }
 
  protected void AssignCase (String Name, String M1) throws Exception
  {
      
      for(int i=0;i<Cases.size();i++)
    {
        if(Cases.get(i).Name.equals(Name))
        {
            Cases.get(i).CaseOwner = M1;
            break;
        }
    }
      SaveToFile();
  }
  
  protected void AddcomCase(Case C1) throws Exception
  {
   Cases.clear();
   LoadFromFile ();
   Cases.add(C1);
   SaveToFile();
  }
  
  protected void AddAction(String Name ,String Action , String ActionID, String  ActionDate ,String ActionTime) throws Exception
  {
    for(int i=0;i<Cases.size();i++)
    {
        if(Cases.get(i).Name.equals(Name))
        {
            Cases.get(i).ActionID +=ActionID+"@";
            Cases.get(i).ActionDate +=ActionDate+"@";
            Cases.get(i).ActionTime +=ActionTime+"@";
            Cases.get(i).Action +=  Action + "@" ;
            SaveToFile();      
            break;
        }
    }
  }
 
  protected void AddOrEditTransaction (String Name, String M1, String M2,String Time, String Date) throws Exception
  {
    for(int i=0;i<Cases.size();i++)
    {
        if(Cases.get(i).Name.equals(Name))
        {
            Cases.get(i).TransactionFrom=M1;
            Cases.get(i).TransactionTo =M2;
            Cases.get(i).CaseOwner=M2;
            Cases.get(i).TransactionDate=Date;
            Cases.get(i).TransactionTime=Time;
            
            SaveToFile();
            break;
        }
    }   
  }
   
   protected List<Case> BringOutgoinCases() throws Exception{
      LoadFromFile ();
       List<Case>OutGoing = new ArrayList<Case>() ;
       for(int i=0;i<Cases.size();i++)
       {
           if(Cases.get(i).Indecator==1)
           {
               OutGoing.add(Cases.get(i));
           }
       }
       
       return OutGoing;
   }
 
   protected Case OutGoingCaseInfo(String Name) throws Exception{
      List<Case>OutGoing = BringOutgoinCases();
       Case C1 = null;
      for(int i=0;i<OutGoing.size();i++)
      {
           if(OutGoing.get(i).Name.equals(Name))
           {
             C1 = OutGoing.get(i);
             break;
           }
       }
      return C1;
  }
  
   protected List<Case> BringComeCases() throws Exception{
      LoadFromFile ();
       List<Case>Come = new ArrayList<Case>();
       for(int i=0;i<Cases.size();i++)
       {
           if(Cases.get(i).Indecator==0)
           {
               Come.add(Cases.get(i));
           }
       }
       
       return Come;
   }
  
   protected Case ComeCaseInfo(String Name) throws Exception{
      List<Case>Come = BringComeCases();
       Case C1 = null;
      for(int i=0;i<Come.size();i++)
      {
           if(Come.get(i).Name.equals(Name))
           {
             C1 = Come.get(i);
             break;
           }
       }
      return C1;
  }
  
  protected void EditCase(Case c1) throws Exception{
      for(int i =0;i<Cases.size();i++)
      {
          if(Cases.get(i).Name.equals(c1.Name))
          {
              Cases.get(i).Name = c1.Name;
              Cases.get(i).Date = c1.Date;
              Cases.get(i).Description = c1.Description;
              Cases.get(i).User = c1.User;
              Cases.get(i).Time=c1.Time;
              Cases.get(i).Source=c1.Source;
              break;
          }
      }
      SaveToFile();
  }
 
  protected void EditAction(String Name,String Action,String ActionID, String  ActionDate ,String ActionTime) throws Exception
  {
      for(int i=0;i<Cases.size();i++)
      {
       if(Cases.get(i).Name.equals(Name))
       {
           String [] tempid = Cases.get(i).ActionID.split("@");
           String [] tempDate = Cases.get(i).ActionDate.split("@");
           String [] tempTime = Cases.get(i).ActionTime.split("@");
           String [] tempAction  = Cases.get(i).Action.split("@");
           
           for(int j=0;j<tempid.length;j++)
           {
               if(tempid[j].equals(ActionID))
               {
                    tempDate[j]   = ActionDate;
                    tempTime[j]   =ActionTime;
                    tempAction[j] = Action;
               }
           }
            
            Cases.get(i).ActionDate = " ";
            Cases.get(i).ActionTime=" ";
            Cases.get(i).Action = " ";
           
            for(int k=0;k<tempid.length;k++)
           {
            Cases.get(i).ActionDate += tempDate[k]+"@";
            Cases.get(i).ActionTime += tempTime[k]+ "@";
            Cases.get(i).Action     += tempAction[k]+"@";
           }
            
       }
      }
      SaveToFile();
  }
  
  protected void DeleteCase(String Name) throws Exception{
      
      for(int i=0;i<Cases.size();i++)
      {
          if(Cases.get(i).Name.equals(Name))
          {
              Cases.remove(Cases.get(i));
              SaveToFile();
              break;
          }
      }
      
  }
  
 protected void DeleteAction(String Name ,String ActionID) throws Exception{
    
     
     for(int i=0;i<Cases.size();i++)
     {
         if(Cases.get(i).Name.equals(Name))
         {
           String [] tempid = Cases.get(i).ActionID.split("@");
           String [] tempDate = Cases.get(i).ActionDate.split("@");
           String [] tempTime = Cases.get(i).ActionTime.split("@");
           String [] tempAction  = Cases.get(i).Action.split("@");
           
           Cases.get(i).ActionID = " ";
           Cases.get(i).ActionDate = " ";
           Cases.get(i).ActionTime=" ";
           Cases.get(i).Action = " ";
           
           for(int j=0;j<tempid.length;j++)
            {
                if(tempid[j].equals(ActionID))
                {
                    continue;
                }
                
                Cases.get(i).ActionID   += tempid[j] + "@";
                Cases.get(i).ActionDate += tempDate[j]+"@";
                Cases.get(i).ActionTime += tempTime[j]+ "@";
                Cases.get(i).Action     += tempAction[j]+"@";
           
            }
         }
     }
     SaveToFile();
 }
  
   protected void DeleteTransaction(String Name) throws Exception
 {
    for(int i=0;i<Cases.size();i++)
    {
        if(Cases.get(i).Name.equals(Name))
        {
            Cases.get(i).TransactionFrom= " ";
            Cases.get(i).TransactionTo =" ";
            Cases.get(i).CaseOwner=" ";
            Cases.get(i).TransactionTime=" ";
            Cases.get(i).TransactionDate=" ";
            
            SaveToFile();
            break;
        }
    }
 }
 
  protected void ChangeIndecator(String Name,int Indecator) throws Exception 
    {
        for(int i =0 ; i<Cases.size();i++)
        {
            if(Cases.get(i).Name == Name)
            {
                Cases.get(i).Indecator = Indecator;
            }
        }
        SaveToFile();
    }
  
  protected void ChangeSource(String Name,String Source) throws Exception 
    {
        for(int i =0 ; i<Cases.size();i++)
        {
            if(Cases.get(i).Name == Name)
            {
                Cases.get(i).Source = Source;
            }
        }
        SaveToFile();
    }
}



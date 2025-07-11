package cmstestcases;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class CmsReadconfig {
	
	Properties pro;
   private	String url;
   private	String username;
   private 	String password;
	
	
  public CmsReadconfig(String environment) {
		try{
			File src= new File("D:\\hybrid-framework\\configurations\\cms.properties");
			
			FileInputStream fis= new FileInputStream(src);
			pro= new Properties();
			pro.load(fis);
			
         switch (environment.toLowerCase()) {
			case "dev":
				url=pro.getProperty("baseurldev");
				username=pro.getProperty("usernamedev");
				password=pro.getProperty("passworddev");
				break;
				
			case "uat":
				url=pro.getProperty("baseurluat");
				username=pro.getProperty("usernameuat");
				password=pro.getProperty("passworduat");
				break;
				
			case "prod":
				url=pro.getProperty("baseurlprod");
				username=pro.getProperty("usernameprod");
				password=pro.getProperty("passwordprod");
				break;


			default:
				break;
			}
		     }
		    catch(Exception e) {
			
			System.out.println("exception " +e.getMessage());
		}
		
	}
	
	public String geturl() {
		return url;
	
	}
	
	
	public String getusername() {
	      return username;
		
		
	}
	public String getpassword() {
		return password;

	}
}

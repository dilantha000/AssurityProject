package getRequest;
import java.util.HashMap;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class ValidateAcceptanceCriteria {
	
	Response resp;
	
	String Name;
	boolean CanRelist;
	
	@BeforeClass
	public void setupMethod(){
		resp=RestAssured.get("https://api.tmsandbox.co.nz/v1/Categories/6327/Details.json?catalogue=false");
	}
		
	@Test(priority = 0)
	public void verifyResponsecode()
	{
			
	    int code= resp.getStatusCode();
	    System.out.println("Status code"+code);
	    Assert.assertEquals(code, 200); 
	    
	    if(code == 200) {
	    	System.out.println("Successfully connected");
	    	
	    }
	    else {
	    	System.out.println("Unable to connect");
	    }
	    
	    
	}
			
	@Test(priority = 1)
	public void findName()
	{		
		Name = resp.jsonPath().getString("Name");
		Assert.assertEquals(Name, "Carbon credits");
		System.out.println("Name ="+ Name);
	}
	
	@Test(priority = 2)
	public void findCanRelist()
	{
		CanRelist = resp.jsonPath().getBoolean("CanRelist");
		Assert.assertEquals(CanRelist, true);
		System.out.println("CanRelist ="+CanRelist);
	}


	@Test(priority = 3)
	public void findDesc()
	{
	String strRes = resp.asString();
	
	boolean flag = false;
	
	List<HashMap<Integer, HashMap<String,String>>> promo = JsonPath.from(strRes).getList("Promotions");
	
	for(HashMap obj : promo){
					
		if(obj.get("Name").equals("Gallery")) {
			String desc = (String) obj.get("Description");
			
			if(desc.contains("2x larger image")) {
				flag = true;
				break;
			}
		}
						
	}
		
	if(flag==true)
	{
		System.out.println("The Promotions element with Name \"Gallery\" has a Description that contains the text \"2x larger image\"");
	}
	else
	{
		System.out.println("The Promotions element with Name = \"Gallery\" has not a Description that contains the text \"2x larger image\"");
	}
	
	Assert.assertEquals(flag, true);
	
	}
		
}

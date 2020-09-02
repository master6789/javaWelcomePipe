package yy;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;


@RestController
@RequestMapping("")
public class WSAppController 
{

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// z");
	private static final String version = "0.0.1";
	private long startTime = System.currentTimeMillis();
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String defaultMethod() 
	{
		return "Hello! This is from Heaven ... \n";
	}

	@RequestMapping(value = "/healthz", method = RequestMethod.GET)
	public String healthz() 
	{
		OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
		
		long uptime = System.currentTimeMillis() - startTime;
		float cpuUtilization = (float)osBean.getProcessCpuLoad();
		long memoryUtilization = osBean.getCommittedVirtualMemorySize();
		
		String resp = "{\n"+
				  "  \"status\": \"OK\",\n"+
				  "  \"version\": \""+version+"\",\n"+
				  "  \"uptime\": \"up since "+DATE_FORMAT.format(new Date())+"\"\n"+
				  "  \"cpu:utilization\": \""+cpuUtilization+"\"\n"+
				  "  \"memory:utilization\": \""+memoryUtilization+"\"\n"+
				"}\n";

		return resp;
	}

}

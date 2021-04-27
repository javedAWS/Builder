import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class BuilderContratcorMain {
	
	private static List<ContractorBean> contractorList;
    
	public static void main(String[] args) {
		try {
				FileReader reader=new FileReader("FileNameAndPath.properties");
				Properties p=new Properties();
				p.load(reader);
				String absolutePath=p.getProperty("filePath").concat(p.getProperty("fileName"));
				DataSource source=new TextFileDataSource();
				List<String>sourceData=source.readDataSource(absolutePath);
				
				contractorList=parseList(sourceData);
				BuilderApplication app=new BuilderContractorApplicationImpl();
				System.out.println(contractorList);
				Map<String,Set<String>> uniqueCustomerIdForContractId=app.getUniqueCustomerIdForContractId(contractorList);
				Map<String,Set<String>> uniqueCustomerIdForGeoZone=app.getUniqueCustomerIdForGeoZone(contractorList);
				Map<String,Double> avgBuildDurationOfZone=app.getAverageBuildDurationOfGeoZone(contractorList);
				app.printToFile(uniqueCustomerIdForContractId,uniqueCustomerIdForGeoZone,avgBuildDurationOfZone);
				
				
		}catch(IOException e) {
			System.out.println("File Not Found"+e.getStackTrace());
		}
	}
	
	public static List<ContractorBean> parseList(List<String> sourceData) {
		List<ContractorBean> contractorBeanList=new ArrayList<ContractorBean>();
		for(String data:sourceData) {
			String[] line=data.split(",");
			ContractorBean bean=new ContractorBean();
			bean.setCustomerId (line[0]);
			bean.setContractId(line[1]);
			bean.setGeoZone(line[2]);
			bean.setTeamCode(line[3]);
			bean.setProjectCode(line[4]);
			bean.setBuildDuration(line[5]);
			contractorBeanList.add(bean);
		}
		return contractorBeanList;
	}
	
	 
}

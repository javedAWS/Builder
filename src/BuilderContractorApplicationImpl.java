import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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

public class BuilderContractorApplicationImpl implements BuilderApplication {

	//private  Map<String,Set<String>> uniqueCustomerIdForContractId;
    //private  Map<String,Set<String>> uniqueCustomerIdForGeoZone;
    
	@Override
    public  Map<String,Set<String>> getUniqueCustomerIdForContractId(List<ContractorBean> contractorList) {
		Map<String,Set<String>> hmap=new HashMap<String,Set<String>>();
		for(ContractorBean bean:contractorList) {
			if(hmap.containsKey(bean.getContractId())) {
				Set<String>uniqueCustId=new HashSet<String>();
				uniqueCustId.addAll(hmap.get(bean.getContractId()));
				uniqueCustId.add(bean.getCustomerId());
				hmap.put(bean.getContractId(),uniqueCustId);
			}else {
				hmap.put(bean.getContractId(), Arrays.asList(bean.getCustomerId()).stream().collect(Collectors.toSet()));
			}
		}
		return hmap;
	}
	
	@Override
	public  Map<String,Set<String>> getUniqueCustomerIdForGeoZone(List<ContractorBean> contractorList) {
		Map<String,Set<String>> hmap=new HashMap<String,Set<String>>();
		for(ContractorBean bean:contractorList) {
			if(hmap.containsKey(bean.getGeoZone())) {
				Set<String>uniqueCustId=new HashSet<String>();
				uniqueCustId.addAll(hmap.get(bean.getGeoZone()));
				uniqueCustId.add(bean.getCustomerId());
				hmap.put(bean.getGeoZone(),uniqueCustId);
			}else {
				hmap.put(bean.getGeoZone(), Arrays.asList(bean.getCustomerId()).stream().collect(Collectors.toSet()));
			}
		}
		return hmap;
	}
	
	@Override
	public Map<String,Double> getAverageBuildDurationOfGeoZone(List<ContractorBean> contractorList){
		Map<String,List<Double>> hmap=new HashMap<String,List<Double>>();
		for(ContractorBean bean:contractorList) {
			if(hmap.containsKey(bean.getGeoZone())) {
				List<Double> lst=new ArrayList<Double>();
				lst.addAll(hmap.get(bean.getGeoZone()));;
				lst.add(Double.valueOf(bean.getBuildDuration().substring(0,bean.getBuildDuration().length()-1)));
				hmap.put(bean.getGeoZone(), lst);
			}else {
				hmap.put(bean.getGeoZone(), 
						Arrays.asList(Double.valueOf(bean.getBuildDuration().substring(0,bean.getBuildDuration().length()-1))));
			}
		}
		System.out.println(hmap);
		Map<String,Double>map=new HashMap<String,Double>();
		for(Map.Entry<String, List<Double>>e:hmap.entrySet()) {
			List<Double>values=e.getValue();
			Double sum=(double) 0;
			for(Double d:values) {
				sum=sum+d;
			}
			map.put(e.getKey(),Double.valueOf(sum/values.size()));
		}
		return map;
	}
	
	@Override
    public void printToFile(Map<String,Set<String>> uniqueCustomerIdForContractId,Map<String,Set<String>> uniqueCustomerIdForGeoZone,Map<String,Double> avgBuildDurationOfZone) throws IOException{
		FileReader reader=new FileReader("FileNameAndPath.properties");
		Properties p=new Properties();
		p.load(reader);
		String absolutePath=p.getProperty("outputLocation").concat(BuilderApplication.FILE_REPORT);
		
		BufferedWriter writer=new BufferedWriter(new FileWriter(absolutePath,true));
    	for(Map.Entry<String, Set<String>>e:uniqueCustomerIdForContractId.entrySet()) {
    		writer.append("The number of unique customer ID for each Contract Id "+ e.getKey()+" are = "+e.getValue().size()  );
    		writer.newLine();
    	}
    	for(Map.Entry<String, Set<String>>e:uniqueCustomerIdForGeoZone.entrySet()) {
    		writer.append("The number of unique customer ID for each geozone "+ e.getKey()+" are = "+e.getValue().size()  );
    		writer.newLine();
    	}
    	for(Map.Entry<String, Double>e:avgBuildDurationOfZone.entrySet()) {
    		writer.append("The average of buildduration for each geozone "+ e.getKey()+" are = "+e.getValue()  );
    		writer.newLine();
    	}
    	for(Map.Entry<String, Set<String>>e:uniqueCustomerIdForGeoZone.entrySet()) {
    		writer.append("The list of unique customerId for each geozone "+ e.getKey()+" are = "+e.getValue()  );
    		writer.newLine();
    	}
    	writer.close();
    }
    
}

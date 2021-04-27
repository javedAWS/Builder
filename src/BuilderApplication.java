import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BuilderApplication {
	
	public static String FILE_REPORT="Report.txt";
	public Map<String,Set<String>> getUniqueCustomerIdForContractId(List<ContractorBean> contractorList);
	public Map<String,Set<String>> getUniqueCustomerIdForGeoZone(List<ContractorBean> contractorList) ;
	public Map<String,Double> getAverageBuildDurationOfGeoZone(List<ContractorBean> contractorList);
	public void printToFile(Map<String,Set<String>> uniqueCustomerIdForContractId,Map<String,Set<String>> uniqueCustomerIdForGeoZone,Map<String,Double> avgBuildDurationOfZone) throws IOException;

}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextFileDataSource implements DataSource{
	
	@Override
	public List<String> readDataSource(String path) throws IOException {
		BufferedReader reader =new BufferedReader(new FileReader(path));
		String line =reader.readLine();
		List<String>contractList=new ArrayList<String>();
		while(line!=null) {
			contractList.add(line);
			line=reader.readLine();
		}
		
		return contractList;
	}
	

}

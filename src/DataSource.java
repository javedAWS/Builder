import java.io.IOException;
import java.util.List;

public interface DataSource {

	public List<String> readDataSource(String path) throws IOException;
}

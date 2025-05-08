package Group3.CourseApp.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CloudinaryService {
    Map uploadFile(MultipartFile file) throws IOException;
    Map deleteFile(String publicId) throws IOException;
}

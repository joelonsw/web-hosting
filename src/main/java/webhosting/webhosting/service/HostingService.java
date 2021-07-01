package webhosting.webhosting.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import webhosting.webhosting.dao.HostingDao;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class HostingService {
    private HostingDao hostingDao;

    private static String FILE_PATH = "C:\\Users\\joel6\\Desktop\\";

    public HostingService(HostingDao hostingDao) {
        this.hostingDao = hostingDao;
    }

    @Transactional
    public void saveFile(String userId, List<MultipartFile> files) throws IOException {
        for (MultipartFile file : files) {
            saveSingleFile(userId, file);
        }
    }

    public void saveSingleFile(String userId, MultipartFile file) throws IOException {
        final String originalFilename = file.getOriginalFilename();
        final String filePath = FILE_PATH + originalFilename;
        final File fileToSaveInLocalPC = new File(filePath);
        file.transferTo(fileToSaveInLocalPC);
        hostingDao.saveFile(userId, filePath);
    }
}

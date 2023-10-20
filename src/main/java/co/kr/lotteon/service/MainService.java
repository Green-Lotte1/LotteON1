package co.kr.lotteon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class MainService {

    @Autowired
    private BuildProperties buildProperties; // 빌드 정보를 갖는 객체 주입

    public void appVersion(Model model) {
        // build.gradle 파일 맨 밑에 빌드 정보를 가져오기 위해 buildInfo() 호출 해야됨
        String appName = buildProperties.getName(); // settings.gradle 파일에서 앱이름 가져옴
        String version = buildProperties.getVersion(); // build.gradle 파일에서 버전값 가져옴
        System.out.println("appName : " + appName);
        System.out.println("version : " + version);

        model.addAttribute("appInfo", appName+"-"+version);
    }

}

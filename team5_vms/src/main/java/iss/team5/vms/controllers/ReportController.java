package iss.team5.vms.controllers;

import iss.team5.vms.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

import iss.team5.vms.services.ReportService;
import iss.team5.vms.model.Report;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    ReportService rs;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    private String uploadReport(Report report, @RequestParam(value="file",required=false) MultipartFile file,
                                @RequestParam(value = "details",required=true) String details,
                                HttpServletRequest request) throws IOException {
        System.out.println("1 success");
        String name = UUID.randomUUID().toString().replaceAll("-", "");
        String imageType=file.getContentType();
        String suffix=imageType.substring(imageType.indexOf("/")+1);
        String pathRoot = request.getSession().getServletContext().getRealPath("");
        String path = "C:/Users/方子悦/Documents/GitHub/VMS/team5_vms/src/main/resources/images/"+name+"."+suffix;
        System.out.println("2 success");
        file.transferTo(new File(path));
        System.out.println("3 success");
        //add path to report
        rs.createReport(new Report(details,path));
        System.out.println("4 success");
        return "report-success";

    }

}

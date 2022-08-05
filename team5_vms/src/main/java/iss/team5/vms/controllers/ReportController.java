package iss.team5.vms.controllers;

import iss.team5.vms.helper.ReportStatus;
import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Report;
import iss.team5.vms.model.Student;
import iss.team5.vms.services.BookingService;
import iss.team5.vms.services.MailService;
import iss.team5.vms.services.StudentService;
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
    @Autowired
    StudentService ss;
    @Autowired
    BookingService bs;
    @Autowired
    MailService ms;


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    private String uploadReport(@RequestParam(value="file",required=false) MultipartFile file,
                                @RequestParam(value = "details",required=true) String details,
                                @RequestParam("bookingid") String bookingString,
                                HttpServletRequest request) throws IOException {
        System.out.println("1 success");
        String path = "";
        if (!file.isEmpty()) {
            String name = UUID.randomUUID().toString().replaceAll("-", "");
            String imageType=file.getContentType();
            System.out.println(imageType);
            String suffix=imageType.substring(imageType.indexOf("/")+1);
            /*String pathRoot = request.getSession().getServletContext().getRealPath("");*/
            File file1 = new File("");
            String filePath = file1.getCanonicalPath();//get app real path in local
            path = filePath.replaceAll("\\\\","/")+ "/src/main/resources/static/images/"+name+"."+suffix;
            System.out.println("2 success");
            file.transferTo(new File(path));
            System.out.println("3 success");
        }

        //add path to report
        Booking booking = bs.findBookingById(bookingString);
        rs.createReport(new Report(details,path,booking, ReportStatus.PROCESSING));
        System.out.println("4 success");
        //test for getting real path for app
        /*System.out.println(request.getServletContext().getRealPath("/"));*/ //real path in running environment like local tomcat
        System.out.println(path);//real path in local
        /*ms.sendSimpleMail("e0838388@u.nus.edu","report test","new report generated!");*/
        return "report-success";

    }

}

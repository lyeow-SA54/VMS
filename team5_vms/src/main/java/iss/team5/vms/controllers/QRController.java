package iss.team5.vms.controllers;

import java.awt.image.BufferedImage;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iss.team5.vms.generators.QRGenerator;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/barcodes")
public class QRController {

	@GetMapping(value = "/{bookingId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> qrgenQRCode(@PathVariable String bookingId) throws Exception {
		String data = "http://10.0.2.2:8080/api/student/checkin/"+bookingId;
        return okResponse(QRGenerator.generateQRCodeImage(data));
    }

	private ResponseEntity<BufferedImage> okResponse(BufferedImage image) {
        return new ResponseEntity<>(image, HttpStatus.OK);
    }
}

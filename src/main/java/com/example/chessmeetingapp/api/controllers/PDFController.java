package com.example.chessmeetingapp.api.controllers;

import com.example.chessmeetingapp.entities.Reservation;
import com.example.chessmeetingapp.services.ReservationService;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

@Controller
@RequestMapping("/pdf")
public class PDFController {

    @Autowired
    ServletContext servletContext;

    private final TemplateEngine templateEngine;
    private final ReservationService reservationService;

    public static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);

    public PDFController(TemplateEngine templateEngine, ReservationService reservationService){
        this.templateEngine = templateEngine;
        this.reservationService = reservationService;
    }

    @RequestMapping(path = "/{reservationId}")
    public String getOrderPage(@PathVariable("reservationId") int Id, Model model) {
        Reservation reservation = reservationService.getReservationById(Id).get();
        model.addAttribute("reservation", reservation);
        return "reservation";
    }


    @RequestMapping(path = "/download/{reservationId}")
    public ResponseEntity<?> getPDF(@PathVariable("reservationId") int Id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        /* Do Business Logic*/

        Reservation reservation = reservationService.getReservationById(Id).get();

        /* Create HTML using Thymeleaf template Engine */

        WebContext context = new WebContext(request, response, servletContext);
        context.setVariable("reservation", reservation);
        String orderHtml = templateEngine.process("reservation", context);

        /* Setup Source and target I/O streams */

        ByteArrayOutputStream target = new ByteArrayOutputStream();
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("http://localhost:8080");
        /* Call convert method */
        HtmlConverter.convertToPdf(orderHtml, target, converterProperties);

        /* extract output as bytes */
        byte[] bytes = target.toByteArray();


        /* Send the response as downloadable PDF */

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reservation-data.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);

    }

}

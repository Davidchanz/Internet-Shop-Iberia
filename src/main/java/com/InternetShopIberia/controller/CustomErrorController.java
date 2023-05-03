package com.InternetShopIberia.controller;

import com.InternetShopIberia.dto.ErrorDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.net.*;

@Controller
public class CustomErrorController {

    /*@GetMapping("/403")
    public String forbidden(Model model) {
        return "error/403";
    }*/

    @GetMapping("/404")
    public String notFound(HttpServletRequest request, Model model) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage("Page could not be found: " + getCurrentURL(request));
        model.addAttribute("userError", errorDto);
        return "error/404";
    }

    @GetMapping("/500")
    public String internal(HttpServletRequest request, Model model) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage("Internal serer error for request: " + getCurrentURL(request));
        model.addAttribute("userError", errorDto);
        return "error/500";
    }

    /*@GetMapping("/access-denied")
    public String accessDenied() {
        return "error/access-denied";
    }*/

    private String getCurrentURL(HttpServletRequest request){
        try {
            URL url = new URL(request.getRequestURL().toString());
            String host = url.getHost();
            String userInfo = url.getUserInfo();
            String scheme = url.getProtocol();
            int port = url.getPort();
            String path = (String) request.getAttribute("javax.servlet.forward.request_uri");
            String query = (String) request.getAttribute("javax.servlet.forward.query_string");

            URI uri = new URI(scheme, userInfo, host, port, path, query, null);
            return uri.toString();
        }catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}

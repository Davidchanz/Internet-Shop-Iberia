package com.InternetShopIberia.controller;

import com.InternetShopIberia.dto.ErrorDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.net.*;

@Controller
public class CustomErrorController {

    @GetMapping("/error/400")
    public String show4Error400(HttpServletRequest request, Model model){
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage("Bad request: " + getCurrentURL(request));
        model.addAttribute("userError", errorDto);
        return "error/400";
    }

    @GetMapping("/error/404")
    public String show4Error404(HttpServletRequest request, Model model){
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage("Page could not be found: " + getCurrentURL(request));
        model.addAttribute("userError", errorDto);
        return "error/404";
    }

    @GetMapping("/error/500")
    public String show4Error500(HttpServletRequest request, Model model){
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage("Internal serer error for request: " + getCurrentURL(request));
        model.addAttribute("userError", errorDto);
        return "error/500";
    }

    @GetMapping("/400")
    public String badRequest(HttpServletRequest request, Model model) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage("Bad request: " + getCurrentURL(request));
        model.addAttribute("userError", errorDto);
        return "error/400";
    }

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

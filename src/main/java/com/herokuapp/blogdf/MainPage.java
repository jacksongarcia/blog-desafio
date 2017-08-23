package com.herokuapp.blogdf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainPage implements Logic {
    public String executa(HttpServletRequest req, HttpServletResponse res) 
    		throws Exception {

        return "teste.jsp";

    }
}

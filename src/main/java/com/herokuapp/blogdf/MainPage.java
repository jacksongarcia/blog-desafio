package com.herokuapp.blogdf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainPage implements Logic {
    public String executa(HttpServletRequest req, HttpServletResponse res) 
    		throws Exception {

        System.out.println("Executando a logica ...");
        
        System.out.println("Retornando o nome da página JSP ...");
        return "teste.jsp";

    }
}

package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ProductDAO;
import model.Product;

public class GetProductsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            if (session.getAttribute("currentSessionUser") != null) {
                List<Product> products = getProducts();
                
                session.setAttribute("products", products);
                RequestDispatcher dispatcher = request.getRequestDispatcher("products.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            response.sendRedirect("login.html");
        }
    }

    private List<Product> getProducts() {
        ProductDAO productDAO = new ProductDAO();
        productDAO.getConnection();
        List<Product> products = productDAO.get();
        return products;
    }
}   

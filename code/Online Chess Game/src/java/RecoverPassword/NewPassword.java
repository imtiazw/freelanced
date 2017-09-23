/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RecoverPassword;

import DataAccessClasses.PersonDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ALI
 */
@WebServlet(name = "NewPassword", urlPatterns = {"/NewPassword"})
public class NewPassword extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            /*
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewPassword</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewPassword at " + request.getContextPath() + "</h1>");
            */
            String verificationCode = request.getParameter("code");
            if(verificationCode != null){
                String sql = "SELECT [userId] " +
                             "FROM [Email Verification] " +
                             "WHERE (SELECT DATEDIFF(DAY, " +
                                   "(SELECT [date] " +
                                   "FROM [Email Verification] " +
                                   "WHERE [verification_code] = '" + verificationCode + "')," +
                                   "GETDATE()) AS DATEDIF) = 0";
                String userId = "";
                userId = new PersonDAO().getUserId(sql);
                if(userId.equals("") == false){
                    request.setAttribute("userId", userId);
                    RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/newpassword.jsp");
                    requestDispatcher.forward(request, response);
                }else{
                    out.println("<h4>This Link is Either Invalid or has been Expired</h4>");
                }
            }
            /*
            out.println("</body>");
            out.println("</html>");
            */
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(NewPassword.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NewPassword.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(NewPassword.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NewPassword.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

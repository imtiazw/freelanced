/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RecoverPassword;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import GameClasses.SingInUpRecoverPassword.RecoverThePassword;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ALI
 */
@WebServlet(name = "RecoverPassword", urlPatterns = {"/RecoverPassword"})
public class RecoverPassword extends HttpServlet {

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

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RecoverPassword</title>");            
            out.println("</head>");
            out.println("<body>");

            String primaryEmail = request.getParameter("primaryEmail");
            String secondaryEmail = request.getParameter("secondaryEmail");
            String email = "";
            String userId = "";
            RecoverThePassword recoverPassword = new RecoverThePassword(primaryEmail, secondaryEmail);
            
            if(primaryEmail.equals("") == false){
                userId = recoverPassword.verifyEmail(primaryEmail);
                email = primaryEmail;
            }else if(secondaryEmail.equals("") == false){
                userId = recoverPassword.verifyEmail(secondaryEmail);
                email = secondaryEmail;
            }
            
            if(userId.equals("") == false){
                if(recoverPassword.sendPasswordRecoveryEmail(userId, email) == true){
                    out.println("<h3>Instructions to password recovery has been email to you. Please follow these instructions.</h3>");
                }
            }else{
                out.println("<h1>Email You Entered is Invalid. <a href = 'http://localhost:OnlineChessGame/recover_password.html'></a>Recover Password </h1>");
            }
            
            out.println("</body>");
            out.println("</html>");
            
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
            Logger.getLogger(RecoverPassword.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RecoverPassword.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RecoverPassword.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RecoverPassword.class.getName()).log(Level.SEVERE, null, ex);
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

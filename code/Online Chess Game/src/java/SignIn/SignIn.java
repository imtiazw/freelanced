/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SignIn;

import DataAccessClasses.PersonDAO;
import GameClasses.OnlineChessGame.GameRoom;
import GameClasses.OnlineChessGame.GameRoomMessage;
import GameClasses.SingInUpRecoverPassword.SignInProfile;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ALI
 */
public class SignIn extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    @SuppressWarnings("empty-statement")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SignIn</title>");            
            out.println("</head>");
            out.println("<body>");

            
            String userId = request.getParameter("userid");
            String password = request.getParameter("password");
            String sql = "SELECT [userId]"
                    + "FROM [Authentication]"
                    + "WHERE [userId] = '" + userId + "'"
                    + "AND [password] = '" + password + "'";
            if(new PersonDAO().verifiedAccount(sql) == true){
                sql = "SELECT * "
                    + "FROM [User], [Authentication], [Player] "
                    + "WHERE [User].userId = '" + userId + "'" 
                    + "AND [User].userId = [Authentication].userId "
                    + "AND [User].userId = [Player].playerId";
                ArrayList signInProfileInfo = new PersonDAO().personInfo(sql);
                SignInProfile signInProfile = new SignInProfile((String)signInProfileInfo.get(0), (String)signInProfileInfo.get(1), (String)signInProfileInfo.get(2), 
                                                                (String)signInProfileInfo.get(3), (String)signInProfileInfo.get(4), (String)signInProfileInfo.get(5), 
                                                                (String)signInProfileInfo.get(6), (String)signInProfileInfo.get(8), (String)signInProfileInfo.get(7), 
                                                                (String)signInProfileInfo.get(9), (boolean)signInProfileInfo.get(10), (int)signInProfileInfo.get(12), 
                                                                (boolean)signInProfileInfo.get(11));
                
                if(signInProfile.isAdmin() == true){
                    response.sendRedirect("admin.html");
                }else{
                    if(signInProfile.isBlockedAccount() == true){
                        out.println("<h3>Your account is blocked please contact at mc120401463@vu.edu.pk</h3>");
                    }else{
                        GameRoom gameRoom = (GameRoom)getServletContext().getAttribute("gameRoom");
                        if(gameRoom.getGameRoomPlayerListWindow().addPlayer(signInProfile) == true){
                            gameRoom.getGameRoomMessageWindow().getGameRoomMessage().put(userId, new LinkedList<GameRoomMessage>());
                            gameRoom.getGameRoomMessageWindow().addMessage(userId, "<span class='signInMessage'>" + userId + " Joined the room!</span>");
                            Cookie cookie = new Cookie("userId", userId);
                            response.addCookie(cookie);
                            response.sendRedirect("game_room.html");
                        } else {
                            out.println("<h1>Game Room Is Full Try Again Later</h1>");
                        }
                    }
                }
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
            Logger.getLogger(SignIn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SignIn.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SignIn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SignIn.class.getName()).log(Level.SEVERE, null, ex);
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

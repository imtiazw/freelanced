/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ChessBoard;

import GameClasses.OnlineChessGame.GameRoom;
import GameClasses.OnlineChessGame.Player;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ALI
 */
@WebServlet(name = "GetCompetitors", urlPatterns = {"/GetCompetitors"})
public class GetCompetitors extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws org.json.JSONException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            /*
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GetCompetitors</title>");            
            out.println("</head>");
            out.println("<body>");
            */
            
            String userId = request.getParameter("userId");
            GameRoom gameRoom = (GameRoom) getServletContext().getAttribute("gameRoom");
            int tableNumber = Integer.parseInt(request.getParameter("tableNumber"));
            if(gameRoom.getChessBoardList().getChessBoard()[tableNumber].getChessBoardPlayerListWindow().getBoardHost().getUserId().equals(userId) == true){
                Player player = gameRoom.getGameRoomPlayerListWindow().getPlayer(userId);
                JSONObject competitorsListJson = new JSONObject();

                ArrayList<Player> competitorsList = gameRoom.getGameRoomPlayerListWindow().getCompetitorsList(player);
                gameRoom.getChessBoardList().getChessBoard()[tableNumber].getInvitationListPlayerListWindow().addCompetetors(competitorsList);

                for (Player competitorsList1 : competitorsList) {
                    if(competitorsList1.getUserId().equals(userId) == false){
                        competitorsListJson.put(competitorsList1.getUserId(), competitorsList1.getRating().getRating());
                    }
                }

                out.print(competitorsListJson);
                /*
                out.println("<h1>Servlet GetCompetitors at " + request.getContextPath() + "</h1>");
                out.println("</body>");
                out.println("</html>");
                 */
                }
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
        } catch (JSONException ex) {
            Logger.getLogger(GetCompetitors.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (JSONException ex) {
            Logger.getLogger(GetCompetitors.class.getName()).log(Level.SEVERE, null, ex);
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

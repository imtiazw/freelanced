/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ChessBoard;

import GameClasses.LastMove.LastMove;
import GameClasses.OnlineChessGame.ChessPlayBoard;
import GameClasses.OnlineChessGame.GameRoom;
import GameClasses.OnlineChessGame.Person;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "GetMove", urlPatterns = {"/GetMove"})
public class GetMove extends HttpServlet {

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
            out.println("<title>Servlet GetMove</title>");            
            out.println("</head>");
            out.println("<body>");
            */
            
            String userId = request.getParameter("userId");
            int tableNumber = Integer.parseInt(request.getParameter("tableNumber"));
            JSONObject result = new JSONObject();
            ChessPlayBoard chessPlayBoard = ((GameRoom) getServletContext().getAttribute("gameRoom")).getChessBoardList().getChessBoard()[tableNumber].getChessPlayBoard();
            if(chessPlayBoard.getChessBoardPlayerListWindow().getPlayer() != null){
                if(chessPlayBoard.getChessBoardPlayerListWindow().getPlayer().getUserId().equals(userId) == true ||
                        chessPlayBoard.getChessBoardPlayerListWindow().getBoardHost().getUserId().equals(userId) == true){
                    if(chessPlayBoard.isGameStarted() == true){
                        if(chessPlayBoard.isCurrentPlayerControlValid(userId) == true){
                            if(chessPlayBoard.isMaximumGameTimeValid() == true){
                                Person opponentPlayer = chessPlayBoard.getChessBoardPlayerListWindow().getOpponentPlayer(userId);
                                LastMove lastMove = chessPlayBoard.getLastMove();
                                //System.out.print(lastMove);
                                if(lastMove.isPlayedByValid(opponentPlayer) == true){
                                    if(lastMove.isToBeReceivedByValid(userId) == true){
                                        if(lastMove.isPlayed() == true){
                                            if(lastMove.isRecieved() == false){
                                                lastMove.setRecieved(true);
                                                result.put("lastMove", new Gson().toJson(chessPlayBoard.getLastMove(), LastMove.class));
                                                result.put("dataAdded", true);     
                                                result.put("timePerMove", chessPlayBoard.getTimePerMove());
                                                chessPlayBoard.startDecreasingTimePerMove();                                      
                                            }
                                        }
                                    }
                                }
                            }
                        }else{
                            result.put("dataAdded", false); 
                        }
                        result.put("currentPlayerControl", chessPlayBoard.getCurrentPlayerControl());
                    }
                }
                out.print(result);
                /*
                out.println("<h1>Servlet GetMove at " + request.getContextPath() + "</h1>");
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
            Logger.getLogger(GetMove.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(GetMove.class.getName()).log(Level.SEVERE, null, ex);
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

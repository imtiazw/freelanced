/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ChessBoard;

import GameClasses.OnlineChessGame.ChessBoard;
import GameClasses.OnlineChessGame.ChessPlayBoard;
import GameClasses.OnlineChessGame.GameRoom;
import GameClasses.OnlineChessGame.Player;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
@WebServlet(name = "ExitBoard", urlPatterns = {"/ExitBoard"})
public class ExitBoard extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     * @throws org.json.JSONException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException, JSONException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            /*
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ExitBoard</title>");            
            out.println("</head>");
            out.println("<body>");
            */
            
            String userId = request.getParameter("userId");
            int tableNumber = Integer.parseInt(request.getParameter("tableNumber"));
            JSONObject result = new JSONObject();
            GameRoom gameRoom = (GameRoom) getServletContext().getAttribute("gameRoom");
            ChessBoard chessBoard = gameRoom.getChessBoardList().getChessBoard()[tableNumber];
            if(chessBoard.getChessBoardPlayerListWindow().getBoardHost().getUserId().equals(userId) == true ||
                    ((chessBoard.getChessBoardPlayerListWindow().getPlayer() != null) && chessBoard.getChessBoardPlayerListWindow().getPlayer().getUserId().equals(userId)) == true){
                Player looser = gameRoom.getGameRoomPlayerListWindow().getPlayer(userId);
                Player winner = null;
                if(chessBoard.getChessBoardPlayerListWindow().getOpponentPlayer(userId) != null){
                    winner = gameRoom.getGameRoomPlayerListWindow().getPlayer(chessBoard.getChessBoardPlayerListWindow().getOpponentPlayer(userId).getUserId());
                }
                chessBoard.exitChessBoard(looser, winner);
                result.put("exitDone", false);

                if(chessBoard.getChessBoardPlayerListWindow().isPlayerHost(looser) == true){
                    gameRoom.getChessBoardList().setChessBoard(tableNumber, new ChessBoard());
                    gameRoom.getChessBoardList().decrementNoOfChessBoards();
                    result.put("exitDone", true);
                }else if(chessBoard.getChessBoardPlayerListWindow().isPlayerHost(looser) == false){
                    chessBoard.getChessBoardPlayerListWindow().removePlayer();
                    chessBoard.setChessPlayBoard(new ChessPlayBoard());
                    chessBoard.getChessPlayBoard().setChessBoardPlayerListWindow(chessBoard.getChessBoardPlayerListWindow());
                    result.put("exitDone", true);
                }

                out.print(result);
            /*
            out.println("<h1>Servlet ExitBoard at " + request.getContextPath() + "</h1>");
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
        } catch (ClassNotFoundException | SQLException | JSONException ex) {
            Logger.getLogger(ExitBoard.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ClassNotFoundException | SQLException | JSONException ex) {
            Logger.getLogger(ExitBoard.class.getName()).log(Level.SEVERE, null, ex);
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

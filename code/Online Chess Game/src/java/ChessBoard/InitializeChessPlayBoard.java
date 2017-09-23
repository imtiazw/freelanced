/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ChessBoard;

import GameClasses.OnlineChessGame.ChessPlayBoard;
import GameClasses.OnlineChessGame.GameRoom;
import GameClasses.OnlineChessGame.Piece;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
@WebServlet(name = "InitializeChessPlayBoard", urlPatterns = {"/InitializeChessPlayBoard"})
public class InitializeChessPlayBoard extends HttpServlet {

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
            throws ServletException, IOException, JSONException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            /*
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet InitializeChessPlayBoard</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InitializeChessPlayBoard at " + request.getContextPath() + "</h1>");
            */
            String userId = request.getParameter("userId");
            int tableNumber = Integer.parseInt(request.getParameter("tableNumber"));
            String team = getTeam(request.getParameter("team"));
            JSONObject result = new JSONObject();
            
            ChessPlayBoard chessPlayBoard = ((GameRoom) getServletContext().getAttribute("gameRoom")).getChessBoardList().getChessBoard()[tableNumber].getChessPlayBoard();
            Piece[] pieces = chessPlayBoard.getPiece();
            setNextPossiblePositions(pieces, result, team);
            System.out.print(result);  //test statement
            out.print(result);
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
        } catch (JSONException ex) {
            Logger.getLogger(InitializeChessPlayBoard.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(InitializeChessPlayBoard.class.getName()).log(Level.SEVERE, null, ex);
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

    private String getTeam(String parameter) {
        String team = "";
        switch (parameter) {
            case "pink":
                team = "player";
                break;
            case "brown":
                team = "host";
                break;
        }
        
        return team;
    }

    private void setNextPossiblePositions(Piece[] pieces, JSONObject result, String team) throws JSONException {
        int startCounter = 0;
        int endCounter = 0;
        
        switch (team) {
            case "host":
                startCounter = 0;
                endCounter = (pieces.length)/2;
                break;
            case "player":
                startCounter = 16;
                endCounter = pieces.length;
                break;
        }
        for(int i=startCounter; i<endCounter; i++){
            result.put(pieces[i].getName(), pieces[i].getNextPossiblePositionsAsStrings());
        }
    }

}

package br.ucsal.geu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ucsal.geu.dao.BlocoDAO;
import br.ucsal.geu.dao.EspacoDAO;
import br.ucsal.geu.dao.TipoDAO;
import br.ucsal.geu.model.Tipo;
import br.ucsal.geu.model.Bloco;
import br.ucsal.geu.model.Espaco;

@WebServlet("/espacos")
public class EspacoController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	    String q = request.getParameter("q");
        if (q != null && q.equals("new")) {
            BlocoDAO dao = new BlocoDAO();
            request.setAttribute("lista", dao.listar());
            TipoDAO dao2 = new TipoDAO();
            request.setAttribute("tlista", dao2.listar());
            request.getRequestDispatcher("espacoform.jsp").forward(request, response);
        }else {
            EspacoDAO dao = new EspacoDAO();
            request.setAttribute("lista", dao.listar());
            request.getRequestDispatcher("espacolist.jsp").forward(request, response);
        }
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	
		String identificacao = request.getParameter("identificacao");
		String andar = request.getParameter("andar");
		String blocoID = request.getParameter("bloco");
		String tipoID = request.getParameter("tipo");
		
		Espaco espaco = new Espaco();
		espaco.setIdentificacao(identificacao);
		BlocoDAO blocoDAO = new BlocoDAO();
		int id = Integer.parseInt(blocoID);
		Bloco bloco = blocoDAO.getByID(id);
		espaco.setBloco(bloco);
		espaco.setAndar(andar);
		TipoDAO tipoDAO = new TipoDAO();
		int id2 = Integer.parseInt(tipoID);
		Tipo tipo = tipoDAO.getByID(id2);
		espaco.setTipo(tipo);
		EspacoDAO dao = new EspacoDAO();
		dao.inserir(espaco);
		request.setAttribute("lista", dao.listar());
		request.getRequestDispatcher("espacolist.jsp").forward(request, response);

	}

}

package br.ucsal.geu.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ucsal.geu.model.Bloco;
import br.ucsal.geu.model.Tipo;
import br.ucsal.geu.model.Espaco;
import br.ucsal.util.Conexao;

public class EspacoDAO {


	private Conexao conexao;

	public EspacoDAO() {
		this.conexao = Conexao.getConexao();
	}

	public List<Espaco> listarLazy() {
        Statement stmt;
        List<Espaco> espacos = new ArrayList<>();
        try {
            stmt = conexao.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select id,identificacao,bloco_id,andar,tipo_id from espacos,bloco,tipo");
            while(rs.next()) {
                Espaco e = new Espaco();
                e.setId(rs.getInt("id"));
                e.setIdentificacao(rs.getString("identificacao"));
                Bloco bloco = new Bloco();
                bloco.setId(rs.getInt("bloco_id"));
                e.setBloco(bloco);
                e.setAndar(rs.getString("andar"));
                Tipo tipo = new Tipo();
                tipo.setId(rs.getInt("tipo_id"));
                e.setTipo(tipo);
                espacos.add(e);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return espacos;
    }
public List<Espaco> listar() {
        Statement stmt;
        List<Espaco> espacos = new ArrayList<>();
        try {
            stmt = conexao.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select espacos.id,identificacao,bloco_id,nome,letra,latitude,longitude,andar,tipo_id,tipo.nome,tipo.descricao from espacos,blocos,Tipo where espacos.bloco_id = blocos.id and espacos.tipo_id = tipo.id;");
            while(rs.next()) {
                Espaco e = new Espaco();
                e.setId(rs.getInt("id"));
                e.setIdentificacao(rs.getString("identificacao"));
                Bloco bloco = new Bloco();
                bloco.setId(rs.getInt("bloco_id"));
                bloco.setNome(rs.getString("nome"));
                bloco.setLetra(rs.getString("letra"));
                bloco.setLatitude(rs.getString("latitude"));
                bloco.setLongitude(rs.getString("longitude"));
                e.setBloco(bloco);
                e.setAndar(rs.getString("andar"));
                Tipo tipo= new Tipo();
                tipo.setId(rs.getInt("tipo_id"));
                tipo.setNome(rs.getString("tipo.nome"));
                tipo.setDescricao(rs.getString("tipo.descricao"));
                e.setTipo(tipo);
                espacos.add(e);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return espacos;
    }
public void inserir(Espaco espaco) {
        try {

            PreparedStatement ps = conexao.getConnection()
                    .prepareStatement("insert into Espacos (identificacao,bloco_id,andar, tipo_id) values (?,?,?,?);");
            ps.setString(1, espaco.getIdentificacao());
            ps.setInt(2, espaco.getBloco().getId());
            ps.setString(3, espaco.getAndar());
            ps.setInt(4, espaco.getTipo().getId());
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

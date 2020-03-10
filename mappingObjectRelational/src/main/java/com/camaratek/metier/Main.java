package com.camaratek.metier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.camaratek.model.Produit;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		List<Produit> produits = chercherProduitParDesignation( "Imprimante" );
		
		int i = 0;
		
		for(Produit produit: produits) {
			i++;
			
			System.out.println( "===== Produit N° " + i + " ========\n" );
			System.out.println( "ID du produit "  + produit.getId() );
			System.out.println( "Désignation du produit "  + produit.getDesignation() );
			System.out.println( "Prix du produit "  + produit.getPrix() );
			System.out.println( "Quantité du produit "  + produit.getQuantite() );
			System.out.println("\n");
			
		}
		
	}
	
	public static List<Produit> chercherProduitParDesignation(String designation) throws ClassNotFoundException, SQLException {
		List<Produit> produits = new ArrayList<Produit>();
		
		Class.forName("com.mysql.jdbc.Driver");
		
		Connection connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/testmapping", "root", "");
		String sql = "SELECT * FROM produit WHERE designation LIKE ?";
		PreparedStatement prepareStatement = connexion.prepareStatement(sql);
		prepareStatement.setString(1, "%" + designation + "%");
		ResultSet resultSet = prepareStatement.executeQuery();
		
		while(resultSet.next()) {
			Produit produit = new Produit();
			
			produit.setId( resultSet.getLong( "id" ) );
			produit.setDesignation( resultSet.getNString( "designation" ) );
			produit.setPrix( resultSet.getDouble( "prix" ) );
			produit.setQuantite( resultSet.getInt( "quantite" ) );
			
			produits.add( produit );
		}
		
		return produits;
	}

}
